/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.ChronoField;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*     */ public class LocalDateTimeSerializer
/*     */   extends JSR310FormattedSerializerBase<LocalDateTime>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();
/*     */   
/*     */   protected LocalDateTimeSerializer() {
/*  45 */     this((DateTimeFormatter)null);
/*     */   }
/*     */   
/*     */   public LocalDateTimeSerializer(DateTimeFormatter f) {
/*  49 */     super(LocalDateTime.class, f);
/*     */   }
/*     */   
/*     */   private LocalDateTimeSerializer(LocalDateTimeSerializer base, Boolean useTimestamp, DateTimeFormatter f) {
/*  53 */     super(base, useTimestamp, f, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JSR310FormattedSerializerBase<LocalDateTime> withFormat(Boolean useTimestamp, DateTimeFormatter f, JsonFormat.Shape shape) {
/*  58 */     return new LocalDateTimeSerializer(this, useTimestamp, f);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DateTimeFormatter _defaultFormatter() {
/*  63 */     return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(LocalDateTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  70 */     if (useTimestamp(provider)) {
/*  71 */       g.writeStartArray();
/*  72 */       _serializeAsArrayContents(value, g, provider);
/*  73 */       g.writeEndArray();
/*     */     } else {
/*  75 */       DateTimeFormatter dtf = this._formatter;
/*  76 */       if (dtf == null) {
/*  77 */         dtf = _defaultFormatter();
/*     */       }
/*  79 */       g.writeString(value.format(dtf));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(LocalDateTime value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*  87 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer
/*  88 */         .typeId(value, serializationShape(provider)));
/*     */     
/*  90 */     if (typeIdDef.valueShape == JsonToken.START_ARRAY) {
/*  91 */       _serializeAsArrayContents(value, g, provider);
/*     */     } else {
/*  93 */       DateTimeFormatter dtf = this._formatter;
/*  94 */       if (dtf == null) {
/*  95 */         dtf = _defaultFormatter();
/*     */       }
/*  97 */       g.writeString(value.format(dtf));
/*     */     } 
/*  99 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void _serializeAsArrayContents(LocalDateTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 105 */     g.writeNumber(value.getYear());
/* 106 */     g.writeNumber(value.getMonthValue());
/* 107 */     g.writeNumber(value.getDayOfMonth());
/* 108 */     g.writeNumber(value.getHour());
/* 109 */     g.writeNumber(value.getMinute());
/* 110 */     int secs = value.getSecond();
/* 111 */     int nanos = value.getNano();
/* 112 */     if (secs > 0 || nanos > 0) {
/* 113 */       g.writeNumber(secs);
/* 114 */       if (nanos > 0) {
/* 115 */         if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 116 */           g.writeNumber(nanos);
/*     */         } else {
/* 118 */           g.writeNumber(value.get(ChronoField.MILLI_OF_SECOND));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 126 */     return useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\LocalDateTimeSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */