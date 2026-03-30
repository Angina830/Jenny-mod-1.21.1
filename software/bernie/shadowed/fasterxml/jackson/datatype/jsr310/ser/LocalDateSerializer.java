/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.LocalDate;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
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
/*     */ public class LocalDateSerializer
/*     */   extends JSR310FormattedSerializerBase<LocalDate>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  41 */   public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();
/*     */   
/*     */   protected LocalDateSerializer() {
/*  44 */     super(LocalDate.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected LocalDateSerializer(LocalDateSerializer base, Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
/*  49 */     super(base, useTimestamp, dtf, shape);
/*     */   }
/*     */   
/*     */   public LocalDateSerializer(DateTimeFormatter formatter) {
/*  53 */     super(LocalDate.class, formatter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected LocalDateSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
/*  58 */     return new LocalDateSerializer(this, useTimestamp, dtf, shape);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider provider) throws IOException {
/*  64 */     if (useTimestamp(provider)) {
/*  65 */       if (this._shape == JsonFormat.Shape.NUMBER_INT) {
/*  66 */         generator.writeNumber(date.toEpochDay());
/*     */       } else {
/*  68 */         generator.writeStartArray();
/*  69 */         generator.writeNumber(date.getYear());
/*  70 */         generator.writeNumber(date.getMonthValue());
/*  71 */         generator.writeNumber(date.getDayOfMonth());
/*  72 */         generator.writeEndArray();
/*     */       } 
/*     */     } else {
/*  75 */       String str = (this._formatter == null) ? date.toString() : date.format(this._formatter);
/*  76 */       generator.writeString(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/*  83 */     SerializerProvider provider = visitor.getProvider();
/*  84 */     boolean useTimestamp = (provider != null && useTimestamp(provider));
/*  85 */     if (useTimestamp) {
/*  86 */       _acceptTimestampVisitor(visitor, typeHint);
/*     */     } else {
/*  88 */       JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
/*  89 */       if (v2 != null) {
/*  90 */         v2.format(JsonValueFormat.DATE);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken serializationShape(SerializerProvider provider) {
/*  97 */     if (useTimestamp(provider)) {
/*  98 */       if (this._shape == JsonFormat.Shape.NUMBER_INT) {
/*  99 */         return JsonToken.VALUE_NUMBER_INT;
/*     */       }
/* 101 */       return JsonToken.START_ARRAY;
/*     */     } 
/* 103 */     return JsonToken.VALUE_STRING;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\LocalDateSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */