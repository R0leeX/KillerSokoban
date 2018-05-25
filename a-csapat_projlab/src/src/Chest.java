package src;

public class Chest extends Element {

	private static int numOfChests = 0;
	
	
	public Chest() {
		numOfChests++;
	}
	
	/**
	 * A létrehozott ládák számának visszaadása.
	 * @return	A létrehozott ládák.
	 */
	static int GetNumOfChests() {
		return numOfChests;
	}
	
	/**
	 * Miután az új mezõjét beregisztrálta, ezen a mezõn meghívja a WeighDown() függvényt, hogy az tudja, hogy egy ládát toltak
	 * rá
	 * @param f		Az új mezõ
	 */
	@Override
	public void RegisterNewField(Field f) {		
		super.RegisterNewField(f);		
		f.WeighedDown();
	}
	
	
	/**
	 * Ha egy beragadós mezõre tolták, szól az õt tartalmazó Storehouse-nak, hogy eggyel kevesebb mozdítható láda van.
	 */
	@Override
	public void Stuck() {
		//numOfChests--;
		storeHouse.ChestLost();
	}
	
	
	/**
	 * Mivel nem nyomódhat össze, mindig hamissal tér vissza.
	 * @return	false
	 */
	@Override
	public boolean Smashed() {
		return false;
	}
	
	
	/**
	 * Ha elnyelõdik, szól az õt tartalmazó Storehouse-nak, hogy eggyel kevesebb mozdítható láda van. 
	 */
	@Override
	public void Swallowed() {
		//numOfChests--;
		storeHouse.ChestLost();
	}

}
