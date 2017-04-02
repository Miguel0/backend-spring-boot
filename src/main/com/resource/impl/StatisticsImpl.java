package main.com.resource.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import main.com.model.StatisticsData;
import main.com.resource.StatisticsResource;
import main.com.service.StatisticsService;

/**
 * This is the implementation of the methods that the resource layer will expose
 * in order to fulfill the requirements given.
 * 
 * @author Miguel Isasmendi
 *
 */
@RestController
public class StatisticsImpl implements StatisticsResource {

	@Autowired
	private StatisticsService statisticsService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<StatisticsData> getLastMinuteStatistics() {
		Optional<StatisticsData> statistics = this.statisticsService.getLastMinuteStatistics();
		ResponseEntity<StatisticsData> response;

		if (statistics.isPresent()) {
			response = ResponseEntity.ok(statistics.get());
		} else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		return response;
	}

}
