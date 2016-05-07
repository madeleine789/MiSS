package pl.edu.agh.miss.intruders;

import java.io.File;
import java.io.IOException;

import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.impl.*;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;
import pl.edu.agh.miss.intruders.model.RosonBuilding;
import pl.edu.agh.miss.intruders.service.Converter;
import pl.edu.agh.miss.intruders.service.IOService;

public class Main {
	
	public static void main(String[] args) throws IOException {

		File file;
		int timeUnits;
		if (args.length < 3){
			file = new File(Main.class.getClassLoader().getResource("roson/straight.roson").getFile());
			timeUnits = 5;
		} else {
			file = new File(args[1]);
			timeUnits = Integer.parseInt(args[2]);
		}

		RosonBuilding b = IOService.importFromJson(file);
		Converter converter = new Converter(b);
        Building building = converter.rosonToSimulation();

        IntruderController ic = new SampleIntruderController();
		RobotsController rc = new SampleRobotsController();
        Config config = new SampleConfig();
		Simulator s = new Simulator(ic, rc, config, building, converter, timeUnits);
		s.simulate();
	}
}
