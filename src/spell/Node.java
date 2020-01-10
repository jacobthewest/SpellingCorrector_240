package spell;

public class Node implements INode{
    public boolean isParentNode_m;
    public int count_m;
    public char[] nodes_m;
    final private int NUMBER_OF_ASCII_CHARACTERS = 255; // "final" is the same thing as "const" in C++

    // Constructor
    public Node() {
        this.isParentNode_m = false;
        this.count_m = 0;
        this.nodes_m = new char[NUMBER_OF_ASCII_CHARACTERS]; // Will be full of null values on instantiation

    }

    @Override
    public int getValue() {
        return 0;
    }

    // Each Node has a count
    // Each Node has an array of possible characters from a-z that can be filled with other nodes
    // ASCII printable characters:: uppercase letters are values 65 (A) - 90 (Z). Lowercase are 97 (a) -122 (z)
    // there are 255 characters total

    // Constructor should have
//    Each Trie-Node has a single parent except for the root of the Trie which does not
////    have a parent

}
