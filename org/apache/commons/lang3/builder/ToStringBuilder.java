/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ToStringBuilder
/*      */   implements Builder<String>
/*      */ {
/*   94 */   private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final StringBuffer buffer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Object object;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ToStringStyle style;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ToStringStyle getDefaultStyle() {
/*  117 */     return defaultStyle;
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
/*      */   public static void setDefaultStyle(ToStringStyle style) {
/*  136 */     defaultStyle = (ToStringStyle)Validate.notNull(style, "The style must not be null", new Object[0]);
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
/*      */   public static String reflectionToString(Object object) {
/*  149 */     return ReflectionToStringBuilder.toString(object);
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
/*      */   public static String reflectionToString(Object object, ToStringStyle style) {
/*  162 */     return ReflectionToStringBuilder.toString(object, style);
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
/*      */   public static String reflectionToString(Object object, ToStringStyle style, boolean outputTransients) {
/*  176 */     return ReflectionToStringBuilder.toString(object, style, outputTransients, false, null);
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
/*      */   public static <T> String reflectionToString(T object, ToStringStyle style, boolean outputTransients, Class<? super T> reflectUpToClass) {
/*  197 */     return ReflectionToStringBuilder.toString(object, style, outputTransients, false, reflectUpToClass);
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
/*      */   public ToStringBuilder(Object object) {
/*  223 */     this(object, null, null);
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
/*      */   public ToStringBuilder(Object object, ToStringStyle style) {
/*  235 */     this(object, style, null);
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
/*      */   public ToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer) {
/*  250 */     if (style == null) {
/*  251 */       style = getDefaultStyle();
/*      */     }
/*  253 */     if (buffer == null) {
/*  254 */       buffer = new StringBuffer(512);
/*      */     }
/*  256 */     this.buffer = buffer;
/*  257 */     this.style = style;
/*  258 */     this.object = object;
/*      */     
/*  260 */     style.appendStart(buffer, object);
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
/*      */   public ToStringBuilder append(boolean value) {
/*  273 */     this.style.append(this.buffer, (String)null, value);
/*  274 */     return this;
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
/*      */   public ToStringBuilder append(boolean[] array) {
/*  287 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  288 */     return this;
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
/*      */   public ToStringBuilder append(byte value) {
/*  301 */     this.style.append(this.buffer, (String)null, value);
/*  302 */     return this;
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
/*      */   public ToStringBuilder append(byte[] array) {
/*  315 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  316 */     return this;
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
/*      */   public ToStringBuilder append(char value) {
/*  329 */     this.style.append(this.buffer, (String)null, value);
/*  330 */     return this;
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
/*      */   public ToStringBuilder append(char[] array) {
/*  343 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  344 */     return this;
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
/*      */   public ToStringBuilder append(double value) {
/*  357 */     this.style.append(this.buffer, (String)null, value);
/*  358 */     return this;
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
/*      */   public ToStringBuilder append(double[] array) {
/*  371 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  372 */     return this;
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
/*      */   public ToStringBuilder append(float value) {
/*  385 */     this.style.append(this.buffer, (String)null, value);
/*  386 */     return this;
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
/*      */   public ToStringBuilder append(float[] array) {
/*  399 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  400 */     return this;
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
/*      */   public ToStringBuilder append(int value) {
/*  413 */     this.style.append(this.buffer, (String)null, value);
/*  414 */     return this;
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
/*      */   public ToStringBuilder append(int[] array) {
/*  427 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  428 */     return this;
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
/*      */   public ToStringBuilder append(long value) {
/*  441 */     this.style.append(this.buffer, (String)null, value);
/*  442 */     return this;
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
/*      */   public ToStringBuilder append(long[] array) {
/*  455 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  456 */     return this;
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
/*      */   public ToStringBuilder append(Object obj) {
/*  469 */     this.style.append(this.buffer, (String)null, obj, (Boolean)null);
/*  470 */     return this;
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
/*      */   public ToStringBuilder append(Object[] array) {
/*  483 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  484 */     return this;
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
/*      */   public ToStringBuilder append(short value) {
/*  497 */     this.style.append(this.buffer, (String)null, value);
/*  498 */     return this;
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
/*      */   public ToStringBuilder append(short[] array) {
/*  511 */     this.style.append(this.buffer, (String)null, array, (Boolean)null);
/*  512 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, boolean value) {
/*  524 */     this.style.append(this.buffer, fieldName, value);
/*  525 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, boolean[] array) {
/*  537 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  538 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, boolean[] array, boolean fullDetail) {
/*  557 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  558 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, byte value) {
/*  570 */     this.style.append(this.buffer, fieldName, value);
/*  571 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToStringBuilder append(String fieldName, byte[] array) {
/*  582 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  583 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, byte[] array, boolean fullDetail) {
/*  602 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  603 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, char value) {
/*  615 */     this.style.append(this.buffer, fieldName, value);
/*  616 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, char[] array) {
/*  628 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  629 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, char[] array, boolean fullDetail) {
/*  648 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  649 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, double value) {
/*  661 */     this.style.append(this.buffer, fieldName, value);
/*  662 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, double[] array) {
/*  674 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  675 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, double[] array, boolean fullDetail) {
/*  694 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  695 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, float value) {
/*  707 */     this.style.append(this.buffer, fieldName, value);
/*  708 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, float[] array) {
/*  720 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  721 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, float[] array, boolean fullDetail) {
/*  740 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  741 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, int value) {
/*  753 */     this.style.append(this.buffer, fieldName, value);
/*  754 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, int[] array) {
/*  766 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  767 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, int[] array, boolean fullDetail) {
/*  786 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  787 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, long value) {
/*  799 */     this.style.append(this.buffer, fieldName, value);
/*  800 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, long[] array) {
/*  812 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  813 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, long[] array, boolean fullDetail) {
/*  832 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  833 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, Object obj) {
/*  845 */     this.style.append(this.buffer, fieldName, obj, (Boolean)null);
/*  846 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, Object obj, boolean fullDetail) {
/*  860 */     this.style.append(this.buffer, fieldName, obj, Boolean.valueOf(fullDetail));
/*  861 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, Object[] array) {
/*  873 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  874 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, Object[] array, boolean fullDetail) {
/*  893 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  894 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, short value) {
/*  906 */     this.style.append(this.buffer, fieldName, value);
/*  907 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, short[] array) {
/*  919 */     this.style.append(this.buffer, fieldName, array, (Boolean)null);
/*  920 */     return this;
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
/*      */   public ToStringBuilder append(String fieldName, short[] array, boolean fullDetail) {
/*  939 */     this.style.append(this.buffer, fieldName, array, Boolean.valueOf(fullDetail));
/*  940 */     return this;
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
/*      */   public ToStringBuilder appendAsObjectToString(Object srcObject) {
/*  953 */     ObjectUtils.identityToString(getStringBuffer(), srcObject);
/*  954 */     return this;
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
/*      */   public ToStringBuilder appendSuper(String superToString) {
/*  972 */     if (superToString != null) {
/*  973 */       this.style.appendSuper(this.buffer, superToString);
/*      */     }
/*  975 */     return this;
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
/*      */   
/*      */   public ToStringBuilder appendToString(String toString) {
/* 1006 */     if (toString != null) {
/* 1007 */       this.style.appendToString(this.buffer, toString);
/*      */     }
/* 1009 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject() {
/* 1019 */     return this.object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer getStringBuffer() {
/* 1028 */     return this.buffer;
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
/*      */   public ToStringStyle getStyle() {
/* 1040 */     return this.style;
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
/*      */   public String toString() {
/* 1055 */     if (getObject() == null) {
/* 1056 */       getStringBuffer().append(getStyle().getNullText());
/*      */     } else {
/* 1058 */       this.style.appendEnd(getStringBuffer(), getObject());
/*      */     } 
/* 1060 */     return getStringBuffer().toString();
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
/*      */   public String build() {
/* 1075 */     return toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\ToStringBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */