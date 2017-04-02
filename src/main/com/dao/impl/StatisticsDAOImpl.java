package main.com.dao.impl;

import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import main.com.dao.StatisticsDAO;
import main.com.model.StatisticsData;
import main.com.model.TransactionData;

@Service
public class StatisticsDAOImpl implements StatisticsDAO {
	private static final Logger logger = LoggerFactory.getLogger(StatisticsDAOImpl.class);

	private StatisticsData lastStatisticData;
	private DelayQueue<DelayedTransactionData> delayedAdditionQueue;
	private ReadWriteLock readWriteLock;

	public StatisticsDAOImpl() {
		this.lastStatisticData = new StatisticsData();
		this.readWriteLock = new ReentrantReadWriteLock();
		this.delayedAdditionQueue = new DelayQueue<DelayedTransactionData>();
		DelayQueue<DelayedTransactionData> delayedRemotionQueue = new DelayQueue<DelayedTransactionData>();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {

						DelayedTransactionData delayedTransactionData = delayedAdditionQueue.take();
						logger.info(Thread.currentThread().getName() + " - Data retrieved from addition queue...");

						StatisticsDAOImpl.this.readWriteLock.writeLock().lock();

						logger.info(Thread.currentThread().getName() + " - Augmenting transactions statistics...");
						StatisticsDAOImpl.this.lastStatisticData
								.addAmountFrom(delayedTransactionData.getTransactionData());

						StatisticsDAOImpl.this.readWriteLock.writeLock().unlock();

						logger.info(Thread.currentThread().getName()
								+ " - Preparing the delayed object to be subtracted of the statistics 60 seconds from now");
						delayedTransactionData.setTimestamp(delayedTransactionData.getTimestamp() + 60000);

						logger.info(Thread.currentThread().getName()
								+ " - Signaling the remotion queue of a new delayed object");
						delayedRemotionQueue.offer(delayedTransactionData);

					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}, "Statistic Transaction Appender Thread").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						logger.info(Thread.currentThread().getName() + " - waiting for data in range to expire...");

						TransactionData transactionData = delayedRemotionQueue.take().getTransactionData();

						StatisticsDAOImpl.this.readWriteLock.writeLock().lock();

						boolean isMaxAmount = StatisticsDAOImpl.this.lastStatisticData.getMax()
								.equals(transactionData.getAmount());
						boolean isMinAmount = StatisticsDAOImpl.this.lastStatisticData.getMin()
								.equals(transactionData.getAmount());

						if (isMaxAmount || isMinAmount) {
							logger.info(Thread.currentThread().getName()
									+ " - creating a new StatisticsData to recalculate the indices...");
							StatisticsDAOImpl.this.lastStatisticData = new StatisticsData();

							// I have to iterate again since I don't have the
							// certainty that the amount of the TransactionData
							// that I've removed from the collection is the only
							// repetition to be took into account for the Max or
							// Min statistics, or who is next in terms of the
							// Max or Min function.
							delayedRemotionQueue.stream().map(DelayedTransactionData::getTransactionData)
									.forEach(StatisticsDAOImpl.this.lastStatisticData::addAmountFrom);
						} else {
							logger.info(Thread.currentThread().getName()
									+ " - Since it's an intermediate value, it's safe to remove it aritmetically with no regards for his identity...");
							StatisticsDAOImpl.this.lastStatisticData.substractAmountFrom(transactionData);
						}

						logger.info(Thread.currentThread().getName() + " - Statistic indices recalculated!!!!");

						readWriteLock.writeLock().unlock();
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}, "Statistic Transaction Remover Thread").start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<StatisticsData> getLastMinuteStatistics() {

		StatisticsData retrievedData = null;

		this.readWriteLock.readLock().lock();

		// multiple readers can enter this section
		// if not locked for writing, and not writers waiting
		// to lock for writing.

		if (this.lastStatisticData.getCount() > 0) {
			// copy the data to avoid it being tempered on the
			// other layers affecting the real stored data.
			retrievedData = this.lastStatisticData.copy();
		}

		readWriteLock.readLock().unlock();

		return Optional.ofNullable(retrievedData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void pushTransaction(TransactionData transactionData) {
		if (transactionData == null) {
			return;
		}

		long farDistantTimeLimit = System.currentTimeMillis() - 60000;

		boolean shouldSetStatisticsAsActual = transactionData.getTimestamp() >= farDistantTimeLimit;

		if (shouldSetStatisticsAsActual) {
			this.delayedAdditionQueue.offer(new DelayedTransactionData(transactionData));
			logger.info("Data added to the addition queue!!!");
		} else {
			logger.info("Data not took into account as per being older than " + farDistantTimeLimit + " by "
					+ (farDistantTimeLimit - transactionData.getTimestamp()) + "!!!!");
		}
	}

	/**
	 * This class is the one that will represent the transaction data inside of
	 * the delayed queues and facilitate the calculation of the salaries of
	 * them.
	 * 
	 * @author Miguel Isasmendi
	 *
	 */
	private class DelayedTransactionData implements Delayed {

		private TransactionData transactionData;
		private Long timestamp;

		public DelayedTransactionData(TransactionData transactionData) {
			this.transactionData = transactionData;
			this.timestamp = this.transactionData.getTimestamp();
		}

		public TransactionData getTransactionData() {
			return this.transactionData;
		}

		public Long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public int compareTo(Delayed arg0) {
			if (arg0 instanceof DelayedTransactionData) {
				return this.timestamp.compareTo(((DelayedTransactionData) arg0).timestamp);
			}

			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			long diff = this.timestamp - System.currentTimeMillis();
			logger.info("timestamp = " + this.timestamp + " minus the current time: " + System.currentTimeMillis()
					+ " = " + diff);
			return unit.convert(diff, TimeUnit.MILLISECONDS);
		}

	}

}
