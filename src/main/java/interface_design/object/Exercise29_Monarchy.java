package interface_design.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interface_design.Monarchy;

/**
 * Create a monarchy class that represent the line of succession to the throne.
 * Implement the interface Monarchy.
 * 
 * 
 *
 * interface Monarchy{
 * 
 * void birth(String child, String parent);
 * 
 * void death(String name);
 * 
 * List<String> getOrderOfSuccession(); }
 * 
 * 
 * birth, represent the songs that a parent has, the order or call represent who
 * born first.
 * 
 * death, represent the dead of a person in this hierarchy.
 * 
 * getOrderOfSuccession, shows the line of succession, where the oldest child of
 * the king is the next in the line, and after him, his first child an so on.
 * 
 * Constraints:
 * 
 * 1- Can we implement helper classes/objects?, Yes, you can use any features
 * you see fit.
 * 
 * @author Jose
 *
 */
public class Exercise29_Monarchy implements Monarchy {

	private Map<String, Person> monarchy;
	private Person king;

	public Exercise29_Monarchy(String kingsName) {
		monarchy = new HashMap<>();
		king = new Person(kingsName);
		monarchy.put(kingsName, king);
	}

	/**
	 * Represent the songs that a parent has, the order or call represent who born
	 * first.
	 * 
	 * Time Complexity: O(1): extracting and inserting into the map is constant
	 * time.
	 * 
	 * Space Complexity: O(1): No extra data structure depending of the input
	 */
	@Override
	public void birth(String child, String parent) {
		Person parentPerson = monarchy.get(parent);
		if (parentPerson != null) {
			Person childPerson = new Person(child);
			monarchy.put(child, childPerson);
			parentPerson.addChild(childPerson);
		}
	}

	/**
	 * represent the dead of a person in this hierarchy. We mark the person as dead.
	 * 
	 * Time Complexity: O(1): we retrieve the person from a map
	 * 
	 * Space Complexity:O(1): No extra data structure depending of the input
	 */
	@Override
	public void death(String name) {
		Person deathPerson = monarchy.get(name);
		if (deathPerson != null) {
			deathPerson.die();

		}
	}

	/**
	 * shows the line of succession, where the oldest child of the king is the next
	 * in the line, and after him, his first child an so on. Using DFS we traverse
	 * the tree as deep as possible and then go to the next sibling and repeat and
	 * so on.
	 * 
	 * Time Complexity: O(n): we traverse the whole tree
	 * 
	 * Space Complexity: O(n): we generate an array with the whole tree. Also store
	 * in memory stack the largest branch.
	 */
	@Override
	public List<String> getOrderOfSuccession() {

		List<String> successionList = new ArrayList<String>();

		getOrderOfSuccessionAux(this.king, successionList);

		return successionList;
	}

	private void getOrderOfSuccessionAux(Person person, List<String> successionList) {

		if (person != null) {
			if (person.isAlive()) {
				successionList.add(person.getName());
			}
			for (Person child : person.getChildren()) {
				getOrderOfSuccessionAux(child, successionList);
			}
		}
	}

	public static void main(String[] args) {

//		Exercise29_Monarchy mornachy = new Exercise29_Monarchy("Jose");
//		mornachy.birth("Pilin", "Jose");
//		mornachy.birth("Daisy", "Jose");
//		mornachy.birth("Wanda", "Jose");
//		mornachy.birth("Khora", "Jose");
//		mornachy.birth("Cosmo", "Wanda");
//		mornachy.birth("Brandy", "Wanda");
//		mornachy.birth("Milo", "Wanda");
//		mornachy.birth("Alejandra", "Brandy");
//		mornachy.birth("Lindsay", "Brandy");
//
//		System.out.println(mornachy.monarchy);
//		System.out.println(mornachy.getOrderOfSuccession());
//
//		mornachy.death("Wanda");
//		System.out.println("death Wanda\n");
//		System.out.println(mornachy.monarchy);
//		System.out.println(mornachy.getOrderOfSuccession());
//
//		mornachy.death("Jose");
//
//		System.out.println("death Jose\n");
//		System.out.println(mornachy.monarchy);
//		System.out.println(mornachy.getOrderOfSuccession());
//
//		mornachy.death("Daisy");
//
//		System.out.println("death Daisy\n");
//		System.out.println(mornachy.monarchy);
//		System.out.println(mornachy.getOrderOfSuccession());

		Exercise29_Monarchy mornachy = new Exercise29_Monarchy("Jake");
		mornachy.birth("Catherine", "Jake");
		mornachy.birth("Jane", "Catherine");
		mornachy.birth("Farah", "Jane");
		mornachy.birth("Tom", "Jake");
		mornachy.birth("Celine", "Jake");
		mornachy.birth("Mark", "Catherine");
		mornachy.birth("Peter", "Celine");

		System.out.println(mornachy.getOrderOfSuccession());

		mornachy.death("Jake");
		System.out.println("\ndeath Jake\n");
		System.out.println(mornachy.getOrderOfSuccession());

		mornachy.death("Jane");
		System.out.println("\ndeath Jane\n");
		System.out.println(mornachy.getOrderOfSuccession());

	}
}