// ****************************************************************
// CountLetters.java
//
// Reads a words from the standard input and prints the number of
// occurrences of each letter in that word.
//
// ****************************************************************

import java.util.Scanner;

class InvalidCharException extends Exception {
    public InvalidCharException(String message) {
        super(message);
    }
}

public class CountLetters{
	static void validateInput(char X) throws InvalidCharException {
	        if ((int)X < 65 || (int)X >90) {
	            throw new InvalidCharException("Bukan Alfabet:" + X);
	        }
	    }


	public static void main(String[] args){
		int[] counts = new int[26];
		Scanner scan = new Scanner(System.in);
		
		//get word from user
		System.out.print("Enter a single word (letters only, please): ");
		String word = scan.nextLine();
		
		//convert to all upper case
		word = word.toUpperCase();
		
		//count frequency of each letter in string
		for (int i=0; i < word.length(); i++){
			try{
				validateInput(word.charAt(i));
				counts[word.charAt(i)-'A']++;
			}catch (InvalidCharException e){
				System.out.println("Exception: " + e.getMessage());
			}
		}
		
		//print frequencies
		System.out.println();
		for (int i=0; i < counts.length; i++){
			if (counts [i] != 0){
				System.out.println((char)(i +'A') + ": " + counts[i]);
			}
		}
		scan.close();
	}	
}

