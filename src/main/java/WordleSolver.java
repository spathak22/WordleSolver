import language.Mode;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import preprocess.PreprocessData;
import org.kohsuke.args4j.Option;
import service.WordsService;

import java.util.*;

public class WordleSolver {

    @Option(name="-enablePreprocess",usage="preprocesses dataset")
    public Boolean enablePreprocess = false;

    WordsService service = new WordsService();
    public static void main(String[] args) {
        WordleSolver solver = new WordleSolver();
        solver.init(args);
    }

    public void init(String[] args) {
        String lastChosenWord = "adieu";
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
        //Preprocess words dataset: build list of 5 letter words
        if (enablePreprocess) {
            PreprocessData preprocessData = new PreprocessData();
            preprocessData.init();
        }

        System.out.println("Hello, Welcome to WordleSolver! ");
        //System.out.println("Enter q to exit program... ");
        System.out.println("Would you like to get started with a 5 letter word with most vowels ? y/n ");
        Scanner sc = new Scanner(System.in);
        if (answerIsYes(sc.next())) {
//            Map<String, Integer> mostVowelWordsMap = service.getMostVowelsWords();
//            LinkedHashMap  reverseSortedVowelWordMap = new LinkedHashMap();
//            mostVowelWordsMap.entrySet()
//                    .stream()
//                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                    .forEachOrdered(x -> reverseSortedVowelWordMap.put(x.getKey(), x.getValue()));
//
//            for (Map.Entry entry: mostVowelWordsMap.entrySet()){
//                lastChosenWord = (String) entry.getKey();
//                break;
//            }
            Mode mode = new Mode();
            Map<String, Integer> allWords = service.getAllWords();
            play(mode, sc, allWords, lastChosenWord);
        }
        System.out.println("Thanks for using WordleSolver, bye!");
    }

    public void play(Mode mode, Scanner sc, Map<String, Integer> wordsMap, String lastChosenWord){
        System.out.println("Try out :: "+lastChosenWord);
        System.out.println("Input result of last move in this pattern: +,+,-,-,# ");
        System.out.print("+ : positive character, ");
        System.out.print("- : negative character, ");
        System.out.println("# : fixed character");
        System.out.println("Enter q for quit");
        String pattern = sc.next();
        if(pattern.equals("q")){return;}
        mode.updateMode(pattern, lastChosenWord);

        wordsMap = service.findWordsContainingPositiveTerms
                (mode.getPositiveTerms(), service.findWordsContainingFixedTerms
                (mode.getFixedTerms(), service.removeNegativeWords(mode.getNegativeTerms(), wordsMap)));
       LinkedHashMap<String, Integer>  reverseSortedMap = new LinkedHashMap();
        wordsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        for (Map.Entry<String, Integer> entry : reverseSortedMap.entrySet()) {
            lastChosenWord = entry.getKey();
            break;
        }
        play(mode, sc, reverseSortedMap, lastChosenWord);
        return;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public boolean answerIsYes(String s){
        if (s.equals( "y") || s.equals("Y")){
            return true;
        }
        return false;
    }
}
