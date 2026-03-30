/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
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
/*     */ public final class ManagedReferenceProperty
/*     */   extends SettableBeanProperty.Delegating
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final String _referenceName;
/*     */   protected final boolean _isContainer;
/*     */   protected final SettableBeanProperty _backProperty;
/*     */   
/*     */   public ManagedReferenceProperty(SettableBeanProperty forward, String refName, SettableBeanProperty backward, boolean isContainer) {
/*  35 */     super(forward);
/*  36 */     this._referenceName = refName;
/*  37 */     this._backProperty = backward;
/*  38 */     this._isContainer = isContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty withDelegate(SettableBeanProperty d) {
/*  43 */     throw new IllegalStateException("Should never try to reset delegate");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/*  49 */     this.delegate.fixAccess(config);
/*  50 */     this._backProperty.fixAccess(config);
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
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  62 */     set(instance, this.delegate.deserialize(p, ctxt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  68 */     return setAndReturn(instance, deserialize(p, ctxt));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(Object instance, Object value) throws IOException {
/*  73 */     setAndReturn(instance, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/*  82 */     if (value != null) {
/*  83 */       if (this._isContainer) {
/*  84 */         if (value instanceof Object[]) {
/*  85 */           for (Object ob : (Object[])value) {
/*  86 */             if (ob != null) this._backProperty.set(ob, instance); 
/*     */           } 
/*  88 */         } else if (value instanceof java.util.Collection) {
/*  89 */           for (Object ob : value) {
/*  90 */             if (ob != null) this._backProperty.set(ob, instance); 
/*     */           } 
/*  92 */         } else if (value instanceof Map) {
/*  93 */           for (Object ob : ((Map)value).values()) {
/*  94 */             if (ob != null) this._backProperty.set(ob, instance); 
/*     */           } 
/*     */         } else {
/*  97 */           throw new IllegalStateException("Unsupported container type (" + value.getClass().getName() + ") when resolving reference '" + this._referenceName + "'");
/*     */         } 
/*     */       } else {
/*     */         
/* 101 */         this._backProperty.set(value, instance);
/*     */       } 
/*     */     }
/*     */     
/* 105 */     return this.delegate.setAndReturn(instance, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ManagedReferenceProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */