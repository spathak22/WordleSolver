package preprocess;

import util.FileUtil;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PreprocessData {
    private String datasetPath = System.getProperty("user.dir");
    private String freqWordsFile = "/dataset/unigram_freq.csv";
    private String wordsFile = "/dataset/words_dictionary.json";
    private String wordsLength5 = "/dataset/words_length_5.json";
    private String wordsLength5MostVowels = "/dataset/words_length_5_most_vowels.json";

    public void init(){
        //Find five character long words
        //FileUtil.csvToJson(datasetPath, freqWordsFile, wordsFile);
        Map map = getFiveLetterWords();
        FileUtil.WriteJsonFile(datasetPath, wordsLength5, map);
        //Find the words having most vowels
        Map vowelMap = getMaxVowelWords(map);
        FileUtil.WriteJsonFile(datasetPath, wordsLength5MostVowels, vowelMap);
    }

    public Map getFiveLetterWords(){
        Map<String, Integer> map = FileUtil.readJsonFile(datasetPath, wordsFile);
        return map.entrySet().stream()
                .filter(e-> e.getKey().length()==5)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
    // Populate map with number of vowels found
    public Map getMaxVowelWords(Map<String, Integer> map){
        int maxVowelsFound = 0;
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            int numberOfVowelsFound = getNumberOfUniqueVowelsInAWord(entry.getKey());
            entry.setValue(numberOfVowelsFound);
            if(numberOfVowelsFound > maxVowelsFound){
                maxVowelsFound = numberOfVowelsFound;
            }
        }
        final int finalMaxVowelsFound = maxVowelsFound;
        return map.entrySet().stream()
                .filter(e-> e.getValue()== finalMaxVowelsFound)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public int getNumberOfUniqueVowelsInAWord(String word){
        Set<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        int vowelCount = 0;
        for (int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            if(set.contains(c)){
                vowelCount++;
                set.remove(c);
            }
        }
        return vowelCount;
    }

}
