/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.LocalTime;
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
/*     */ public class LocalTimeSerializer
/*     */   extends JSR310FormattedSerializerBase<LocalTime>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();
/*     */   
/*     */   protected LocalTimeSerializer() {
/*  45 */     this((DateTimeFormatter)null);
/*     */   }
/*     */   
/*     */   public LocalTimeSerializer(DateTimeFormatter formatter) {
/*  49 */     super(LocalTime.class, formatter);
/*     */   }
/*     */   
/*     */   protected LocalTimeSerializer(LocalTimeSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
/*  53 */     super(base, useTimestamp, formatter, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JSR310FormattedSerializerBase<LocalTime> withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
/*  58 */     return new LocalTimeSerializer(this, useTimestamp, dtf);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DateTimeFormatter _defaultFormatter() {
/*  63 */     return DateTimeFormatter.ISO_LOCAL_TIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(LocalTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
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
/*     */   public void serializeWithType(LocalTime value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
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
/*     */   private final void _serializeAsArrayContents(LocalTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 105 */     g.writeNumber(value.getHour());
/* 106 */     g.writeNumber(value.getMinute());
/* 107 */     int secs = value.getSecond();
/* 108 */     int nanos = value.getNano();
/* 109 */     if (secs > 0 || nanos > 0) {
/*     */       
/* 111 */       g.writeNumber(secs);
/* 112 */       if (nanos > 0) {
/* 113 */         if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 114 */           g.writeNumber(nanos);
/*     */         } else {
/* 116 */           g.writeNumber(value.get(ChronoField.MILLI_OF_SECOND));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 124 */     return useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\LocalTimeSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */