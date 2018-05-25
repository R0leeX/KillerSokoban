package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WallView extends Wall implements Drawable {

	private Rectangle wall;
	
	
	/**
	 * A fal megjelen�t�s��rt felel�s Wall lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy sz�rke n�gyzetet.
	 */
	public WallView(int x, int y) {
		wall = new Rectangle(x, y, 50, 50);
		wall.setFill( Color.DARKGREY );
		wall.setStroke(Color.BLACK);
	}
	
	
	/**
	 * A fal elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(wall);
	}

}
