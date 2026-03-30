/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonCreator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonValue;
/*    */ 
/*    */ public enum JsonFormatTypes
/*    */ {
/* 10 */   STRING,
/* 11 */   NUMBER,
/* 12 */   INTEGER,
/* 13 */   BOOLEAN,
/* 14 */   OBJECT,
/* 15 */   ARRAY,
/* 16 */   NULL,
/* 17 */   ANY;
/*    */   static {
/* 19 */     _byLCName = new HashMap<>();
/*    */     
/* 21 */     for (JsonFormatTypes t : values())
/* 22 */       _byLCName.put(t.name().toLowerCase(), t); 
/*    */   }
/*    */   private static final Map<String, JsonFormatTypes> _byLCName;
/*    */   
/*    */   @JsonValue
/*    */   public String value() {
/* 28 */     return name().toLowerCase();
/*    */   }
/*    */   
/*    */   @JsonCreator
/*    */   public static JsonFormatTypes forValue(String s) {
/* 33 */     return _byLCName.get(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonFormatTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */