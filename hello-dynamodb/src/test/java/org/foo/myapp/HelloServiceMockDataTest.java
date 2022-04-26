package org.foo.myapp;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HelloServiceMockDataTest extends BaseTest {

  @Mock HelloData data;
  @Inject HelloService hello;

  @BeforeEach
  void before() {
    when(data.hello()).thenReturn("stubResult");
  }

  @Test
  void hello_using_stub() {
    assertEquals("foo-stubResult", hello.fooHello());
  }
}
