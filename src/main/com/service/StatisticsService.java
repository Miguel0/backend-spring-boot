package main.com.service;

import java.util.Optional;
import main.com.model.StatisticsData;

/**
 * This is the definition of the methods that the service layer will expose in
 * order to fulfill the given requirements.
 * 
 * @author Miguel Isasmendi
 *
 */
public interface StatisticsService {
	/**
	 * Returns an optional that could have statistics for the last 60 seconds,
	 * or not. It's expected than the client of this service should be able to
	 * deal with that situations.
	 * 
	 * @return optional potentially containing statistics data
	 */
	public Optional<StatisticsData> getLastMinuteStatistics();

}
