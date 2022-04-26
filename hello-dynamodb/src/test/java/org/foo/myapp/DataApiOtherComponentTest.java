package org.foo.myapp;

import jakarta.inject.Inject;
import org.foo.myapp.BaseTest;
import org.foo.myapp.DataApi;
import org.foo.myapp.DataRecord;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataApiOtherComponentTest extends BaseTest {

  @Inject DataApi data;

  @Test
  void put_get() {
    data.put(new DataRecord("key3", "bob", "bazz"));

    DataRecord rob = data.get("key3");
    assertThat(rob.name()).isEqualTo("bob");
    assertThat(rob.other()).isEqualTo("bazz");
  }

}
