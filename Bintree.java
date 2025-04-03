

class BinaryTreeNode
{
    int value;
    BinaryTreeNode left,right;
    
    BinaryTreeNode(int item)
    {
        value = item;
        left = right = null;
    }
}

public class Bintree 
{
    BinaryTreeNode root;
    
    void insert(int value)
    {
        root = insertRec(root,value);
    }
    
    BinaryTreeNode insertRec(BinaryTreeNode root, int value)
    {
        if (root ==null)
        {
            root = new BinaryTreeNode(value);
            return root;
        }
        if (value < root.value)
        {
            root.left = insertRec(root.left,value);
        }
        else if (value > root.value)
        {
            root.right = insertRec(root.right,value);
            
        }
        return root;
    }
    
    void printTree()
    {
        printTreeRec(root);
    }
    void printTreeRec(BinaryTreeNode root)
    {
        if(root != null)
        {
            System.out.println(root.value);
            printTreeRec(root.left);
            printTreeRec(root.right);
        }
    }
    
    public static void main(String[] args)
    {
        Bintree tree =new Bintree();
        
        int[] values = {10,5,15,2,7,12,17};
        for (int value : values)
        {
            tree.insert(value);
        }
        tree.printTree();
    }
}
