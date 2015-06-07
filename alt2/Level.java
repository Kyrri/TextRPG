import java.util.Scanner;

/** The type Level. */
public class Level {

	/** The constant ATKBONUS. */
	private static final int	ATKBONUS	= 10;
	/** The Map data. */
	private char[][]			mapData;
	/** The constant PLAIN. */
	public static final char	PLAIN		= '.';
	/** The constant PLAYER_CHAR. */
	public static final char	PLAYER_CHAR	= 'P';
	/** The constant FOUNTAIN. */
	public static final char	FOUNTAIN	= 'O';
	/** The constant SMITHY. */
	public static final char	SMITHY		= 'T';
	/** The constant BATTLE. */
	public static final char	BATTLE		= 'B';
	/** The constant GOAL. */
	public static final char	GOAL		= 'Z';
	/** The constant START. */
	public static final char	START		= 'S';
	/** The constant Merchant*/
	public static final char	MERCHANT	= 'H';
	/** The constant QUESTGIVER. */
    public static final char    QUESTGIVER  = 'Q';
	/** The Player x coordinate. */
	private int					playerX;
	/** The Player y coordinate. */
	private int					playerY;

	private boolean				inventoryShown;
	private final int           TOTALQUESTS;
    private int                 completedQuests;
    private boolean             canExit;

	/** Instantiates a new Level.
	 *
	 * @param mapData the map data */
	public Level(char[][] mapData) {
		if (mapData.length < 3 || mapData[0].length < 3) { throw new IllegalArgumentException("Invalid Map Data"); }
		this.mapData = mapData;
		if (!findStart()) { throw new IllegalArgumentException("Invalid Map Data: No starting position"); }
		inventoryShown = false;
		TOTALQUESTS = 1; //read in quests, base number on that
        completedQuests = 0;
        canExit = false;
	}

	/** Find start.
	 *
	 * @return true, wenn die Startposition gefunden wuerde */
	private boolean findStart() {
		for (int y = 0; y < mapData.length; y++) {
			for (int x = 0; x < mapData[0].length; x++) {
				if (mapData[y][x] == START) {
					playerX = x;
					playerY = y;
					return true;
				}
			}
		}
		return false;
	}

	/** To string.
	 *
	 * @return the string */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < mapData.length; ++y) {
			for (int x = 0; x < mapData[0].length; ++x) {
				if (y == playerY && x == playerX) {
					sb.append(PLAYER_CHAR);
				} else {
					sb.append(mapData[y][x]);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/** Can move.
	 *
	 * @param c the direction
	 * @return true, wenn die Richtung möglich ist */
	public boolean canMove(char c) {
		switch (c) {
			case 'n':
				return canMoveUp();
			case 's':
				return canMoveDown();
			case 'o':
				return canMoveRight();
			case 'w':
				return canMoveLeft();
			default:
				return false;
		}
	}
	
	public boolean showInventory(char c) {
		return c == 'i';
	}
	public boolean showQuests(char c){
        return c == 'l';
    }

	/** Move void.
	 *
	 * @param c the direction */
	public void move(char c) {
		switch (c) {
			case 'n':
				moveUp();
			break;
			case 's':
				moveDown();
			break;
			case 'o':
				moveRight();
			break;
			case 'w':
				moveLeft();
			break;
		}
	}

	/** Is walkable position.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true, wenn das Feld x,y begehbar ist */
	public boolean isWalkablePosition(int x, int y) {
		return (y >= 0)
				&& (x >= 0)
				&& (y < mapData.length)
				&& (x < mapData[0].length)
				&& (mapData[y][x] == PLAIN || mapData[y][x] == FOUNTAIN || mapData[y][x] == SMITHY
						|| mapData[y][x] == BATTLE || mapData[y][x] == GOAL || mapData[y][x] == START || mapData[y][x] == MERCHANT 
						|| mapData[y][x] == QUESTGIVER);
	}

	/** Can move up.
	 *
	 * @return true, wenn mögliche Bewegung */
	public boolean canMoveUp() {
		return isWalkablePosition(playerX, playerY - 1);
	}

	/** Can move down.
	 *
	 * @return true, wenn mögliche Bewegung */
	public boolean canMoveDown() {
		return isWalkablePosition(playerX, playerY + 1);
	}

	/** Can move left.
	 *
	 * @return true, wenn mögliche Bewegung */
	public boolean canMoveLeft() {
		return isWalkablePosition(playerX - 1, playerY);
	}

	/** Can move right.
	 *
	 * @return true, wenn mögliche Bewegung */
	public boolean canMoveRight() {
		return isWalkablePosition(playerX + 1, playerY);
	}

	/** Move up. */
	public void moveUp() {
		if (canMoveUp()) {
			playerY--;
		}
	}

	/** Move down. */
	public void moveDown() {
		if (canMoveDown()) {
			playerY++;
		}
	}

	/** Move left. */
	public void moveLeft() {
		if (canMoveLeft()) {
			playerX--;
		}
	}

	/** Move right. */
	public void moveRight() {
		if (canMoveRight()) {
			playerX++;
		}
	}

	/** Show prompt. */
	public void showPrompt() {
		System.out.println("------------------------------");
		if (canMoveUp()) {
			System.out.println("n -> Norden");
		}
		if (canMoveDown()) {
			System.out.println("s -> Sueden");
		}
		if (canMoveRight()) {
			System.out.println("o -> Osten");
		}
		if (canMoveLeft()) {
			System.out.println("w -> Westen");
		}
		System.out.println("------------------------------");
		System.out.println("i -> Zeige Inventar");
		System.out.println("l -> Questlog");
		System.out.print("Richtung? ");
	}

	/** Gets field.
	 *
	 * @return the field */
	private char getField() {
		return mapData[playerY][playerX];
	}

	/** Clear field. */
	private void clearField() {
		char field = getField();
		if (field == SMITHY || field == FOUNTAIN || field == BATTLE) {
			mapData[playerY][playerX] = PLAIN;
		}
	}

	/** Handle current field event.
	 *
	 * @param p the player */
	public void handleCurrentFieldEvent(Player p) {
		char field = getField();
		switch (field) {
			case Level.SMITHY:
				p.setAtk(p.getAtk() + ATKBONUS);
				System.out.printf("Die ATK des Spielers wurde um %d erhöht.%n", ATKBONUS);
			break;
			case Level.QUESTGIVER: 
	               if(completedQuests<TOTALQUESTS){
	                   this.completedQuests+= p.checkQuest(); 
	               }
	               if(completedQuests>=TOTALQUESTS){
	                   System.out.println("You have Completed all the quests - You May Now Exit the Level at Z");//should be in German
	                   canExit = true;
	               }
	               else{
	                  if(p.getActiveQuests()>=TOTALQUESTS){
	                      System.out.println("You have all avaliable quests - complete them to continue");
	                  }
	                  else{           
	                      Quest q = new Quest("Luxury Pelts", "", "Pelz", 1); //add a single quest from file that hasn't been added, keep track of added quests so you don't readd
	                      if(q.getPreReqs().equals("") || q.getPreReqs().equals(null)){ //no Prereq
	                          p.addToQuests(q);
	                          System.out.println("Neue Quest! "+ q.getName());
	                          //increase quest tracking index?
	                      }
	                      else if(p.isQuestComplete(q)){ //check if Prereq is completed, if done
	                          p.addToQuests(q);
	                          System.out.println("Neue Quest! "+ q.getName());
	                          //increase quest tracking index?
	                      }
	                      else{
	                          System.out.println("You must finish '" + q.getPreReqs() + "' before getting a new quest.");
	                          //do not increase quest tracking index?
	                      }
	                       
	                  }
	               }        
	            break;
			case Level.FOUNTAIN:
				p.setHp(p.getMaxHp());
				System.out.println("Spieler wurde vollständig geheilt!");
			break;
			case Level.BATTLE:
				startBattle(p);
			break;
			case Level.MERCHANT:
				startTrade(p);
			break;
			case Level.GOAL:
			    if(canExit){
                    System.out.println("Herzlichen Glückwunsch! Sie haben gewonnen!");
                    System.exit(0); 
                }
                else{
                    System.out.println("You must complete all the quests to Exit! Look for Q"); //should be in German
                }
			break;
		}
		clearField();
	}

	/** Random monster.
	 *
	 * @return the monster */
	private static Monster randomMonster() {
		Monster[] monsterFarm = { new Monster(), new ResistantMonster(), new WaitingMonster() };

		double bucketSize = 1.0 / monsterFarm.length;
		double bucket = Math.random() / bucketSize;
		int selectedMonster = (int) Math.floor(bucket);
		return monsterFarm[selectedMonster];
	}
	
	private static Merchant randomMerchant() {
		Merchant wto;
		if(Math.random() <= 0.50){
			wto = new Merchant("TraderJoe", 100, 3);	
		} else {
			wto = new Merchant("TraderJeb", 90, 6);
		}
		return wto;
	}
	
	/**
	 * Start trade
	 */
	
	public void startTrade(Player p){
		int action;
		
		Merchant h = randomMerchant();
		
		showInventory(h,p);
		action = askAction();
		
		if(action == 1){
		
		    buyItem(p, h);
		}else if(action == 2){
		
		    sellItem(p, h);
		}
	}

	private int askAction() {
		String action;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welche Aktion?");
		System.out.println("1 für Kaufen");
		System.out.println("2 für Verkaufen");
		action = sc.nextLine();
		System.out.println(action);
		if(action.equals("1")){
			return 1;
			

		}else if (action.equals("2")){
			return 2;
		
		}else{
			System.out.println("Ungültige Eingabe");
			askAction();
			return 0;
		}
		
	}
	
	private void buyItem(Player p, Merchant h){
		
		inventoryShown = true;
		LinkedList inv = h.getInventory();
		
		System.out.println("Angebot von "+h.getName()+" :");
		System.out.println("#########################");
		for (int i = 0; i < inv.length(); i++) {
			System.out.println(i + ".) " + inv.printItem(i));
		}
		
		int article = askArticleNumber(inv.length());
		h.getItem(article);
		System.out.println(h.getVerkaufspreis(article));
		if(h.getVerkaufspreis(article)<=p.getGold()){
			
			Item iv = new Item(h.getItem(article).getName());
			
			
			p.deductGold(h.getVerkaufspreis(article));
			h.addMoreGold(h.getVerkaufspreis(article));
			h.deleteItem(article);
			p.addToInventory(iv);
	
			
			System.out.println("Kauf erfolgreich");
			System.out.println("Geldreserve: "+p.getGold());
			System.out.println("Dein Inventar");
			System.out.println("#########################");
			inv = p.getInventory();
			
			for (int i = 0; i < inv.length(); i++) {
				System.out.println(i + ".) " + inv.printItem(i));
			}
		} else {
			System.out.println("Nicht genung Finanzreservern");
		}
		
	}
	
	private void sellItem(Player p, Merchant h){
		
		inventoryShown = true;
		LinkedList inv = p.getInventory();
		
		System.out.println("Dein Inventar :");
		System.out.println("#########################");
		for (int i = 0; i < inv.length(); i++) {
			System.out.println(i + ".) " + inv.printItem(i));
		}
	
		int article = askArticleNumber(inv.length());
		p.getItem(article);
		if(p.getAnkaufspreis(article)<=h.getGold()){
			//System.out.println("Hier1");
			Item iv = new Item(h.getItem(article).getName(), h.getRatio());
			//System.out.println("Hier 2");
			
			h.addToInventory(iv);
			p.deleteItem(article);
			h.deductGold(p.getAnkaufspreis(article));
			p.addMoreGold(p.getVerkaufspreis(article));
			System.out.println("Verkauf erfolgreich");
			System.out.println("Geldreserve: "+p.getGold());
			System.out.println("Dein Inventar");
			System.out.println("#########################");
			inv = p.getInventory();
			
			for (int i = 0; i < inv.length(); i++) {
				System.out.println(i + ".) " + inv.printItem(i));
			}
		} else {
			System.out.println("Händler hat nicht genügend Geld");
		}
		
	}
	
	private int askArticleNumber(int i){
		System.out.println("Bitte Artikelnummer eingeben");
		int article;
	    Scanner sc = new Scanner(System.in);
	    String artNumber = sc.nextLine();
	    // TODO String überprüfen
	    for (int l=0; l < i; l++)
	    	if (l == Integer.parseInt(artNumber)){
	    		article = l;
	    		
	    		return article;
	    	}
	    System.out.println("Ungültige Eingabe");
	    askArticleNumber(i);
	    return 0;
	}
	
	/** Start battle.
	 *
	 * @param p the p */
	public void startBattle(Player p) {
        Character m = randomMonster();

        Scanner sc = new Scanner(System.in);
        
        System.out.println("                 Kampf Start                    ");
        System.out.print(p);
        System.out.print(m);

        while(true) {
            System.out.println("------------------------------------------------");
            System.out.println("Mögliche Aktionen:");
            System.out.println("1 -> Angriff");
            System.out.printf("2 -> Heals (%d verbleibend)%n", p.getRemainingItemUses());
            System.out.printf("3 -> Harter Schlag (%d AP, %d%% Selbstschaden)%n", Player.HARD_HIT_COST, Player.HARD_HIT_SELF_DAMAGE_PERCENT);
            System.out.printf("4 -> Feuerball (%d AP)%n", Player.FIREBALL_COST);
            System.out.printf("5 -> ATK auswürfeln (%d AP)%n", Player.REROLL_COST);
            System.out.println("6 -> Zeige Inventar");
            System.out.println("7 -> Wähle Item aus Inventar.");
            System.out.println("Welche Aktion?: ");
            System.out.println("------------------------------------------------");
            String aktion = sc.nextLine();
            int playerDamage;
            switch (aktion) {
                case "1":
                    playerDamage = p.attack(m);
                    if (playerDamage == -1) {
                        System.out.println("Spieler verfehlt!");
                    } else {
                        System.out.printf("Spieler trifft und macht %d Schaden!%n", playerDamage);
                    }
                    break;
                case "2":
                    if (p.heal()) {
                        System.out.println("Spieler heilt sich!");
                    } else {
                        System.out.println("Nicht genügend Heiltränke!");
                    }
                    break;
                case "3":
                    playerDamage = p.hardHit(m);
                    if (playerDamage != -1) {
                        System.out.println("Spieler schlägt hart zu!");
                        System.out.printf("Spieler verursacht %d Schaden!%n", playerDamage);
                        System.out.printf("Spieler verursacht %d Selbstschaden!%n", (int) (Player.HARD_HIT_SELF_DAMAGE_PERCENT / 100.0 * playerDamage));
                    } else {
                        System.out.println("Nicht genügend AP!");
                    }
                    break;
                case "4":
                    playerDamage = p.fireball(m);
                    if (playerDamage != -1) {
                        System.out.println("Spieler schießt einen Feuerball!");
                        System.out.printf("Spieler verursacht %d Schaden!%n", playerDamage);
                    } else {
                        System.out.println("Nicht genügend AP!");
                    }
                    break;
                case "5":
                    if (p.reroll()) {
                        System.out.println("ATK neu ausgewürfelt!");
                        System.out.print("Neue Statuswerte: ");
                        System.out.print(p);
                    } else {
                        System.out.println("Nicht genügend AP!");
                    }
                    break;
                case "6":
                	showInventory(p);
                	break;
                case "7":
                	if (inventoryShown) {
                		String s = sc.nextLine();
                		try {
                			System.out.println("Wähle ein Item aus (0 - " + (p.getInventory().length() - 1) + "):");
                			chooseInventory(p, Integer.parseInt(s));
                		} catch (Exception e) {
                			System.out.println("Das ist was falsch gelaufen ... Eingabe ignoriert.");
                		}
                	} else {
                		System.out.println("Kann kein Item auswählen.");
                	}
                	inventoryShown = false;
                	break;
                default:
                    System.out.println("Fehlerhafte Aktion!");
                    continue;
            }

            if (p.isDefeated()) {
                System.out.println("Game Over!");
                System.exit(0);
            } else if (m.isDefeated()) {
                System.out.println("Spieler gewinnt!");
                p.addMoreGold(m.getGold());
                LinkedList mInv = m.getInventory();
                for (int i = 0; i < mInv.length(); i++) {
                	p.addToInventory((Item)mInv.getItem(i));
                }
                break;
            }

            System.out.print(p);
            System.out.print(m);

            System.out.println("Monster greift an!");
            int monsterDamage = m.attack(p);
            if (monsterDamage == -1) {
                System.out.println("Monster verfehlt!");
            } else if (monsterDamage == -2) {
                System.out.println("Monster tut nichts.");
            } else {
                System.out.printf("Monster trifft und macht %d Schaden!%n", monsterDamage);
            }

            if (p.isDefeated()) {
                System.out.println("Game Over!");
                System.exit(0);
            }

            p.regenerateAp();

            System.out.print(p);
            System.out.print(m);
        }
    }

	public void showInventory(Player p) {
		inventoryShown = true;
		LinkedList inv = p.getInventory();
		System.out.println("Dein Inventar umfasst: ");
		for (int i = 0; i < inv.length(); i++) {
			System.out.println(i + ".) " + inv.printItem(i) );
		}
	}
	
	//Inventory
	public void showInventory(Merchant h, Player p) {
		inventoryShown = true;
		LinkedList inv = h.getInventory();
		//System.out.println("Goldreserve von "+h.getGold()+" :");
		//System.out.println("Ratio "+h.getRatio()+" :");
		System.out.println("Angebot von "+h.getName()+" :");
		System.out.println("#########################");
		for (int i = 0; i < inv.length(); i++) {
			System.out.println(i + ".) " + inv.printItem(i));
		}
		
		System.out.println("#########################");
		System.out.println("Ankaufspreisliste :");
		inv = p.getInventory();
		for (int i = 0; i < inv.length(); i++) {
			System.out.println(i + ".) " + inv.printItem(i));
		}
		
		
	}
	
	private void chooseInventory(Player p, int index) {
		if (index >= p.getInventory().length()) {
			System.out.println("Sorry, Item existiert nicht.");
			return;
		}
		p.setCurrentItem((Item)p.getInventory().getItem(index));
	}
	//Quests
    public void showQuests(Player p){
        LinkedList quests = p.getQuests();
        System.out.println("Dein Quests umfasst: ");
        for (int i = 0; i < quests.length(); i++) {
            System.out.println(i + ".) " +quests.printItem(i));
        }
    }

}
