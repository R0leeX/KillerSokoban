package src;

import java.util.List;
import java.util.Optional;

import javafx.scene.shape.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StorehouseView extends Application {
	
	static List<Drawable> map;
	static int width, height;
	static Storehouse storehouse;
	static StorehouseView shView;
	
	/**
	 * A f�jlb�l beolvasott p�lya hozz�csatol�sa a View-hoz
	 * @param m	A beolvasott p�lya, Drawable objektumok 2D t�mbje
	 */	
	public void InitializeFromFile( List<Drawable> m, int x, int y ) {
		map = m;
		width = x;
		height = y;
	}
	
	/**
	 * A p�ly�t alkot� Drawable objektumok (mez�k) kirajzol�sa.
	 */
	public void DrawAll(Group g) {
		for( Drawable d : map )
			d.Draw(g);
	}

	
	/**
	 * Egy felugr� ablakban kihirdeti a gy�ztest.
	 * @param playerNum	  A gy�ztes j�t�kos sorsz�ma
	 */
	public void AnnounceWinner(int playerNum) {

		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("J�t�k v�ge");
		alert.setHeaderText(null);
		if( playerNum == 0 )
			alert.setContentText("D�ntetlen!");
		else {
			String szin;
			switch(playerNum) {
			case 1:
				szin = "z�ld";
				break;
			case 2:
				szin = "piros";
				break;
			case 3:
				szin = "k�k";
				break;	
			default:
				szin = "";
			}
						
			alert.setContentText("A " + szin + " j�t�kos gy�z�tt!");
		}
		
		alert.showAndWait();
	}
	
	
	/**
	 * Meg�ll�tja a j�t�kot, �s egy felugr� ablakot jelen�t meg, ahonnan kil�phet�nk, vagy �jraind�thatjuk 
	 * a j�t�kot.
	 */
	public void PauseGame() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Pause");
		alert.setHeaderText("");
		alert.setContentText("�j j�t�k?");

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
		    storehouse.ResetMap();
		}		
	}

	
	/**
	 * A JavaFX ablak megnyit�sa, �s egyben a j�t�k ind�t�sa.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group g = new Group();
		DrawAll(g);
		
		StorehouseController shController = new StorehouseController();
		shController.AttachModel(storehouse);
		shController.AttachView(shView);
		
		Scene scene = new Scene(g, 50*width, 50*height);
		scene.setOnKeyPressed(shController);
		
		primaryStage.setScene(scene);
		//primaryStage.setResizable(false);
        primaryStage.show();

	}
	

	public static void main(String[] args) throws InterruptedException {
		
		Parser parser = new Parser();
		storehouse = new Storehouse();
		shView = new StorehouseView();
		parser.ParseAndBuild("test0.txt", storehouse, shView);
		storehouse.AttachView(shView);
		storehouse.StartGame();
		
		launch(args);
	        
	}
}

/*
@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group g = new Group();
		for (int i = 0; i < 5; i++) {
		    Rectangle r = new Rectangle();
		    r.setY(i * 20);
		    r.setWidth(20);
		    r.setHeight(20);
		    r.setFill(Color.BLACK);
		    g.getChildren().add(r);
		}
		
		Scene scene = new Scene(g, 300, 300);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    
                    	Group g1 = new Group();
                		for (int i = 0; i < 5; i++) {
                		    Rectangle r = new Rectangle();
                		    r.setY(i * 20);
                		    r.setWidth(i*10);
                		    r.setHeight(10);
                		    r.setFill(Color.BLUE);
                		    g1.getChildren().add(r);
                		}
                		primaryStage.setScene(new Scene(g1, 200, 200));
            	        primaryStage.show();
                    break;
                }
            }
		} );
		
		scene.setOnKeyPressed(new StorehouseController(primaryStage));
		
		primaryStage.setScene(scene);
        primaryStage.show();

	}
*/