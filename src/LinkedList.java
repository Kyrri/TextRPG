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
    public Node firstNode(){
        return this.firstItem;
    }
    public boolean isInList(Object x){ //returns whether item is in list or not
        Node temp = new Node(x);
        Node current = this.firstItem;
        boolean inList = false;
        while(current.getNext() != null && inList==false){
            if(x instanceof Item){
                if(((Item)current.getNext().getItem()).compareTo((Item)temp.getItem())==0){
                    return true;
                } 
            }
            else if(x instanceof Quest){ //returns true if quest both exists and is complete
                if((((Quest)current.getNext().getItem()).getName()).equals(((Quest)temp.getItem()).getPreReqs())){
                    if(((Quest)current.getNext().getItem()).isComplete()){
                        return true;
                    }
                }
            }
            current=current.getNext();
        }
        return false;
    }
    
    public int indexInList(String name){//returns index of item in list
        Node current = this.firstItem;
        int i=0;
        boolean inList = false;
        while(current.getNext() != null && !inList && current.getNext().getItem() instanceof Item){
            if(((Item)current.getNext().getItem()).getName().toUpperCase().equals(name.toUpperCase())){
                inList=true;
                break;
            }
            i++;
            current=current.getNext();
        }
        if(inList){
          return i;  
        }
        else{
            return -1;
        }
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
    public int getQuantity(int i) throws IndexOutOfBoundsException{ //returns quantity of item at specific index
        if(i>=this.length()){
            throw new IndexOutOfBoundsException("indexed item doesn't exist");
        }
        else{
            Node current = this.firstItem;
            for(int q=0; q<=i; q++){
                current=current.getNext();
            }
            return current.getQuantity();
        }
    }
    public String printItem(int i) throws IndexOutOfBoundsException{
        String result = "";
        if(i>=this.length()){
            throw new IndexOutOfBoundsException("indexed item doesn't exist");
        }
        else{
            Node current = this.firstItem;
            for(int q=0; q<=i; q++){
                current = current.getNext();
            }
            if(current.getItem() instanceof Item){
                result = current.getItem()+", Menge: "+current.getQuantity();
            }
            else if(current.getItem() instanceof Quest){
                result = ""+current.getItem();
            }
        }
        return result;
    }
    public List insert(Object x){ //inserts item in order of sort (use to automatically sort items as they're added)
        Node temp = new Node(x);
        Node current = this.firstItem;
        if(x instanceof Item){
            while(current.getNext() != null && ((Item)current.getNext().getItem()).compareTo(temp.getItem())<0){
                current=current.getNext();
            }
            if(current.getNext() != null && ((Item)current.getNext().getItem()).compareTo(temp.getItem())==0){ //are the same
                current.getNext().increaseQuantity();
                return this;
            }
        }
        else if(x instanceof Quest){
            while(current.getNext() != null && ((Quest)current.getNext().getItem()).compareTo(temp.getItem())<0){
                current=current.getNext();
            }
            if(current.getNext() != null && ((Quest)current.getNext().getItem()).compareTo(temp.getItem())==0){//are the same
                return this; //Can't have multiple of same quest. Assume error, doesn't re-add existing quest
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
            if(x instanceof Item && ((Item)current.getNext().getItem()).compareTo(temp.getItem())==0){
                current.getNext().increaseQuantity();
                return this;
            }
            if(x instanceof Quest && ((Quest)current.getNext().getItem()).compareTo(temp.getItem())==0){
                return this; //Can't have multiple of same quest. Assume error, doesn't re-add existing quest
            }
            current=current.getNext();
        }
        current.setNext(temp);
        this.length++;
        return this;
    }
    public List delete(Object x){ //deletes first instance of specific item
        Node temp = new Node(x);
        Node current = this.firstItem;
        if(x instanceof Item){
            while(current.getNext() != null){
                if(current.getNext().getItem().equals(temp.getItem())){
                    break;
                }
                else{
                    current=current.getNext();
                }
            }
            if(current.getNext().getItem().equals(temp.getItem())){
                if(current.getNext().getQuantity()>1){
                    current.getNext().decreaseQuantity();
                }
                else{
                    Node connector = current.getNext().getNext();
                    current.setNext(connector);
                    this.length--;
                }
            }
        }
        return this;
    }
    public List delete(){ //deletes first item in list
        Node current = this.firstItem;
        
        if(current.getNext()!=null){
            if(current.getNext().getQuantity()>1){
                current.getNext().decreaseQuantity();
            }
            else if(current.getNext().getNext()!=null){
                current.setNext(current.getNext().getNext());
                this.length--;
            }
            else{
                current.setNext(null);
                this.length--;
            }
        }
        return this;
    }
    public Object[] toArray(int i){
        Node current = this.firstItem;
        Object[] array = new Object[i];
        int q = 0;
        while(current.getNext() != null && q<i){
            array[q] = current.getNext().getItem();
            q++;
            current = current.getNext();
        }
        return array;
    }
    
    //Quest Specific
    
    public int[] checkQuest(LinkedList inv){
        Node current = this.firstItem;
        int[] i = {-1, -1};
        boolean foundQuest = false;
 
        while(current.getNext()!=null && !foundQuest){
            if(((Quest)current.getNext().getItem()).isComplete()){
                current=current.getNext();
            }
            else{
                String itemName = ((Quest)current.getNext().getItem()).getReqs();
                int quantity = ((Quest)current.getNext().getItem()).getQuantity();
                int index = inv.indexInList(itemName);
                int compareQuantity = inv.getQuantity(index);
                if(index==-1){
                    compareQuantity = 0;
                }
                System.out.println(itemName + ": Req  -  " + quantity + ", Have -  " + compareQuantity);
                if(compareQuantity>=quantity){
                    i[0] = index;
                    i[1] = quantity;
                    ((Quest)current.getNext().getItem()).setCompleted();
                    foundQuest=true;
                    return i;
                }
                else{
                    current=current.getNext();
                }
            }
        }
        return i;  
    }
    private class Node {
        
        Node next;
        Object item;
        int quantity;

        public Node(Object itemValue){
            this.next = null;
            this.item = itemValue;
            this.quantity = 1;
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
        public int getQuantity(){
            return this.quantity;
        }
        public void increaseQuantity(){
            this.quantity++;
        }
        public void decreaseQuantity(){
            this.quantity--;
        }
        public Node getNext(){
            return next;
        }
        public void setNext(Node nextValue){
            next = nextValue;
        }
    }

}
