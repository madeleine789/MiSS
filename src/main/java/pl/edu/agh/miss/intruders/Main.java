package pl.edu.agh.miss.intruders;

import pl.edu.agh.miss.intruders.model.graph.Building;
import pl.edu.agh.miss.intruders.service.IOService;
import pl.edu.agh.miss.intruders.view.GraphView;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;
import pl.edu.agh.miss.intruders.api.Room;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;

public class Main {
	
	public static void main(String[] args) throws IOException {
		File file;
		if (args.length < 2){
			file = new File(Main.class.getClassLoader().getResource("roson/simple.roson").getFile());
		} else {
			file = new File(args[1]);
		}

		Building b = IOService.importFromJson(file);
		System.out.println(b.toString());
		GraphView graphView = new GraphView().withMergedEdges(false).withNodeLabels(true).withEdgeLabels(true).withRobots(true);
		graphView.generateAndDisplay(b);
		IntruderController ic = new SampleIntruderController();
		RobotsController rc = new SampleRobotsController();
		List<DoorNode> nodes = createNodes();
		Config config = new SampleConfig();
		List<Room> rooms = new LinkedList<>();
		Simulator s = new Simulator(ic, rc, nodes, config, rooms);
		s.simulate();
	}

	private static List<DoorNode> createNodes() {
		List<DoorNode> nodes = new LinkedList<>();
		DoorNode a = new SampleDoorNode();
		DoorNode b = new SampleDoorNode();
		DoorEdge ab = new SampleDoorEdge();
		DoorEdge ba = new SampleDoorEdge();
		
		ab.setSource(a);
		ab.setDestination(b);
		ab.setLength(2);
		ab.setProbability((float)1);		
		ba.setSource(b);
		ba.setDestination(a);
		ba.setLength(2);
		ba.setProbability((float)1);
		
		a.addEdge(ab);
		a.addEdge(ba);
		b.addEdge(ab);
		b.addEdge(ba);
		
		Queue<Float> abQueue = new LinkedList<>();
		abQueue.add((float)0);
		abQueue.add((float)0);
		ab.setIntrudersQueue(abQueue);
		Queue<Float> baQueue = new LinkedList<>();
		baQueue.add((float)0);
		baQueue.add((float)0);
		ba.setIntrudersQueue(baQueue);
		
		Queue<Robot> abRQueue = new LinkedList<>();
		abRQueue.add(null);
		abRQueue.add(null);
		abRQueue.add(new SampleRobot());
		ab.setRobotsQueue(abRQueue);
		Queue<Robot> baRQueue = new LinkedList<>();
		baRQueue.add(null);
		baRQueue.add(null);
		baRQueue.add(new SampleRobot());
		ba.setRobotsQueue(baRQueue);
		
		a.setName("A");
		b.setName("B");
		a.setTheOtherSide(b);
		b.setTheOtherSide(a);
		a.setProbability((float)0.5);
		b.setProbability((float)0.5);
		a.setStayProbability((float)0);
		b.setStayProbability((float)0);
		
		nodes.add(a);
		nodes.add(b);		
		return nodes;
	}
}
