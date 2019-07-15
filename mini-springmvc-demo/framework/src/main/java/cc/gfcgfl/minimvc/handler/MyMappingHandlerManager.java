package cc.gfcgfl.minimvc.handler;

import cc.gfcgfl.minimvc.mvc_annotation.MyController;
import cc.gfcgfl.minimvc.mvc_annotation.MyRequestMapping;
import cc.gfcgfl.minimvc.mvc_annotation.MyRequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: MyMappingHandlerManager
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/19/19 4:39 PM
 **/
public class MyMappingHandlerManager {
    public static List<MyMappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHander(List<Class<?>> classList){
        for (Class<?> aClass : classList) {
            if(aClass.isAnnotationPresent(MyController.class)){
                parseMappingHandlerFromController(aClass);
            }
        }
    }

    private static void parseMappingHandlerFromController(Class<?> controllerClass) {
        // get all methods
        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyRequestMapping.class)){
                String mappingUri = method.getDeclaredAnnotation(MyRequestMapping.class).value();
                List<String> requestParamList = new LinkedList<>();
                for (Parameter parameter : method.getParameters()) {
                    if(parameter.isAnnotationPresent(MyRequestParam.class)){
                        requestParamList.add(parameter.getDeclaredAnnotation(MyRequestParam.class).value());
                    }else{
                        //如果没有用requestParam注解，那使用方法参数的名字
                        requestParamList.add(parameter.getName());
                    }
                }
                String[] requestParams = requestParamList.toArray(new String[requestParamList.size()]);
                MyMappingHandler mappingHandler = new MyMappingHandler(mappingUri, method, controllerClass, requestParams);
                mappingHandlerList.add(mappingHandler);
            }
        }
    }
}
