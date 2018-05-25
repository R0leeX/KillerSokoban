package src;

public class Worker extends Element {
	
	private static int numOfPlayers;
	
	private int playerNum;
	private int pushingPower = 100;
	private boolean moving = false;
	
	
	/**
	 * A Worker osztály konstruktora, amely az éppen létrehozott játékosnak beállítja, hogy hanyadiknak
	 * került létrehozásra, majd növeli a létrehozott jáékosok számát.
	 */
	public Worker() {
		playerNum = numOfPlayers;
		numOfPlayers++;
	}
	
	/**
	 * @return 	A még életben lévõ játékosok száma
	 */
	static int GetNumberOfPlayers() {
		return numOfPlayers;
	}
	
	/**
	 * @return	A játékos sorszáma 	
	 */
	public int GetPlayerNumber() {
		return playerNum;
	}
	
	
	/**
	 * A dolgozó mozgatását kezelõ függvény.
	 * @param d	Melyik irányba mozduljon el a dolgozó.	
	 */
	public boolean Move(Direction d) {
		boolean temp;
		
		moving = true;
		temp = ownField.Transfer(d, pushingPower);
		moving = false;
		
		return temp;
	}
	
	
	/**
	 * Az Element osztály felüldefiniált függvénye. A HitBy() egyedül a Worker.Transfer() függvény hívhatja, amennyiben a 
	 * következõ mezõ, amire lépni akar a dolgozó nem üres. Ekkor a kívánt mûködés szerint dolgozó nem tolhat el dolgozót,
	 * ezért a Worker.HitBy() egyszerûen hamissal tér vissza, jelezve, hogy az adott irányb nem lehet elmozdulni.
	 * @param e				A pályaelem, amivel ütközne a dolgozó
	 * @param d				Az irány, amerre tovább kéne mozdulnia a dolgozónak
	 * @param pushingPower	Az erõ, amivel a dolgozót megpróbálják odébbtolni
	 * @return false, hiszen erre a mezõre nem lehet továbblépni
	 */
	@Override
	public boolean HitBy(Element e, Direction d, int pushingPower) {
		return false;
	}
	
	
	/**
	 * Amennyiben a dolgozót megtolják, és az adott irányba nem tud továbbmozogni, akkor összenyomódik. Ha õ lép, és úgy nem
	 * tud továbblépni, akkor nem történik semmi.
	 * @return true, amennyiben a dolgozó nem azért mozog tovább, mert õ lépett, egyébként false
	 */
	@Override
	public boolean Smashed() {
		if(moving) {
			return false;
		}
		else {			
			// Ameddig nincs beregisztrálva Storehouse hozzá!
			if(storeHouse != null)
				storeHouse.PlayerLost( playerNum );
			numOfPlayers--;
			return true;
		}
	}
	
	
	/**
	 * Amennyiben egy lyukra lép a dolgozó, az elnyeli õt, és errõl a dolgozó értesíti az õt tartalmazó Storehouse-t.
	 */
	@Override
	public void Swallowed() {
		// Ameddig nincs beregisztrálva Storehouse hozzá!
		numOfPlayers--;
		if(storeHouse != null)
			storeHouse.PlayerLost( playerNum );
	}
	
	
	/**
	 * A dolgozó aktuális mezõjére mézet tesz, azaz megnöveli a súrlódását.
	 */
	public void PlaceHoney() {
		 ownField.AddHoney();
	}
	
	
	/**
	 * A dolgozó aktuális mezõjére olajat tesz, azaz csökkenti a súrlódását.
	 */
	public void PlaceOil() {
		ownField.AddOil();
	}
	
}
