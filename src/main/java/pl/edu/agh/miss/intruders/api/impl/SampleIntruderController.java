package pl.edu.agh.miss.intruders.api.impl;

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
			float intruderByTheDoorProb = node.getProbability();
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getDestination().equals(node)) {
					intruderByTheDoorProb += edge.getIntruderQueue().poll();
				}
			}
		
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getSource().equals(node)) {
					float intruderMoveProb = intruderByTheDoorProb*edge.getProbability();
					edge.getIntruderQueue().add(intruderMoveProb);
				}
			}
			DoorNode otherSide = node.getTheOtherSide();
			float intruderPassThroughProb = intruderByTheDoorProb*node.getPassThroughProbability();
			otherSide.setProbability(otherSide.getProbability()+intruderPassThroughProb);
			node.setProbability(intruderByTheDoorProb*node.getStayProbability());
		}
	}

}
