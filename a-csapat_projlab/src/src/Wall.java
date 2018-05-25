package src;

public class Wall extends Field {
	
	
	/**
	 * A fal-mezõ konstruktora, ami a beállítja, hogy a "falon lévõ pályaelem" mozdíthatatlan.
	 */
	public Wall() {
		fieldType = 'W';
		fixed = true;
	}
	
	
	/**
	 * A falra nem lehet feltenni semmilyen pályaelemet.
	 * @param e				A pályaelem, amit fel kéne tennie magára a falnak
	 * @param d				Az irány, amerre tovább kéne mozdítania a rajta lévõ pályaelemet
	 * @param pushingPower  Az erõ, amivel a pályaelemet nekitolják a falnak
	 * @return
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		return false;
	}
	
	
	/**
	 * A falnak nincs szüksége arra, hogy ellenõrizze, hogy beragadós-e, ennek megfelelõen definiálja felül a
	 * Field.CheckStickyt()-t (továbbá szomszédai sincsenek beregisztrálva, így kivételt dobna).
	 */
	@Override
	public void CheckSticky() {
	}
}
