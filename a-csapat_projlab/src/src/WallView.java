package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WallView extends Wall implements Drawable {

	private Rectangle wall;
	
	
	/**
	 * A fal megjelenítéséért felelõs Wall leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy szürke négyzetet.
	 */
	public WallView(int x, int y) {
		wall = new Rectangle(x, y, 50, 50);
		wall.setFill( Color.DARKGREY );
		wall.setStroke(Color.BLACK);
	}
	
	
	/**
	 * A fal elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(wall);
	}

}
