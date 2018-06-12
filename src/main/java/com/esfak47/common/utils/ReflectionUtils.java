package com.esfak47.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类 提供了一系列的获取某一个类的信息的方法 <br /> 包括获取全类名，实现的接口，接口的泛型等 <br /> 并且提供了根据Class类型获取对应的实例对象的方法，以及修改属性和调用对象的方法等
 *
 * @author tony
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * 获取全类名
     *
     * @return 全类名【String类型】
     */
    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }

    /**
     * 获取实现的接口名
     *
     * @return 所有的接口名【每一个接口名的类型为String，最后保存到一个List集合中】
     */
    public static List<String> getInterfaces(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();

        List<String> list = new ArrayList<>();
        for (Class<?> itfc : interfaces) {
            // 接口名
            String interfaceName = itfc.getSimpleName();

            list.add(interfaceName);
        }

        return list;
    }

    /**
     * 获取所有的注解名
     *
     * @return 所有的注解名【每一个注解名的类型为String，最后保存到一个List集合中】
     */
    public static List<String> getAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();

        List<String> list = new ArrayList<>();
        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getSimpleName();
            list.add(annotationName);
        }

        return list;
    }

    /**
     * 获取父类的泛型
     *
     * @return 父类的泛型【Class类型】
     */
    public static Class<?> getSuperClassGenericParameterizedType(Class<?> clazz) {
        Type genericSuperClass = clazz.getGenericSuperclass();

        Class<?> superClassGenericParameterizedType = null;

        // 判断父类是否有泛型
        if (genericSuperClass instanceof ParameterizedType) {
            // 向下转型，以便调用方法
            ParameterizedType pt = (ParameterizedType) genericSuperClass;
            // 只取第一个，因为一个类只能继承一个父类
            Type superClazz = pt.getActualTypeArguments()[0];
            // 转换为Class类型
            superClassGenericParameterizedType = (Class<?>) superClazz;
        }

        return superClassGenericParameterizedType;
    }

    /**
     * 获取接口的所有泛型
     *
     * @return 所有的泛型接口【每一个泛型接口的类型为Class，最后保存到一个List集合中】
     */
    public static List<Class<?>> getInterfaceGenericParameterizedTypes(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();

        List<Class<?>> list = new ArrayList<>();
        for (Type genericInterface : genericInterfaces) {
            // 判断接口是否有泛型
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericInterface;

                // 得到所有的泛型【Type类型的数组】
                Type[] interfaceTypes = pt.getActualTypeArguments();

                for (Type interfaceType : interfaceTypes) {
                    // 获取对应的泛型【Type类型】
                    // 转换为Class类型
                    Class<?> interfaceClass = (Class<?>) interfaceType;
                    list.add(interfaceClass);
                }

            }

        }

        return list;
    }

    /**
     * 根据Class类型，获取对应的实例【要求必须有无参的构造器】
     *
     * @return 对应的实例【Object类型】
     */
    public static Object getNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    /**
     * 根据传入的类的Class对象，以及构造方法的形参的Class对象，获取对应的构造方法对象
     *
     * @param clazz          类的Class对象
     * @param parameterTypes 构造方法的形参的Class对象【可以不写】
     * @return 构造方法对象【Constructor类型】
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes)
            throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredConstructor(parameterTypes);
    }

    /**
     * 根据传入的构造方法对象，以及，获取对应的实例
     *
     * @param constructor 构造方法对象
     * @param initargs    传入构造方法的实参【可以不写】
     * @return 对应的实例【Object类型】
     */
    public static Object getNewInstance(Constructor<?> constructor, Object... initargs)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        constructor.setAccessible(true);
        return constructor.newInstance(initargs);
    }

    /**
     * 根据传入的属性名字符串，修改对应的属性值
     *
     * @param clazz 类的Class对象
     * @param name  属性名
     * @param obj   要修改的实例对象
     * @param value 修改后的新值
     */
    public static void setField(Class<?> clazz, String name, Object obj, Object value)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
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

    public static boolean defaultConstructorOnly(Class clazz) {
        Constructor[] constructors = clazz.getConstructors();
        return constructors.length == 1 && constructors[0].getParameterCount() == 0;
    }

    public static Constructor getConstructor(Class clazz, Class annotationClazz) {
        Constructor[] constructors = clazz.getConstructors();
        if (constructors.length != 1) {
            return null;
        }
        if (constructors[0].getAnnotation(annotationClazz) == null) {
            return null;
        } else {
            return constructors[0];
        }

    }

}