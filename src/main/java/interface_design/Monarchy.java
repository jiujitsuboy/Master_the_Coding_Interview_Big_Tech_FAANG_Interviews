package interface_design;

import java.util.List;

public interface Monarchy {
	// add a child of this parent
	void birth(String child, String parent);

	// mark the person as dead
	void death(String name);

	// return a list with the order of succession to the throne
	List<String> getOrderOfSuccession();
}
