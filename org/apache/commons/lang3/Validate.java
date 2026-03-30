/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.Objects;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Validate
/*      */ {
/*      */   private static final String DEFAULT_NOT_NAN_EX_MESSAGE = "The validated value is not a number";
/*      */   private static final String DEFAULT_FINITE_EX_MESSAGE = "The value is invalid: %f";
/*      */   private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
/*      */   private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
/*      */   private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
/*      */   private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
/*      */   private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
/*      */   private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
/*      */   private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
/*      */   private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
/*      */   private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
/*      */   private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
/*      */   private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
/*      */   private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
/*      */   private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
/*      */   private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
/*      */   private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
/*      */   private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
/*      */   private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
/*      */   private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";
/*      */   
/*      */   public static void isTrue(boolean expression, String message, long value) {
/*  109 */     if (!expression) {
/*  110 */       throw new IllegalArgumentException(String.format(message, new Object[] { Long.valueOf(value) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isTrue(boolean expression, String message, double value) {
/*  134 */     if (!expression) {
/*  135 */       throw new IllegalArgumentException(String.format(message, new Object[] { Double.valueOf(value) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isTrue(boolean expression, String message, Object... values) {
/*  158 */     if (!expression) {
/*  159 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isTrue(boolean expression) {
/*  183 */     if (!expression) {
/*  184 */       throw new IllegalArgumentException("The validated expression is false");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T notNull(T object) {
/*  207 */     return notNull(object, "The validated object is null", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T notNull(T object, String message, Object... values) {
/*  225 */     return Objects.requireNonNull(object, () -> String.format(message, values));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] notEmpty(T[] array, String message, Object... values) {
/*  248 */     Objects.requireNonNull(array, () -> String.format(message, values));
/*  249 */     if (array.length == 0) {
/*  250 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*  252 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] notEmpty(T[] array) {
/*  272 */     return notEmpty(array, "The validated array is empty", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends java.util.Collection<?>> T notEmpty(T collection, String message, Object... values) {
/*  295 */     Objects.requireNonNull(collection, () -> String.format(message, values));
/*  296 */     if (collection.isEmpty()) {
/*  297 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*  299 */     return collection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends java.util.Collection<?>> T notEmpty(T collection) {
/*  319 */     return notEmpty(collection, "The validated collection is empty", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends java.util.Map<?, ?>> T notEmpty(T map, String message, Object... values) {
/*  342 */     Objects.requireNonNull(map, () -> String.format(message, values));
/*  343 */     if (map.isEmpty()) {
/*  344 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*  346 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends java.util.Map<?, ?>> T notEmpty(T map) {
/*  366 */     return notEmpty(map, "The validated map is empty", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends CharSequence> T notEmpty(T chars, String message, Object... values) {
/*  389 */     Objects.requireNonNull(chars, () -> String.format(message, values));
/*  390 */     if (chars.length() == 0) {
/*  391 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*  393 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends CharSequence> T notEmpty(T chars) {
/*  414 */     return notEmpty(chars, "The validated character sequence is empty", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends CharSequence> T notBlank(T chars, String message, Object... values) {
/*  440 */     Objects.requireNonNull(chars, () -> String.format(message, values));
/*  441 */     if (StringUtils.isBlank((CharSequence)chars)) {
/*  442 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*  444 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends CharSequence> T notBlank(T chars) {
/*  467 */     return notBlank(chars, "The validated character sequence is blank", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] noNullElements(T[] array, String message, Object... values) {
/*  497 */     notNull(array);
/*  498 */     for (int i = 0; i < array.length; i++) {
/*  499 */       if (array[i] == null) {
/*  500 */         Object[] values2 = ArrayUtils.add(values, Integer.valueOf(i));
/*  501 */         throw new IllegalArgumentException(String.format(message, values2));
/*      */       } 
/*      */     } 
/*  504 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] noNullElements(T[] array) {
/*  529 */     return noNullElements(array, "The validated array contains null element at index: %d", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Iterable<?>> T noNullElements(T iterable, String message, Object... values) {
/*  559 */     notNull(iterable);
/*  560 */     int i = 0;
/*  561 */     for (Iterator<?> it = iterable.iterator(); it.hasNext(); i++) {
/*  562 */       if (it.next() == null) {
/*  563 */         Object[] values2 = ArrayUtils.addAll(values, new Object[] { Integer.valueOf(i) });
/*  564 */         throw new IllegalArgumentException(String.format(message, values2));
/*      */       } 
/*      */     } 
/*  567 */     return iterable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Iterable<?>> T noNullElements(T iterable) {
/*  592 */     return noNullElements(iterable, "The validated collection contains null element at index: %d", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] validIndex(T[] array, int index, String message, Object... values) {
/*  620 */     notNull(array);
/*  621 */     if (index < 0 || index >= array.length) {
/*  622 */       throw new IndexOutOfBoundsException(String.format(message, values));
/*      */     }
/*  624 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] validIndex(T[] array, int index) {
/*  651 */     return validIndex(array, index, "The validated array index is invalid: %d", new Object[] { Integer.valueOf(index) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends java.util.Collection<?>> T validIndex(T collection, int index, String message, Object... values) {
/*  679 */     notNull(collection);
/*  680 */     if (index < 0 || index >= collection.size()) {
/*  681 */       throw new IndexOutOfBoundsException(String.format(message, values));
/*      */     }
/*  683 */     return collection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends java.util.Collection<?>> T validIndex(T collection, int index) {
/*  707 */     return validIndex(collection, index, "The validated collection index is invalid: %d", new Object[] { Integer.valueOf(index) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends CharSequence> T validIndex(T chars, int index, String message, Object... values) {
/*  736 */     notNull(chars);
/*  737 */     if (index < 0 || index >= chars.length()) {
/*  738 */       throw new IndexOutOfBoundsException(String.format(message, values));
/*      */     }
/*  740 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends CharSequence> T validIndex(T chars, int index) {
/*  768 */     return validIndex(chars, index, "The validated character sequence index is invalid: %d", new Object[] { Integer.valueOf(index) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void validState(boolean expression) {
/*  794 */     if (!expression) {
/*  795 */       throw new IllegalStateException("The validated state is false");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void validState(boolean expression, String message, Object... values) {
/*  816 */     if (!expression) {
/*  817 */       throw new IllegalStateException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void matchesPattern(CharSequence input, String pattern) {
/*  841 */     if (!Pattern.matches(pattern, input)) {
/*  842 */       throw new IllegalArgumentException(String.format("The string %s does not match the pattern %s", new Object[] { input, pattern }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void matchesPattern(CharSequence input, String pattern, String message, Object... values) {
/*  865 */     if (!Pattern.matches(pattern, input)) {
/*  866 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void notNaN(double value) {
/*  889 */     notNaN(value, "The validated value is not a number", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void notNaN(double value, String message, Object... values) {
/*  907 */     if (Double.isNaN(value)) {
/*  908 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void finite(double value) {
/*  930 */     finite(value, "The value is invalid: %f", new Object[] { Double.valueOf(value) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void finite(double value, String message, Object... values) {
/*  948 */     if (Double.isNaN(value) || Double.isInfinite(value)) {
/*  949 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void inclusiveBetween(T start, T end, Comparable<T> value) {
/*  973 */     if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
/*  974 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { value, start, end }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void inclusiveBetween(T start, T end, Comparable<T> value, String message, Object... values) {
/*  998 */     if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
/*  999 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void inclusiveBetween(long start, long end, long value) {
/* 1019 */     if (value < start || value > end) {
/* 1020 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { Long.valueOf(value), Long.valueOf(start), Long.valueOf(end) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void inclusiveBetween(long start, long end, long value, String message) {
/* 1042 */     if (value < start || value > end) {
/* 1043 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void inclusiveBetween(double start, double end, double value) {
/* 1063 */     if (value < start || value > end) {
/* 1064 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { Double.valueOf(value), Double.valueOf(start), Double.valueOf(end) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void inclusiveBetween(double start, double end, double value, String message) {
/* 1086 */     if (value < start || value > end) {
/* 1087 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void exclusiveBetween(T start, T end, Comparable<T> value) {
/* 1111 */     if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
/* 1112 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { value, start, end }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void exclusiveBetween(T start, T end, Comparable<T> value, String message, Object... values) {
/* 1136 */     if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
/* 1137 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void exclusiveBetween(long start, long end, long value) {
/* 1157 */     if (value <= start || value >= end) {
/* 1158 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { Long.valueOf(value), Long.valueOf(start), Long.valueOf(end) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void exclusiveBetween(long start, long end, long value, String message) {
/* 1180 */     if (value <= start || value >= end) {
/* 1181 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void exclusiveBetween(double start, double end, double value) {
/* 1201 */     if (value <= start || value >= end) {
/* 1202 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { Double.valueOf(value), Double.valueOf(start), Double.valueOf(end) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void exclusiveBetween(double start, double end, double value, String message) {
/* 1224 */     if (value <= start || value >= end) {
/* 1225 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isInstanceOf(Class<?> type, Object obj) {
/* 1250 */     if (!type.isInstance(obj)) {
/* 1251 */       throw new IllegalArgumentException(String.format("Expected type: %s, actual: %s", new Object[] { type.getName(), (obj == null) ? "null" : obj
/* 1252 */               .getClass().getName() }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isInstanceOf(Class<?> type, Object obj, String message, Object... values) {
/* 1275 */     if (!type.isInstance(obj)) {
/* 1276 */       throw new IllegalArgumentException(String.format(message, values));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isAssignableFrom(Class<?> superType, Class<?> type) {
/* 1301 */     if (!superType.isAssignableFrom(type)) {
/* 1302 */       throw new IllegalArgumentException(String.format("Cannot assign a %s to a %s", new Object[] { (type == null) ? "null" : type.getName(), superType
/* 1303 */               .getName() }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void isAssignableFrom(Class<?> superType, Class<?> type, String message, Object... values) {
/* 1326 */     if (!superType.isAssignableFrom(type))
/* 1327 */       throw new IllegalArgumentException(String.format(message, values)); 
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\Validate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */