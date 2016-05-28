package pl.edu.agh.miss.intruders.model;

import java.util.*;

public class Node {
    private final static float DEFAULT_PROBABILITY = 0.1f;
    private String nodeId;
    private List<Edge> incidentEdges = new ArrayList<>();
    private Set<Node> incidentNodes = new HashSet<>();
    private float probability = DEFAULT_PROBABILITY;
    private boolean isRobotThere = false;
    private boolean isIntruderThere = false;
    private double x;
    private double y;

    public Node(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<Edge> getIncidentEdges() {
        return incidentEdges;
    }

    public void setIncidentEdges(List<Edge> incidentEdges) {
        this.incidentEdges = incidentEdges;
    }

    public Set<Node> getIncidentNodes() {
        return incidentNodes;
    }

    public void setIncidentNodes(Set<Node> incidentNodes) {
        this.incidentNodes = incidentNodes;
    }

    public String getNodeId() {
        return nodeId;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = (float) probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isRobotThere() {
        return isRobotThere;
    }

    public void isRobotThere(boolean isRobotThere) {
        this.isRobotThere = isRobotThere;
    }

    public boolean isIntruderThere() {
        return isIntruderThere;
    }

    public void isIntruderThere(boolean isIntruderThere) {
        isIntruderThere = isIntruderThere;
    }

    public boolean deleteEdge(String from, String to) {
        Optional<Edge> edgeOp = incidentEdges.stream().filter(
                e -> Objects.equals(e.getNodeFromId(), from) && Objects.equals(e.getNodeToId(), to)
        ).findFirst();
        if (edgeOp.isPresent()) {
            incidentEdges.remove(edgeOp.get());
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t").append(getNodeId()).append("\n");
        getIncidentEdges().forEach(edge -> sb.append("\t\t").append(edge.toString()).append("\n"));
        return sb.toString();
    }

}


