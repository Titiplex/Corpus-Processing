package base;

import java.util.Scanner;
import java.util.Vector;

import moteurRechercheException.CorpusException;
import moteurRechercheException.TfIdfException;
import traitement.Traitement;

import java.io.File;
import java.io.FileNotFoundException;

/**Collection of documents and contains a title which is the path to the text file used to generate the corpus
 * 
 */
public class Corpus extends Vector<Document> {

	private static final long serialVersionUID = -7116965599619788494L;

	private String titre;
	private DataSets dataSet;
	private Traitement traitement;

	/**Constructs a corpus from a file of docs
	 * You must precise the type of corpus with DataSets
	 * @param filePath
	 * @param dataSets
	 */
	public Corpus(String filePath, DataSets dataSets) throws FileNotFoundException, CorpusException {
		super();
		if(filePath == null || filePath.isEmpty()) throw new CorpusException(filePath);
		File file = new File(filePath);
		Scanner reader = new Scanner(file);
		
		int numberOfTreatedDocs = 1000;
		int i = 0;
		
		while(reader.hasNextLine() && i < numberOfTreatedDocs) {
			String data = reader.nextLine();

			String[] columns;
			String[] corps = null;
			Document doc = null;

			if(DataSets.Ouvrage == dataSets) {
				columns = data.split("\t");
				corps = columns[6].split("\\W+"); //enlever la ponctuation
				doc = new Document(columns[2]);
			} else if (DataSets.Wikipedia == dataSets) {
				columns = data.split("|||");
				corps = columns[1].split("\\W+"); // enlever la ponctuation
				doc = new Document(columns[0]);
			}

			for(String mot : corps) doc.putMot(new Mot(mot));

			super.add(doc);
			i++;
		}

		reader.close();
		this.titre = filePath;
		this.dataSet = dataSets;
	}
	
	/**Processes the corpus into the traitement passed in parameters
	 * 
	 * @param traitement
	 * @return
	 * @throws TfIdfException
	 */
	public Traitement getFeatures(Traitement traitement) throws TfIdfException {
		traitement.processCorpus(this);
		return traitement;
	}

	@Override
	public String toString() {

		String corpus = this.titre + "\n\n"; // on ajoute le titre du corpus

		for(int i = 0; i < super.size(); i++) {
			corpus += super.get(i).toString(); // puis les documents
			corpus += "\n\n";
		}
		return corpus;
	}

	/**Adds a Document in the Corpus
	 * 
	 * @param doc
	 */
	public void addDocument(Document doc) {
		super.add(doc);
	}

	/**
	 * @return the string of titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @return the dataSet
	 */
	public DataSets getDataSet() {
		return dataSet;
	}

	/**Calculates the size of the corpus in terms of number of words
	 * 
	 * @param tailleMot
	 * @return word size of the corpus
	 */
	public int taille(TailleMot tailleMot) {
		return tailleMot.calculer(this);
	}

	/**Calculates the size of the corpus in terms of number of docs
	 * 
	 * @param tailleDocument
	 * @return doc size of the corpus
	 */
	public int taille(TailleDocument tailleDocument) {
		return tailleDocument.calculer(this);
	}

	/**
	 * @return traitement
	 */
	public Traitement getTraitement() {
		return traitement;
	}

	/**
	 * @param traitement 
	 */
	public void setTraitement(Traitement traitement) {
		this.traitement = traitement;
	}
}
