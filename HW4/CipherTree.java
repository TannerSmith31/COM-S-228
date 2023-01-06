package edu.iastate.cs228.hw4;

/**
 * This class creates a cipher tree that can decode the message.
 * @author Tanner Smith
 */
public class CipherTree {
	private TreeNode root;
	
	/**
	 * Creates a CipherTree from a decoder string
	 */
	public CipherTree(String encodingString) {
		root = new TreeNode('^', null);
		TreeNode curNode = root;	//keeps track of where things are
		for (int i = 1; i < encodingString.length(); i++) {	//starts at i=1 because the root node has already been made
			char curChar = encodingString.charAt(i);
			if (curChar == '^') {
				if(curNode.leftChild == null) {
					curNode.leftChild = new TreeNode('^', curNode);
					curNode = curNode.leftChild;
				} else if (curNode.leftChild != null) {
					curNode.rightChild = new TreeNode('^', curNode);
					curNode = curNode.rightChild;
				}
			}else {	//curChar is a leaf
				if (curNode.leftChild == null) {
					curNode.leftChild = new TreeNode(curChar, curNode);
					//no need to go to the node as it is a leaf
				}
				else if (curNode.leftChild != null) {
					curNode.rightChild = new TreeNode(curChar, curNode);
					while (curNode.rightChild != null && curNode != root) {
						curNode = curNode.parent;
					}
				}
			}
		}
	}
	
	
	//method to print characters and their binary codes
	/**
	 * Method that prints out each of the plaintext characters in the decoder tree and
	 * their corresponding ciphertext (string of 1's and 0's)
	 * @param tree - the decoding tree for the cipher text
	 * @param code - not used?
	 */
	public static void printCodes(CipherTree tree, String code){ //I never used "String Code". I think it is for the recursive solution
		System.out.println("Character  Code");
		System.out.println("-------------------------");
		StringBuilder curCode = new StringBuilder("");
		TreeNode curNode = tree.root;
		curNode.visited = true;
		char curPayload = '^';
		while (!(curNode == tree.root.rightChild && curNode.rightChild.visited == true)) {
			while (curNode.leftChild != null && curNode.leftChild.visited == false) {
				curNode = curNode.leftChild;
				curCode.append('0');
				curNode.visited = true;
			}
			curPayload = curNode.payload;
			
			if (curPayload == '\n') {	//account for the newline character so it doesn't mess up formatting
				System.out.println("   " + "/n" + "      " + curCode);
			} else {
				System.out.println("   " + curPayload + "       " + curCode);
			}

			while ((curNode.rightChild == null || curNode.rightChild.visited == true) && curNode != tree.root) {
				curNode = curNode.parent;
				curCode.deleteCharAt(curCode.length()-1); //remove last character of curCode
			}
			curNode = curNode.rightChild;
			curNode.visited = true;
			curCode.append('1');
			curPayload = curNode.payload;
			
		}
		
		
	}
	
	
	/**
	 * decodes the 'cipherText' using the given decoder tree and 
	 * prints out the decoded message to the console 
	 * @param codes: the cipher tree decoder used to decode the message
	 * @param msg: the long string of 1's and 0's to be decoded
	 */
	public void decode(CipherTree decoder, String cipherText) {
		int curIndex = 0;
		TreeNode curNode;
		while (curIndex < cipherText.length()) {
			curNode = decoder.root;	//starts you back at the root of the tree every time you find a character
			while (curNode.payload == '^') {	//while not at a leaf
				if (cipherText.charAt(curIndex) == '0') {	//if there is a 0, travel left
					curNode = curNode.leftChild;
					curIndex++;
				}else if (cipherText.charAt(curIndex) == '1') {	//if there is a 1, travel right
					curNode = curNode.rightChild;
					curIndex++;
				}
			}
			System.out.print(curNode.payload);	//whenever you hit a leaf, print what's in it
		}
	}
	
	/**
	 * TreeNodes are what comprise the cipher tree. They act in a similar way to the nodes of
	 * a linked list in that they hold a piece of information and links to other nodes that.
	 */
	private class TreeNode{
		char payload;		//character stored in this node
		boolean visited;	//whether this node has been visited before (for printing the characters & their codes)
		TreeNode parent;	//link to this nodes parent node
		TreeNode rightChild;//link to this nodes right child node
		TreeNode leftChild;	//link to this nodes left child node
		
		/**
		 * Constructs a new tree node with a specified payload and parent. Initializes its children
		 * to NULL to be updated later.
		 * @param payload: character to be stored in this node
		 * @param parent: parent
		 */
		public TreeNode(char payload, TreeNode parent) {
			this.payload = payload;
			visited = false;
			this.parent = parent;
			rightChild = null;
			leftChild = null;
			
		}
		
		
	}
}
