package coffee;

import io.avaje.inject.test.InjectExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(InjectExtension.class)
class CoffeeMakerInjectExtensionTest {

  @Mock Heater heater;
  @Spy Pump pump;
  @Inject CoffeeMaker maker;

  @Test
  void testWith_mocksAndSpy() {
    doNothing().when(pump).pump();

    maker.brew();

    verify(pump).pump();
    verify(heater).on();
    verify(heater).off();
  }

}
