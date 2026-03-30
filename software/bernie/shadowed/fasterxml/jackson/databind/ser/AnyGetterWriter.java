/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.MapSerializer;
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
/*     */ public class AnyGetterWriter
/*     */ {
/*     */   protected final BeanProperty _property;
/*     */   protected final AnnotatedMember _accessor;
/*     */   protected JsonSerializer<Object> _serializer;
/*     */   protected MapSerializer _mapSerializer;
/*     */   
/*     */   public AnyGetterWriter(BeanProperty property, AnnotatedMember accessor, JsonSerializer<?> serializer) {
/*  32 */     this._accessor = accessor;
/*  33 */     this._property = property;
/*  34 */     this._serializer = (JsonSerializer)serializer;
/*  35 */     if (serializer instanceof MapSerializer) {
/*  36 */       this._mapSerializer = (MapSerializer)serializer;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fixAccess(SerializationConfig config) {
/*  44 */     this._accessor.fixAccess(config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getAndSerialize(Object bean, JsonGenerator gen, SerializerProvider provider) throws Exception {
/*  51 */     Object value = this._accessor.getValue(bean);
/*  52 */     if (value == null) {
/*     */       return;
/*     */     }
/*  55 */     if (!(value instanceof Map)) {
/*  56 */       provider.reportBadDefinition(this._property.getType(), String.format("Value returned by 'any-getter' %s() not java.util.Map but %s", new Object[] { this._accessor.getName(), value.getClass().getName() }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  61 */     if (this._mapSerializer != null) {
/*  62 */       this._mapSerializer.serializeFields((Map)value, gen, provider);
/*     */       return;
/*     */     } 
/*  65 */     this._serializer.serialize(value, gen, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getAndFilter(Object bean, JsonGenerator gen, SerializerProvider provider, PropertyFilter filter) throws Exception {
/*  75 */     Object value = this._accessor.getValue(bean);
/*  76 */     if (value == null) {
/*     */       return;
/*     */     }
/*  79 */     if (!(value instanceof Map)) {
/*  80 */       provider.reportBadDefinition(this._property.getType(), String.format("Value returned by 'any-getter' (%s()) not java.util.Map but %s", new Object[] { this._accessor.getName(), value.getClass().getName() }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  85 */     if (this._mapSerializer != null) {
/*  86 */       this._mapSerializer.serializeFilteredAnyProperties(provider, gen, bean, (Map)value, filter, null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  91 */     this._serializer.serialize(value, gen, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resolve(SerializerProvider provider) throws JsonMappingException {
/*  99 */     if (this._serializer instanceof ContextualSerializer) {
/* 100 */       JsonSerializer<?> ser = provider.handlePrimaryContextualization(this._serializer, this._property);
/* 101 */       this._serializer = (JsonSerializer)ser;
/* 102 */       if (ser instanceof MapSerializer)
/* 103 */         this._mapSerializer = (MapSerializer)ser; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\AnyGetterWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */