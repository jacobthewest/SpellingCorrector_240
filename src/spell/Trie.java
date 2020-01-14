package spell;

import java.util.TreeSet;

public class Trie implements ITrie {

    // A Trie is an array of arrays of Node objects
    public Node root_m;
    public int nodeCount_m;

    // Constructor
    public Trie() {
        Node tempNode = new Node();
        this.root_m = tempNode;
        this.nodeCount_m = 1;
    }

    @Override
    public void add(String word) {
        int tempNodeCount = 0;
        int index = Character.toUpperCase(word.charAt(0));

        // I need to parse through each char of the string
        if(this.root_m.nodes_m[index] == null) { // If the index of the first character of the root node is null

            Node tempNode = new Node(); // Create new node
            tempNode.ascii_value_m = (int)word.charAt(0); // Here we are type casting the char into an int to mark
                // what its ascii_value_m is.
            tempNode.value_m = word.charAt(0);
            System.out.print(Character.toString((tempNode.value_m)));
            tempNodeCount += 1; // Because we've already counted the root node, this is for a child node in the root nodes array

            this.root_m.nodes_m[index] = tempNode; // Set the root node to the tempNode

            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                index = Character.toUpperCase(word.charAt(0));
                String previousChars = Character.toString(root_m.nodes_m[index].value_m);
                tempNodeCount = this.root_m.nodes_m[index].makeRecursiveNodesFromString(choppedString, tempNodeCount, previousChars); //Start recursion off of the root node
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.root_m.nodes_m[index].count_m++;
            }
        }
        else {
            // Node with this letter has already been created at this level of the trie
            if (word.length() > 1) {
                String choppedString = word.substring(1); // Cuts off the first char in the string
                index = Character.toUpperCase(word.charAt(0));
                String previousChars = Character.toString(root_m.nodes_m[index].value_m);
                tempNodeCount = this.root_m.nodes_m[index].makeRecursiveNodesFromString(choppedString, tempNodeCount, previousChars);
            }
            // We are at the last char of String word
            else {
                // Increment the count because we are at the final char of the word
                this.root_m.nodes_m[index].count_m++;
            }
        }

        this.nodeCount_m += tempNodeCount;
        System.out.print("\n");
    }

    public String toString() {
//        StringBuilder masterString = new StringBuilder();
//        this.root_m.createString(masterString, "");
//        return masterString.toString();
        StringBuilder curr_w = new StringBuilder();
        StringBuilder res = new StringBuilder();
        this.root_m.recToString(curr_w,res,this.root_m);
        return res.toString();
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
        return this.nodeCount_m;
    }

    @Override
    public int hashCode() {
        int hashCode = (getWordCount() * 7) + (getNodeCount() * 666); // 7 because it's holy. 666 because its evil.
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Trie)) {
            return false;
        }
        Trie sketchyTrie = (Trie)o;
        compare(this.root_m, sketchyTrie.root_m);
        return true;
    }

    public boolean compare(Node n1, Node n2) {
        return true;
    }
}
