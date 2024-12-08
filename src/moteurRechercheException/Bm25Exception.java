package moteurRechercheException;

/**Exception if the query is null or empty.
 * 
 */
public class Bm25Exception extends TraitementException {

	private static final long serialVersionUID = -9178434577801768450L;

	/**Returns a Bm25Exception
	 * 
	 * @param cause
	 */
	public Bm25Exception() {
		super("Bm25Exception : ", new Throwable("Query is null or empty."));
		// TODO Auto-generated constructor stub
	}
}