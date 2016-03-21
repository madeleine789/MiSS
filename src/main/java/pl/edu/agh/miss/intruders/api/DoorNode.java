package pl.edu.agh.miss.intruders.api;

import java.util.List;

/** Graph node representing a door.
 *
 * The DoorNode is the main unit of the graph representing a building
 * layout.
 *
 * Each DoorNode is connected to all the doors within the same room,
 * DoorEdges represent such connections. Also, each DoorNode has
 * a corresponding DoorNode in another room ("the other side"
 * of the door).
 */
public interface DoorNode {
    /** Get the Door object associated with this door */
    Door getDoor();
    /** Get the Room object associated (containing) with this door */
    Room getRoom();

    /** Probability weight that the intruder is in this node.
     *
     * Probability returned is a sum of the probability of the intruder
     * staying in this node or being in process of moving to another
     * door in this room (see DoorEdge for details).
     * Returned weight might be compared to other weights retreived
     * from other nodes in the same time step.
     */
    int getProbability();
    /** Get array of robots currently in the DoorNode */
    Robot[] getRobots();
}
