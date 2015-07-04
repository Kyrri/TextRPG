import java.io.Serializable;

public class LinkedList<T extends Comparable & Serializable> implements
        List<T>, Serializable {
    private Node firstItem;
    private int length;

//    private static final String SUFFIX = "ser";

    public static final long serialVersionUID = 42;

    public LinkedList() {
        this.firstItem = new Node(null);
        length = 0;
    }

    public boolean isEmpty() {
        return this.length() < 1;
    }

    public int length() {
        return this.length;
    }

    public Node firstNode() {
        return this.firstItem;
    }

    // public boolean save(String name) {
    // if (!validatePath(name))
    // return false;
    // ObjectOutputStream oos;
    // try {
    // oos = new ObjectOutputStream(new FileOutputStream(name));
    // oos.writeObject(oos); //this?
    // oos.close();
    // return true;
    // } catch (IOException e) {
    // return false;
    // }
    //
    // }
    //
    // @SuppressWarnings("unchecked")
    // // Because this we have boolean validatePAth(String)
    // public static <T extends Comparable & Serializable> LinkedList<T> load(
    // String name) throws Exception {
    //
    // if (!validatePath(name))
    // throw new Exception("Wrong file format");
    //
    // ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));
    // Object loaded = ois.readObject();
    // ois.close();
    // return (LinkedList<T>) loaded;
    // }

//    private static boolean validatePath(String path) {
//        return path.endsWith(SUFFIX);
//    }

    public boolean isInList(T x) { // returns whether item is in list or not
        Node temp = new Node(x);
        Node current = this.firstItem;
        boolean inList = false;
        while (current.getNext() != null && inList == false) {
            // Quest specific - default returns true if item exists
            if (current.getNext().getItem() instanceof Quest) { // returns true
                                                                // if quest both
                                                                // exists and is
                                                                // complete
                if ((current.getNext().getItem()).getName().equals(
                        ((Quest) temp.getItem()).getPreReqs())) {
                    if (((Quest) current.getNext().getItem()).isComplete()) {
                        return true;
                    }
                }
            } else {
                if (current.getNext().getItem().compareTo(temp.getItem()) == 0) {
                    return true;
                }
            }
            current = current.getNext();
        }
        return false;
    }

    public int indexInList(String name) {// returns index of item in list
        Node current = this.firstItem;
        int i = 0;
        boolean inList = false;
        while (current.getNext() != null && !inList) {
            if (current.getNext().getItem().getName().toUpperCase()
                    .equals(name.toUpperCase())) {
                inList = true;
                break;
            }
            i++;
            current = current.getNext();
        }
        if (inList) {
            return i;
        } else {
            return -1;
        }
    }

    public T firstItem() throws IllegalStateException { // returns first item
        if (this.firstItem.getItem() == null) {
            throw new IllegalStateException("No first item present");
        }
        return firstItem.getItem();
    }

    public T getItem(int i) throws IndexOutOfBoundsException { // returns item
                                                               // at specific
                                                               // index
        if (i >= this.length()) {
            throw new IndexOutOfBoundsException("indexed item doesn't exist");
        } else {
            Node current = this.firstItem;
            for (int q = 0; q <= i; q++) {
                current = current.getNext();
            }
            return current.getItem();
        }
    }

    public int getQuantity(int i) throws IndexOutOfBoundsException { // returns
                                                                     // quantity
                                                                     // of
                                                                     // item
                                                                     // at
                                                                     // specific
                                                                     // index
        if (i >= this.length()) {
            throw new IndexOutOfBoundsException("indexed item doesn't exist");
        } else {
            Node current = this.firstItem;
            for (int q = 0; q <= i; q++) {
                current = current.getNext();
            }
            return current.getQuantity();
        }
    }

    public List<T> insert(T x) { // inserts item in order of sort (use to
                                 // automatically sort items as they're
                                 // added)
        Node temp = new Node(x);
        Node current = this.firstItem;
        while (current.getNext() != null
                && current.getNext().getItem().compareTo(temp.getItem()) < 0) {
            current = current.getNext();
        }
        if (current.getNext() != null
                && current.getNext().getItem().compareTo(temp.getItem()) == 0) { // are
                                                                                 // the
                                                                                 // same
            current.getNext().increaseQuantity();
            return this;
        }

        if (current.getNext() != null) {
            temp.setNext(current.getNext());
            current.setNext(temp);
            this.length++;
        } else {
            current.setNext(temp);
            this.length++;
        }
        return this;
    }

    public List<T> append(T x) { // inserts item at end of list
        Node temp = new Node(x);
        Node current = this.firstItem;
        while (current.getNext() != null) {
            if (current.getNext().getItem().compareTo(temp.getItem()) == 0) {
                current.getNext().increaseQuantity();
                return this;
            }
            current = current.getNext();
        }
        current.setNext(temp);
        this.length++;
        return this;
    }

    public List<T> delete(T x) { // deletes first instance of specific item
        Node temp = new Node(x);
        Node current = this.firstItem;
        while (current.getNext() != null) {
            if (current.getNext().getItem().equals(temp.getItem())) {
                break;
            } else {
                current = current.getNext();
            }
        }
        if (current.getNext().getItem().equals(temp.getItem())) {
            if (current.getNext().getQuantity() > 1) {
                current.getNext().decreaseQuantity();
            } else {
                Node connector = current.getNext().getNext();
                current.setNext(connector);
                this.length--;
            }
        }
        return this;
    }

    public List<T> delete() { // deletes first item in list
        Node current = this.firstItem;

        if (current.getNext() != null) {
            if (current.getNext().getQuantity() > 1) {
                current.getNext().decreaseQuantity();
            } else if (current.getNext().getNext() != null) {
                current.setNext(current.getNext().getNext());
                this.length--;
            } else {
                current.setNext(null);
                this.length--;
            }
        }
        return this;
    }

    public Object[] toArray(int i) { // returns LinkedList as an object array
        Node current = this.firstItem;
        Object[] array = new Object[i];
        int q = 0;
        while (current.getNext() != null && q < i) {
            array[q] = current.getNext().getItem();
            q++;
            current = current.getNext();
        }
        return array;
    }

    // Type-specific, depends on type of item being printed. Has default of
    // printing object and quantity
    public String printItem(int i) throws IndexOutOfBoundsException {
        String result = "";
        if (i >= this.length()) {
            throw new IndexOutOfBoundsException("indexed item doesn't exist");
        } else {
            Node current = this.firstItem;
            for (int q = 0; q <= i; q++) {
                current = current.getNext();
            }
            if (current.getItem() instanceof Quest) {
                result = "" + current.getItem();
            } else {
                result = current.getItem() + ", Menge: "
                        + current.getQuantity();
            }
        }
        return result;
    }

    // Quest Specific - Non Generic
    public int[] checkQuest(LinkedList<Item> inv) {
        Node current = this.firstItem;
        int[] i = { -1, -1 };
        boolean foundQuest = false;
        if (current.getNext().getItem() instanceof Quest) {
            while (current.getNext() != null && !foundQuest) {
                if (((Quest) current.getNext().getItem()).isComplete()) {
                    current = current.getNext();
                } else {
                    String itemName = ((Quest) current.getNext().getItem())
                            .getReqs();
                    int quantity = ((Quest) current.getNext().getItem())
                            .getQuantity();
                    int index = inv.indexInList(itemName);
                    int compareQuantity = inv.getQuantity(index);
                    if (index == -1) {
                        compareQuantity = 0;
                    }
                    System.out.println(itemName + ": Vorbedingung  -  "
                            + quantity + ", Vorhanden -  " + compareQuantity);
                    if (compareQuantity >= quantity) {
                        i[0] = index;
                        i[1] = quantity;
                        ((Quest) current.getNext().getItem()).setCompleted();
                        foundQuest = true;
                        return i;
                    } else {
                        current = current.getNext();
                    }
                }
            }
        }
        return i;
    }

    private class Node {
        Node next;
        T item;
        int quantity;

        public Node(T itemValue) {
            this.next = null;
            this.item = itemValue;
            this.quantity = 1;
        }

        public T getItem() {
            return item;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void increaseQuantity() {
            this.quantity++;
        }

        public void decreaseQuantity() {
            this.quantity--;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node nextValue) {
            next = nextValue;
        }
    }

}
