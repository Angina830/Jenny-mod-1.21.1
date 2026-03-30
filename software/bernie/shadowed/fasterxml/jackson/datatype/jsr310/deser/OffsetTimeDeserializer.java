/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.OffsetTime;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
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
/*     */ public class OffsetTimeDeserializer
/*     */   extends JSR310DateTimeDeserializerBase<OffsetTime>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  37 */   public static final OffsetTimeDeserializer INSTANCE = new OffsetTimeDeserializer();
/*     */   
/*     */   private OffsetTimeDeserializer() {
/*  40 */     this(DateTimeFormatter.ISO_OFFSET_TIME);
/*     */   }
/*     */   
/*     */   protected OffsetTimeDeserializer(DateTimeFormatter dtf) {
/*  44 */     super(OffsetTime.class, dtf);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<OffsetTime> withDateFormat(DateTimeFormatter dtf) {
/*  49 */     return (JsonDeserializer<OffsetTime>)new OffsetTimeDeserializer(dtf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OffsetTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*  55 */     if (parser.hasToken(JsonToken.VALUE_STRING)) {
/*  56 */       String string = parser.getText().trim();
/*  57 */       if (string.length() == 0) {
/*  58 */         return null;
/*     */       }
/*     */       try {
/*  61 */         return OffsetTime.parse(string, this._formatter);
/*  62 */       } catch (DateTimeException e) {
/*  63 */         _rethrowDateTimeException(parser, context, e, string);
/*     */       } 
/*     */     } 
/*  66 */     if (!parser.isExpectedStartArrayToken()) {
/*  67 */       if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
/*  68 */         return (OffsetTime)parser.getEmbeddedObject();
/*     */       }
/*  70 */       throw context.wrongTokenException(parser, handledType(), JsonToken.START_ARRAY, "Expected array or string.");
/*     */     } 
/*     */     
/*  73 */     JsonToken t = parser.nextToken();
/*  74 */     if (t != JsonToken.VALUE_NUMBER_INT) {
/*  75 */       if (t == JsonToken.END_ARRAY) {
/*  76 */         return null;
/*     */       }
/*  78 */       if ((t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT) && context
/*  79 */         .isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  80 */         OffsetTime parsed = deserialize(parser, context);
/*  81 */         if (parser.nextToken() != JsonToken.END_ARRAY) {
/*  82 */           handleMissingEndArrayForSingle(parser, context);
/*     */         }
/*  84 */         return parsed;
/*     */       } 
/*  86 */       context.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", new Object[] { t });
/*     */     } 
/*     */ 
/*     */     
/*  90 */     int hour = parser.getIntValue();
/*  91 */     int minute = parser.nextIntValue(-1);
/*  92 */     if (minute == -1) {
/*  93 */       t = parser.getCurrentToken();
/*  94 */       if (t == JsonToken.END_ARRAY) {
/*  95 */         return null;
/*     */       }
/*  97 */       if (t != JsonToken.VALUE_NUMBER_INT) {
/*  98 */         _reportWrongToken(context, JsonToken.VALUE_NUMBER_INT, "minutes");
/*     */       }
/* 100 */       minute = parser.getIntValue();
/*     */     } 
/* 102 */     int partialSecond = 0;
/* 103 */     int second = 0;
/* 104 */     if (parser.nextToken() == JsonToken.VALUE_NUMBER_INT) {
/* 105 */       second = parser.getIntValue();
/* 106 */       if (parser.nextToken() == JsonToken.VALUE_NUMBER_INT) {
/* 107 */         partialSecond = parser.getIntValue();
/* 108 */         if (partialSecond < 1000 && 
/* 109 */           !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 110 */           partialSecond *= 1000000;
/*     */         }
/* 112 */         parser.nextToken();
/*     */       } 
/*     */     } 
/* 115 */     if (parser.getCurrentToken() == JsonToken.VALUE_STRING) {
/* 116 */       OffsetTime result = OffsetTime.of(hour, minute, second, partialSecond, ZoneOffset.of(parser.getText()));
/* 117 */       if (parser.nextToken() != JsonToken.END_ARRAY) {
/* 118 */         _reportWrongToken(context, JsonToken.END_ARRAY, "timezone");
/*     */       }
/* 120 */       return result;
/*     */     } 
/* 122 */     throw context.wrongTokenException(parser, handledType(), JsonToken.VALUE_STRING, "Expected string for TimeZone after numeric values");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\OffsetTimeDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */