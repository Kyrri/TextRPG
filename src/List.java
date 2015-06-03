public interface List {

	/** Ueberprueft ob die Liste leer ist
	 *
	 * @return true, Liste ist leer */
	boolean isEmpty();

	/** Gibt die Laenge der Liste zur�ck
	 *
	 * @return die Laenge */
	int length();

	/** Prueft ob ein Item in der Liste ist
	 *
	 * @param x das Item
	 * @return true, x ist in der Liste enthalten */
	boolean isInList(Item x);

	/** Gibt das erste Item der Liste zurueck
	 *
	 * @return das erste Item
	 * @throws IllegalStateException wenn die Liste leer ist */
	Item firstItem() throws IllegalStateException;

	/** Gibt das i-te Item der Liste zurueck
	 *
	 * @param i der Index
	 * @return das i-te Item
	 * @throws IndexOutOfBoundsException wenn i < 0 oder i >= length() */
	Item getItem(int i) throws IndexOutOfBoundsException;

	/** Fuegt ein Element sortiert in die Liste ein
	 *
	 * @param x das Item
	 * @return die geanderte Liste */
	List insert(Item x);

	/** Fuegt ein Element an das Ende der Liste ein
	 *
	 * @param x das Item
	 * @return die geanderte Liste */
	List append(Item x);

	/** Loescht das erste vorkommen des Items x
	 *
	 * @param x das Item
	 * @return die geanderte Liste */
	List delete(Item x);

	/** Loescht das erste Element der Liste
	 *
	 * @return die geanderte Liste */
	List delete();
}