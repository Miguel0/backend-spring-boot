package main.com.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the data received that will be used to calculate the statistics
 * needed for the application.
 * 
 * @author Miguel Isasmendi
 *
 */
@JsonAutoDetect
public class TransactionData {
	private Long id;
	private Double amount;
	private Long timestamp;

	public TransactionData() {
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public boolean validate() {
		// TODO check if the amount could have negative values
		return this.timestamp != null && this.timestamp > 0 && this.amount != null;
	}

}
