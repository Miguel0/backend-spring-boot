package main.com.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.com.model.StatisticsData;

/**
 * This is the definition of the methods that the resource layer will expose in
 * order to fulfill the requirements given.
 * 
 * @author Miguel Isasmendi
 *
 */
@RestController
@RequestMapping(path = "/statistics", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
public interface StatisticsResource {
	/**
	 * Returns the data of the statistics related to the data held up to the
	 * last minute. In case there's no information given for that timeRange it
	 * returns a "NO_CONTENT" status code.
	 * 
	 * @return a ResponseEntity properly configured and containing the last
	 *         minute statistics.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<StatisticsData> getLastMinuteStatistics();
}
