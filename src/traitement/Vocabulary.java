package traitement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import base.Mot;

public class Vocabulary {
	private static final Vocabulary VOCABULARY = new Vocabulary();

	private HashMap<Mot, Integer> idMotCollection = new HashMap<>();
	private HashSet<Mot> stopMotCollection = new HashSet<>();

	private Vocabulary() {
		super();

		try {
			File file = new File("stopWords.txt");
			Scanner reader = new Scanner(file);

			while(reader.hasNextLine()) {
				String data = reader.nextLine();
				this.stopMotCollection.add(new Mot(data));
			}

			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error with opening file path for stop words.");
			e.printStackTrace();
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the idMotCollection
	 */
	public static HashMap<Mot, Integer> getIdMotCollection() {
		return VOCABULARY.idMotCollection;
	}

	/**
	 * @return the stopMotCollection
	 */
	public static HashSet<Mot> getStopMotCollection() {
		return VOCABULARY.stopMotCollection;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getIdMotCollectionSize() {
		return VOCABULARY.idMotCollection.size();
	}

	/**Returns the position of a word in the collection (id)
	 * 
	 * @param mot
	 */
	public static void addIdMot(Mot mot) {
		int id = VOCABULARY.idMotCollection.size();
		VOCABULARY.idMotCollection.put(mot, id);
	}
	
	/**
	 * 
	 * @param mot
	 * @return
	 */
	public static int getIdMot(Mot mot) {
		return VOCABULARY.idMotCollection.get(mot);
	}
}
