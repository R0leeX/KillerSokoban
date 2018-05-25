package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HoleView extends Hole implements Drawable {
	
	private Rectangle hole;
	
	
	/**
	 * A lyuk megjelenítéséért felelõs Hole leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy fekete négyzetet.
	 */
	public HoleView(int x, int y) {
		hole = new Rectangle(x, y, 50, 50);
		hole.setFill( Color.BLACK );
		hole.setStroke( Color.BLACK );
	}
	
	
	/**
	 * Amennyiben átkapcsolják, és így bekapcsolt állapotban van feketébe állítja a színét, egyébként
	 * fehérbe.
	 */
	@Override
	public void Switch() {
		super.Switch();
		
		if( switchedOn )
			hole.setFill( Color.BLACK );
		else
			hole.setFill( Color.WHITE );
	}
	
	
	/**
	 * A nyelõ elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(hole);
	}
	
	
	/**
	 * Ha egy ládák tolnak rá és be van kapcsolva, akkor eltünteti a képernyõrõl azt, ellenkezõ esetben
	 * egyszerûen továbbrakja a rálépõ játékost.
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		
		boolean temp = super.Accept(e, d, pushingPower);
		if( temp ) {
			if( switchedOn )
				( (Moveable)e ).DeleteFromScreen();
			else
				( (Moveable)e ).MoveInDir(d);
		}		
		
		return temp; 
	}
	
	
	/**
	 * Ha nincs bekapcsolva, akkor ugyanúgy lehet rá helyezni olajat.
	 */
	@Override
	public void AddOil() {
		super.AddOil();
		if( !switchedOn )
			hole.setFill( Color.LIGHTSKYBLUE );
	}
	
	
	/**
	 * Ha nincs bekapcsolva, akkor ugyanúgy lehet rá helyezni mézet.
	 */
	@Override
	public void AddHoney() {
		super.AddHoney();
		if( !switchedOn )
			hole.setFill( Color.YELLOW );
	}

}
