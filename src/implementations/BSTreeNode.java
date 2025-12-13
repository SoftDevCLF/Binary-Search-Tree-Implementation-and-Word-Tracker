package implementations;

import java.io.Serializable;

/**
 * Represents a node in a Binary Search Tree (BST).
 * Each node stores an element and references to its left and right children.
 *
 * @param <E> the type of element stored in this node
 * 
 */

public class BSTreeNode<E> implements Serializable
{
	// Required UID for serialization compatibility
	private static final long serialVersionUID = 1L;
	
	/** The element stored in this node */
	private E element;
	
	/** Reference to the left / right child node */
	private BSTreeNode<E> left, right;
	
    /**
     * Creates a new BSTreeNode with the specified element and children.
     *
     * @param element the element to store in this node
     * @param left reference to the left child node (can be null)
     * @param right reference to the right child node (can be null)
     */
	public BSTreeNode(E element, BSTreeNode<E> left, BSTreeNode<E> right)
	{
		super();
		this.element = element;
		this.left = left;
		this.right = right;
	}
	
	 /**
     * Returns the element stored in this node.
     *
     * @return element the element
     */
	public E getElement()
	{
		return element;
	}
	
	/**
     * Sets the element stored in this node.
     *
     * @param element the element to set
     */
	public void setElement(E element)
	{
		this.element = element;
	}
	
	/**
     * Returns the left child of this node.
     *
     * @return left the left child
     */
	public BSTreeNode<E> getLeft()
	{
		return left;
	}
	
    /**
     * Sets the left child of this node.
     *
     * @param left the left child node to set
     */
	public void setLeft(BSTreeNode<E> left)
	{
		this.left = left;
	}
	
    /**
     * Returns the right child of this node.
     *
     * @return right the right child
     */
	public BSTreeNode<E> getRight()
	{
		return right;
	}
	
    /**
     * Sets the right child of this node.
     *
     * @param right the right child node to set
     */
	public void setRight(BSTreeNode<E> right)
	{
		this.right = right;
	}
	
	
	
}
