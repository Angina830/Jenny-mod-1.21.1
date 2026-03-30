/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FilteredBeanPropertyWriter
/*     */ {
/*     */   public static BeanPropertyWriter constructViewBased(BeanPropertyWriter base, Class<?>[] viewsToIncludeIn) {
/*  19 */     if (viewsToIncludeIn.length == 1) {
/*  20 */       return new SingleView(base, viewsToIncludeIn[0]);
/*     */     }
/*  22 */     return new MultiView(base, viewsToIncludeIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SingleView
/*     */     extends BeanPropertyWriter
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     protected final BeanPropertyWriter _delegate;
/*     */ 
/*     */     
/*     */     protected final Class<?> _view;
/*     */ 
/*     */ 
/*     */     
/*     */     protected SingleView(BeanPropertyWriter delegate, Class<?> view) {
/*  43 */       super(delegate);
/*  44 */       this._delegate = delegate;
/*  45 */       this._view = view;
/*     */     }
/*     */ 
/*     */     
/*     */     public SingleView rename(NameTransformer transformer) {
/*  50 */       return new SingleView(this._delegate.rename(transformer), this._view);
/*     */     }
/*     */ 
/*     */     
/*     */     public void assignSerializer(JsonSerializer<Object> ser) {
/*  55 */       this._delegate.assignSerializer(ser);
/*     */     }
/*     */ 
/*     */     
/*     */     public void assignNullSerializer(JsonSerializer<Object> nullSer) {
/*  60 */       this._delegate.assignNullSerializer(nullSer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/*  67 */       Class<?> activeView = prov.getActiveView();
/*  68 */       if (activeView == null || this._view.isAssignableFrom(activeView)) {
/*  69 */         this._delegate.serializeAsField(bean, gen, prov);
/*     */       } else {
/*  71 */         this._delegate.serializeAsOmittedField(bean, gen, prov);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeAsElement(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/*  79 */       Class<?> activeView = prov.getActiveView();
/*  80 */       if (activeView == null || this._view.isAssignableFrom(activeView)) {
/*  81 */         this._delegate.serializeAsElement(bean, gen, prov);
/*     */       } else {
/*  83 */         this._delegate.serializeAsPlaceholder(bean, gen, prov);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void depositSchemaProperty(JsonObjectFormatVisitor v, SerializerProvider provider) throws JsonMappingException {
/*  91 */       Class<?> activeView = provider.getActiveView();
/*  92 */       if (activeView == null || this._view.isAssignableFrom(activeView)) {
/*  93 */         super.depositSchemaProperty(v, provider);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class MultiView
/*     */     extends BeanPropertyWriter
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected final BeanPropertyWriter _delegate;
/*     */     protected final Class<?>[] _views;
/*     */     
/*     */     protected MultiView(BeanPropertyWriter delegate, Class<?>[] views) {
/* 109 */       super(delegate);
/* 110 */       this._delegate = delegate;
/* 111 */       this._views = views;
/*     */     }
/*     */ 
/*     */     
/*     */     public MultiView rename(NameTransformer transformer) {
/* 116 */       return new MultiView(this._delegate.rename(transformer), this._views);
/*     */     }
/*     */ 
/*     */     
/*     */     public void assignSerializer(JsonSerializer<Object> ser) {
/* 121 */       this._delegate.assignSerializer(ser);
/*     */     }
/*     */ 
/*     */     
/*     */     public void assignNullSerializer(JsonSerializer<Object> nullSer) {
/* 126 */       this._delegate.assignNullSerializer(nullSer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/* 133 */       if (_inView(prov.getActiveView())) {
/* 134 */         this._delegate.serializeAsField(bean, gen, prov);
/*     */         return;
/*     */       } 
/* 137 */       this._delegate.serializeAsOmittedField(bean, gen, prov);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeAsElement(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
/* 144 */       if (_inView(prov.getActiveView())) {
/* 145 */         this._delegate.serializeAsElement(bean, gen, prov);
/*     */         return;
/*     */       } 
/* 148 */       this._delegate.serializeAsPlaceholder(bean, gen, prov);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void depositSchemaProperty(JsonObjectFormatVisitor v, SerializerProvider provider) throws JsonMappingException {
/* 155 */       if (_inView(provider.getActiveView())) {
/* 156 */         super.depositSchemaProperty(v, provider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private final boolean _inView(Class<?> activeView) {
/* 162 */       if (activeView == null) {
/* 163 */         return true;
/*     */       }
/* 165 */       int len = this._views.length;
/* 166 */       for (int i = 0; i < len; i++) {
/* 167 */         if (this._views[i].isAssignableFrom(activeView)) {
/* 168 */           return true;
/*     */         }
/*     */       } 
/* 171 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\FilteredBeanPropertyWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */