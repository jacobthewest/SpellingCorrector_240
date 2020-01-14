package spell;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        FileReader fileReader = new FileReader(dictionaryFileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Scanner scanner = new Scanner(bufferedReader);
        scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");  // This ignores comments and whitespace

        Trie trie = new Trie(); // Contains a parent node

        String currentString;
        boolean errorFree = true;

        while(errorFree) {
            try{
                currentString = scanner.next();
                trie.add(currentString);
            }
            catch(NoSuchElementException e) { // We have processed all of the words
                errorFree = false;
            }
        }
        scanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
//        Node tempNode = trie_m.find(inputWord);
//        return tempNode.
        return "This is from the template suggestSimilarWord extended function in the SpellCorrector.java class.";
    }
}

