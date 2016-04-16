package pl.edu.agh.miss.intruders;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.impl.*;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;
import pl.edu.agh.miss.intruders.model.RosonBuilding;
import pl.edu.agh.miss.intruders.service.Converter;
import pl.edu.agh.miss.intruders.service.IOService;
import pl.edu.agh.miss.intruders.service.GraphView;

public class Main {
	
	public static void main(String[] args) throws IOException {

		File file;
		if (args.length < 2){
			file = new File(Main.class.getClassLoader().getResource("roson/maze.roson").getFile());
		} else {
			file = new File(args[1]);
		}

		RosonBuilding b = IOService.importFromJson(file);
		GraphView graphView = new GraphView().withMergedEdges(false).withNodeLabels(true).withEdgeLabels(false)
               .withRobots(true);
		graphView.generateAndDisplay(b);
        Building building = Converter.rosonToSimulation(b);

        IntruderController ic = new SampleIntruderController();
		RobotsController rc = new SampleRobotsController();
        Config config = new SampleConfig();
		SampleBuilding building1 = new SampleBuilding(new LinkedList<>(), createNodes());
		Simulator s = new Simulator(ic, rc, config, building);
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
		ab.setProbability(1f);
		ba.setSource(b);
		ba.setDestination(a);
		ba.setLength(2);
		ba.setProbability(1f);
		
		a.addEdge(ab);
		a.addEdge(ba);
		b.addEdge(ab);
		b.addEdge(ba);
		
		Queue<Float> abQueue = new LinkedList<>();
		abQueue.add(0f);
		abQueue.add(0f);
		ab.setIntrudersQueue(abQueue);
		Queue<Float> baQueue = new LinkedList<>();
		baQueue.add(0f);
		baQueue.add(0f);
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
		a.setProbability(0.5f);
		b.setProbability(0.5f);
		a.setStayProbability(0f);
		b.setStayProbability(0f);
		
		nodes.add(a);
		nodes.add(b);		
		return nodes;
	}
}
