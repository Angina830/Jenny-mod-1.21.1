/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ValueInstantiator
/*     */ {
/*     */   public Class<?> getValueClass() {
/*  50 */     return Object.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueTypeDesc() {
/*  58 */     Class<?> cls = getValueClass();
/*  59 */     if (cls == null) {
/*  60 */       return "UNKNOWN";
/*     */     }
/*  62 */     return cls.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInstantiate() {
/*  71 */     return (canCreateUsingDefault() || canCreateUsingDelegate() || canCreateFromObjectWith() || canCreateFromString() || canCreateFromInt() || canCreateFromLong() || canCreateFromDouble() || canCreateFromBoolean());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateFromString() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateFromInt() {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateFromLong() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateFromDouble() {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateFromBoolean() {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateUsingDefault() {
/* 112 */     return (getDefaultCreator() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateUsingDelegate() {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateUsingArrayDelegate() {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCreateFromObjectWith() {
/* 135 */     return false;
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
/*     */   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getDelegateType(DeserializationConfig config) {
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getArrayDelegateType(DeserializationConfig config) {
/* 169 */     return null;
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
/*     */   public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
/* 188 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no default no-arguments constructor found", new Object[0]);
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
/*     */   public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) throws IOException {
/* 202 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no creator with arguments specified", new Object[0]);
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
/*     */ 
/*     */   
/*     */   public Object createFromObjectWith(DeserializationContext ctxt, SettableBeanProperty[] props, PropertyValueBuffer buffer) throws IOException {
/* 228 */     return createFromObjectWith(ctxt, buffer.getParameters(props));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createUsingDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
/* 236 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no delegate creator specified", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createUsingArrayDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
/* 245 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no array delegate creator specified", new Object[0]);
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
/* 257 */     return _createFromStringFallbacks(ctxt, value);
/*     */   }
/*     */   
/*     */   public Object createFromInt(DeserializationContext ctxt, int value) throws IOException {
/* 261 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no int/Int-argument constructor/factory method to deserialize from Number value (%s)", new Object[] { Integer.valueOf(value) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromLong(DeserializationContext ctxt, long value) throws IOException {
/* 267 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no long/Long-argument constructor/factory method to deserialize from Number value (%s)", new Object[] { Long.valueOf(value) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromDouble(DeserializationContext ctxt, double value) throws IOException {
/* 273 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no double/Double-argument constructor/factory method to deserialize from Number value (%s)", new Object[] { Double.valueOf(value) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createFromBoolean(DeserializationContext ctxt, boolean value) throws IOException {
/* 279 */     return ctxt.handleMissingInstantiator(getValueClass(), this, null, "no boolean/Boolean-argument constructor/factory method to deserialize from boolean value (%s)", new Object[] { Boolean.valueOf(value) });
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
/*     */   public AnnotatedWithParams getDefaultCreator() {
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getDelegateCreator() {
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getArrayDelegateCreator() {
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedWithParams getWithArgsCreator() {
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedParameter getIncompleteParameter() {
/* 337 */     return null;
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
/*     */   protected Object _createFromStringFallbacks(DeserializationContext ctxt, String value) throws IOException {
/* 355 */     if (canCreateFromBoolean()) {
/* 356 */       String str = value.trim();
/* 357 */       if ("true".equals(str)) {
/* 358 */         return createFromBoolean(ctxt, true);
/*     */       }
/* 360 */       if ("false".equals(str)) {
/* 361 */         return createFromBoolean(ctxt, false);
/*     */       }
/*     */     } 
/*     */     
/* 365 */     if (value.length() == 0 && 
/* 366 */       ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
/* 367 */       return null;
/*     */     }
/*     */     
/* 370 */     return ctxt.handleMissingInstantiator(getValueClass(), this, ctxt.getParser(), "no String-argument constructor/factory method to deserialize from String value ('%s')", new Object[] { value });
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
/*     */   public static class Base
/*     */     extends ValueInstantiator
/*     */   {
/*     */     protected final Class<?> _valueType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Base(Class<?> type) {
/* 403 */       this._valueType = type;
/*     */     }
/*     */     
/*     */     public Base(JavaType type) {
/* 407 */       this._valueType = type.getRawClass();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValueTypeDesc() {
/* 412 */       return this._valueType.getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getValueClass() {
/* 417 */       return this._valueType;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Gettable {
/*     */     ValueInstantiator getValueInstantiator();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\ValueInstantiator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */