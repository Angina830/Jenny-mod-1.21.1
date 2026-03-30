/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.TimeZone;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ 
/*    */ public class TimeZoneSerializer
/*    */   extends StdScalarSerializer<TimeZone> {
/*    */   public TimeZoneSerializer() {
/* 14 */     super(TimeZone.class);
/*    */   }
/*    */   
/*    */   public void serialize(TimeZone value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 18 */     g.writeString(value.getID());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(TimeZone value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 26 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, TimeZone.class, JsonToken.VALUE_STRING));
/*    */     
/* 28 */     serialize(value, g, provider);
/* 29 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\TimeZoneSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */