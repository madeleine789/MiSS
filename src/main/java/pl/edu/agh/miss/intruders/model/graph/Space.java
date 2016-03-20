package pl.edu.agh.miss.intruders.model.graph;

public class Space extends Node {
    private String spaceId;
    private double probability = 0.01;

    public Space(String nodeId, String spaceId) {
        super(nodeId);
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}
