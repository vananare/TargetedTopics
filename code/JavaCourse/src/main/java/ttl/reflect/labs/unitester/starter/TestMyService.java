package ttl.reflect.labs.unitester.starter;

/**
 * @author whynot
 */
public class TestMyService {

    @TestMethod
    public boolean testForEven() {
        MyService ms = new MyService();
        int result = ms.foo(4);

        return result == -5;
    }

    @TestMethod
    public boolean testForOdd() {
        MyService ms = new MyService();
        int result = ms.foo(7);

        return result == 5;
    }

    public void noAnnotation() {

    }

    @TestMethod
    public void testSomething() {

    }

    @TestMethod
    public boolean testSomething(int i) {
        return false;
    }
}
