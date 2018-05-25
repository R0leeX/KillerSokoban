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
	 * A kapcsol�t �tkapcsolja. Ha �gy bekapcsolt �llapotba ker�l, a s�rl�d�s�t az alap�rtelmezett �rt�kre �ll�tjuk, illetve
	 * ha van rajta p�yaelem, azt elnyeli.
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
	 * Amennyiben be van kapcsolva, a r�tolt p�lyaelemet elfogadja �s elnyeli. Egy�bk�nt �gy viselkedik, mint egy sima mez�
	 * @param e				A p�lyaelem, amit fel k�ne tennie mag�ra
	 * @param d				Az ir�ny, amerre tov�bb k�ne mozd�tania az esetlegesen rajta l�v� p�lyaelemet
	 * @param pushingPower	Az er�, amivel a p�lyaelemet r�tolj�k a lyukra/ nekitolj�k a kikapcsolt lyukon l�v� p�lyaelemnek
	 * @return	true minden esetben, ha be van kapcsolva, egy�bk�nt a Field.Accept() visszat�r�si �rt�ke
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
