/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.Duration;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
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
/*     */ public class DurationSerializer
/*     */   extends JSR310FormattedSerializerBase<Duration>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  46 */   public static final DurationSerializer INSTANCE = new DurationSerializer();
/*     */   
/*     */   private DurationSerializer() {
/*  49 */     super(Duration.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DurationSerializer(DurationSerializer base, Boolean useTimestamp, DateTimeFormatter dtf) {
/*  54 */     super(base, useTimestamp, dtf, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DurationSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
/*  59 */     return new DurationSerializer(this, useTimestamp, dtf);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Duration duration, JsonGenerator generator, SerializerProvider provider) throws IOException {
/*  65 */     if (useTimestamp(provider)) {
/*  66 */       if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/*  67 */         generator.writeNumber(DecimalUtils.toBigDecimal(duration
/*  68 */               .getSeconds(), duration.getNano()));
/*     */       } else {
/*     */         
/*  71 */         generator.writeNumber(duration.toMillis());
/*     */       } 
/*     */     } else {
/*     */       
/*  75 */       generator.writeString(duration.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/*  82 */     JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
/*  83 */     if (v2 != null) {
/*  84 */       v2.numberType(JsonParser.NumberType.LONG);
/*  85 */       SerializerProvider provider = visitor.getProvider();
/*  86 */       if (provider == null || !provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS))
/*     */       {
/*     */         
/*  89 */         v2.format(JsonValueFormat.UTC_MILLISEC);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken serializationShape(SerializerProvider provider) {
/*  96 */     if (useTimestamp(provider)) {
/*  97 */       if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/*  98 */         return JsonToken.VALUE_NUMBER_FLOAT;
/*     */       }
/* 100 */       return JsonToken.VALUE_NUMBER_INT;
/*     */     } 
/* 102 */     return JsonToken.VALUE_STRING;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\DurationSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */