package pl.edu.agh.miss.intruders;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.miss.intruders.api.Door;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;
import pl.edu.agh.miss.intruders.api.Room;

public class SampleDoorNode implements DoorNode {
	
	private List<DoorEdge> edges;
	
	int probability;
	
	private DoorNode otherSide;
	
	private String name;
	
	public SampleDoorNode() {
		edges = new LinkedList<>();
	}

	@Override
	public Door getDoor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room getRoom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProbability() {
		return probability;
	}
	
	@Override
	public void setProbability(int prob) {
		this.probability = prob;
	}

	@Override
	public Robot[] getRobots() {
		List<Robot> robots = new LinkedList<>();
		for (DoorEdge edge : getEdges()) {
			robots.addAll(edge.getRobotsQueue());
		}
		return (Robot[]) robots.toArray();
	}

	@Override
	public DoorNode getTheOtherSide() {
		return otherSide;
	}

	@Override
	public int getPassThroughProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPassThroughProbability(int weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getStayProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStayProbability(int weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DoorEdge> getEdges() {
		return edges;
	}

	@Override
	public void move(Robot robot, DoorEdge edge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void passThrough(Robot robot) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DoorNode> getOtherDoors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEdge(DoorEdge edge) {
		edges.add(edge);
	}

	@Override
	public void setTheOtherSide(DoorNode node) {
		this.otherSide = node;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Node "+getName()+"\n");
		sb.append("Edges:\n");
		for (DoorEdge edge : edges) {
			sb.append("\t"+edge.toString());
		}
		sb.append("probability: "+probability+"\n");
		return sb.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
