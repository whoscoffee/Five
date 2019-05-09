import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

//this is an ai, for a word guessing game
// by jake
class Five {

    Dictionary dictionary;
    // exactly what it sounds like
    ArrayList<Character> alphabet = new ArrayList<>();
    // so ai can compare and what not
    ArrayList<String> guessedWords = new ArrayList<>();
    ArrayList<ArrayList<Character>> knownWords = new ArrayList<>();
    ArrayList<Integer[]> knownValues = new ArrayList<>();
    ArrayList<Character> knownLetters = new ArrayList<>();
    // misc
    int numguesses;
    boolean cheated;
    // secret word being guessed for
    String secret, cheatcode = "xxxxx";
    // secret word in a char array
    ArrayList<Character> car = new ArrayList<>();

    // constructor
    public Five() {
        start("Five-words.txt");
    }

    // constructor 2
    public Five(String filename) {
        start(filename);
    }

    // sends file to Dictionary
    // sets secret word
    public void start(String filename) {
        dictionary = new Dictionary(filename);
        secret = dictionary.getLegalSecretWord();
        // sets an array of the secret word
        for (char c : secret.toCharArray())
            car.add(c);
        play();
    }

    // this is some durty looking code
    // but its my ai, and the game loop, so its kinda important
    // altho NEEDS SEPERATION!
    public void play() {
        char c = 'a';
        // a - z
        for (int i = 0; i < 26; i++)
            alphabet.add(c++);
        // start up stuff
        Scanner sc = new Scanner(System.in);
        System.out.println("Five!");
        boolean istrue = true;
        String guess = "";
        // until ai is correct
        // also the game loop
        finishline: while (istrue) {
            // start up stuff
            System.out.print("Your Guess? ");
            boolean hasGuessed = false;
            // while ai hasnt made guess
            outer: while (hasGuessed == false) {
                // makes random guess
                guess = dictionary.getLegalSecretWord();
                // compares with alphabet
                for (char d : guess.toCharArray()) {
                    if (alphabet.contains(d) == false)
                        continue outer;
                    if (alphabet.contains(d) == false && knownLetters.size() < new Random().nextInt(4))
                        continue outer;
                }
                // to make sure it doesnt guess a word twice
                if (guessedWords.contains(guess) == false)
                    hasGuessed = true;
            }
            // add to list
            guessedWords.add(guess);
            // game loop start up stuff
            ArrayList<Character> ahh = new ArrayList<>();
            for (char e : guess.toCharArray())
                ahh.add(e);
            knownWords.add(ahh);
            Integer[] ayy = { countMatchingLetters(guess), countInPlaceLetters(guess) };
            knownValues.add(ayy);
            System.out.println(guess);
            // hackers wont even know
            if (guess.equals(cheatcode)) {
                System.out.println("Secret Word is: " + secret);
                cheated = true;
            } else if (dictionary.validWord(guess)) {
                // if is correct guess
                if (guess.equals(secret)) {
                    istrue = false;
                    break finishline;
                }
                // else
                System.out.println("Matching: " + countMatchingLetters(guess));
                System.out.println("In-Place: " + countInPlaceLetters(guess));
            } else
                System.out.println("I don't know that word.");
            numguesses++;
            // pyrimid Ai kick ass
            // it kinda does alot
            // so i based this off of how i play the game
            // i have an alphabet
            // i compare my previous guesses, matchedletters, and in-placeletters
            // and i remove what i can in the ways i know
            // altho pseudocode is here for real the explaination
            if (countMatchingLetters(guess) == 0 && countInPlaceLetters(guess) == 0) {
                for (char d : guess.toCharArray())
                    if (alphabet.contains(d)) {
                        alphabet.remove(alphabet.indexOf(d));
                        for (int i = 0; i < knownWords.size(); i++)
                            if (knownWords.get(i).size() > 1)
                                for (int j = 0; j < alphabet.size(); j++) {
                                    for (int k = 0; k < knownWords.get(i).size(); k++)
                                        if (alphabet.contains(knownWords.get(i).get(k)) == false
                                                && knownWords.size() > 1)
                                            knownWords.get(i).remove(alphabet.get(j));
                                }
                            else if (knownWords.get(i).size() == 1) {
                                knownLetters.add(knownWords.get(i).get(0));
                            }
                    }
            }
        }
        // when that mess is done, u will be here where u get bitcxhes
        if (cheated != true)
            System.out.printf("Correct! You got it in %d guesses.", ++numguesses);
        else {
            System.out.printf("Correct! You got it in %d guesses,%nbut only by cheating.%n", ++numguesses);
        }

    }

    // return # of matching letters secret/guess
    private int countMatchingLetters(String guess) {
        int count = 0;
        ArrayList<Character> dont = new ArrayList<>();
        for (char c : guess.toCharArray())
            if (car.contains(c) && dont.contains(c) != true) {
                dont.add(c);
                count++;
            }
        return count;
    }

    // return # of in-place letters secret/guess
    private int countInPlaceLetters(String guess) {
        int count = 0;
        for (int i = 0; i < guess.length(); i++)
            if (secret.charAt(i) == guess.charAt(i))
                count++;
        return count;
    }

    // main program, required, minimal code
    public static void main(String[] args) {
        try {
            new Five(args[0]);
        } catch (Exception e) {
            new Five();
        }
    }
}