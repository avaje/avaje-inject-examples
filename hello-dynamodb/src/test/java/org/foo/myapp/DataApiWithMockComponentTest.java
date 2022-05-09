package org.foo.myapp;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@InjectTest
class DataApiWithMockComponentTest {

  @Mock AmazonDynamoDB dynamoDB;

  @Inject DataApi data;

  @BeforeEach
  void setup() {
    GetItemResult stubResult = new GetItemResult();
    stubResult.addItemEntry("name", new AttributeValue("stub"));
    when(dynamoDB.getItem(any())).thenReturn(stubResult);
  }

  @Test
  void get() {
    DataRecord rob = data.get("I-Don't-Care");

    assertThat(rob.name()).isEqualTo("stub");
    verify(dynamoDB).getItem(any());
  }

}
