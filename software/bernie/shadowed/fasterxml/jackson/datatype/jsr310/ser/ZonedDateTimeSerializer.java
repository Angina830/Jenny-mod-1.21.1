/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.ZonedDateTime;
/*    */ import java.time.chrono.ChronoZonedDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.time.temporal.Temporal;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ 
/*    */ public class ZonedDateTimeSerializer extends InstantSerializerBase<ZonedDateTime> {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Boolean _writeZoneId;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ZonedDateTimeSerializer() {
/* 27 */     this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
/*    */   }
/*    */   
/*    */   public ZonedDateTimeSerializer(DateTimeFormatter formatter) {
/* 31 */     super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(), ChronoZonedDateTime::toEpochSecond, ZonedDateTime::getNano, formatter);
/*    */ 
/*    */     
/* 34 */     this._writeZoneId = null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ZonedDateTimeSerializer(ZonedDateTimeSerializer base, Boolean useTimestamp, DateTimeFormatter formatter, Boolean writeZoneId) {
/* 39 */     super(base, useTimestamp, formatter);
/* 40 */     this._writeZoneId = writeZoneId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
/* 48 */     return new ZonedDateTimeSerializer(this, useTimestamp, formatter, this._writeZoneId);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId) {
/* 53 */     return new ZonedDateTimeSerializer(this, this._useTimestamp, this._formatter, writeZoneId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(ZonedDateTime value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 60 */     if (!useTimestamp(provider) && 
/* 61 */       shouldWriteWithZoneId(provider)) {
/*    */       
/* 63 */       g.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value));
/*    */       
/*    */       return;
/*    */     } 
/* 67 */     super.serialize(value, g, provider);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldWriteWithZoneId(SerializerProvider ctxt) {
/* 74 */     return (this._writeZoneId != null) ? this._writeZoneId.booleanValue() : ctxt
/* 75 */       .isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 80 */     if (!useTimestamp(provider) && shouldWriteWithZoneId(provider)) {
/* 81 */       return JsonToken.VALUE_STRING;
/*    */     }
/* 83 */     return super.serializationShape(provider);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\ZonedDateTimeSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */