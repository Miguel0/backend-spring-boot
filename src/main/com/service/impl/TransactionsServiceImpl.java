package main.com.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.dao.TransactionsDAO;
import main.com.model.TransactionData;
import main.com.service.TransactionsService;

/**
 * This is the implementation of the methods that the service layer will expose
 * in order to fulfill the given requirements.
 * 
 * @author Miguel Isasmendi
 *
 */
@Service
public class TransactionsServiceImpl implements TransactionsService {
	@Autowired
	private TransactionsDAO transactionsDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<TransactionData> addTransaction(TransactionData transactionData) {
		return this.transactionsDAO.addTransaction(transactionData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TransactionData> getTransactions() {
		return this.transactionsDAO.getTransactions();
	}

}
