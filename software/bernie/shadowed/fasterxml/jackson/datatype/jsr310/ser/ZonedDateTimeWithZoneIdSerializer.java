/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.time.ZonedDateTime;
/*    */ import java.time.chrono.ChronoZonedDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ZonedDateTimeWithZoneIdSerializer
/*    */   extends InstantSerializerBase<ZonedDateTime>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   public static final ZonedDateTimeWithZoneIdSerializer INSTANCE = new ZonedDateTimeWithZoneIdSerializer();
/*    */   
/*    */   protected ZonedDateTimeWithZoneIdSerializer() {
/* 22 */     super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(), ChronoZonedDateTime::toEpochSecond, ZonedDateTime::getNano, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ZonedDateTimeWithZoneIdSerializer(ZonedDateTimeWithZoneIdSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
/* 30 */     super(base, useTimestamp, formatter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
/* 37 */     return new ZonedDateTimeWithZoneIdSerializer(this, useTimestamp, formatter);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\ZonedDateTimeWithZoneIdSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */