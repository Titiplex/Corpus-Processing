package traitement;

import java.util.HashMap;

import base.Corpus;
import base.Document;
import base.Mot;
import base.TailleDocument;
import base.TailleMot;
import moteurRechercheException.TfIdfException;

public class Bm25 extends Traitement {
	private double k1 = 1.2, b = 0.75, avgDl;

	public Bm25() {
		super();
	}

	@Override
	public Bm25 processCorpus(Corpus corpus) throws TfIdfException {
		if(corpus == null || corpus.size() == 0) throw new TfIdfException(corpus.getTitre());
		// TODO Auto-generated method stub
		Traitement.vocabulaire(corpus);
		this.populateDocsForWord(corpus);
		int tailleVocab = Vocabulary.getIdMotCollectionSize();
		this.idf = new double[tailleVocab];

		for(Document doc : corpus) {
			// on calcule la fréquence de chaque mot
			double[] tabTf = new double[tailleVocab];

			for(Mot mot : doc) { // on prend le nombre d'apparition des mots
				if(Vocabulary.getIdMotCollection().containsKey(mot)) 
					tabTf[Vocabulary.getIdMot(mot)]++;
			}

			// on gère le titre et on lui assigne un poids plus important
			String[] motsTitre = doc.getTitre().split("\\W+");
			for(String motTitre : motsTitre) {
				Mot mot = new Mot(motTitre);
				if(Vocabulary.getIdMotCollection().containsKey(mot)) tabTf[Vocabulary.getIdMot(mot)]+=this.poidsMotsTitre;
			}

			for(Double tab : tabTf) tab/=doc.size(); //on calcule la fréquence
			this.tf.put(doc, tabTf);
		}
		// on set idf selon la formule donnée
		for(Mot mot : Vocabulary.getIdMotCollection().keySet())
			this.idf[Vocabulary.getIdMot(mot)] = Math.log10(
					(corpus.taille(new TailleDocument()) - this.getNumberDocForWord(mot) + 0.5) 
					/ 
					(this.getNumberDocForWord(mot) + 0.5)
					+ 1
					);

		// on set avgDl qui est la longueur moyenne des docs du corpus
		this.avgDl = corpus.taille(new TailleMot()) / corpus.taille(new TailleDocument());

		return this;
	}

	@Override
	public Bm25 processCorpusSansStop(Corpus corpus) throws TfIdfException {
		if(corpus == null || corpus.size() == 0) throw new TfIdfException(corpus.getTitre());
		// TODO Auto-generated method stub
		Traitement.vocabulaireSansStop(corpus);
		this.populateDocsForWord(corpus);
		int tailleVocab = Vocabulary.getIdMotCollectionSize();
		this.idf = new double[tailleVocab];

		for(Document doc : corpus) {
			// on calcule la fréquence de chaque mot
			double[] tabTf = new double[tailleVocab];

			for(Mot mot : doc) { // on prend le nombre d'apparition des mots
				if(Vocabulary.getIdMotCollection().containsKey(mot)) 
					tabTf[Vocabulary.getIdMot(mot)]++;
			}

			// on gère le titre et on lui assigne un poids plus important
			String[] motsTitre = doc.getTitre().split("\\W+");
			for(String motTitre : motsTitre) {
				Mot mot = new Mot(motTitre);
				if(Vocabulary.getIdMotCollection().containsKey(mot)) tabTf[Vocabulary.getIdMot(mot)]+=this.poidsMotsTitre;
			}

			for(Double tab : tabTf) tab/=doc.size(); //on calcule la fréquence
			this.tf.put(doc, tabTf);
		}
		// on set idf selon la formule donnée
		for(Mot mot : Vocabulary.getIdMotCollection().keySet())
			this.idf[Vocabulary.getIdMotCollection().get(mot)] = Math.log10(
					(corpus.taille(new TailleDocument()) - this.getNumberDocForWord(mot) + 0.5) 
					/ 
					(this.getNumberDocForWord(mot) + 0.5)
					+ 1
					);

		// on set avdDl qui est la longueur moyenne des docs du corpus
		this.avgDl = corpus.taille(new TailleMot()) / corpus.taille(new TailleDocument());

		return this;
	}

	@Override
	protected HashMap<Document, Double> evaluate(double[] features) {
		// TODO Auto-generated method stub
		HashMap<Document, Double> scoresDocuments = new HashMap<>(); // hashmap pour stocker la correspondance de chaque doc

		for(Document doc : this.tf.keySet()) {
			double[] tabTf = this.tf.get(doc);
			double score = 0;

			for(int i = 0; i < Vocabulary.getIdMotCollectionSize(); i++) {
				// le score est une somme des scores des mots
				// score calculé selon la formule donnée
				score += this.idf[i] * 
						(tabTf[i] * (this.k1 + 1)) /
						(tabTf[i] + this.k1 * 
								(1 - b + b * doc.size() / this.avgDl));
			}

			scoresDocuments.put(doc, score);
		}
		return scoresDocuments;
	}
}
