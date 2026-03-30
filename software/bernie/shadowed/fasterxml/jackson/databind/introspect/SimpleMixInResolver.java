/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ClassKey;
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
/*     */ public class SimpleMixInResolver
/*     */   implements ClassIntrospector.MixInResolver, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final ClassIntrospector.MixInResolver _overrides;
/*     */   protected Map<ClassKey, Class<?>> _localMixIns;
/*     */   
/*     */   public SimpleMixInResolver(ClassIntrospector.MixInResolver overrides) {
/*  36 */     this._overrides = overrides;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SimpleMixInResolver(ClassIntrospector.MixInResolver overrides, Map<ClassKey, Class<?>> mixins) {
/*  41 */     this._overrides = overrides;
/*  42 */     this._localMixIns = mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMixInResolver withOverrides(ClassIntrospector.MixInResolver overrides) {
/*  50 */     return new SimpleMixInResolver(overrides, this._localMixIns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMixInResolver withoutLocalDefinitions() {
/*  58 */     return new SimpleMixInResolver(this._overrides, null);
/*     */   }
/*     */   
/*     */   public void setLocalDefinitions(Map<Class<?>, Class<?>> sourceMixins) {
/*  62 */     if (sourceMixins == null || sourceMixins.isEmpty()) {
/*  63 */       this._localMixIns = null;
/*     */     } else {
/*  65 */       Map<ClassKey, Class<?>> mixIns = new HashMap<>(sourceMixins.size());
/*  66 */       for (Map.Entry<Class<?>, Class<?>> en : sourceMixins.entrySet()) {
/*  67 */         mixIns.put(new ClassKey(en.getKey()), en.getValue());
/*     */       }
/*  69 */       this._localMixIns = mixIns;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addLocalDefinition(Class<?> target, Class<?> mixinSource) {
/*  74 */     if (this._localMixIns == null) {
/*  75 */       this._localMixIns = new HashMap<>();
/*     */     }
/*  77 */     this._localMixIns.put(new ClassKey(target), mixinSource);
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleMixInResolver copy() {
/*  82 */     ClassIntrospector.MixInResolver overrides = (this._overrides == null) ? null : this._overrides.copy();
/*     */     
/*  84 */     Map<ClassKey, Class<?>> mixIns = (this._localMixIns == null) ? null : new HashMap<>(this._localMixIns);
/*     */     
/*  86 */     return new SimpleMixInResolver(overrides, mixIns);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findMixInClassFor(Class<?> cls) {
/*  92 */     Class<?> mixin = (this._overrides == null) ? null : this._overrides.findMixInClassFor(cls);
/*  93 */     if (mixin == null && this._localMixIns != null) {
/*  94 */       mixin = this._localMixIns.get(new ClassKey(cls));
/*     */     }
/*  96 */     return mixin;
/*     */   }
/*     */   
/*     */   public int localSize() {
/* 100 */     return (this._localMixIns == null) ? 0 : this._localMixIns.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\SimpleMixInResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */