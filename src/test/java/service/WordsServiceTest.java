package service;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class WordsServiceTest {
    WordsService service = new WordsService();

    @Test
    public void removeNegativeWordsShouldWork(){
        Set<String> negativeWords = new HashSet<>();
        negativeWords.add("o");
        Map<String, Integer> map = service.removeNegativeWords(negativeWords, getWordsList());
        Assert.assertEquals(2, map.size());
    }

    @Test
    public void findWordsContainingFixedTermsShouldWork(){
        Map<Integer, String> fixedTerms = new HashMap<>();
        fixedTerms.put(0, "b");
        fixedTerms.put(4, "g");
        Map<String, Integer> map = service.findWordsContainingFixedTerms(fixedTerms, getWordsList());
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void findWordsContainingPositiveTermsShouldWork(){
        Map<String, Set<Integer>> positiveWords = new HashMap<>();
        Set<Integer> set = new HashSet<>(Arrays.asList(1,2));
        positiveWords.put("o", set);
        positiveWords.put("s", set);
        Map<String, Integer> map = service.findWordsContainingPositiveTerms(positiveWords, getWordsList());
        Assert.assertEquals(1, map.size());
    }

    public Map<String, Integer> getWordsList(){
        Map<String, Integer> words = new HashMap<>();
        words.put("hello", 1);
        words.put("rosie", 1);
        words.put("angel", 1);
        words.put("bring", 1);
        words.put("tacos", 1);
        return words;
    }



}
