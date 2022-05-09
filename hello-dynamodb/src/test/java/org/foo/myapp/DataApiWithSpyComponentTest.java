package org.foo.myapp;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import io.avaje.inject.BeanScope;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.TestBeanScope;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@InjectTest
class DataApiWithSpyComponentTest { // extends BaseComponentTest {

  @Spy AmazonDynamoDB dynamoDB;
  @Inject DataApi data;

  @Test
  void put_get() {
    data.put(new DataRecord("key3", "bob", "bazz"));

    DataRecord rob = data.get("key3");
    assertThat(rob.name()).isEqualTo("bob");
    assertThat(rob.other()).isEqualTo("bazz");

    verify(dynamoDB).putItem(Mockito.any());
  }

  @Test
  void put_get_programmatic() {
    try (var beanScope = TestBeanScope.builder()
      .forTesting()
      .spy(AmazonDynamoDB.class)
      .build()) {

      var dataApi = beanScope.get(DataApi.class);

      dataApi.put(new DataRecord("key30", "bobi", "bazz"));

      var bobi = dataApi.get("key30");
      assertThat(bobi.name()).isEqualTo("bobi");
      assertThat(bobi.other()).isEqualTo("bazz");

      var dynamoDB1 = beanScope.get(AmazonDynamoDB.class);
      verify(dynamoDB1).putItem(Mockito.any());
    }
  }

}
