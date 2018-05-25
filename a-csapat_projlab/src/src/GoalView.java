package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GoalView extends Goal implements Drawable {

	private Rectangle goal;
	
	
	/**
	 * A nyelõ megjelenítéséért felelõs Goal leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy négyzetet, illetve a színét
	 * annak megfelelõen állítja, hogy hanyadik játékoshoz tartozik.
	 */
	public GoalView(int x, int y, int goalNum) {
		goal = new Rectangle(x, y, 50, 50);
		
		switch( goalNum ) {
		case 1:
			goal.setFill( Color.RED );
		break;
			
		case 2:
			goal.setFill( Color.DARKBLUE );
        break;
        
		case 3:
			goal.setFill( Color.DARKGOLDENROD );
        break;
        
        default: 
        	goal.setFill( Color.DARKGREEN );
	}
		
		goal.setStroke(Color.BLACK);
	}
	
	
	/**
	 * A nyelõ elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(goal);
	}
	
	
	/**
	 * Ha egy ládák tolnak a nyelõre, akkor eltünteti a képernyõrõl azt, ellenkezõ esetben egyszerûen
	 * továbbrakja a rálépõ játékost.
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		
		boolean temp = super.Accept(e, d, pushingPower);
		
		if(temp && (ownElement != null) ) {
			if(storingChest)
				( (Moveable)ownElement ).DeleteFromScreen();
			else
				( (Moveable)ownElement ).MoveInDir(d);
		}
		
		return temp;
	}

}
