package ttl.reflect.metadata;

/**
 * This example illustrates the
 * use of the @Override annotation
 */
public class OverrideExample {
  private String myValue;

  @Override
  //public String tostring() {
  public String toString() {
    return myValue;
  }
}
