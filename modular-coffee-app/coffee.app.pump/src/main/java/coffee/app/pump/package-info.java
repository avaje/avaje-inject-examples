@InjectModule(name = "coffee-pump", requiresPackages = HeatMarker.class, provides = Pump.class)
package coffee.app.pump;

import coffee.app.heater.HeatMarker;
import io.avaje.inject.InjectModule;
