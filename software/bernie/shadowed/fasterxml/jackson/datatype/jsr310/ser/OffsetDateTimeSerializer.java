/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.time.OffsetDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ 
/*    */ public class OffsetDateTimeSerializer
/*    */   extends InstantSerializerBase<OffsetDateTime>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 11 */   public static final OffsetDateTimeSerializer INSTANCE = new OffsetDateTimeSerializer();
/*    */   
/*    */   protected OffsetDateTimeSerializer() {
/* 14 */     super(OffsetDateTime.class, dt -> dt.toInstant().toEpochMilli(), OffsetDateTime::toEpochSecond, OffsetDateTime::getNano, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected OffsetDateTimeSerializer(OffsetDateTimeSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
/* 21 */     super(base, useTimestamp, formatter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
/* 28 */     return new OffsetDateTimeSerializer(this, useTimestamp, formatter);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\OffsetDateTimeSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */