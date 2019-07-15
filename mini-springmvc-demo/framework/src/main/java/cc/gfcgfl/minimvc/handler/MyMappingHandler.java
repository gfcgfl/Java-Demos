package cc.gfcgfl.minimvc.handler;

import cc.gfcgfl.minimvc.beans.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: MyMappingHandler
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/19/19 4:36 PM
 **/
public class MyMappingHandler {
    private String uri;
    private Method method;
    private Class<?> controllerClass;
    private String[] requestParams;

    public MyMappingHandler(String uri, Method method, Class<?> controllerClass, String[] requestParams) {
        this.uri = uri;
        this.method = method;
        this.controllerClass = controllerClass;
        this.requestParams = requestParams;
    }

    public String getUri() {
        return uri;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public String[] getRequestParams() {
        return requestParams;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
//System.out.println("into handle method");
        String[] args = new String[this.requestParams.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = req.getParameter(requestParams[i]);
        }
//        for (int i = 0; i < args.length; i++) {
//            System.out.print(args[i] + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < args.length; i++) {
//            System.out.print(requestParams[i] + " ");
//        }
//        System.out.println();

//        Object controller = this.controllerClass.newInstance();
        Object controller = BeanFactory.getBean(controllerClass);
        Object result = this.method.invoke(controller, args);
        String resultString = result.toString();
        System.out.println(resultString);
        resp.getWriter().print(resultString);
    }
}
