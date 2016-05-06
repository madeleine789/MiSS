package pl.edu.agh.miss.intruders.api.impl;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Room;

public class SampleRoom implements Room{
	
	List<DoorNode> doorNodes;
	
	public SampleRoom() {
		doorNodes = new LinkedList<>();
	}

	@Override
	public List<DoorNode> getDoorNodes() {
		return doorNodes;
	}

	@Override
	public void addNode(DoorNode node) {
		doorNodes.add(node);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("$$$$$\n");
		for (DoorNode node : doorNodes) {
			sb.append(node.getName()+" ");
		}
		return sb.toString();
	}

}
