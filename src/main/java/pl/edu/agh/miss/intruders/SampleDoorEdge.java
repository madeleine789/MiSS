package pl.edu.agh.miss.intruders;

import java.util.LinkedList;
import java.util.Queue;

import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;

public class SampleDoorEdge implements DoorEdge {
	
	private DoorNode source;
	
	private DoorNode destination;
	
	private int length;
	
	private int probability;
	
	private Queue<Integer> intruderQueue;
	
	private Queue<Robot> robotsQueue;
	
	public SampleDoorEdge() {
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
	public Queue<Integer> getIntruderQueue() {
		return intruderQueue;
	}

	@Override
	public int getProbability() {
		return probability;
	}

	@Override
	public void setProbability(int weight) {
		this.probability = weight;
	}

	@Override
	public Queue<Robot> getRobotsQueue() {
		return robotsQueue;
	}

	@Override
	public void setIntrudersQueue(Queue<Integer> queue) {
		this.intruderQueue = queue;
	}

	@Override
	public void setRobotsQueue(Queue<Robot> queue) {
		this.robotsQueue = queue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Edge "+getName()+": ");
		for (Integer i : intruderQueue) {
			sb.append(i+", ");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public String getName() {
		return source.getName()+"->"+destination.getName();
	}
	
}
