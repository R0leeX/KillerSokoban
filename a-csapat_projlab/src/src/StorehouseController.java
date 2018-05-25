package src;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StorehouseController implements EventHandler<KeyEvent>{
	
	private StorehouseView storehouseView;
	private Storehouse storehouse;
	
	
	/**
	 * A programhoz tartozó VIEW beregisztrálása.
	 */
	public void AttachView(StorehouseView s) {
		storehouseView = s;
	}
	
	
	/**
	 * A programhoz tartozó MODELL beregisztrálása.
	 */
	public void AttachModel(Storehouse s) {
		storehouse = s;
	}
	
	
	/**
	 * Az eseménykezelõ, reagálás az egyes billenytûnyomásokra.
	 */
	@Override	
	public void handle(KeyEvent arg0) {
		
		/*
		KeyCode keyCode = arg0.getCode();
		
		if( keyCode == KeyCode.ESCAPE )
			storehouseView.PauseGame();
		
		
		int playerNum = 0;
		Direction d;
		
		if( keyCode == KeyCode.UP || keyCode == KeyCode.DOWN
				|| keyCode == KeyCode.RIGHT || keyCode == KeyCode.LEFT )
			playerNum = 1;
		
		if( keyCode == KeyCode.W || keyCode == KeyCode.A
				|| keyCode == KeyCode.S || keyCode == KeyCode.D )
			playerNum = 2;
		
		if( playerNum != 0) {
		
			if( keyCode == KeyCode.UP || keyCode == KeyCode.W )
				d = Direction.UP;
			else {
				if( keyCode == KeyCode.DOWN || keyCode == KeyCode.S )
					d = Direction.DOWN;
				else {
					if( keyCode == KeyCode.LEFT || keyCode == KeyCode.A )
						d = Direction.LEFT;
					else 
						d = Direction.RIGHT;
				}
			}
			
			storehouse.MovePlayerNumber(playerNum, d);
		}
		*/
		
		
		
		switch (arg0.getCode()) {
        case UP:    
        	storehouse.MovePlayerNumber(1, Direction.UP);
        	break;
        
        case DOWN:  
        	storehouse.MovePlayerNumber(1, Direction.DOWN);
        	break;
        
        case LEFT:  
        	storehouse.MovePlayerNumber(1, Direction.LEFT); 
        	break;
        
        case RIGHT: 
        	storehouse.MovePlayerNumber(1, Direction.RIGHT); 
        	break;
        	
        case ENTER:
        	storehouse.ModifyFieldFriction(1, 0);
        	break;
        	
        case SPACE:
        	storehouse.ModifyFieldFriction(1, 1);
        	break;
        
        case W: 
        	storehouse.MovePlayerNumber(2, Direction.UP); 
        	break;
        
        case A: 
        	storehouse.MovePlayerNumber(2, Direction.LEFT);  
        	break;
        
        case S: 
        	storehouse.MovePlayerNumber(2, Direction.DOWN);  
        	break;
        
        case D: 
        	storehouse.MovePlayerNumber(2, Direction.RIGHT);  
        	break;
        
        case Q: 
        	storehouse.ModifyFieldFriction(2, 0);
        	break;
        	
        case E:
        	storehouse.ModifyFieldFriction(2, 1);
        	break;
        	
        	
        
        case ESCAPE: 
        	storehouseView.PauseGame(); 
        	break;
        
        default:
        	break;
		}
	}

	
	
}
