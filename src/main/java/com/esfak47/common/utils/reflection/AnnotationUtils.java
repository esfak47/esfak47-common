package com.esfak47.common.utils.reflection;

import com.esfak47.common.lang.Assert;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author tony
 * @date 2018/5/3
 */
public class AnnotationUtils {

    private AnnotationUtils() {
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> type) {
        Assert.notNull(clazz, "clazz should not be null");
        Assert.notNull(type, "type should not be null");
        T annotation = clazz.getAnnotation(type);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotations = clazz.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        Optional<Annotation> any = Arrays.stream(annotations).filter(
                annotation1 -> annotation1.annotationType().isAnnotationPresent(type)).findAny();
        return any.map(annotation1 -> annotation1.annotationType().getAnnotation(type)).orElse(null);

    }
}
