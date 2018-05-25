package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SwitchView extends Switch implements Drawable {

	private Rectangle field;
	private Circle button;
	
	
	/**
	 * A kapcsol� megjelen�t�s��rt felel�s Switch lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy fekete n�gyzetet egy feh�r k�rrel
	 * a k�zep�n.
	 */
	public SwitchView(int x, int y) {
		field = new Rectangle(x, y, 50, 50);
		field.setFill( Color.WHITE );
		field.setStroke(Color.BLACK);
		
		button = new Circle( x+25, y+25, 10, Color.BLACK );
	} 
	
	
	/**
	 * A mez� elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().addAll(field, button);
	}
	
	
	/**
	 * Tov�bbl�pteti a k�perny�n a r�l�p� j�t�kost.
	 * ( A beragad�s (sticky) flag ha akt�v, akkor "bez�ld�l" a mez�. )
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
	 * Ha olajat tesz egy j�t�kos az adott mez�re, akkor "bek�k�l".
	 */
	@Override
	public void AddOil() {
		super.AddOil();
		field.setFill( Color.LIGHTSKYBLUE );
	}
	
	
	/**
	 * Ha m�zet tesz egy j�t�kos az adott mez�re, akkor "bes�rgul".
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
