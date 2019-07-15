package cc.gfcgfl.hotswap.starter;

import cc.gfcgfl.hotswap.loader.ClassHotLoader;
import cc.gfcgfl.hotswap.observer.ClassFileObserver;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName: ClassLoaderTest
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/15 16:09
 **/
public class AppStart {

    public static void run(Class<?> aClass) {
        final String classPath = aClass.getResource("/").getPath();
        final String className = "cc.gfcgfl.hotswap.test.Person";
        final String fileName = className.replace(".", "/") + ".class";

        File f = new File(classPath, fileName);
        ClassFileObserver cfo = new ClassFileObserver(f.getAbsolutePath());

        cfo.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                try {

                    Object[] loadTimes = (Object[]) arg;
                    System.out.println(loadTimes[0] + " <---> " + loadTimes[1]);// 新旧时间对比

                    Class<?> loadClass = ClassHotLoader.get(classPath)
                            .loadClass(className);
                    Object person = loadClass.newInstance();
                    Method sayHelloMethod = loadClass.getMethod("sayHello");
                    sayHelloMethod.invoke(person);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cfo.startObserve();
    }
}
