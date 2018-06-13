package cn.com.agree.ab.a4.server.harbor.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClOUD_REQUEST_OPERATION_TYPE {
	
	String OPERATION_VALUE() default "";
	
	String OPERATION_TYPE() default "";
	
}
