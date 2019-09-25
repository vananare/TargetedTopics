package ttl.reflect.labs.unitester.starter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author whynot
 */
public class UnitTester {

    public List<String> runTests(Class<?> testClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        Object testInstance = testClass.newInstance();

        Method[] methods = testClass.getDeclaredMethods();

        List<String> result  = new ArrayList<>();
        for(Method m : methods) {
            if(m.isAnnotationPresent(TestMethod.class)) {
                //Check for no Arguments
                if(m.getModifiers() == Modifier.PUBLIC && m.getParameterCount() == 0) {
                    if(m.getReturnType() == boolean.class || m.getReturnType() == Boolean.class) {
                        Boolean b = (Boolean)m.invoke(testInstance);
                        result.add("method " + m.getName() + " result: " + b);
                    }
                }
            }
        }
        return result;
    }


    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
       UnitTester tester = new UnitTester();

       List<String> results = tester.runTests(TestMyService.class);

       results.forEach(System.out::println);
    }

}
