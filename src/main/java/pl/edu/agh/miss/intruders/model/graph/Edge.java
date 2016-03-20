package pl.edu.agh.miss.intruders.model.graph;

public class Edge {
    private String nodeToId;
    private String nodeFromId;
    private double cost;

    public void setNodeToId(String nodeToId) {
        this.nodeToId = nodeToId;
    }

    public void setNodeFromId(String nodeFromId) {
        this.nodeFromId = nodeFromId;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNodeFromId() {
        return nodeFromId;
    }

    public String getNodeToId() {
        return nodeToId;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return nodeFromId + " - " + cost + " -> " + nodeToId;
    }
}
