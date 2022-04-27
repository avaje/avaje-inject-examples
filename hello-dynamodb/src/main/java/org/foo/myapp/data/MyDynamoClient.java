package org.foo.myapp.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.foo.myapp.DataApi;
import org.foo.myapp.DataRecord;

import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
class MyDynamoClient implements DataApi {

  final AmazonDynamoDB dynamoDB;

  @Inject
  MyDynamoClient(AmazonDynamoDB dynamoDB) {
    this.dynamoDB = dynamoDB;
  }

  @Override
  public void put(DataRecord record) {
    Map<String, AttributeValue> attr = new LinkedHashMap<>();
    attr.put("key", new AttributeValue(record.key()));
    attr.put("name", new AttributeValue(record.name()));
    attr.put("other", new AttributeValue(record.other()));

    PutItemRequest request = new PutItemRequest();
    request.withTableName("junk");
    request.setItem(attr);
    dynamoDB.putItem(request);
  }

  @Override
  public DataRecord get(String key) {
    GetItemRequest request = new GetItemRequest();
    request.withTableName("junk");
    request.withKey(key(key));
    request.withAttributesToGet("key", "name", "other");
    GetItemResult res = dynamoDB.getItem(request);
    return convert(res.getItem());
  }

  private DataRecord convert(Map<String, AttributeValue> item) {
    if (item == null) {
      throw new RuntimeException("No data");
    }
    return new DataRecord(attr(item, "key"), attr(item, "name"), attr(item, "other"));
  }

  private String attr(Map<String, AttributeValue> item, String key) {
    AttributeValue attributeValue = item.get(key);
    return (attributeValue == null) ? null : attributeValue.getS();
  }

  private Map<String, AttributeValue> key(String year) {
    Map<String, AttributeValue> key = new LinkedHashMap<>();
    key.put("key", new AttributeValue(year));
    return key;
  }
}
