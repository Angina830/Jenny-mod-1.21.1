/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
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
/*    */ public final class ObjectIdWriter
/*    */ {
/*    */   public final JavaType idType;
/*    */   public final SerializableString propertyName;
/*    */   public final ObjectIdGenerator<?> generator;
/*    */   public final JsonSerializer<Object> serializer;
/*    */   public final boolean alwaysAsId;
/*    */   
/*    */   protected ObjectIdWriter(JavaType t, SerializableString propName, ObjectIdGenerator<?> gen, JsonSerializer<?> ser, boolean alwaysAsId) {
/* 53 */     this.idType = t;
/* 54 */     this.propertyName = propName;
/* 55 */     this.generator = gen;
/* 56 */     this.serializer = (JsonSerializer)ser;
/* 57 */     this.alwaysAsId = alwaysAsId;
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
/*    */   public static ObjectIdWriter construct(JavaType idType, PropertyName propName, ObjectIdGenerator<?> generator, boolean alwaysAsId) {
/* 70 */     String simpleName = (propName == null) ? null : propName.getSimpleName();
/* 71 */     SerializedString serializedString = (simpleName == null) ? null : new SerializedString(simpleName);
/* 72 */     return new ObjectIdWriter(idType, (SerializableString)serializedString, generator, null, alwaysAsId);
/*    */   }
/*    */   
/*    */   public ObjectIdWriter withSerializer(JsonSerializer<?> ser) {
/* 76 */     return new ObjectIdWriter(this.idType, this.propertyName, this.generator, ser, this.alwaysAsId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectIdWriter withAlwaysAsId(boolean newState) {
/* 83 */     if (newState == this.alwaysAsId) {
/* 84 */       return this;
/*    */     }
/* 86 */     return new ObjectIdWriter(this.idType, this.propertyName, this.generator, this.serializer, newState);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\ObjectIdWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */