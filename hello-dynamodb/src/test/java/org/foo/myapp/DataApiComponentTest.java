package org.foo.myapp;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

@InjectTest
class DataApiComponentTest { // extends BaseComponentTest {

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

    // cleanup
    data.delete("key1");
    data.delete("key2");
  }

  @Test
  void put_get_other() {
    data.put(new DataRecord("otherKey", "bob", "bazz"));

    DataRecord rob = data.get("otherKey");
    assertThat(rob.name()).isEqualTo("bob");
    assertThat(rob.other()).isEqualTo("bazz");

    // cleanup
    data.delete("otherKey");
  }

  @Test
  void put_get_other2() {
    data.put(new DataRecord("otherKey", "notBob", "bazz"));

    DataRecord rob = data.get("otherKey");
    assertThat(rob.name()).isEqualTo("notBob");
    assertThat(rob.other()).isEqualTo("bazz");

    // cleanup
    data.delete("otherKey");
  }
}
