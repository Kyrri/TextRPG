import java.util.Random;

public class Item implements Comparable {
	private int 	number = 0;
	private String	Namen;
	private int		Wert;
	private int		Gewicht;
	private int 	Verkaufspreis;
	private int 	Ankaufspreis;

	/**
	 * Konstruktoren 
	 */
	
	public Item() {
		Random rand = new Random();
		int pickVals = rand.nextInt(Level.possibleItems.length);// random index from possibleNames, values and weights lists
	
		this.Namen = (Level.possibleItems[pickVals]).getName();
		this.Wert = (Level.possibleItems[pickVals]).getWert();
		this.Gewicht =(Level.possibleItems[pickVals]).getWeight();
	}
	/**
	 * 
	 * @param ratio 
	 * Differzenz zwischen Ankaufspreis und Verkaufspreis für den Spieler
	 * 
	 */
	public Item(int ratio) {
		Random rand = new Random();
		int pickVals = rand.nextInt(Level.possibleItems.length);// random index from possibleNames, values and weights lists
	    
		this.Namen = (Level.possibleItems[pickVals]).getName();
        this.Wert = (Level.possibleItems[pickVals]).getWert() + ratio;
        this.Gewicht =(Level.possibleItems[pickVals]).getWeight();
	}
	/**
	 * 
	 * @param name des Händler TraderJoe oder TraderJeb
	 * @param ratio Gewinnmarge des Händlers
	 */
	public Item(String name, int ratio){
	    this.Namen = name;
        int i =0;
        while(i<Level.possibleItems.length && !(Level.possibleItems[i]).getName().equals(name)){
            i++;
        }
        this.Wert = (Level.possibleItems[i]).getWert() + ratio;
        this.Gewicht = (Level.possibleItems[i]).getWeight();
	}
	
	public Item(String name, int value, int weight) {
		this.Namen = name;
		this.Wert = value;
		this.Gewicht = weight;
	}
	public Item(String name) {
        this.Namen = name;
        int i =0;
        while(i<Level.possibleItems.length && !(Level.possibleItems[i]).getName().equals(name)){
            i++;
        }
        this.Wert = (Level.possibleItems[i]).getWert();
        this.Gewicht = (Level.possibleItems[i]).getWeight();
    }
	/**
	 * 
	 * @param curWert
	 * Preisverfall des Gegenstandes nachdem der Spieler gekauft hat
	 */
	public void setWert(int curWert){
		System.out.println(curWert);
		this.Wert = curWert - 3;
	}


	public boolean equals(Object obj) {
		if (!(obj instanceof Item) || obj == null) { return false; }
		if (obj == this) { return true; }
		Item compare = (Item) obj;
		if (compare.Namen.toUpperCase() == this.Namen.toUpperCase() && compare.Wert == this.Wert
				&& compare.Gewicht == this.Gewicht) {
			return true;
		} else {
			return false;
		}

	}
	
	

	public String toString() {
		return " Name: " + this.Namen + ", Wert: " + this.Wert + ", Gewicht: " + this.Gewicht;
	}

	public int compareTo(Object o) throws IllegalArgumentException { // returns -1 if current item alphabetically (or
																		// value or weight if former the same) proceeds
																		// object
		if (!(o instanceof Item) || o == null) { // returns +1 if follows, 0 if all values are identical (if 0, increase
													// item count of this in inventory by #of object)
			throw new IllegalArgumentException("comparing object does not exist or is not an item");
		}

		Item compare = (Item) o;
		if (this.Namen.toUpperCase().compareTo(compare.Namen.toUpperCase()) != 0) {
			return this.Namen.toUpperCase().compareTo(compare.Namen.toUpperCase());
		} else if (this.Wert != compare.Wert) {
			if (this.Wert < compare.Wert) {
				return -1;
			} else {
				return 1;
			}
		} else if (this.Gewicht != compare.Gewicht) {
			if (this.Gewicht < compare.Gewicht) {
				return -1;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	public String getName() {
		return Namen;
	}
	public int getWert(){
        return Wert;
    }
	public int getVPreis() {
		return Wert;
	}
	public int getAPreis() {
		return Wert;
	}
	public int getWeight(){
        return this.Gewicht;
    }   
	
}
