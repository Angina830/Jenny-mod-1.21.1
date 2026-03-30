/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AnnotationCollector
/*     */ {
/*  18 */   protected static final Annotations NO_ANNOTATIONS = new NoAnnotations();
/*     */ 
/*     */   
/*     */   protected final Object _data;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotationCollector(Object d) {
/*  26 */     this._data = d;
/*     */   }
/*     */   public static Annotations emptyAnnotations() {
/*  29 */     return NO_ANNOTATIONS;
/*     */   }
/*     */   public static AnnotationCollector emptyCollector() {
/*  32 */     return EmptyCollector.instance;
/*     */   }
/*     */   
/*     */   public static AnnotationCollector emptyCollector(Object data) {
/*  36 */     return new EmptyCollector(data);
/*     */   }
/*     */   public abstract Annotations asAnnotations();
/*     */   
/*     */   public abstract AnnotationMap asAnnotationMap();
/*     */   
/*     */   public Object getData() {
/*  43 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isPresent(Annotation paramAnnotation);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract AnnotationCollector addOrOverride(Annotation paramAnnotation);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class EmptyCollector
/*     */     extends AnnotationCollector
/*     */   {
/*  64 */     public static final EmptyCollector instance = new EmptyCollector(null);
/*     */     EmptyCollector(Object data) {
/*  66 */       super(data);
/*     */     }
/*     */     
/*     */     public Annotations asAnnotations() {
/*  70 */       return NO_ANNOTATIONS;
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotationMap asAnnotationMap() {
/*  75 */       return new AnnotationMap();
/*     */     }
/*     */     
/*     */     public boolean isPresent(Annotation ann) {
/*  79 */       return false;
/*     */     }
/*     */     
/*     */     public AnnotationCollector addOrOverride(Annotation ann) {
/*  83 */       return new AnnotationCollector.OneCollector(this._data, ann.annotationType(), ann);
/*     */     }
/*     */   }
/*     */   
/*     */   static class OneCollector
/*     */     extends AnnotationCollector
/*     */   {
/*     */     private Class<?> _type;
/*     */     private Annotation _value;
/*     */     
/*     */     public OneCollector(Object data, Class<?> type, Annotation value) {
/*  94 */       super(data);
/*  95 */       this._type = type;
/*  96 */       this._value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public Annotations asAnnotations() {
/* 101 */       return new AnnotationCollector.OneAnnotation(this._type, this._value);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotationMap asAnnotationMap() {
/* 106 */       return AnnotationMap.of(this._type, this._value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPresent(Annotation ann) {
/* 111 */       return (ann.annotationType() == this._type);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotationCollector addOrOverride(Annotation ann) {
/* 116 */       Class<?> type = ann.annotationType();
/*     */       
/* 118 */       if (this._type == type) {
/* 119 */         this._value = ann;
/* 120 */         return this;
/*     */       } 
/* 122 */       return new AnnotationCollector.NCollector(this._data, this._type, this._value, type, ann);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class NCollector
/*     */     extends AnnotationCollector
/*     */   {
/*     */     protected final HashMap<Class<?>, Annotation> _annotations;
/*     */     
/*     */     public NCollector(Object data, Class<?> type1, Annotation value1, Class<?> type2, Annotation value2) {
/* 133 */       super(data);
/* 134 */       this._annotations = new HashMap<>();
/* 135 */       this._annotations.put(type1, value1);
/* 136 */       this._annotations.put(type2, value2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Annotations asAnnotations() {
/* 141 */       if (this._annotations.size() == 2) {
/* 142 */         Iterator<Map.Entry<Class<?>, Annotation>> it = this._annotations.entrySet().iterator();
/* 143 */         Map.Entry<Class<?>, Annotation> en1 = it.next(), en2 = it.next();
/* 144 */         return new AnnotationCollector.TwoAnnotations(en1.getKey(), en1.getValue(), en2.getKey(), en2.getValue());
/*     */       } 
/*     */       
/* 147 */       return new AnnotationMap(this._annotations);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotationMap asAnnotationMap() {
/* 152 */       AnnotationMap result = new AnnotationMap();
/* 153 */       for (Annotation ann : this._annotations.values()) {
/* 154 */         result.add(ann);
/*     */       }
/* 156 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPresent(Annotation ann) {
/* 161 */       return this._annotations.containsKey(ann.annotationType());
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotationCollector addOrOverride(Annotation ann) {
/* 166 */       this._annotations.put(ann.annotationType(), ann);
/* 167 */       return this;
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
/*     */   public static class NoAnnotations
/*     */     implements Annotations, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Annotation> A get(Class<A> cls) {
/* 192 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean has(Class<?> cls) {
/* 197 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasOneOf(Class<? extends Annotation>[] annoClasses) {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 207 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class OneAnnotation
/*     */     implements Annotations, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final Class<?> _type;
/*     */     private final Annotation _value;
/*     */     
/*     */     public OneAnnotation(Class<?> type, Annotation value) {
/* 220 */       this._type = type;
/* 221 */       this._value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Annotation> A get(Class<A> cls) {
/* 227 */       if (this._type == cls) {
/* 228 */         return (A)this._value;
/*     */       }
/* 230 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean has(Class<?> cls) {
/* 235 */       return (this._type == cls);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasOneOf(Class<? extends Annotation>[] annoClasses) {
/* 240 */       for (Class<?> cls : annoClasses) {
/* 241 */         if (cls == this._type) {
/* 242 */           return true;
/*     */         }
/*     */       } 
/* 245 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 250 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TwoAnnotations
/*     */     implements Annotations, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final Class<?> _type1;
/*     */     private final Class<?> _type2;
/*     */     private final Annotation _value1;
/*     */     private final Annotation _value2;
/*     */     
/*     */     public TwoAnnotations(Class<?> type1, Annotation value1, Class<?> type2, Annotation value2) {
/* 264 */       this._type1 = type1;
/* 265 */       this._value1 = value1;
/* 266 */       this._type2 = type2;
/* 267 */       this._value2 = value2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Annotation> A get(Class<A> cls) {
/* 273 */       if (this._type1 == cls) {
/* 274 */         return (A)this._value1;
/*     */       }
/* 276 */       if (this._type2 == cls) {
/* 277 */         return (A)this._value2;
/*     */       }
/* 279 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean has(Class<?> cls) {
/* 284 */       return (this._type1 == cls || this._type2 == cls);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasOneOf(Class<? extends Annotation>[] annoClasses) {
/* 289 */       for (Class<?> cls : annoClasses) {
/* 290 */         if (cls == this._type1 || cls == this._type2) {
/* 291 */           return true;
/*     */         }
/*     */       } 
/* 294 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 299 */       return 2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\AnnotationCollector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */