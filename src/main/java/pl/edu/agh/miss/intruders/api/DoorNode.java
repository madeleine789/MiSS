package pl.edu.agh.miss.intruders.api;

import java.util.List;

/**
 * Graph node representing a door.
 *
 * The DoorNode is the main unit of the graph representing a building layout.
 *
 * Each DoorNode is connected to all the doors within the same room, DoorEdges
 * represent such connections. Also, each DoorNode has a corresponding DoorNode
 * in another room ("the other side" of the door).
 */
public interface DoorNode {
	
	String getName();
	
	void setName(String name);
	
	/** Get the Door object associated with this door */
	Door getDoor();

	/** Get the Room object associated (containing) with this door */
	Room getRoom();

	/**
	 * Probability weight that the intruder is in this node.
	 *
	 * Probability returned is a sum of the probability of the intruder staying
	 * in this node or being in process of moving to another door in this room
	 * (see DoorEdge for details). Returned weight might be compared to other
	 * weights retrieved from other nodes in the same time step.
	 */
	float getProbability();
	
	void setProbability(float prob);
	

	/** Get array of robots currently in the DoorNode */
	Robot[] getRobots();

	/** Get an array of the other doors in the room */
	List<DoorEdge> getEdges();
	
	void addEdge(DoorEdge edge);
	
	List<DoorNode> getOtherDoors();

	/**
	 * Get the corresponding door object in the other room ("passing through")
	 */
	DoorNode getTheOtherSide();
	
	void setTheOtherSide(DoorNode node);
	
	List<Robot> getRobotsFromTheOtherSide();
	
	void setRobotsFromTheOtherSide(List<Robot> robotsFromTheOtherSide);

	/** Move a robot to through an edge. */
	void move(Robot robot, DoorEdge edge);

	/** Make a robot pass through the door. */
	void passThrough(Robot robot);
}
