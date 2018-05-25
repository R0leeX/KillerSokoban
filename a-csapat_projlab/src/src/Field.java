package src;

public class Field {

	// Csak a PROTO-s konzolos megjelen�t�shez
	protected char fieldType = 'F';						// T�bbi esetben a l�trehoz�skor �ll�tand�
	protected char currFriction = '0'; 
	//------------------------------------------
	
	
	private Field[] neighbours = new Field[4];		// A mez�t k�zrefog� n�gy szomsz�dja
	protected Element ownElement;					// A mez�n �ll� p�lyaelem
	protected boolean storingChest = false;;		// A mez� l�d�t t�rol-e
	protected boolean sticky = false;					// Amennyiben a mez�re l�d�t tolnak, az elmozd�that� lesz-e m�g
	protected boolean fixed = false;				// A mez�re lehet-e m�g l�pni/ l�d�t tolni
	protected int friction = 25;					// A mez� s�rl�d�sa
	
	
	/**
	 * Az adott ir�nyban szomsz�dos mez� let�rol�sa.
	 * @param f		A szomsz�dos mez�
	 * @param d		Amelyik ir�nyban a szomsz�d a mez�
	 */
	public void AddNeighbour(Field f, Direction d) {
		neighbours[d.ordinal()] = f;
	}
	
	
	/**
	 * A param�terben �tvett p�lyaelem elhelyez�se a mez�n.
	 * @param e	 A felhelyezend� p�lyaelem.
	 */
	public void AddElement(Element e) {
		ownElement = e;
		ownElement.RegisterNewField(this);
	}
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	/**
	 * A mez�n l�v� p�lyaelemet adja vissza.
	 * @return	A mez�n l�v� p�lyaelem
	 */
	public Element GetElement() {
		return ownElement;
	}
	
	
	/**
	 * A p�ly�n elhelyezked� p�lyaelemeket leszedi a p�ly�r�l, illetve a flag-eket alaphelyzetbe
	 * �ll�tja.
	 */
	public void Reset() {
		ownElement = null;
		storingChest = false;
		sticky = false;
		fixed = false;
		friction = 25;
	}
	
	
	
	/**
	 * Be�ll�tja, hogy a mez�re tolva egy l�d�t, az beragad.
	 */
	public void SetSticky() {
		sticky = true;
	}
	
	
	/**
	 * A mez�n l�v� j�t�kost megpr�b�lja tov�bbtenni az adott ir�nyban szomsz�dos mez�re. (Ezt a mez�dust csak a Worker.Move()
	 * met�dus haszn�lja. )
	 * @param d				Melyik ir�nyba akar tov�bbmenni a j�t�kos
	 * @param pushingPower	Az er�, amivel a dolgoz� megtolja a k�vetkez� mez�n l�v� p�lyaelemet, ha van rajta 
	 * @return	Siker�lt-e tov�bbtenni a mez�n l�v� dolgoz�t
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
	 * A param�terben kapott p�lyaelemet pr�b�lja meg felhelyezni mag�ra. Amennyiben �res a mez� ezt meg is teszi. Ellent�tes
	 * esetben megpr�b�lja �ttenni a rajta l�v� p�lyaelemet a param�terben kapott ir�nyba. Ha siker�l, let�rolja a kapott
	 * p�lyaelemet, egy�bk�nt nem t�rt�nik semmi.
	 * @param e				A p�lyaelem, amit fel k�ne tennie mag�ra a mez�nek
	 * @param d				Az ir�ny, amerre tov�bb k�ne mozd�tania a rajta l�v� p�lyaelemet
	 * @param pushingPower	Az er�, amivel a p�lyaelemet r�tolj�k a mez�re/ nekitolj�k a mez�n l�v� p�lyaelemnek
	 * @return	Siker�lt-e elfogadni a r�tolt p�lyaelemet.
	 * @throws
	 */
	public boolean Accept(Element e, Direction d, int pushingPower) {
		if(ownElement != null) {																// IF nem �res a mez�
			if(storingChest && (pushingPower-friction) < 0)
				return false;
			else
				if( !neighbours[d.ordinal()].Accept(ownElement, d, pushingPower-friction) ) {	// AND nem szabad a k�vetkez�
					if( !ownElement.Smashed() )													// AND a saj�t p�lyaelem nem 
						return false;															//     nyom�dik �ssze
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
		return true;																			// Minden egy�b esetben TRUE
	}
	
	
	
	/**
	 * Megn�zi, hogy a szomsz�dok k�z�l van-e p�rhuzamos ir�nyokban k�t olyan mez�, amire m�r biztosan nem lehet m�sik
	 * p�lyaelemet felhelyezni. 
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
	 * Amennyiben egy l�d�t toltak a mez�re, �s a mez� beragad�s volt, sz�l a szomsz�dos mez�knek, hogy r� m�r nem lehet 
	 * m�sik p�lyaelemet helyezni.
	 */
	public void AlertNeighbours() {
		neighbours[0].CheckSticky();
		neighbours[1].CheckSticky();
		neighbours[2].CheckSticky();
		neighbours[3].CheckSticky();
	}
	
	
	/**
	 * Amennyiben egy l�d�t tolnak r�, megjegyzi, hogy �ppen egy l�d�t t�rol, illetve ha beragad�s volt, �rtes�ti a t�bbieket,
	 * hogy r� m�r nem lehet m�sik p�lyaelemet helyezni.
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
	 * A s�rl�d�s�t megn�veli.
	 */
	public void AddHoney() {
		friction = 50;
		
		// PROTO konzol
		currFriction = 'M';
	}
	
	
	/**
	 * A s�rl�d�s�t cs�kkenti.
	 */
	public void AddOil() {
		friction = 0;
		
		// PROTO konzol
		currFriction = 'O';
	}
	
	
	/**
	 * Ki�rja a t�pus�t, a s�rl�d�s�t, illetve ha van rajta p�lyaelem, annak az azonos�t�j�t.
	 */
	@Override
	public String toString() {
		if( ownElement != null )
			return fieldType + ownElement.elementName + currFriction;
		else 
			return "" + fieldType + fieldType + fieldType + currFriction;
	}
}


/* Alternat�va annak eld�nt�s�re az AlertNeightbours() h�v�s hat�r�ra, hogy beragad�s-e az adott mez�.

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
