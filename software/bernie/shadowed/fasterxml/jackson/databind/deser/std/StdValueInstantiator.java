/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class StdValueInstantiator
/*     */   extends ValueInstantiator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final String _valueTypeDesc;
/*     */   protected final Class<?> _valueClass;
/*     */   protected AnnotatedWithParams _defaultCreator;
/*     */   protected AnnotatedWithParams _withArgsCreator;
/*     */   protected SettableBeanProperty[] _constructorArguments;
/*     */   protected JavaType _delegateType;
/*     */   protected AnnotatedWithParams _delegateCreator;
/*     */   protected SettableBeanProperty[] _delegateArguments;
/*     */   protected JavaType _arrayDelegateType;
/*     */   protected AnnotatedWithParams _arrayDelegateCreator;
/*     */   protected SettableBeanProperty[] _arrayDelegateArguments;
/*     */   protected AnnotatedWithParams _fromStringCreator;
/*     */   protected AnnotatedWithParams _fromIntCreator;
/*     */   protected AnnotatedWithParams _fromLongCreator;
/*     */   protected AnnotatedWithParams _fromDoubleCreator;
/*     */   protected AnnotatedWithParams _fromBooleanCreator;
/*     */   protected AnnotatedParameter _incompleteParameter;
/*     */   
/*     */   @Deprecated
/*     */   public StdValueInstantiator(DeserializationConfig config, Class<?> valueType) {
/*  83 */     this._valueTypeDesc = ClassUtil.nameOf(valueType);
/*  84 */     this._valueClass = (valueType == null) ? Object.class : valueType;
/*     */   }
/*     */   
/*     */   public StdValueInstantiator(DeserializationConfig config, JavaType valueType) {
/*  88 */     this._valueTypeDesc = (valueType == null) ? "UNKNOWN TYPE" : valueType.toString();
/*  89 */     this._valueClass = (valueType == null) ? Object.class : valueType.getRawClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdValueInstantiator(StdValueInstantiator src) {
/*  98 */     this._valueTypeDesc = src._valueTypeDesc;
/*  99 */     this._valueClass = src._valueClass;
/*     */     
/* 101 */     this._defaultCreator = src._defaultCreator;
/*     */     
/* 103 */     this._constructorArguments = src._constructorArguments;
/* 104 */     this._withArgsCreator = src._withArgsCreator;
/*     */     
/* 106 */     this._delegateType = src._delegateType;
/* 107 */     this._delegateCreator = src._delegateCreator;
/* 108 */     this._delegateArguments = src._delegateArguments;
/*     */     
/* 110 */     this._arrayDelegateType = src._arrayDelegateType;
/* 111 */     this._arrayDelegateCreator = src._arrayDelegateCreator;
/* 112 */     this._arrayDelegateArguments = src._arrayDelegateArguments;
/*     */     
/* 114 */     this._fromStringCreator = src._fromStringCreator;
/* 115 */     this._fromIntCreator = src._fromIntCreator;
/* 116 */     this._fromLongCreator = src._fromLongCreator;
/* 117 */     this._fromDoubleCreator = src._fromDoubleCreator;
/* 118 */     this._fromBooleanCreator = src._fromBooleanCreator;
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
/*     */   public void configureFromObjectSettings(AnnotatedWithParams defaultCreator, AnnotatedWithParams delegateCreator, JavaType delegateType, SettableBeanProperty[] delegateArgs, AnnotatedWithParams withArgsCreator, SettableBeanProperty[] constructorArgs) {
/* 130 */     this._defaultCreator = defaultCreator;
/* 131 */     this._delegateCreator = delegateCreator;
/* 132 */     this._delegateType = delegateType;
/* 133 */     this._delegateArguments = delegateArgs;
/* 134 */     this._withArgsCreator = withArgsCreator;
/* 135 */     this._constructorArguments = constructorArgs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureFromArraySettings(AnnotatedWithParams arrayDelegateCreator, JavaType arrayDelegateType, SettableBeanProperty[] arrayDelegateArgs) {
/* 143 */     this._arrayDelegateCreator = arrayDelegateCreator;
/* 144 */     this._arrayDelegateType = arrayDelegateType;
/* 145 */     this._arrayDelegateArguments = arrayDelegateArgs;
/*     */   }
/*     */   
/*     */   public void configureFromStringCreator(AnnotatedWithParams creator) {
/* 149 */     this._fromStringCreator = creator;
/*     */   }
/*     */   
/*     */   public void configureFromIntCreator(AnnotatedWithParams creator) {
/* 153 */     this._fromIntCreator = creator;
/*     */   }
/*     */   
/*     */   public void configureFromLongCreator(AnnotatedWithParams creator) {
/* 157 */     this._fromLongCreator = creator;
/*     */   }
/*     */   
/*     */   public void configureFromDoubleCreator(AnnotatedWithParams creator) {
/* 161 */     this._fromDoubleCreator = creator;
/*     */   }
/*     */   
/*     */   public void configureFromBooleanCreator(AnnotatedWithParams creator) {
/* 165 */     this._fromBooleanCreator = creator;
/*     */   }
/*     */   
/*     */   public void configureIncompleteParameter(AnnotatedParameter parameter) {
/* 169 */     this._incompleteParameter = parameter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueTypeDesc() {
/* 180 */     return this._valueTypeDesc;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> getValueClass() {
/* 185 */     return this._valueClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateFromString() {
/* 190 */     return (this._fromStringCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateFromInt() {
/* 195 */     return (this._fromIntCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateFromLong() {
/* 200 */     return (this._fromLongCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateFromDouble() {
/* 205 */     return (this._fromDoubleCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateFromBoolean() {
/* 210 */     return (this._fromBooleanCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateUsingDefault() {
/* 215 */     return (this._defaultCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateUsingDelegate() {
/* 220 */     return (this._delegateType != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateUsingArrayDelegate() {
/* 225 */     return (this._arrayDelegateType != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCreateFromObjectWith() {
/* 230 */     return (this._withArgsCreator != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getDelegateType(DeserializationConfig config) {
/* 235 */     return this._delegateType;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getArrayDelegateType(DeserializationConfig config) {
/* 240 */     return this._arrayDelegateType;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
/* 245 */     return this._constructorArguments;
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
/*     */   public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
/* 257 */     if (this._defaultCreator == null) {
/* 258 */       return super.createUsingDefault(ctxt);
/*     */     }
/*     */     try {
/* 261 */       return this._defaultCreator.call();
/* 262 */     } catch (Exception e) {
/* 263 */       return ctxt.handleInstantiationProblem(this._valueClass, null, (Throwable)rewrapCtorProblem(ctxt, e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) throws IOException {
/* 270 */     if (this._withArgsCreator == null) {
/* 271 */       return super.createFromObjectWith(ctxt, args);
/*     */     }
/*     */     try {
/* 274 */       return this._withArgsCreator.call(args);
/* 275 */     } catch (Exception e) {
/* 276 */       return ctxt.handleInstantiationProblem(this._valueClass, args, (Throwable)rewrapCtorProblem(ctxt, e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createUsingDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
/* 284 */     if (this._delegateCreator == null && 
/* 285 */       this._arrayDelegateCreator != null) {
/* 286 */       return _createUsingDelegate(this._arrayDelegateCreator, this._arrayDelegateArguments, ctxt, delegate);
/*     */     }
/*     */     
/* 289 */     return _createUsingDelegate(this._delegateCreator, this._delegateArguments, ctxt, delegate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createUsingArrayDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
/* 295 */     if (this._arrayDelegateCreator == null && 
/* 296 */       this._delegateCreator != null)
/*     */     {
/* 298 */       return createUsingDelegate(ctxt, delegate);
/*     */     }
/*     */     
/* 301 */     return _createUsingDelegate(this._arrayDelegateCreator, this._arrayDelegateArguments, ctxt, delegate);
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
/*     */   public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
/* 313 */     if (this._fromStringCreator == null) {
/* 314 */       return _createFromStringFallbacks(ctxt, value);
/*     */     }
/*     */     try {
/* 317 */       return this._fromStringCreator.call1(value);
/* 318 */     } catch (Throwable t) {
/* 319 */       return ctxt.handleInstantiationProblem(this._fromStringCreator.getDeclaringClass(), value, (Throwable)rewrapCtorProblem(ctxt, t));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromInt(DeserializationContext ctxt, int value) throws IOException {
/* 328 */     if (this._fromIntCreator != null) {
/* 329 */       Object arg = Integer.valueOf(value);
/*     */       try {
/* 331 */         return this._fromIntCreator.call1(arg);
/* 332 */       } catch (Throwable t0) {
/* 333 */         return ctxt.handleInstantiationProblem(this._fromIntCreator.getDeclaringClass(), arg, (Throwable)rewrapCtorProblem(ctxt, t0));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 338 */     if (this._fromLongCreator != null) {
/* 339 */       Object arg = Long.valueOf(value);
/*     */       try {
/* 341 */         return this._fromLongCreator.call1(arg);
/* 342 */       } catch (Throwable t0) {
/* 343 */         return ctxt.handleInstantiationProblem(this._fromLongCreator.getDeclaringClass(), arg, (Throwable)rewrapCtorProblem(ctxt, t0));
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     return super.createFromInt(ctxt, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromLong(DeserializationContext ctxt, long value) throws IOException {
/* 353 */     if (this._fromLongCreator == null) {
/* 354 */       return super.createFromLong(ctxt, value);
/*     */     }
/* 356 */     Object arg = Long.valueOf(value);
/*     */     try {
/* 358 */       return this._fromLongCreator.call1(arg);
/* 359 */     } catch (Throwable t0) {
/* 360 */       return ctxt.handleInstantiationProblem(this._fromLongCreator.getDeclaringClass(), arg, (Throwable)rewrapCtorProblem(ctxt, t0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromDouble(DeserializationContext ctxt, double value) throws IOException {
/* 368 */     if (this._fromDoubleCreator == null) {
/* 369 */       return super.createFromDouble(ctxt, value);
/*     */     }
/* 371 */     Object arg = Double.valueOf(value);
/*     */     try {
/* 373 */       return this._fromDoubleCreator.call1(arg);
/* 374 */     } catch (Throwable t0) {
/* 375 */       return ctxt.handleInstantiationProblem(this._fromDoubleCreator.getDeclaringClass(), arg, (Throwable)rewrapCtorProblem(ctxt, t0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromBoolean(DeserializationContext ctxt, boolean value) throws IOException {
/* 383 */     if (this._fromBooleanCreator == null) {
/* 384 */       return super.createFromBoolean(ctxt, value);
/*     */     }
/* 386 */     Boolean arg = Boolean.valueOf(value);
/*     */     try {
/* 388 */       return this._fromBooleanCreator.call1(arg);
/* 389 */     } catch (Throwable t0) {
/* 390 */       return ctxt.handleInstantiationProblem(this._fromBooleanCreator.getDeclaringClass(), arg, (Throwable)rewrapCtorProblem(ctxt, t0));
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
/*     */   public AnnotatedWithParams getDelegateCreator() {
/* 403 */     return this._delegateCreator;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getArrayDelegateCreator() {
/* 408 */     return this._arrayDelegateCreator;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getDefaultCreator() {
/* 413 */     return this._defaultCreator;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getWithArgsCreator() {
/* 418 */     return this._withArgsCreator;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedParameter getIncompleteParameter() {
/* 423 */     return this._incompleteParameter;
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
/*     */   @Deprecated
/*     */   protected JsonMappingException wrapException(Throwable t) {
/* 441 */     for (Throwable curr = t; curr != null; curr = curr.getCause()) {
/* 442 */       if (curr instanceof JsonMappingException) {
/* 443 */         return (JsonMappingException)curr;
/*     */       }
/*     */     } 
/* 446 */     return new JsonMappingException(null, "Instantiation of " + getValueTypeDesc() + " value failed: " + t.getMessage(), t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonMappingException unwrapAndWrapException(DeserializationContext ctxt, Throwable t) {
/* 457 */     for (Throwable curr = t; curr != null; curr = curr.getCause()) {
/* 458 */       if (curr instanceof JsonMappingException) {
/* 459 */         return (JsonMappingException)curr;
/*     */       }
/*     */     } 
/* 462 */     return ctxt.instantiationException(getValueClass(), t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonMappingException wrapAsJsonMappingException(DeserializationContext ctxt, Throwable t) {
/* 472 */     if (t instanceof JsonMappingException) {
/* 473 */       return (JsonMappingException)t;
/*     */     }
/* 475 */     return ctxt.instantiationException(getValueClass(), t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonMappingException rewrapCtorProblem(DeserializationContext ctxt, Throwable t) {
/* 486 */     if (t instanceof ExceptionInInitializerError || t instanceof java.lang.reflect.InvocationTargetException) {
/*     */ 
/*     */       
/* 489 */       Throwable cause = t.getCause();
/* 490 */       if (cause != null) {
/* 491 */         t = cause;
/*     */       }
/*     */     } 
/* 494 */     return wrapAsJsonMappingException(ctxt, t);
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
/*     */   private Object _createUsingDelegate(AnnotatedWithParams delegateCreator, SettableBeanProperty[] delegateArguments, DeserializationContext ctxt, Object delegate) throws IOException {
/* 510 */     if (delegateCreator == null) {
/* 511 */       throw new IllegalStateException("No delegate constructor for " + getValueTypeDesc());
/*     */     }
/*     */     
/*     */     try {
/* 515 */       if (delegateArguments == null) {
/* 516 */         return delegateCreator.call1(delegate);
/*     */       }
/*     */       
/* 519 */       int len = delegateArguments.length;
/* 520 */       Object[] args = new Object[len];
/* 521 */       for (int i = 0; i < len; i++) {
/* 522 */         SettableBeanProperty prop = delegateArguments[i];
/* 523 */         if (prop == null) {
/* 524 */           args[i] = delegate;
/*     */         } else {
/* 526 */           args[i] = ctxt.findInjectableValue(prop.getInjectableValueId(), (BeanProperty)prop, null);
/*     */         } 
/*     */       } 
/*     */       
/* 530 */       return delegateCreator.call(args);
/* 531 */     } catch (Throwable t) {
/* 532 */       throw rewrapCtorProblem(ctxt, t);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StdValueInstantiator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */