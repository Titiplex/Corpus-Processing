package base;

/**Used to calculate various parameters about a corpus
 * 
 */
abstract class Taille {
	protected int taille;
	
	/**Calculates a specific size about the Corpus
	 * 
	 * @param corpus
	 * @return stats about the corpus
	 */
	public abstract int calculer(Corpus corpus);

	/**
	 * @return taille
	 */
	public int getTaille() {
		return taille;
	}
}
