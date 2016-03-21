package pl.edu.agh.miss.intruders.api.intruder;

import pl.edu.agh.miss.intruders.api.DoorNode;

/**
 * Created by tgs on 3/21/16.
 */
public interface DoorEdge extends pl.edu.agh.miss.intruders.api.DoorEdge {
    DoorNode getSource();
    DoorNode getDestination();
    int getLength();

    /** Get the probability of the intruder moving through this edge. */
    int getProbability();
    /** Set the probability of the intruder moving through this edge. */
    void setProbability(int weight);
}
