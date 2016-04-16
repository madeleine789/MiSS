package pl.edu.agh.miss.intruders.api;

public interface Config {
    float getNewProbability(float currentProbability, Robot robot);
}
