public class AVLTree <T extends Comparable> implements List<T>{ //Actually implements AVL Tree
    private Node root;
    
    public AVLTree(){
        this.root  = null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public int length(){
        if (this.isEmpty()){
            return 0;
        }
        return root.getSize();
    }
    public Node firstNode(){
        return this.root;
    }
    public boolean isInList(T x){ //returns whether item is in list or not
        if(!this.isEmpty()){
            return root.isInList(x);
        }
        else{
            return false;
        }
    }
    public T firstItem() throws IllegalStateException{ //returns first item
        if(this.root.getItem()==null){
            throw new IllegalStateException("No first item present");
        }
        return this.root.getSmallest().getItem();
    }
    public T getItem(int i) throws IndexOutOfBoundsException{ //returns item at specific index
        throw new UnsupportedOperationException("Does not support indexing");
    }
    public T getItemByName(String name){
        if(!this.isEmpty()){
            return root.getItemByName(name);
        }
        else{
            return null;
        }
    }
    public List<T> insert(T x){ //inserts item in order of sort (use to automatically sort items as they're added)
        if(this.isEmpty()){
            this.root = new Node(x);
        }else{
            this.root.insert(x);
        }
        return this;
        
    }
    public List<T> append(T x){ //inserts item at end of list
        throw new UnsupportedOperationException("Not supported in AVL Trees");
    }
    public List<T> delete(T x){ //deletes first instance of specific item
        if(!this.isEmpty()){
            if(root.delete(x)){
                root = null;
            }
        }
        return this;
    }
    public List<T> delete(){ //deletes first item in list    
        if(!this.isEmpty()){
            delete(firstItem());
        }
        return this;
    }
    public int getQuantity(String name){ //returns quantity of item by name
        if(!this.isEmpty()){
            return this.root.getQuantityByName(name);
        }
        else{
            return 0;
        }
    }
    
    //Type-specific, depends on type of item being printed. Has default of printing object and quantity
    public void printItems(){ 
        if(!this.isEmpty()){
            this.root.printAll(); 
        }
        return;
    }
    
    //Quest Specific - Non Generic
    public String[] checkQuest(AVLTree<Item> inv){
       if(!this.isEmpty()){
           return this.root.checkQuest(inv);
       }
       String[] temp = {null, null};
       return temp;
    }
    
    private class Node {  
        Node left;
        Node right;
        T item;
        int quantity;
        int height;

        public Node(T itemValue){
            this.left = null;
            this.right = null;
            this.item = itemValue;
            this.quantity = 1;
            this.height = 1;
        }
        private Node(Node prev, Node newLeft, Node newRight){
            item = prev.item;
            quantity = prev.quantity;
            left = newLeft;
            right = newRight;
            this.setHeight();
        }
        public void setHeight(){
            if(this.left==null && this.right==null){
                this.height = 1;
                return;
            }
            else if(this.left==null){
                this.height = 1+this.right.height;
            }
            else if(this.right==null){
                this.height = 1+this.left.height;
            }
            else{
                this.height = 1 + Math.max(this.left.height, this.right.height);
            }
        }
        public T getItem(){
            return item;
        }
        public T getItemByName(String name){
           int compare = this.item.getName().toUpperCase().compareTo(name.toUpperCase());
            if(compare==0){
                return this.item;
            }
            else if(this.left!=null && compare > 0){
                return this.left.getItemByName(name); 
            }
            else if(this.right!=null && compare < 0){
                return this.right.getItemByName(name); 
            }
            else{
                return null;
            }
        }
        public int getQuantity(){
            return this.quantity;
        }
        public Node getSmallest(){
            Node temp = this;
            while(temp.left != null){
                temp=temp.left;
            }
            return temp;
        }
        public int getQuantityByName(String name){
            int compare = this.item.getName().toUpperCase().compareTo(name.toUpperCase());
            if(compare == 0){
                return this.quantity;
            }
            else if(this.left !=null && compare > 0){
                return left.getQuantityByName(name);
            }
            else if(this.right !=null && compare < 0){
                return right.getQuantityByName(name);
            }
            else{
                return 0;
            }
        }
        public int getSize(){
            if(this.left == null && this.right == null){
                return this.quantity;
            }
            if(this.left == null){
                return this.quantity + this.right.getSize();
            }
            if(this.right == null){
                return this.quantity + this.left.getSize();
            }
            else{
                return this.quantity+ this.left.getSize() + this.right.getSize();
            }
        }
        //Balance
       public boolean isBalanced(){
           if((left==null) && (right==null)){
               return (Math.abs(this.getBalance())<2);
           }
           else if(left==null){
               return (Math.abs(this.getBalance())<2 && right.isBalanced());
           }
           else if(right==null){
               return (Math.abs(this.getBalance())<2 && left.isBalanced());
           }else{
               return (Math.abs(this.getBalance())<2 && right.isBalanced() && left.isBalanced());
           }
       }
       public int getBalance(){
           if((left==null) && (right==null)){
               return 0;
           }
           else if(left==null){
               return 0-right.height;
           }
           else if(right==null){
               return left.height;
           }
           else{
               return left.height - right.height;
           }
       }
      public void reBalance(int balanceFactor){
          if(balanceFactor>=2){
              if(left.getBalance() <= -1){
                  left.rotateLeft();
              }
              this.rotateRight();
          }
          else if(balanceFactor<=-2){
              if(right.getBalance() >= 1){
                  right.rotateRight();
              }
              this.rotateLeft();   
          }
          return;
      }
      //Rotations
      public void rotateLeft(){
          Node temp = new Node(this, left, right.left);
          this.item = right.item;
          this.quantity = right.quantity;
          this.left = temp;
          this.right = right.right;
          this.setHeight();
      }
      public void rotateRight(){
          Node temp = new Node (this, left.right, right);
          this.item = left.item;
          this.quantity = left.quantity;
          this.right = temp;
          this.left =left.left;
          this.setHeight();
      }
      public boolean isInList(T x){    
          int compare = this.item.compareTo(x);
          //Quest specific - default returns true if item exists for non-quest objects
          if(this.item instanceof Quest){ //returns true if quest both exists and is complete
              compare = this.item.getName().toUpperCase().compareTo(((Quest)x).getPreReqs().toUpperCase());
              if(compare==0){
                  if(((Quest)this.item).isComplete()){
                      return true;
                  }
                  else{
                      return false;
                  }
              }
          }
          if(compare==0){
              return true;
          }else if(this.left!=null && compare>0){
              return this.left.isInList(x);
          }else if(this.right!=null && compare<0){
              return this.right.isInList(x);
          }else{
              return false;
          }
      }
      public void insert(T x){
          int compare = this.item.compareTo(x);
          if(compare == 0){
              this.quantity++;
              return;
          }
          else if(compare < 0){
              if(this.right == null){
                  this.right = new Node(x);
              }
              else{
                  this.right.insert(x);
              }
          }
          else if(compare > 0){
              if(this.left == null){
                  this.left = new Node(x);
              }
              else{
                  this.left.insert(x);
              }
          }
          this.setHeight();
          int balanceFactor = getBalance();
          if(Math.abs(balanceFactor)>1){
              this.reBalance(balanceFactor);
          }
      }
      private boolean delete(T x){//returns true if the root needs to be set to null
         int compare = this.item.compareTo(x);    
         if(compare==0){
             this.quantity--;
             if(this.quantity>0){
                 return false;
             }
             if((this.left==null) && (this.right==null)){
                 return true;
             }
             else if(this.left==null){
                 this.item=right.item;
                 this.quantity=right.quantity;
                 this.right = null;
                 this.setHeight();
                 return false;
             }
             else if(this.right==null){
                 this.item=left.item;
                 this.quantity=left.quantity;
                 this.left = null;
                 this.setHeight();
                 return false;
             }else{ //has both children
                 Node temp = right.getSmallest();
                 this.item = temp.item;
                 this.quantity = temp.quantity;
                 if(this.right.delete(temp.item)){
                     this.right=null;
                 }
                 this.setHeight();
                 int balanceFactor = this.getBalance();
                 if(Math.abs(balanceFactor)>1){
                     this.reBalance(balanceFactor);
                 }
                 return false;
             }
         }
         else if(compare>0){
             if(this.left == null){
                 return false;
             }else{
                 if(this.left.delete(x)){
                     this.left = null;
                 }
             }
         }else if(compare<0){
             if(this.right == null){
                 return false;
             }else{
                 if(this.right.delete(x)){
                     this.right = null;
                 }
             }    
         }
         this.setHeight();
         int balanceFactor = this.getBalance();
         if(Math.abs(balanceFactor)>1){
             reBalance(balanceFactor);
         }
         return false;
      }
      //Additional Functions
      public void printAll(){
         if(this != null){
             if(this.left!=null){
                 this.left.printAll();
             }
             System.out.println(this.item +", Menge: "+ this.quantity );
             if(this.right!=null){
                 this.right.printAll();     
             }
         }
         return;
      }
      //Quest Specific
      public String[] checkQuest(AVLTree<Item> inv){
          String[] i = {null, null};
          if(this.item instanceof Quest){      
              if(!((Quest)this.item).isComplete()){
                  String itemName = ((Quest)item).getReqs();
                  int quantity = ((Quest)item).getQuantity();
                  int compareQuantity = inv.getQuantity(itemName);
                  System.out.println(this.item.getName());
                  System.out.println(itemName + ": Vorbedingung  -  " + quantity + ", Vorhanden -  " + compareQuantity);
                  if(compareQuantity>=quantity){
                      i[0] = itemName;
                      i[1] = quantity+"";
                      ((Quest)item).setCompleted();
                      return i;
                  }
              }
              if(this.left!=null){
                  return this.left.checkQuest(inv);
              }
              if(this.right!=null){
                  return this.right.checkQuest(inv);
              }
              
          }
          return i;  
      }
      
    }
}
