package spell;

import java.util.TreeSet;

public class Trie implements ITrie {

    // A Trie is an array of arrays of Node objects
    public Node root_m;
    public TreeSet<String> wordDictionary_m;

    // Constructor
    public Trie() {
        Node tempNode = new Node();
        this.root_m = tempNode;
        this.wordDictionary_m = new TreeSet<String>();
    }

    @Override
    public void add(String word) {
        this.wordDictionary_m.add(word);
        word = word.toUpperCase();

        // I need to parse through each char of the string
        if(this.root_m.nodes_m[word.charAt(0)] == null) { // If the index of the first character of the root node is null

            Node tempNode = new Node(); // Create new node
            tempNode.ascii_value_m = (int)word.charAt(0); // Here we are type casting the char into an int to mark
                // what its ascii_value_m is.
            this.root_m.nodes_m[word.charAt(0)] = tempNode; // Set the root node to the tempNode


            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                this.root_m.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString); //Start recursion off of the root node
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.root_m.nodes_m[word.charAt(0)].count_m++;
            }
        }
        else {
            // Node with this letter has already been created at this level of the trie
            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                this.root_m.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString);
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.root_m.nodes_m[word.charAt(0)].count_m++;
            }
        }
    }

    public String toString() {
        String returnMe = "";
        for( String singleWord : this.wordDictionary_m )
        {
            returnMe = returnMe + singleWord + "\n";
        }
        return returnMe;
    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }
}
