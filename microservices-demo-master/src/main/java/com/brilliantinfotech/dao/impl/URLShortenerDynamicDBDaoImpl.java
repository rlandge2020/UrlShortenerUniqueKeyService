package com.brilliantinfotech.dao.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.brilliantinfotech.dao.URLShortenerDynamicDBDao;
import com.brilliantinfotech.utility.RandomKeyGenerator;
import com.brilliantinfotech.utility.UniqueKeyResponse;

/**
 * Implementation for getting an unique key from the Key Generation Service
 * (KGS).
 * 
 * @author Rahul Landge
 *
 */
@Component
public class URLShortenerDynamicDBDaoImpl implements URLShortenerDynamicDBDao {

	private static final String TABLE_NAME_FOR_UNIQUE_KEY = "UNIQUE_KEY";

	private static final String UNIQUE_KEY_COLUMN_NAME = "URL_SHORTENER_UNIQUE_KEY";

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;	

	public URLShortenerDynamicDBDaoImpl() {			
	}

	@Override
	public UniqueKeyResponse getUniqueKey() {
		String key = null;
		UniqueKeyResponse respGetKey = new UniqueKeyResponse();
		try {
						
			ScanRequest scanRequest = new ScanRequest()
				    .withTableName(TABLE_NAME_FOR_UNIQUE_KEY);			
			scanRequest.setLimit(1);

				ScanResult result = amazonDynamoDB.scan(scanRequest);
	
				for (Map<String, AttributeValue> item : result.getItems()) {
					AttributeValue attrib =   item.get(UNIQUE_KEY_COLUMN_NAME);
					key = attrib.getS();
				}
			if (Objects.nonNull(key)) {	
				respGetKey.setKey(key);
				respGetKey.setStatusCode("000");
				respGetKey.setStatusMessage("Retrieved Key Successfully");
			}
		} catch (Exception e) {
			respGetKey.setStatusCode("500");
			respGetKey.setStatusMessage("Exception in retrieving key:" + e.getMessage());

		}
		return respGetKey;
	}

	@Override
	public UniqueKeyResponse deleteKey(String key) {
		 DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table table = dynamoDB.getTable(TABLE_NAME_FOR_UNIQUE_KEY);
		UniqueKeyResponse resp = new UniqueKeyResponse();
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
				.withPrimaryKey(new PrimaryKey(UNIQUE_KEY_COLUMN_NAME, key));
		// Conditional delete (we expect this to fail)

		try {
			System.out.println("Attempting a conditional delete...");
			table.deleteItem(deleteItemSpec);
			System.out.println("DeleteItem succeeded");
			resp.setStatusCode("000");
			resp.setStatusMessage("Success:Item deleted successfully");

		} catch (Exception e) {
			System.err.println("Unable to delete item: " + key);
			System.err.println(e.getMessage());
			resp.setStatusCode("500");
			resp.setStatusMessage("Exception:Failed to delete message:" + e.getMessage());
		}

		return resp;
	}

	@Override
	public UniqueKeyResponse createKeys(int numberOfKeys) {
		DynamoDBMapper dynamoDBMapper =  new DynamoDBMapper(amazonDynamoDB);
	//	System.out.println("DynamoDB:" + amazonDynamoDB.listTables().getTableNames());
		UniqueKeyResponse respCreateKey = new UniqueKeyResponse();
		AtomicInteger counter = new AtomicInteger();
		//createTable();
		try {			
			for (int i = 0; i < numberOfKeys; i++) {
				UniqueKey key = new UniqueKey();
				key.setKey(RandomKeyGenerator.generateRandomString(8));
				key.setReadOnlyFlag("N");
				dynamoDBMapper.save(key);
				counter.incrementAndGet();
			}
		} catch (Exception e) {
			System.out.println("Error saving key:" + e.getMessage());

		}
		respCreateKey.setStatusCode("000");
		respCreateKey.setStatusMessage(counter.get() + "  keys generated and stored in databases");
		return respCreateKey;
	}

	private void createTable() {
		 DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
	//	if (!isTableExist(TABLE_NAME_FOR_UNIQUE_KEY)) {
			try {

				System.out.println("Attempting to create table; please wait...");
				Table table = dynamoDB.createTable(TABLE_NAME_FOR_UNIQUE_KEY,
						Arrays.asList(new KeySchemaElement("ID", KeyType.HASH), // Partition
								// key
								new KeySchemaElement("ID", KeyType.RANGE)), // Sort key
						Arrays.asList(new AttributeDefinition("ID", ScalarAttributeType.S),
								new AttributeDefinition(UNIQUE_KEY_COLUMN_NAME, ScalarAttributeType.S)),
						new ProvisionedThroughput(10L, 10L));
				table.waitForActive();
				System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
			} catch (Exception e) {
				System.err.println("Unable to create table: ");
				System.err.println(e.getMessage());
			}
		//}
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */

	private boolean isTableExist(String tableName) {
		 DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table dynamodbTable = dynamoDB.getTable(tableName);
		System.out.println("DynamoDb table:" + dynamodbTable.getTableName());
		TableDescription tableDescription = null;
		try {
			if (dynamodbTable!=null)
			 tableDescription = dynamodbTable.describe();
			System.out.println("Table description: " + tableDescription.getTableStatus());
			return true;
		} catch (com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException rnfe) {
			System.out.println("Table does not exist");
		}
		return false;
	}
	
	
	/**
	 * @return the client
	 */
	public AmazonDynamoDB getAmazonDynamoDB() {
		return amazonDynamoDB;
	}

	/**
	 * @param client the client to set
	 */
	public void setAmazonDynamoDB(AmazonDynamoDB amazonDynamoDB) {
		this.amazonDynamoDB = amazonDynamoDB;
	}
}
