package pl.edu.agh.miss.intruders.model;

public class Gate extends Node {
    private String gateId;

    public Gate(String nodeId, String gateId) {
        super(nodeId);
        this.gateId = gateId;
    }

    public String getGateId() {
        return gateId;
    }

    public String getLabel() {
        return this.getNodeId().substring(4) + " [" + this.getGateId().replace("Wall", "G") + "]";
    }
}
