import sun.reflect.generics.tree.Tree;

import java.lang.*;
import java.util.*;

/**
 * Created by LiuHuiwen on 2017/6/15.
 */
public class BinarySearchTree {
    public static void main(String args[]){
//        BinarySearchTree bst = new BinarySearchTree();
        redBlackTree bst = new redBlackTree();
        ArrayList<Integer> inorderwalk = new ArrayList<>();
        int[] keys = new int[]{20,9,13,7,15,18,17,6,4,2,3};
        TreeNode root = bst.buildTree(keys);
//        bst.inorderTreeWalk2(root,inorderwalk);
//        TreeNode keynode = bst.treeMinmum(root);
        TreeNode node = bst.treeSearch(root, 2);
//        TreeNode successor = bst.treePredecessor(node);
        bst.inorderTreeWalk1(root, inorderwalk);
//        bst.print(root);
        System.out.println(inorderwalk);
        System.out.println();
        System.out.println("---------------------------");
        inorderwalk.clear();
        root = bst.treeDeleteNode(root, node);
        bst.inorderTreeWalk1(root, inorderwalk);
        System.out.println(inorderwalk);
    }
    public void inorderTreeWalk1(TreeNode root, ArrayList<Integer> list){
        //recursive function
        if(root==null) return ;
        inorderTreeWalk1(root.left, list);
        list.add(root.key);
        inorderTreeWalk1(root.right, list);
    }
    public void inorderTreeWalk2(TreeNode root, ArrayList<Integer> list){
        //non-recursive function
        if(root==null) return;
        Stack<TreeNode > stack = new Stack<>();
        TreeNode point = root;
        stack.push(root);
        while(!stack.isEmpty()){
            while(point!=null&&point.left!=null){
                stack.push(point.left);
                point = point.left;
            }
            point = stack.pop();
            list.add(point.key);
            point = point.right;
            if(point!=null) stack.push(point);
        }
    }
    public TreeNode treeInsert(TreeNode root, int key){
        if(root==null) return new TreeNode(key, null);
        TreeNode parent = root;
        TreeNode point = root;
        while(point!=null){
            parent = point;
            if(key < point.key) point = point.left;
            else point = point.right;
        }
        if(key < parent.key) parent.left = new TreeNode(key, parent);
        else parent.right = new TreeNode(key, parent);
        return root;
    }
    public TreeNode buildTree(int[] keys){
        TreeNode root = null;
        for(int key:keys){
            root = this.treeInsert(root,key);
        }
        return root;
    }
    public TreeNode treeSearch(TreeNode root, int key){
        TreeNode point = root;
        while(point.key!=key){
            if(key < point.key) point = point.left;
            else point = point.right;
            if(point==null) return null;
        }
        return point;
    }
    public TreeNode treeMaximum(TreeNode root){
        if(root==null) return null;
        TreeNode point = root;
        while(point.right!=null) point = point.right;
        return point;
    }
    public TreeNode treeMinmum(TreeNode root){
        if(root==null) return null;
        TreeNode point = root;
        while(point.left!=null) point = point.left;
        return point;
    }
    public TreeNode treeSuccessor(TreeNode x){
        if(x==null) return null;
        if(x.right!=null) return treeMinmum(x.right);
        TreeNode point = x;
        while(point.parent!=null){
            TreeNode pre = point.parent;
            if(point==pre.right){
                point = pre;
                continue;
            }
            else break;
        }
        return point.parent;
    }
    public TreeNode treePredecessor(TreeNode x){
        if(x==null) return null;
        if(x.left!=null) return treeMaximum(x.left);
        TreeNode point = x;
        while(point.parent!=null){
            TreeNode pre = point.parent;
            if(point==pre.left){
                point = pre;
                continue;
            }
            else break;
        }
        return point.parent;
    }
    public TreeNode treeDeleteNode(TreeNode root, TreeNode delNode){
        boolean r = (delNode.right==null);
        boolean l = (delNode.left==null);
        if(l){
            root = transplant(root, delNode, delNode.right);
            return root;
        }
        if(r){
            root = transplant(root, delNode, delNode.left);
            return root;
        }
        TreeNode replaceNode = treeMinmum(delNode);
        if(replaceNode.parent!=delNode){
            root = transplant(root, replaceNode, replaceNode.right);
            replaceNode.right = delNode.right;
        }
        root = transplant(root,delNode,replaceNode);
        replaceNode.left = delNode.left;
        delNode.left.parent = replaceNode;
        return root;
    }
    private TreeNode transplant(TreeNode root, TreeNode delNode, TreeNode replaceNode){
        if(delNode.parent==null){
            root = replaceNode;
            return root;
        }
        TreeNode parent = delNode.parent;
        if(parent.left==delNode) parent.left = replaceNode;
        else parent.right = replaceNode;
        if(replaceNode!=null) replaceNode.parent = parent;
        return root;
    }
}
class TreeNode{
    public static enum Color{Black, Red};
    public TreeNode parent;
    public TreeNode left;
    public  TreeNode right;
    public int key;
    public Color color;
//    public int value;
    public TreeNode(int k, TreeNode parent){
        this.key = k;
        this.parent = parent;
        this.left = null;
        this.right = null;
        this.color = Color.Red;
    }
}
class redBlackTree extends BinarySearchTree{
    private TreeNode NIL;
    redBlackTree(){
        this.NIL = new TreeNode(0, NIL);
        this.NIL.left = NIL;
        this.NIL.right = NIL;
        this.NIL.color = TreeNode.Color.Black;
    }
    public TreeNode leftRotate(TreeNode root, TreeNode x){
        TreeNode y = x.right;
        if(x.parent != NIL){
            if(x == x.parent.left) x.parent.left = y;
            else x.parent.right = y;
        }
        y.parent = x.parent;
        x.parent = y;
        x.right = y.left;
        if(y.right != NIL) y.right.parent = x;
        y.left = x;
        if(y.parent ==NIL) return y;
        else return root;
    }
    public TreeNode rightRotate(TreeNode root, TreeNode x){
        TreeNode y = x.left;
        y.parent = x.parent;
        if(x.parent != NIL){
            if(x == x.parent.left) x.parent.left = y;
            else x.parent.right = y;
        }
        x.left = y.right;
        if(y.right != NIL) y.right.parent = x;
        x.parent = y;
        y.right = x;
        if(y.parent == NIL) return y;
        else return root;
    }
    public TreeNode rbInsertFixup(TreeNode root, TreeNode x){
        TreeNode newroot= root;
        while(x.parent.color == TreeNode.Color.Red){
            TreeNode y;
            if(x.parent == x.parent.parent.left){
                y = x.parent.parent.right;
                if(y.color == TreeNode.Color.Red){
                    x.parent.color = TreeNode.Color.Black;
                    y.color = TreeNode.Color.Black;
                    x.parent.parent.color = TreeNode.Color.Red;
                    x = x.parent.parent;
                }
                else {
                    if (x == x.parent.right) {
                        x = x.parent;
                        newroot = this.leftRotate(newroot, x);
                    }
                    x.parent.color = TreeNode.Color.Black;
                    x.parent.parent.color = TreeNode.Color.Red;
                    newroot = this.rightRotate(newroot, x.parent.parent);
                }
            }
            else{
                y = x.parent.parent.left;
                if(y.color == TreeNode.Color.Red){
                    x.parent.color = TreeNode.Color.Black;
                    y.color = TreeNode.Color.Black;
                    x.parent.parent.color = TreeNode.Color.Red;
                    x = x.parent.parent;
                }
                else {
                    if (x == x.parent.left) {
                        x = x.parent;
                        newroot = this.rightRotate(newroot, x);
                    }
                    x.parent.color = TreeNode.Color.Black;
                    x.parent.parent.color = TreeNode.Color.Red;
                    newroot = this.leftRotate(newroot, x.parent.parent);
                }
            }
            newroot.color = TreeNode.Color.Black;
        }
        return newroot;
    }
    @Override
    public TreeNode treeInsert(TreeNode root, int key){
        if(root==NIL){
            TreeNode newNode = new TreeNode(key, NIL);
            newNode.right = NIL;
            newNode.left = NIL;
            newNode.color = TreeNode.Color.Black;
            return newNode;
        }
        TreeNode point = root;
        TreeNode pre = point;
        while(point != NIL){
            pre = point;
            if(key < point.key) point = point.left;
            else point = point.right;
        }
        TreeNode newNode = new TreeNode(key,pre);
        newNode.right = NIL;
        newNode.left = NIL;
        newNode.color = TreeNode.Color.Red;
        if(key < pre.key) pre.left = newNode;
        else pre.right = newNode;
        point = this.rbInsertFixup(root, newNode);
        return point;
    }
    @Override
    public TreeNode buildTree(int[] keys){
        TreeNode root = this.NIL;
        for(int key:keys){
            root = this.treeInsert(root,key);
//            this.print(root);
//            System.out.println();
//            System.out.println("----------------------------------");
        }
        return root;
    }
    @Override
    public void inorderTreeWalk1(TreeNode root, ArrayList<Integer> list){
        //recursive function
        if(root==NIL) return ;
        inorderTreeWalk1(root.left, list);
        list.add(root.key);
        inorderTreeWalk1(root.right, list);
    }
    public void print(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> lst = new LinkedList<>();
        queue.offer(root);
        lst.offer(1);
        int count = 0, flag = 1, index = 1;
        while(!queue.isEmpty()){
            ++count;
            index = lst.poll();
            TreeNode point = queue.poll();
            if(point != NIL){
                queue.offer(point.left);
                lst.offer(2*count);
                queue.offer(point.right);
                lst.offer(2*count + 1);
            }
            while(count != index){
                count++;
                System.out.print("0, ");
            }
            System.out.print(point.key+ " " + point.color + ", ");
//            System.out.print(point.key + ", ");
            if(count == flag){
                flag = flag*2 + 1;
                System.out.println();
            }
        }
    }
    public TreeNode rbDeleteFixup(TreeNode root, TreeNode x){
        while((x != root) && x.color == TreeNode.Color.Black){
            if(x == x.parent.left){
                TreeNode w = x.parent.right;
                if(w.color == TreeNode.Color.Red) {
                    w.color = TreeNode.Color.Black;
                    x.parent.color = TreeNode.Color.Red;
                    root = this.leftRotate(root, x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == TreeNode.Color.Black && w.right.color == TreeNode.Color.Black){
                    w.color = TreeNode.Color.Red;
                    x = x.parent;
                }
                else{
                    if(w.right.color == TreeNode.Color.Black){
                        w.left.color = TreeNode.Color.Black;
                        w.color = TreeNode.Color.Red;
                        root = this.rightRotate(root, w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = TreeNode.Color.Black;
                    w.right.color = TreeNode.Color.Black;
                    root = this.leftRotate(root, x.parent);
                    x = root;
                }
            }
            else{
                TreeNode w = x.parent.left;
                if(w.color == TreeNode.Color.Red){
                    w.color = TreeNode.Color.Black;
                    x.parent.color = TreeNode.Color.Red;
                    root = this.rightRotate(root, x.parent);
                    w = x.parent.left;
                }
                if(w.left.color == TreeNode.Color.Black && w.right.color == TreeNode.Color.Black){
                    w.color = TreeNode.Color.Red;
                    x = x.parent;
                }
                else{
                    if(w.left.color == TreeNode.Color.Black){
                        w.right.color = TreeNode.Color.Black;
                        w.color = TreeNode.Color.Red;
                        root = this.leftRotate(root, w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = TreeNode.Color.Black;
                    w.left.color = TreeNode.Color.Black;
                    root = this.rightRotate(root, x.parent);
                    x = root;
                }
            }
        }
        x.color = TreeNode.Color.Black;
        return root;
    }
    @Override
    public TreeNode treeDeleteNode(TreeNode root, TreeNode delNode) {
        TreeNode y = delNode;
        TreeNode x;
        TreeNode.Color originalColor = y.color;
        if(delNode.left == NIL){
            x = delNode.right;
            root = transplant(root, delNode, delNode.right);
        }
        else{
            if(delNode.right == NIL){
                x = delNode.left;
                root = transplant(root, delNode, delNode.left);
            }
            else{
                y = treeMinmum(delNode.right);
                originalColor = y.color;
                x = y.right;
                if(y.parent == delNode) x.parent = y;
                else{
                    root = transplant(root, y, y.right);
                    y.right = delNode.right;
                    y.right.parent = y;
                }
                root = transplant(root, delNode, y);
                y.left = delNode.left;
                y.left.parent = y;
                y.color = delNode.color;
            }
        }
        if(originalColor == TreeNode.Color.Black) root = rbDeleteFixup(root, x);
        return root;
    }
    private TreeNode transplant(TreeNode root, TreeNode delNode, TreeNode replaceNode){//将子树 delNode 完全由子树 replaceNode代替
        if(delNode.parent==NIL){
            root = replaceNode;
        }
        else{
            TreeNode parent = delNode.parent;
            if(parent.left==delNode) parent.left = replaceNode;
            else parent.right = replaceNode;
        }
        replaceNode.parent = delNode.parent;
        return root;
    }
    @Override
    public TreeNode treeMinmum(TreeNode root){
        if(root==NIL) return NIL;
        TreeNode point = root;
        while(point.left!=NIL) point = point.left;
        return point;
    }
}