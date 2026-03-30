/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Calendar;
/*     */ import java.util.Currency;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.EnumResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class StdKeyDeserializer
/*     */   extends KeyDeserializer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int TYPE_BOOLEAN = 1;
/*     */   public static final int TYPE_BYTE = 2;
/*     */   public static final int TYPE_SHORT = 3;
/*     */   public static final int TYPE_CHAR = 4;
/*     */   public static final int TYPE_INT = 5;
/*     */   public static final int TYPE_LONG = 6;
/*     */   public static final int TYPE_FLOAT = 7;
/*     */   public static final int TYPE_DOUBLE = 8;
/*     */   public static final int TYPE_LOCALE = 9;
/*     */   public static final int TYPE_DATE = 10;
/*     */   public static final int TYPE_CALENDAR = 11;
/*     */   public static final int TYPE_UUID = 12;
/*     */   public static final int TYPE_URI = 13;
/*     */   public static final int TYPE_URL = 14;
/*     */   public static final int TYPE_CLASS = 15;
/*     */   public static final int TYPE_CURRENCY = 16;
/*     */   public static final int TYPE_BYTE_ARRAY = 17;
/*     */   protected final int _kind;
/*     */   protected final Class<?> _keyClass;
/*     */   protected final FromStringDeserializer<?> _deser;
/*     */   
/*     */   protected StdKeyDeserializer(int kind, Class<?> cls) {
/*  61 */     this(kind, cls, null);
/*     */   }
/*     */   
/*     */   protected StdKeyDeserializer(int kind, Class<?> cls, FromStringDeserializer<?> deser) {
/*  65 */     this._kind = kind;
/*  66 */     this._keyClass = cls;
/*  67 */     this._deser = deser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StdKeyDeserializer forType(Class<?> raw) {
/*     */     int kind;
/*  75 */     if (raw == String.class || raw == Object.class || raw == CharSequence.class)
/*  76 */       return StringKD.forType(raw); 
/*  77 */     if (raw == UUID.class)
/*  78 */     { kind = 12; }
/*  79 */     else if (raw == Integer.class)
/*  80 */     { kind = 5; }
/*  81 */     else if (raw == Long.class)
/*  82 */     { kind = 6; }
/*  83 */     else if (raw == Date.class)
/*  84 */     { kind = 10; }
/*  85 */     else if (raw == Calendar.class)
/*  86 */     { kind = 11; }
/*     */     
/*  88 */     else if (raw == Boolean.class)
/*  89 */     { kind = 1; }
/*  90 */     else if (raw == Byte.class)
/*  91 */     { kind = 2; }
/*  92 */     else if (raw == Character.class)
/*  93 */     { kind = 4; }
/*  94 */     else if (raw == Short.class)
/*  95 */     { kind = 3; }
/*  96 */     else if (raw == Float.class)
/*  97 */     { kind = 7; }
/*  98 */     else if (raw == Double.class)
/*  99 */     { kind = 8; }
/* 100 */     else if (raw == URI.class)
/* 101 */     { kind = 13; }
/* 102 */     else if (raw == URL.class)
/* 103 */     { kind = 14; }
/* 104 */     else if (raw == Class.class)
/* 105 */     { kind = 15; }
/* 106 */     else { if (raw == Locale.class) {
/* 107 */         FromStringDeserializer<?> deser = FromStringDeserializer.findDeserializer(Locale.class);
/* 108 */         return new StdKeyDeserializer(9, raw, deser);
/* 109 */       }  if (raw == Currency.class) {
/* 110 */         FromStringDeserializer<?> deser = FromStringDeserializer.findDeserializer(Currency.class);
/* 111 */         return new StdKeyDeserializer(16, raw, deser);
/* 112 */       }  if (raw == byte[].class) {
/* 113 */         kind = 17;
/*     */       } else {
/* 115 */         return null;
/*     */       }  }
/* 117 */      return new StdKeyDeserializer(kind, raw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
/* 124 */     if (key == null) {
/* 125 */       return null;
/*     */     }
/*     */     try {
/* 128 */       Object result = _parse(key, ctxt);
/* 129 */       if (result != null) {
/* 130 */         return result;
/*     */       }
/* 132 */     } catch (Exception re) {
/* 133 */       return ctxt.handleWeirdKey(this._keyClass, key, "not a valid representation, problem: (%s) %s", new Object[] { re.getClass().getName(), re.getMessage() });
/*     */     } 
/*     */     
/* 136 */     if (this._keyClass.isEnum() && ctxt.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 137 */       return null;
/*     */     }
/* 139 */     return ctxt.handleWeirdKey(this._keyClass, key, "not a valid representation", new Object[0]);
/*     */   }
/*     */   public Class<?> getKeyClass() {
/* 142 */     return this._keyClass;
/*     */   }
/*     */   protected Object _parse(String key, DeserializationContext ctxt) throws Exception {
/*     */     int value;
/* 146 */     switch (this._kind) {
/*     */       case 1:
/* 148 */         if ("true".equals(key)) {
/* 149 */           return Boolean.TRUE;
/*     */         }
/* 151 */         if ("false".equals(key)) {
/* 152 */           return Boolean.FALSE;
/*     */         }
/* 154 */         return ctxt.handleWeirdKey(this._keyClass, key, "value not 'true' or 'false'", new Object[0]);
/*     */       
/*     */       case 2:
/* 157 */         value = _parseInt(key);
/*     */         
/* 159 */         if (value < -128 || value > 255) {
/* 160 */           return ctxt.handleWeirdKey(this._keyClass, key, "overflow, value cannot be represented as 8-bit value", new Object[0]);
/*     */         }
/* 162 */         return Byte.valueOf((byte)value);
/*     */ 
/*     */       
/*     */       case 3:
/* 166 */         value = _parseInt(key);
/* 167 */         if (value < -32768 || value > 32767) {
/* 168 */           return ctxt.handleWeirdKey(this._keyClass, key, "overflow, value cannot be represented as 16-bit value", new Object[0]);
/*     */         }
/*     */         
/* 171 */         return Short.valueOf((short)value);
/*     */       
/*     */       case 4:
/* 174 */         if (key.length() == 1) {
/* 175 */           return Character.valueOf(key.charAt(0));
/*     */         }
/* 177 */         return ctxt.handleWeirdKey(this._keyClass, key, "can only convert 1-character Strings", new Object[0]);
/*     */       case 5:
/* 179 */         return Integer.valueOf(_parseInt(key));
/*     */       
/*     */       case 6:
/* 182 */         return Long.valueOf(_parseLong(key));
/*     */ 
/*     */       
/*     */       case 7:
/* 186 */         return Float.valueOf((float)_parseDouble(key));
/*     */       case 8:
/* 188 */         return Double.valueOf(_parseDouble(key));
/*     */       case 9:
/*     */         try {
/* 191 */           return this._deser._deserialize(key, ctxt);
/* 192 */         } catch (IllegalArgumentException e) {
/* 193 */           return _weirdKey(ctxt, key, e);
/*     */         } 
/*     */       case 16:
/*     */         try {
/* 197 */           return this._deser._deserialize(key, ctxt);
/* 198 */         } catch (IllegalArgumentException e) {
/* 199 */           return _weirdKey(ctxt, key, e);
/*     */         } 
/*     */       case 10:
/* 202 */         return ctxt.parseDate(key);
/*     */       case 11:
/* 204 */         return ctxt.constructCalendar(ctxt.parseDate(key));
/*     */       case 12:
/*     */         try {
/* 207 */           return UUID.fromString(key);
/* 208 */         } catch (Exception e) {
/* 209 */           return _weirdKey(ctxt, key, e);
/*     */         } 
/*     */       case 13:
/*     */         try {
/* 213 */           return URI.create(key);
/* 214 */         } catch (Exception e) {
/* 215 */           return _weirdKey(ctxt, key, e);
/*     */         } 
/*     */       case 14:
/*     */         try {
/* 219 */           return new URL(key);
/* 220 */         } catch (MalformedURLException e) {
/* 221 */           return _weirdKey(ctxt, key, e);
/*     */         } 
/*     */       case 15:
/*     */         try {
/* 225 */           return ctxt.findClass(key);
/* 226 */         } catch (Exception e) {
/* 227 */           return ctxt.handleWeirdKey(this._keyClass, key, "unable to parse key as Class", new Object[0]);
/*     */         } 
/*     */       case 17:
/*     */         try {
/* 231 */           return ctxt.getConfig().getBase64Variant().decode(key);
/* 232 */         } catch (IllegalArgumentException e) {
/* 233 */           return _weirdKey(ctxt, key, e);
/*     */         } 
/*     */     } 
/* 236 */     throw new IllegalStateException("Internal error: unknown key type " + this._keyClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _parseInt(String key) throws IllegalArgumentException {
/* 247 */     return Integer.parseInt(key);
/*     */   }
/*     */   
/*     */   protected long _parseLong(String key) throws IllegalArgumentException {
/* 251 */     return Long.parseLong(key);
/*     */   }
/*     */   
/*     */   protected double _parseDouble(String key) throws IllegalArgumentException {
/* 255 */     return NumberInput.parseDouble(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object _weirdKey(DeserializationContext ctxt, String key, Exception e) throws IOException {
/* 260 */     return ctxt.handleWeirdKey(this._keyClass, key, "problem: %s", new Object[] { e.getMessage() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class StringKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/* 273 */     private static final StringKD sString = new StringKD(String.class);
/* 274 */     private static final StringKD sObject = new StringKD(Object.class);
/*     */     private StringKD(Class<?> nominalType) {
/* 276 */       super(-1, nominalType);
/*     */     }
/*     */     
/*     */     public static StringKD forType(Class<?> nominalType) {
/* 280 */       if (nominalType == String.class) {
/* 281 */         return sString;
/*     */       }
/* 283 */       if (nominalType == Object.class) {
/* 284 */         return sObject;
/*     */       }
/* 286 */       return new StringKD(nominalType);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
/* 291 */       return key;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class DelegatingKD
/*     */     extends KeyDeserializer
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final Class<?> _keyClass;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final JsonDeserializer<?> _delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected DelegatingKD(Class<?> cls, JsonDeserializer<?> deser) {
/* 317 */       this._keyClass = cls;
/* 318 */       this._delegate = deser;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
/* 326 */       if (key == null) {
/* 327 */         return null;
/*     */       }
/* 329 */       TokenBuffer tb = new TokenBuffer(ctxt.getParser(), ctxt);
/* 330 */       tb.writeString(key);
/*     */       
/*     */       try {
/* 333 */         JsonParser p = tb.asParser();
/* 334 */         p.nextToken();
/* 335 */         Object result = this._delegate.deserialize(p, ctxt);
/* 336 */         if (result != null) {
/* 337 */           return result;
/*     */         }
/* 339 */         return ctxt.handleWeirdKey(this._keyClass, key, "not a valid representation", new Object[0]);
/* 340 */       } catch (Exception re) {
/* 341 */         return ctxt.handleWeirdKey(this._keyClass, key, "not a valid representation: %s", new Object[] { re.getMessage() });
/*     */       } 
/*     */     }
/*     */     public Class<?> getKeyClass() {
/* 345 */       return this._keyClass;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class EnumKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     protected final EnumResolver _byNameResolver;
/*     */     
/*     */     protected final AnnotatedMethod _factory;
/*     */     
/*     */     protected EnumResolver _byToStringResolver;
/*     */ 
/*     */     
/*     */     protected EnumKD(EnumResolver er, AnnotatedMethod factory) {
/* 366 */       super(-1, er.getEnumClass());
/* 367 */       this._byNameResolver = er;
/* 368 */       this._factory = factory;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object _parse(String key, DeserializationContext ctxt) throws IOException {
/* 374 */       if (this._factory != null) {
/*     */         try {
/* 376 */           return this._factory.call1(key);
/* 377 */         } catch (Exception exception) {
/* 378 */           ClassUtil.unwrapAndThrowAsIAE(exception);
/*     */         } 
/*     */       }
/* 381 */       EnumResolver res = ctxt.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING) ? _getToStringResolver(ctxt) : this._byNameResolver;
/*     */       
/* 383 */       Enum<?> e = res.findEnum(key);
/* 384 */       if (e == null && !ctxt.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 385 */         return ctxt.handleWeirdKey(this._keyClass, key, "not one of values excepted for Enum class: %s", new Object[] { res.getEnumIds() });
/*     */       }
/*     */ 
/*     */       
/* 389 */       return e;
/*     */     }
/*     */ 
/*     */     
/*     */     private EnumResolver _getToStringResolver(DeserializationContext ctxt) {
/* 394 */       EnumResolver res = this._byToStringResolver;
/* 395 */       if (res == null) {
/* 396 */         synchronized (this) {
/* 397 */           res = EnumResolver.constructUnsafeUsingToString(this._byNameResolver.getEnumClass(), ctxt.getAnnotationIntrospector());
/*     */         } 
/*     */       }
/*     */       
/* 401 */       return res;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class StringCtorKeyDeserializer
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected final Constructor<?> _ctor;
/*     */ 
/*     */     
/*     */     public StringCtorKeyDeserializer(Constructor<?> ctor) {
/* 416 */       super(-1, ctor.getDeclaringClass());
/* 417 */       this._ctor = ctor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object _parse(String key, DeserializationContext ctxt) throws Exception {
/* 423 */       return this._ctor.newInstance(new Object[] { key });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class StringFactoryKeyDeserializer
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     final Method _factoryMethod;
/*     */ 
/*     */     
/*     */     public StringFactoryKeyDeserializer(Method fm) {
/* 438 */       super(-1, fm.getDeclaringClass());
/* 439 */       this._factoryMethod = fm;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object _parse(String key, DeserializationContext ctxt) throws Exception {
/* 445 */       return this._factoryMethod.invoke(null, new Object[] { key });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StdKeyDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */