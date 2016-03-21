package pl.edu.agh.miss.intruders.api;

import java.util.Queue;

/** Graph edge between 2 DoorNodes.
 *
 * All edges are directed. Every edge has a length (the distance
 * between the doors) which is represented by the number of time ticks
 * needed for a robot/intruder to move through it.
 */
public interface DoorEdge {
    /** Source DoorNode. */
    DoorNode getSource();
    /** Destination DoorNode. */
    DoorNode getDestination();

    /** Edge length (in time ticks). */
    int getLength();
    /** Probability transition events for the future steps.
     *
     * Queue of length equal to the length of the edge. Each element
     * describes how much of the intruder presence probability will
     * be moved through the node in the time step.
     */
    Queue<Integer> getIntruderQueue();
}
