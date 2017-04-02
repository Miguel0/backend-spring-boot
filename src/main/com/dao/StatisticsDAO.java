package main.com.dao;

import java.util.Optional;

import main.com.model.StatisticsData;
import main.com.model.TransactionData;

/**
 * This is the definition of the methods that the DAO layer will expose in order
 * to fulfill the given requirements.
 * 
 * @author Miguel Isasmendi
 *
 */
public interface StatisticsDAO {

	/**
	 * Returns an optional that could have statistics for the last 60 seconds,
	 * or not. It's expected than the client of this service should be able to
	 * deal with that situations.
	 * 
	 * @return optional potentially containing statistics data
	 */
	public Optional<StatisticsData> getLastMinuteStatistics();

	/**
	 * Trigger the mechanism to calculate the statistics given the configuration
	 * of that process and the timestamp.
	 */
	public void pushTransaction(TransactionData transactionData);

}
