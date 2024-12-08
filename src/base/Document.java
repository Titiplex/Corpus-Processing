package base;

import java.util.ArrayList;

/**Collection of words and contains a title
 * 
 */
public class Document extends ArrayList<Mot> {

	private static final long serialVersionUID = -6912072712720334534L;
	
	private String titre;
	
	/**
	 * @param string titre
	 */
	public Document(String titre) {
		super();
		this.titre = titre;
	}

	/**
	 * @return the titre of the document
	 */
	public String getTitre() {
		return titre;
	}
	
	/**Puts a Mot in the ArrayList Document.
	 * 
	 */
	public void putMot(Mot mot) {
		// "on ajoute un mot" (partie A) donc logiquement on ne peut ajouter qu'une instance de mot
		// et non pas un string que l'on transforme en mot
		super.add(mot);
	}

	@Override
	public String toString() {
		
		String docString = this.titre + "\n\t";
		
		for(int i = 0; i < super.size(); i++) {
			if(i!=0) docString += " "; // éviter l'espace avant le premier mot du doc (pas beau)
			docString += super.get(i).getMot();
		}
		return docString;
		// autre solution : chercher le doc dans le txt à partir du string pour avoir la ponctuation
	}
}
