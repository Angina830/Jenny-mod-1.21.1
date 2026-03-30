/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.OffsetTime;
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
/*     */ public class OffsetTimeSerializer
/*     */   extends JSR310FormattedSerializerBase<OffsetTime>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();
/*     */   
/*     */   protected OffsetTimeSerializer() {
/*  45 */     super(OffsetTime.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected OffsetTimeSerializer(OffsetTimeSerializer base, Boolean useTimestamp, DateTimeFormatter dtf) {
/*  50 */     super(base, useTimestamp, dtf, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected OffsetTimeSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
/*  55 */     return new OffsetTimeSerializer(this, useTimestamp, dtf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(OffsetTime time, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  61 */     if (useTimestamp(provider)) {
/*  62 */       g.writeStartArray();
/*  63 */       _serializeAsArrayContents(time, g, provider);
/*  64 */       g.writeEndArray();
/*     */     } else {
/*  66 */       String str = (this._formatter == null) ? time.toString() : time.format(this._formatter);
/*  67 */       g.writeString(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(OffsetTime value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*  75 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer
/*  76 */         .typeId(value, serializationShape(provider)));
/*     */     
/*  78 */     if (typeIdDef.valueShape == JsonToken.START_ARRAY) {
/*  79 */       _serializeAsArrayContents(value, g, provider);
/*     */     } else {
/*  81 */       String str = (this._formatter == null) ? value.toString() : value.format(this._formatter);
/*  82 */       g.writeString(str);
/*     */     } 
/*  84 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void _serializeAsArrayContents(OffsetTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  90 */     g.writeNumber(value.getHour());
/*  91 */     g.writeNumber(value.getMinute());
/*  92 */     int secs = value.getSecond();
/*  93 */     int nanos = value.getNano();
/*  94 */     if (secs > 0 || nanos > 0) {
/*  95 */       g.writeNumber(secs);
/*  96 */       if (nanos > 0) {
/*  97 */         if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/*  98 */           g.writeNumber(nanos);
/*     */         } else {
/* 100 */           g.writeNumber(value.get(ChronoField.MILLI_OF_SECOND));
/*     */         } 
/*     */       }
/*     */     } 
/* 104 */     g.writeString(value.getOffset().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 109 */     return useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\OffsetTimeSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */