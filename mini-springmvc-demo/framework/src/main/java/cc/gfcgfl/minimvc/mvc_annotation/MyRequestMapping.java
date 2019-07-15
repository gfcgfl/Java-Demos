package cc.gfcgfl.minimvc.mvc_annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)  //在这里规定 为了简单，此注解只能添加在 方法上
// 用来模拟springmvc中的RequestMapping  所以在这里命名为MyRequestMapping
public @interface MyRequestMapping {

    String value();
}
