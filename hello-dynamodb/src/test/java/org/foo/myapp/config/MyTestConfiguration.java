package org.foo.myapp.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.TestScope;
import io.ebean.test.containers.LocalstackContainer;
import org.foo.myapp.HelloData;

import java.util.List;

/**
 * "Default" test dependencies.
 */
@TestScope
@Factory
class MyTestConfiguration {

  @Bean
  LocalstackContainer localstackContainer() {
    LocalstackContainer container = LocalstackContainer
      .builder("0.14.2")
      .services("dynamodb")
      // .containerName("ut_localstack")
      // .awsRegion("ap-southeast-2")
      // .port(4566)
      .build();
    container.start();
    return container;
  }

  @Bean
  AmazonDynamoDB dynamoDB(LocalstackContainer container) {
    AmazonDynamoDB dynamoDB = container.dynamoDB();
    createTable(dynamoDB);
    return dynamoDB;
  }

  void createTable(AmazonDynamoDB client) {
    DynamoInit dynamoInit = new DynamoInit(client);
    dynamoInit.createTable("junk", List.of(new KeySchemaElement("key", KeyType.HASH)),
      List.of(new AttributeDefinition("key", ScalarAttributeType.S)),
      new ProvisionedThroughput(1L, 1L));
  }

  @Bean
  HelloData build() {
    return new TestHelloData();
  }

  static class TestHelloData implements HelloData {

    @Override
    public String hello() {
      return "testHello";
    }
  }

  private record DynamoInit(AmazonDynamoDB dynamoDB) {

    void createTable(String tableName, List<KeySchemaElement> keys, List<AttributeDefinition> attributes, ProvisionedThroughput throughput) {
      try {
        dynamoDB.createTable(new CreateTableRequest(attributes, tableName, keys, throughput));
      } catch (ResourceInUseException e) {
        System.out.println("dynamoDB table already created");
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
  }
}
