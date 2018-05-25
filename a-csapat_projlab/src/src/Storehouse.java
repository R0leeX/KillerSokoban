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
	 * Egy f�jlb�l beolvasott, �s az alapj�n elk�sz�tett map-pel �s a megfelel� f�jlb�l olvasott param�terekkel inicializ�ljuk
	 * a Storehouse-t.
	 * @param m		A l�trehozott p�lya mez�inek 2D t�mbje
	 * @param p		A j�t�kosok t�mbje
	 * @param g		A nyel�k t�mbje
	 */
	public void InitalizeFromFile(Field[][] m, Worker[] p, Goal[] g) {
		map = m;
		players = p;
		goals = g;
		
		moveableChests = Chest.GetNumOfChests();
		playersAlive = Worker.GetNumberOfPlayers();
	}
	
	
	/**
	 * A Storehouse-hoz tartoz� VIEW beregisztr�l�sa.
	 */
	public void AttachView( StorehouseView shView) {
		storehouseView = shView;
	}
	
	
	/**
	 * A p�lya kezdeti �llapot�nak elt�rol�sa.
	 */
	public void SaveInitState(InitState is) {
		initState = is;
	}
	
	
	/**
	 * A p�lya vissza�ll�t�sa a kezdeti �llapotra.
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
	 * Az adott sorsz�m� j�t�kos mozgat�sa a param�terben �tvett ir�nyba.
	 * @param playerNum	A mozgatand� j�t�kos sorsz�ma
	 * @param d			A mozgat�s ir�nya
	 */
	public void MovePlayerNumber(int playerNum, Direction d) {
		// StorehouseController-ben a j�t�kosokat 1-t�l sz�mozzuk --> [ playerNum-1 ]
		if( gameRunning && players[ playerNum-1 ] != null )
			players[ playerNum-1 ].Move(d);
	}
	
	
	/**
	 * Az adott sorsz�m� j�t�kos valamit elhelyez a mez�n.
	 * @param playerNum		A mozgatand� j�t�kos sorsz�ma
	 * @param frictionType	Mit tesz a j�t�kos a mez�re ( 0: m�z, 1: olaj )
	 */
	public void ModifyFieldFriction(int playerNum, int frictionType) {
		// StorehouseController-ben a j�t�kosokat 1-t�l sz�mozzuk --> [ playerNum-1 ]
		if( gameRunning && players[ playerNum-1 ] != null )
			if( frictionType == 0)
				players[ playerNum-1 ].PlaceHoney();
			else
				players[ playerNum-1 ].PlaceOil();
	}
	
	
	/**
	 * Amenniyben egy l�da beragad vagy elnyel�dik, cs�kkenti a mozd�that� l�d�k sz�m�t. Ha �gy ez a sz�m egyn�l kisebb, v�ge
	 * a j�t�knak.
	 */
	public void ChestLost() {
		moveableChests--;
		System.out.println("Moveable: " + moveableChests + " �sszes: " + Chest.GetNumOfChests() );
		if(moveableChests < 1) 
			GameOver();
	}
	
	
	/**
	 * Amenniyben egy j�t�kos �sszenyom�dik vagy elnyeli egy lyuk, cs�kkenti a m�g akt�v j�t�kosok sz�m�t, illetve nullba
	 * �ll�tja a hozz� tartoz� nyel�t Ha �gy ez a sz�m kett�n�l kisebb, v�ge a j�t�knak.
	 * @param playerNum	A meghalt j�t�kos sorsz�ma
	 */
	public void PlayerLost(int playerNum) {
		// A Worker h�vja a saj�t sorsz�m�val - a j�t�kosokat 0-t�l sz�mozzuk --> [ playerNum ]
		
		// goals[playerNum] = null;
		players[ playerNum ] = null;
		
		playersAlive--;
		if(playersAlive < 2)
			GameOver();
	}
	
	
	/**
	 * Elind�tja a j�t�kot.
	 */
	public void StartGame() {
		gameRunning = true;
	}
	
	
	/**
	 * A j�t�kot meg�ll�tja. Amenniyben m�r csak egy j�t�kos maradt, kiki�ltja gy�ztesnek, az alapj�n, hogy a nyel� t�mb melyik
	 * eleme nem null, egy�bk�nt megkeresi a nem null nyel�k k�z�l a legnagyobb pontsz�m�t, �s az ahhoz tartoz� j�t�kost 
	 * ki�ltja ki gy�ztesnek, illetve pontegyez�s eset�n d�ntetlent hirdet.
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
				boolean draw = false; // --> a d�ntetlen lekezel�se!
				
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
	 * A p�ly�t amennyiben nem f�jlb�l olvassuk be, itt lehet inicializ�lni.
	 */
	public void SetUpStoreHouse() {
		// Building the map
	}
	
	
	/**
	 * A mez�k szomsz�dainak beregisztr�l�sa. A falaknak nem sz�ks�ges ismerni�k a szomsz�daikat, ez�rt mennek az i �s j
	 * v�ltoz�k (p�lyamagass�g-1)-ig, illetve 1-t�l (p�lyasz�less�g-1)-ig.
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
	 * Minden egyes mez�t megk�r, hogy ellen�rizze, nem beragad�s-e.
	 */
	public void MarkStickyFields() {
		for(int i = 1; i < map.length-1; i++)
			for(int j = 1; j < map[0].length-1; j++) {
				map[i][j].CheckSticky();
			}
	}
}
