package pl.edu.agh.miss.intruders;

import java.util.List;

import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Room;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;

public class Simulator {

	private IntruderController intruderController;

	private RobotsController robotsController;

	private Config config;

	private List<DoorNode> doorNodes;
	
	private List<Room> rooms;

	public Simulator(IntruderController intruderController, RobotsController robotsController, Config config,
					 Building building) {
		this.intruderController = intruderController;
		this.robotsController = robotsController;
		this.doorNodes = building.getDoorNodes();
		this.config = config;
		this.rooms = building.getRooms();
	}

	public void simulate() {
		intruderController.init(doorNodes);
		robotsController.init(doorNodes,rooms);
		for (int i = 0; i < 10; i++) {
			intruderController.update();
			robotsController.update();
			robotsController.reduceProbabilities(config);
			System.out.println("###");
			for (DoorNode node : doorNodes) {
				System.out.println(node);
			}
		}
	}

}
