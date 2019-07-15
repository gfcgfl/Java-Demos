package cc.gfcgfl.minimvc.mvc_annotation;

import java.lang.annotation.*;

/**
 * @ClassName: MyRequestParam
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/19/19 4:31 PM
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
// 用来模拟springmvc中的RequestParam 所以在这里命名为MyRequestParam
public @interface MyRequestParam {
    String value();
}
