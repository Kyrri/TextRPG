public class LinkedList implements List{
    private Node firstItem;
    private int length;
    
    public LinkedList(){
        this.firstItem  = new Node(null);
        length = 0;
    }
    public boolean isEmpty(){
        return this.length()<1;
    }
    public int length(){
        return this.length;
    }
    public boolean isInList(Object x){ //returns whether item is in list or not
        Node temp = new Node(x);
        Node current = this.firstItem;
        boolean inList = false;
        while(current.getNext() != null && inList==false){
            if(x instanceof Item){
                if(((Item)current.getNext().getItem()).compareTo(temp.getItem())==0){
                    return true;
                } 
            }
            else if(x instanceof Quest){
                if(((Quest)current.getNext().getItem()).compareTo(temp.getItem())==0){
                    return true;
                }
            }
            current=current.getNext();
        }
        return false;
    }
    public Object firstItem() throws IllegalStateException{ //returns first item
        if(this.firstItem.getItem()==null){
            throw new IllegalStateException("No first item present");
        }
        return firstItem.getItem();
    }
    public Object getItem(int i) throws IndexOutOfBoundsException{ //returns item at specific index
        if(i>=this.length()){
            throw new IndexOutOfBoundsException("indexed item doesn't exist");
        }
        else{
            Node current = this.firstItem;
            for(int q=0; q<=i; q++){
                current=current.getNext();
            }
            return current.getItem();
        }
    }
    public List insert(Object x){ //inserts item in order of sort (use to automatically sort items as they're added)
        Node temp = new Node(x);
        Node current = this.firstItem;
        if(x instanceof Item){
            while(current.getNext() != null && ((Item)current.getNext().getItem()).compareTo(temp.getItem())<0){
                current=current.getNext();
            }
        }
        else if(x instanceof Quest){
            while(current.getNext() != null && ((Quest)current.getNext().getItem()).compareTo(temp.getItem())<0){
                current=current.getNext();
            }
        }
        if(current.getNext() != null){
            temp.setNext(current.getNext());
            current.setNext(temp);
            this.length++;
        }
        else{
            current.setNext(temp);
            this.length++;
        }
        return this;
    }
    public List append(Object x){ //inserts item at end of list
        Node temp = new Node(x);
        Node current = this.firstItem;
        while(current.getNext() != null){
            current=current.getNext();
        }
        current.setNext(temp);
        this.length++;
        return this;
    }
    public List delete(Object x){ //deletes first instance of specific item
        Node temp = new Node(x);
        Node current = this.firstItem;
        while(current.getNext() != null || current.getNext().getItem().equals(temp.getItem())){
            current=current.getNext();
        }
        if(current.getNext().getItem().equals(temp.getItem())){
            Node connector = current.getNext().getNext();
            current.setNext(connector);
            this.length--;
        }
        return this;
    }
    public List delete(){ //deletes first item in list
        Node current = this.firstItem;
        
        if(current.getNext()!=null){
            if(current.getNext().getNext()!=null){
                current.setNext(current.getNext().getNext());
            }
            else{
                current.setNext(null);
            }
            this.length--;
        }
        return this;
    }
    private class Node {
        
        Node next;
        Object item;

        public Node(Object itemValue){
            this.next = null;
            this.item = itemValue;
        }
        public Node(Object itemValue, Node nextValue){
            this.next = nextValue;
            this.item = itemValue;
        }
        public Object getItem(){
            return item;
        }
        public void setItem(Object itemValue){
            this.item = itemValue;
        }
        public Node getNext(){
            return next;
        }
        public void setNext(Node nextValue){
            next = nextValue;
        }
    }
}
