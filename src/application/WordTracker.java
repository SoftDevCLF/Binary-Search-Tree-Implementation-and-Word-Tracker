package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import implementations.BSTree;
import implementations.BSTreeNode;
import application.Word;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


public class WordTracker 
{
	public static void main(String[] args) throws IOException {
		
		//Load existing BST or create a new one
		BSTree<Word> tree = loadRepository();
		
		//Process input file and update BST
		processFile(tree, inputFile);
		
		//Generate report based on the option
		String report = generateReport(tree, option);
		
		//Output to console
		if (outputFile != null) {
			try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
				pw.print(report);
			}
		} else {
			System.out.print(report);
		}
	}
	
		/**
	     * Process file builds a BST of words.
	     * For each word it remembers where it appeared (file and line number) 
	     */
		public static void processFile(BSTree<Word> tree, String filename) throws IOException {
			//Construct a BST with all words from a text file (supplied at command line)
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		        //Record line numbers on which these words were used
		    	String line;
		        int lineNumber = 1;
		        while ((line = br.readLine()) != null) {
		            line = line.toLowerCase();
	
		            //Replace non-letters with spaces
		            StringBuilder cleaned = new StringBuilder();
		            for (char c : line.toCharArray()) {
		                if (Character.isLetter(c) || Character.isWhitespace(c)) {
		                    cleaned.append(c);
		                } else {
		                    cleaned.append(' ');
		                }
		            }
		            // Split file into words
		            String[] words = cleaned.toString().trim().split("\\s+");
		            for (String w : words) {
		                if (w.isEmpty()) continue;
		                Word temp = new Word(w);
		                //Store the line numbers with the file names, associated with nodes in the tree
		                BSTreeNode<Word> node = tree.search(temp); //search for existing word
		                if (node != null) {
		                    Word existing = node.getElement();
		                    existing.addOccurrence(filename, lineNumber);
		                } else {
		                    temp.addOccurrence(filename, lineNumber);
		                    tree.add(temp); //add new word to the Binary search tree
		                }
		            }
		            lineNumber++;
		        }
		    }
		}
		
	/**
	 * Generate Report formats and outputs the results 
	 * based on the command‑line 
	 */
	public static String generateReport(BSTree<Word> tree, String option) {
	    // Anna will implement reporting logic
	    return "";
	}
	
    /**
     * Loads the previously saved BST from repository.ser if it exists.
     * If the file does not exist or cannot be loaded, an empty BST is returned.
     */
    public static BSTree<Word> loadRepository() 
    {
        File file = new File("repository.ser");

        if (!file.exists()) 
        {
            return new BSTree<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) 
        {
            return (BSTree<Word>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) 
        {
            System.err.println("Could not load repository.ser: " + e.getMessage());
            return new BSTree<>();
        }
    }

    /**
     * Saves the BST to repository.ser using Java serialization.
     */
    public static void saveRepository(BSTree<Word> tree) 
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("repository.ser"))) 
        {
            oos.writeObject(tree);
        }
        catch (IOException e) 
        {
            System.err.println("Error saving repository.ser: " + e.getMessage());
        }
    }
}
