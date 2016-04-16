package pl.edu.agh.miss.intruders.api;

import java.util.List;

public interface Room {
	
	List<DoorNode> getDoorNodes();
	
	void addNode(DoorNode node);
}
