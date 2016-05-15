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

public class Main {
	
	public static void main(String[] args) throws IOException, ParseException {

		Options options = new Options();
		options.addOption("file", true, "path to roson file");
		options.addOption("times", true, "length of simulation in time units");
		options.addOption("merged", false, "merge edges on visualization");
		options.addOption("labels", true, "show labels under nodes");
		options.addOption("robots", false, "show robots on visualization");
		options.addOption("screenshots", false, "save screenshot after every step");

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

		boolean labels, edges, robots, screenshots;
		labels = cmd.hasOption("labels");
		edges = cmd.hasOption("merged");
		robots = cmd.hasOption("robots");
		screenshots = cmd.hasOption("screenshots");

		RosonBuilding b = IOService.importFromJson(file);
		Converter converter = new Converter(b);
        Building building = converter.rosonToSimulation();
		GraphView graphView = new GraphView().withMergedEdges(edges)
				.withNodeLabels(labels).withRobots(robots).withScreenshots(screenshots);

        IntruderController ic = new SampleIntruderController();
		RobotsController rc = new SampleRobotsController();
        Config config = new SampleConfig();
		Simulator s = new Simulator(ic, rc, config, building, converter, timeUnits, graphView);
		s.simulate();
	}
}
