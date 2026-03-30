/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Collection;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.WeakHashMap;
/*      */ import org.apache.commons.lang3.ClassUtils;
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.StringEscapeUtils;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ToStringStyle
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -2587890625525655916L;
/*   84 */   public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   98 */   public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  121 */   public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   public static final ToStringStyle NO_CLASS_NAME_STYLE = new NoClassNameToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   public static final ToStringStyle JSON_STYLE = new JsonToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  170 */   private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Map<Object, Object> getRegistry() {
/*  191 */     return REGISTRY.get();
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
/*      */   static boolean isRegistered(Object value) {
/*  206 */     Map<Object, Object> m = getRegistry();
/*  207 */     return (m != null && m.containsKey(value));
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
/*      */   static void register(Object value) {
/*  220 */     if (value != null) {
/*  221 */       Map<Object, Object> m = getRegistry();
/*  222 */       if (m == null) {
/*  223 */         REGISTRY.set(new WeakHashMap<>());
/*      */       }
/*  225 */       getRegistry().put(value, null);
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
/*      */   static void unregister(Object value) {
/*  242 */     if (value != null) {
/*  243 */       Map<Object, Object> m = getRegistry();
/*  244 */       if (m != null) {
/*  245 */         m.remove(value);
/*  246 */         if (m.isEmpty()) {
/*  247 */           REGISTRY.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useFieldNames = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useClassName = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useShortClassName = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useIdentityHashCode = true;
/*      */ 
/*      */ 
/*      */   
/*  276 */   private String contentStart = "[";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  281 */   private String contentEnd = "]";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  286 */   private String fieldNameValueSeparator = "=";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fieldSeparatorAtStart = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fieldSeparatorAtEnd = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  301 */   private String fieldSeparator = ",";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  306 */   private String arrayStart = "{";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  311 */   private String arraySeparator = ",";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean arrayContentDetail = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  321 */   private String arrayEnd = "}";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean defaultFullDetail = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  332 */   private String nullText = "<null>";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  337 */   private String sizeStartText = "<size=";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  342 */   private String sizeEndText = ">";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  347 */   private String summaryObjectStartText = "<";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  352 */   private String summaryObjectEndText = ">";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendSuper(StringBuffer buffer, String superToString) {
/*  376 */     appendToString(buffer, superToString);
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
/*      */   public void appendToString(StringBuffer buffer, String toString) {
/*  390 */     if (toString != null) {
/*  391 */       int pos1 = toString.indexOf(this.contentStart) + this.contentStart.length();
/*  392 */       int pos2 = toString.lastIndexOf(this.contentEnd);
/*  393 */       if (pos1 != pos2 && pos1 >= 0 && pos2 >= 0) {
/*  394 */         if (this.fieldSeparatorAtStart) {
/*  395 */           removeLastFieldSeparator(buffer);
/*      */         }
/*  397 */         buffer.append(toString, pos1, pos2);
/*  398 */         appendFieldSeparator(buffer);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendStart(StringBuffer buffer, Object object) {
/*  410 */     if (object != null) {
/*  411 */       appendClassName(buffer, object);
/*  412 */       appendIdentityHashCode(buffer, object);
/*  413 */       appendContentStart(buffer);
/*  414 */       if (this.fieldSeparatorAtStart) {
/*  415 */         appendFieldSeparator(buffer);
/*      */       }
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
/*      */   public void appendEnd(StringBuffer buffer, Object object) {
/*  428 */     if (!this.fieldSeparatorAtEnd) {
/*  429 */       removeLastFieldSeparator(buffer);
/*      */     }
/*  431 */     appendContentEnd(buffer);
/*  432 */     unregister(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeLastFieldSeparator(StringBuffer buffer) {
/*  442 */     if (StringUtils.endsWith(buffer, this.fieldSeparator)) {
/*  443 */       buffer.setLength(buffer.length() - this.fieldSeparator.length());
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
/*      */   public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
/*  461 */     appendFieldStart(buffer, fieldName);
/*      */     
/*  463 */     if (value == null) {
/*  464 */       appendNullText(buffer, fieldName);
/*      */     } else {
/*      */       
/*  467 */       appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
/*      */     } 
/*      */     
/*  470 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendInternal(StringBuffer buffer, String fieldName, Object value, boolean detail) {
/*  493 */     if (isRegistered(value) && !(value instanceof Number) && !(value instanceof Boolean) && !(value instanceof Character)) {
/*      */       
/*  495 */       appendCyclicObject(buffer, fieldName, value);
/*      */       
/*      */       return;
/*      */     } 
/*  499 */     register(value);
/*      */     
/*      */     try {
/*  502 */       if (value instanceof Collection) {
/*  503 */         if (detail) {
/*  504 */           appendDetail(buffer, fieldName, (Collection)value);
/*      */         } else {
/*  506 */           appendSummarySize(buffer, fieldName, ((Collection)value).size());
/*      */         }
/*      */       
/*  509 */       } else if (value instanceof Map) {
/*  510 */         if (detail) {
/*  511 */           appendDetail(buffer, fieldName, (Map<?, ?>)value);
/*      */         } else {
/*  513 */           appendSummarySize(buffer, fieldName, ((Map)value).size());
/*      */         }
/*      */       
/*  516 */       } else if (value instanceof long[]) {
/*  517 */         if (detail) {
/*  518 */           appendDetail(buffer, fieldName, (long[])value);
/*      */         } else {
/*  520 */           appendSummary(buffer, fieldName, (long[])value);
/*      */         }
/*      */       
/*  523 */       } else if (value instanceof int[]) {
/*  524 */         if (detail) {
/*  525 */           appendDetail(buffer, fieldName, (int[])value);
/*      */         } else {
/*  527 */           appendSummary(buffer, fieldName, (int[])value);
/*      */         }
/*      */       
/*  530 */       } else if (value instanceof short[]) {
/*  531 */         if (detail) {
/*  532 */           appendDetail(buffer, fieldName, (short[])value);
/*      */         } else {
/*  534 */           appendSummary(buffer, fieldName, (short[])value);
/*      */         }
/*      */       
/*  537 */       } else if (value instanceof byte[]) {
/*  538 */         if (detail) {
/*  539 */           appendDetail(buffer, fieldName, (byte[])value);
/*      */         } else {
/*  541 */           appendSummary(buffer, fieldName, (byte[])value);
/*      */         }
/*      */       
/*  544 */       } else if (value instanceof char[]) {
/*  545 */         if (detail) {
/*  546 */           appendDetail(buffer, fieldName, (char[])value);
/*      */         } else {
/*  548 */           appendSummary(buffer, fieldName, (char[])value);
/*      */         }
/*      */       
/*  551 */       } else if (value instanceof double[]) {
/*  552 */         if (detail) {
/*  553 */           appendDetail(buffer, fieldName, (double[])value);
/*      */         } else {
/*  555 */           appendSummary(buffer, fieldName, (double[])value);
/*      */         }
/*      */       
/*  558 */       } else if (value instanceof float[]) {
/*  559 */         if (detail) {
/*  560 */           appendDetail(buffer, fieldName, (float[])value);
/*      */         } else {
/*  562 */           appendSummary(buffer, fieldName, (float[])value);
/*      */         }
/*      */       
/*  565 */       } else if (value instanceof boolean[]) {
/*  566 */         if (detail) {
/*  567 */           appendDetail(buffer, fieldName, (boolean[])value);
/*      */         } else {
/*  569 */           appendSummary(buffer, fieldName, (boolean[])value);
/*      */         }
/*      */       
/*  572 */       } else if (value.getClass().isArray()) {
/*  573 */         if (detail) {
/*  574 */           appendDetail(buffer, fieldName, (Object[])value);
/*      */         } else {
/*  576 */           appendSummary(buffer, fieldName, (Object[])value);
/*      */         }
/*      */       
/*      */       }
/*  580 */       else if (detail) {
/*  581 */         appendDetail(buffer, fieldName, value);
/*      */       } else {
/*  583 */         appendSummary(buffer, fieldName, value);
/*      */       } 
/*      */     } finally {
/*      */       
/*  587 */       unregister(value);
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
/*      */   protected void appendCyclicObject(StringBuffer buffer, String fieldName, Object value) {
/*  604 */     ObjectUtils.identityToString(buffer, value);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
/*  617 */     buffer.append(value);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll) {
/*  629 */     if (coll != null && !coll.isEmpty()) {
/*  630 */       buffer.append(this.arrayStart);
/*  631 */       int i = 0;
/*  632 */       for (Object item : coll) {
/*  633 */         appendDetail(buffer, fieldName, i++, item);
/*      */       }
/*  635 */       buffer.append(this.arrayEnd);
/*      */       
/*      */       return;
/*      */     } 
/*  639 */     buffer.append(coll);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map) {
/*  651 */     buffer.append(map);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, Object value) {
/*  664 */     buffer.append(this.summaryObjectStartText);
/*  665 */     buffer.append(getShortClassName(value.getClass()));
/*  666 */     buffer.append(this.summaryObjectEndText);
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
/*      */   public void append(StringBuffer buffer, String fieldName, long value) {
/*  680 */     appendFieldStart(buffer, fieldName);
/*  681 */     appendDetail(buffer, fieldName, value);
/*  682 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, long value) {
/*  694 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, int value) {
/*  708 */     appendFieldStart(buffer, fieldName);
/*  709 */     appendDetail(buffer, fieldName, value);
/*  710 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, int value) {
/*  722 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, short value) {
/*  736 */     appendFieldStart(buffer, fieldName);
/*  737 */     appendDetail(buffer, fieldName, value);
/*  738 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, short value) {
/*  750 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, byte value) {
/*  764 */     appendFieldStart(buffer, fieldName);
/*  765 */     appendDetail(buffer, fieldName, value);
/*  766 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, byte value) {
/*  778 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, char value) {
/*  792 */     appendFieldStart(buffer, fieldName);
/*  793 */     appendDetail(buffer, fieldName, value);
/*  794 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, char value) {
/*  806 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, double value) {
/*  820 */     appendFieldStart(buffer, fieldName);
/*  821 */     appendDetail(buffer, fieldName, value);
/*  822 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, double value) {
/*  834 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, float value) {
/*  848 */     appendFieldStart(buffer, fieldName);
/*  849 */     appendDetail(buffer, fieldName, value);
/*  850 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, float value) {
/*  862 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, boolean value) {
/*  876 */     appendFieldStart(buffer, fieldName);
/*  877 */     appendDetail(buffer, fieldName, value);
/*  878 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean value) {
/*  890 */     buffer.append(value);
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
/*      */   public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail) {
/*  904 */     appendFieldStart(buffer, fieldName);
/*      */     
/*  906 */     if (array == null) {
/*  907 */       appendNullText(buffer, fieldName);
/*      */     }
/*  909 */     else if (isFullDetail(fullDetail)) {
/*  910 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/*  913 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/*  916 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
/*  931 */     buffer.append(this.arrayStart);
/*  932 */     for (int i = 0; i < array.length; i++) {
/*  933 */       Object item = array[i];
/*  934 */       appendDetail(buffer, fieldName, i, item);
/*      */     } 
/*  936 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, int i, Object item) {
/*  950 */     if (i > 0) {
/*  951 */       buffer.append(this.arraySeparator);
/*      */     }
/*  953 */     if (item == null) {
/*  954 */       appendNullText(buffer, fieldName);
/*      */     } else {
/*  956 */       appendInternal(buffer, fieldName, item, this.arrayContentDetail);
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
/*      */   protected void reflectionAppendArrayDetail(StringBuffer buffer, String fieldName, Object array) {
/*  970 */     buffer.append(this.arrayStart);
/*  971 */     int length = Array.getLength(array);
/*  972 */     for (int i = 0; i < length; i++) {
/*  973 */       Object item = Array.get(array, i);
/*  974 */       appendDetail(buffer, fieldName, i, item);
/*      */     } 
/*  976 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, Object[] array) {
/*  989 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, long[] array, Boolean fullDetail) {
/* 1005 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1007 */     if (array == null) {
/* 1008 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1010 */     else if (isFullDetail(fullDetail)) {
/* 1011 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1014 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1017 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, long[] array) {
/* 1030 */     buffer.append(this.arrayStart);
/* 1031 */     for (int i = 0; i < array.length; i++) {
/* 1032 */       if (i > 0) {
/* 1033 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1035 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1037 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, long[] array) {
/* 1050 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, int[] array, Boolean fullDetail) {
/* 1066 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1068 */     if (array == null) {
/* 1069 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1071 */     else if (isFullDetail(fullDetail)) {
/* 1072 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1075 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1078 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, int[] array) {
/* 1091 */     buffer.append(this.arrayStart);
/* 1092 */     for (int i = 0; i < array.length; i++) {
/* 1093 */       if (i > 0) {
/* 1094 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1096 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1098 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, int[] array) {
/* 1111 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, short[] array, Boolean fullDetail) {
/* 1127 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1129 */     if (array == null) {
/* 1130 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1132 */     else if (isFullDetail(fullDetail)) {
/* 1133 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1136 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1139 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, short[] array) {
/* 1152 */     buffer.append(this.arrayStart);
/* 1153 */     for (int i = 0; i < array.length; i++) {
/* 1154 */       if (i > 0) {
/* 1155 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1157 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1159 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, short[] array) {
/* 1172 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, byte[] array, Boolean fullDetail) {
/* 1188 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1190 */     if (array == null) {
/* 1191 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1193 */     else if (isFullDetail(fullDetail)) {
/* 1194 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1197 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1200 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array) {
/* 1213 */     buffer.append(this.arrayStart);
/* 1214 */     for (int i = 0; i < array.length; i++) {
/* 1215 */       if (i > 0) {
/* 1216 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1218 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1220 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, byte[] array) {
/* 1233 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, char[] array, Boolean fullDetail) {
/* 1249 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1251 */     if (array == null) {
/* 1252 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1254 */     else if (isFullDetail(fullDetail)) {
/* 1255 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1258 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1261 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, char[] array) {
/* 1274 */     buffer.append(this.arrayStart);
/* 1275 */     for (int i = 0; i < array.length; i++) {
/* 1276 */       if (i > 0) {
/* 1277 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1279 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1281 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, char[] array) {
/* 1294 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, double[] array, Boolean fullDetail) {
/* 1310 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1312 */     if (array == null) {
/* 1313 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1315 */     else if (isFullDetail(fullDetail)) {
/* 1316 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1319 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1322 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, double[] array) {
/* 1335 */     buffer.append(this.arrayStart);
/* 1336 */     for (int i = 0; i < array.length; i++) {
/* 1337 */       if (i > 0) {
/* 1338 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1340 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1342 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, double[] array) {
/* 1355 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, float[] array, Boolean fullDetail) {
/* 1371 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1373 */     if (array == null) {
/* 1374 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1376 */     else if (isFullDetail(fullDetail)) {
/* 1377 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1380 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1383 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, float[] array) {
/* 1396 */     buffer.append(this.arrayStart);
/* 1397 */     for (int i = 0; i < array.length; i++) {
/* 1398 */       if (i > 0) {
/* 1399 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1401 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1403 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, float[] array) {
/* 1416 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   public void append(StringBuffer buffer, String fieldName, boolean[] array, Boolean fullDetail) {
/* 1432 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1434 */     if (array == null) {
/* 1435 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1437 */     else if (isFullDetail(fullDetail)) {
/* 1438 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1441 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1444 */     appendFieldEnd(buffer, fieldName);
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
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean[] array) {
/* 1457 */     buffer.append(this.arrayStart);
/* 1458 */     for (int i = 0; i < array.length; i++) {
/* 1459 */       if (i > 0) {
/* 1460 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1462 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1464 */     buffer.append(this.arrayEnd);
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
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, boolean[] array) {
/* 1477 */     appendSummarySize(buffer, fieldName, array.length);
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
/*      */   protected void appendClassName(StringBuffer buffer, Object object) {
/* 1489 */     if (this.useClassName && object != null) {
/* 1490 */       register(object);
/* 1491 */       if (this.useShortClassName) {
/* 1492 */         buffer.append(getShortClassName(object.getClass()));
/*      */       } else {
/* 1494 */         buffer.append(object.getClass().getName());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendIdentityHashCode(StringBuffer buffer, Object object) {
/* 1506 */     if (isUseIdentityHashCode() && object != null) {
/* 1507 */       register(object);
/* 1508 */       buffer.append('@');
/* 1509 */       buffer.append(Integer.toHexString(System.identityHashCode(object)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendContentStart(StringBuffer buffer) {
/* 1519 */     buffer.append(this.contentStart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendContentEnd(StringBuffer buffer) {
/* 1528 */     buffer.append(this.contentEnd);
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
/*      */   protected void appendNullText(StringBuffer buffer, String fieldName) {
/* 1540 */     buffer.append(this.nullText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendFieldSeparator(StringBuffer buffer) {
/* 1549 */     buffer.append(this.fieldSeparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendFieldStart(StringBuffer buffer, String fieldName) {
/* 1559 */     if (this.useFieldNames && fieldName != null) {
/* 1560 */       buffer.append(fieldName);
/* 1561 */       buffer.append(this.fieldNameValueSeparator);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendFieldEnd(StringBuffer buffer, String fieldName) {
/* 1572 */     appendFieldSeparator(buffer);
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
/*      */   protected void appendSummarySize(StringBuffer buffer, String fieldName, int size) {
/* 1591 */     buffer.append(this.sizeStartText);
/* 1592 */     buffer.append(size);
/* 1593 */     buffer.append(this.sizeEndText);
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
/*      */   protected boolean isFullDetail(Boolean fullDetailRequest) {
/* 1611 */     if (fullDetailRequest == null) {
/* 1612 */       return this.defaultFullDetail;
/*      */     }
/* 1614 */     return fullDetailRequest.booleanValue();
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
/*      */   protected String getShortClassName(Class<?> cls) {
/* 1627 */     return ClassUtils.getShortClassName(cls);
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
/*      */   protected boolean isUseClassName() {
/* 1641 */     return this.useClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseClassName(boolean useClassName) {
/* 1650 */     this.useClassName = useClassName;
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
/*      */   protected boolean isUseShortClassName() {
/* 1662 */     return this.useShortClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseShortClassName(boolean useShortClassName) {
/* 1672 */     this.useShortClassName = useShortClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isUseIdentityHashCode() {
/* 1683 */     return this.useIdentityHashCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseIdentityHashCode(boolean useIdentityHashCode) {
/* 1692 */     this.useIdentityHashCode = useIdentityHashCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isUseFieldNames() {
/* 1703 */     return this.useFieldNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseFieldNames(boolean useFieldNames) {
/* 1712 */     this.useFieldNames = useFieldNames;
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
/*      */   protected boolean isDefaultFullDetail() {
/* 1724 */     return this.defaultFullDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDefaultFullDetail(boolean defaultFullDetail) {
/* 1734 */     this.defaultFullDetail = defaultFullDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isArrayContentDetail() {
/* 1745 */     return this.arrayContentDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setArrayContentDetail(boolean arrayContentDetail) {
/* 1754 */     this.arrayContentDetail = arrayContentDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getArrayStart() {
/* 1765 */     return this.arrayStart;
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
/*      */   protected void setArrayStart(String arrayStart) {
/* 1777 */     if (arrayStart == null) {
/* 1778 */       arrayStart = "";
/*      */     }
/* 1780 */     this.arrayStart = arrayStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getArrayEnd() {
/* 1791 */     return this.arrayEnd;
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
/*      */   protected void setArrayEnd(String arrayEnd) {
/* 1803 */     if (arrayEnd == null) {
/* 1804 */       arrayEnd = "";
/*      */     }
/* 1806 */     this.arrayEnd = arrayEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getArraySeparator() {
/* 1817 */     return this.arraySeparator;
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
/*      */   protected void setArraySeparator(String arraySeparator) {
/* 1829 */     if (arraySeparator == null) {
/* 1830 */       arraySeparator = "";
/*      */     }
/* 1832 */     this.arraySeparator = arraySeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getContentStart() {
/* 1843 */     return this.contentStart;
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
/*      */   protected void setContentStart(String contentStart) {
/* 1855 */     if (contentStart == null) {
/* 1856 */       contentStart = "";
/*      */     }
/* 1858 */     this.contentStart = contentStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getContentEnd() {
/* 1869 */     return this.contentEnd;
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
/*      */   protected void setContentEnd(String contentEnd) {
/* 1881 */     if (contentEnd == null) {
/* 1882 */       contentEnd = "";
/*      */     }
/* 1884 */     this.contentEnd = contentEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getFieldNameValueSeparator() {
/* 1895 */     return this.fieldNameValueSeparator;
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
/*      */   protected void setFieldNameValueSeparator(String fieldNameValueSeparator) {
/* 1907 */     if (fieldNameValueSeparator == null) {
/* 1908 */       fieldNameValueSeparator = "";
/*      */     }
/* 1910 */     this.fieldNameValueSeparator = fieldNameValueSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getFieldSeparator() {
/* 1921 */     return this.fieldSeparator;
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
/*      */   protected void setFieldSeparator(String fieldSeparator) {
/* 1933 */     if (fieldSeparator == null) {
/* 1934 */       fieldSeparator = "";
/*      */     }
/* 1936 */     this.fieldSeparator = fieldSeparator;
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
/*      */   protected boolean isFieldSeparatorAtStart() {
/* 1949 */     return this.fieldSeparatorAtStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFieldSeparatorAtStart(boolean fieldSeparatorAtStart) {
/* 1960 */     this.fieldSeparatorAtStart = fieldSeparatorAtStart;
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
/*      */   protected boolean isFieldSeparatorAtEnd() {
/* 1973 */     return this.fieldSeparatorAtEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFieldSeparatorAtEnd(boolean fieldSeparatorAtEnd) {
/* 1984 */     this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getNullText() {
/* 1995 */     return this.nullText;
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
/*      */   protected void setNullText(String nullText) {
/* 2007 */     if (nullText == null) {
/* 2008 */       nullText = "";
/*      */     }
/* 2010 */     this.nullText = nullText;
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
/*      */   protected String getSizeStartText() {
/* 2024 */     return this.sizeStartText;
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
/*      */   protected void setSizeStartText(String sizeStartText) {
/* 2039 */     if (sizeStartText == null) {
/* 2040 */       sizeStartText = "";
/*      */     }
/* 2042 */     this.sizeStartText = sizeStartText;
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
/*      */   protected String getSizeEndText() {
/* 2056 */     return this.sizeEndText;
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
/*      */   protected void setSizeEndText(String sizeEndText) {
/* 2071 */     if (sizeEndText == null) {
/* 2072 */       sizeEndText = "";
/*      */     }
/* 2074 */     this.sizeEndText = sizeEndText;
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
/*      */   protected String getSummaryObjectStartText() {
/* 2088 */     return this.summaryObjectStartText;
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
/*      */   protected void setSummaryObjectStartText(String summaryObjectStartText) {
/* 2103 */     if (summaryObjectStartText == null) {
/* 2104 */       summaryObjectStartText = "";
/*      */     }
/* 2106 */     this.summaryObjectStartText = summaryObjectStartText;
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
/*      */   protected String getSummaryObjectEndText() {
/* 2120 */     return this.summaryObjectEndText;
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
/*      */   protected void setSummaryObjectEndText(String summaryObjectEndText) {
/* 2135 */     if (summaryObjectEndText == null) {
/* 2136 */       summaryObjectEndText = "";
/*      */     }
/* 2138 */     this.summaryObjectEndText = summaryObjectEndText;
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
/*      */   private static final class DefaultToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2173 */       return DEFAULT_STYLE;
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
/*      */   private static final class NoFieldNameToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NoFieldNameToStringStyle() {
/* 2198 */       setUseFieldNames(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2207 */       return NO_FIELD_NAMES_STYLE;
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
/*      */   private static final class ShortPrefixToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ShortPrefixToStringStyle() {
/* 2232 */       setUseShortClassName(true);
/* 2233 */       setUseIdentityHashCode(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2241 */       return SHORT_PREFIX_STYLE;
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
/*      */   private static final class SimpleToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SimpleToStringStyle() {
/* 2266 */       setUseClassName(false);
/* 2267 */       setUseIdentityHashCode(false);
/* 2268 */       setUseFieldNames(false);
/* 2269 */       setContentStart("");
/* 2270 */       setContentEnd("");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2278 */       return SIMPLE_STYLE;
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
/*      */   private static final class MultiLineToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MultiLineToStringStyle() {
/* 2302 */       setContentStart("[");
/* 2303 */       setFieldSeparator(System.lineSeparator() + "  ");
/* 2304 */       setFieldSeparatorAtStart(true);
/* 2305 */       setContentEnd(System.lineSeparator() + "]");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2314 */       return MULTI_LINE_STYLE;
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
/*      */   private static final class NoClassNameToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NoClassNameToStringStyle() {
/* 2339 */       setUseClassName(false);
/* 2340 */       setUseIdentityHashCode(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2349 */       return NO_CLASS_NAME_STYLE;
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
/*      */   private static final class JsonToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final String FIELD_NAME_QUOTE = "\"";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     JsonToStringStyle() {
/* 2387 */       setUseClassName(false);
/* 2388 */       setUseIdentityHashCode(false);
/*      */       
/* 2390 */       setContentStart("{");
/* 2391 */       setContentEnd("}");
/*      */       
/* 2393 */       setArrayStart("[");
/* 2394 */       setArrayEnd("]");
/*      */       
/* 2396 */       setFieldSeparator(",");
/* 2397 */       setFieldNameValueSeparator(":");
/*      */       
/* 2399 */       setNullText("null");
/*      */       
/* 2401 */       setSummaryObjectStartText("\"<");
/* 2402 */       setSummaryObjectEndText(">\"");
/*      */       
/* 2404 */       setSizeStartText("\"<size=");
/* 2405 */       setSizeEndText(">\"");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail) {
/* 2412 */       if (fieldName == null) {
/* 2413 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2416 */       if (!isFullDetail(fullDetail)) {
/* 2417 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2421 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, long[] array, Boolean fullDetail) {
/* 2428 */       if (fieldName == null) {
/* 2429 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2432 */       if (!isFullDetail(fullDetail)) {
/* 2433 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2437 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, int[] array, Boolean fullDetail) {
/* 2444 */       if (fieldName == null) {
/* 2445 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2448 */       if (!isFullDetail(fullDetail)) {
/* 2449 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2453 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, short[] array, Boolean fullDetail) {
/* 2460 */       if (fieldName == null) {
/* 2461 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2464 */       if (!isFullDetail(fullDetail)) {
/* 2465 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2469 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, byte[] array, Boolean fullDetail) {
/* 2476 */       if (fieldName == null) {
/* 2477 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2480 */       if (!isFullDetail(fullDetail)) {
/* 2481 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2485 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, char[] array, Boolean fullDetail) {
/* 2492 */       if (fieldName == null) {
/* 2493 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2496 */       if (!isFullDetail(fullDetail)) {
/* 2497 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2501 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, double[] array, Boolean fullDetail) {
/* 2508 */       if (fieldName == null) {
/* 2509 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2512 */       if (!isFullDetail(fullDetail)) {
/* 2513 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2517 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, float[] array, Boolean fullDetail) {
/* 2524 */       if (fieldName == null) {
/* 2525 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2528 */       if (!isFullDetail(fullDetail)) {
/* 2529 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2533 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, boolean[] array, Boolean fullDetail) {
/* 2540 */       if (fieldName == null) {
/* 2541 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2544 */       if (!isFullDetail(fullDetail)) {
/* 2545 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2549 */       super.append(buffer, fieldName, array, fullDetail);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
/* 2556 */       if (fieldName == null) {
/* 2557 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */       
/* 2560 */       if (!isFullDetail(fullDetail)) {
/* 2561 */         throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2565 */       super.append(buffer, fieldName, value, fullDetail);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void appendDetail(StringBuffer buffer, String fieldName, char value) {
/* 2570 */       appendValueAsString(buffer, String.valueOf(value));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
/* 2576 */       if (value == null) {
/* 2577 */         appendNullText(buffer, fieldName);
/*      */         
/*      */         return;
/*      */       } 
/* 2581 */       if (value instanceof String || value instanceof Character) {
/* 2582 */         appendValueAsString(buffer, value.toString());
/*      */         
/*      */         return;
/*      */       } 
/* 2586 */       if (value instanceof Number || value instanceof Boolean) {
/* 2587 */         buffer.append(value);
/*      */         
/*      */         return;
/*      */       } 
/* 2591 */       String valueAsString = value.toString();
/* 2592 */       if (isJsonObject(valueAsString) || isJsonArray(valueAsString)) {
/* 2593 */         buffer.append(value);
/*      */         
/*      */         return;
/*      */       } 
/* 2597 */       appendDetail(buffer, fieldName, valueAsString);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map) {
/* 2602 */       if (map != null && !map.isEmpty()) {
/* 2603 */         buffer.append(getContentStart());
/*      */         
/* 2605 */         boolean firstItem = true;
/* 2606 */         for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 2607 */           String keyStr = Objects.toString(entry.getKey(), null);
/* 2608 */           if (keyStr != null) {
/* 2609 */             if (firstItem) {
/* 2610 */               firstItem = false;
/*      */             } else {
/* 2612 */               appendFieldEnd(buffer, keyStr);
/*      */             } 
/* 2614 */             appendFieldStart(buffer, keyStr);
/* 2615 */             Object value = entry.getValue();
/* 2616 */             if (value == null) {
/* 2617 */               appendNullText(buffer, keyStr); continue;
/*      */             } 
/* 2619 */             appendInternal(buffer, keyStr, value, true);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2624 */         buffer.append(getContentEnd());
/*      */         
/*      */         return;
/*      */       } 
/* 2628 */       buffer.append(map);
/*      */     }
/*      */     
/*      */     private boolean isJsonArray(String valueAsString) {
/* 2632 */       return (valueAsString.startsWith(getArrayStart()) && valueAsString
/* 2633 */         .endsWith(getArrayEnd()));
/*      */     }
/*      */     
/*      */     private boolean isJsonObject(String valueAsString) {
/* 2637 */       return (valueAsString.startsWith(getContentStart()) && valueAsString
/* 2638 */         .endsWith(getContentEnd()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void appendValueAsString(StringBuffer buffer, String value) {
/* 2648 */       buffer.append('"').append(StringEscapeUtils.escapeJson(value)).append('"');
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void appendFieldStart(StringBuffer buffer, String fieldName) {
/* 2654 */       if (fieldName == null) {
/* 2655 */         throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
/*      */       }
/*      */ 
/*      */       
/* 2659 */       super.appendFieldStart(buffer, "\"" + StringEscapeUtils.escapeJson(fieldName) + "\"");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2671 */       return JSON_STYLE;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\ToStringStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */