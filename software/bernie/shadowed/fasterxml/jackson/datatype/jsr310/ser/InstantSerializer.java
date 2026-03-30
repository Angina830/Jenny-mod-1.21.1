/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InstantSerializer
/*    */   extends InstantSerializerBase<Instant>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 35 */   public static final InstantSerializer INSTANCE = new InstantSerializer();
/*    */   
/*    */   protected InstantSerializer() {
/* 38 */     super(Instant.class, Instant::toEpochMilli, Instant::getEpochSecond, Instant::getNano, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected InstantSerializer(InstantSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
/* 45 */     super(base, useTimestamp, formatter);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected JSR310FormattedSerializerBase<Instant> withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
/* 53 */     return new InstantSerializer(this, useTimestamp, formatter);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\InstantSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */