package cc.gfcgfl.minimvc.core;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @ClassName: ClassScanner
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/19/19 3:30 PM
 **/
public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
//        System.out.println(packageName);
//        System.out.println();
//        System.out.println();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
//            System.out.println(resource.getPath());
//            System.out.println("+++++++++++++++++++++++++++");
            String protocol = resource.getProtocol();
            if (protocol.contains("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath, path));
            } else if(protocol.contains("file")){
                String fileOrPath = resource.getFile();
                classList.addAll(getClassFromPath(fileOrPath, packageName));
            }
        }
        return classList;
    }

    private static List<Class<?>> getClassFromPath(String fileOrPath, String packageName) throws ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        List<String> listofClassAbsoluteName = new ArrayList<>();
        File[] files = new File(fileOrPath).listFiles();

        recursiveFindClass(listofClassAbsoluteName, files);

        int l = fileOrPath.length();

        for (String classAbsoluteName : listofClassAbsoluteName) {
            String className = packageName + classAbsoluteName.substring(l, classAbsoluteName.length()-6).replace("/",".");
            list.add(Class.forName(className));
        }

        return list;

    }

    private static void recursiveFindClass(List<String> list, File[] files){
        if(files == null)
            return;
        for (File file : files) {
            if(file.isFile() && file.getName().endsWith(".class")){
                list.add(file.getAbsolutePath());
            }else if(file.isDirectory()){
                recursiveFindClass(list, file.listFiles());
            }
        }
    }

    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
