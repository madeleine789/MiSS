package pl.edu.agh.miss.intruders;

import java.util.List;

import pl.edu.agh.miss.intruders.api.Door;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;
import pl.edu.agh.miss.intruders.api.Room;

public class SampleDoorNode implements DoorNode {
	
	private List<DoorEdge> edges;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Robot[] getRobots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoorNode getTheOtherSide() {
		// TODO Auto-generated method stub
		return null;
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

}
