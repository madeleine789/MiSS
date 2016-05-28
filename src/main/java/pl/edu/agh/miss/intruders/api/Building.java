package pl.edu.agh.miss.intruders.api;

import java.util.List;

public interface Building {

    List<Room> getRooms();

    List<DoorNode> getDoorNodes();

    List<Robot> getRobots();
}
