package pl.edu.agh.miss.intruders.api;

/**
 * Created by tgs on 3/20/16.
 */
public interface Config {
    float getNewProbability(float currentProbability, Robot robot);
}
