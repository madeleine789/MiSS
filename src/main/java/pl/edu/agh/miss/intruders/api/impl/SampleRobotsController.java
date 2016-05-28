package pl.edu.agh.miss.intruders.api.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;
import pl.edu.agh.miss.intruders.api.Room;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;

public class SampleRobotsController implements RobotsController {

	private List<DoorNode> doorNodes;
	
	private List<Room> rooms;

	@Override
	public void init(List<DoorNode> doorNodes, List<Room> rooms) {
		this.doorNodes = doorNodes;
		this.rooms = rooms;
	}

	@Override
	public void update() {
		Map<DoorNode, List<Robot>> robotsOnTheOtherSide = new HashMap<>();
		for (DoorNode node : doorNodes) {
				// get robots to distribute
			List<DoorEdge> edges = node.getEdges();
			List<Robot> robotsByTheDoor = new LinkedList<>();
			for (DoorEdge edge : edges) {
				if (edge.getDestination().equals(node) && !edge.getRobotsQueue().isEmpty()) {
					robotsByTheDoor.addAll(edge.getRobotsQueue().poll());
				}
			}
			robotsByTheDoor.addAll(node.getRobotsFromTheOtherSide());
				// prepare lists to be distributed
			List<DoorEdge> outEdges= new LinkedList<>();
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getSource().equals(node)) {
					outEdges.add(edge);
				}
			}
			List<List<Robot>> listsToDistribute = new LinkedList<>();
			for (int i=0;i<outEdges.size()+1;i++) {
				listsToDistribute.add(new LinkedList<>());
			}
			int i = 0;
			while (!robotsByTheDoor.isEmpty()) {
				listsToDistribute.get(i).add(robotsByTheDoor.remove(0));
				i = (i+1) % (outEdges.size()+1);
			}
			Collections.shuffle(listsToDistribute);
				// distribute lists
			for (DoorEdge edge : outEdges) {
				edge.getRobotsQueue().add(listsToDistribute.remove(0));
			}
			robotsOnTheOtherSide.put(node, listsToDistribute.remove(0));
		}
		// update other side
		for (DoorNode node : robotsOnTheOtherSide.keySet()) {
			node.setRobotsFromTheOtherSide(robotsOnTheOtherSide.get(node));
		}
	}

	@Override
	public void reduceProbabilities(Config config) {
		for (Room room : rooms) {
			System.out.println(room);
			for (DoorNode node : room.getDoorNodes()) {
				for (Robot robot : node.getRobots()) {
					for (DoorNode nodeToUpdate : room.getDoorNodes()) {
						for (DoorEdge edge : nodeToUpdate.getEdges()) {
							Queue<Float> newIntruderProb = new LinkedList<>();
							edge.getIntruderQueue().forEach(prob->newIntruderProb.add(config.getNewProbability(prob, robot)));
							edge.setIntrudersQueue(newIntruderProb);
						}
						nodeToUpdate.setProbability(config.getNewProbability(nodeToUpdate.getProbability(), robot));
					}
				}
			}
		}
	}
}
