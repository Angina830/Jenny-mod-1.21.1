/*    */ package software.bernie.shadowed.fasterxml.jackson.core;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.util.Separators;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PrettyPrinter
/*    */ {
/* 31 */   public static final Separators DEFAULT_SEPARATORS = Separators.createDefaultInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
/*    */   
/*    */   void writeRootValueSeparator(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void writeStartObject(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void writeEndObject(JsonGenerator paramJsonGenerator, int paramInt) throws IOException;
/*    */   
/*    */   void writeObjectEntrySeparator(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void writeObjectFieldValueSeparator(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void writeStartArray(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void writeEndArray(JsonGenerator paramJsonGenerator, int paramInt) throws IOException;
/*    */   
/*    */   void writeArrayValueSeparator(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void beforeArrayValues(JsonGenerator paramJsonGenerator) throws IOException;
/*    */   
/*    */   void beforeObjectEntries(JsonGenerator paramJsonGenerator) throws IOException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\PrettyPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */