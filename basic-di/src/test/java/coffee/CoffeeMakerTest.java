package coffee;

import aframework.MyController;
import io.avaje.inject.BeanScope;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class CoffeeMakerTest {

  @Test
  void brew() {
    try (BeanScope scope = BeanScope.newBuilder().build()) {
      scope.get(CoffeeMaker.class).brew();
    }
  }

  @Test
  void brew_listByAnnotation() {
    try (BeanScope scope = BeanScope.newBuilder().build()) {

      CoffeeMaker maker = scope.get(CoffeeMaker.class);
      maker.brew();

      List<MyController> list = scope.list(MyController.class);
      assertThat(list).hasSize(2);
    }
  }

  @Test
  void testWith_mocksAndSpy() {
    try (BeanScope scope = BeanScope.newBuilder()
      .forTesting()
      .withMock(Heater.class)
      .withSpy(Pump.class)
      .build()) {

      Pump pump = scope.get(Pump.class);

      doNothing().when(pump).pump();

      CoffeeMaker maker = scope.get(CoffeeMaker.class);
      maker.brew();

      verify(pump).pump();

      Heater heater = scope.get(Heater.class);
      verify(heater).on();
      verify(heater).off();
    }
  }
}
