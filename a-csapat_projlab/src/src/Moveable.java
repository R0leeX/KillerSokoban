package src;

public interface Moveable {
	
	
	/**
	 * Az adott elem mozgatása a képernyõn, adott irányba.
	 */
	public void MoveInDir( Direction d);
	
	
	/**
	 * Az adott elem törlése a képernyõrõl.
	 */
	public void DeleteFromScreen();
	
}
