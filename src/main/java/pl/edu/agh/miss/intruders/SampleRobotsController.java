package pl.edu.agh.miss.intruders;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;

public class SampleRobotsController implements RobotsController {

	private List<DoorNode> doorNodes;

	@Override
	public void init(List<DoorNode> doorNodes) {
		this.doorNodes = doorNodes;
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
		for (DoorNode node : doorNodes) {
			for (Robot robot : node.getRobots()) {
				for (DoorEdge edge : node.getEdges()) {
					Queue<Integer> newIntruderProb = new LinkedList<>();
					edge.getIntruderQueue().forEach(prob->newIntruderProb.add(config.getNewProbability(prob, robot)));
					edge.setIntrudersQueue(newIntruderProb);
				}
			}
		}
	}
}
