package trie;

public interface Trie {

	/**
	 * Add a word to the trie
	 * 
	 * @param word the word to insert into the trie
	 */
	void insert(String word);

	/**
	 * Search the word specified
	 * 
	 * @param word the word to search inside the trie
	 * @return boolean indicating the word exists or not
	 */
	boolean search(String word);

	/**
	 * Search if there are string which prefix is the prefix specified
	 * 
	 * @param prefix
	 * @return boolean if found or not a word which prefix is prefix of
	 */
	boolean startsWith(String prefix);
}
