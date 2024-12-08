package base;

/**Calculates the size of a corpus in terms of number of docs
 * 
 */
public class TailleDocument extends Taille {
	
	@Override
	public int calculer(Corpus corpus) {
		this.taille = corpus.size(); // nombre de docs = taille du corpus qui est une collection de docs
		return this.taille;
	}
}
