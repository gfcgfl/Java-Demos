package cc.gfcgfl.hotswap.listener;

import cc.gfcgfl.hotswap.loader.ClassHotLoader;
import cc.gfcgfl.hotswap.starter.AppStart;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

/**
 * @ClassName: FileAlterationListener
 * @Description:
 * @CreatedBy: xiaoguo
 * @CreatedAt: 2019/7/15 20:29
 **/
public class FileAlterationListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        if (file.getName().contains(".class")) {
            try {
                ClassHotLoader classHotLoader = ClassHotLoader.get(AppStart.classPath);
                classHotLoader.reset();
                AppStart.start0(classHotLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFileCreate(File file) {
        this.onFileChange(file);
    }

}
