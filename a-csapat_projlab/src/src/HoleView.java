package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HoleView extends Hole implements Drawable {
	
	private Rectangle hole;
	
	
	/**
	 * A lyuk megjelen�t�s��rt felel�s Hole lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy fekete n�gyzetet.
	 */
	public HoleView(int x, int y) {
		hole = new Rectangle(x, y, 50, 50);
		hole.setFill( Color.BLACK );
		hole.setStroke( Color.BLACK );
	}
	
	
	/**
	 * Amennyiben �tkapcsolj�k, �s �gy bekapcsolt �llapotban van feket�be �ll�tja a sz�n�t, egy�bk�nt
	 * feh�rbe.
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
	 * A nyel� elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		g.getChildren().add(hole);
	}
	
	
	/**
	 * Ha egy l�d�k tolnak r� �s be van kapcsolva, akkor elt�nteti a k�perny�r�l azt, ellenkez� esetben
	 * egyszer�en tov�bbrakja a r�l�p� j�t�kost.
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
	 * Ha nincs bekapcsolva, akkor ugyan�gy lehet r� helyezni olajat.
	 */
	@Override
	public void AddOil() {
		super.AddOil();
		if( !switchedOn )
			hole.setFill( Color.LIGHTSKYBLUE );
	}
	
	
	/**
	 * Ha nincs bekapcsolva, akkor ugyan�gy lehet r� helyezni m�zet.
	 */
	@Override
	public void AddHoney() {
		super.AddHoney();
		if( !switchedOn )
			hole.setFill( Color.YELLOW );
	}

}
