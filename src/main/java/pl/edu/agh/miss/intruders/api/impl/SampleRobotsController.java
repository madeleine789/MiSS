package pl.edu.agh.miss.intruders.api.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

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
		for (DoorNode node : doorNodes) {
			List<DoorEdge> edges = node.getEdges();
			List<Robot> robotsByTheDoor = new LinkedList<>();
			for (DoorEdge edge : edges) {
				if (edge.getDestination().equals(node)) {
					robotsByTheDoor.add(edge.getRobotsQueue().poll());
				}
			}
			Random rand = new Random();
			for (Robot robot : robotsByTheDoor) {
				int edgeNumber = rand.nextInt(edges.size());
				edges.get(edgeNumber).getRobotsQueue().add(robot);
			}
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
					}
				}
			}
		}
	}
}
