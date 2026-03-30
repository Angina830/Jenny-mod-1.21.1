/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.bootstrap.DOMImplementationRegistry;
/*    */ import org.w3c.dom.ls.DOMImplementationLS;
/*    */ import org.w3c.dom.ls.LSSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StdSerializer;
/*    */ 
/*    */ public class DOMSerializer
/*    */   extends StdSerializer<Node>
/*    */ {
/*    */   protected final DOMImplementationLS _domImpl;
/*    */   
/*    */   public DOMSerializer() {
/* 24 */     super(Node.class);
/*    */     DOMImplementationRegistry registry;
/*    */     try {
/* 27 */       registry = DOMImplementationRegistry.newInstance();
/* 28 */     } catch (Exception e) {
/* 29 */       throw new IllegalStateException("Could not instantiate DOMImplementationRegistry: " + e.getMessage(), e);
/*    */     } 
/* 31 */     this._domImpl = (DOMImplementationLS)registry.getDOMImplementation("LS");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(Node value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
/* 38 */     if (this._domImpl == null) throw new IllegalStateException("Could not find DOM LS"); 
/* 39 */     LSSerializer writer = this._domImpl.createLSSerializer();
/* 40 */     jgen.writeString(writer.writeToString(value));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 46 */     return (JsonNode)createSchemaNode("string", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 51 */     if (visitor != null) visitor.expectAnyFormat(typeHint); 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\DOMSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */