package cc.gfcgfl.hotswap.impl;

import cc.gfcgfl.hotswap.annotation.Animal;
import cc.gfcgfl.hotswap.interfaces.IAnimal;

/**
 * @ClassName: impl
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/15 16:06
 **/
@Animal("person")
public class Person implements IAnimal {
    @Override
    public void sayHello(){
        System.out.println("hello！");
    }
}
