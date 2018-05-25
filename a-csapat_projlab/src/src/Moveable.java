package src;

public interface Moveable {
	
	
	/**
	 * Az adott elem mozgat�sa a k�perny�n, adott ir�nyba.
	 */
	public void MoveInDir( Direction d);
	
	
	/**
	 * Az adott elem t�rl�se a k�perny�r�l.
	 */
	public void DeleteFromScreen();
	
}
