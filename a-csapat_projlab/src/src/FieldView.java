package src;

import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class FieldView extends Field implements Drawable {
	
	private Rectangle field;
	
	
	/**
	 * A sima mezõ megjelenítéséért felelõs Field leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy fehér négyzetet.
	 */
	public FieldView(int x, int y) {
		field = new Rectangle(x, y, 50, 50);
		field.setFill( Color.WHITE );
		field.setStroke( Color.BLACK );
	}
	
	
	/**
	 * A mezõ elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(field);
		
		/*
		if(sticky)
			field.setFill( Color.CHARTREUSE );
		*/
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
		System.out.println("WHY??");
	}
	
	
	@Override
	public void CheckSticky() {
		super.CheckSticky();
		if(sticky)
			field.setFill( Color.CHARTREUSE );
			
	}
	

}
