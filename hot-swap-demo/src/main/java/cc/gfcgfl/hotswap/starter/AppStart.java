package cc.gfcgfl.hotswap.starter;

import cc.gfcgfl.hotswap.annotation.Animal;
import cc.gfcgfl.hotswap.listener.FileAlterationListener;
import cc.gfcgfl.hotswap.loader.ClassHotLoader;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: ClassLoaderTest
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/15 16:09
 **/
public class AppStart {


    public static String classPath = null;
    private static final String basePackage = "cc.gfcgfl.hotswap.impl";
    private static List<Class<?>> classInBasePackage;
    private static Class<Animal> annotationClass = Animal.class;
    private static String methodName = "sayHello";

    public static void run(Class<?> aClass) throws Exception {
        classPath = aClass.getResource("/").getPath().substring(1);
//        String absolutePath = getAbsolutePath(basePackage);
        ClassHotLoader classHotLoader = ClassHotLoader.get(classPath);
        classHotLoader.reset();
        startFileListener(classPath);
        start0(classHotLoader);
    }

    private static void startFileListener(String classPath) throws Exception {
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(classPath);

        //添加一个文件监听器
        fileAlterationObserver.addListener(new FileAlterationListener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start();
    }

    public static void start0(ClassHotLoader classHotLoader) throws Exception {
        componentScan(basePackage, classHotLoader);

        for (Class<?> aClass : classInBasePackage) {
            Object o = aClass.newInstance();
            Method method = aClass.getMethod(methodName);
            method.invoke(o);
        }
    }

    private static void componentScan(String basePackage, ClassHotLoader classHotLoader)
            throws Exception {
        String absolutePath = getAbsolutePath(basePackage);
        List<Class<?>> classes = new LinkedList<>();
        findComponents(classes, new File(absolutePath), classHotLoader);
        classInBasePackage = classes;
    }

    private static void findComponents(List<Class<?>> classes,
                                       File file, ClassHotLoader classHotLoader) throws ClassNotFoundException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File aFile : files) {
                    findComponents(classes, aFile, classHotLoader);
                }
            }
        } else if (file.getName().endsWith(".class")) {
            String _class = file.getAbsolutePath()
                    .replaceAll("\\\\", ".")
                    .replaceAll("/", ".");
            _class = _class.substring(classPath.length(), _class.lastIndexOf("."));
            Class<?> aClass = classHotLoader.loadClass(_class);
            if (aClass.isAnnotationPresent(annotationClass))
                classes.add(aClass);
        }
    }

    private static String getAbsolutePath(String basePackage) throws Exception {
        if (classPath == null || classPath.isEmpty())
            throw new Exception();
        String basePackagePath = basePackage.replace(".", "/");
        File f = new File(classPath, basePackagePath);
        return f.getAbsolutePath();
    }
}
