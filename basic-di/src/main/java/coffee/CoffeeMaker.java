package coffee;

import io.avaje.inject.PostConstruct;
import io.avaje.inject.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
class CoffeeMaker {

  private final Heater heater;

  private final Pump pump;

  @Inject
  CoffeeMaker(Heater heater, Pump pump) {
    this.heater = heater;
    this.pump = pump;
  }

  public void brew() {
    heater.on();
    pump.pump();
    System.out.println(" [_]P coffee! [_]P ");
    heater.off();
  }

  @PostConstruct
  public void onConst() {
    System.out.println("postConstruct");
  }


  @PreDestroy
  public void destroy() {
    System.out.println("destroy");
  }
}
