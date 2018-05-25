package src;

public class Wall extends Field {
	
	
	/**
	 * A fal-mez� konstruktora, ami a be�ll�tja, hogy a "falon l�v� p�lyaelem" mozd�thatatlan.
	 */
	public Wall() {
		fieldType = 'W';
		fixed = true;
	}
	
	
	/**
	 * A falra nem lehet feltenni semmilyen p�lyaelemet.
	 * @param e				A p�lyaelem, amit fel k�ne tennie mag�ra a falnak
	 * @param d				Az ir�ny, amerre tov�bb k�ne mozd�tania a rajta l�v� p�lyaelemet
	 * @param pushingPower  Az er�, amivel a p�lyaelemet nekitolj�k a falnak
	 * @return
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		return false;
	}
	
	
	/**
	 * A falnak nincs sz�ks�ge arra, hogy ellen�rizze, hogy beragad�s-e, ennek megfelel�en defini�lja fel�l a
	 * Field.CheckStickyt()-t (tov�bb� szomsz�dai sincsenek beregisztr�lva, �gy kiv�telt dobna).
	 */
	@Override
	public void CheckSticky() {
	}
}
