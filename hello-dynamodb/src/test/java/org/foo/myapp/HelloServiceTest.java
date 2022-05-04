package org.foo.myapp;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloServiceTest extends BaseComponentTest {

    @Inject HelloService hello;

    @Test
    void hello() {
        assertEquals("foo-testHello", hello.fooHello());
    }
}
