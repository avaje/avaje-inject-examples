package org.foo.myapp;

import io.avaje.inject.BeanScope;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello");

    BeanScope context = BeanScope.newBuilder().build();
    if (context != null) {
      System.out.println("asd");
    }
    HelloService helloService = context.get(HelloService.class, null);
    System.out.println("asd");

    String foo = helloService.fooHello();
    if (foo != null) {
      int length = foo.length();
    }
    System.out.println();
  }
}
