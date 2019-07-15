package cc.gfcgfl.minimvc.starter;

import cc.gfcgfl.minimvc.beans.BeanFactory;
import cc.gfcgfl.minimvc.handler.MyMappingHandler;
import cc.gfcgfl.minimvc.handler.MyMappingHandlerManager;
import cc.gfcgfl.minimvc.core.ClassScanner;
import cc.gfcgfl.minimvc.server.TomcatServer;

import java.util.List;

/**
 * @ClassName: MiniApplication
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/18/19 8:45 PM
 **/
public class MiniApplication {
    public static void run(Class<?> cls) {
        System.out.println("start!!!!");

        TomcatServer tomcatServer = new TomcatServer();
        try {
//            tomcatServer.startServerTest();
            tomcatServer.startServer();

            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
//            classList.forEach((it) -> System.out.println(it.getName()));
            BeanFactory.initBean(classList);
            MyMappingHandlerManager.resolveMappingHander(classList);

            for (MyMappingHandler myMappingHandler : MyMappingHandlerManager.mappingHandlerList) {
                System.out.println(myMappingHandler.getUri() + ":" + myMappingHandler.getMethod());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
