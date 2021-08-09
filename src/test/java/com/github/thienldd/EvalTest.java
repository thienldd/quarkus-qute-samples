package com.github.thienldd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.qute.Engine;
import io.quarkus.qute.EngineBuilder;
import io.quarkus.qute.UserTagSectionHelper.Factory;
import io.quarkus.test.junit.QuarkusTest;
import java.util.Map;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class EvalTest {

  void configure(@Observes EngineBuilder builder) {
    System.out.println("Config qute");
    builder.addSectionHelper(new Factory("itemDetail", "itemDetail"));
  }

  @Inject
  Engine engine;

  @Test
  void test() {
    var items = Map.of(1, Map.of("quantity", 10, "unit", "kg"));
    assertEquals("10 kg",
        engine.parse("{#itemDetail itemId=1 myNestedContent=\"{item.quantity} {item.unit}\" /}")
            .data("items", items)
            .render());
  }
}
