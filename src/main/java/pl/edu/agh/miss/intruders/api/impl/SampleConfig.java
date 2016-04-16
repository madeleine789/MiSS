package pl.edu.agh.miss.intruders.api.impl;

import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.Robot;

public class SampleConfig implements Config {

	@Override
	public float getNewProbability(float currentProbability, Robot robot) {
		if (robot!=null) {
			return currentProbability/10;
		}
		return currentProbability;
	}

}
