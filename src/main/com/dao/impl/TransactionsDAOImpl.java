package main.com.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.dao.StatisticsDAO;
import main.com.dao.TransactionsDAO;
import main.com.model.TransactionData;

@Service
public class TransactionsDAOImpl implements TransactionsDAO {
	@Autowired
	private StatisticsDAO statisticsDAO;

	private ConcurrentHashMap<Long, TransactionData> allTransactionsCollection = new ConcurrentHashMap<Long, TransactionData>();
	private AtomicLong idGenerator = new AtomicLong();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<TransactionData> addTransaction(TransactionData transactionData) {
		transactionData.setId(idGenerator.getAndIncrement());

		this.allTransactionsCollection.put(transactionData.getId(), transactionData);

		this.statisticsDAO.pushTransaction(transactionData);

		return Optional.of(transactionData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TransactionData> getTransactions() {
		return allTransactionsCollection.values().stream()
				.sorted((previous, actual) -> previous.getTimestamp().compareTo(actual.getTimestamp()) * -1)
				.collect(Collectors.toList());
	}

}
