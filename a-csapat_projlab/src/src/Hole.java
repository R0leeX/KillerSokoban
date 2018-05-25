package src;

public class Hole extends Field {
	
	protected boolean switchedOn = true;
	
	/**
	 * A lyuk konstruktora.
	 */
	public Hole() {
		fieldType = 'H';
	}
	
	
	/**
	 * A kapcsolót átkapcsolja. Ha így bekapcsolt állapotba kerül, a súrlódását az alapértelmezett értékre állítjuk, illetve
	 * ha van rajta páyaelem, azt elnyeli.
	 */
	public void Switch() {
		switchedOn = !switchedOn;
		
		if( switchedOn ) {
			fieldType = '+';
			friction = 25;
			currFriction = '0';
			if( ownElement != null ) {
				ownElement.Swallowed();
				ownElement = null;
			}
		}
		else
			fieldType = '-';
	}
	
	
	/**
	 * Amennyiben be van kapcsolva, a rátolt pályaelemet elfogadja és elnyeli. Egyébként úgy viselkedik, mint egy sima mezõ
	 * @param e				A pályaelem, amit fel kéne tennie magára
	 * @param d				Az irány, amerre tovább kéne mozdítania az esetlegesen rajta lévõ pályaelemet
	 * @param pushingPower	Az erõ, amivel a pályaelemet rátolják a lyukra/ nekitolják a kikapcsolt lyukon lévõ pályaelemnek
	 * @return	true minden esetben, ha be van kapcsolva, egyébként a Field.Accept() visszatérési értéke
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		if( switchedOn ) {
			e.Swallowed();
			return true;	
		}
		else
			return super.Accept(e, d, pushingPower);
	}

}
