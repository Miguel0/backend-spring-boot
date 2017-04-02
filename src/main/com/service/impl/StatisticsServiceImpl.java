package main.com.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.dao.StatisticsDAO;
import main.com.model.StatisticsData;
import main.com.service.StatisticsService;

/**
 * This is the implementation of the methods that the service layer will expose
 * in order to fulfill the given requirements.
 * 
 * @author Miguel Isasmendi
 *
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsDAO statisticsDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<StatisticsData> getLastMinuteStatistics() {
		return this.statisticsDAO.getLastMinuteStatistics();
	}

}
