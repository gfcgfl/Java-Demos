package cc.gfcgfl.hotswap.impl;

import cc.gfcgfl.hotswap.annotation.Animal;
import cc.gfcgfl.hotswap.interfaces.IAnimal;

/**
 * @ClassName: Dog
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/15 20:03
 **/
@Animal("dog")
public class Dog implements IAnimal {
    @Override
    public void sayHello(){
        System.out.println("Wong!!！");
    }
}
