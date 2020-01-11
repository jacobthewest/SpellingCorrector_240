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

    public void createString(StringBuilder masterStringBuilder) {
        if (!this.is_root_node_m) {

            char ascii_val_as_char = (char)this.ascii_value_m; // Cast ascii_value_m to a char
            masterStringBuilder.append(ascii_val_as_char); // Add the char to our stringbuilder
            System.out.print(ascii_val_as_char);

            if (this.count_m > 0 && (this.count_m > this.num_times_printed_m)) { // We have the ending of a word

                masterStringBuilder.append("\n"); // Add the new line to our stringbuilder
                System.out.print("\n");
                this.num_times_printed_m++; // Make it so we don't print this word again (unless it shows up more than once)
            }
        }

        StringBuilder childrenAsStringBuilder = new StringBuilder();

        for (int i = 0; i < this.nodes_m.length; i++) { // Just .length because it's a member variable of an array

            // Only work with the node if it is actually a node and IS NOT null
            if(this.nodes_m[i] != null) {
                int tempInt = this.nodes_m[i].ascii_value_m;
                char tempChar = (char)tempInt;
                nodes_m[i].createString(childrenAsStringBuilder); // This will return a StringBuilder
            }
        }
    }

    public void makeRecursiveNodesFromString(String word) {  // word is coming in already uppercased

        // I need to parse through each char of the string
        if(this.nodes_m[word.charAt(0)] == null) {

            Node tempNode = new Node(); // Create new node
            tempNode.ascii_value_m = (int)word.charAt(0); // Here we are type casting the char into an int to mark
                // what its ascii_value_m is.
            this.nodes_m[word.charAt(0)] = tempNode; // Set the root node to the tempNode
            //System.out.print(word.charAt(0));

            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                this.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString);
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
                this.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString);
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.nodes_m[word.charAt(0)].count_m++;
            }
        }
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
