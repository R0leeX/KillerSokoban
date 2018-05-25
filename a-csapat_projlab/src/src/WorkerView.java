package src;

import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.*;

public class WorkerView extends Worker implements Drawable, Moveable {
	
	private Circle worker;
	private Group group;
	
	private int x, y;
	private int initX, initY;
	
	private boolean deleted = false;
	
	/**
	 * A dolgozó megjelenítéséért felelõs Worker leszármazott.
	 * A konstruktor paraméterben kapott koordinátákban létrehoz egy kört, illetve a színét annak 
	 * megfelelõen állítja, hogy a játékost hanyadiknak hoztál létre (playerNum).
	 */
	public WorkerView(int x, int y) {
		
		this.initX = this.x = x + 5;
		this.initY = this.y = y + 5 /*+ 50*/;
		worker = new Circle( x+25, y+25, 20 );
		
		switch( GetPlayerNumber() ) {
			case 1:
				worker.setFill( Color.RED );
			break;
				
			case 2:
				worker.setFill( Color.DARKBLUE );
	        break;
	        
			case 3:
				worker.setFill( Color.DARKGOLDENROD );
	        break;
	        
	        default: 
	        	worker.setFill( Color.DARKGREEN );
		}
		
		worker.setStroke( Color.BLACK );
	
	}
	
	
	/**
	 * A nyelõ elhelyezése a megjelenítenõd Group-ban, a Drawable interfész által definiált függvény.
	 * @param g	 A pályára felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		group = g;
		g.getChildren().add(worker);
	}


	/**
	 * A dolgozó mozgatása megjelenítéskor, a megfelelõ koordináták beállításával.
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
	
	worker.relocate(x, y);
	
	}


	/**
	 * A dolgozó eltûntetése a pályáról azzal, hogy kiszedjük a megjelenítési csoportból.
	 */
	@Override
	public void DeleteFromScreen() {
		group.getChildren().remove( worker );
		deleted = true;
	}
	
	
	/**
	 * Ha a játékost összenyomják, és így megsemmisül, a képernyõrõl is törölni kell.
	 */
	@Override
	public boolean Smashed() {
		boolean temp = super.Smashed();
		if(temp)
			DeleteFromScreen();
		return temp;
	}

	
	/**
	 * Ha a játékos elnyelõdik, és így megsemmisül, a képernyõrõl is törölni kell.
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
		worker.relocate( x, y );
		if( !group.getChildren().contains( worker ) )
			group.getChildren().add( worker );
	}
}
