package spell;

public class Node implements INode{
    public int count_m;
    public boolean is_root_node_m;
    public Node[] nodes_m;
    public int num_times_printed_m;
    public int ascii_value_m;
    public char value_m;

    final private int NUMBER_OF_ASCII_CHARACTERS = 255; // "final" is the same thing as "const" in C++

    // Constructor
    public Node() {
        this.count_m = 0;
        this.nodes_m = new Node[NUMBER_OF_ASCII_CHARACTERS]; // Will be full of null values on instantiation
        this.ascii_value_m = 0;
        this.is_root_node_m = false;
        this.num_times_printed_m = -1;
    }

    public int makeRecursiveNodesFromString(String word, int tempNodeCount, String previousChars) {

        int index = Character.toUpperCase(word.charAt(0));

        // I need to parse through each char of the string
        if(this.nodes_m[word.charAt(0)] == null) {
            Node tempNode = new Node(); // Create new node
            tempNodeCount++;
            tempNode.value_m = word.charAt(0);
            System.out.println(Character.toString((tempNode.value_m)));

            tempNode.ascii_value_m = (int)word.charAt(0); // Here we are type casting the char into an int to mark
                // what its ascii_value_m is.
            this.nodes_m[index] = tempNode; // Set the root node to the tempNode

            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string

                // Update previousChars to include the current Node's character for ONLY the upcoming function
                previousChars += (previousChars + word.charAt(0));
                tempNodeCount = this.nodes_m[index].makeRecursiveNodesFromString(choppedString, tempNodeCount, previousChars);

            }
            // We are at the last char of String word
            else {
                this.nodes_m[index].count_m++; // Increment the last char
            }
        }
        else {
            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string

                // Update previousChars to include the current Node's character for ONLY the upcoming function
                previousChars += (previousChars + word.charAt(0));
                tempNodeCount = this.nodes_m[index].makeRecursiveNodesFromString(choppedString, tempNodeCount, previousChars);
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.nodes_m[index].count_m++;
            }
        }

        return  tempNodeCount;
    }

    public void recToString(StringBuilder curr_w, StringBuilder curr_res, Node curr_node) {
        //System.out.println("New node here");
        if (curr_node == null) return;
        if (curr_node.count_m > 0 && (curr_node.num_times_printed_m < curr_node.count_m)) {
            curr_res.append(curr_w);
            curr_res.append('\n');
        }

        for (int i = 0; i < this.nodes_m.length; i++) {
            if (this.nodes_m[i] != null) {
                if(this.value_m == 'o') {
                    //System.out.println(this.nodes_m['a']);
                }
                //System.out.println(this.nodes_m[i].value_m);
                curr_w.append(this.nodes_m[i].value_m);
                Node temp = this.nodes_m[i];
                this.nodes_m[i].recToString(curr_w, curr_res, this.nodes_m[i]);
            }
        }
        curr_w.deleteCharAt(curr_w.length()-1);
        //System.out.println(curr_w);
    }


//    public void createString(StringBuilder masterStringBuilder, String previousChars) {
//        // Root? Do nothing. Pass previous chars
//        // Not root and not end of word? Append to previous chars and pass the variable in
//        // End of word? Great! Append the word to the master string with a new line. Append the current word the the previous chars and pass it
//        // Already printed a word before? Keep appending to previous string. Word occurs multiple times? if it is printed already, then still print it
//            // If I'm wrong then this is an easy fix
//        if(previousChars != "") {
//            previousChars += this.value_m;
//        }
//        if (this.count_m > 0) {
//            while((this.count_m > 0) && (this.count_m > this.num_times_printed_m)) { // Should we print this?
//                masterStringBuilder.append(previousChars + "\n");
//                this.num_times_printed_m++;
//            }
//        }
//
//        // Done printing this word for now. So we are advancing onto child nodes that need printing
//        for (int i = 0; i < this.nodes_m.length; i++) {
//            if (nodes_m[i] != null) {
//                nodes_m[i].createString(masterStringBuilder, previousChars);
//            }
//        }
//
////            if (this.count_m > 0 && (this.count_m > this.num_times_printed_m)) { // We have the ending of a word
////                masterStringBuilder.append("\n"); // Add the new line to our stringbuilder
////                System.out.print("\n");
////                this.num_times_printed_m++; // Make it so we don't print this word again (unless it shows up more than once)
////            }
//
////        for (int i = 0; i < this.nodes_m.length; i++) { // Just .length because it's a member variable of an array
////            String nowCurrentPreviousString = this.previousChars + this.value_m;
////            // Only work with the node if it is actually a node and IS NOT null
////            if(this.nodes_m[i] != null) {
////                char tempChar = this.nodes_m[i].value_m;
////                nodes_m[i].createString(masterStringBuilder, nowCurrentPreviousString); // This will return a StringBuilder
////            }
////        }
//    }


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
