package spell;

public class Node implements INode{
    public int count_m;
    public boolean is_root_node_m;
    public Node[] nodes_m;
    public int num_times_printed_m;
    public int ascii_value_m;

    final private int NUMBER_OF_ASCII_CHARACTERS = 255; // "final" is the same thing as "const" in C++

    // Constructor
    public Node() {
        this.count_m = 0;
        this.nodes_m = new Node[NUMBER_OF_ASCII_CHARACTERS]; // Will be full of null values on instantiation
        this.ascii_value_m = 0;
        this.is_root_node_m = false;
        this.num_times_printed_m = -1;
    }

    public int makeRecursiveNodesFromString(String word, int tempNodeCount) {  // word is coming in already uppercased

        // I need to parse through each char of the string
        if(this.nodes_m[word.charAt(0)] == null) {

            Node tempNode = new Node(); // Create new node
            tempNodeCount++;
            //System.out.print("here is the tempNodeCount: " + tempNodeCount);
            tempNode.ascii_value_m = (int)word.charAt(0); // Here we are type casting the char into an int to mark
                // what its ascii_value_m is.
            this.nodes_m[word.charAt(0)] = tempNode; // Set the root node to the tempNode
            //System.out.print(word.charAt(0));

            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                tempNodeCount = this.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString, tempNodeCount);
                //System.out.print("here is the tempNodeCount: " + tempNodeCount);
            }
            // We are at the last char of String word
            else {
                this.nodes_m[word.charAt(0)].count_m++; // Increment the last char
                //System.out.println("\n");
            }
        }
        else {
            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                tempNodeCount = this.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString, tempNodeCount);
                //System.out.print("here is the tempNodeCount: " + tempNodeCount);
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.nodes_m[word.charAt(0)].count_m++;
            }
        }

        return  tempNodeCount;
    }

    @Override
    public int getValue() {
        return this.ascii_value_m;
    }

    // Each Node has a count
    // Each Node has an array of possible characters from a-z that can be filled with other nodes
    // ASCII printable characters:: uppercase letters are values 65 (A) - 90 (Z). Lowercase are 97 (a) -122 (z)
    // there are 255 characters total

    // Constructor should have
//    Each Trie-Node has a single parent except for the root of the Trie which does not
////    have a parent

}
