package pl.edu.agh.miss.intruders.api.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;

public class SampleDoorEdge implements DoorEdge {
	
	private DoorNode source;
	
	private DoorNode destination;
	
	private int length;
	
	private Queue<Float> intruderQueue;
	
	private Queue<List<Robot>> robotsQueue;
	
	public SampleDoorEdge(DoorNode src, DoorNode dest) {
		source = src;
		destination = dest;
		intruderQueue = new LinkedList<>();
	}

	@Override
	public DoorNode getSource() {
		return source;
	}

	@Override
	public DoorNode getDestination() {
		return destination;
	}


	@Override
	public void setSource(DoorNode source) {
		this.source = source;
	}

	@Override
	public void setDestination(DoorNode destination) {
		this.destination = destination;
	}

	
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public Queue<Float> getIntruderQueue() {
		return intruderQueue;
	}

	@Override
	public Queue<List<Robot>> getRobotsQueue() {
		return robotsQueue;
	}

	@Override
	public void setIntrudersQueue(Queue<Float> queue) {
		this.intruderQueue = queue;
	}

	@Override
	public void setRobotsQueue(Queue<List<Robot>> queue) {
		this.robotsQueue = queue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Edge "+getName()+": ");
		for (Float i : intruderQueue) {
			sb.append(i+", ");
		}
		sb.append(" ** ");
		for (List<Robot> s : robotsQueue) {
			for (Robot r : s) {
				sb.append(r == null ? "null" : "robot");
				sb.append(", ");
			}
			sb.append(" | ");
		}
		sb.append(" ( "+getLength()+" ) ");
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public String getName() {
		return source.getName()+"->"+destination.getName();
	}
	
}
