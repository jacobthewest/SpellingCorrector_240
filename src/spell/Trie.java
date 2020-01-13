package spell;

import java.util.TreeSet;

public class Trie implements ITrie {

    // A Trie is an array of arrays of Node objects
    public Node root_m;
    public TreeSet<String> wordDictionary_m;
    public int nodeCount_m;

    // Constructor
    public Trie() {
        Node tempNode = new Node();
        this.root_m = tempNode;
        this.wordDictionary_m = new TreeSet<String>();
        this.nodeCount_m = 1;
    }

    @Override
    public void add(String word) {
        int tempNodeCount = 0;

        this.wordDictionary_m.add(word);
        word = word.toUpperCase();

        // I need to parse through each char of the string
        if(this.root_m.nodes_m[word.charAt(0)] == null) { // If the index of the first character of the root node is null

            Node tempNode = new Node(); // Create new node
            tempNode.ascii_value_m = (int)word.charAt(0); // Here we are type casting the char into an int to mark
                // what its ascii_value_m is.
            tempNodeCount += 1; // Because we've already counted the root node, this is for a child node in the root nodes array
            //System.out.print("here is the tempNodeCount: " + tempNodeCount);
            this.root_m.nodes_m[word.charAt(0)] = tempNode; // Set the root node to the tempNode


            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                tempNodeCount = this.root_m.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString, tempNodeCount); //Start recursion off of the root node
                //System.out.print("here is the tempNodeCount: " + tempNodeCount);
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
                tempNodeCount = this.root_m.nodes_m[word.charAt(0)].makeRecursiveNodesFromString(choppedString, tempNodeCount);
                //System.out.print("here is the tempNodeCount: " + tempNodeCount);
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.root_m.nodes_m[word.charAt(0)].count_m++;
            }
        }

        this.nodeCount_m += tempNodeCount;
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
        return this.wordDictionary_m.size();
    }

    @Override
    public int getNodeCount() {
        return this.nodeCount_m;
    }
}
