package pl.edu.agh.miss.intruders.model;

public class Space extends Node {
    private String spaceId;

    public Space(String nodeId, String spaceId) {
        super(nodeId);
        this.spaceId = spaceId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public String getLabel() {
        return this.getNodeId().substring(4) + " [" + this.getSpaceId().replace("Space", "S") + "]";
    }
}
