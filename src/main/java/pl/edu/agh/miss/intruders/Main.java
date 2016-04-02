package pl.edu.agh.miss.intruders;

import pl.edu.agh.miss.intruders.model.graph.Building;
import pl.edu.agh.miss.intruders.service.IOService;
import pl.edu.agh.miss.intruders.view.GraphView;

import java.io.File;

public class Main {
    public static void main(String... args) throws Exception{
        File file;
        if (args.length < 2){
            file = new File(Main.class.getClassLoader().getResource("roson/simple.roson").getFile());
        } else {
            file = new File(args[1]);
        }

        Building b = IOService.importFromJson(file);
        System.out.println(b.toString());
        GraphView graphView = new GraphView().withMergedEdges(false).withNodeLabels(true).withEdgeLabels(true).withRobots(true);
        graphView.generateAndDisplay(b);
    }
}
