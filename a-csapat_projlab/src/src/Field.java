package src;

public class Field {

	// Csak a PROTO-s konzolos megjelenítéshez
	protected char fieldType = 'F';						// Többi esetben a létrehozáskor állítandó
	protected char currFriction = '0'; 
	//------------------------------------------
	
	
	private Field[] neighbours = new Field[4];		// A mezõt közrefogó négy szomszédja
	protected Element ownElement;					// A mezõn álló pályaelem
	protected boolean storingChest = false;;		// A mezõ ládát tárol-e
	protected boolean sticky = false;					// Amennyiben a mezõre ládát tolnak, az elmozdítható lesz-e még
	protected boolean fixed = false;				// A mezõre lehet-e még lépni/ ládát tolni
	protected int friction = 25;					// A mezõ súrlódása
	
	
	/**
	 * Az adott irányban szomszédos mezõ letárolása.
	 * @param f		A szomszédos mezõ
	 * @param d		Amelyik irányban a szomszéd a mezõ
	 */
	public void AddNeighbour(Field f, Direction d) {
		neighbours[d.ordinal()] = f;
	}
	
	
	/**
	 * A paraméterben átvett pályaelem elhelyezése a mezõn.
	 * @param e	 A felhelyezendõ pályaelem.
	 */
	public void AddElement(Element e) {
		ownElement = e;
		ownElement.RegisterNewField(this);
	}
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	/**
	 * A mezõn lévõ pályaelemet adja vissza.
	 * @return	A mezõn lévõ pályaelem
	 */
	public Element GetElement() {
		return ownElement;
	}
	
	
	/**
	 * A pályán elhelyezkedõ pályaelemeket leszedi a pályáról, illetve a flag-eket alaphelyzetbe
	 * állítja.
	 */
	public void Reset() {
		ownElement = null;
		storingChest = false;
		sticky = false;
		fixed = false;
		friction = 25;
	}
	
	
	
	/**
	 * Beállítja, hogy a mezõre tolva egy ládát, az beragad.
	 */
	public void SetSticky() {
		sticky = true;
	}
	
	
	/**
	 * A mezõn lévõ játékost megpróbálja továbbtenni az adott irányban szomszédos mezõre. (Ezt a mezódust csak a Worker.Move()
	 * metódus használja. )
	 * @param d				Melyik irányba akar továbbmenni a játékos
	 * @param pushingPower	Az erõ, amivel a dolgozó megtolja a következõ mezõn lévõ pályaelemet, ha van rajta 
	 * @return	Sikerült-e továbbtenni a mezõn lévõ dolgozót
	 */
	public boolean Transfer(Direction d, int pushingPower) {
		boolean temp;
		
		Element e = neighbours[d.ordinal()].GetElement();
		if(e != null) 
			temp = e.HitBy(ownElement, d, pushingPower);
		else
			temp = neighbours[d.ordinal()].Accept(ownElement, d, pushingPower);
		
		if( temp ) {
			ownElement = null;
		}
		
		return temp;
	}
	
	
	/**
	 * A paraméterben kapott pályaelemet próbálja meg felhelyezni magára. Amennyiben üres a mezõ ezt meg is teszi. Ellentétes
	 * esetben megpróbálja áttenni a rajta lévõ pályaelemet a paraméterben kapott irányba. Ha sikerül, letárolja a kapott
	 * pályaelemet, egyébként nem történik semmi.
	 * @param e				A pályaelem, amit fel kéne tennie magára a mezõnek
	 * @param d				Az irány, amerre tovább kéne mozdítania a rajta lévõ pályaelemet
	 * @param pushingPower	Az erõ, amivel a pályaelemet rátolják a mezõre/ nekitolják a mezõn lévõ pályaelemnek
	 * @return	Sikerült-e elfogadni a rátolt pályaelemet.
	 * @throws
	 */
	public boolean Accept(Element e, Direction d, int pushingPower) {
		if(ownElement != null) {																// IF nem üres a mezõ
			if(storingChest && (pushingPower-friction) < 0)
				return false;
			else
				if( !neighbours[d.ordinal()].Accept(ownElement, d, pushingPower-friction) ) {	// AND nem szabad a következõ
					if( !ownElement.Smashed() )													// AND a saját pályaelem nem 
						return false;															//     nyomódik össze
				}																				// --> FALSE
		}
	
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Field sender;
		if( d == Direction.UP ) 
			sender = neighbours[ (Direction.DOWN ).ordinal() ];
		else {
			if( d == Direction.DOWN )
				sender = neighbours[ ( Direction.UP ).ordinal() ];
			else {
				if( d == Direction.RIGHT )
					sender = neighbours[ ( Direction.LEFT ).ordinal() ];
				else
					sender = neighbours[ ( Direction.RIGHT ).ordinal() ];
			}
		}
		
		if( sender != null) {
			sender.ownElement = null;
			sender.storingChest = false;
		}
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		ownElement = e;
		storingChest = false;
		ownElement.RegisterNewField(this);
		return true;																			// Minden egyéb esetben TRUE
	}
	
	
	
	/**
	 * Megnézi, hogy a szomszédok közül van-e párhuzamos irányokban két olyan mezõ, amire már biztosan nem lehet másik
	 * pályaelemet felhelyezni. 
	 */
	public void CheckSticky() {
		if(!sticky)
			if( (neighbours[0].fixed || neighbours[1].fixed) && (neighbours[2].fixed || neighbours[3].fixed) ) {
				sticky = true;	
				if(storingChest) {
					fixed = true;
					AlertNeighbours();
					ownElement.Stuck();
				}
			}
	}
	
	
	
	/**
	 * Amennyiben egy ládát toltak a mezõre, és a mezõ beragadós volt, szól a szomszédos mezõknek, hogy rá már nem lehet 
	 * másik pályaelemet helyezni.
	 */
	public void AlertNeighbours() {
		neighbours[0].CheckSticky();
		neighbours[1].CheckSticky();
		neighbours[2].CheckSticky();
		neighbours[3].CheckSticky();
	}
	
	
	/**
	 * Amennyiben egy ládát tolnak rá, megjegyzi, hogy éppen egy ládát tárol, illetve ha beragadós volt, értesíti a többieket,
	 * hogy rá már nem lehet másik pályaelemet helyezni.
	 */
	public void WeighedDown() {
		if(sticky) {
			fixed = true;
			AlertNeighbours();
			ownElement.Stuck();
		}
		storingChest = true;
	}
	
	
	/**
	 * A súrlódását megnöveli.
	 */
	public void AddHoney() {
		friction = 50;
		
		// PROTO konzol
		currFriction = 'M';
	}
	
	
	/**
	 * A súrlódását csökkenti.
	 */
	public void AddOil() {
		friction = 0;
		
		// PROTO konzol
		currFriction = 'O';
	}
	
	
	/**
	 * Kiírja a típusát, a súrlódását, illetve ha van rajta pályaelem, annak az azonosítóját.
	 */
	@Override
	public String toString() {
		if( ownElement != null )
			return fieldType + ownElement.elementName + currFriction;
		else 
			return "" + fieldType + fieldType + fieldType + currFriction;
	}
}


/* Alternatíva annak eldöntésére az AlertNeightbours() hívás határára, hogy beragadós-e az adott mezõ.

    public void CheckNeighbours(int dir1, int dir2) {
		if( neighbours[dir1].fixed || neighbours[dir2].fixed ) {
			sticky = true;	
			if(storingChest) {
				fixed = true;
				AlertNeighbours();
			}
		}
	}
	
	
	public void CheckSticky(Direction d) {
	
	if(!sticky) {
			if( d.ordinal() < 2 ) {
				CheckNeighbours(2, 3);
			}
			else {
				CheckNeighbours(0, 1);
			}
			
		}
	
	
*/
