/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Currency;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Pattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidFormatException;
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
/*     */ public abstract class FromStringDeserializer<T>
/*     */   extends StdScalarDeserializer<T>
/*     */ {
/*     */   public static Class<?>[] types() {
/*  57 */     return new Class[] { File.class, URL.class, URI.class, Class.class, JavaType.class, Currency.class, Pattern.class, Locale.class, Charset.class, TimeZone.class, InetAddress.class, InetSocketAddress.class, StringBuilder.class };
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
/*     */   protected FromStringDeserializer(Class<?> vc) {
/*  81 */     super(vc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Std findDeserializer(Class<?> rawType) {
/*  90 */     int kind = 0;
/*  91 */     if (rawType == File.class) {
/*  92 */       kind = 1;
/*  93 */     } else if (rawType == URL.class) {
/*  94 */       kind = 2;
/*  95 */     } else if (rawType == URI.class) {
/*  96 */       kind = 3;
/*  97 */     } else if (rawType == Class.class) {
/*  98 */       kind = 4;
/*  99 */     } else if (rawType == JavaType.class) {
/* 100 */       kind = 5;
/* 101 */     } else if (rawType == Currency.class) {
/* 102 */       kind = 6;
/* 103 */     } else if (rawType == Pattern.class) {
/* 104 */       kind = 7;
/* 105 */     } else if (rawType == Locale.class) {
/* 106 */       kind = 8;
/* 107 */     } else if (rawType == Charset.class) {
/* 108 */       kind = 9;
/* 109 */     } else if (rawType == TimeZone.class) {
/* 110 */       kind = 10;
/* 111 */     } else if (rawType == InetAddress.class) {
/* 112 */       kind = 11;
/* 113 */     } else if (rawType == InetSocketAddress.class) {
/* 114 */       kind = 12;
/* 115 */     } else if (rawType == StringBuilder.class) {
/* 116 */       kind = 13;
/*     */     } else {
/* 118 */       return null;
/*     */     } 
/* 120 */     return new Std(rawType, kind);
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
/*     */   public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 134 */     String text = p.getValueAsString();
/* 135 */     if (text != null) {
/* 136 */       if (text.length() == 0 || (text = text.trim()).length() == 0)
/*     */       {
/* 138 */         return _deserializeFromEmptyString();
/*     */       }
/* 140 */       Exception cause = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 145 */         return _deserialize(text, ctxt);
/* 146 */       } catch (IllegalArgumentException|java.net.MalformedURLException e) {
/* 147 */         cause = e;
/*     */ 
/*     */         
/* 150 */         String msg = "not a valid textual representation";
/* 151 */         String m2 = cause.getMessage();
/* 152 */         if (m2 != null) {
/* 153 */           msg = msg + ", problem: " + m2;
/*     */         }
/*     */         
/* 156 */         JsonMappingException jsonMappingException = ctxt.weirdStringException(text, this._valueClass, msg);
/* 157 */         jsonMappingException.initCause(cause);
/* 158 */         throw jsonMappingException;
/*     */       } 
/*     */     } 
/* 161 */     JsonToken t = p.getCurrentToken();
/*     */     
/* 163 */     if (t == JsonToken.START_ARRAY) {
/* 164 */       return _deserializeFromArray(p, ctxt);
/*     */     }
/* 166 */     if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
/*     */       
/* 168 */       Object ob = p.getEmbeddedObject();
/* 169 */       if (ob == null) {
/* 170 */         return null;
/*     */       }
/* 172 */       if (this._valueClass.isAssignableFrom(ob.getClass())) {
/* 173 */         return (T)ob;
/*     */       }
/* 175 */       return _deserializeEmbedded(ob, ctxt);
/*     */     } 
/* 177 */     return (T)ctxt.handleUnexpectedToken(this._valueClass, p);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract T _deserialize(String paramString, DeserializationContext paramDeserializationContext) throws IOException;
/*     */   
/*     */   protected T _deserializeEmbedded(Object ob, DeserializationContext ctxt) throws IOException {
/* 184 */     ctxt.reportInputMismatch(this, "Don't know how to convert embedded Object of type %s into %s", new Object[] { ob.getClass().getName(), this._valueClass.getName() });
/*     */ 
/*     */     
/* 187 */     return null;
/*     */   }
/*     */   
/*     */   protected T _deserializeFromEmptyString() throws IOException {
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Std
/*     */     extends FromStringDeserializer<Object>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public static final int STD_FILE = 1;
/*     */     
/*     */     public static final int STD_URL = 2;
/*     */     
/*     */     public static final int STD_URI = 3;
/*     */     
/*     */     public static final int STD_CLASS = 4;
/*     */     
/*     */     public static final int STD_JAVA_TYPE = 5;
/*     */     
/*     */     public static final int STD_CURRENCY = 6;
/*     */     
/*     */     public static final int STD_PATTERN = 7;
/*     */     
/*     */     public static final int STD_LOCALE = 8;
/*     */     
/*     */     public static final int STD_CHARSET = 9;
/*     */     
/*     */     public static final int STD_TIME_ZONE = 10;
/*     */     
/*     */     public static final int STD_INET_ADDRESS = 11;
/*     */     
/*     */     public static final int STD_INET_SOCKET_ADDRESS = 12;
/*     */     public static final int STD_STRING_BUILDER = 13;
/*     */     protected final int _kind;
/*     */     
/*     */     protected Std(Class<?> valueType, int kind) {
/* 227 */       super(valueType);
/* 228 */       this._kind = kind;
/*     */     }
/*     */     
/*     */     protected Object _deserialize(String value, DeserializationContext ctxt) throws IOException {
/*     */       int ix;
/*     */       String first, second;
/* 234 */       switch (this._kind) {
/*     */         case 1:
/* 236 */           return new File(value);
/*     */         case 2:
/* 238 */           return new URL(value);
/*     */         case 3:
/* 240 */           return URI.create(value);
/*     */         case 4:
/*     */           try {
/* 243 */             return ctxt.findClass(value);
/* 244 */           } catch (Exception e) {
/* 245 */             return ctxt.handleInstantiationProblem(this._valueClass, value, ClassUtil.getRootCause(e));
/*     */           } 
/*     */         
/*     */         case 5:
/* 249 */           return ctxt.getTypeFactory().constructFromCanonical(value);
/*     */         
/*     */         case 6:
/* 252 */           return Currency.getInstance(value);
/*     */         
/*     */         case 7:
/* 255 */           return Pattern.compile(value);
/*     */         
/*     */         case 8:
/* 258 */           ix = _firstHyphenOrUnderscore(value);
/* 259 */           if (ix < 0) {
/* 260 */             return new Locale(value);
/*     */           }
/* 262 */           first = value.substring(0, ix);
/* 263 */           value = value.substring(ix + 1);
/* 264 */           ix = _firstHyphenOrUnderscore(value);
/* 265 */           if (ix < 0) {
/* 266 */             return new Locale(first, value);
/*     */           }
/* 268 */           second = value.substring(0, ix);
/* 269 */           return new Locale(first, second, value.substring(ix + 1));
/*     */         
/*     */         case 9:
/* 272 */           return Charset.forName(value);
/*     */         case 10:
/* 274 */           return TimeZone.getTimeZone(value);
/*     */         case 11:
/* 276 */           return InetAddress.getByName(value);
/*     */         case 12:
/* 278 */           if (value.startsWith("[")) {
/*     */ 
/*     */             
/* 281 */             int i = value.lastIndexOf(']');
/* 282 */             if (i == -1) {
/* 283 */               throw new InvalidFormatException(ctxt.getParser(), "Bracketed IPv6 address must contain closing bracket", value, InetSocketAddress.class);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 288 */             int j = value.indexOf(':', i);
/* 289 */             int port = (j > -1) ? Integer.parseInt(value.substring(j + 1)) : 0;
/* 290 */             return new InetSocketAddress(value.substring(0, i + 1), port);
/*     */           } 
/* 292 */           ix = value.indexOf(':');
/* 293 */           if (ix >= 0 && value.indexOf(':', ix + 1) < 0) {
/*     */             
/* 295 */             int port = Integer.parseInt(value.substring(ix + 1));
/* 296 */             return new InetSocketAddress(value.substring(0, ix), port);
/*     */           } 
/*     */           
/* 299 */           return new InetSocketAddress(value, 0);
/*     */         case 13:
/* 301 */           return new StringBuilder(value);
/*     */       } 
/* 303 */       VersionUtil.throwInternal();
/* 304 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object _deserializeFromEmptyString() throws IOException {
/* 310 */       if (this._kind == 3) {
/* 311 */         return URI.create("");
/*     */       }
/*     */       
/* 314 */       if (this._kind == 8) {
/* 315 */         return Locale.ROOT;
/*     */       }
/* 317 */       if (this._kind == 13) {
/* 318 */         return new StringBuilder();
/*     */       }
/* 320 */       return super._deserializeFromEmptyString();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int _firstHyphenOrUnderscore(String str) {
/* 325 */       for (int i = 0, end = str.length(); i < end; i++) {
/* 326 */         char c = str.charAt(i);
/* 327 */         if (c == '_' || c == '-') {
/* 328 */           return i;
/*     */         }
/*     */       } 
/* 331 */       return -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\FromStringDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */