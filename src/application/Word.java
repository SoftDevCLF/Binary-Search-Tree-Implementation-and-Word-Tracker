package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word implements Comparable<Word>, Serializable 
{
    private static final long serialVersionUID = 1L;

    private String text;

    // Store filenames and the line numbers where the word appears.
    // filename → list of line numbers
    private Map<String, List<Integer>> occurrences;

    // Also track the total frequency of the word
    private int frequency;

    public Word(String text) 
    {
        this.text = text.toLowerCase();
        this.occurrences = new HashMap<>();
        this.frequency = 0;
    }

    public String getText() 
    {
        return text;
    }

    /**
     * Add an occurrence of this word (filename + line number).
     * 
     */
    public void addOccurrence(String filename, int lineNumber) 
    {
        occurrences.putIfAbsent(filename, new ArrayList<>());
        occurrences.get(filename).add(lineNumber);
        frequency++;
    }

    public int getFrequency() 
    {
        return frequency;
    }

    @Override
    public int compareTo(Word other) 
    {
        return this.text.compareTo(other.text);
    }

    // -pf
    public String toPrintFiles() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(text).append(" : ");

        for (String file : occurrences.keySet()) 
        {
            sb.append(file).append(" ");
        }

        return sb.toString().trim();
    }

    // -pl
    public String toPrintFilesAndLines() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(text).append(" : ");

        for (String file : occurrences.keySet()) 
        {
            sb.append(file).append(" lines ");

            for (int line : occurrences.get(file)) 
            {
                sb.append(line).append(", ");
            }

            sb.append("  ");
        }

        return sb.toString().trim();
    }

    // -po
    public String toPrintFilesLinesAndFrequency() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(text)
          .append(" (")
          .append(frequency)
          .append(") : ");

        for (String file : occurrences.keySet()) 
        {
            sb.append(file).append(" lines ");

            for (int line : occurrences.get(file)) 
            {
                sb.append(line).append(", ");
            }

            sb.append("  ");
        }

        return sb.toString().trim();
    }
}
