package pl.edu.agh.miss.intruders;

import java.util.List;

import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;

public class SampleIntruderController implements IntruderController {
	
	private List<DoorNode> doorNodes;

	@Override
	public void init(List<DoorNode> doorNodes) {
		this.doorNodes = doorNodes;
	}

	@Override
	public void update() {
		for (DoorNode node : doorNodes) {
			int intruderByTheDoorProb = 0;
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getDestination().equals(node)) {
					intruderByTheDoorProb += edge.getIntruderQueue().poll();
				}
			}
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getSource().equals(node)) {
					edge.getIntruderQueue().add(intruderByTheDoorProb*edge.getProbability());
				}
			}
		}
	}

}
