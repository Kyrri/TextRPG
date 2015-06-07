import java.util.Random;

public class Item implements Comparable {

	private String	Namen;
	private int		Verkaufswert;
	private int		Gewicht;

	public Item() {
		Random rand = new Random();
		int[] value = {5,4,1,2,8,3,10,2};
		int[] weight = {3,2,2,1,5,3,4,2};
		String[] possibleNames = {"Leder", "Stoff", "Klaue", "Auge", "Kopf", "Schnur", "Pelz", "Zahn"};
		int pickVals = rand.nextInt(((7-0)+1)+0);// random index from possibleNames, values and weights lists
	
		this.Namen = possibleNames[pickVals];
		this.Verkaufswert = value[pickVals];
		this.Gewicht = weight[pickVals];

	}

	public Item(String name, int value, int weight) {
		this.Namen = name;
		this.Verkaufswert = value;
		this.Gewicht = weight;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Item) || obj == null) { return false; }
		if (obj == this) { return true; }
		Item compare = (Item) obj;
		if (compare.Namen.toUpperCase() == this.Namen.toUpperCase() && compare.Verkaufswert == this.Verkaufswert
				&& compare.Gewicht == this.Gewicht) {
			return true;
		} else {
			return false;
		}

	}

	public String toString() {
		return "Namen: " + this.Namen + ", Verkaufswert: " + this.Verkaufswert + ", Gewicht: " + this.Gewicht;
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
		} else if (this.Verkaufswert != compare.Verkaufswert) {
			if (this.Verkaufswert < compare.Verkaufswert) {
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
		return this.Namen;
	}
	public int getValue(){
	   return this.Verkaufswert;
	}
	public int getWeight(){
	    return this.Gewicht;
	}
}
