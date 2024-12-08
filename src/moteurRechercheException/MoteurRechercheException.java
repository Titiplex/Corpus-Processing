package moteurRechercheException;

/**Exception for any problem with this search engine
 * 
 */
public abstract class MoteurRechercheException extends Exception {

	private static final long serialVersionUID = -2074397578835019980L;

	/**Returns a MoteurRechercheException
	 * @param message
	 * @param cause
	 */
	public MoteurRechercheException(String message, Throwable cause) {
		super("MoteurRechercheException, " + message, cause);
		// TODO Auto-generated constructor stub
	}
}
