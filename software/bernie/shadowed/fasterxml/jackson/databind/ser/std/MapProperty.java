/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapProperty
/*     */   extends PropertyWriter
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  25 */   private static final BeanProperty BOGUS_PROP = (BeanProperty)new BeanProperty.Bogus();
/*     */   
/*     */   protected final TypeSerializer _typeSerializer;
/*     */   
/*     */   protected final BeanProperty _property;
/*     */   
/*     */   protected Object _key;
/*     */   protected Object _value;
/*     */   protected JsonSerializer<Object> _keySerializer;
/*     */   protected JsonSerializer<Object> _valueSerializer;
/*     */   
/*     */   public MapProperty(TypeSerializer typeSer, BeanProperty prop) {
/*  37 */     super((prop == null) ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : prop.getMetadata());
/*  38 */     this._typeSerializer = typeSer;
/*  39 */     this._property = (prop == null) ? BOGUS_PROP : prop;
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
/*     */   public void reset(Object key, Object value, JsonSerializer<Object> keySer, JsonSerializer<Object> valueSer) {
/*  51 */     this._key = key;
/*  52 */     this._value = value;
/*  53 */     this._keySerializer = keySer;
/*  54 */     this._valueSerializer = valueSer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void reset(Object key, JsonSerializer<Object> keySer, JsonSerializer<Object> valueSer) {
/*  61 */     reset(key, this._value, keySer, valueSer);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  66 */     if (this._key instanceof String) {
/*  67 */       return (String)this._key;
/*     */     }
/*  69 */     return String.valueOf(this._key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/*  76 */     return this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object v) {
/*  83 */     this._value = v;
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyName getFullName() {
/*  88 */     return new PropertyName(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/*  93 */     return (A)this._property.getAnnotation(acls);
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends java.lang.annotation.Annotation> A getContextAnnotation(Class<A> acls) {
/*  98 */     return (A)this._property.getContextAnnotation(acls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsField(Object map, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 105 */     this._keySerializer.serialize(this._key, gen, provider);
/* 106 */     if (this._typeSerializer == null) {
/* 107 */       this._valueSerializer.serialize(this._value, gen, provider);
/*     */     } else {
/* 109 */       this._valueSerializer.serializeWithType(this._value, gen, provider, this._typeSerializer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsOmittedField(Object map, JsonGenerator gen, SerializerProvider provider) throws Exception {
/* 117 */     if (!gen.canOmitFields()) {
/* 118 */       gen.writeOmittedField(getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsElement(Object map, JsonGenerator gen, SerializerProvider provider) throws Exception {
/* 126 */     if (this._typeSerializer == null) {
/* 127 */       this._valueSerializer.serialize(this._value, gen, provider);
/*     */     } else {
/* 129 */       this._valueSerializer.serializeWithType(this._value, gen, provider, this._typeSerializer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAsPlaceholder(Object value, JsonGenerator gen, SerializerProvider provider) throws Exception {
/* 137 */     gen.writeNull();
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
/*     */   public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
/* 151 */     this._property.depositSchemaProperty(objectVisitor, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void depositSchemaProperty(ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getType() {
/* 163 */     return this._property.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyName getWrapperName() {
/* 168 */     return this._property.getWrapperName();
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMember getMember() {
/* 173 */     return this._property.getMember();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\MapProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */