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
		int pickVals = rand.nextInt((((Level.possibleItems.length-1)-0)+1)+0);// random index from possibleNames, values and weights lists
	
		this.Name = ((Item)Level.possibleItems[pickVals]).getName();
		this.Wert = ((Item)Level.possibleItems[pickVals]).getWert();
		this.Gewicht =((Item)Level.possibleItems[pickVals]).getWeight();
	}		
	public Item(int ratio) {
		Random rand = new Random();
		int pickVals = rand.nextInt((((Level.possibleItems.length-1)-0)+1)+0);// random index from possibleNames, values and weights lists
	    
		this.Name = ((Item)Level.possibleItems[pickVals]).getName();
        this.Wert = ((Item)Level.possibleItems[pickVals]).getWert() + ratio;
        this.Gewicht =((Item)Level.possibleItems[pickVals]).getWeight();
	}
	public Item(String name, int ratio){
	    this.Name = name;
        int i =0;
        while(i<Level.possibleItems.length && !((Item)Level.possibleItems[i]).getName().equals(name)){
            i++;
        }
        this.Wert = ((Item)Level.possibleItems[i]).getWert() + ratio;
        this.Gewicht = ((Item)Level.possibleItems[i]).getWeight();
	}
	public Item(String name, int value, int weight) {
		this.Name = name;
		this.Wert = value;
		this.Gewicht = weight;
	}
	public Item(String name) {
        this.Name = name;
        int i =0;
        while(i<Level.possibleItems.length && !((Item)Level.possibleItems[i]).getName().equals(name)){
            i++;
        }
        this.Wert = ((Item)Level.possibleItems[i]).getWert();
        this.Gewicht = ((Item)Level.possibleItems[i]).getWeight();
    }
	
	public void setWert(int curWert){
		System.out.println(curWert);
		this.Wert = curWert - 3;
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
