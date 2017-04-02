package main.com.model;

import java.io.Serializable;

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

	public void addAmountFrom(TransactionData transactionData) {
		Double amount = transactionData.getAmount();

		this.max = this.max == null ? amount : Math.max(this.max, amount);
		this.min = this.min == null ? amount : Math.min(this.min, amount);
		
		this.sum += amount;
		this.count++;
		this.avg = sum / count;
	}
	
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
