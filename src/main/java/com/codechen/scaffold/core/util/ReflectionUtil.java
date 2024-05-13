package com.codechen.scaffold.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cyl
 * @date 2023-07-10 16:57
 * @description 反射工具类
 */
public class ReflectionUtil {

    /**
     * 获取实现的接口名
     *
     * @param clazz
     * @return
     */
    public static List<String> getInterfaces(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        return Arrays.asList(interfaces)
                .stream()
                .map(item -> item.getSimpleName())
                .collect(Collectors.toList());
    }

    /**
     * 获取所有属性名
     *
     * @param clazz
     * @return
     */
    public static List<String> getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        return Arrays.asList(fields)
                .stream()
                .map(field -> field.getName())
                .collect(Collectors.toList());
    }

    /**
     * 获取所有方法名
     *
     * @param clazz
     * @return
     */
    public static List<String> getMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        return Arrays.stream(methods)
                .map(method -> method.getName())
                .collect(Collectors.toList());
    }

    /**
     * 获取所有注解名
     *
     * @param clazz
     * @return
     */
    public static List<String> getAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        return Arrays.asList(annotations)
                .stream()
                .map(annotation -> annotation.annotationType().getSimpleName())
                .collect(Collectors.toList());
    }

    /**
     * 根据传入的属性名字符串，修改对应的属性值
     *
     * @param clazz      类的 Class 对象
     * @param filedName  属性名
     * @param classValue 要修改的实例对象
     * @param value      修改后的新值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setField(Class<?> clazz, String filedName, Object classValue, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(filedName);

        field.setAccessible(true);
        field.set(classValue, value);
    }

    /**
     * 根据传入的方法名字符串，获取对应的方法
     *
     * @param clazz          类的Class对象
     * @param name           方法名
     * @param parameterTypes 方法的形参对应的Class类型【可以不写】
     * @return 方法对象【Method类型】
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredMethod(name, parameterTypes);
    }

    /**
     * 根据传入的方法对象，调用对应的方法
     *
     * @param method 方法对象
     * @param obj    要调用的实例对象【如果是调用静态方法，则可以传入null】
     * @param args   传入方法的实参【可以不写】
     * @return 方法的返回值【没有返回值，则返回null】
     */
    public static Object invokeMethod(Method method, Object obj, Object... args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    /**
     * 获取属性值
     *
     * @param object    实例对象
     * @param fieldName 属性字段名
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = object.getClass();
        Field declaredField = aClass.getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }
}
