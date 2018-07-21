import java.util.*;
import java.util.stream.Stream;

public class Application {

    private static final String REGEX_VOGAIS = ".*[AEIOUaeiou].*";

    public static void main(String[] args) {
        //String testString = "aAbBABacafe";
        //String testString = "aAbBABMAiaHHAaeIAIBHFAOpUIdhacafaD";
        String testString = "AIBHFAppUIdhacafaDo";

        Stream<Character> input
                = testString.codePoints().mapToObj(c -> (char) c);

        char response = firstChar(input);

        System.out.println("Dada a Stream '" + testString + "':");
        if (response != '\0') {
            System.out.println("A vogal '" + response + "' é a primeira vogal que não se repete seguindo a sequencia vogal|consoante|vogal");
        } else {
            System.out.println("De acordo com as regras, nenhuma vogal foi encontrada");
        }

    }

    public static char firstChar(Stream input) {
        Iterator iterator = input.iterator();

        String antepenultimate, penultimate;
        antepenultimate = penultimate = "";
        char response = '\0';
        int countA, countE, countI, countO, countU;
        countA = countE = countI = countO = countU= 0;
        int i = 1;
        int countByCandidate = 0;
        Map<Character,Integer> candidates = new HashMap<>();

        while (iterator.hasNext()) {
            int countByVowel = 0;
            String letter = iterator.next().toString().toUpperCase();

            Boolean isVowel = letter.matches(REGEX_VOGAIS);
            if (isVowel) {
                if (letter.matches("[Aa]")) {
                    //countA++;
                    countByVowel = countA++;
                }
                if (letter.matches("[Ee]")) {
                    countE++;
                    countByVowel = countE;
                }
                if (letter.matches("[Ii]")) {
                    countI++;
                    countByVowel = countI;
                }
                if (letter.matches("[Oo]")) {
                    countO++;
                    countByVowel = countO;
                }
                if (letter.matches("[Uu]")) {
                    countU++;
                    countByVowel = countU;
                }
            }
            if (i == 1)
                antepenultimate = letter;
            else if (i == 2)
                penultimate = letter;
            else if (i >= 3) {
                if (antepenultimate.matches(REGEX_VOGAIS) && !penultimate.matches(REGEX_VOGAIS) && isVowel && countByVowel == 1){
                    candidates.put(letter.charAt(0),countByVowel);
                }
                else if(antepenultimate.matches(REGEX_VOGAIS) && !penultimate.matches(REGEX_VOGAIS) && isVowel && countByVowel > 1){
                    candidates.remove(letter.charAt(0));
                }
                antepenultimate = penultimate;
                penultimate = letter;
            }
            i++;

        }
        if(candidates.isEmpty()){
            return response;
        }
        return candidates.entrySet().iterator().next().getKey();
    }
}