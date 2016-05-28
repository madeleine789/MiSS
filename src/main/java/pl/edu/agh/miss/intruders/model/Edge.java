package pl.edu.agh.miss.intruders.model;

public class Edge {
    private String nodeToId;
    private String nodeFromId;
    private double cost;
    private boolean hasRobot = false;

    public void setNodeToId(String nodeToId) {
        this.nodeToId = nodeToId;
    }

    public void setNodeFromId(String nodeFromId) {
        this.nodeFromId = nodeFromId;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setHasRobot(boolean hasRobot) {
        this.hasRobot = hasRobot;
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

    public boolean hasRobot() {
        return hasRobot;
    }

    @Override
    public String toString() {
        return nodeFromId + "->" + nodeToId;
    }
}
