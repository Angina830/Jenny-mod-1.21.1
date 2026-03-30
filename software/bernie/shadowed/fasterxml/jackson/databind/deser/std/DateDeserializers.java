/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ import java.io.IOException;
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.StdDateFormat;
/*     */ 
/*     */ public class DateDeserializers {
/*  26 */   private static final HashSet<String> _classNames = new HashSet<>();
/*     */   static {
/*  28 */     Class<?>[] numberTypes = new Class[] { Calendar.class, GregorianCalendar.class, Date.class, Date.class, Timestamp.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     for (Class<?> cls : numberTypes) {
/*  36 */       _classNames.add(cls.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonDeserializer<?> find(Class<?> rawType, String clsName) {
/*  42 */     if (_classNames.contains(clsName)) {
/*     */       
/*  44 */       if (rawType == Calendar.class) {
/*  45 */         return new CalendarDeserializer();
/*     */       }
/*  47 */       if (rawType == Date.class) {
/*  48 */         return DateDeserializer.instance;
/*     */       }
/*  50 */       if (rawType == Date.class) {
/*  51 */         return new SqlDateDeserializer();
/*     */       }
/*  53 */       if (rawType == Timestamp.class) {
/*  54 */         return new TimestampDeserializer();
/*     */       }
/*  56 */       if (rawType == GregorianCalendar.class) {
/*  57 */         return new CalendarDeserializer((Class)GregorianCalendar.class);
/*     */       }
/*     */     } 
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static abstract class DateBasedDeserializer<T>
/*     */     extends StdScalarDeserializer<T>
/*     */     implements ContextualDeserializer
/*     */   {
/*     */     protected final DateFormat _customFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final String _formatString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected DateBasedDeserializer(Class<?> clz) {
/*  85 */       super(clz);
/*  86 */       this._customFormat = null;
/*  87 */       this._formatString = null;
/*     */     }
/*     */ 
/*     */     
/*     */     protected DateBasedDeserializer(DateBasedDeserializer<T> base, DateFormat format, String formatStr) {
/*  92 */       super(base._valueClass);
/*  93 */       this._customFormat = format;
/*  94 */       this._formatString = formatStr;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract DateBasedDeserializer<T> withDateFormat(DateFormat param1DateFormat, String param1String);
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 104 */       JsonFormat.Value format = findFormatOverrides(ctxt, property, handledType());
/*     */ 
/*     */       
/* 107 */       if (format != null) {
/* 108 */         TimeZone tz = format.getTimeZone();
/* 109 */         Boolean lenient = format.getLenient();
/*     */ 
/*     */         
/* 112 */         if (format.hasPattern()) {
/* 113 */           String pattern = format.getPattern();
/* 114 */           Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
/* 115 */           SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
/* 116 */           if (tz == null) {
/* 117 */             tz = ctxt.getTimeZone();
/*     */           }
/* 119 */           df.setTimeZone(tz);
/* 120 */           if (lenient != null) {
/* 121 */             df.setLenient(lenient.booleanValue());
/*     */           }
/* 123 */           return withDateFormat(df, pattern);
/*     */         } 
/*     */         
/* 126 */         if (tz != null) {
/* 127 */           StdDateFormat stdDateFormat; DateFormat dateFormat1, df = ctxt.getConfig().getDateFormat();
/*     */           
/* 129 */           if (df.getClass() == StdDateFormat.class) {
/* 130 */             Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
/* 131 */             StdDateFormat std = (StdDateFormat)df;
/* 132 */             std = std.withTimeZone(tz);
/* 133 */             std = std.withLocale(loc);
/* 134 */             if (lenient != null) {
/* 135 */               std = std.withLenient(lenient);
/*     */             }
/* 137 */             stdDateFormat = std;
/*     */           } else {
/*     */             
/* 140 */             dateFormat1 = (DateFormat)stdDateFormat.clone();
/* 141 */             dateFormat1.setTimeZone(tz);
/* 142 */             if (lenient != null) {
/* 143 */               dateFormat1.setLenient(lenient.booleanValue());
/*     */             }
/*     */           } 
/* 146 */           return withDateFormat(dateFormat1, this._formatString);
/*     */         } 
/*     */         
/* 149 */         if (lenient != null) {
/* 150 */           StdDateFormat stdDateFormat; DateFormat dateFormat1, df = ctxt.getConfig().getDateFormat();
/* 151 */           String pattern = this._formatString;
/*     */           
/* 153 */           if (df.getClass() == StdDateFormat.class) {
/* 154 */             StdDateFormat std = (StdDateFormat)df;
/* 155 */             std = std.withLenient(lenient);
/* 156 */             stdDateFormat = std;
/* 157 */             pattern = std.toPattern();
/*     */           } else {
/*     */             
/* 160 */             dateFormat1 = (DateFormat)stdDateFormat.clone();
/* 161 */             dateFormat1.setLenient(lenient.booleanValue());
/* 162 */             if (dateFormat1 instanceof SimpleDateFormat) {
/* 163 */               ((SimpleDateFormat)dateFormat1).toPattern();
/*     */             }
/*     */           } 
/* 166 */           if (pattern == null) {
/* 167 */             pattern = "[unknown]";
/*     */           }
/* 169 */           return withDateFormat(dateFormat1, pattern);
/*     */         } 
/*     */       } 
/* 172 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Date _parseDate(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 179 */       if (this._customFormat != null && 
/* 180 */         p.hasToken(JsonToken.VALUE_STRING)) {
/* 181 */         String str = p.getText().trim();
/* 182 */         if (str.length() == 0) {
/* 183 */           return (Date)getEmptyValue(ctxt);
/*     */         }
/* 185 */         synchronized (this._customFormat) {
/*     */           
/* 187 */           return this._customFormat.parse(str);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       return super._parseDate(p, ctxt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class CalendarDeserializer
/*     */     extends DateBasedDeserializer<Calendar>
/*     */   {
/*     */     protected final Constructor<Calendar> _defaultCtor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CalendarDeserializer() {
/* 217 */       super(Calendar.class);
/* 218 */       this._defaultCtor = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public CalendarDeserializer(Class<? extends Calendar> cc) {
/* 223 */       super(cc);
/* 224 */       this._defaultCtor = ClassUtil.findConstructor(cc, false);
/*     */     }
/*     */     
/*     */     public CalendarDeserializer(CalendarDeserializer src, DateFormat df, String formatString) {
/* 228 */       super(src, df, formatString);
/* 229 */       this._defaultCtor = src._defaultCtor;
/*     */     }
/*     */ 
/*     */     
/*     */     protected CalendarDeserializer withDateFormat(DateFormat df, String formatString) {
/* 234 */       return new CalendarDeserializer(this, df, formatString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Calendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 240 */       Date d = _parseDate(p, ctxt);
/* 241 */       if (d == null) {
/* 242 */         return null;
/*     */       }
/* 244 */       if (this._defaultCtor == null) {
/* 245 */         return ctxt.constructCalendar(d);
/*     */       }
/*     */       try {
/* 248 */         Calendar c = this._defaultCtor.newInstance(new Object[0]);
/* 249 */         c.setTimeInMillis(d.getTime());
/* 250 */         TimeZone tz = ctxt.getTimeZone();
/* 251 */         if (tz != null) {
/* 252 */           c.setTimeZone(tz);
/*     */         }
/* 254 */         return c;
/* 255 */       } catch (Exception e) {
/* 256 */         return (Calendar)ctxt.handleInstantiationProblem(handledType(), d, e);
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
/*     */   @JacksonStdImpl
/*     */   public static class DateDeserializer
/*     */     extends DateBasedDeserializer<Date>
/*     */   {
/* 271 */     public static final DateDeserializer instance = new DateDeserializer();
/*     */     public DateDeserializer() {
/* 273 */       super(Date.class);
/*     */     } public DateDeserializer(DateDeserializer base, DateFormat df, String formatString) {
/* 275 */       super(base, df, formatString);
/*     */     }
/*     */ 
/*     */     
/*     */     protected DateDeserializer withDateFormat(DateFormat df, String formatString) {
/* 280 */       return new DateDeserializer(this, df, formatString);
/*     */     }
/*     */ 
/*     */     
/*     */     public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 285 */       return _parseDate(p, ctxt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SqlDateDeserializer
/*     */     extends DateBasedDeserializer<Date>
/*     */   {
/*     */     public SqlDateDeserializer() {
/* 296 */       super(Date.class);
/*     */     } public SqlDateDeserializer(SqlDateDeserializer src, DateFormat df, String formatString) {
/* 298 */       super(src, df, formatString);
/*     */     }
/*     */ 
/*     */     
/*     */     protected SqlDateDeserializer withDateFormat(DateFormat df, String formatString) {
/* 303 */       return new SqlDateDeserializer(this, df, formatString);
/*     */     }
/*     */ 
/*     */     
/*     */     public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 308 */       Date d = _parseDate(p, ctxt);
/* 309 */       return (d == null) ? null : new Date(d.getTime());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TimestampDeserializer
/*     */     extends DateBasedDeserializer<Timestamp>
/*     */   {
/*     */     public TimestampDeserializer() {
/* 322 */       super(Timestamp.class);
/*     */     } public TimestampDeserializer(TimestampDeserializer src, DateFormat df, String formatString) {
/* 324 */       super(src, df, formatString);
/*     */     }
/*     */ 
/*     */     
/*     */     protected TimestampDeserializer withDateFormat(DateFormat df, String formatString) {
/* 329 */       return new TimestampDeserializer(this, df, formatString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 335 */       Date d = _parseDate(p, ctxt);
/* 336 */       return (d == null) ? null : new Timestamp(d.getTime());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\DateDeserializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */