package org.foo.myapp;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataApiComponentTest extends BaseComponentTest {

  @Inject DataApi data;

  @Test
  void put_get() {
    data.put(new DataRecord("key1", "rob", "other data"));
    data.put(new DataRecord("key2", "jason", "bar"));

    DataRecord rob = data.get("key1");
    assertThat(rob.key()).isEqualTo("key1");
    assertThat(rob.name()).isEqualTo("rob");
    assertThat(rob.other()).isEqualTo("other data");

    DataRecord jason = data.get("key2");
    assertThat(jason.name()).isEqualTo("jason");
  }

}
