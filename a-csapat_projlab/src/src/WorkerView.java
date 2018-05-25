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
	 * A dolgoz� megjelen�t�s��rt felel�s Worker lesz�rmazott.
	 * A konstruktor param�terben kapott koordin�t�kban l�trehoz egy k�rt, illetve a sz�n�t annak 
	 * megfelel�en �ll�tja, hogy a j�t�kost hanyadiknak hozt�l l�tre (playerNum).
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
	 * A nyel� elhelyez�se a megjelen�ten�d Group-ban, a Drawable interf�sz �ltal defini�lt f�ggv�ny.
	 * @param g	 A p�ly�ra felhelyezett alakzatok "csoportja" (JavaFX).
	 */
	@Override
	public void Draw(Group g) {
		group = g;
		g.getChildren().add(worker);
	}


	/**
	 * A dolgoz� mozgat�sa megjelen�t�skor, a megfelel� koordin�t�k be�ll�t�s�val.
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
	
	worker.relocate(x, y);
	
	}


	/**
	 * A dolgoz� elt�ntet�se a p�ly�r�l azzal, hogy kiszedj�k a megjelen�t�si csoportb�l.
	 */
	@Override
	public void DeleteFromScreen() {
		group.getChildren().remove( worker );
		deleted = true;
	}
	
	
	/**
	 * Ha a j�t�kost �sszenyomj�k, �s �gy megsemmis�l, a k�perny�r�l is t�r�lni kell.
	 */
	@Override
	public boolean Smashed() {
		boolean temp = super.Smashed();
		if(temp)
			DeleteFromScreen();
		return temp;
	}

	
	/**
	 * Ha a j�t�kos elnyel�dik, �s �gy megsemmis�l, a k�perny�r�l is t�r�lni kell.
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
