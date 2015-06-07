public class Quest implements Comparable{

    private String   Namen;
    private boolean  Abgeschlossen;
    private String   Vorquest;
    private String   Questgegenstand;
    private int      Anzahl;

    public Quest(String name, String prequest, String item, int quantity) {
        this.Namen = name;
        this.Abgeschlossen = false;
        this.Vorquest = prequest;
        this.Questgegenstand = item;
        this.Anzahl = quantity;
    }
	public boolean isComplete(){
        return this.Abgeschlossen;
    }
    public void setCompleted(){
        this.Abgeschlossen = true;
    }
    public int getQuantity(){
        return this.Anzahl;
    }


    public String toString() {
        return "Namen: " + this.Namen + ", Abgeschlossen: " + (this.Abgeschlossen?"Erf�llt":"Nicht Erf�llt") + ", Questgegenstand: " + this.Questgegenstand + ", Anzahl: " + this.Anzahl;
    }

    public String getName() {
        return Namen;
    }
    public String getReqs(){
        return Questgegenstand;
    }
    public String getPreReqs(){
        return Vorquest;
    }
    public boolean equals(Object obj) {
        if (!(obj instanceof Quest) || obj == null) { return false; }
        if (obj == this) { return true; }
        Quest compare = (Quest) obj;
        if (compare.Namen.toUpperCase() == this.Namen.toUpperCase() && compare.Abgeschlossen == this.Abgeschlossen
                && compare.Vorquest == this.Vorquest && compare.Questgegenstand == this.Questgegenstand && compare.Anzahl == this.Anzahl) {
            return true;
        } else {
            return false;
        }

    }

    public int compareTo(Object o) throws IllegalArgumentException { // returns -1 if current item alphabetically (or
                                                                        // value or weight if former the same) proceeds
                                                                        // object
        if (!(o instanceof Quest) || o == null) { // returns +1 if follows, 0 if all values are identical (if 0, increase
                                                    // item count of this in inventory by #of object)
            throw new IllegalArgumentException("comparing object does not exist or is not an item");
        }

        Quest compare = (Quest) o;
        if (this.Namen.toUpperCase().compareTo(compare.Namen.toUpperCase()) != 0) {
            return this.Namen.toUpperCase().compareTo(compare.Namen.toUpperCase());
        } else {
            return 0;
        }
    }

}
