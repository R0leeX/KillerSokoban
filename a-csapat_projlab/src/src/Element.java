package src;

public abstract class Element {
	
	public String elementName;						// Létrehozáskor inicializálandó!
	
	protected Storehouse storeHouse;
	protected Field ownField;
	
	
	/**
	 * Visszaadja, hogy melyik mezõn áll
	 * @return	A mezõ, amin áll
	 */
	public Field GetPosition() {		
		return ownField;
	}
	
	
	/**
	 * A pályaelem visszaállítása a kezdeti állapotba.
	 */
	public void Reset() {}
	
	
	/**
	 * Elmenti azt a mezõt, amire rálépett.
	 * @param f		Az új mezõ, amin áll
	 */
	public void RegisterNewField(Field f) {
		ownField = f;
	}
	

	/**
	 * Eltárolja azt a Storehouse-t, ami õt tartalmazza.
	 * @param sH	Az a Storehouse-t, ami õt tartalmazza
	 */
	public void RegisterStorehouse(Storehouse sH) {
		storeHouse = sH;
	}
	
	
	/**
	 * Amennyiben nekiütközik egy másik pályaelem, egyszerûen megpróbálja rátenni a beléütközõ pályaelemet a saját mezõjére.
	 * @param e				A pályaelem, amivel nekiütközik a pályaelemnek
	 * @param d				Az irány, amerre tovább kéne mozdulnia a pályaelemnek
	 * @param pushingPower	Az erõ, amivel a pályaelemet megpróbálják odébbtolni
	 * @return	Sikerült-e továbbtenni a beléütközõ pályaelemet
	 */
	public boolean HitBy(Element e, Direction d, int pushingPower) {
		return ownField.Accept(e, d, pushingPower);
	}
	
	
	/**
	 * A pályaelem beragadása, az alap implemetáció szerint ekkor semmi nem történik.
	 */
	public void Stuck( ) {
	}
	
	
	
	/**
	 * A pályaelem összenyomódását kezelõ absztrakt függvény.
	 */
	public abstract boolean Smashed( );
	
	
	/**
	 * A pályaelem elnyelõdését kezelõ absztrakt függvény.
	 */
	public abstract void Swallowed();
}
