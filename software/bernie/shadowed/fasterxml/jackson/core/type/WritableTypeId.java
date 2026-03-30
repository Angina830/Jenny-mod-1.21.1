/*     */ package software.bernie.shadowed.fasterxml.jackson.core.type;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WritableTypeId
/*     */ {
/*     */   public Object forValue;
/*     */   public Class<?> forValueType;
/*     */   public Object id;
/*     */   public String asProperty;
/*     */   public Inclusion include;
/*     */   public JsonToken valueShape;
/*     */   public boolean wrapperWritten;
/*     */   public Object extra;
/*     */   
/*     */   public enum Inclusion
/*     */   {
/*  40 */     WRAPPER_ARRAY,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     WRAPPER_OBJECT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     METADATA_PROPERTY,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     PAYLOAD_PROPERTY,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     PARENT_PROPERTY;
/*     */     
/*     */     public boolean requiresObjectContext() {
/*  95 */       return (this == METADATA_PROPERTY || this == PAYLOAD_PROPERTY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableTypeId() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableTypeId(Object value, JsonToken valueShape0) {
/* 163 */     this(value, valueShape0, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableTypeId(Object value, Class<?> valueType0, JsonToken valueShape0) {
/* 172 */     this(value, valueShape0, (Object)null);
/* 173 */     this.forValueType = valueType0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableTypeId(Object value, JsonToken valueShape0, Object id0) {
/* 183 */     this.forValue = value;
/* 184 */     this.id = id0;
/* 185 */     this.valueShape = valueShape0;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\type\WritableTypeId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */