package passoff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spell.ITrie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {


    private static String triePattern ="\\s*baboon\\s+car\\s+cares\\s+caress\\s*";
    private static String trieString = "baboon\ncar\ncares\ncaress";
    private static String wrongTrieString = "baboon\ncar\ncar\ncares\ncaress";
    private static String FILENAME = "notsobig.txt";
    private ITrie studentTrie;
    private ITrie studentTrie2;

    @BeforeEach
    @DisplayName("Set Up Trie Objects")
    public void setup(){
        try{
            studentTrie = new spell.Trie();
            studentTrie2 = new spell.Trie();
        }
        catch(Throwable t){
            assertTrue(false, t.getClass() + ". Make sure class name is spell.Trie.");
        }
    }

    @Test
    @DisplayName("Single Word Testing")
    public void testSingleWord(){
        assertEquals(0, studentTrie.getWordCount(), "Incorrect word count on empty trie");
        add("cares");
        assertNotNull(studentTrie.find("cares"), "First word added wasn't found (\"cares\")");
        assertEquals(6, studentTrie.getNodeCount(), "Incorrect node count after 1 add");
        assertEquals(1, studentTrie.getWordCount(), "Incorrect word count after 1 add");
    }

    @Test
    @DisplayName("Two Word Testing")
    public void testTwoWords(){
        add("cares");
        add("caress");
        assertNotNull(studentTrie.find("caress"), "Second word added wasn't found (\"caress\")");
        assertEquals(7, studentTrie.getNodeCount(), "Incorrect node count after second add");
        assertEquals(2, studentTrie.getWordCount(), "Incorrect word count after 2 adds");
    }

    @Test
    @DisplayName("New Path Testing")
    public void testNewPath(){
        add("cares");
        add("caress");
        add("baboon");
        assertNotNull(studentTrie.find("baboon"), "New word added not found (\"baboon\")");
        assertEquals(13, studentTrie.getNodeCount(), "Incorrect node count after adding completely new word");
        assertEquals(3, studentTrie.getWordCount(), "Incorrect word count after 3 adds");
    }

    @Test
    @DisplayName("Prefix Testing")
    public void testPrefix(){
        add("cares");
        add("caress");
        add("baboon");
        add("car");
        assertNotNull(studentTrie.find("car"), "Prefix of first word not found (\"car\")");
        assertEquals(13, studentTrie.getNodeCount(), "Incorrect node count after adding no new nodes");
        assertEquals(4, studentTrie.getWordCount(), "Incorrect word count after 4 adds");
    }

    @Test
    @DisplayName("Equals Testing")
    public void testEquals(){
        add("cares");
        add("caress");
        add("baboon");
        studentTrie.add("car");
        assertTrue(studentTrie.equals(studentTrie), "Trie found not equal to itself");
        assertFalse(studentTrie.equals(null), "Trie equal to null");
        assertFalse(studentTrie.equals(new String("String")), "Trie equal to a String object");
        assertFalse(studentTrie.equals(studentTrie2), "Unequal Trie objects found equal (student trie has word \\\"car\\\", where other trie doesn\'t)");
        studentTrie2.add("car");
        assertTrue(studentTrie.equals(studentTrie2), "Equal Trie objects found unequal");
        assertEquals(studentTrie.equals(studentTrie2), studentTrie2.equals(studentTrie), "Trie\'s equals() method is not commutative");
        studentTrie2.add("car");
        assertFalse(studentTrie.equals(studentTrie2), "Unequal Trie objects found equal (both have word \"car\", but word frequency is different)");
    }

    @Test
    @DisplayName("More Equals Testing")
    public void testMoreEquals(){
        assertTrue(studentTrie.equals(studentTrie2), "Two empty tries not found equal.");
        for(char c = 'a'; c <= 'z'; c++){
            studentTrie.add(Character.toString(c));
        }
        assertFalse(studentTrie.equals(studentTrie2), "One empty trie found equal to un-empty trie.");
        assertFalse(studentTrie2.equals(studentTrie), "One empty trie found equal to un-empty trie.");
        for(char c = 'a'; c <= 'z'; c++){
            studentTrie2.add(Character.toString(c));
        }
        assertTrue(studentTrie.equals(studentTrie2), "Tries with a-z on root found unequal.");

        add("jack");
        add("jackson");
        add("jackblack");
        add("janitor");
        assertTrue(studentTrie.equals(studentTrie2), "Two equal branching tries found un-equal.");
        studentTrie.add("jackblanco");
        assertFalse(studentTrie.equals(studentTrie2), "Two un-equal branching tries found equal.");
        assertFalse(studentTrie2.equals(studentTrie), "Two un-equal branching tries found equal.");
    }

    @Test
    @DisplayName("Duplicate Nodes Testing")
    public void testDuplicateNodes(){
        add("cares");
        add("caress");
        add("baboon");
        add("car");
        add("car");
        assertEquals(13, studentTrie.getNodeCount(), "Incorrect node count after duplicate nodes");
        assertEquals(4, studentTrie.getWordCount(), "Incorrect word count after duplicate add");
    }

    @Test
    @DisplayName("Find Testing")
    public void testFind(){
        add("cares");
        add("caress");
        add("baboon");
        add("car");
        add("car");
        assertNull(studentTrie.find("vnjklnasldkgnmb"),
                "Found nonsense word (should have returned null)");
        assertNull(studentTrie.find("caresses"),
                "Found  \"caresses\" (should have returned null)");
        assertNull(studentTrie.find("c"),
                "Found first letter of first word (should have returned null)");
        assertNull(studentTrie.find("ca"),
                "Found \"ca\" (prefix of first word) (should have returned null)");
        assertNull(studentTrie.find("care"),
                "Found \"care\" (prefix of first word) (should have returned null)");
    }

    @Test
    @DisplayName("Hash Code Testing")
    public void testHashCode(){
        add("cares");
        add("caress");
        add("baboon");
        add("car");
        add("car");
        assertEquals(studentTrie.hashCode(), studentTrie.hashCode(), "Same Trie does not return the same hashCode");
        assertEquals(studentTrie.hashCode(), studentTrie2.hashCode(), "Equal Trie objects return unequal hashCodes");
        assertNotEquals(System.identityHashCode(studentTrie), studentTrie.hashCode(), "The hashCode() method was not overwritten");
    }

    @Test
    @DisplayName("More Hash Code Testing")
    public void testMoreHashCode(){
        studentTrie.add("dat"); //word: 1 node: 4
        studentTrie2.add("far"); //word: 1 node: 4
        System.out.println(studentTrie.hashCode());
        System.out.println(studentTrie2.hashCode());
        assertNotEquals(studentTrie.hashCode(), studentTrie2.hashCode(), "The hashCode is too simple. Different Tries return same hashCode");

        studentTrie.add("far"); //word: 2 node: 7
        studentTrie2.add("dat"); //word: 2 node: 7
        assertEquals(studentTrie.hashCode(), studentTrie2.hashCode(), "Equal Tries of different construction history return different hashCode");
        studentTrie2.add("da"); //word: 3 node: 7
        assertNotEquals(studentTrie.hashCode(), studentTrie2.hashCode(), "Tries of differing word count return same hashCode");

        studentTrie.add("date"); //word: 3 node: 8
        assertNotEquals(studentTrie.hashCode(), studentTrie2.hashCode(), "Tries of differing node count return same hashCode");

        studentTrie2.add("d"); //word: 3 node: 8
        assertNotEquals(studentTrie.hashCode(), studentTrie2.hashCode(), "Different tries of same node count and word count return same hashCode");
    }

    @Test
    @DisplayName("To String Testing")
    public void testToString(){
        add("cares");
        add("caress");
        add("baboon");
        add("car");
        add("car");
        assertFalse(studentTrie.toString().equalsIgnoreCase(wrongTrieString) || studentTrie.toString().equalsIgnoreCase(wrongTrieString+"\n"),
                "Trie toString() method has wrong count for (\"car\")");
        assertTrue(studentTrie.toString().equalsIgnoreCase(trieString) || studentTrie.toString().equalsIgnoreCase(trieString+"\n"),
                "Trie toString() method returns incorrect String\n\n");
        assertTrue(studentTrie.toString().equalsIgnoreCase(studentTrie2.toString()),"Equal Trie objects\' toString() methods return different Strings");
    }

    @Test
    @DisplayName("Large Trie Testing")
    public void testLargeTrie(){
        add("cares");
        add("caress");
        add("baboon");
        add("car");
        add("car");
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    FILENAME));
            while (in.ready()) {
                String temp = in.readLine();
                add(temp);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());
            fail("Exception thrown during million+ word add (including duplicates)");
        }
        assertEquals(78891, studentTrie.getNodeCount(),
                "Incorrect node count after million+ word add (including many duplicates)");
        assertEquals(studentTrie, studentTrie2,
                "Equal Trie objects found unequal during million+ word add (including duplicates)");
        assertEquals(29157, studentTrie.getWordCount(), "Incorrect word count after many adds");
    }

    private void add(String word){
        studentTrie.add(word);
        studentTrie2.add(word);
    }
}
