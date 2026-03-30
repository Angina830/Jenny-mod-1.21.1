/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*    */ public final class WritableObjectId
/*    */ {
/*    */   public final ObjectIdGenerator<?> generator;
/*    */   public Object id;
/*    */   protected boolean idWritten = false;
/*    */   
/*    */   public WritableObjectId(ObjectIdGenerator<?> generator) {
/* 29 */     this.generator = generator;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean writeAsId(JsonGenerator gen, SerializerProvider provider, ObjectIdWriter w) throws IOException {
/* 34 */     if (this.id != null && (this.idWritten || w.alwaysAsId)) {
/*    */       
/* 36 */       if (gen.canWriteObjectId()) {
/* 37 */         gen.writeObjectRef(String.valueOf(this.id));
/*    */       } else {
/* 39 */         w.serializer.serialize(this.id, gen, provider);
/*    */       } 
/* 41 */       return true;
/*    */     } 
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object generateId(Object forPojo) {
/* 50 */     if (this.id == null) {
/* 51 */       this.id = this.generator.generateId(forPojo);
/*    */     }
/* 53 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeAsField(JsonGenerator gen, SerializerProvider provider, ObjectIdWriter w) throws IOException {
/* 62 */     this.idWritten = true;
/*    */ 
/*    */     
/* 65 */     if (gen.canWriteObjectId()) {
/*    */       
/* 67 */       gen.writeObjectId(String.valueOf(this.id));
/*    */       
/*    */       return;
/*    */     } 
/* 71 */     SerializableString name = w.propertyName;
/* 72 */     if (name != null) {
/* 73 */       gen.writeFieldName(name);
/* 74 */       w.serializer.serialize(this.id, gen, provider);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\WritableObjectId.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */