package src;

public abstract class Element {
	
	public String elementName;						// L�trehoz�skor inicializ�land�!
	
	protected Storehouse storeHouse;
	protected Field ownField;
	
	
	/**
	 * Visszaadja, hogy melyik mez�n �ll
	 * @return	A mez�, amin �ll
	 */
	public Field GetPosition() {		
		return ownField;
	}
	
	
	/**
	 * A p�lyaelem vissza�ll�t�sa a kezdeti �llapotba.
	 */
	public void Reset() {}
	
	
	/**
	 * Elmenti azt a mez�t, amire r�l�pett.
	 * @param f		Az �j mez�, amin �ll
	 */
	public void RegisterNewField(Field f) {
		ownField = f;
	}
	

	/**
	 * Elt�rolja azt a Storehouse-t, ami �t tartalmazza.
	 * @param sH	Az a Storehouse-t, ami �t tartalmazza
	 */
	public void RegisterStorehouse(Storehouse sH) {
		storeHouse = sH;
	}
	
	
	/**
	 * Amennyiben neki�tk�zik egy m�sik p�lyaelem, egyszer�en megpr�b�lja r�tenni a bel��tk�z� p�lyaelemet a saj�t mez�j�re.
	 * @param e				A p�lyaelem, amivel neki�tk�zik a p�lyaelemnek
	 * @param d				Az ir�ny, amerre tov�bb k�ne mozdulnia a p�lyaelemnek
	 * @param pushingPower	Az er�, amivel a p�lyaelemet megpr�b�lj�k od�bbtolni
	 * @return	Siker�lt-e tov�bbtenni a bel��tk�z� p�lyaelemet
	 */
	public boolean HitBy(Element e, Direction d, int pushingPower) {
		return ownField.Accept(e, d, pushingPower);
	}
	
	
	/**
	 * A p�lyaelem beragad�sa, az alap implemet�ci� szerint ekkor semmi nem t�rt�nik.
	 */
	public void Stuck( ) {
	}
	
	
	
	/**
	 * A p�lyaelem �sszenyom�d�s�t kezel� absztrakt f�ggv�ny.
	 */
	public abstract boolean Smashed( );
	
	
	/**
	 * A p�lyaelem elnyel�d�s�t kezel� absztrakt f�ggv�ny.
	 */
	public abstract void Swallowed();
}
