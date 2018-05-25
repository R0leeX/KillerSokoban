package src;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ChestView extends Chest implements Drawable, Moveable {

	private Rectangle chest;
	private Group group;
	
	private int initX, initY;
	private int x, y;
	
	private boolean deleted = false;
	
	
	
	/**
	 * A l�da megjelen�t�s��rt felel�s Chest lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy barna n�gyzetet.
	 */
	public ChestView(int x, int y) {			
		this.initX = this.x = x + 5;										// Hogy az 50*50-es Field k�zep�re ker�lj�n
		this.initY = this.y = y + 5 /*+ 50*/;								// Az p�lya l�trehoz�sakor h�vott Accept() miatt sz�ks�ges
		chest = new Rectangle(this.x, this.y, 40, 40);
		chest.setFill( Color.SADDLEBROWN );
		chest.setStroke( Color.BLACK );
	}
	
	
	/**
	 * A l�da elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		group = g;
		g.getChildren().add( chest );
	}
	
	
	/**
	 * A l�da mozgat�sa megjelen�t�skor, a megfelel� koordin�t�k be�ll�t�s�val.
	 * @param d	 A mozgat�s ir�nya
	 */
	@Override
	public void MoveInDir(Direction d) {
		
		switch ( d ) {
    	case UP:
    		y -= 50;
    		break;
    
    	case DOWN:  
    		y += 50;
    		break;
    
    	case LEFT:  
    		x -= 50;
    		break;
    
    	case RIGHT: 
    		x += 50;
    		break;
		}

	chest.relocate(x, y);

	}

	
	/**
	 * A l�da elt�ntet�se a p�ly�r�l azzal, hogy kiszedj�k a megjelen�t�si csoportb�l.
	 */
	@Override
	public void DeleteFromScreen() {
		group.getChildren().remove( chest );
		deleted = true;
	}

	
	/**
	 * Ha a l�da elnyel�dik, �s �gy megsemmis�l, a k�perny�r�l is t�r�lni kell.
	 */
	@Override
	public void Swallowed() {
		super.Swallowed();
		DeleteFromScreen();
	}
	
	@Override
	public void Reset() {
		x = initX;
		y = initY;
		chest.relocate( x, y );
		if( !group.getChildren().contains( chest ) )
			group.getChildren().add( chest );
	}
}
