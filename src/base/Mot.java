package base;

import java.util.Objects;

/**A word (string)
 * 
 */
public class Mot {
	
	private String mot;

	/** Creates a mot from a string
	 * @param mot
	 */
	public Mot(String mot) {
		this.mot = mot.toLowerCase(); // to lower case pour prendre en compte les mots aux débuts de phrases, etc
	}

	/**
	 * @return the string of mot
	 */
	public String getMot() {
		return mot;
	}

	// redef les deux fct suivantes pour comparer les mots entre eux
	@Override
	public int hashCode() {
		return Objects.hash(mot);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mot other = (Mot) obj;
		return Objects.equals(mot, other.mot);
	}
}
