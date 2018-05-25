package src;

public class Goal extends Field {
	
	private int score = 0;
	
	
	/**
	 * A nyel� konstruktora.
	 */
	public Goal() {
		fieldType = 'G';
	}
	
	
	/**
	 * Az adott nyel�h�z tartoz� pontsz�m lek�rdez�se.
	 * @return	A nyel� �ltal elnyelt l�d�k sz�ma
	 */
	public int GetScore() {
		return score;
	}
	
	
	
	/**
	 * Amennyiben egy l�d�t tolnak a nyel�re, az elnyeli azt, �s megn�veli a pontsz�ml�l�j�t eggyel.
	 */
	@Override
	public void WeighedDown() {
		score++;
		ownElement.Swallowed();
		ownElement = null;
	}
	
	
	/**
	 * A mez� �llapot�nak vissza�ll�t�sa az eredetibe, illetve a pontsz�m null�z�sa.
	 */
	@Override
	public void Reset() {
		super.Reset();
		score = 0;
	}
}
