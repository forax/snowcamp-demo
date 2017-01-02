package io.snowcamp.papaya.test;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class Run {
  public static void main(String[] args) {
    JUnitCore junit = new JUnitCore();
    junit.addListener(new TextListener(System.out));
    junit.run(DBDocumentAPI.class,
        DBObjectAPI.class,
        DocumentTest.class);
  }
}
