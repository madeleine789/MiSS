package pl.edu.agh.miss.intruders.model.graph;

public class Gate extends Node {
    private String gateId;

    public Gate(String nodeId, String gateId) {
        super(nodeId);
        this.gateId = gateId;
    }

    public String getGateId() {
        return gateId;
    }
}
