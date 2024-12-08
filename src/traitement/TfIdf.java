package traitement;

import java.util.HashMap;

import base.Corpus;
import base.Document;
import base.Mot;
import base.TailleDocument;
import moteurRechercheException.TfIdfException;

public class TfIdf extends Traitement {

	public TfIdf () {
		super();
	}

	@Override
	public TfIdf processCorpus(Corpus corpus) throws TfIdfException {
		if(corpus == null || corpus.size() == 0) throw new TfIdfException(corpus.getTitre());
		Traitement.vocabulaire(corpus);
		this.populateDocsForWord(corpus);
		int tailleVocab = Vocabulary.getIdMotCollectionSize();
		this.idf = new double[tailleVocab];

		for(Document doc : corpus) {
			// on calcule la fréquence de chaque mot
			double[] tabTf = new double[tailleVocab];

			for(Mot mot : doc) { // on prend le nombre d'apparition des mots
				if(Vocabulary.getIdMotCollection().containsKey(mot)) // si dans id mot collection => pas dans stop mot
					// check error
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
			// nb de docs dans le corpus / nb de docs qui contiennent le mot
			this.idf[Vocabulary.getIdMot(mot)] = Math.log10(corpus.taille(new TailleDocument()) / this.getNumberDocForWord(mot));

		return this;
	}

	@Override
	public TfIdf processCorpusSansStop(Corpus corpus) throws TfIdfException{
		if(corpus == null || corpus.size() == 0) throw new TfIdfException(corpus.getTitre());
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
			this.idf[Vocabulary.getIdMotCollection().get(mot)] = Math.log10(corpus.taille(new TailleDocument()) / this.getNumberDocForWord(mot));

		return this;
	}

	@Override
	protected HashMap<Document, Double> evaluate(double[] features) {
		HashMap<Document, Double> scoresDocuments = new HashMap<>();

		for(Document doc : this.tf.keySet()) {
			double[] tabTf = this.tf.get(doc);
			double[] tfIdf = new double[Vocabulary.getIdMotCollectionSize()];

			// on calcule le tdidf pour chaque mot
			for(int i = 0; i < Vocabulary.getIdMotCollectionSize(); i++) {
				tfIdf[i] = tabTf[i] * this.idf[i];
			}

			scoresDocuments.put(doc, this.cosinus(features, tfIdf)); // le score est calculé avec le cosinus query et tfidf
		}

		return scoresDocuments;
	}
}
