
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/** The type Crawler. */
public class Crawler {
	
	
	
	/** The entry point of application.
	 *
	 * @param args the input arguments 
	 * @throws IOException */
	public static void main(String[] args) throws IOException {
		MazeGenerator mg = new RecursiveBacktracker();
		// Level m = new Level(mg.generate(31, 71));
		Level m = new Level(mg.generate(10, 20));
		Scanner sc = new Scanner(System.in);
		Player p = new Player();
		System.out.println("Datei laden oder Standardwerte benutzen");
	
		int action = abfrage();
		
		if(action == 1){
			int lineNumber = 0;
			BufferedReader br = Files.newBufferedReader(Paths.get("item.csv")); 
			String line = null;
			String cvsSplitBy = ",";
			while ((line = br.readLine()) != null){
				
				lineNumber++;
				if (lineNumber > 1) {
					//line = line.replace(',', '.');
					System.out.println(line);
					String[] zeile = line.split(cvsSplitBy);
					
					String name = zeile[0]; 
					
					int value = (int) Double.parseDouble(zeile[1]);
					int weight = (int) Double.parseDouble(zeile[2]);
					Item item = new Item(name, value, weight);
					
					
				}
			}
			lineNumber = 0;
			br = Files.newBufferedReader(Paths.get("quest.csv")); 
			line = null;
			
			while ((line = br.readLine()) != null){
				
				lineNumber++;
				if (lineNumber > 1) {
					//line = line.replace(',', '.');
					System.out.println(line);
					String[] zeile = line.split(cvsSplitBy);
					String name = zeile[0];
					String prequest = zeile[1];
					String item = zeile[2];
					int quatity = (int) Double.parseDouble(zeile[3]);
					
					Quest quest = new Quest(name, prequest, item, quatity);
				}
			}
			
		}
		
		while (!p.isDefeated()) {
			System.out.println(m);
			m.showPrompt();
			String input = sc.nextLine();
			if (input.isEmpty()) {
				System.out.println("Leere Eingabe, bitte eine Richtung eingeben");
			} else {
				char direction = input.charAt(0);
				if (m.showInventory(direction)) {
					m.showInventory(p);
				}else if (m.showQuests(direction)) {
                    m.showQuests(p);
                }else if (!m.canMove(direction)) {
					System.out.println("Ungültige Richtung");
				} else {
					m.move(direction);
					m.handleCurrentFieldEvent(p);
				}
			}
		}
	}
	
	private static int abfrage(){
		System.out.println("1 für Datei laden");
		System.out.println("2 für Standardwerte");
		Scanner sc = new Scanner(System.in);
		String eingabe = sc.nextLine();
		if (eingabe.equals("1")){
			return 1;
		} else if (eingabe.equals("2")){
			return 2;
		} else {
			System.out.println("Ungültige Eingabe");
			abfrage();
			return 0;
		}
		
	}
}
