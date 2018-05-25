package src;

import javafx.scene.Group;

public interface Drawable {
	
	/**
	 * A kirajzolandó alakzat elhelyezése a JavaFX-es Groupban.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	public void Draw(Group g);
	
}
