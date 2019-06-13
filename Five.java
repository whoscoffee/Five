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
    // inits
    ArrayList<Character> alphabet = new ArrayList<>();
    ArrayList<String> guessedWords = new ArrayList<>();
    ArrayList<ArrayList<Character>> knownWords = new ArrayList<>();
    ArrayList<Integer[]> knownValues = new ArrayList<>();
    ArrayList<Character> knownLetters = new ArrayList<>();
    // misc
    int numguesses;
    boolean cheated;
    String secret, cheatcode = "xxxxx";
    // secret word as a char array
    ArrayList<Character> car = new ArrayList<>();

    // constructor
    public Five() {
        start("Five-words.txt");
    }

    // constructor 2
    public Five(String filename) {
        start(filename);
    }

    // sends file to Dictionary and sets secret word
    public void start(String filename) {
        dictionary = new Dictionary(filename);
        secret = dictionary.getLegalSecretWord();
        // sets car
        for (char c : secret.toCharArray())
            car.add(c);
        play();
    }

    // this is my ai, and the game loop
    // NEEDS SEPERATION!
    public void play() {
        char c = 'a';
        // sets alphabet
        for (int i = 0; i < 26; i++)
            alphabet.add(c++);
        // start up stuff
        Scanner sc = new Scanner(System.in);
        System.out.println("Five!");
        boolean istrue = true;
        String guess = "";
        // ai guessing loop AND GAME LOOP!?
        finishline: while (istrue) {
            // more start up stuff
            System.out.print("Your Guess? ");
            boolean hasGuessed = false;
            // while ai hasnt made a final guess
            outer: while (hasGuessed == false) {
                // ai makes random guess
                guess = dictionary.getLegalSecretWord();
                // lesson: when playing the game, u can sort out characters that arent in the correct guess
                // so with that we have a list of chars non of which should be in our guess, and definently arent in the correct guess
                // so here the ai compares its guess with the aphabet characters it knows arent right
                // and if the alphabet contains any of the same characters it reguesses
                for (char d : guess.toCharArray()) {
                    if (alphabet.contains(d) == false)
                        continue outer;
                    if (alphabet.contains(d) == false && knownLetters.size() < new Random().nextInt(4))
                        continue outer;
                }
                // if already guessed, then it guesses again
                if (guessedWords.contains(guess) == false)
                    hasGuessed = true;
            }
            // stores guess to make sure it doesnt guess a word twice
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
            // if my guess had no inplace or matching letters
            if (countMatchingLetters(guess) == 0 && countInPlaceLetters(guess) == 0) {
                for (char d : guess.toCharArray())
                    //then removes all characters of guess from alphabet
                    if (alphabet.contains(d)) {
                        alphabet.remove(alphabet.indexOf(d));
                        // gets fun here
                        for (int i = 0; i < knownWords.size(); i++)
                            // if current known word has more than 2 characters
                            if (knownWords.get(i).size() > 1)
                                // for each character left in alphabet
                                for (int j = 0; j < alphabet.size(); j++) {
                                    // for the characters in current knowWord
                                    for (int k = 0; k < knownWords.get(i).size(); k++)
                                        // if alphabet doesnt contain current knownword character, then remove that char from known word
                                        if (alphabet.contains(knownWords.get(i).get(k)) == false
                                                && knownWords.size() > 1)
                                            knownWords.get(i).remove(alphabet.get(j));
                                }
                            // if known word is only 1 character long, then add that character to knowletters
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
    
    // BELOW: are some helper functions
    
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
