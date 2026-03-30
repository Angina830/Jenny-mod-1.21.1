/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.Date;
/*    */ import java.text.DateFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class SqlDateSerializer
/*    */   extends DateTimeSerializerBase<Date>
/*    */ {
/*    */   public SqlDateSerializer() {
/* 23 */     this((Boolean)null, (DateFormat)null);
/*    */   }
/*    */   
/*    */   protected SqlDateSerializer(Boolean useTimestamp, DateFormat customFormat) {
/* 27 */     super(Date.class, useTimestamp, customFormat);
/*    */   }
/*    */ 
/*    */   
/*    */   public SqlDateSerializer withFormat(Boolean timestamp, DateFormat customFormat) {
/* 32 */     return new SqlDateSerializer(timestamp, customFormat);
/*    */   }
/*    */ 
/*    */   
/*    */   protected long _timestamp(Date value) {
/* 37 */     return (value == null) ? 0L : value.getTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 44 */     if (_asTimestamp(provider)) {
/* 45 */       g.writeNumber(_timestamp(value));
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     if (this._customFormat == null) {
/*    */ 
/*    */ 
/*    */       
/* 53 */       g.writeString(value.toString());
/*    */       return;
/*    */     } 
/* 56 */     _serializeAsString(value, g, provider);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\SqlDateSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */