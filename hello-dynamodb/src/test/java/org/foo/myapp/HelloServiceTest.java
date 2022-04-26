package org.foo.myapp;

import jakarta.inject.Inject;
import org.foo.myapp.BaseTest;
import org.foo.myapp.HelloService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloServiceTest extends BaseTest {

    @Inject HelloService hello;

    @Test
    void hello() {
        assertEquals("foo-testHello", hello.fooHello());
    }
}
