package cc.gfcgfl.hotswap.loader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName: ClassHotLoader
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/15 15:23
 **/
public class ClassHotLoader {

    private static ClassHotLoader instance = null;
    private CustomClassLoader classLoader;
    private String classPath;

    private ClassHotLoader(String classPath) {
        this.classPath = classPath;
    }

    public static ClassHotLoader get(String classPath) {
        if (instance == null) {
            synchronized (ClassHotLoader.class) {
                if (instance == null) {
                    instance = new ClassHotLoader(classPath);
                }
            }
        }
        return instance;
    }

    /**
     * 自定义类加载引擎
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (this) {
            classLoader = new CustomClassLoader(this.classPath);
            //先去调用自定义classloader的findclass方法，一般正常的话这里就会返回Class实例
            //也就是这里正常情况下不会调用loadClass方法
            Class<?> findClass = classLoader.findClass(name);
            if (findClass != null) {
                return findClass;
            }
        }
        //由于在上面findClass方法可以实现重新读取class文件并返回相应的class实例，
        //所以正常情况下这里就不会执行了。
        return classLoader.loadClass(name);
    }

    public static class CustomClassLoader extends ClassLoader {

        private String classPath = null;

        public CustomClassLoader(String classPath) {
            //这里的CustomClassLoader不违背双亲委派模型，
            // 调用customClassLoader的loadClass依然会经历双亲模型那一套流程
            // 但是在本实验中，却没有调用它的loadClass方法。
            super(ClassLoader.getSystemClassLoader());
            this.classPath = classPath;
        }

        /**
         * 重写findClass
         */
        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {

            byte[] classByte = null;
            classByte = readClassFile(name);

            if (classByte == null || classByte.length == 0) {
                throw new ClassNotFoundException("ClassNotFound : " + name);
            }

            return this.defineClass(name, classByte, 0, classByte.length);
        }

        /**
         * 读取类文件
         *
         * @param name
         * @return
         * @throws ClassNotFoundException
         */
        private byte[] readClassFile(String name) throws ClassNotFoundException {

            String fileName = name.replace(".", "/") + ".class";

            File classFile = new File(this.classPath, fileName);
            if (!classFile.exists() || classFile.isDirectory()) {
                throw new ClassNotFoundException("ClassNotFound : " + name);
            }
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(classFile);
                int available = fis.available();
                int bufferSize = Math.max(Math.min(1024, available), 256);
                ByteBuffer buf = ByteBuffer.allocate(bufferSize);

                byte[] bytes = null;

                FileChannel channel = fis.getChannel();
                while (channel.read(buf) > 0) {
                    buf.flip();
                    bytes = traslateArray(bytes, buf);
                    buf.clear();
                }

                return bytes;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeIOQuiet(fis);
            }

            return null;
        }

        /**
         * 数组转换
         *
         * @param bytes
         * @param
         * @return
         */
        public byte[] traslateArray(byte[] bytes, ByteBuffer buf) {

            if (bytes == null) {
                bytes = new byte[0];
            }
            byte[] _array = null;
            if (buf.hasArray()) {
                _array = new byte[buf.limit()];
                System.arraycopy(buf.array(), 0, _array, 0, _array.length);
            } else {
                _array = new byte[0];
            }

            byte[] _implyArray = new byte[bytes.length + _array.length];
            System.arraycopy(bytes, 0, _implyArray, 0, bytes.length);
            System.arraycopy(_array, 0, _implyArray, bytes.length,
                    _array.length);
            bytes = _implyArray;
            return bytes;
        }

        /**
         * 关闭io流
         *
         * @param closeable
         */
        public static void closeIOQuiet(Closeable closeable) {

            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
