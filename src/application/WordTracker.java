package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import application.Word;
import implementations.BSTree;
import implementations.BSTreeNode;


public class WordTracker 
{
	public static void main(String[] args) throws IOException {
		
		if (args.length < 2) {
			System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf|-pl|-po [-f<output.txt>]");
			return;
		}
		
		String inputFile = args[0];
		String option = args[1];
		String outputFile = null;
		
		
		if (args.length == 3 && args[2].startsWith("-f")) {
			outputFile = args[2].substring(2);
		}
		
		//Load existing BST or create a new one
		BSTree<Word> tree = loadRepository();
		
		//Process input file and update BST
		processFile(tree, inputFile);
		
		// Save updated repository
		saveRepository(tree);
		
		//Generate report based on the option
		String report = generateReport(tree, option);
		
		//Output to console
		if (outputFile != null) {
			try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
				pw.print(report);
			}
			System.out.println("Exported to " + outputFile);
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
	    
		
		StringBuilder sb = new StringBuilder();
		utilities.Iterator<Word> it = tree.inorderIterator();
		
		sb.append("Displaying").append(option).append(" format\n\n");
		
		while (it.hasNext()) {
			Word w = it.next();
			sb.append("Key: ").append(w.getText());
			
			// -pf files only
			if (option.equals("-pf")) {
				sb.append(" found in file(s): ");
				sb.append(String.join(", ", w.getFiles()));
			}
			
			// -pl files + line numbers
			else if (option.equals("-pl")) {
				sb.append("\n");
				
				for (String file : w.getFiles()) {
					sb.append("  found in file: ")
	                  .append(file)
	                  .append(" on lines: ")
	                  .append(w.getOccurrences().get(file))
	                  .append("\n");
				}
			}
			
			// -po files + line numbers + frequency
			else if (option.equals("-po")) {
				sb.append("\n");
				
				for (String file : w.getFiles()) {
					sb.append("  found in file: ")
	                  .append(file)
	                  .append(" on lines: ")
	                  .append(w.getOccurrences().get(file))
	                  .append("\n");
				}
				
				sb.append("  total frequency: ")
	              .append(w.getFrequency())
	              .append("\n");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
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
