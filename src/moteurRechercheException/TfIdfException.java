package moteurRechercheException;

/**Exception for if a corpus is null or empty
 * 
 */
public class TfIdfException extends TraitementException {

	private static final long serialVersionUID = 9137476786231809742L;

	/**Returns a TfIdfException
	 * 
	 * @param cause
	 */
	public TfIdfException(String titreCorpus) {
		super("TfIdfException : ", new Throwable(titreCorpus + " is null or empty."));
		// TODO Auto-generated constructor stub
	}
}
