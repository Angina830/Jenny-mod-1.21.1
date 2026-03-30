/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.LocalTime;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalTimeDeserializer
/*     */   extends JSR310DateTimeDeserializerBase<LocalTime>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  40 */   private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
/*     */   
/*  42 */   public static final LocalTimeDeserializer INSTANCE = new LocalTimeDeserializer();
/*     */   
/*     */   private LocalTimeDeserializer() {
/*  45 */     this(DEFAULT_FORMATTER);
/*     */   }
/*     */   
/*     */   public LocalTimeDeserializer(DateTimeFormatter formatter) {
/*  49 */     super(LocalTime.class, formatter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<LocalTime> withDateFormat(DateTimeFormatter formatter) {
/*  54 */     return (JsonDeserializer<LocalTime>)new LocalTimeDeserializer(formatter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*  60 */     if (parser.hasToken(JsonToken.VALUE_STRING)) {
/*  61 */       String string = parser.getText().trim();
/*  62 */       if (string.length() == 0) {
/*  63 */         return null;
/*     */       }
/*  65 */       DateTimeFormatter format = this._formatter;
/*     */       try {
/*  67 */         if (format == DEFAULT_FORMATTER && 
/*  68 */           string.contains("T")) {
/*  69 */           return LocalTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
/*     */         }
/*     */         
/*  72 */         return LocalTime.parse(string, format);
/*  73 */       } catch (DateTimeException e) {
/*  74 */         _rethrowDateTimeException(parser, context, e, string);
/*     */       } 
/*     */     } 
/*  77 */     if (parser.isExpectedStartArrayToken()) {
/*  78 */       JsonToken t = parser.nextToken();
/*  79 */       if (t == JsonToken.END_ARRAY) {
/*  80 */         return null;
/*     */       }
/*  82 */       if (context.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) && (t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT)) {
/*     */         
/*  84 */         LocalTime parsed = deserialize(parser, context);
/*  85 */         if (parser.nextToken() != JsonToken.END_ARRAY) {
/*  86 */           handleMissingEndArrayForSingle(parser, context);
/*     */         }
/*  88 */         return parsed;
/*     */       } 
/*  90 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/*  91 */         LocalTime result; int hour = parser.getIntValue();
/*     */         
/*  93 */         parser.nextToken();
/*  94 */         int minute = parser.getIntValue();
/*     */ 
/*     */         
/*  97 */         t = parser.nextToken();
/*  98 */         if (t == JsonToken.END_ARRAY) {
/*  99 */           result = LocalTime.of(hour, minute);
/*     */         } else {
/* 101 */           int second = parser.getIntValue();
/* 102 */           t = parser.nextToken();
/* 103 */           if (t == JsonToken.END_ARRAY) {
/* 104 */             result = LocalTime.of(hour, minute, second);
/*     */           } else {
/* 106 */             int partialSecond = parser.getIntValue();
/* 107 */             if (partialSecond < 1000 && 
/* 108 */               !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS))
/* 109 */               partialSecond *= 1000000; 
/* 110 */             t = parser.nextToken();
/* 111 */             if (t != JsonToken.END_ARRAY) {
/* 112 */               throw context.wrongTokenException(parser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
/*     */             }
/*     */             
/* 115 */             result = LocalTime.of(hour, minute, second, partialSecond);
/*     */           } 
/*     */         } 
/* 118 */         return result;
/*     */       } 
/* 120 */       context.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", new Object[] { t });
/*     */     } 
/*     */ 
/*     */     
/* 124 */     if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
/* 125 */       return (LocalTime)parser.getEmbeddedObject();
/*     */     }
/* 127 */     throw context.wrongTokenException(parser, handledType(), JsonToken.START_ARRAY, "Expected array or string.");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\LocalTimeDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */