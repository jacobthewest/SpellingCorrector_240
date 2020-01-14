package spell;

import java.util.TreeSet;

public class Trie implements ITrie {

    // A Trie is an array of arrays of Node objects
    public Node root;
    public int nodeCount;
    public int wordCount;

    // Constructor
    public Trie() {
        Node tempNode = new Node();
        this.root = new Node();
        this.nodeCount = 1;
        this.wordCount = 0;
    }

    @Override
    public void add(String word) {
        Node tempNode = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';

            if (tempNode.nodes[index] == null) {
                tempNode.nodes[index] = new Node();
                nodeCount++;
                tempNode.nodes[index].previousChars = word.substring(0, i + 1);
                if (i == (word.length() - 1)) {
                    wordCount++;
                    tempNode.nodes[index].count++;
                }
                tempNode = tempNode.nodes[index];
            }
            else {
                if ((word.length() - 1) == i) {
                    tempNode.nodes[index].count++;
                }
                else {
                    tempNode = tempNode.nodes[index];
                }
            }
        }
    }

    public void recToString(StringBuilder masterString, Node tempNode) {
        if (tempNode == null) {
            return;
        }
        else if (tempNode.count > 0) {
            masterString.append(tempNode.previousChars + "\n"); // Found the end of the word
        }

        for(int i = 0; i < tempNode.nodes.length; i++) {
            if (tempNode.nodes[i] != null) {
                Node childNode = tempNode.nodes[i];
                recToString(masterString, childNode);
            }
        }
    }

    public String toString() {
        StringBuilder masterString = new StringBuilder();
        recToString(masterString, root);
        return masterString.toString();
    }


    @Override
    public INode find(String word) {
        Node tempNode = root;
        for(int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (tempNode.nodes[index] == null) {
                return null; // Node doesn't exist
            }

            // We are on the last character of the word, we have found the node, and we are double checking
                // that it has a word created there
            if (word.length() - 1 == i) {
                if (tempNode.nodes[index].count > 0) {
                    return tempNode.nodes[index];
                }
                else {
                    return null; // A word doesn't exist at this point
                }
            }
            tempNode = tempNode.nodes[index];
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
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
        recCompare(this.root, sketchyTrie.root);
        return true;
    }

    public boolean recCompare(Node n1, Node n2) {

        // Compare the nodes root words
        if(!(n1.previousChars.equals(n2.previousChars))) {
            return false; // They don't have the same root word
        }

        // Recursively compare the nodes children
        for(int i = 0; i < n1.nodes.length; i++) {
            // Is one child null and the other child not null?
            if (n1.nodes[i] == null && n2.nodes[i] != null) {return false;}
            if (n1.nodes[i] != null && n2.nodes[i] == null) {return false;}

            if ((n1.nodes[i] != null) && (n2.nodes[i] != null)) {
                // Call recursively to compare the child node root words again
                recCompare(n1.nodes[i], n2.nodes[i]);
            }
        }
        return true;
    }

    public class Node implements INode {
        public int count;
        public Node[] nodes;
        public String previousChars;

        public Node() {
            count = 0;
            nodes = new Node[26];
        }
        /**
         * Returns the frequency count for the word represented by the node
         *
         * @return The frequency count for the word represented by the node
         */
        public int getValue() {
            return this.count;
        }
    }
}
