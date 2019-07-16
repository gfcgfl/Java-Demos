package cc.gfcgfl.hotswap.impl;

import cc.gfcgfl.hotswap.annotation.Animal;
import cc.gfcgfl.hotswap.interfaces.IAnimal;

/**
 * @ClassName: Cat
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/16 20:31
 **/
@Animal("cat")
public class Cat implements IAnimal {
    @Override
    public void sayHello() {
        System.out.println("miao miao");
    }
}
