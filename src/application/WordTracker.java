package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import implementations.BSTree;

public class WordTracker 
{
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
