/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
/*    */ 
/*    */ 
/*    */ public class NioPathDeserializer
/*    */   extends StdScalarDeserializer<Path>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public NioPathDeserializer() {
/* 21 */     super(Path.class);
/*    */   }
/*    */   
/*    */   public Path deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 25 */     if (!p.hasToken(JsonToken.VALUE_STRING)) {
/* 26 */       return (Path)ctxt.handleUnexpectedToken(Path.class, p);
/*    */     }
/* 28 */     String value = p.getText();
/*    */ 
/*    */     
/* 31 */     if (value.indexOf(':') < 0) {
/* 32 */       return Paths.get(value, new String[0]);
/*    */     }
/*    */     try {
/* 35 */       URI uri = new URI(value);
/* 36 */       return Paths.get(uri);
/* 37 */     } catch (URISyntaxException e) {
/* 38 */       return (Path)ctxt.handleInstantiationProblem(handledType(), value, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\NioPathDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */