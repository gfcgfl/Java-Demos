package cc.gfcgfl.minimvc.annotation;

import java.lang.annotation.*;

/**
*@Description: similar to the annotation '@AutoWired'
*@Params:
*@Return:
*@CreatedAt: 7/15/19 1:10 PM
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAutoWired {

}
