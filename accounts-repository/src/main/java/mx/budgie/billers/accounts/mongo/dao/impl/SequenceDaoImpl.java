package mx.budgie.billers.accounts.mongo.dao.impl;

import mx.budgie.billers.accounts.mongo.dao.SequenceDao;
import mx.budgie.billers.accounts.mongo.documents.AccountAuthSequenceDocument;
import mx.budgie.billers.accounts.mongo.documents.ClientAuthSequenceDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceDaoImpl implements SequenceDao {

	@Autowired
	public MongoTemplate mongoTemplate;

	public long getAccountSequenceNext(){		
		Query query = new Query(Criteria.where("sequence").gt(0));
		Update update = new Update().inc("sequence", 1);
		FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
		AccountAuthSequenceDocument sequenceID = mongoTemplate.findAndModify(query,update, options, AccountAuthSequenceDocument.class);
		if (sequenceID == null) {			
			mongoTemplate.insert(new AccountAuthSequenceDocument(1));
			sequenceID = mongoTemplate.findAndModify(query, update, options,AccountAuthSequenceDocument.class);
		}
		return sequenceID.getSequence();
	}

	@Override
	public long getClientAuthSequenceNext() {
		Query query = new Query(Criteria.where("sequence").gt(0));
		Update update = new Update().inc("sequence", 1);
		FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
		ClientAuthSequenceDocument sequenceID = mongoTemplate.findAndModify(query,update, options, ClientAuthSequenceDocument.class);
		if (sequenceID == null) {			
			mongoTemplate.insert(new ClientAuthSequenceDocument(1));
			sequenceID = mongoTemplate.findAndModify(query, update, options,ClientAuthSequenceDocument.class);
		}
		return sequenceID.getSequence();
	}
}
