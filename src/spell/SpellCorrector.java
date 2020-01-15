package spell;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.TestReporter;
import spell.Trie.Node;

public class SpellCorrector implements ISpellCorrector {
    private String suggestedWord = null;
    private Trie dictionary = null;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        FileReader fileReader = new FileReader(dictionaryFileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Scanner scanner = new Scanner(bufferedReader);

        Trie trie = new Trie(); // Contains a parent node

        String currentString;
        boolean errorFree = true;

        while(errorFree) {
            try{
                currentString = scanner.next().toLowerCase();
                trie.add(currentString);
            }
            catch(NoSuchElementException e) { // We have processed all of the words
                errorFree = false;
            }
        }
        scanner.close();
        dictionary = trie;
    }

    public String getMasterSuggestion(Set<String> editDistance1) {
        int maxCount = 0;
        String masterSuggestion = null;
        for(String eachWord : editDistance1) {
            if (dictionary.find(eachWord) != null) {
                Trie.Node tempNode = (Node)dictionary.find(eachWord);
                if(tempNode.count > maxCount) {
                    masterSuggestion = eachWord;
                    maxCount = tempNode.count;
                }
            }
        }
        return masterSuggestion;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        Set<String> editDistance1 = new TreeSet<String>();

        if (dictionary.find(inputWord) != null) { // They searched for a valid word
            return inputWord;
        }
        // If we didn't return there ^^^ then the input word is invalid. Time for some work.
        else {
            insertionDistance(inputWord, editDistance1);
            alterationDistance(inputWord, editDistance1);
            deletionDistance(inputWord, editDistance1);
            transpositionDistance(inputWord, editDistance1);

            String masterSuggestion = getMasterSuggestion(editDistance1);
            if (masterSuggestion == null) {
                Set<String> editDistance2 = makeDistance2(editDistance1);
                masterSuggestion = getMasterSuggestion(editDistance2);
                if (masterSuggestion == null) {
                    return null;
                }
            }
            return masterSuggestion;
        }
    }

    public void insertionDistance(String inputWord, Set<String> editDistance1) {
        for (int i = 0; i < (inputWord.length() + 1); i++) {
            for (char curr_char = 'a'; curr_char <= 'z'; curr_char++) {
                StringBuilder tempWord = new StringBuilder(inputWord);
                tempWord.insert(i, curr_char);
                editDistance1.add(tempWord.toString());
            }
        }
    }

    public void alterationDistance(String inputWord, Set<String> editDistance1) {
        char[] tempAltString = inputWord.toCharArray();
        for (int i = 0; i < inputWord.length(); i++) {
            for (char curr_char = 'a'; curr_char <= 'z'; curr_char++) {
                tempAltString[i] = curr_char;
                String newString = new String(tempAltString);
                editDistance1.add(newString);
            }
            tempAltString = inputWord.toCharArray(); // Reset the word to normal
        }
    }

    public void deletionDistance(String inputWord, Set<String> editDistance1) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (char curr_char = 'a'; curr_char <= 'z'; curr_char++) {
                StringBuilder tempString = new StringBuilder(inputWord);
                tempString.deleteCharAt(i);
                editDistance1.add(tempString.toString());
            }
        }
    }

    public Set<String> makeDistance2(Set<String> editDistance1) {
        Set<String> editDistance2 = new TreeSet<String>();
        for (String eachWord : editDistance1) {
            insertionDistance(eachWord, editDistance2);
            alterationDistance(eachWord, editDistance2);
            deletionDistance(eachWord, editDistance2);
            transpositionDistance(eachWord, editDistance2);
        }
        return editDistance2;
    }

    public void transpositionDistance(String inputWord, Set<String> editDistance1) {
        char[] tempTranspositionString = inputWord.toCharArray();
        for(int i = 0; i < (inputWord.length() - 1); i++) {

            // Swap chars
            char leftChar = tempTranspositionString[i];
            char rightChar = tempTranspositionString[i + 1];
            tempTranspositionString[i] = rightChar;
            tempTranspositionString[i + 1] = leftChar;

            String transposedString = new String(tempTranspositionString);
            editDistance1.add(transposedString);

            tempTranspositionString = inputWord.toCharArray();
        }
    }
}

