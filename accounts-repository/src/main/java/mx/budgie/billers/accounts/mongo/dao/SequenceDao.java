package mx.budgie.billers.accounts.mongo.dao;

public interface SequenceDao {

	public long getAccountSequenceNext();
	
	public long getClientAuthSequenceNext();
}
