/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
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
/*     */ public abstract class DelegatingDeserializer
/*     */   extends StdDeserializer<Object>
/*     */   implements ContextualDeserializer, ResolvableDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JsonDeserializer<?> _delegatee;
/*     */   
/*     */   public DelegatingDeserializer(JsonDeserializer<?> d) {
/*  37 */     super(d.getClass());
/*  38 */     this._delegatee = d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> paramJsonDeserializer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resolve(DeserializationContext ctxt) throws JsonMappingException {
/*  57 */     if (this._delegatee instanceof ResolvableDeserializer) {
/*  58 */       ((ResolvableDeserializer)this._delegatee).resolve(ctxt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/*  67 */     JavaType vt = ctxt.constructType(this._delegatee.handledType());
/*  68 */     JsonDeserializer<?> del = ctxt.handleSecondaryContextualization(this._delegatee, property, vt);
/*     */     
/*  70 */     if (del == this._delegatee) {
/*  71 */       return this;
/*     */     }
/*  73 */     return newDelegatingInstance(del);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> replaceDelegatee(JsonDeserializer<?> delegatee) {
/*  79 */     if (delegatee == this._delegatee) {
/*  80 */       return this;
/*     */     }
/*  82 */     return newDelegatingInstance(delegatee);
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
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  95 */     return this._delegatee.deserialize(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt, Object intoValue) throws IOException {
/* 104 */     return this._delegatee.deserialize(p, ctxt, intoValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 112 */     return this._delegatee.deserializeWithType(p, ctxt, typeDeserializer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachable() {
/* 122 */     return this._delegatee.isCachable();
/*     */   }
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 126 */     return this._delegatee.supportsUpdate(config);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> getDelegatee() {
/* 131 */     return this._delegatee;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SettableBeanProperty findBackReference(String logicalName) {
/* 137 */     return this._delegatee.findBackReference(logicalName);
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessPattern getNullAccessPattern() {
/* 142 */     return this._delegatee.getNullAccessPattern();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getNullValue(DeserializationContext ctxt) throws JsonMappingException {
/* 147 */     return this._delegatee.getNullValue(ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
/* 152 */     return this._delegatee.getEmptyValue(ctxt);
/*     */   }
/*     */   
/*     */   public Collection<Object> getKnownPropertyNames() {
/* 156 */     return this._delegatee.getKnownPropertyNames();
/*     */   }
/*     */   public ObjectIdReader getObjectIdReader() {
/* 159 */     return this._delegatee.getObjectIdReader();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\DelegatingDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */