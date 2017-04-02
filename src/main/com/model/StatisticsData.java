package main.com.model;

import java.io.Serializable;

/**
 * Represents the data that has been calculated on the server based on the
 * amount and the timestamp of the TransactionData.
 * 
 * @author Miguel Isasmendi
 *
 */
public class StatisticsData implements Serializable {
	private static final long serialVersionUID = 2285424489374750859L;

	private Double sum;
	private Double avg;
	private Double max;
	private Double min;
	private Long count;

	public StatisticsData() {
		this.sum = 0D;
		this.avg = 0D;
		this.max = null;
		this.min = null;
		this.count = 0L;
	}

	public Double getSum() {
		return sum;
	}

	public Double getAvg() {
		return avg;
	}

	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public Long getCount() {
		return count;
	}

	/**
	 * Subtracts and apply to this instance the amount value of the
	 * TransactionData received. This operation should be called carefully since
	 * it doesn't calculate the min or the max value for the elements from which
	 * the rest of the fields is calculated.
	 */
	public void substractAmountFrom(TransactionData transactionData) {
		if (transactionData == null) {
			return;
		}

		this.sum -= transactionData.getAmount();
		this.count--;
		this.avg = sum / count;
	}

	/**
	 * Adds and apply to this instance the amount value of the TransactionData
	 * received.
	 */
	public void addAmountFrom(TransactionData transactionData) {
		if (transactionData == null) {
			return;
		}

		Double amount = transactionData.getAmount();

		this.max = this.max == null ? amount : Math.max(this.max, amount);
		this.min = this.min == null ? amount : Math.min(this.min, amount);

		this.sum += amount;
		this.count++;
		this.avg = sum / count;
	}

	/**
	 * Copy method to favor the possibility of share the statistics data without
	 * letting someone to change.
	 * 
	 * @return a new instance of the same class than the receiver, with a copy
	 *         of the data that this holds by himself.
	 */
	public StatisticsData copy() {
		StatisticsData newData = new StatisticsData();

		newData.avg = this.avg;
		newData.count = this.count;
		newData.min = this.min;
		newData.max = this.max;
		newData.sum = this.sum;

		return newData;
	}

}
