package moteurRechercheException;

/**Exception for problems happening during processing functions
 * 
 */
public abstract class TraitementException extends MoteurRechercheException {

	private static final long serialVersionUID = -3621116870808920811L;

	/**Returns a TraitementException
	 * @param message
	 * @param cause
	 */
	public TraitementException(String message, Throwable cause) {
		super("TraitementException, " + message, cause);
		// TODO Auto-generated constructor stub
	}
}
