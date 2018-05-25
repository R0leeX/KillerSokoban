package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GoalView extends Goal implements Drawable {

	private Rectangle goal;
	
	
	/**
	 * A nyel� megjelen�t�s��rt felel�s Goal lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy n�gyzetet, illetve a sz�n�t
	 * annak megfelel�en �ll�tja, hogy hanyadik j�t�koshoz tartozik.
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
	 * A nyel� elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(goal);
	}
	
	
	/**
	 * Ha egy l�d�k tolnak a nyel�re, akkor elt�nteti a k�perny�r�l azt, ellenkez� esetben egyszer�en
	 * tov�bbrakja a r�l�p� j�t�kost.
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
