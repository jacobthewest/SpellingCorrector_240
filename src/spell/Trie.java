package spell;

public class Trie implements ITrie {

    // A Trie is an array of arrays of Node objects

    private Node root_m;

    // Constructor
    public Trie() {
        Node tempNode = new Node();
        tempNode.isParentNode_m = true;
        this.root_m = tempNode;
    }

    @Override
    public void add(String word) {
        // START HERE
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
