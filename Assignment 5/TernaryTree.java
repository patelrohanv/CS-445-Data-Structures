import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.UnsupportedOperationException;

@SuppressWarnings("unchecked")
public class TernaryTree<T> implements TernaryTreeInterface<T>, TreeInterface<T>, TreeIteratorInterface<T>{
    private TernaryNode<T> root;
    
    public TernaryTree(){
        root = null;
    }

    public TernaryTree(T rootData){
        root = new TernaryNode<T>(rootData); 
    }
    public TernaryTree(T rootData, TernaryTree<T> leftTree, 
        TernaryTree<T> middleTree, TernaryTree<T> rightTree){
        privateSetTree(rootData, leftTree, middleTree, rightTree);
    }

    /**
     * Sets the ternary tree to a new one-node ternary tree with the given data
     * @param rootData The data for the new tree's root node
     */
    public void setTree(T rootData){
        root = new TernaryNode<>(rootData);
    }

    /**
     * Sets this ternary tree to a new ternary tree
     * @param rootData The data for the new tree's root node
     * @param leftTree The left subtree of the new tree
     * @param middleTree The middle subtree of the new tree
     * @param rightTree The right subtree of the new tree
     */
    public void setTree(T rootData, TernaryTreeInterface<T> leftTree, 
        TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree){
        privateSetTree(rootData, (TernaryTree<T>) leftTree, 
            (TernaryTree<T>) middleTree, (TernaryTree<T>) rightTree);
    }

   private void privateSetTree(T rootData, TernaryTree<T> leftTree, 
    TernaryTree<T> middleTree, TernaryTree<T> rightTree) {
        root = new TernaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.getRootNode());
        }

        if ((middleTree != null) && !middleTree.isEmpty()) {
            root.setMiddleChild(leftTree.getRootNode());
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            root.setRightChild(leftTree.getRootNode());
        }

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }

        if ((middleTree != null) && (middleTree != this)) {
            middleTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    } // end privateSetTree

    public T getRootData() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    } // end getRootData

    public boolean isEmpty() {
        return root == null;
    } // end isEmpty

    public void clear() {
        root = null;
    } // end clear

    protected void setRootData(T rootData) {
        root.setData(rootData);
    } // end setRootData

    protected void setRootNode(TernaryNode<T> rootNode) {
        root = rootNode;
    } // end setRootNode

    protected TernaryNode<T> getRootNode() {
        return root;
    } // end getRootNode

    public int getHeight() {
        return root.getHeight();
    } // end getHeight

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    } // end getNumberOfNodes

    public Iterator<T> getPreorderIterator() {
        return new PreorderIterator();
    } // end getPreorderIterator
/**
 * getInorderIterator is unsupported because inorder traversal
 * visits all the nodes in the left subtree, then the root and then
 * all the nodes in the right subtree. This is not applicable to the 
 * ternary tree because the iterator would have to go to the left 
 * subtree, then the root, then the middle subtree, then the root again,
 * and then the right subtree and it does not make logical sense to 
 * visit the root more than once.
 */
    public Iterator<T> getInorderIterator() {
        throw new UnsupportedOperationException();
    } // end getInorderIterator

    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    } // end getPostorderIterator

    public Iterator<T> getLevelOrderIterator() {
    //  throw new UnsupportedOperationException();
         return new LevelOrderIterator();
    } // end getLevelOrderIterator

    private class PreorderIterator implements Iterator<T> {
        private Stack<TernaryNode<T>> nodeStack;

        public PreorderIterator() {
            nodeStack = new Stack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        } // end hasNext

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }
                if (middleChild != null){
                    nodeStack.push(middleChild);
                }
                
                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PreorderIterator

    public void iterativePreorderTraverse() {
        Stack<TernaryNode<T>> nodeStack = new Stack<>();
        if (root != null) {
            nodeStack.push(root);
        }
        TernaryNode<T> nextNode;
        while (!nodeStack.isEmpty()) {
            nextNode = nodeStack.pop();
            TernaryNode<T> leftChild = nextNode.getLeftChild();
            TernaryNode<T> middleChild = nextNode.getMiddleChild();
            TernaryNode<T> rightChild = nextNode.getRightChild();

            // Push into stack in reverse order of recursive calls
            if (rightChild != null) {
                nodeStack.push(rightChild);
            }

            if (middleChild != null) {
                nodeStack.push(rightChild);
            }

            if (leftChild != null) {
                nodeStack.push(leftChild);
            }

            System.out.print(nextNode.getData() + " ");
        } // end while
    } // end iterativePreorderTraverse

 private class PostorderIterator implements Iterator<T> {
        private Stack<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;

        public PostorderIterator() {
            nodeStack = new Stack<>();
            currentNode = root;
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        } // end hasNext
        public T next() {
            boolean foundNext = false;
            TernaryNode<T> leftChild, middleChild, rightChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                middleChild = currentNode.getMiddleChild();                
                rightChild = currentNode.getRightChild();

                if (leftChild == null) {
                    currentNode = currentNode.getMiddleChild();
                }
                if(middleChild == null){
                    currentNode = currentNode.getRightChild();
                }
                if (rightChild == null){
                    currentNode = leftChild;
                }
            } // end while

            // Stack is not empty either because we just pushed a node, or
            // it wasn't empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                TernaryNode<T> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                        currentNode = parent.getRightChild();
                    } else {
                        currentNode = null;
                    }
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            } // end if

            return nextNode.getData();
        } // end next


        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PostorderIterator

    private class LevelOrderIterator implements Iterator<T> {
        private LinkedList<TernaryNode<T>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedList<>();
            if (root != null) {
                nodeQueue.addLast(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        } // end hasNext

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.removeFirst();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.addLast(leftChild);
                }

                if(middleChild != null){
                    nodeQueue.addLast(middleChild);
                }

                if (rightChild != null) {
                    nodeQueue.addLast(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end LevelOrderIterator
}