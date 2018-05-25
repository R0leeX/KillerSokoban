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
	 * A láda megjelenítéséért felelõs Chest leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy barna négyzetet.
	 */
	public ChestView(int x, int y) {			
		this.initX = this.x = x + 5;										// Hogy az 50*50-es Field közepére kerüljön
		this.initY = this.y = y + 5 /*+ 50*/;								// Az pálya létrehozásakor hívott Accept() miatt szükséges
		chest = new Rectangle(this.x, this.y, 40, 40);
		chest.setFill( Color.SADDLEBROWN );
		chest.setStroke( Color.BLACK );
	}
	
	
	/**
	 * A láda elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		group = g;
		g.getChildren().add( chest );
	}
	
	
	/**
	 * A láda mozgatása megjelenítéskor, a megfelelõ koordináták beállításával.
	 * @param d	 A mozgatás iránya
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
	 * A láda eltûntetése a pályáról azzal, hogy kiszedjük a megjelenítési csoportból.
	 */
	@Override
	public void DeleteFromScreen() {
		group.getChildren().remove( chest );
		deleted = true;
	}

	
	/**
	 * Ha a láda elnyelõdik, és így megsemmisül, a képernyõrõl is törölni kell.
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
