package io.snowcamp.papaya.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  DBDocumentAPI.class,
  DBObjectAPI.class,
  DocumentTest.class
})
public class TestSuite {
  // empty
}