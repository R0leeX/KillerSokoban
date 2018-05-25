package src;

public class Switch extends Field {
	
	private Hole hole;
	
	
	/**
	 * A kapcsol� konstruktora.
	 */
	public Switch() {
		fieldType = 'S';
	}
	
	
	/**
	 * A kapcsol�hoz tartoz� lyuk beregisztr�l�sa.
	 * @param holeToSwitch	A kapcsolhat� lyuk
	 */
	public void RegisterHole(Hole holeToSwitch) {
		hole = holeToSwitch;
		hole.Switch();
	}
	
	
	
	/**
	 * Amennyiben egy l�da ker�l a kapcsol�ra, el�sz�r ugyan�gy lekezeli, mintha egy sima mez� lenne, ezut�n pedig a hozz�
	 * tartoz� lyukat bekapcsolja.
	 */
	@Override
	public void WeighedDown() {
		hole.Switch();
		super.WeighedDown();
	}
	
	
	/**
	 * Ugyan�gy elfogadja a r�ker�l� p�lyaelemeket, mint egy sima mez�. Ezel�tt azonban elt�rolja, hogy eredetileg l�da volt-e
	 * rajta. Amennyiben nem, vagy nem siker�lt felhelyeznie mag�ra a param�terben kapott p�lyaelemet, nem t�rt�nik semmi. Ha
	 * azonban el�z�leg l�da volt rajta, �s most �j p�lyaelem ker�lt r�, a kapcsol�t �tkapcsolja. Ezzel ker�lhet� el az, hogy
	 * ha egy l�da volt rajta, �s egy �j l�d�t tolunk r�, ez ne kapcsolja ki a kapcsol�hoz tartoz� lyukat, hanem az elv�rt
	 * m�k�d�s szerint bekapcsolva maradjon.
	 * @param e				A p�lyaelem, amit fel k�ne tennie mag�ra a kapcsol�nak
	 * @param d				Az ir�ny, amerre tov�bb k�ne mozd�tania a rajta l�v� p�lyaelemet
	 * @param pushingPower	Az er�, amivel a p�lyaelemet r�tolj�k a kapcsol�ra/ nekitolj�k a kapcsol�n l�v� p�lyaelemnek
	 * @return	Siker�lt-e elfogadni a r�tolt p�lyaelemet.
	 * @throws
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		boolean wasStoringChest = storingChest;
		boolean temp = super.Accept(e, d, pushingPower);
		if( temp && wasStoringChest )
			hole.Switch();
		return temp;
	}
	
	
	/**
	 * A mez� �llapot�nak vissza�ll�t�sa az eredetibe, �s a kapcsol� kikapcsol�sa.
	 */
	@Override
	public void Reset() {
		if( storingChest )
			hole.Switch();
		super.Reset();
	}
	
}
