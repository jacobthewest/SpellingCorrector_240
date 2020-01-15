package spell;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.TreeSet;

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

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        // First see if the input word exists already. If not, then proceed
        INode oneNodeToRuleThemAll = dictionary.find(inputWord);

        if (oneNodeToRuleThemAll != null) { // They searched for a valid word
            return inputWord;
        }
        // If we didn't return there ^^^ then the input word is invalid. Time for some work.

        // Create lists of edit distance 1 and two (using the input word) for all of the errors
        TreeSet<String> deletion_distance_1 = deletion1(inputWord);
        TreeSet<String> transposition_distance_1 = transposition1(inputWord);
        TreeSet<String> alteration_distance_1 = alteration1(inputWord);
        TreeSet<String> insertion_distance_1 = insertion1(inputWord);

        // Try to find the words from the lists in the dictionary

        //For every string in the list, check for a match in the dictionary. If there is a dictionary, stop everything ever and
            // Return the word from the node.
        boolean suggestionFound = false;
        if (!suggestionFound) {
            for (String word: deletion_distance_1) {
                if (dictionary.find(word) != null) {
                    suggestionFound = true;
                    oneNodeToRuleThemAll = dictionary.find(word);
                    break;
                }
            }
        }
        if (!suggestionFound) {
            for (String word: transposition_distance_1) {
                if (dictionary.find(word) != null) {
                    suggestionFound = true;
                    oneNodeToRuleThemAll = dictionary.find(word);
                    break;
                }
            }
        }
        if (!suggestionFound) {
            for (String word: alteration_distance_1) {
                if (dictionary.find(word) != null) {
                    suggestionFound = true;
                    oneNodeToRuleThemAll = dictionary.find(word);
                    break;
                }
            }
        }
        if (!suggestionFound) {
            for (String word: insertion_distance_1) {
                if (dictionary.find(word) != null) {
                    suggestionFound = true;
                    oneNodeToRuleThemAll = dictionary.find(word);
                    break;
                }
            }
        }

        return oneNodeToRuleThemAll.
    }

    public TreeSet<String> insertion1(String inputWord) {
        TreeSet<String> list = new TreeSet<String>();

        for (int i = 0; i < inputWord.length(); i++) {
            if (i == 0) { // First char
                for(char index = 'a'; index <= 'z'; index++) {
                    String changeMe = inputWord;

                    // Insert whole alphabet BEFORE the first char
                    String tempChar = Character.toString(index);
                    changeMe = tempChar + changeMe;
                    list.add(changeMe);
                }
            }
            if (i == inputWord.length() - 1) { // Last char
//                for(char index = 'a'; index <= 'z'; index++) { // Insert whole alphabet to the left of the last char
//                    String changeMe = inputWord;
//
//                    String tempChar = Character.toString(index);
//                    String lastChar = Character.toString(changeMe.charAt(changeMe.length() - 1));
//                    changeMe = changeMe.substring(0, changeMe.length() - 2) + tempChar + lastChar;
//                    list.add(changeMe);
//                }
                for(char index = 'a'; index <= 'z'; index++) { // Insert whole alphabet to the right of the last char
                    String changeMe = inputWord;

                    String tempChar = Character.toString(index);
                    changeMe = changeMe + tempChar;
                    list.add(changeMe);
                }
            }
            else { // Char in the middle
                // Insert whole alphabet to the left of the current char
                for (char index = 'a'; index <= 'z'; index++) {
                    String changeMe = inputWord;
                    // Insert whole alphabet to the left of the last char

                    String tempChar = Character.toString(index);
                    //String lastChar = Character.toString(changeMe.charAt(changeMe.length()));
                    changeMe = changeMe.substring(0, (i + 1)) + tempChar + changeMe.substring(i + 1, changeMe.length());
                    list.add(changeMe);
                }
            }
        }
        return list;
    }

    public TreeSet<String> alteration1(String inputWord) {
        TreeSet<String> list = new TreeSet<String>();
        for (int i = 0; i < inputWord.length(); i++) {
            for (char index = 'a'; index <= 'z'; index++) {
                String alterMe = inputWord;
                String tempChar = Character.toString(index);
                if (i == 0) { // Editing first char in the word
                    alterMe = tempChar + alterMe.substring(1, alterMe.length());
                }
                else if (i == (inputWord.length() - 1)) { // Last index
                    alterMe = alterMe.substring(0, alterMe.length() - 1) + tempChar;
                }
                else { // Somewhere in the middle
                    alterMe = alterMe.substring(0, i) + tempChar + alterMe.substring(i + 1, alterMe.length());
                }
                list.add(alterMe);
            }
        }
        return list;
    }

    public TreeSet<String> transposition1(String inputWord) {
        TreeSet<String> list = new TreeSet<String>();

        // Input string has 5 chars? Then 4 transposes exist
        for (int i = 0; i < inputWord.length(); i++) {
            if (i == 0) { // First index
                String transposed = null;
                String left = Character.toString(inputWord.charAt(0));
                String right = Character.toString(inputWord.charAt(1));
                // Swap chars
                transposed = right + left + inputWord.substring(2, inputWord.length());
                list.add(transposed);
            }
            else if (i == (inputWord.length() - 1)) { // last index
                String transposed = null;
                String left;
                left = Character.toString(inputWord.charAt(inputWord.length() - 2));
                String right;
                right = Character.toString(inputWord.charAt(inputWord.length() - 1));
                // Swap chars
                transposed = inputWord.substring(0, (i - 1)) + right + left;
                list.add(transposed);
            }
            else { // We are somewhere in the middle
                if (inputWord.length() > 3) {
                    String transposed = null;
                    String left;
                    left = Character.toString(inputWord.charAt(i));
                    String right;
                    right = Character.toString(inputWord.charAt(i + 1));
                    transposed = inputWord.substring(0, i) + right + left + inputWord.substring((i + 2), inputWord.length());
                    list.add(transposed);
                }
            }
        }
        return list;
    }

     public TreeSet<String> deletion1(String inputWord) {
         TreeSet<String> list = new TreeSet<String>();

        if (inputWord.length() < 3) {
            // Assuming that it is size two for now
            list.add(inputWord.substring(1, inputWord.length())); // Remove first index
            list.add(inputWord.substring(0, inputWord.length() - 1)); // Remove second index
        }

        for (int i = 0; i < inputWord.length(); i++) {
            String insertMe;
            if ((i > 0) && (i < inputWord.length() - 1)) { // remove the middle of the string
                String frontHalf = inputWord.substring(0, i);
                String backHalf = inputWord.substring((i + 1), inputWord.length());
                insertMe = frontHalf + backHalf;
            }
            else if(i == 0) { // remove the first index
                insertMe = inputWord.substring(1, inputWord.length());
            }
            else { // remove the last index
                insertMe = inputWord.substring(0, inputWord.length() - 1);
            }
            list.add(insertMe);
        }
        return list;
     }
}

