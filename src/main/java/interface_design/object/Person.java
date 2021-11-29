package interface_design.object;

import java.util.ArrayList;
import java.util.List;

public class Person {

	private String name;
	private boolean isAlive;
	private List<Person> children;

	public Person(String name) {
		this.name = name;
		isAlive = true;
		children = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void die() {
		this.isAlive = false;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void addChild(Person children) {
		this.children.add(children);
	}

	public String toString() {
		StringBuilder hierarchy = new StringBuilder();
		hierarchy.append(name);
		hierarchy.append(": ");
		for(int childIndex=0;childIndex<children.size();childIndex++) {
			hierarchy.append(children.get(childIndex).getName());
			if(childIndex+1<children.size()) {
				hierarchy.append("->");
			}
		}
		
		return hierarchy.toString();
	}
}
