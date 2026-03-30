/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
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
/*     */ public class UnwrappingBeanPropertyWriter
/*     */   extends BeanPropertyWriter
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final NameTransformer _nameTransformer;
/*     */   
/*     */   public UnwrappingBeanPropertyWriter(BeanPropertyWriter base, NameTransformer unwrapper) {
/*  43 */     super(base);
/*  44 */     this._nameTransformer = unwrapper;
/*     */   }
/*     */ 
/*     */   
/*     */   protected UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter base, NameTransformer transformer, SerializedString name) {
/*  49 */     super(base, name);
/*  50 */     this._nameTransformer = transformer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UnwrappingBeanPropertyWriter rename(NameTransformer transformer) {
/*  56 */     String oldName = this._name.getValue();
/*  57 */     String newName = transformer.transform(oldName);
/*     */ 
/*     */     
/*  60 */     transformer = NameTransformer.chainedTransformer(transformer, this._nameTransformer);
/*     */     
/*  62 */     return _new(transformer, new SerializedString(newName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnwrappingBeanPropertyWriter _new(NameTransformer transformer, SerializedString newName) {
/*  72 */     return new UnwrappingBeanPropertyWriter(this, transformer, newName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnwrapping() {
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/*  90 */     Object value = get(bean);
/*  91 */     if (value == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  96 */     JsonSerializer<Object> ser = this._serializer;
/*  97 */     if (ser == null) {
/*  98 */       Class<?> cls = value.getClass();
/*  99 */       PropertySerializerMap map = this._dynamicSerializers;
/* 100 */       ser = map.serializerFor(cls);
/* 101 */       if (ser == null) {
/* 102 */         ser = _findAndAddDynamic(map, cls, prov);
/*     */       }
/*     */     } 
/* 105 */     if (this._suppressableValue != null) {
/* 106 */       if (MARKER_FOR_EMPTY == this._suppressableValue) {
/* 107 */         if (ser.isEmpty(prov, value)) {
/*     */           return;
/*     */         }
/* 110 */       } else if (this._suppressableValue.equals(value)) {
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 115 */     if (value == bean && 
/* 116 */       _handleSelfReference(bean, gen, prov, ser)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (!ser.isUnwrappingSerializer()) {
/* 123 */       gen.writeFieldName((SerializableString)this._name);
/*     */     }
/*     */     
/* 126 */     if (this._typeSerializer == null) {
/* 127 */       ser.serialize(value, gen, prov);
/*     */     } else {
/* 129 */       ser.serializeWithType(value, gen, prov, this._typeSerializer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void assignSerializer(JsonSerializer<Object> ser) {
/* 137 */     if (ser != null) {
/* 138 */       NameTransformer t = this._nameTransformer;
/* 139 */       if (ser.isUnwrappingSerializer()) {
/* 140 */         t = NameTransformer.chainedTransformer(t, ((UnwrappingBeanSerializer)ser)._nameTransformer);
/*     */       }
/* 142 */       ser = ser.unwrappingSerializer(t);
/*     */     } 
/* 144 */     super.assignSerializer(ser);
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
/*     */   public void depositSchemaProperty(final JsonObjectFormatVisitor visitor, SerializerProvider provider) throws JsonMappingException {
/* 157 */     JsonSerializer<Object> ser = provider.findValueSerializer(getType(), (BeanProperty)this).unwrappingSerializer(this._nameTransformer);
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (ser.isUnwrappingSerializer()) {
/* 162 */       ser.acceptJsonFormatVisitor((JsonFormatVisitorWrapper)new JsonFormatVisitorWrapper.Base(provider)
/*     */           {
/*     */ 
/*     */             
/*     */             public JsonObjectFormatVisitor expectObjectFormat(JavaType type) throws JsonMappingException
/*     */             {
/* 168 */               return visitor;
/*     */             }
/*     */           }getType());
/*     */     } else {
/* 172 */       super.depositSchemaProperty(visitor, provider);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _depositSchemaProperty(ObjectNode propertiesNode, JsonNode schemaNode) {
/* 180 */     JsonNode props = schemaNode.get("properties");
/* 181 */     if (props != null) {
/* 182 */       Iterator<Map.Entry<String, JsonNode>> it = props.fields();
/* 183 */       while (it.hasNext()) {
/* 184 */         Map.Entry<String, JsonNode> entry = it.next();
/* 185 */         String name = entry.getKey();
/* 186 */         if (this._nameTransformer != null) {
/* 187 */           name = this._nameTransformer.transform(name);
/*     */         }
/* 189 */         propertiesNode.set(name, entry.getValue());
/*     */       } 
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
/*     */   protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
/* 206 */     if (this._nonTrivialBaseType != null) {
/* 207 */       JavaType subtype = provider.constructSpecializedType(this._nonTrivialBaseType, type);
/* 208 */       serializer = provider.findValueSerializer(subtype, (BeanProperty)this);
/*     */     } else {
/* 210 */       serializer = provider.findValueSerializer(type, (BeanProperty)this);
/*     */     } 
/* 212 */     NameTransformer t = this._nameTransformer;
/* 213 */     if (serializer.isUnwrappingSerializer()) {
/* 214 */       t = NameTransformer.chainedTransformer(t, ((UnwrappingBeanSerializer)serializer)._nameTransformer);
/*     */     }
/* 216 */     JsonSerializer<Object> serializer = serializer.unwrappingSerializer(t);
/*     */     
/* 218 */     this._dynamicSerializers = this._dynamicSerializers.newWith(type, serializer);
/* 219 */     return serializer;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\UnwrappingBeanPropertyWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */