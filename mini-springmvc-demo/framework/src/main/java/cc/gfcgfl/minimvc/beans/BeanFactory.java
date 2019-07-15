package cc.gfcgfl.minimvc.beans;


import cc.gfcgfl.minimvc.annotation.MyAutoWired;
import cc.gfcgfl.minimvc.annotation.MyBean;
import cc.gfcgfl.minimvc.mvc_annotation.MyController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: BeanFactory
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/21/19 10:23 AM
 **/
public class BeanFactory {
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        Map<Class<?>, Object> notCreateFinishedBeanMap = new HashMap<>();
        while (toCreate.size() != 0) {
            //在这一轮创建之前，先记录列表里面还有多少个类
            int size = toCreate.size();
//            for (int i = 0; i < toCreate.size(); i++) {
//                if (finishCreateBean(toCreate.get(i), notCreateFinishedBeanMap)) {
//                    toCreate.remove(i);
//                }
//            }
            int i = 0;
            while (toCreate.size() > i) {
                if (finishCreateBean(toCreate.get(i), notCreateFinishedBeanMap)) {
                    toCreate.remove(i);
                } else
                    i++;
            }
            //这一轮创建完成后，查看是否列表中的元素有减少，减少了说明这一轮有bean创建成功，没有减少说明没有bean可以被成功创建 (cycle dependency)
            if (toCreate.size() == size) {
                throw new Exception("cycle dependency");
            }
        }
    }

    private static boolean finishCreateBean(Class<?> aClass, Map<Class<?>, Object> notCreateFinishedBeanMap)
            throws IllegalAccessException, InstantiationException {
        if (!(aClass.isAnnotationPresent(MyBean.class) || aClass.isAnnotationPresent(MyController.class))) {
            return true;
        }
        Object instance = notCreateFinishedBeanMap.get(aClass);
        if (instance == null)
            instance = aClass.newInstance();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyAutoWired.class)) {
                Object bean = BeanFactory.getBean(field.getType());
                if (bean == null) {  //there not exist the dependent bean in bean factory then we can not create bean for this aClass
                    //put the not completely-created bean(instance) into notCreateFinishedBeanMap
                    notCreateFinishedBeanMap.put(aClass, instance);
                    return false;
                }
                field.setAccessible(true);
                field.set(instance, bean);

            }
        }
        classToBean.put(aClass, instance);
        return true;
    }
}
