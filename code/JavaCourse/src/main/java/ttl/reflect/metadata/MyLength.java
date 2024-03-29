package ttl.reflect.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MyLength {

	int min();
	int max() default Integer.MAX_VALUE;
}