/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class StdKeySerializer
/*    */   extends StdSerializer<Object>
/*    */ {
/*    */   public StdKeySerializer() {
/* 18 */     super(Object.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 23 */     g.writeFieldName(value.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StdKeySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */