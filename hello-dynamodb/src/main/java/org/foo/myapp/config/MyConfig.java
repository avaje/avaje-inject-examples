package org.foo.myapp.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import org.foo.myapp.HelloData;

@Factory
public class MyConfig {

  @Bean
  HelloData data() {
    return new MainHelloData();
  }

  @Bean
  AmazonDynamoDB dynamoDB() {
    throw new IllegalStateException("Not ready yet");
  }

  private static class MainHelloData implements HelloData {

    @Override
    public String hello() {
      return "mainHello";
    }
  }
}
