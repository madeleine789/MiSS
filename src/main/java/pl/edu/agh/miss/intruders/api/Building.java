package pl.edu.agh.miss.intruders.api;

import java.util.List;

public interface Building {

    public List<Room> getRooms();

    public List<DoorNode> getDoorNodes();

    public List<Robot> getRobots();
}
