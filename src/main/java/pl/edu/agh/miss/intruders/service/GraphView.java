package pl.edu.agh.miss.intruders.service;


import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.ui.view.Viewer;
import pl.edu.agh.miss.intruders.model.RosonBuilding;
import pl.edu.agh.miss.intruders.model.Edge;
import pl.edu.agh.miss.intruders.model.Node;
import pl.edu.agh.miss.intruders.service.utils.ColorUtils;

import java.io.IOException;

public class GraphView {

    private boolean mergedEdges = false;
    private boolean nodeLabels = false;
    private boolean edgeLabels = false;
    private boolean robots = false;
    public boolean screenshots = false;
    private FileSinkImages fsi;

    public Graph generate(RosonBuilding building) {
        if(!mergedEdges)
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = new MultiGraph("building");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        Viewer viewer = graph.display();
        if(building.needsAutomaticLayout())
            viewer.enableAutoLayout();
        else viewer.disableAutoLayout();
        
        int i = 0;
        for (Edge edge : building.getEdges()) {
            String start, end;
            if (building.isGate(edge.getNodeFromId())) start = building.getGates().get(edge.getNodeFromId()).getLabel();
            else start = building.getSpaces().get(edge.getNodeFromId()).getLabel();

            if (building.isGate(edge.getNodeToId())) end = building.getGates().get(edge.getNodeToId()).getLabel();
            else end = building.getSpaces().get(edge.getNodeToId()).getLabel();

            org.graphstream.graph.Edge e = graph.addEdge(String.valueOf(i), start, end, true);
            if(edgeLabels) {
                e.addAttribute("ui.label", String.valueOf(edge.getCost()));
                e.addAttribute("ui.style", "text-alignment: under;");
            }
            e.addAttribute("id", edge.toString());
            i++;
        }

        for (org.graphstream.graph.Node node : graph) {
            String id = "Node" + node.getId().substring(0, node.getId().indexOf(" "));
            Node n = building.getNode(id);
            if(!building.needsAutomaticLayout()) {
                node.setAttribute("x", n.getX());
                node.setAttribute("y", n.getY());
            }
            node.addAttribute("ui.style", "stroke-mode: plain; stroke-color: rgb(0,0,0);");

            if (building.isSpace(id)) {
                node.addAttribute("ui.style", "shape : box; size: 15px, 15px;");
                node.addAttribute("ui.style", "fill-color:  black;");
            } else if (building.isGate(id)) {
                String color = ColorUtils.getRGBString(ColorUtils.probabilityToColor(n.getProbability()));
                node.addAttribute("ui.style", "fill-color:  " + color + ";");
                node.addAttribute("ui.style", "shape : diamond; size: 15px, 15px;");
                if (robots && n.isRobotThere()) {
                    node.addAttribute("ui.style", "icon: url('./src/main/resources/robot.png'); icon-mode: at-left; ");
                }
            }

            if (nodeLabels) {
                node.addAttribute("ui.label", id);
                node.addAttribute("ui.style", "text-alignment: under;");
            }
        }
        fsi = new FileSinkImages(FileSinkImages.OutputType.PNG,FileSinkImages.Resolutions.HD720);
        graph.addSink(fsi);
        fsi.setQuality(FileSinkImages.Quality.HIGH);
        return graph;
    }

    public void makeScreenShot(Graph g, String img) {
        try {
            fsi.writeAll(g, img);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public GraphView withMergedEdges(boolean value) {
        this.mergedEdges = value;
        return this;
    }

    public GraphView withEdgeLabels(boolean value) {
        this.edgeLabels = value;
        return this;
    }

    public GraphView withNodeLabels(boolean value) {
        this.nodeLabels = value;
        return this;
    }

    public GraphView withRobots(boolean value) {
        this.robots = value;
        return this;
    }

    public GraphView withScreenshots(boolean value) {
        this.screenshots = value;
        return this;
    }
}

