package src;

public class Worker extends Element {
	
	private static int numOfPlayers;
	
	private int playerNum;
	private int pushingPower = 100;
	private boolean moving = false;
	
	
	/**
	 * A Worker oszt�ly konstruktora, amely az �ppen l�trehozott j�t�kosnak be�ll�tja, hogy hanyadiknak
	 * ker�lt l�trehoz�sra, majd n�veli a l�trehozott j��kosok sz�m�t.
	 */
	public Worker() {
		playerNum = numOfPlayers;
		numOfPlayers++;
	}
	
	/**
	 * @return 	A m�g �letben l�v� j�t�kosok sz�ma
	 */
	static int GetNumberOfPlayers() {
		return numOfPlayers;
	}
	
	/**
	 * @return	A j�t�kos sorsz�ma 	
	 */
	public int GetPlayerNumber() {
		return playerNum;
	}
	
	
	/**
	 * A dolgoz� mozgat�s�t kezel� f�ggv�ny.
	 * @param d	Melyik ir�nyba mozduljon el a dolgoz�.	
	 */
	public boolean Move(Direction d) {
		boolean temp;
		
		moving = true;
		temp = ownField.Transfer(d, pushingPower);
		moving = false;
		
		return temp;
	}
	
	
	/**
	 * Az Element oszt�ly fel�ldefini�lt f�ggv�nye. A HitBy() egyed�l a Worker.Transfer() f�ggv�ny h�vhatja, amennyiben a 
	 * k�vetkez� mez�, amire l�pni akar a dolgoz� nem �res. Ekkor a k�v�nt m�k�d�s szerint dolgoz� nem tolhat el dolgoz�t,
	 * ez�rt a Worker.HitBy() egyszer�en hamissal t�r vissza, jelezve, hogy az adott ir�nyb nem lehet elmozdulni.
	 * @param e				A p�lyaelem, amivel �tk�zne a dolgoz�
	 * @param d				Az ir�ny, amerre tov�bb k�ne mozdulnia a dolgoz�nak
	 * @param pushingPower	Az er�, amivel a dolgoz�t megpr�b�lj�k od�bbtolni
	 * @return false, hiszen erre a mez�re nem lehet tov�bbl�pni
	 */
	@Override
	public boolean HitBy(Element e, Direction d, int pushingPower) {
		return false;
	}
	
	
	/**
	 * Amennyiben a dolgoz�t megtolj�k, �s az adott ir�nyba nem tud tov�bbmozogni, akkor �sszenyom�dik. Ha � l�p, �s �gy nem
	 * tud tov�bbl�pni, akkor nem t�rt�nik semmi.
	 * @return true, amennyiben a dolgoz� nem az�rt mozog tov�bb, mert � l�pett, egy�bk�nt false
	 */
	@Override
	public boolean Smashed() {
		if(moving) {
			return false;
		}
		else {			
			// Ameddig nincs beregisztr�lva Storehouse hozz�!
			if(storeHouse != null)
				storeHouse.PlayerLost( playerNum );
			numOfPlayers--;
			return true;
		}
	}
	
	
	/**
	 * Amennyiben egy lyukra l�p a dolgoz�, az elnyeli �t, �s err�l a dolgoz� �rtes�ti az �t tartalmaz� Storehouse-t.
	 */
	@Override
	public void Swallowed() {
		// Ameddig nincs beregisztr�lva Storehouse hozz�!
		numOfPlayers--;
		if(storeHouse != null)
			storeHouse.PlayerLost( playerNum );
	}
	
	
	/**
	 * A dolgoz� aktu�lis mez�j�re m�zet tesz, azaz megn�veli a s�rl�d�s�t.
	 */
	public void PlaceHoney() {
		 ownField.AddHoney();
	}
	
	
	/**
	 * A dolgoz� aktu�lis mez�j�re olajat tesz, azaz cs�kkenti a s�rl�d�s�t.
	 */
	public void PlaceOil() {
		ownField.AddOil();
	}
	
}
