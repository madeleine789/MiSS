package pl.edu.agh.miss.intruders;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.*;
import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.impl.*;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;
import pl.edu.agh.miss.intruders.model.RosonBuilding;
import pl.edu.agh.miss.intruders.service.Converter;
import pl.edu.agh.miss.intruders.service.GraphView;
import pl.edu.agh.miss.intruders.service.IOService;
import pl.edu.agh.miss.measure.Measurer;

public class Main {
	
	public static void main(String[] args) throws IOException, ParseException {

		Options options = new Options();
		options.addOption("file", true, "path to roson file");
		options.addOption("iterations", true, "length of simulation in iterations");
		options.addOption("times", true, "time units per iteration");
		options.addOption("merged", false, "merge edges on visualization");
		options.addOption("labels", false, "show labels under nodes");
		options.addOption("robots", false, "show robots on visualization");
		options.addOption("screenshots", false, "save screenshot after every step");
		options.addOption("v", false, "show building graph with no simulation");
		options.addOption("s", false, "run simulation without visualization");
		options.addOption("verbosity", true, "verbosity level (0-3)");
		options.addOption("auto", false, "use automatic layout");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);
		String path = cmd.getOptionValue("file");
		File file;
		if (path == null) {
			file = new File(Main.class.getClassLoader().getResource("roson/straight.roson").getFile());
		} else {
			file = new File(Main.class.getClassLoader().getResource(path).getFile());
		}
		String times = cmd.getOptionValue("times");
		int timeUnits;
		if (times == null){
			timeUnits = 15;
		} else {
			timeUnits = Integer.parseInt(times);
		}
		String iter = cmd.getOptionValue("iterations");
		int iterations;
		if (iter == null){
			iterations = 5;
		} else {
			iterations = Integer.parseInt(iter);
		}
		String verbosityOption = cmd.getOptionValue("verbosity");
		int verbosity;
		if (verbosityOption==null) {
			verbosity = 1;
		} else {
			verbosity = Integer.parseInt(verbosityOption);
		}

		boolean labels, edges, robots, screenshots;
		labels = cmd.hasOption("labels");
		edges = cmd.hasOption("merged");
		robots = cmd.hasOption("robots");
		screenshots = cmd.hasOption("screenshots");

		RosonBuilding b = IOService.importFromJson(file);
		b.setAutomaticLayout(cmd.hasOption("auto"));
		Converter converter = new Converter(b);
		GraphView graphView = new GraphView().withMergedEdges(edges)
				.withNodeLabels(labels).withRobots(robots).withScreenshots(screenshots);
		if (cmd.hasOption("v")) {
			graphView.generate(b).display();
		} else {
			Building building = converter.rosonToSimulation();
			IntruderController ic = new SampleIntruderController();
			RobotsController rc = new SampleRobotsController();
			Config config = new SampleConfig();
			Measurer measurer = new Measurer(verbosity, cmd);
			Simulator s;
			if (cmd.hasOption("s")) {
				s = new Simulator(ic, rc, config, building, converter, iterations, timeUnits, null, measurer);
			} else {
				s = new Simulator(ic, rc, config, building, converter, iterations, timeUnits, graphView, measurer);
			}
			s.simulate();
		}
	}
}
