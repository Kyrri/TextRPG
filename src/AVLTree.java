import java.io.Serializable;

public class AVLTree<T extends Comparable> implements List<T>, Serializable {

    private static final long serialVersionUID = 42;
    private AVLNode root;

    public boolean isEmpty() {
        return root == null;
    }

    public int length() {
        if (isEmpty()) {
            return 0;
        }
        return root.size();
    }

    public boolean isInList(T x) {
        if (!isEmpty()) {
            return root.isInList(x);
        }
        return false;
    }

    public T firstItem() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("empty");
        }
        return root.getSmallest().value;
    }

    public T getItem(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= length()) {
            throw new IndexOutOfBoundsException();
        }
        return root.getItem(i);
    }

    public List<T> insert(T x) {
        if (isEmpty()) {
            root = new AVLNode(x);
        } else {
            root.insert(x);
        }
        assert root.isBalanced();
        return this;
    }

    public List append(T x) {
        throw new UnsupportedOperationException("append is unsupported");
    }

    public List delete(T x) {
        if (!isEmpty()) {
            if (root.delete(x, false)) {
                root = null;
            } else {
                assert root.isBalanced();
            }
        }
        return this;
    }

    public List delete() {
        if (!isEmpty()) {
            delete(firstItem());
        }
        return this;
    }

    public int count(T x) {
        if (!isEmpty()) {
            return root.count(x);
        }
        return 0;
    }

    class AVLNode implements Serializable {

        private AVLNode left;
        private AVLNode right;

        private int height;
        private T value;
        private int count;

        public AVLNode() {
        }

        public AVLNode(T t) {
            value = t;
            count = 1;
            height = 1;
        }

        private AVLNode(AVLNode old, AVLNode left, AVLNode right) {
            value = old.value;
            count = old.count;
            this.left = left;
            this.right = right;
            height();
        }

        public boolean isBalanced() {
            if ((left == null) && (right == null)) {
                return Math.abs(balance()) < 2;
            }
            if (left == null) {
                return Math.abs(balance()) < 2 && right.isBalanced();
            }
            if (right == null) {
                return Math.abs(balance()) < 2 && left.isBalanced();
            }
            return Math.abs(balance()) < 2 && right.isBalanced() && left.isBalanced();
        }

        private void height() {
            if (left == null && right == null) {
                height = 1;
                return;
            }
            if (left == null) {
                height = 1 + right.height;
                return;
            }
            if (right == null) {
                height = 1 + left.height;
                return;
            }
            height = 1 + Math.max(left.height, right.height);
        }

        private int balance() {
            if ((left == null) && (right == null)) {
                return 0;
            }
            if (left == null) {
                return 0 - right.height;
            }
            if (right == null) {
                return left.height;
            }
            return left.height - right.height;
        }

        private void rebalance(int balance) {
            if (balance == 2) {
                if (left.balance() == 1) { // right
                    rightRotation();
                } else { //left right
                    left.leftRotation();
                    rightRotation();
                }
                return;
            }
            if (balance == -2) {
                if (right.balance() == -1) { // left
                    leftRotation();
                } else { // right left
                    right.rightRotation();
                    leftRotation();
                }
                return;
            }
            assert false;
        }

        private void leftRotation() {
            AVLNode newLeft = new AVLNode(this, left, right.left);
            value = right.value;
            count = right.count;
            left = newLeft;
            right = right.right;
            height();
        }

    
        private void rightRotation() {
            AVLNode newRight = new AVLNode(this, left.right, right);
            value = left.value;
            count = left.count;
            right = newRight;
            left = left.left;
            height();
        }

        public boolean isInList(T elem) {
            int comp = value.compareTo(elem);
            if (comp == 0) {
                return value.equals(elem);
            }
            if (right != null && comp < 0 ) {
                return right.isInList(elem);
            }
            if (left != null && comp > 0) {
                return left.isInList(elem);
            }
            return false;
        }

 
        public void insert(T t) {
            int comp = value.compareTo(t);
            if (comp == 0) {
                count++;
                return;
            }
            if (comp > 0) { // ist kleiner also muss es nach links
                if (left == null) {
                    left = new AVLNode(t);
                } else {
                    left.insert(t);
                }
            } else { // und hier rechts
                if (right == null) {
                    right = new AVLNode(t);
                } else {
                    right.insert(t);
                }
            }
            height();
            int balance = balance();
            if (Math.abs(balance) > 1) {
                rebalance(balance);
            }
        }

  
        private boolean delete(T t, boolean force) {
            int comp = value.compareTo(t);
            if (comp == 0) {
                if (force) {
                    return true;
                }
                count--;
                if (count > 0) {
                    return false;
                }

                if ((left == null) && (right == null)) { // er hat keine Kinder
                    return true;
                }
                if (left == null) { // hat nur ein kind rechts
                    value = right.value;
                    count = right.count;
                    right = null;
                    height();
                    return false;
                }
                if (right == null) { // hat nur ein kind links
                    value = left.value;
                    count = left.count;
                    left = null;
                    height();
                    return false;
                }
                // der knoten hat zwei kinder
                AVLNode next = right.getSmallest(); // es muss ein vorgaenger existieren,
                value = next.value;
                count = next.count;
                if (right.delete(next.value, true)) {
                    right = null;
                }
                height();
                int balance = balance();
                if (Math.abs(balance) > 1) {
                    rebalance(balance);
                }
                return false;
            }
            if (comp > 0) { // ist groesser also muss es nach links
                if (left == null) {
                    return false;
                } else {
                    if (left.delete(t, false)) {
                        left = null;
                    }
                }
            } else {  // und hier rechts
                if (right == null) {
                    return false;
                } else {
                    if (right.delete(t, false)) {
                        right = null;
                    }
                }
            }
            height();
            int balance = balance();
            if (Math.abs(balance) > 1) {
                rebalance(balance);
            }
            return false;
        }


        private AVLNode getSmallest() {
            AVLNode next = this;
            while (next.left != null) {
                next = next.left;
            }
            return next;
        }


        private int count(T x) {
            int comp = value.compareTo(x);
            if (comp == 0) {
                return count;
            }
            if (left != null && comp > 0) {
                return left.count(x);
            }
            if (right != null && comp < 0) {
                return right.count(x);
            }
            return 0;
        }

 
        private int size() {
            if (left == null && right == null) {
                return count;
            }
            if (left == null) {
                return count + right.size();
            }
            if (right == null) {
                return count + left.size();
            }
            return count + left.size() + right.size();
        }


        private T getItem(int i) {
            assert i >= 0;
            int sizeLeft = 0;
            if (left != null) {
                sizeLeft = left.size();
            }

            if (i >= sizeLeft && i < sizeLeft + count) {
                return value;
            }

            if (i < sizeLeft) {
                return left.getItem(i);
            } else {
                return right.getItem(i - sizeLeft - 1);
            }
        }
    }


    public String toString() {
        int length = length();
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i++) {
            string.append(i);
            string.append(" - ");
            string.append(getItem(i));
            string.append("\n");
        }
        return string.toString();
    }
}
