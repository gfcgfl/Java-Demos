package cc.gfcgfl.minimvc.mvc_annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// 用来模拟springmvc中的Controller  所以在这里命名为MyController
public @interface MyController {
    // 这个注解没有属性值
}
