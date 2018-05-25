package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	int x, y, numOfPlayers, numOfSwitches, numOfChests;
	Field[][] map;
	ArrayList<Drawable> mapView;
	Worker[] players;
	Switch switches[];
	Hole holesToSwitch[];
	Goal goals[];
	InitState initState;
	
	
	/**
	 * A beolvasott kapcsolók és a hozzájuk tartozó lyukak összekapcsolása.
	 */
	void LinkSwicthesToHoles() {
		for(int i = 0; i < switches.length; i++)
			switches[i].RegisterHole( holesToSwitch[i] );
	}
	
	
	
	/**
	 * A létrehozott pályát körbeveszi fallal.
	 */
	void BuildWalls() {
		for(int j = 0; j < y; j ++) {
			WallView wall0j = new WallView( j*50, 0 );
			map[0][j] = wall0j;
			mapView.add( wall0j );
			
			WallView wallxm1j = new WallView( j*50, (x-1)*50 ); 
			map[x-1][j] = wallxm1j;
			mapView.add( wallxm1j );
		}
			
		for(int i = 1; i < x-1; i++) {
			WallView walli0 = new WallView( 0, i*50 );
			map[i][0] = walli0;
			mapView.add( walli0 );
			
			WallView walliym1 = new WallView( (y-1)*50, i*50 );
			map[i][y-1] = walliym1;
			mapView.add( walliym1 );	
		}
	}
	
	
	
	/**
	 * A fájlból beolvasott adatok alapján létrehozza a megfelelõ mezõket a megfelelõ attribútumokkal a megfelelõ helyre,
	 * felépíti a falakat és beregisztrálja a mezõket, végül felteszi a pályaelemeket a megfelelõ helyre.
	 * @param fields		A mezõk leírása négyjegyû egész számok formájában
	 * @param storehouse	A feltöltendõ Storehouse objektum.
	 * @return	Minden az elvártak szerint ment-e (játékosok száma, ládák száma, mezõk száma, típusa megfelelõ volt-e)
	 * @throws
	 */
	public boolean CreateMap( String[] fields, Storehouse storeHouse ) {
		
		int playersCreated = 0;
		int chestsCreated = 0;
		
		for(int i = 1; i < x-1; i++)
			for(int j = 1; j < y-1; j++) {
				
				int fieldDescriptor = Integer.parseInt( fields[ (i-1)*(y-2) + (j-1) ] );
				
				switch( fieldDescriptor/1000 ) {
				
				case 1:
					FieldView field = new FieldView( j*50, i*50 );
					map[i][j] = field;
					mapView.add( field );
					break;
					
				case 2:
					GoalView goal = new GoalView( j*50, i*50, fieldDescriptor % 10 );
					goals[ fieldDescriptor % 10 ] = goal;
					map[i][j] = goal;
					mapView.add( goal );
					break;
					
				case 3:
					SwitchView sw = new SwitchView( j*50, i*50 );
					switches[ (fieldDescriptor % 10) - 1 ] = sw;
					map[i][j] = sw;
					mapView.add( sw );
					break;
					
				case 4:
					HoleView h = new HoleView( j*50, i*50 );
					if(  ( (fieldDescriptor%10) > 0 ) && ( holesToSwitch.length+1 > (fieldDescriptor%10) )  )
							holesToSwitch[ (fieldDescriptor%10) - 1 ] = h;
					map[i][j] = h;
					mapView.add( h );
					break;
					
				case 5:
					WallView wall = new WallView( j*50, i*50 );
					map[i][j] = wall;
					mapView.add( wall );
					break;
					
				default:
					System.out.print("Nincs ilyen mezõtípus: " +  fieldDescriptor + " ( " + (i-1) + ":" + (j-1) + " ) - ");
					return false;
				
				}
				
				switch( (fieldDescriptor/10) % 10) {
				
				case 0:
					break;
					
				case 1:
					map[i][j].AddHoney();
					break;
					
				case 2:
					map[i][j].AddOil();
					break;
					
				default:	
					System.out.print("Hibásan megadott surlodas: " + fieldDescriptor +  "( " + (i-1) + ":" + (j-1) + " ) - ");
					return false;
				}
			}
		
	
		BuildWalls();
		LinkSwicthesToHoles();
		
		for(int i = 1; i < x-1; i++)
			for(int j = 1; j < y-1; j++) {
				
				int fieldDescriptor = Integer.parseInt( fields[ (i-1)*(y-2) + (j-1) ] );
				
				switch( (fieldDescriptor/100) % 10) {
				
				case 0:
					break;
					
				case 1:
					if( playersCreated == players.length ) {
						System.out.print("Túl sok játékos ( " + (i-1) + ":" + (j-1) + " ) - ");
						return false;
					}
					else {
						WorkerView player = new WorkerView( j*50, i*50 );
						
						players[ playersCreated ] = player;
						// players[ playersCreated ].elementName = ( "P" + (playersCreated+1) );
						players[ playersCreated ].RegisterStorehouse(storeHouse);
						//map[i][j].Accept( players[ playersCreated ], Direction.UP, 0 );
						map[i][j].AddElement( players[ playersCreated ] );
						mapView.add( player );
						
						initState.workerX[ playersCreated ] = i;
						initState.workerY[ playersCreated ] = j;
						initState.workers[ playersCreated++ ] = player;
					}
					break;
					
				case 2:
					if( chestsCreated == numOfChests ) {
						System.out.print("Túl sok láda ( " + (i-1) + ":" + (j-1) + " ) - ");
						return false;
					}
					else {
						//chestsCreated++;
						ChestView chest = new ChestView( j*50, i*50 );
						// c.elementName = ( "C" + chestsCreated );
						chest.RegisterStorehouse(storeHouse);
						//map[i][j].Accept(chest, Direction.UP, 0);
						map[i][j].AddElement( chest );
						mapView.add( chest );
						
						initState.chestX[ chestsCreated ] = i;
						initState.chestY[ chestsCreated ] = j;
						initState.chests[ chestsCreated++ ] = chest;
					}
					break;
					
				default:	
					System.out.print("Nincs ilyen pályaelem típus: " +  fieldDescriptor + "( " + (i-1) + ":" + (j-1) + " ) - ");
					return false;
				}
			}
		
		
		return true;
	}



/**
 * Felolvassa a fájl négy sorát egy négy elemû tömbbe, ezeket feldarabolja a szóközök mentén. Meghívja a pálya 
 * felépítéséhez szükséges függvényeket, és figyeli, hogy minden helyesen lefutott-e.
 * @param filename		A fájl, amibõl beolvassuk a pályaleírókat
 * @param storeHouse	Az inicialiálandó MODEL objektum.
 * @param shView		Az inicilozálandó VIEW objektum.
 * @return	Sikeres volt-e a beolvasás, illetve a pálya felépítése.
 */	
	public boolean ParseAndBuild(String filename, Storehouse storeHouse, StorehouseView shView) {
	
		try( BufferedReader in = new BufferedReader(new FileReader(filename)) ) {
		
		// A pályaleíró adatok beolvasása
		String[] metaData = ( in.readLine() ).split(" ");
		
		if( metaData.length != 5 ) {
			System.out.print("\nHibás pályaleíró adatok!\n");
			return false;
		}
		else {
			x = Integer.parseInt( metaData[0] );
			y = Integer.parseInt( metaData[1] );
			map = new Field[x][y];
			mapView = new ArrayList<Drawable>();
			initState = new InitState();
			
			numOfPlayers = Integer.parseInt( metaData[2] );
			players = new Worker[ numOfPlayers ];
			goals = new Goal[ numOfPlayers ];
			initState.workerX = new int[ numOfPlayers ];
			initState.workerY = new int[ numOfPlayers ];
			initState.workers = new Worker[ numOfPlayers ];
			
			numOfSwitches = Integer.parseInt( metaData[3] );
			switches = new Switch[ numOfSwitches ];
			holesToSwitch = new Hole[ numOfSwitches ];
			
			numOfChests = Integer.parseInt( metaData[4] );
			initState.chestX = new int[ numOfChests ];
			initState.chestY = new int[ numOfChests ];
			initState.chests = new Chest[ numOfChests ];
		}
		
		
		// A mezõleírók beolvasása, majd a pálya felépítése -> CreateMap()
		String fields[] = ( in.readLine() ).split(" ");
		
		if( fields.length != (x-2)*(y-2) ) {
			System.out.print("\nHibásan megadott mezõ adatok!\n");
			return false;
		}
		else {
			if( !CreateMap(fields, storeHouse) )
				return false;
		}
		
		// A Storehouse inicializálása
		
		storeHouse.InitalizeFromFile(map, players, goals);
		storeHouse.RegisterFieldNeighbours();
		storeHouse.MarkStickyFields();
		storeHouse.SaveInitState( initState );
		
		shView.InitializeFromFile(mapView, y, x);
		
	}
	catch (FileNotFoundException e) {
		e.printStackTrace();
	} 
	catch (IOException e) {
		e.printStackTrace();
	}
		
	map = null;	
	mapView = null;
	players = null;	
	switches = null; 
	holesToSwitch = null; 
	goals = null;
	
	return true;
	}
	
}
