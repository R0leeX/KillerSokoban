package src;

import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class FieldView extends Field implements Drawable {
	
	private Rectangle field;
	
	
	/**
	 * A sima mez� megjelen�t�s��rt felel�s Field lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy feh�r n�gyzetet.
	 */
	public FieldView(int x, int y) {
		field = new Rectangle(x, y, 50, 50);
		field.setFill( Color.WHITE );
		field.setStroke( Color.BLACK );
	}
	
	
	/**
	 * A mez� elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
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
		System.out.println("WHY??");
	}
	
	
	@Override
	public void CheckSticky() {
		super.CheckSticky();
		if(sticky)
			field.setFill( Color.CHARTREUSE );
			
	}
	

}
