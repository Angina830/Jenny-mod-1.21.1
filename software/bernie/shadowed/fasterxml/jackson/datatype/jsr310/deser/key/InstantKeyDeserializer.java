/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.Instant;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ public class InstantKeyDeserializer
/*    */   extends Jsr310KeyDeserializer
/*    */ {
/* 12 */   public static final InstantKeyDeserializer INSTANCE = new InstantKeyDeserializer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Instant deserialize(String key, DeserializationContext ctxt) throws IOException {
/*    */     try {
/* 21 */       return DateTimeFormatter.ISO_INSTANT.<Instant>parse(key, Instant::from);
/* 22 */     } catch (DateTimeException e) {
/* 23 */       return _rethrowDateTimeException(ctxt, Instant.class, e, key);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\InstantKeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */