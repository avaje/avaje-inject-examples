package coffee.sub;

import io.avaje.inject.Component;
import io.avaje.inject.PostConstruct;
import io.avaje.inject.PreDestroy;

@Component
public class Widget  {

  public void doStuff() {
    System.out.println("=> => widget => =>");
  }

  @PostConstruct
  public void postConstruct() {
    System.out.println("Widget postConstruct");
  }


  @PreDestroy
  public void destroy() {
    System.out.println("Widget destroy");
  }
}
