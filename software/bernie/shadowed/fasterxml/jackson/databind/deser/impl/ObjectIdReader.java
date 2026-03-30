/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
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
/*     */ public class ObjectIdReader
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _idType;
/*     */   public final PropertyName propertyName;
/*     */   public final ObjectIdGenerator<?> generator;
/*     */   public final ObjectIdResolver resolver;
/*     */   protected final JsonDeserializer<Object> _deserializer;
/*     */   public final SettableBeanProperty idProperty;
/*     */   
/*     */   protected ObjectIdReader(JavaType t, PropertyName propName, ObjectIdGenerator<?> gen, JsonDeserializer<?> deser, SettableBeanProperty idProp, ObjectIdResolver resolver) {
/*  51 */     this._idType = t;
/*  52 */     this.propertyName = propName;
/*  53 */     this.generator = gen;
/*  54 */     this.resolver = resolver;
/*  55 */     this._deserializer = (JsonDeserializer)deser;
/*  56 */     this.idProperty = idProp;
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
/*     */   public static ObjectIdReader construct(JavaType idType, PropertyName propName, ObjectIdGenerator<?> generator, JsonDeserializer<?> deser, SettableBeanProperty idProp, ObjectIdResolver resolver) {
/*  68 */     return new ObjectIdReader(idType, propName, generator, deser, idProp, resolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<Object> getDeserializer() {
/*  78 */     return this._deserializer;
/*     */   }
/*     */   
/*     */   public JavaType getIdType() {
/*  82 */     return this._idType;
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
/*     */   public boolean maySerializeAsObject() {
/*  97 */     return this.generator.maySerializeAsObject();
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
/*     */   public boolean isValidReferencePropertyName(String name, JsonParser parser) {
/* 112 */     return this.generator.isValidReferencePropertyName(name, parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readObjectReference(JsonParser jp, DeserializationContext ctxt) throws IOException {
/* 122 */     return this._deserializer.deserialize(jp, ctxt);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ObjectIdReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */