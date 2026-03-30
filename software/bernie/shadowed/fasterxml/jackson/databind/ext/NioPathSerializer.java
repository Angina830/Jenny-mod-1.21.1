/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NioPathSerializer
/*    */   extends StdScalarSerializer<Path>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public NioPathSerializer() {
/* 22 */     super(Path.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(Path value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
/* 27 */     gen.writeString(value.toUri().toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(Path value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 37 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, Path.class, JsonToken.VALUE_STRING));
/*    */     
/* 39 */     serialize(value, g, provider);
/* 40 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\NioPathSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */