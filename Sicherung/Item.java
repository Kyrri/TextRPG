import java.util.Random;

public class Item implements Comparable {
	private int 	number = 0;
	private String	Name;
	private int		Wert;
	private int		Gewicht;
	private int 	Verkaufspreis;
	private int 	Ankaufspreis;

	public Item() {
		Random rand = new Random();
		int[] value = {12,14,11,12,18,13,24,12};
		int[] weight = {3,2,2,1,5,3,4,2};
		String[] possibleNames = {"Leder", "Stoff", "Klaue", "Auge", "Kopf", "Schnur", "Pelz", "Zahn"};
		int pickVals = rand.nextInt(((7-0)+1)+0);// random index from possibleNames, values and weights lists
	
		this.Name = possibleNames[pickVals];
		this.Wert = value[pickVals];
		this.Gewicht = weight[pickVals];

	}	
	
	public Item(int ratio) {
		Random rand = new Random();
		int[] value = {12,14,11,12,18,13,24,12};
		int[] weight = {3,2,2,1,5,3,4,2};
		String[] possibleNames = {"Leder", "Stoff", "Klaue", "Auge", "Kopf", "Schnur", "Pelz", "Zahn"};
		int pickVals = rand.nextInt(((7-0)+1)+0);// random index from possibleNames, values and weights lists
	    
		this.Name = possibleNames[pickVals];
		this.Wert = value[pickVals];
		this.Wert = Wert + ratio;
		//System.out.println(ratio);
		//System.out.println(Verkaufspreis);
		this.Gewicht = weight[pickVals];

	}

	public Item(String name, int value, int weight) {
		this.Name = name;
		this.Wert = value;
		this.Gewicht = weight;
	}
	
	public void setWert(int curWert){
		System.out.println(curWert);
		this.Wert = curWert - 3;
	}
	
	public int getWert(){
		return Wert;
	}
	

	public boolean equals(Object obj) {
		if (!(obj instanceof Item) || obj == null) { return false; }
		if (obj == this) { return true; }
		Item compare = (Item) obj;
		if (compare.Name.toUpperCase() == this.Name.toUpperCase() && compare.Wert == this.Wert
				&& compare.Gewicht == this.Gewicht) {
			return true;
		} else {
			return false;
		}

	}
	
	

	public String toString() {
		return " Name: " + this.Name + ", Wert: " + this.Wert + ", Gewicht: " + this.Gewicht;
	}

	public int compareTo(Object o) throws IllegalArgumentException { // returns -1 if current item alphabetically (or
																		// value or weight if former the same) proceeds
																		// object
		if (!(o instanceof Item) || o == null) { // returns +1 if follows, 0 if all values are identical (if 0, increase
													// item count of this in inventory by #of object)
			throw new IllegalArgumentException("comparing object does not exist or is not an item");
		}

		Item compare = (Item) o;
		if (this.Name.toUpperCase().compareTo(compare.Name.toUpperCase()) != 0) {
			return this.Name.toUpperCase().compareTo(compare.Name.toUpperCase());
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
		return Name;
	}

	public int getVPreis() {
		return Wert;
	}
	
	public int getAPreis() {
		return Wert;
	}
}
