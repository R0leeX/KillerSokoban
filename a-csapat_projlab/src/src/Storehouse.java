package src;

public class Storehouse {
	
	private Field[][] map;
	private Worker[] players;
	private Goal[] goals;
	
	private int moveableChests;
	private int playersAlive;
	
	private InitState initState;
	
	private boolean gameRunning = false;
	
	private StorehouseView storehouseView;
	
	
	
	/**
	 * Egy fájlból beolvasott, és az alapján elkészített map-pel és a megfelelõ fájlból olvasott paraméterekkel inicializáljuk
	 * a Storehouse-t.
	 * @param m		A létrehozott pálya mezõinek 2D tömbje
	 * @param p		A játékosok tömbje
	 * @param g		A nyelõk tömbje
	 */
	public void InitalizeFromFile(Field[][] m, Worker[] p, Goal[] g) {
		map = m;
		players = p;
		goals = g;
		
		moveableChests = Chest.GetNumOfChests();
		playersAlive = Worker.GetNumberOfPlayers();
	}
	
	
	/**
	 * A Storehouse-hoz tartozó VIEW beregisztrálása.
	 */
	public void AttachView( StorehouseView shView) {
		storehouseView = shView;
	}
	
	
	/**
	 * A pálya kezdeti állapotának eltárolása.
	 */
	public void SaveInitState(InitState is) {
		initState = is;
	}
	
	
	/**
	 * A pálya visszaállítása a kezdeti állapotra.
	 */
	public void ResetMap() {
		for(int i = 1; i < map.length-1; i++)
			for(int j = 1; j < map[0].length-1; j++) {
				map[i][j].Reset();
			}
		
		playersAlive = initState.workers.length;
		moveableChests = initState.chests.length;
		
		for(int i = 0; i < playersAlive; i++) {
			players[i] = initState.workers[i];
			players[i].Reset();
			map[ initState.workerX[i] ][ initState.workerY[i] ].AddElement( players[i] );
		}
		
		for(int i = 0; i < moveableChests; i++) {
			map[ initState.chestX[i] ][ initState.chestY[i] ].AddElement( initState.chests[i] );
			initState.chests[i].Reset();
		}
		
		MarkStickyFields();
		StartGame();	
	}
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	/**
	 * Az adott sorszámú játékos mozgatása a paraméterben átvett irányba.
	 * @param playerNum	A mozgatandó játékos sorszáma
	 * @param d			A mozgatás iránya
	 */
	public void MovePlayerNumber(int playerNum, Direction d) {
		// StorehouseController-ben a játékosokat 1-tõl számozzuk --> [ playerNum-1 ]
		if( gameRunning && players[ playerNum-1 ] != null )
			players[ playerNum-1 ].Move(d);
	}
	
	
	/**
	 * Az adott sorszámú játékos valamit elhelyez a mezõn.
	 * @param playerNum		A mozgatandó játékos sorszáma
	 * @param frictionType	Mit tesz a játékos a mezõre ( 0: méz, 1: olaj )
	 */
	public void ModifyFieldFriction(int playerNum, int frictionType) {
		// StorehouseController-ben a játékosokat 1-tõl számozzuk --> [ playerNum-1 ]
		if( gameRunning && players[ playerNum-1 ] != null )
			if( frictionType == 0)
				players[ playerNum-1 ].PlaceHoney();
			else
				players[ playerNum-1 ].PlaceOil();
	}
	
	
	/**
	 * Amenniyben egy láda beragad vagy elnyelõdik, csökkenti a mozdítható ládák számát. Ha így ez a szám egynél kisebb, vége
	 * a játéknak.
	 */
	public void ChestLost() {
		moveableChests--;
		System.out.println("Moveable: " + moveableChests + " Összes: " + Chest.GetNumOfChests() );
		if(moveableChests < 1) 
			GameOver();
	}
	
	
	/**
	 * Amenniyben egy játékos összenyomódik vagy elnyeli egy lyuk, csökkenti a még aktív játékosok számát, illetve nullba
	 * állítja a hozzá tartozó nyelõt Ha így ez a szám kettõnél kisebb, vége a játéknak.
	 * @param playerNum	A meghalt játékos sorszáma
	 */
	public void PlayerLost(int playerNum) {
		// A Worker hívja a saját sorszámával - a játékosokat 0-tõl számozzuk --> [ playerNum ]
		
		// goals[playerNum] = null;
		players[ playerNum ] = null;
		
		playersAlive--;
		if(playersAlive < 2)
			GameOver();
	}
	
	
	/**
	 * Elindítja a játékot.
	 */
	public void StartGame() {
		gameRunning = true;
	}
	
	
	/**
	 * A játékot megállítja. Amenniyben már csak egy játékos maradt, kikiáltja gyõztesnek, az alapján, hogy a nyelõ tömb melyik
	 * eleme nem null, egyébként megkeresi a nem null nyelõk közül a legnagyobb pontszámút, és az ahhoz tartozó játékost 
	 * kiáltja ki gyõztesnek, illetve pontegyezés esetén döntetlent hirdet.
	 */
	public void GameOver() {
		
		
		if (gameRunning) {
			if (playersAlive < 2) {
				// for (int i = 0; i < goals.length; i++)
				// 		if (goals[i] != null) {
				for (int i = 0; i < players.length; i++)
					if ( players[i] != null ) {
						storehouseView.AnnounceWinner( i + 1 );
						System.out.println((i + 1) + ". jatekos nyert!\n");
					}
			} 
			else {
				int bestPlayer = 0;
				boolean draw = false; // --> a döntetlen lekezelése!
				
				//					goals.lenght
				for (int i = 1; i < players.length; i++) {
					if ( players[i] != null ) {
						// if ( goals[i] != null ) {
						if ( goals[i].GetScore() > goals[bestPlayer].GetScore()) {
							draw = false;
							bestPlayer = i;
						} else if (goals[i].GetScore() == goals[bestPlayer].GetScore())
							draw = true;
					}
					
				}

				if (draw) {
					storehouseView.AnnounceWinner( 0 );
					System.out.println("Dontetlen\n");
				}
				else {
					storehouseView.AnnounceWinner( bestPlayer + 1 );
					System.out.println((bestPlayer + 1) + ". jatekos nyert!\n");
				}
			} 
			
			gameRunning = false;
		}
		
	}
	
	
	/**
	 * A pályát amennyiben nem fájlból olvassuk be, itt lehet inicializálni.
	 */
	public void SetUpStoreHouse() {
		// Building the map
	}
	
	
	/**
	 * A mezõk szomszédainak beregisztrálása. A falaknak nem szükséges ismerniük a szomszédaikat, ezért mennek az i és j
	 * változók (pályamagasság-1)-ig, illetve 1-tõl (pályaszélesség-1)-ig.
	 */
	public void RegisterFieldNeighbours() {
		for(int i = 1; i < map.length-1; i++)
			for(int j = 1; j < map[0].length-1; j++) {
				map[i][j].AddNeighbour(map[i-1][j], Direction.UP);
				map[i][j].AddNeighbour(map[i+1][j], Direction.DOWN);
				map[i][j].AddNeighbour(map[i][j-1], Direction.LEFT);
				map[i][j].AddNeighbour(map[i][j+1], Direction.RIGHT);
			}
	}
	
	
	
	/**
	 * Minden egyes mezõt megkér, hogy ellenõrizze, nem beragadós-e.
	 */
	public void MarkStickyFields() {
		for(int i = 1; i < map.length-1; i++)
			for(int j = 1; j < map[0].length-1; j++) {
				map[i][j].CheckSticky();
			}
	}
}
