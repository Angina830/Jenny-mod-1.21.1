/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
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
/*     */ public class MergingSettableBeanProperty
/*     */   extends SettableBeanProperty.Delegating
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMember _accessor;
/*     */   
/*     */   protected MergingSettableBeanProperty(SettableBeanProperty delegate, AnnotatedMember accessor) {
/*  41 */     super(delegate);
/*  42 */     this._accessor = accessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected MergingSettableBeanProperty(MergingSettableBeanProperty src, SettableBeanProperty delegate) {
/*  48 */     super(delegate);
/*  49 */     this._accessor = src._accessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MergingSettableBeanProperty construct(SettableBeanProperty delegate, AnnotatedMember accessor) {
/*  55 */     return new MergingSettableBeanProperty(delegate, accessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty withDelegate(SettableBeanProperty d) {
/*  60 */     return (SettableBeanProperty)new MergingSettableBeanProperty(d, this._accessor);
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
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  73 */     Object newValue, oldValue = this._accessor.getValue(instance);
/*     */ 
/*     */ 
/*     */     
/*  77 */     if (oldValue == null) {
/*  78 */       newValue = this.delegate.deserialize(p, ctxt);
/*     */     } else {
/*  80 */       newValue = this.delegate.deserializeWith(p, ctxt, oldValue);
/*     */     } 
/*  82 */     if (newValue != oldValue)
/*     */     {
/*     */       
/*  85 */       this.delegate.set(instance, newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  93 */     Object newValue, oldValue = this._accessor.getValue(instance);
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (oldValue == null) {
/*  98 */       newValue = this.delegate.deserialize(p, ctxt);
/*     */     } else {
/* 100 */       newValue = this.delegate.deserializeWith(p, ctxt, oldValue);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (newValue != oldValue)
/*     */     {
/*     */       
/* 108 */       if (newValue != null) {
/* 109 */         return this.delegate.setAndReturn(instance, newValue);
/*     */       }
/*     */     }
/* 112 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object value) throws IOException {
/* 119 */     if (value != null) {
/* 120 */       this.delegate.set(instance, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/* 128 */     if (value != null) {
/* 129 */       return this.delegate.setAndReturn(instance, value);
/*     */     }
/* 131 */     return instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\MergingSettableBeanProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */