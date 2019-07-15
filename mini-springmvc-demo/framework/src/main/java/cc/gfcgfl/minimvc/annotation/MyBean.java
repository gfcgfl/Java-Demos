package cc.gfcgfl.minimvc.annotation;


import java.lang.annotation.*;

/**
*@Description: similar to the annotation '@Bean'
*@Params:
*@Return:
*@CreatedAt: 7/15/19 1:01 PM
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBean {

}
