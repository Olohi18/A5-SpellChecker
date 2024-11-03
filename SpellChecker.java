// create a new SpellDictionary object

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;

public class SpellChecker {

    /**
     * Checks the list of words passed into the command line and returns the appropriate suggestions as necessary
     * @param wordArray
     */
    public static void listCheck(String[] wordArray){
        //iterates through the array of words passed in
        for (String elem: wordArray){
            //prints out the appropriate approval message if ditionary contains word
            if (SpellDictionary.containsWord(elem)){
                System.out.println(elem + " is spelled correctly.");
                System.out.println();
            //prints out suggestions if not
            }else{
                String suggestions = "";
                ArrayList<String> nearMisses = new ArrayList<>();
                nearMisses = SpellDictionary.nearMisses(elem);
                for (String m: nearMisses){
                    suggestions += " " + m;
                }
                if (nearMisses.isEmpty()){
                    System.out.println("Not found!! Suggestions not available at this moment");
                    return;
                }
                System.out.println(elem + " not found");
                System.out.println("Suggestions are: " + suggestions);
                System.out.println();
            }
        }}

    /**
     * Checks the content of the file for errors and returns helpful indicators when necessary
     * @param contentArray
     */
    public static void fileCheck(ArrayList<String> contentArray){
        //create a hashset to keep track of found mispelled words 
        HashSet<String> foundMispellings = new HashSet<String>();
        //iterate through the array, contentArray
        for (String elem: contentArray){
            //if word is not valid
            if (!(SpellDictionary.containsWord(elem)) && !(foundMispellings.contains(elem))){
                //offer suggestions and print an appropriate message
                String suggestions = "";
                ArrayList<String> nearMisses = new ArrayList<>();
                nearMisses = SpellDictionary.nearMisses(elem);
                //returns a specific message if no suggestions are available
                if (nearMisses.isEmpty()){
                    System.out.println("Not found!! Suggestions not available at this moment");
                    return;
                }
                //adds the element to the hashset, foundMispellings
                foundMispellings.add(elem);
                for (String m: nearMisses){
                    suggestions += " " + m; //adds each nearMiss to suggestions for printing
                }
                System.out.println("Not found: " + elem);
                System.out.println("Suggestions: " + suggestions);
                System.out.println();
            }}    

    }

   
    //main method to interact directly with the command line
    public static void main(String[] args) {
        SpellDictionary testDict = new SpellDictionary("words.txt"); //read in valid words into memory
        if (args.length != 0){
            String[] wordArray = new String[args.length];
            //iterate through every word and store in array
            for (int i = 0; i<args.length; i++){
                wordArray[i] = args[i];
            }
            //pass array into list check method
            listCheck(wordArray);

        }

        if (args.length == 0){
            //read in file into a scanner
            Scanner scanner = new Scanner(System.in);
            //create a new array list
            ArrayList<String> contentArray = new ArrayList<String>();
            //iterate through the file and add each line to the string builder
            while (scanner.hasNext()){
                String wordToAdd = scanner.next().replaceAll("[^a-zA-z0-9]", "");
                contentArray.add(wordToAdd.toLowerCase());
            }
            scanner.close();
            fileCheck(contentArray); //call word processor method
        }
        
    }
}
