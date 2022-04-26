package org.foo.myapp.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.TestScope;
import jakarta.inject.Named;
import org.foo.myapp.HelloData;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

/**
 * "Default" test dependencies.
 */
@TestScope
@Factory
class MyTestConfiguration {

  static final String awsRegion = "ap-southeast-2";

  static {
    System.setProperty("com.amazonaws.sdk.disableCbor", "true");
    System.setProperty("aws.region", awsRegion);
    System.setProperty("aws.accesskey", "localstack");
    System.setProperty("aws.secretkey", "localstack");
    System.setProperty("stack.name", "rob-test");
  }

  @Named("dynamoContainer")
  @Bean
  GenericContainer<?> dynamoDBContainer() {
    GenericContainer<?> container = new FixedHostPortGenericContainer<>("amazon/dynamodb-local:1.13.2")
      .withFixedExposedPort(9000, 8000)
      .withEnv("DEFAULT_REGION", awsRegion)
      .withEnv("AWS_ACCESS_KEY_ID", "localstack")
      .withEnv("AWS_SECRET_KEY", "localstack")
      .withCommand("-jar DynamoDBLocal.jar -inMemory -sharedDb");

    container.start();
    return container;
  }

  @Bean
  AmazonDynamoDB dynamoDB(GenericContainer<?> dynamoDBContainer) {
    assert dynamoDBContainer != null;
    var endpointUrl = String.format("http://localhost:%d", 9000);//dynamoDBContainer.getFirstMappedPort());
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
      .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("localstack", "localstack")))
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, awsRegion))
      .build();
    createTable(client);
    return client;
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
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
  }
}
