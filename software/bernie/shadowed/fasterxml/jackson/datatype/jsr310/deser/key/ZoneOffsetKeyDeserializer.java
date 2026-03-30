/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.DateTimeException;
/*    */ import java.time.ZoneOffset;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ public class ZoneOffsetKeyDeserializer
/*    */   extends Jsr310KeyDeserializer
/*    */ {
/* 11 */   public static final ZoneOffsetKeyDeserializer INSTANCE = new ZoneOffsetKeyDeserializer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ZoneOffset deserialize(String key, DeserializationContext ctxt) throws IOException {
/*    */     try {
/* 20 */       return ZoneOffset.of(key);
/* 21 */     } catch (DateTimeException e) {
/* 22 */       return _rethrowDateTimeException(ctxt, ZoneOffset.class, e, key);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\key\ZoneOffsetKeyDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */