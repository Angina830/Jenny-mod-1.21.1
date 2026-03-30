/*    */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.format.InputAccessor;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.format.MatchStrength;
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
/*    */ public class MappingJsonFactory
/*    */   extends JsonFactory
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   public MappingJsonFactory() {
/* 24 */     this((ObjectMapper)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingJsonFactory(ObjectMapper mapper) {
/* 29 */     super(mapper);
/* 30 */     if (mapper == null) {
/* 31 */       setCodec(new ObjectMapper(this));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingJsonFactory(JsonFactory src, ObjectMapper mapper) {
/* 37 */     super(src, mapper);
/* 38 */     if (mapper == null) {
/* 39 */       setCodec(new ObjectMapper(this));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final ObjectMapper getCodec() {
/* 48 */     return (ObjectMapper)this._objectCodec;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonFactory copy() {
/* 54 */     _checkInvalidCopy(MappingJsonFactory.class);
/*    */     
/* 56 */     return new MappingJsonFactory(this, null);
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormatName() {
/* 74 */     return "JSON";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MatchStrength hasFormat(InputAccessor acc) throws IOException {
/* 83 */     if (getClass() == MappingJsonFactory.class) {
/* 84 */       return hasJSONFormat(acc);
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\MappingJsonFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */