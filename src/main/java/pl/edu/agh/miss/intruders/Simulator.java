package pl.edu.agh.miss.intruders;

import org.graphstream.graph.Graph;
import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;
import pl.edu.agh.miss.intruders.model.Edge;
import pl.edu.agh.miss.intruders.model.RosonBuilding;
import pl.edu.agh.miss.intruders.service.Converter;
import pl.edu.agh.miss.intruders.service.GraphView;
import pl.edu.agh.miss.intruders.service.utils.ColorUtils;

public class Simulator {

	private IntruderController intruderController;

	private RobotsController robotsController;

	private Config config;

	private Building building;

	private Converter converter;

	private int timeUnits = 5;

	public Simulator(IntruderController intruderController, RobotsController robotsController, Config config,
					 Building building, Converter converter, int timeUnits) {
		this.intruderController = intruderController;
		this.robotsController = robotsController;
		this.config = config;
		this.building = building;
		this.converter = converter;
		this.timeUnits = timeUnits;
	}

	public void simulate() {

		GraphView graphView = new GraphView().withMergedEdges(false).withNodeLabels(true).withEdgeLabels(false)
               .withRobots(true);
		RosonBuilding rosonBuilding = converter.simulationToRoson(building);
		Graph graph = graphView.generate(rosonBuilding);
		intruderController.init(building.getDoorNodes());
		robotsController.init(building.getDoorNodes(), building.getRooms());
		for (int i = 0; i < timeUnits; i++) {
			intruderController.update();
			robotsController.update();
			robotsController.reduceProbabilities(config);
			System.out.println("###");
			building.getDoorNodes().forEach(System.out::println);
			updateGraph(graph);
			sleep();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void updateGraph(Graph graph) {
		RosonBuilding rosonBuilding = converter.simulationToRoson(building);
		for (org.graphstream.graph.Node node : graph) {
			String id = "Node" + node.getId().substring(0, node.getId().indexOf(" "));
			double probability = rosonBuilding.getNode(id).getProbability();
			String color = ColorUtils.getRGBString(ColorUtils.probabilityToColor(probability));
			node.setAttribute("ui.style", "fill-color:  " + color + ";");
			if (rosonBuilding.getNode(id).isRobotThere()) {
				if(rosonBuilding.isGate(id))
				node.addAttribute("ui.style", "icon: url('./src/main/resources/robot.png'); icon-mode: at-left; ");
				else rosonBuilding.getNode(id).isRobotThere(false);
			} else {
				node.addAttribute("ui.style", "icon: url('./src/main/resources/empty.png'); icon-mode: at-left; ");
			}
		}

		for (org.graphstream.graph.Edge edge : graph.getEachEdge()) {
			Edge e = rosonBuilding.getEdge(edge.getAttribute("id"));
			if (e.hasRobot()) {
				edge.addAttribute("ui.style", "fill-color: rgb(255,0,0);");
			} else {
				edge.addAttribute("ui.style", "fill-color: rgb(0,0,0);");
			}
		}
	}

}
