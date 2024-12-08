package base;

import java.util.HashMap;

/**Calculates the size of the corpus in terms of number of words
 * 
 */
public class TailleMot extends Taille {

	/**Calculates the number of occurrence of each word in the whole corpus
	 * 
	 * @param corpus
	 * @return
	 */
	public HashMap<Mot, Integer> calculerOccurrences(Corpus corpus) {
		HashMap<Mot, Integer> occurrences = new HashMap<>();

		corpus.forEach(doc -> {
			doc.forEach(mot -> {
				if (occurrences.containsKey(mot)) {
					int value = occurrences.get(mot);
					occurrences.replace(mot, value + 1);
				} else {
					occurrences.put(mot, 1);
				}
			});
		});

		return occurrences;
	}

	@Override
	public int calculer(Corpus corpus) {
		int tailleDocument = 0;
		HashMap<Mot, Integer> occurrences = calculerOccurrences(corpus);

		for (int i = 0; i < corpus.size(); i++) {
			Document doc = corpus.get(i);
			for (Mot mot : doc) {
				tailleDocument += occurrences.get(mot); 
			}
		}

		return tailleDocument;
	}
}
