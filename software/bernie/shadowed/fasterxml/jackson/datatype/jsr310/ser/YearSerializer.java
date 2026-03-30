/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.time.Year;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
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
/*    */ public class YearSerializer
/*    */   extends JSR310FormattedSerializerBase<Year>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 43 */   public static final YearSerializer INSTANCE = new YearSerializer();
/*    */   
/*    */   protected YearSerializer() {
/* 46 */     this((DateTimeFormatter)null);
/*    */   }
/*    */   
/*    */   public YearSerializer(DateTimeFormatter formatter) {
/* 50 */     super(Year.class, formatter);
/*    */   }
/*    */   
/*    */   protected YearSerializer(YearSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
/* 54 */     super(base, useTimestamp, formatter, null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected YearSerializer withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
/* 59 */     return new YearSerializer(this, useTimestamp, formatter);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(Year year, JsonGenerator generator, SerializerProvider provider) throws IOException {
/* 65 */     if (useTimestamp(provider)) {
/* 66 */       generator.writeNumber(year.getValue());
/*    */     } else {
/* 68 */       String str = (this._formatter == null) ? year.toString() : year.format(this._formatter);
/* 69 */       generator.writeString(str);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 76 */     JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
/* 77 */     if (v2 != null) {
/* 78 */       v2.numberType(JsonParser.NumberType.LONG);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 84 */     return useTimestamp(provider) ? JsonToken.VALUE_NUMBER_INT : JsonToken.VALUE_STRING;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\YearSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */