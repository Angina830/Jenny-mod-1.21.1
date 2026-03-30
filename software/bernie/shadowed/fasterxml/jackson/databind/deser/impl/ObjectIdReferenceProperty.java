/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ 
/*     */ public class ObjectIdReferenceProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final SettableBeanProperty _forward;
/*     */   
/*     */   public ObjectIdReferenceProperty(SettableBeanProperty forward, ObjectIdInfo objectIdInfo) {
/*  23 */     super(forward);
/*  24 */     this._forward = forward;
/*  25 */     this._objectIdInfo = objectIdInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdReferenceProperty(ObjectIdReferenceProperty src, JsonDeserializer<?> deser, NullValueProvider nva) {
/*  31 */     super(src, deser, nva);
/*  32 */     this._forward = src._forward;
/*  33 */     this._objectIdInfo = src._objectIdInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectIdReferenceProperty(ObjectIdReferenceProperty src, PropertyName newName) {
/*  38 */     super(src, newName);
/*  39 */     this._forward = src._forward;
/*  40 */     this._objectIdInfo = src._objectIdInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/*  45 */     return new ObjectIdReferenceProperty(this, newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/*  50 */     if (this._valueDeserializer == deser) {
/*  51 */       return this;
/*     */     }
/*  53 */     return new ObjectIdReferenceProperty(this, deser, this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/*  58 */     return new ObjectIdReferenceProperty(this, this._valueDeserializer, nva);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/*  63 */     if (this._forward != null) {
/*  64 */       this._forward.fixAccess(config);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/*  70 */     return (A)this._forward.getAnnotation(acls);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMember getMember() {
/*  75 */     return this._forward.getMember();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreatorIndex() {
/*  80 */     return this._forward.getCreatorIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  85 */     deserializeSetAndReturn(p, ctxt, instance);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*     */     try {
/*  92 */       return setAndReturn(instance, deserialize(p, ctxt));
/*  93 */     } catch (UnresolvedForwardReference reference) {
/*  94 */       boolean usingIdentityInfo = (this._objectIdInfo != null || this._valueDeserializer.getObjectIdReader() != null);
/*  95 */       if (!usingIdentityInfo) {
/*  96 */         throw JsonMappingException.from(p, "Unresolved forward reference but no identity info.", reference);
/*     */       }
/*  98 */       reference.getRoid().appendReferring(new PropertyReferring(this, reference, this._type.getRawClass(), instance));
/*  99 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object value) throws IOException {
/* 105 */     this._forward.set(instance, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/* 110 */     return this._forward.setAndReturn(instance, value);
/*     */   }
/*     */   
/*     */   public static final class PropertyReferring
/*     */     extends ReadableObjectId.Referring
/*     */   {
/*     */     private final ObjectIdReferenceProperty _parent;
/*     */     public final Object _pojo;
/*     */     
/*     */     public PropertyReferring(ObjectIdReferenceProperty parent, UnresolvedForwardReference ref, Class<?> type, Object ob) {
/* 120 */       super(ref, type);
/* 121 */       this._parent = parent;
/* 122 */       this._pojo = ob;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleResolvedForwardReference(Object id, Object value) throws IOException {
/* 128 */       if (!hasId(id)) {
/* 129 */         throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + id + "] that wasn't previously seen as unresolved.");
/*     */       }
/*     */       
/* 132 */       this._parent.set(this._pojo, value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ObjectIdReferenceProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */