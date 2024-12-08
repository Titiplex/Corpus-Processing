package moteurRechercheException;

/**Exception for if the generating file for the corpus is a null or empty file
 * 
 */
public class CorpusException extends MoteurRechercheException {

	private static final long serialVersionUID = 644811171861827728L;

	/**Returns a Corpus Exception
	 * 
	 * @param cause
	 */
	public CorpusException(String filePath) {
		super("CorpusException : ", new Throwable(filePath + " is a null or empty file."));
		// TODO Auto-generated constructor stub
	}
}