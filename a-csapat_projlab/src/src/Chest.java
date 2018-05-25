package src;

public class Chest extends Element {

	private static int numOfChests = 0;
	
	
	public Chest() {
		numOfChests++;
	}
	
	/**
	 * A l�trehozott l�d�k sz�m�nak visszaad�sa.
	 * @return	A l�trehozott l�d�k.
	 */
	static int GetNumOfChests() {
		return numOfChests;
	}
	
	/**
	 * Miut�n az �j mez�j�t beregisztr�lta, ezen a mez�n megh�vja a WeighDown() f�ggv�nyt, hogy az tudja, hogy egy l�d�t toltak
	 * r�
	 * @param f		Az �j mez�
	 */
	@Override
	public void RegisterNewField(Field f) {		
		super.RegisterNewField(f);		
		f.WeighedDown();
	}
	
	
	/**
	 * Ha egy beragad�s mez�re tolt�k, sz�l az �t tartalmaz� Storehouse-nak, hogy eggyel kevesebb mozd�that� l�da van.
	 */
	@Override
	public void Stuck() {
		//numOfChests--;
		storeHouse.ChestLost();
	}
	
	
	/**
	 * Mivel nem nyom�dhat �ssze, mindig hamissal t�r vissza.
	 * @return	false
	 */
	@Override
	public boolean Smashed() {
		return false;
	}
	
	
	/**
	 * Ha elnyel�dik, sz�l az �t tartalmaz� Storehouse-nak, hogy eggyel kevesebb mozd�that� l�da van. 
	 */
	@Override
	public void Swallowed() {
		//numOfChests--;
		storeHouse.ChestLost();
	}

}
