/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
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
/*     */ public class LocalDateDeserializer
/*     */   extends JSR310DateTimeDeserializerBase<LocalDate>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
/*     */   
/*  44 */   public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();
/*     */   
/*     */   private LocalDateDeserializer() {
/*  47 */     this(DEFAULT_FORMATTER);
/*     */   }
/*     */   
/*     */   public LocalDateDeserializer(DateTimeFormatter dtf) {
/*  51 */     super(LocalDate.class, dtf);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonDeserializer<LocalDate> withDateFormat(DateTimeFormatter dtf) {
/*  56 */     return (JsonDeserializer<LocalDate>)new LocalDateDeserializer(dtf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*  62 */     if (parser.hasToken(JsonToken.VALUE_STRING)) {
/*  63 */       String string = parser.getText().trim();
/*  64 */       if (string.length() == 0) {
/*  65 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  69 */       DateTimeFormatter format = this._formatter;
/*     */       try {
/*  71 */         if (format == DEFAULT_FORMATTER)
/*     */         {
/*  73 */           if (string.length() > 10 && string.charAt(10) == 'T') {
/*  74 */             if (string.endsWith("Z")) {
/*  75 */               return LocalDateTime.ofInstant(Instant.parse(string), ZoneOffset.UTC).toLocalDate();
/*     */             }
/*  77 */             return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
/*     */           } 
/*     */         }
/*     */         
/*  81 */         return LocalDate.parse(string, format);
/*  82 */       } catch (DateTimeException e) {
/*  83 */         _rethrowDateTimeException(parser, context, e, string);
/*     */       } 
/*     */     } 
/*  86 */     if (parser.isExpectedStartArrayToken()) {
/*  87 */       JsonToken t = parser.nextToken();
/*  88 */       if (t == JsonToken.END_ARRAY) {
/*  89 */         return null;
/*     */       }
/*  91 */       if (context.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) && (t == JsonToken.VALUE_STRING || t == JsonToken.VALUE_EMBEDDED_OBJECT)) {
/*     */         
/*  93 */         LocalDate parsed = deserialize(parser, context);
/*  94 */         if (parser.nextToken() != JsonToken.END_ARRAY) {
/*  95 */           handleMissingEndArrayForSingle(parser, context);
/*     */         }
/*  97 */         return parsed;
/*     */       } 
/*  99 */       if (t == JsonToken.VALUE_NUMBER_INT) {
/* 100 */         int year = parser.getIntValue();
/* 101 */         int month = parser.nextIntValue(-1);
/* 102 */         int day = parser.nextIntValue(-1);
/*     */         
/* 104 */         if (parser.nextToken() != JsonToken.END_ARRAY) {
/* 105 */           throw context.wrongTokenException(parser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
/*     */         }
/*     */         
/* 108 */         return LocalDate.of(year, month, day);
/*     */       } 
/* 110 */       context.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", new Object[] { t });
/*     */     } 
/*     */ 
/*     */     
/* 114 */     if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
/* 115 */       return (LocalDate)parser.getEmbeddedObject();
/*     */     }
/* 117 */     if (parser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/* 118 */       return LocalDate.ofEpochDay(parser.getLongValue());
/*     */     }
/* 120 */     throw context.wrongTokenException(parser, handledType(), JsonToken.VALUE_STRING, "Expected array or string.");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\LocalDateDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */