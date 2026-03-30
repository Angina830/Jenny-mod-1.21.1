/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotationMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ public class CreatorCollector
/*     */ {
/*     */   protected static final int C_DEFAULT = 0;
/*     */   protected static final int C_STRING = 1;
/*     */   protected static final int C_INT = 2;
/*     */   protected static final int C_LONG = 3;
/*  32 */   protected static final String[] TYPE_DESCS = new String[] { "default", "from-String", "from-int", "from-long", "from-double", "from-boolean", "delegate", "property-based" };
/*     */ 
/*     */   
/*     */   protected static final int C_DOUBLE = 4;
/*     */   
/*     */   protected static final int C_BOOLEAN = 5;
/*     */   
/*     */   protected static final int C_DELEGATE = 6;
/*     */   
/*     */   protected static final int C_PROPS = 7;
/*     */   
/*     */   protected static final int C_ARRAY_DELEGATE = 8;
/*     */   
/*     */   protected final BeanDescription _beanDesc;
/*     */   
/*     */   protected final boolean _canFixAccess;
/*     */   
/*     */   protected final boolean _forceAccess;
/*     */   
/*  51 */   protected final AnnotatedWithParams[] _creators = new AnnotatedWithParams[9];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   protected int _explicitCreators = 0;
/*     */ 
/*     */   
/*     */   protected boolean _hasNonDefaultCreator = false;
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty[] _delegateArgs;
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty[] _arrayDelegateArgs;
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty[] _propertyBasedArgs;
/*     */ 
/*     */   
/*     */   protected AnnotatedParameter _incompleteParameter;
/*     */ 
/*     */ 
/*     */   
/*     */   public CreatorCollector(BeanDescription beanDesc, MapperConfig<?> config) {
/*  80 */     this._beanDesc = beanDesc;
/*  81 */     this._canFixAccess = config.canOverrideAccessModifiers();
/*  82 */     this._forceAccess = config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueInstantiator constructValueInstantiator(DeserializationConfig config) {
/*  88 */     JavaType delegateType = _computeDelegateType(this._creators[6], this._delegateArgs);
/*     */     
/*  90 */     JavaType arrayDelegateType = _computeDelegateType(this._creators[8], this._arrayDelegateArgs);
/*     */     
/*  92 */     JavaType type = this._beanDesc.getType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     AnnotatedWithParams defaultCtor = StdTypeConstructor.tryToOptimize(this._creators[0]);
/*     */ 
/*     */     
/* 100 */     StdValueInstantiator inst = new StdValueInstantiator(config, type);
/* 101 */     inst.configureFromObjectSettings(defaultCtor, this._creators[6], delegateType, this._delegateArgs, this._creators[7], this._propertyBasedArgs);
/*     */ 
/*     */     
/* 104 */     inst.configureFromArraySettings(this._creators[8], arrayDelegateType, this._arrayDelegateArgs);
/*     */     
/* 106 */     inst.configureFromStringCreator(this._creators[1]);
/* 107 */     inst.configureFromIntCreator(this._creators[2]);
/* 108 */     inst.configureFromLongCreator(this._creators[3]);
/* 109 */     inst.configureFromDoubleCreator(this._creators[4]);
/* 110 */     inst.configureFromBooleanCreator(this._creators[5]);
/* 111 */     inst.configureIncompleteParameter(this._incompleteParameter);
/* 112 */     return (ValueInstantiator)inst;
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
/*     */   public void setDefaultCreator(AnnotatedWithParams creator) {
/* 132 */     this._creators[0] = _fixAccess(creator);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStringCreator(AnnotatedWithParams creator, boolean explicit) {
/* 137 */     verifyNonDup(creator, 1, explicit);
/*     */   }
/*     */   
/*     */   public void addIntCreator(AnnotatedWithParams creator, boolean explicit) {
/* 141 */     verifyNonDup(creator, 2, explicit);
/*     */   }
/*     */   
/*     */   public void addLongCreator(AnnotatedWithParams creator, boolean explicit) {
/* 145 */     verifyNonDup(creator, 3, explicit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDoubleCreator(AnnotatedWithParams creator, boolean explicit) {
/* 150 */     verifyNonDup(creator, 4, explicit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBooleanCreator(AnnotatedWithParams creator, boolean explicit) {
/* 155 */     verifyNonDup(creator, 5, explicit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDelegatingCreator(AnnotatedWithParams creator, boolean explicit, SettableBeanProperty[] injectables) {
/* 160 */     if (creator.getParameterType(0).isCollectionLikeType()) {
/* 161 */       if (verifyNonDup(creator, 8, explicit)) {
/* 162 */         this._arrayDelegateArgs = injectables;
/*     */       }
/*     */     }
/* 165 */     else if (verifyNonDup(creator, 6, explicit)) {
/* 166 */       this._delegateArgs = injectables;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyCreator(AnnotatedWithParams creator, boolean explicit, SettableBeanProperty[] properties) {
/* 173 */     if (verifyNonDup(creator, 7, explicit)) {
/*     */       
/* 175 */       if (properties.length > 1) {
/* 176 */         HashMap<String, Integer> names = new HashMap<>();
/* 177 */         for (int i = 0, len = properties.length; i < len; i++) {
/* 178 */           String name = properties[i].getName();
/*     */ 
/*     */           
/* 181 */           if (name.length() != 0 || properties[i].getInjectableValueId() == null) {
/*     */ 
/*     */ 
/*     */             
/* 185 */             Integer old = names.put(name, Integer.valueOf(i));
/* 186 */             if (old != null) {
/* 187 */               throw new IllegalArgumentException(String.format("Duplicate creator property \"%s\" (index %s vs %d)", new Object[] { name, old, Integer.valueOf(i) }));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 193 */       this._propertyBasedArgs = properties;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addIncompeteParameter(AnnotatedParameter parameter) {
/* 198 */     if (this._incompleteParameter == null) {
/* 199 */       this._incompleteParameter = parameter;
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
/*     */   public boolean hasDefaultCreator() {
/* 213 */     return (this._creators[0] != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDelegatingCreator() {
/* 220 */     return (this._creators[6] != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPropertyBasedCreator() {
/* 227 */     return (this._creators[7] != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JavaType _computeDelegateType(AnnotatedWithParams creator, SettableBeanProperty[] delegateArgs) {
/* 238 */     if (!this._hasNonDefaultCreator || creator == null) {
/* 239 */       return null;
/*     */     }
/*     */     
/* 242 */     int ix = 0;
/* 243 */     if (delegateArgs != null) {
/* 244 */       for (int i = 0, len = delegateArgs.length; i < len; i++) {
/* 245 */         if (delegateArgs[i] == null) {
/* 246 */           ix = i;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 251 */     return creator.getParameterType(ix);
/*     */   }
/*     */   
/*     */   private <T extends software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember> T _fixAccess(T member) {
/* 255 */     if (member != null && this._canFixAccess) {
/* 256 */       ClassUtil.checkAndFixAccess((Member)member.getAnnotated(), this._forceAccess);
/*     */     }
/*     */     
/* 259 */     return member;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean verifyNonDup(AnnotatedWithParams newOne, int typeIndex, boolean explicit) {
/* 267 */     int mask = 1 << typeIndex;
/* 268 */     this._hasNonDefaultCreator = true;
/* 269 */     AnnotatedWithParams oldOne = this._creators[typeIndex];
/*     */     
/* 271 */     if (oldOne != null) {
/*     */       boolean verify;
/* 273 */       if ((this._explicitCreators & mask) != 0) {
/*     */         
/* 275 */         if (!explicit) {
/* 276 */           return false;
/*     */         }
/*     */         
/* 279 */         verify = true;
/*     */       } else {
/*     */         
/* 282 */         verify = !explicit;
/*     */       } 
/*     */ 
/*     */       
/* 286 */       if (verify && oldOne.getClass() == newOne.getClass()) {
/*     */         
/* 288 */         Class<?> oldType = oldOne.getRawParameterType(0);
/* 289 */         Class<?> newType = newOne.getRawParameterType(0);
/*     */         
/* 291 */         if (oldType == newType) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 297 */           if (_isEnumValueOf(newOne)) {
/* 298 */             return false;
/*     */           }
/* 300 */           if (!_isEnumValueOf(oldOne))
/*     */           {
/*     */             
/* 303 */             throw new IllegalArgumentException(String.format("Conflicting %s creators: already had %s creator %s, encountered another: %s", new Object[] { TYPE_DESCS[typeIndex], explicit ? "explicitly marked" : "implicitly discovered", oldOne, newOne }));
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 312 */         else if (newType.isAssignableFrom(oldType)) {
/*     */           
/* 314 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 319 */     if (explicit) {
/* 320 */       this._explicitCreators |= mask;
/*     */     }
/* 322 */     this._creators[typeIndex] = _fixAccess(newOne);
/* 323 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _isEnumValueOf(AnnotatedWithParams creator) {
/* 332 */     return (creator.getDeclaringClass().isEnum() && "valueOf".equals(creator.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class StdTypeConstructor
/*     */     extends AnnotatedWithParams
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*     */     public static final int TYPE_ARRAY_LIST = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     public static final int TYPE_HASH_MAP = 2;
/*     */ 
/*     */     
/*     */     public static final int TYPE_LINKED_HASH_MAP = 3;
/*     */ 
/*     */     
/*     */     private final AnnotatedWithParams _base;
/*     */ 
/*     */     
/*     */     private final int _type;
/*     */ 
/*     */ 
/*     */     
/*     */     public StdTypeConstructor(AnnotatedWithParams base, int t) {
/* 364 */       super(base, null);
/* 365 */       this._base = base;
/* 366 */       this._type = t;
/*     */     }
/*     */ 
/*     */     
/*     */     public static AnnotatedWithParams tryToOptimize(AnnotatedWithParams src) {
/* 371 */       if (src != null) {
/* 372 */         Class<?> rawType = src.getDeclaringClass();
/* 373 */         if (rawType == List.class || rawType == ArrayList.class) {
/* 374 */           return new StdTypeConstructor(src, 1);
/*     */         }
/* 376 */         if (rawType == LinkedHashMap.class) {
/* 377 */           return new StdTypeConstructor(src, 3);
/*     */         }
/* 379 */         if (rawType == HashMap.class) {
/* 380 */           return new StdTypeConstructor(src, 2);
/*     */         }
/*     */       } 
/* 383 */       return src;
/*     */     }
/*     */     
/*     */     protected final Object _construct() {
/* 387 */       switch (this._type) {
/*     */         case 1:
/* 389 */           return new ArrayList();
/*     */         case 3:
/* 391 */           return new LinkedHashMap<>();
/*     */         case 2:
/* 393 */           return new HashMap<>();
/*     */       } 
/* 395 */       throw new IllegalStateException("Unknown type " + this._type);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getParameterCount() {
/* 400 */       return this._base.getParameterCount();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getRawParameterType(int index) {
/* 405 */       return this._base.getRawParameterType(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getParameterType(int index) {
/* 410 */       return this._base.getParameterType(index);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Type getGenericParameterType(int index) {
/* 416 */       return this._base.getGenericParameterType(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object call() throws Exception {
/* 421 */       return _construct();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object call(Object[] args) throws Exception {
/* 426 */       return _construct();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object call1(Object arg) throws Exception {
/* 431 */       return _construct();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getDeclaringClass() {
/* 436 */       return this._base.getDeclaringClass();
/*     */     }
/*     */ 
/*     */     
/*     */     public Member getMember() {
/* 441 */       return this._base.getMember();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(Object pojo, Object value) throws UnsupportedOperationException, IllegalArgumentException {
/* 447 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getValue(Object pojo) throws UnsupportedOperationException, IllegalArgumentException {
/* 453 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Annotated withAnnotations(AnnotationMap fallback) {
/* 458 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedElement getAnnotated() {
/* 463 */       return this._base.getAnnotated();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getModifiers() {
/* 468 */       return this._base.getMember().getModifiers();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 473 */       return this._base.getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getType() {
/* 478 */       return this._base.getType();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getRawType() {
/* 483 */       return this._base.getRawType();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 488 */       return (o == this);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 493 */       return this._base.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 498 */       return this._base.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\CreatorCollector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */