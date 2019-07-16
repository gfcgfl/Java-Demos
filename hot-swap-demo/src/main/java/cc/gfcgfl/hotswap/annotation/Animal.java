package cc.gfcgfl.hotswap.annotation;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Animal {
    String value();
}
