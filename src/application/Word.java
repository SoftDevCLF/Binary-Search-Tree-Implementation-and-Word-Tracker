package application;

import java.io.Serializable;
import java.util.*;

public class Word implements Comparable<Word>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String text;
	//List of numbers where the word will appear
	private Map<String, List<Integer>> occurrences = new HashMap<>();
    //Frequency to count total occurrences across all files
	private int frequency;
	
	
    //Constructor to initialize word as string
	public Word(String text) {
		this.text = text.toLowerCase();
		this.frequency = 0;
		this.occurrences = new HashMap<>();	
	}

   //Methods
	public void addOccurrence(String filename, int lineNumber) {
		if (!occurrences.containsKey(filename)) {
			occurrences.put(filename, new ArrayList<>());
		}
		occurrences.get(filename).add(lineNumber);
		frequency++;
	}
	
	public String getText() {
		return text;
	}

    public Map<String, List<Integer>> getOccurrences() {
    	return occurrences;
    }
    
    public Set<String> getFiles() {
    	return occurrences.keySet();
    }

    public int getFrequency() {
    	return frequency;
    }

    //Compares to another words text alphabetically
	@Override
	public int compareTo(Word o)
	{
		// TODO Auto-generated method stub
		return this.text.compareTo(o.text);
	}
    

    
}