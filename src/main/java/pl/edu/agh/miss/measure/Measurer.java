package pl.edu.agh.miss.measure;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;

public class Measurer {
	
	List<Float> majorIntruderValues;
	
	List<Float> allIntruderValues;

	
	private int verbosity;

	private CommandLine cmd;
	
	public Measurer(int verbosity, CommandLine cmd) {
		majorIntruderValues = new LinkedList<>();
		allIntruderValues = new LinkedList<>();
		this.verbosity = verbosity;
		this.cmd = cmd;
	}

	public void measure(String fileName) throws IOException {
		if (verbosity==0) {
			return;
		}
		FileWriter fw = new FileWriter(fileName, false);
		writeHeader(fw);
		if (verbosity==1) {
			fw.write("Before: "+Float.toString(majorIntruderValues.get(0))+"\n");
			fw.write("After: "+Float.toString(majorIntruderValues.get(majorIntruderValues.size()-1))+"\n");
		} else if (verbosity==2) {
			for (float iv : majorIntruderValues) {
				fw.write(Float.toString(iv)+"\n");
			}
		} else if (verbosity==3) {
			for (float iv : allIntruderValues) {
				fw.write(Float.toString(iv)+"\n");
			}			
		}
		fw.close();
	}
	
	private void writeHeader(FileWriter fw) throws IOException {
		fw.write("===== Intruder simulation results =====\n");
		fw.write("File: "+cmd.getOptionValue("file")+"\n");
		fw.write("Number of iterations: "+cmd.getOptionValue("iterations")+"\n");
		fw.write("Time units per iteration: "+cmd.getOptionValue("times")+"\n");
		fw.write("Verbosity: "+Integer.toString(verbosity)+"\n");
		fw.write("=======================================\n");
	}

	private float getTotalIntruderValue (Building building) {
		float total = 0;
		for (DoorNode node : building.getDoorNodes()) {
			total += node.getProbability();
			for (DoorEdge edge : node.getEdges()) {
				for (float intruderVal : edge.getIntruderQueue()) {
					total += intruderVal;
				}
			}
		}
		return total;
	}

	public void addMajorState(Building building) {
		majorIntruderValues.add(getTotalIntruderValue(building));
		allIntruderValues.add(getTotalIntruderValue(building));
	}

	public void addMinorState(Building building) {
		allIntruderValues.add(getTotalIntruderValue(building));
	}

}
