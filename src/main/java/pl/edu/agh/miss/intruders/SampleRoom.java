package pl.edu.agh.miss.intruders;

import java.util.List;

import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Room;

public class SampleRoom implements Room{
	
	List<DoorNode> doorNodes;

	@Override
	public List<DoorNode> getDoorNodes() {
		return doorNodes;
	}

}
