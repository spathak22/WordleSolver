package language;

import java.util.*;

public class Mode {
    //Map of letter present in word and set of indexes where letter is not present
    private Map<String, Set<Integer>> positiveTerms = new HashMap<>();
    private Set<String> negativeTerms = new HashSet<>();
    private Map<Integer, String> fixedTerms = new HashMap<>();

    // 1. Prune the list by removing the words containing negative values
    // 2. Find the words with fixed terms
    // 3. Find the word with most positive values match
    public  void updateMode(String pattern, String word){
        if(pattern.length()!=9){
            throw new RuntimeException("pattern length should be 9 including commas");
        }
        if(word.length()!=5){
            throw new RuntimeException("word length should be 5");
        }
        StringTokenizer st = new StringTokenizer(pattern, ",");
        for (int i = 0; i < 5; i++){
            String symbol = st.nextToken();
            String value = word.charAt(i)+"";
            //Positive char
            if(symbol.equals("+")){
                if(this.positiveTerms.containsKey(value)){
                    this.positiveTerms.get(value).add(i);
                }else{
                    Set set = new HashSet();
                    set.add(i);
                    this.positiveTerms.put(value, set);
                }

                if(this.negativeTerms.contains(value)){
                    this.negativeTerms.remove(value);
                }
            }//Negative char
            else if(symbol.equals("-")
                    && !this.positiveTerms.containsKey(value)
                    && !this.fixedTerms.containsKey(value)){
                this.negativeTerms.add(value);
            }//Fixed char
            else if(symbol.equals("#")){
                this.fixedTerms.put(i, value);
                if(this.negativeTerms.contains(value)){
                    this.negativeTerms.remove(value);
                }
            }
        }
    }



    public Map<String, Set<Integer>> getPositiveTerms() {
        return positiveTerms;
    }

    public Set<String> getNegativeTerms() {
        return negativeTerms;
    }

    public Map<Integer, String> getFixedTerms() {
        return fixedTerms;
    }
}
