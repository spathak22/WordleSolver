package service;

import util.FileUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WordsService {
    private String datasetPath = System.getProperty("user.dir");
    private String wordsLength5MostVowels = "/dataset/words_length_5_most_vowels.json";
    private String wordsLength5 = "/dataset/words_length_5.json";

    public Map getMostVowelsWords(){
        return FileUtil.readJsonFile(datasetPath, wordsLength5MostVowels);
    }

    public Map getAllWords(){
        return FileUtil.readJsonFile(datasetPath, wordsLength5);
    }

    // 1. Prune the list by removing the words containing negative values
    // 2. Find the words with fixed terms
    // 3. Find the word with most positive values match

    public Map removeNegativeWords(Set<String> negativeTerms, Map<String, Integer> wordsMap){
        if(negativeTerms.size()==0){
            return wordsMap;
        }
        return wordsMap.entrySet().stream()
                .filter(e -> !containsTerm(e.getKey(), negativeTerms))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public Map findWordsContainingFixedTerms(Map<Integer, String> fixedTerms, Map<String, Integer> wordsMap){
        if(fixedTerms.size()==0){
            return wordsMap;
        }
        return wordsMap.entrySet().stream()
                .filter(e -> containsFixedTerms(e.getKey(), fixedTerms))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public Map findWordsContainingPositiveTerms(Map<String, Set<Integer>> positiveTerms, Map<String, Integer> wordsMap){
        if(positiveTerms.size()==0){
            return wordsMap;
        }
        return wordsMap.entrySet().stream()
                .filter(e -> containsAllTerms(e.getKey(), positiveTerms))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

    }

    public boolean containsTerm(String word, Set<String> terms){
        for (int i = 0; i < word.length(); i++){
            if(terms.contains(word.charAt(i)+"")){
                return true;
            }
        }
        return false;
    }

    public boolean containsAllTerms(String word, Map<String, Set<Integer>> terms){
        //Set<String> visited = new HashSet<>();
        int containsCount = 0;
        for (int i = 0; i < word.length(); i++){
            String c = word.charAt(i)+"";
            if(terms.containsKey(c) && !terms.get(c).contains(i)){
//                visited.add(c);
                containsCount++;
            }
        }
        if(containsCount >= terms.size()){
            return true;
        }
        return false;
    }

    public boolean containsFixedTerms(String word, Map<Integer, String> fixedTerms){
        for (Map.Entry<Integer, String> entry: fixedTerms.entrySet()){
            int index = entry.getKey();
            String value = entry.getValue();
            if (!value.equals(word.charAt(index)+"")){
                return false;
            }
        }
        return true;
    }
}
