package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SwitchView extends Switch implements Drawable {

	private Rectangle field;
	private Circle button;
	
	
	/**
	 * A kapcsoló megjelenítéséért felelõs Switch leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy fekete négyzetet egy fehér körrel
	 * a közepén.
	 */
	public SwitchView(int x, int y) {
		field = new Rectangle(x, y, 50, 50);
		field.setFill( Color.WHITE );
		field.setStroke(Color.BLACK);
		
		button = new Circle( x+25, y+25, 10, Color.BLACK );
	} 
	
	
	/**
	 * A mezõ elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().addAll(field, button);
	}
	
	
	/**
	 * Továbblépteti a képernyõn a rálépõ játékost.
	 * ( A beragadós (sticky) flag ha aktív, akkor "bezöldül" a mezõ. )
	 */
	@Override
	public boolean Accept(Element e, Direction d, int pushingPower) {
		
		boolean temp = super.Accept(e, d, pushingPower);
		
		if(temp)
			( (Moveable)ownElement ).MoveInDir(d);
		
		/*
		if(sticky)
			field.setFill( Color.CHARTREUSE );
		*/
		
		return temp;
	}
	
	/**
	 * Ha olajat tesz egy játékos az adott mezõre, akkor "bekékül".
	 */
	@Override
	public void AddOil() {
		super.AddOil();
		field.setFill( Color.LIGHTSKYBLUE );
	}
	
	
	/**
	 * Ha mézet tesz egy játékos az adott mezõre, akkor "besárgul".
	 */
	@Override
	public void AddHoney() {
		super.AddHoney();
		field.setFill( Color.YELLOW );
	}

	
	@Override
	public void Reset() {
		super.Reset();
		field.setFill( Color.WHITE );
	}

	
	@Override
	public void CheckSticky() {
		super.CheckSticky();
		if(sticky)
			field.setFill( Color.CHARTREUSE );
			
	}
}
