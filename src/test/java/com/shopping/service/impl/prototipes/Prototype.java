package com.shopping.service.impl.prototipes;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

public abstract class Prototype {

  static Fairy fairy;
  static TextProducer textProducer;

  static {
    fairy = Fairy.create();
    textProducer = fairy.textProducer();
  }

  public static String buildString() {
    return textProducer.randomString(10);
  }

}
