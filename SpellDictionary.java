import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class SpellDictionary{
    //create a dictionary to store the words
    public static String filename;
    public static HashSet<String> validWords;

    /**
     * Contructor that accepts filename and reads in the file to a list of "validWords"
     * @param filename
     */
    public SpellDictionary(String filename){
        this.filename = filename;
        this.validWords = new HashSet<>(); //create a new hash set 
        Scanner words = null;

        //opens the file and stores in a variable, words
        try{
            words = new Scanner(new File(this.filename));
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            System.exit(-1);
        }

        //adds the words in words to the hashset
        while (words.hasNext()){
            String newWord = words.nextLine();     
            this.validWords.add(newWord.replaceAll("\\p{Punct}", "").toLowerCase()); //add new word to hashset
        }}

    /**
     * Prints out the valid words stored in the hashset
     */
    public void printDictionary(){
        //prints out the words in the hashset
        for (String elem: SpellDictionary.validWords){
            System.out.println(elem);
        }
    }

    /**
     * Checks if a word, query, is valid
     * @param query
     * @return true or false, indicating whether the input, query, is contained in the hashset
     */
    public static boolean containsWord(String query){
        return validWords.contains(query.toLowerCase());
    }

    /**
   *  @param query the word to check
   *  @return a list of all valid words that are one edit away from the query
   */
    public static ArrayList<String> nearMisses(String query){
        ArrayList<String> missArray = new ArrayList<>();
        if (!(containsWord(query))){
            HashSet<String> nearMisses = new HashSet<>();
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray(); //stores the letters of the alphabet in an array
            query = query.toLowerCase();

            //Deletions: 
                //Iterate through len(query) times
                for (int i=0; i<query.length(); i++){
                    //delete ith word and check if in dictionary, if yess, add to the hashset of nearmisses
                    String replaced = query.substring(0, i) + query.substring(i+1);
                    // String replaced = replace(query.get(i), ' ')
                    if (containsWord(replaced)){
                        nearMisses.add(replaced); 
                    }}


            //Insertions: 
                //goes through each position in the word, query
                for (int i =0; i<=query.length(); i++){
                    //goes through each alphabet
                    for (int j=0; j<alphabet.length; j++){
                        //adds the alphabet to the position i in the query
                        String replaced; //defines the variable, strin
                        if (i != query.length()){
                            replaced = query.substring(0, i) + alphabet[j] + query.substring(i, query.length());
                        //special handling for inserting after the word
                        }else{
                            replaced = query + alphabet[j];
                            //System.out.println(alphabet[i] + " " + replaced + " " + containsWord(replaced));
                        }
                        //checks if a word is in the list of validwords and adds to nearMisses if yes
                        if (containsWord(replaced)){
                            nearMisses.add(replaced);
                        }
                }}

                //**Substitutions**: Replace one character with another. 
                //for every letter in query
                for (int i = 0; i<query.length(); i++){
                    //iterate through 26 alphabets
                    for (int j = 0; j<alphabet.length; j++){
                        // System.out.println(query.substring(i, i+1));
                        String replaced = query.substring(0, i) + alphabet[j] + query.substring(i+1, query.length()); //replace ith letter with jth alphabet
                        //check if new word in dictionary, if yes, add to arraylist}
                        if (containsWord(replaced)){
                            nearMisses.add(replaced);
                        }
                    }}

                //**Transpositions**: Swap two adjacent characters. 
                //for every ith character,
                for (int i =0; i<query.length()-1;i++){
                    String replaced;
                    String first = query.substring(i, i+1);
                    String last = query.substring(i+1, i+2);
                    if (i<query.length()-2){
                        //swap with the ith + 1 character if i is less than len(query)-1
                        replaced = query.substring(0, i) + last + first + query.substring(i+2, query.length());
                    }
                    else{
                        replaced = query.substring(0, i) + last + first;
                    }
                    if (containsWord(replaced)){
                        nearMisses.add(replaced);   
                    }}

                //**Splits**: 
                //iterate through chars in the word
                for (int i = 1; i < query.length()-2; i++){
                    //split the word into two words and add a space in between
                    String replaced;
                    replaced = query.substring(0,i+1) + " " + query.substring(i+1);
                    // System.out.println("replaced in split " + replaced);
                    //check if the word is contained in the list of valid words
                    if (containsWord(replaced)){
                        // System.out.println(replaced + " found");
                        nearMisses.add(replaced);
                    }}


                //convert hashset to an array
                for (String elem: nearMisses){
                    missArray.add(elem);
                }}
            
            return missArray;           
        }
        
        
    

    public static void main(String[] args) {
        SpellDictionary testDict = new SpellDictionary("words.txt"); 
        System.out.println(testDict.nearMisses("whitechristmas"));
        //System.out.println(testDict.nearMisses("nigeria")); 
        //test for deletions
        System.out.println(testDict.nearMisses("cattqle").contains("cattle")); //True
        System.out.println(testDict.nearMisses("Nigeria").isEmpty());//True
        //test for insertions
        System.out.println(testDict.nearMisses("nigeri").contains("nigeria")); //True
        System.out.println(testDict.nearMisses("ol").contains("aol")); //True
        //test for substitutions
        System.out.println(testDict.nearMisses("Abrayam").contains("abraham")); //True
        System.out.println(testDict.nearMisses("Aboja").contains("abuja")); //True
        //test for transpositions
        System.out.println(testDict.nearMisses("Centgirade").contains("centigrade")); //True
        System.out.println(testDict.nearMisses("Charmisn").contains("charmins")); //True
        //test for splits
        System.out.println(testDict.nearMisses("whitechristmas").contains("white christmas")); //False
        System.out.println(testDict.nearMisses("cattel").contains("cat tel")); //False

    }
}
