/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalDateTimeDeserializer
/*     */   extends JSR310DateTimeDeserializerBase<LocalDateTime>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
/*     */   
/*  46 */   public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();
/*     */   
/*     */   private LocalDateTimeDeserializer() {
/*  49 */     this(DEFAULT_FORMATTER);
/*     */   }
/*     */   
/*     */   public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
/*  53 */     super(LocalDateTime.class, formatter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<LocalDateTime> withDateFormat(DateTimeFormatter formatter) {
/*  58 */     return (JsonDeserializer<LocalDateTime>)new LocalDateTimeDeserializer(formatter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*  64 */     if (parser.hasTokenId(6)) {
/*  65 */       String string = parser.getText().trim();
/*  66 */       if (string.length() == 0) {
/*  67 */         return null;
/*     */       }
/*     */       
/*     */       try {
/*  71 */         if (this._formatter == DEFAULT_FORMATTER)
/*     */         {
/*  73 */           if (string.length() > 10 && string.charAt(10) == 'T') {
/*  74 */             if (string.endsWith("Z")) {
/*  75 */               return LocalDateTime.ofInstant(Instant.parse(string), ZoneOffset.UTC);
/*     */             }
/*  77 */             return LocalDateTime.parse(string, DEFAULT_FORMATTER);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*  82 */         return LocalDateTime.parse(string, this._formatter);
/*  83 */       } catch (DateTimeException e) {
/*  84 */         _rethrowDateTimeException(parser, context, e, string);
/*     */       } 
/*     */     } 
/*  87 */     if (parser.isExpectedStartArrayToken()) {
/*  88 */       JsonToken t = parser.nextToken();
/*  89 */       if (t == JsonToken.END_ARRAY) {
/*  90 */         return null;
/*     */       }
/*  92 */       if ((t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT) && context
/*  93 */         .isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
/*  94 */         LocalDateTime parsed = deserialize(parser, context);
/*  95 */         if (parser.nextToken() != JsonToken.END_ARRAY) {
/*  96 */           handleMissingEndArrayForSingle(parser, context);
/*     */         }
/*  98 */         return parsed;
/*     */       } 
/* 100 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/*     */         LocalDateTime result;
/*     */         
/* 103 */         int year = parser.getIntValue();
/* 104 */         int month = parser.nextIntValue(-1);
/* 105 */         int day = parser.nextIntValue(-1);
/* 106 */         int hour = parser.nextIntValue(-1);
/* 107 */         int minute = parser.nextIntValue(-1);
/*     */         
/* 109 */         t = parser.nextToken();
/* 110 */         if (t == JsonToken.END_ARRAY) {
/* 111 */           result = LocalDateTime.of(year, month, day, hour, minute);
/*     */         } else {
/* 113 */           int second = parser.getIntValue();
/* 114 */           t = parser.nextToken();
/* 115 */           if (t == JsonToken.END_ARRAY) {
/* 116 */             result = LocalDateTime.of(year, month, day, hour, minute, second);
/*     */           } else {
/* 118 */             int partialSecond = parser.getIntValue();
/* 119 */             if (partialSecond < 1000 && 
/* 120 */               !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS))
/* 121 */               partialSecond *= 1000000; 
/* 122 */             if (parser.nextToken() != JsonToken.END_ARRAY) {
/* 123 */               throw context.wrongTokenException(parser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
/*     */             }
/*     */             
/* 126 */             result = LocalDateTime.of(year, month, day, hour, minute, second, partialSecond);
/*     */           } 
/*     */         } 
/* 129 */         return result;
/*     */       } 
/* 131 */       context.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", new Object[] { t });
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
/* 136 */       return (LocalDateTime)parser.getEmbeddedObject();
/*     */     }
/* 138 */     throw context.wrongTokenException(parser, handledType(), JsonToken.VALUE_STRING, "Expected array or string.");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\LocalDateTimeDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */