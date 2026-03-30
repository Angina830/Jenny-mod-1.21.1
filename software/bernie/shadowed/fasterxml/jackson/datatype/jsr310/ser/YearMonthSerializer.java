/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.time.YearMonth;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class YearMonthSerializer
/*    */   extends JSR310FormattedSerializerBase<YearMonth>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 43 */   public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();
/*    */   
/*    */   private YearMonthSerializer() {
/* 46 */     this((DateTimeFormatter)null);
/*    */   }
/*    */   
/*    */   public YearMonthSerializer(DateTimeFormatter formatter) {
/* 50 */     super(YearMonth.class, formatter);
/*    */   }
/*    */ 
/*    */   
/*    */   private YearMonthSerializer(YearMonthSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
/* 55 */     super(base, useTimestamp, formatter, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected YearMonthSerializer withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
/* 61 */     return new YearMonthSerializer(this, useTimestamp, formatter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(YearMonth yearMonth, JsonGenerator generator, SerializerProvider provider) throws IOException {
/* 68 */     if (useTimestamp(provider)) {
/* 69 */       generator.writeStartArray();
/* 70 */       generator.writeNumber(yearMonth.getYear());
/* 71 */       generator.writeNumber(yearMonth.getMonthValue());
/* 72 */       generator.writeEndArray();
/*    */     } else {
/* 74 */       String str = (this._formatter == null) ? yearMonth.toString() : yearMonth.format(this._formatter);
/* 75 */       generator.writeString(str);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 82 */     SerializerProvider provider = visitor.getProvider();
/* 83 */     boolean useTimestamp = (provider != null && useTimestamp(provider));
/* 84 */     if (useTimestamp) {
/* 85 */       super._acceptTimestampVisitor(visitor, typeHint);
/*    */     } else {
/* 87 */       JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
/* 88 */       if (v2 != null) {
/* 89 */         v2.format(JsonValueFormat.DATE_TIME);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 96 */     return useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\YearMonthSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */