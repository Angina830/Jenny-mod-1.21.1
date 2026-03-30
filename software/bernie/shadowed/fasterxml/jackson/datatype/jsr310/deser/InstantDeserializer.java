/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.OffsetDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.Pattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.DecimalUtils;
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
/*     */ public class InstantDeserializer<T extends Temporal>
/*     */   extends JSR310DateTimeDeserializerBase<T>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  60 */   private static final Pattern ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX = Pattern.compile("\\+00:?(00)?$");
/*     */   static {
/*  62 */     INSTANT = new InstantDeserializer((Class)Instant.class, DateTimeFormatter.ISO_INSTANT, Instant::from, a -> Instant.ofEpochMilli(a.value), a -> Instant.ofEpochSecond(a.integer, a.fraction), null, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     OFFSET_DATE_TIME = new InstantDeserializer((Class)OffsetDateTime.class, DateTimeFormatter.ISO_OFFSET_DATE_TIME, OffsetDateTime::from, a -> OffsetDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId), a -> OffsetDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction), a.zoneId), (d, z) -> d.withOffsetSameInstant(z.getRules().getOffset(d.toLocalDateTime())), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     ZONED_DATE_TIME = new InstantDeserializer((Class)ZonedDateTime.class, DateTimeFormatter.ISO_ZONED_DATE_TIME, ZonedDateTime::from, a -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId), a -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction), a.zoneId), ZonedDateTime::withZoneSameInstant, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final InstantDeserializer<Instant> INSTANT;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final InstantDeserializer<OffsetDateTime> OFFSET_DATE_TIME;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final InstantDeserializer<ZonedDateTime> ZONED_DATE_TIME;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Function<FromIntegerArguments, T> fromMilliseconds;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Function<FromDecimalArguments, T> fromNanoseconds;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Function<TemporalAccessor, T> parsedToValue;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BiFunction<T, ZoneId, T> adjust;
/*     */ 
/*     */   
/*     */   protected final boolean replaceZeroOffsetAsZ;
/*     */ 
/*     */   
/*     */   protected final Boolean _adjustToContextTZOverride;
/*     */ 
/*     */ 
/*     */   
/*     */   protected InstantDeserializer(Class<T> supportedType, DateTimeFormatter formatter, Function<TemporalAccessor, T> parsedToValue, Function<FromIntegerArguments, T> fromMilliseconds, Function<FromDecimalArguments, T> fromNanoseconds, BiFunction<T, ZoneId, T> adjust, boolean replaceZeroOffsetAsZ) {
/* 121 */     super(supportedType, formatter);
/* 122 */     this.parsedToValue = parsedToValue;
/* 123 */     this.fromMilliseconds = fromMilliseconds;
/* 124 */     this.fromNanoseconds = fromNanoseconds;
/* 125 */     this.adjust = (adjust == null) ? ((d, z) -> d) : adjust;
/* 126 */     this.replaceZeroOffsetAsZ = replaceZeroOffsetAsZ;
/* 127 */     this._adjustToContextTZOverride = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected InstantDeserializer(InstantDeserializer<T> base, DateTimeFormatter f) {
/* 133 */     super(base.handledType(), f);
/* 134 */     this.parsedToValue = base.parsedToValue;
/* 135 */     this.fromMilliseconds = base.fromMilliseconds;
/* 136 */     this.fromNanoseconds = base.fromNanoseconds;
/* 137 */     this.adjust = base.adjust;
/* 138 */     this.replaceZeroOffsetAsZ = (this._formatter == DateTimeFormatter.ISO_INSTANT);
/* 139 */     this._adjustToContextTZOverride = base._adjustToContextTZOverride;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected InstantDeserializer(InstantDeserializer<T> base, Boolean adjustToContextTimezoneOverride) {
/* 145 */     super(base.handledType(), base._formatter);
/* 146 */     this.parsedToValue = base.parsedToValue;
/* 147 */     this.fromMilliseconds = base.fromMilliseconds;
/* 148 */     this.fromNanoseconds = base.fromNanoseconds;
/* 149 */     this.adjust = base.adjust;
/* 150 */     this.replaceZeroOffsetAsZ = base.replaceZeroOffsetAsZ;
/* 151 */     this._adjustToContextTZOverride = adjustToContextTimezoneOverride;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<T> withDateFormat(DateTimeFormatter dtf) {
/* 156 */     if (dtf == this._formatter) {
/* 157 */       return (JsonDeserializer<T>)this;
/*     */     }
/* 159 */     return (JsonDeserializer<T>)new InstantDeserializer(this, dtf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*     */     String string;
/*     */     Temporal temporal;
/* 168 */     switch (parser.getCurrentTokenId()) {
/*     */       
/*     */       case 8:
/* 171 */         return _fromDecimal(context, parser.getDecimalValue());
/*     */       
/*     */       case 7:
/* 174 */         return _fromLong(context, parser.getLongValue());
/*     */ 
/*     */       
/*     */       case 6:
/* 178 */         string = parser.getText().trim();
/* 179 */         if (string.length() == 0) {
/* 180 */           return null;
/*     */         }
/*     */         
/* 183 */         if (this._formatter == DateTimeFormatter.ISO_INSTANT || this._formatter == DateTimeFormatter.ISO_OFFSET_DATE_TIME || this._formatter == DateTimeFormatter.ISO_ZONED_DATE_TIME) {
/*     */ 
/*     */ 
/*     */           
/* 187 */           int dots = _countPeriods(string);
/* 188 */           if (dots >= 0) {
/*     */             try {
/* 190 */               if (dots == 0) {
/* 191 */                 return _fromLong(context, Long.parseLong(string));
/*     */               }
/* 193 */               if (dots == 1) {
/* 194 */                 return _fromDecimal(context, new BigDecimal(string));
/*     */               }
/* 196 */             } catch (NumberFormatException numberFormatException) {}
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 201 */           string = replaceZeroOffsetAsZIfNecessary(string);
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 206 */           TemporalAccessor acc = this._formatter.parse(string);
/* 207 */           temporal = (Temporal)this.parsedToValue.apply(acc);
/* 208 */           if (shouldAdjustToContextTimezone(context)) {
/* 209 */             return this.adjust.apply((T)temporal, getZone(context));
/*     */           }
/* 211 */         } catch (DateTimeException e) {
/* 212 */           temporal = (Temporal)_rethrowDateTimeException(parser, context, e, string);
/*     */         } 
/* 214 */         return (T)temporal;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 220 */         return (T)parser.getEmbeddedObject();
/*     */       
/*     */       case 3:
/* 223 */         return (T)_deserializeFromArray(parser, context);
/*     */     } 
/* 225 */     return (T)_reportWrongToken(parser, context, new JsonToken[] { JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<T> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 235 */     InstantDeserializer<T> deserializer = (InstantDeserializer)super.createContextual(ctxt, property);
/* 236 */     if (deserializer != this) {
/* 237 */       JsonFormat.Value val = findFormatOverrides(ctxt, property, handledType());
/* 238 */       if (val != null) {
/* 239 */         return (JsonDeserializer<T>)new InstantDeserializer(deserializer, val.getFeature(JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE));
/*     */       }
/*     */     } 
/* 242 */     return (JsonDeserializer<T>)this;
/*     */   }
/*     */   
/*     */   protected boolean shouldAdjustToContextTimezone(DeserializationContext context) {
/* 246 */     return (this._adjustToContextTZOverride != null) ? this._adjustToContextTZOverride.booleanValue() : context
/* 247 */       .isEnabled(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _countPeriods(String str) {
/* 255 */     int commas = 0;
/* 256 */     for (int i = 0, end = str.length(); i < end; i++) {
/* 257 */       int ch = str.charAt(i);
/* 258 */       if (ch < 48 || ch > 57) {
/* 259 */         if (ch == 46) {
/* 260 */           commas++;
/*     */         } else {
/* 262 */           return -1;
/*     */         } 
/*     */       }
/*     */     } 
/* 266 */     return commas;
/*     */   }
/*     */ 
/*     */   
/*     */   protected T _fromLong(DeserializationContext context, long timestamp) {
/* 271 */     if (context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 272 */       return this.fromNanoseconds.apply(new FromDecimalArguments(timestamp, 0, 
/* 273 */             getZone(context)));
/*     */     }
/*     */     
/* 276 */     return this.fromMilliseconds.apply(new FromIntegerArguments(timestamp, 
/* 277 */           getZone(context)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected T _fromDecimal(DeserializationContext context, BigDecimal value) {
/* 282 */     long seconds = value.longValue();
/* 283 */     int nanoseconds = DecimalUtils.extractNanosecondDecimal(value, seconds);
/* 284 */     return this.fromNanoseconds.apply(new FromDecimalArguments(seconds, nanoseconds, 
/* 285 */           getZone(context)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ZoneId getZone(DeserializationContext context) {
/* 291 */     return (this._valueClass == Instant.class) ? null : context.getTimeZone().toZoneId();
/*     */   }
/*     */ 
/*     */   
/*     */   private String replaceZeroOffsetAsZIfNecessary(String text) {
/* 296 */     if (this.replaceZeroOffsetAsZ) {
/* 297 */       return ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX.matcher(text).replaceFirst("Z");
/*     */     }
/*     */     
/* 300 */     return text;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FromIntegerArguments
/*     */   {
/*     */     public final long value;
/*     */     public final ZoneId zoneId;
/*     */     
/*     */     private FromIntegerArguments(long value, ZoneId zoneId) {
/* 310 */       this.value = value;
/* 311 */       this.zoneId = zoneId;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FromDecimalArguments
/*     */   {
/*     */     public final long integer;
/*     */     public final int fraction;
/*     */     public final ZoneId zoneId;
/*     */     
/*     */     private FromDecimalArguments(long integer, int fraction, ZoneId zoneId) {
/* 323 */       this.integer = integer;
/* 324 */       this.fraction = fraction;
/* 325 */       this.zoneId = zoneId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\InstantDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */