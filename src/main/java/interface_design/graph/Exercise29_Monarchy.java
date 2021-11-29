package interface_design.graph;

import java.util.ArrayList;
import java.util.List;
import graph.Graph;
import graph.GraphNode;
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

	private Graph<String> monarchy;

	public Exercise29_Monarchy(String rootParent) {
		monarchy = new Graph<String>();
		monarchy.addVertex(new GraphNode<>(rootParent));
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
		GraphNode<String> parentVertex = monarchy.getVertexes().get(parent);
		if (parentVertex != null) {
			GraphNode<String> childVertex = new GraphNode<>(child);
			monarchy.addVertex(childVertex);
			childVertex.setParent(parentVertex);
			monarchy.addEdge(parentVertex, childVertex, false);
		}
	}

	/**
	 * represent the dead of a person in this hierarchy. We remove the person and
	 * move the next successor into his position and rearrange all the lines of
	 * succession.
	 * 
	 * Time Complexity: O(p+d): we iterate over the death's and parent's children
	 * 
	 * Space Complexity:O(1): No extra data structure depending of the input
	 */
	@Override
	public void death(String name) {
		GraphNode<String> deathVertex = monarchy.getVertexes().get(name);
		if (deathVertex != null) {
			GraphNode<String> parentVertex = deathVertex.getParent();
			GraphNode<String> replacementVertex = null;

			// if the death vertex has children
			if (deathVertex.getEdges().size() > 0) {
				List<GraphNode<String>> deathChildrenVertex = deathVertex.getEdges();

				// first child

				replacementVertex = monarchy.getVertexes().get(deathChildrenVertex.get(0).getValue());

				replacementVertex.setParent(parentVertex);

				// remove first child from the death's children
				deathChildrenVertex.remove(0);

				// add children from death vertex to the replacement

				for (GraphNode<String> edge : deathChildrenVertex) {
					edge.setParent(replacementVertex);
				}
				replacementVertex.getEdges().addAll(deathChildrenVertex);

				// update the replacement vertex with his new children

				// replace the death vertex with his replacement in the same position of the
				// children from his parent
				if (parentVertex != null) {

					int indexDeathVertex = parentVertex.getEdges().indexOf(deathVertex);

					if (indexDeathVertex != -1) {
						parentVertex.getEdges().set(indexDeathVertex, replacementVertex);
					}
				}

			} else if (parentVertex != null) {
				parentVertex.getEdges().remove(deathVertex);
			}

			// remove the death vertex from the list of vertex from the graph
			monarchy.getVertexes().remove(name);
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
		if (monarchy != null) {
			getOrderOfSuccessionAux(monarchy.getVertexes().values().iterator().next(), successionList);
		}

		return successionList;
	}

	private void getOrderOfSuccessionAux(GraphNode<String> vertex, List<String> successionList) {

		if (vertex != null) {
			successionList.add(vertex.getValue());
			for (GraphNode<String> edge : vertex.getEdges()) {
				getOrderOfSuccessionAux(edge, successionList);
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

		System.out.println(mornachy.monarchy);
		System.out.println(mornachy.getOrderOfSuccession());

		mornachy.death("Jake2");
		System.out.println("\ndeath Jake\n");
		System.out.println(mornachy.monarchy);
		System.out.println(mornachy.getOrderOfSuccession());

		mornachy.death("Jane");
		System.out.println("\ndeath Jane\n");
		System.out.println(mornachy.monarchy);
		System.out.println(mornachy.getOrderOfSuccession());

	}
}