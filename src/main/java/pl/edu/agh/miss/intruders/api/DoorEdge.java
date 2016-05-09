package pl.edu.agh.miss.intruders.api;

import java.util.List;
import java.util.Queue;

/**
 * Graph edge between 2 DoorNodes.
 *
 * All edges are directed. Every edge has a length (the distance between the
 * doors) which is represented by the number of time ticks needed for a
 * robot/intruder to move through it.
 */
public interface DoorEdge {
	
	String getName();
	
	/** Source DoorNode. */
	DoorNode getSource();
	
	void setSource(DoorNode source);

	/** Destination DoorNode. */
	DoorNode getDestination();
	
	void setDestination(DoorNode destination);

	/** Edge length (in time ticks). */
	int getLength();
	
	void setLength(int length);

	/**
	 * Probability transition events for the future steps.
	 *
	 * Queue of length equal to the length of the edge. Each element describes
	 * how much of the intruder presence probability will be moved through the
	 * node in the time step.
	 */
	Queue<Float> getIntruderQueue();
	
	void setIntrudersQueue(Queue<Float> queue);
	
	Queue<List<Robot>> getRobotsQueue();
	
	void setRobotsQueue(Queue<List<Robot>> queue);
}
