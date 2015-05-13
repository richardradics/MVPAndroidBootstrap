package com.richardradics.commons.helper;

/**
 * Created by Rcsk on 2014.08.05..
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionHelper {

    public static Map<Class<? extends Annotation>, List<Field>> groupDeclaredFieldsByAnnotation(Class targetClass) {
        return groupFieldsByAnnotation(targetClass.getDeclaredFields());
    }

    public static Map<Class<? extends Annotation>, List<Field>> groupFieldsByAnnotation(Class targetClass) {
        return groupFieldsByAnnotation(targetClass.getFields());
    }

    public static Map<Class<? extends Annotation>, List<Field>> groupInheritedFieldsByAnnotation(Class targetClass,
                                                                                                 boolean declared) {

        HashMap<Class<? extends Annotation>, List<Field>> classFields =
                new HashMap<Class<? extends Annotation>, List<Field>>();

        if (declared) {
            classFields.putAll(groupDeclaredFieldsByAnnotation(targetClass));
        } else {
            classFields.putAll(groupFieldsByAnnotation(targetClass));
        }

        if (targetClass.getSuperclass() != null) {

            HashMap<Class<? extends Annotation>, List<Field>> superclassFields =
                    new HashMap<Class<? extends Annotation>, List<Field>>(
                            groupInheritedFieldsByAnnotation(targetClass.getSuperclass(), declared));

            for (Class<? extends Annotation> annotation : superclassFields.keySet()) {
                if (classFields.containsKey(annotation)) {
                    classFields.get(annotation).addAll(superclassFields.get(annotation));
                } else {
                    classFields.put(annotation, superclassFields.get(annotation));
                }
            }
        }

        return classFields;
    }

    private static Map<Class<? extends Annotation>, List<Field>> groupFieldsByAnnotation(Field[] fields) {

        HashMap<Class<? extends Annotation>, List<Field>> groupedFields =
                new HashMap<Class<? extends Annotation>, List<Field>>();

        for (Field field : fields) {
            for (Annotation a : field.getDeclaredAnnotations()) {
                List<Field> fieldsForAnnotation = groupedFields.get(a.annotationType());
                if (fieldsForAnnotation == null) {
                    fieldsForAnnotation = new ArrayList<Field>();
                }
                fieldsForAnnotation.add(field);
                groupedFields.put(a.annotationType(), fieldsForAnnotation);
            }
        }

        return groupedFields;
    }

    public static List<Field> getFieldsWithAnnotation(Class<? extends Annotation> annotationClass, Class targetClass) {

        ArrayList<Field> fieldsWithAnnotation = new ArrayList<Field>();
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                fieldsWithAnnotation.add(field);
                break;
            }
        }

        return fieldsWithAnnotation;
    }

    public static List<Field> getInheritedFieldsWithAnnotation(
            Class<? extends Annotation> annotationClass, Class targetClass) {

        List<Field> fieldsWithAnnotation = getFieldsWithAnnotation(annotationClass, targetClass);

        if (targetClass.getSuperclass() != null) {
            fieldsWithAnnotation.addAll(
                    getInheritedFieldsWithAnnotation(
                            annotationClass, targetClass.getSuperclass()));
        }

        return fieldsWithAnnotation;
    }

    public static List<Field> getInheritedFields(Class targetClass, Class<? extends Annotation> classMarker) {

        List<Field> allDeclaredFields = new ArrayList<Field>();
        if (targetClass.isAnnotationPresent(classMarker)) {
            ArrayList<Field> declaredFields = new ArrayList<Field>(Arrays.asList(targetClass.getDeclaredFields()));
            allDeclaredFields.addAll(declaredFields);
        }
        Class superClass = targetClass.getSuperclass();
        if (superClass != null) {
            allDeclaredFields.addAll(getInheritedFields(superClass, classMarker));
        }
        return allDeclaredFields;
    }

    public static boolean isMetaAnnotationPresent(Field field, Class<? extends Annotation> metaAnnotation) {
        for (Annotation a : field.getDeclaredAnnotations()) {
            if (a.annotationType().isAnnotationPresent(metaAnnotation)) {
                return true;
            }
        }
        return false;
    }


}
