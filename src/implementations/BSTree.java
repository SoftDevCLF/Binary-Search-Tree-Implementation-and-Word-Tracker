package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.BSTreeADT;
import utilities.Iterator;

/**
 * Implementation of a Binary Search Tree (BST) with nodes of type {@link BSTreeNode}.
 * This class supports adding, searching, removing minimum/maximum elements,
 * and iterating over the tree in in-order, pre-order, and post-order traversals.
 *
 * @param <E> the type of elements stored in this tree; must implement Comparable
 */
public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable
{
	// Serial version UID for serialization
	private static final long serialVersionUID = 1L;
	
	/** Root node of the BST */
	private BSTreeNode<E> root;
	
	/** Number of elements in the tree */
	private int size;

	/**
     * Constructs an empty BSTree.
     * 
     */
	public BSTree()
	{
		this.root = null;
		this.size = 0;
	}

    /**
     * Constructs a BSTree with a single root node containing the specified element.
     *
     * @param element the element to store at the root
     */
	public BSTree(E element)
	{
		this.root = new BSTreeNode<E>(element, null, null);
		this.size = 1;
	}


	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException
	{
		if (root == null)
		{
			throw new NullPointerException("Tree is empty.");
		}
		return root;
	}

	@Override
	public int getHeight()
	{
		return heightOf(root);
	}

    /**
     * Recursively calculates  the height of a node.
     *
     * @param node the node to calculate the height for
     * @return height of the node (-1 if null)
     */
	private int heightOf(BSTreeNode<E> node)
	{
		if (node == null)
		{
			return 0;
		} else
		{
			int leftHeight = heightOf(node.getLeft());
			int rightHeight = heightOf(node.getRight());
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}

	@Override
	public int size()
	{
		if (root == null)
		{
			return 0;
		}
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public void clear()
	{
		root = null;
		size = 0;

	}

	@Override
	public boolean contains(E entry) throws NullPointerException
	{
		if (entry == null)
		{
			throw new NullPointerException("Entry cannot be null.");
		}
		return search(entry) != null;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException
	{
		if (entry == null)
		{
			throw new NullPointerException("Cannot search a null entry.");
		}

		BSTreeNode<E> currentNode = root;
		while (currentNode != null)
		{
			int comparatorResult = entry.compareTo(currentNode.getElement());
			if (comparatorResult == 0)
			{
				return currentNode;
			} else if (comparatorResult < 0)
			{
				currentNode = currentNode.getLeft();
			} else
			{
				currentNode = currentNode.getRight();
			}
		}
		return null;
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException
	{
		if (newEntry == null)
		{
			throw new NullPointerException("Cannot add a null entry.");
		}

		if (root == null)
		{
			root = new BSTreeNode<E>(newEntry, null, null);
			size++;
			return true;
		}

		BSTreeNode<E> currentNode = root;
		BSTreeNode<E> parentNode = null;

		while (currentNode != null)
		{
			parentNode = currentNode;
			int comparatorResult = newEntry.compareTo(currentNode.getElement());
			if (comparatorResult == 0)
			{
				// Duplicate entry found; do not add
				return false;
				
			} else if (comparatorResult < 0)
			{
				currentNode = currentNode.getLeft();
			} else
			{
				currentNode = currentNode.getRight();
			}
		}

		BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry, null, null);
		if (newEntry.compareTo(parentNode.getElement()) < 0)
		{
			parentNode.setLeft(newNode);
		} else
		{
			parentNode.setRight(newNode);
		}
		size++;
		return true;
	}

	@Override
	public BSTreeNode<E> removeMin()
	{
		if (root == null)
		{
			return null;
		}

		BSTreeNode<E> currentNode = root;
		BSTreeNode<E> parentNode = null;

		while (currentNode.getLeft() != null)
		{
			parentNode = currentNode;
			currentNode = currentNode.getLeft();
		}

		if (parentNode == null)
		{
			root = currentNode.getRight();
		} else
		{
			parentNode.setLeft(currentNode.getRight());
		}

		currentNode.setLeft(null);
		currentNode.setRight(null);
		size--;

		return currentNode;
	}

	@Override
	public BSTreeNode<E> removeMax()
	{
		if (root == null)
		{
			return null;
		}

		BSTreeNode<E> currentNode = root;
		BSTreeNode<E> parentNode = null;

		while (currentNode.getRight() != null)
		{
			parentNode = currentNode;
			currentNode = currentNode.getRight();
		}

		if (parentNode == null)
		{
			root = currentNode.getLeft();
		} else
		{
			parentNode.setRight(currentNode.getLeft());
		}
		currentNode.setLeft(null);
		currentNode.setRight(null);
		size--;
		return currentNode;
	}

	@Override
	public Iterator<E> inorderIterator()
	{
		int numberOfNodes = size(); 
		@SuppressWarnings("unchecked")
		E[] elements = (E[]) new Comparable[numberOfNodes]; 

		fillInOrder(root, elements, 0);

		// Index for iteration
		return new Iterator<E>()
		{
			private int index = 0;

			@Override
			public boolean hasNext()
			{
				return index < elements.length;
			}

			@Override
			public E next() throws NoSuchElementException
			{
				if (!hasNext())
				{
					throw new NoSuchElementException();
				}
				return elements[index++];
			}
		};
	}

    /**
     * Fills an array with elements from the subtree rooted at a node in in-order.
     *
     * @param node current node
     * @param elements array to store elements
     * @param index current index
     * @return next available index in array
     */
	private int fillInOrder(BSTreeNode<E> node, E[] elements, int index)
	{
		if (node != null)
		{
			//Handle left subtree recursively, so that left children are added first
			index = fillInOrder(node.getLeft(), elements, index);
			//Visit node
			elements[index++] = node.getElement();
			//Handle right subtree recursively, so that right children are added last
			index = fillInOrder(node.getRight(), elements, index);
		}
		return index;
	}

	@Override
	public Iterator<E> preorderIterator()
	{
		int numberOfNodes = size(); 
		@SuppressWarnings("unchecked")
		E[] elements = (E[]) new Comparable[numberOfNodes];

		fillPreOrder(root, elements, 0);

		// Index for iteration
		return new Iterator<E>()
		{
			private int index = 0;

			@Override
			public boolean hasNext()
			{
				return index < elements.length;
			}

			@Override
			public E next() throws NoSuchElementException
			{
				if (!hasNext())
				{
					throw new NoSuchElementException();
				}
				return elements[index++];
			}
		};

	}

    /**
     * Fills an array with elements from the subtree rooted at a node in pre-order.
     *
     * @param node current node
     * @param elements array to store elements
     * @param index current index
     * @return next available index
     */
	private int fillPreOrder(BSTreeNode<E> node, E[] elements, int index)
	{
		if (node != null)
		{
			//Visit node
			elements[index++] = node.getElement();
			//Handle left subtree recursively, in this way left children are added next
			index = fillPreOrder(node.getLeft(), elements, index);
			//Recurse right subtree, so that right children are added last
			index = fillPreOrder(node.getRight(), elements, index);
		}
		return index;
	}

	@Override
	public Iterator<E> postorderIterator()
	{
		int numberOfNodes = size(); 
		@SuppressWarnings("unchecked")
		E[] elements = (E[]) new Comparable[numberOfNodes]; 

		fillPostOrder(root, elements, 0);

		// Index for iteration
		return new Iterator<E>()
		{
			private int index = 0;

			@Override
			public boolean hasNext()
			{
				return index < elements.length;
			}

			@Override
			public E next() throws NoSuchElementException
			{
				if (!hasNext())
				{
					throw new NoSuchElementException();
				}
				
				return elements[index++];
			}
		};
	}

    /**
     * Fills an array with elements from the subtree rooted at a node in post-order.
     *
     * @param node current node
     * @param elements array to store elements
     * @param index current index
     * @return next available index
     */
	private int fillPostOrder(BSTreeNode<E> node, E[] elements, int index)
	{
		if (node != null)
		{
			//Handle left subtree recursively, so that left children are added first
			index = fillPostOrder(node.getLeft(), elements, index);
			//Handle right subtree recursively, so that right children are added next
			index = fillPostOrder(node.getRight(), elements, index);
			//Visit node last
			elements[index++] = node.getElement();
		}
		return index;
	}
}
