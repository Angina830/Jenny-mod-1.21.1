/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonValue;
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
/*    */ public enum JsonValueFormat
/*    */ {
/* 16 */   COLOR("color"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   DATE("date"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   DATE_TIME("date-time"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   EMAIL("email"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   HOST_NAME("host-name"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   IP_ADDRESS("ip-address"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   IPV6("ipv6"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   PHONE("phone"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   REGEX("regex"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   STYLE("style"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   TIME("time"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   URI("uri"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 87 */   UTC_MILLISEC("utc-millisec");
/*    */   
/*    */   private final String _desc;
/*    */ 
/*    */   
/*    */   JsonValueFormat(String desc) {
/* 93 */     this._desc = desc;
/*    */   }
/*    */   
/*    */   @JsonValue
/*    */   public String toString() {
/* 98 */     return this._desc;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonValueFormat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */