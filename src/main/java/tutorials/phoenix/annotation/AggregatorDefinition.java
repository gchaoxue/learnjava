package tutorials.phoenix.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotating the business method which handle the business message.
 *
 * Created by gchaoxue on 2018/12/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AggregatorDefinition {

    String aggregatorId();
}
