package graph.network.time.optimal;

public class DijkstraNode implements Comparable<DijkstraNode> {
	private int name;
	private Integer value;

	public DijkstraNode(int name) {
		this(name, Integer.MAX_VALUE);
	}

	public DijkstraNode(int name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(DijkstraNode node) {
		return this.value.compareTo(node.value);
	}

	public String toString() {
		return String.format("%s: %s", name, value);
	}

}
