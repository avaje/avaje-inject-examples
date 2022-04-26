package org.foo.myapp;

import jakarta.inject.Singleton;

@Singleton
public class HelloService {

  final HelloData data;

  public HelloService(HelloData data) {
    this.data = data;
  }

  public String fooHello() {
    return "foo-" + data.hello();
  }
}
