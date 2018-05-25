package src;

public class Goal extends Field {
	
	private int score = 0;
	
	
	/**
	 * A nyelõ konstruktora.
	 */
	public Goal() {
		fieldType = 'G';
	}
	
	
	/**
	 * Az adott nyelõhöz tartozó pontszám lekérdezése.
	 * @return	A nyelõ által elnyelt ládák száma
	 */
	public int GetScore() {
		return score;
	}
	
	
	
	/**
	 * Amennyiben egy ládát tolnak a nyelõre, az elnyeli azt, és megnöveli a pontszámlálóját eggyel.
	 */
	@Override
	public void WeighedDown() {
		score++;
		ownElement.Swallowed();
		ownElement = null;
	}
	
	
	/**
	 * A mezõ állapotának visszaállítása az eredetibe, illetve a pontszám nullázása.
	 */
	@Override
	public void Reset() {
		super.Reset();
		score = 0;
	}
}
