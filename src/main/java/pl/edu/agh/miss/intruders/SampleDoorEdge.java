package pl.edu.agh.miss.intruders;

import java.util.Queue;

import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;

public class SampleDoorEdge implements DoorEdge {
	
	private DoorNode source;
	
	private DoorNode destination;

	@Override
	public DoorNode getSource() {
		return source;
	}

	@Override
	public DoorNode getDestination() {
		return destination;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Queue<Integer> getIntruderQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setProbability(int weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public Queue<Robot> getRobotsQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIntrudersQueue(Queue<Integer> queue) {
		// TODO Auto-generated method stub
		
	}

}
