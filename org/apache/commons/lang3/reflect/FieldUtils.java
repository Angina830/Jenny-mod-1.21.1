/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.JavaVersion;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.SystemUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldUtils
/*     */ {
/*     */   public static Field getField(Class<?> cls, String fieldName) {
/*  66 */     Field field = getField(cls, fieldName, false);
/*  67 */     MemberUtils.setAccessibleWorkaround(field);
/*  68 */     return field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getField(Class<?> cls, String fieldName, boolean forceAccess) {
/*  89 */     Validate.notNull(cls, "The class must not be null", new Object[0]);
/*  90 */     Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
/*     */       try {
/* 108 */         Field field = acls.getDeclaredField(fieldName);
/*     */ 
/*     */         
/* 111 */         if (!Modifier.isPublic(field.getModifiers()))
/* 112 */         { if (forceAccess)
/* 113 */           { field.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 118 */             return field; }  } else { return field; } 
/* 119 */       } catch (NoSuchFieldException noSuchFieldException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     Field match = null;
/* 127 */     for (Class<?> class1 : (Iterable<Class<?>>)ClassUtils.getAllInterfaces(cls)) {
/*     */       try {
/* 129 */         Field test = class1.getField(fieldName);
/* 130 */         Validate.isTrue((match == null), "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", new Object[] { fieldName, cls });
/*     */         
/* 132 */         match = test;
/* 133 */       } catch (NoSuchFieldException noSuchFieldException) {}
/*     */     } 
/*     */ 
/*     */     
/* 137 */     return match;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getDeclaredField(Class<?> cls, String fieldName) {
/* 152 */     return getDeclaredField(cls, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getDeclaredField(Class<?> cls, String fieldName, boolean forceAccess) {
/* 172 */     Validate.notNull(cls, "The class must not be null", new Object[0]);
/* 173 */     Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
/*     */     
/*     */     try {
/* 176 */       Field field = cls.getDeclaredField(fieldName);
/* 177 */       if (!MemberUtils.isAccessible(field)) {
/* 178 */         if (forceAccess) {
/* 179 */           field.setAccessible(true);
/*     */         } else {
/* 181 */           return null;
/*     */         } 
/*     */       }
/* 184 */       return field;
/* 185 */     } catch (NoSuchFieldException noSuchFieldException) {
/*     */ 
/*     */       
/* 188 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getAllFields(Class<?> cls) {
/* 202 */     List<Field> allFieldsList = getAllFieldsList(cls);
/* 203 */     return allFieldsList.<Field>toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Field> getAllFieldsList(Class<?> cls) {
/* 217 */     Validate.notNull(cls, "The class must not be null", new Object[0]);
/* 218 */     List<Field> allFields = new ArrayList<>();
/* 219 */     Class<?> currentClass = cls;
/* 220 */     while (currentClass != null) {
/* 221 */       Field[] declaredFields = currentClass.getDeclaredFields();
/* 222 */       Collections.addAll(allFields, declaredFields);
/* 223 */       currentClass = currentClass.getSuperclass();
/*     */     } 
/* 225 */     return allFields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFieldsWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
/* 240 */     List<Field> annotatedFieldsList = getFieldsListWithAnnotation(cls, annotationCls);
/* 241 */     return annotatedFieldsList.<Field>toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Field> getFieldsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
/* 256 */     Validate.notNull(annotationCls, "The annotation class must not be null", new Object[0]);
/* 257 */     List<Field> allFields = getAllFieldsList(cls);
/* 258 */     List<Field> annotatedFields = new ArrayList<>();
/* 259 */     for (Field field : allFields) {
/* 260 */       if (field.getAnnotation(annotationCls) != null) {
/* 261 */         annotatedFields.add(field);
/*     */       }
/*     */     } 
/* 264 */     return annotatedFields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Field field) throws IllegalAccessException {
/* 279 */     return readStaticField(field, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Field field, boolean forceAccess) throws IllegalAccessException {
/* 297 */     Validate.notNull(field, "The field must not be null", new Object[0]);
/* 298 */     Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field '%s' is not static", new Object[] { field.getName() });
/* 299 */     return readField(field, (Object)null, forceAccess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
/* 317 */     return readStaticField(cls, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 339 */     Field field = getField(cls, fieldName, forceAccess);
/* 340 */     Validate.notNull(field, "Cannot locate field '%s' on %s", new Object[] { fieldName, cls });
/*     */     
/* 342 */     return readStaticField(field, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
/* 361 */     return readDeclaredStaticField(cls, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredStaticField(Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 383 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 384 */     Validate.notNull(field, "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 386 */     return readStaticField(field, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Field field, Object target) throws IllegalAccessException {
/* 403 */     return readField(field, target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Field field, Object target, boolean forceAccess) throws IllegalAccessException {
/* 423 */     Validate.notNull(field, "The field must not be null", new Object[0]);
/* 424 */     if (forceAccess && !field.isAccessible()) {
/* 425 */       field.setAccessible(true);
/*     */     } else {
/* 427 */       MemberUtils.setAccessibleWorkaround(field);
/*     */     } 
/* 429 */     return field.get(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Object target, String fieldName) throws IllegalAccessException {
/* 446 */     return readField(target, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 467 */     Validate.notNull(target, "target object must not be null", new Object[0]);
/* 468 */     Class<?> cls = target.getClass();
/* 469 */     Field field = getField(cls, fieldName, forceAccess);
/* 470 */     Validate.isTrue((field != null), "Cannot locate field %s on %s", new Object[] { fieldName, cls });
/*     */     
/* 472 */     return readField(field, target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredField(Object target, String fieldName) throws IllegalAccessException {
/* 489 */     return readDeclaredField(target, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 510 */     Validate.notNull(target, "target object must not be null", new Object[0]);
/* 511 */     Class<?> cls = target.getClass();
/* 512 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 513 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls, fieldName });
/*     */     
/* 515 */     return readField(field, target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Field field, Object value) throws IllegalAccessException {
/* 531 */     writeStaticField(field, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Field field, Object value, boolean forceAccess) throws IllegalAccessException {
/* 551 */     Validate.notNull(field, "The field must not be null", new Object[0]);
/* 552 */     Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field %s.%s is not static", new Object[] { field.getDeclaringClass().getName(), field
/* 553 */           .getName() });
/* 554 */     writeField(field, (Object)null, value, forceAccess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
/* 573 */     writeStaticField(cls, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Class<?> cls, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 597 */     Field field = getField(cls, fieldName, forceAccess);
/* 598 */     Validate.notNull(field, "Cannot locate field %s on %s", new Object[] { fieldName, cls });
/*     */     
/* 600 */     writeStaticField(field, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
/* 619 */     writeDeclaredStaticField(cls, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredStaticField(Class<?> cls, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 642 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 643 */     Validate.notNull(field, "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 645 */     writeField(field, (Object)null, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Field field, Object target, Object value) throws IllegalAccessException {
/* 662 */     writeField(field, target, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Field field, Object target, Object value, boolean forceAccess) throws IllegalAccessException {
/* 685 */     Validate.notNull(field, "The field must not be null", new Object[0]);
/* 686 */     if (forceAccess && !field.isAccessible()) {
/* 687 */       field.setAccessible(true);
/*     */     } else {
/* 689 */       MemberUtils.setAccessibleWorkaround(field);
/*     */     } 
/* 691 */     field.set(target, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeFinalModifier(Field field) {
/* 704 */     removeFinalModifier(field, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void removeFinalModifier(Field field, boolean forceAccess) {
/* 725 */     Validate.notNull(field, "The field must not be null", new Object[0]);
/*     */     
/*     */     try {
/* 728 */       if (Modifier.isFinal(field.getModifiers())) {
/*     */         
/* 730 */         Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 731 */         boolean doForceAccess = (forceAccess && !modifiersField.isAccessible());
/* 732 */         if (doForceAccess) {
/* 733 */           modifiersField.setAccessible(true);
/*     */         }
/*     */         try {
/* 736 */           modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/*     */         } finally {
/* 738 */           if (doForceAccess) {
/* 739 */             modifiersField.setAccessible(false);
/*     */           }
/*     */         } 
/*     */       } 
/* 743 */     } catch (NoSuchFieldException|IllegalAccessException e) {
/* 744 */       if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_12)) {
/* 745 */         throw new UnsupportedOperationException("In java 12+ final cannot be removed.", e);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Object target, String fieldName, Object value) throws IllegalAccessException {
/* 770 */     writeField(target, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 794 */     Validate.notNull(target, "target object must not be null", new Object[0]);
/* 795 */     Class<?> cls = target.getClass();
/* 796 */     Field field = getField(cls, fieldName, forceAccess);
/* 797 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 799 */     writeField(field, target, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
/* 818 */     writeDeclaredField(target, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 842 */     Validate.notNull(target, "target object must not be null", new Object[0]);
/* 843 */     Class<?> cls = target.getClass();
/* 844 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 845 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 847 */     writeField(field, target, value, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\reflect\FieldUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */