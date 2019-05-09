import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

class Dictionary {

    private HashSet<String> words = new HashSet<>();
    private ArrayList<String> secrets = new ArrayList<>();

    // constructor: read words from a file
    public Dictionary(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            String str = "";
            while (sc.hasNextLine()) {
                str = sc.nextLine();
                words.add(str);
                if (isLegalSecretWord(str))
                    secrets.add(str);
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(1);
        }

    }

    // is word in the dictionary?
    public boolean validWord(String word) {
        if (words.contains(word))
            return true;
        else
            return false;
    }

    // get a legal secret word from the dictionary
    public String getLegalSecretWord() {
        return secrets.get(new Random().nextInt(secrets.size()));
    }

    // is this word a legal secret word?
    private boolean isLegalSecretWord(String word) {
        char[] car = word.toCharArray();
        for (int i = 0; i < car.length; i++)
            for (int j = i + 1; j < car.length; j++)
                if (car[i] == car[j])
                    return false;
        return true;

    }
}