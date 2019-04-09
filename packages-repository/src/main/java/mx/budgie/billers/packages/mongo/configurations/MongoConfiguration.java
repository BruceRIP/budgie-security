/**
 * 
 */
package mx.budgie.billers.packages.mongo.configurations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mx.budgie.billers.packages.mongo.properties.PackageMongoYmlProperties;

/**
 * @author brucewayne
 *
 */
//@Configuration
//@EnableMongoRepositories(basePackages={"mx.budgie.billers.packages.mongo"})
public class MongoConfiguration {
	
	private final Logger LOGGER = LogManager.getLogger(getClass());
	
	@Autowired
	private PackageMongoYmlProperties ymlProp;	
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {		
		return new SimpleMongoDbFactory(new MongoClient(ymlProp.getProperty("host"),
				27017), ymlProp.getProperty("database"));
	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		return new MongoTemplate(mongoDbFactory());
	}
	
	public void testConnection(String collection) {
		MongoClient mongoClient = new MongoClient(ymlProp.getProperty("host"),
				Integer.valueOf(ymlProp.getProperty("port")));
		MongoDatabase mongoDatabase = mongoClient.getDatabase(ymlProp.getProperty("database"));		
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		long numberOfDocuments = mongoCollection.count();
		LOGGER.info("Total documents in '" + collection + "' collection [" + numberOfDocuments + "]");
		mongoClient.close();
	}

	public MongoClient createConnection() {
		MongoClient mongoClient = new MongoClient(ymlProp.getProperty("host"),
				Integer.valueOf(ymlProp.getProperty("port")));
		return mongoClient;
	}

	public String getDatabase() {
		return ymlProp.getProperty("database");
	}

	public String getHost() {
		return ymlProp.getProperty("host");
	}

	public int getPort() {
		return Integer.parseInt(ymlProp.getProperty("port"));
	}

}

