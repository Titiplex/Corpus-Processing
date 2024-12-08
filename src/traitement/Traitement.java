package traitement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.lang.Math;

import base.Corpus;
import base.Document;
import base.Mot;
import moteurRechercheException.Bm25Exception;
import moteurRechercheException.TfIdfException;

public abstract class Traitement {

	protected HashMap<Document, double[]> tf; 
	private HashMap<Mot, Integer> numberDocs;
	protected double[] idf;
	protected int poidsMotsTitre = 2000;

	protected Traitement() {
		this.tf	= new HashMap<>();
		this.numberDocs = new HashMap<>();
	}

	/**Fills the instance of Vocabulary with corpus words
	 * 
	 * @param corpus
	 */
	protected static void vocabulaire(Corpus corpus) {
		corpus.forEach(doc -> {
			doc.forEach(mot -> {
				if(!(Vocabulary.getStopMotCollection().contains(mot)) 
						&& !(Vocabulary.getIdMotCollection().containsKey(mot))) 
					Vocabulary.addIdMot(mot);
			});
		});
	}

	/**Variation of vocabulaire that doesn't take into account stop words
	 * 
	 * @param corpus
	 */
	protected static void vocabulaireSansStop(Corpus corpus) {
		corpus.forEach(doc -> {
			doc.forEach(mot -> {
				if(!(Vocabulary.getIdMotCollection().containsKey(mot))) 
					Vocabulary.addIdMot(mot);
			});
		});
	}

	/**
	 * 
	 * @param corpus
	 */
	protected void populateDocsForWord(Corpus corpus) {
		for(Document doc : corpus) {
			HashSet<Mot> motsDoc = new HashSet<>(doc);
			for(Mot mot : motsDoc) {
				if(!this.numberDocs.containsKey(mot)) this.numberDocs.put(mot, 1);
				else {
					int value = this.numberDocs.get(mot);
					this.numberDocs.replace(mot, value + 1);
				}
			}
		}
	}

	/**We search for the number of documents that contain the searched word
	 * 
	 * @param corpus
	 * @param searchMot
	 * @return
	 */
	protected int getNumberDocForWord(Mot searchMot) {
		return numberDocs.get(searchMot);
	}

	/**Gets the frequency of words in the query
	 * 
	 * @param query
	 * @return
	 */
	protected double[] getFeatures(String query) {
		String[] wordsQuery = query.split("\\W+");
		double[] features = new double[Vocabulary.getIdMotCollectionSize()];

		for(String piece : wordsQuery) {
			Mot mot = new Mot(piece);
			if(!(Vocabulary.getStopMotCollection().contains(mot)) && Vocabulary.getIdMotCollection().containsKey(mot))
				features[Vocabulary.getIdMot(mot)]++;
		}
		for(Double tab : features) tab /= wordsQuery.length;

		return features;
	}

	/**Variation of getFeatures that doesn't take into account stop words
	 * 
	 * @param query
	 * @return
	 */
	protected double[] getFeaturesSansStop(String query) {
		String[] wordsQuery = query.split("\\W+");
		double[] features = new double[Vocabulary.getIdMotCollectionSize()];

		for(String piece : wordsQuery) {
			Mot mot = new Mot(piece);
			if(Vocabulary.getIdMotCollection().containsKey(mot))
				features[Vocabulary.getIdMot(mot)]++;
		}
		for(Double tab : features) tab /= wordsQuery.length;

		return features;
	}

	/** Calculates a cosine from two arrays/vectors of doubles
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	protected double cosinus(double[] A, double[] B) {
		double produitScalaire = 0;
		double norme1 = 0, norme2 = 0;
		for(int i = 0; i < A.length; i++) {
			produitScalaire += A[i] * B[i];
			norme1 += Math.pow(A[i], 2);
			norme2 += Math.pow(B[i], 2);
		}
		norme1 = Math.sqrt(norme1);
		norme2 = Math.sqrt(norme2);

		return Math.cos(produitScalaire/(norme1*norme2));
	}

	/**Calculates frequency for words in the corpus
	 * 
	 * @param corpus
	 * @return
	 */
	public abstract Traitement processCorpus(Corpus corpus) throws TfIdfException;

	/**Calculates frequency for words in the corpus without taking into account stop words
	 * 
	 * @param corpus
	 * @return
	 */
	public abstract Traitement processCorpusSansStop(Corpus corpus) throws TfIdfException;

	/**Calculates similarity between corpus and query
	 * 
	 * @param features
	 * @return
	 */
	protected abstract HashMap<Document, Double> evaluate(double[] features);

	/**Processes the query in order to display the appropriate docs
	 * 
	 * @param query
	 * @param maxNbDocDisplay
	 */
	public void processQuery(String query, int maxNbDocDisplay) throws Bm25Exception {
		if(query == null || query.length() == 0) throw new Bm25Exception();
		// on récup l'ensemble de poids
		HashMap<Document, Double> scoresDocuments = this.evaluate(this.getFeatures(query));

		// on va utiliser une liste pour trier, Entry = une entrée de la hashmap (mieux que utiliser des tabs)
		ArrayList<Entry<Document, Double>> sortingList = new ArrayList<>();

		for(Entry<Document, Double> entry : scoresDocuments.entrySet()) {
			sortingList.add(entry);
		}

		//on tri par la valeur double
		sortingList.sort(Entry.comparingByValue(Comparator.reverseOrder())); 
		// https://docs.oracle.com/javase/8/docs/api///java/util/Comparator.html

		// on affiche les docs
		for(int i = 0; i < maxNbDocDisplay; i++) {
			System.out.println("Title " + i + " : " + sortingList.get(i).getKey().toString());
		}
	}
}
