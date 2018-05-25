package src;

public class Switch extends Field {
	
	private Hole hole;
	
	
	/**
	 * A kapcsoló konstruktora.
	 */
	public Switch() {
		fieldType = 'S';
	}
	
	
	/**
	 * A kapcsolóhoz tartozó lyuk beregisztrálása.
	 * @param holeToSwitch	A kapcsolható lyuk
	 */
	public void RegisterHole(Hole holeToSwitch) {
		hole = holeToSwitch;
		hole.Switch();
	}
	
	
	
	/**
	 * Amennyiben egy láda kerül a kapcsolóra, elõször ugyanúgy lekezeli, mintha egy sima mezõ lenne, ezután pedig a hozzá
	 * tartozó lyukat bekapcsolja.
	 */
	@Override
	public void WeighedDown() {
		hole.Switch();
		super.WeighedDown();
	}
	
	
	/**
	 * Ugyanúgy elfogadja a rákerülõ pályaelemeket, mint egy sima mezõ. Ezelõtt azonban eltárolja, hogy eredetileg láda volt-e
	 * rajta. Amennyiben nem, vagy nem sikerült felhelyeznie magára a paraméterben kapott pályaelemet, nem történik semmi. Ha
	 * azonban elõzõleg láda volt rajta, és most új pályaelem került rá, a kapcsolót átkapcsolja. Ezzel kerülhetõ el az, hogy
	 * ha egy láda volt rajta, és egy új ládát tolunk rá, ez ne kapcsolja ki a kapcsolóhoz tartozó lyukat, hanem az elvárt
	 * mûködés szerint bekapcsolva maradjon.
	 * @param e				A pályaelem, amit fel kéne tennie magára a kapcsolónak
	 * @param d				Az irány, amerre tovább kéne mozdítania a rajta lévõ pályaelemet
	 * @param pushingPower	Az erõ, amivel a pályaelemet rátolják a kapcsolóra/ nekitolják a kapcsolón lévõ pályaelemnek
	 * @return	Sikerült-e elfogadni a rátolt pályaelemet.
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
	 * A mezõ állapotának visszaállítása az eredetibe, és a kapcsoló kikapcsolása.
	 */
	@Override
	public void Reset() {
		if( storingChest )
			hole.Switch();
		super.Reset();
	}
	
}
