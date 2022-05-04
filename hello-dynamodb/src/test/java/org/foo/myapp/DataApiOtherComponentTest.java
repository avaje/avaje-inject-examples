package org.foo.myapp;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataApiOtherComponentTest extends BaseComponentTest {

  @Inject DataApi data;

  @Test
  void put_get() {
    data.put(new DataRecord("key3", "bob", "bazz"));

    DataRecord rob = data.get("key3");
    assertThat(rob.name()).isEqualTo("bob");
    assertThat(rob.other()).isEqualTo("bazz");
  }

}
