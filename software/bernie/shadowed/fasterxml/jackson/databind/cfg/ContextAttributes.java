/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class ContextAttributes
/*     */ {
/*     */   public static ContextAttributes getEmpty() {
/*  24 */     return Impl.getEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ContextAttributes withSharedAttribute(Object paramObject1, Object paramObject2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ContextAttributes withSharedAttributes(Map<?, ?> paramMap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ContextAttributes withoutSharedAttribute(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object getAttribute(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ContextAttributes withPerCallAttribute(Object paramObject1, Object paramObject2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Impl
/*     */     extends ContextAttributes
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */     
/*  67 */     protected static final Impl EMPTY = new Impl(Collections.emptyMap());
/*     */     
/*  69 */     protected static final Object NULL_SURROGATE = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final Map<?, ?> _shared;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected transient Map<Object, Object> _nonShared;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Impl(Map<?, ?> shared) {
/*  93 */       this._shared = shared;
/*  94 */       this._nonShared = null;
/*     */     }
/*     */     
/*     */     protected Impl(Map<?, ?> shared, Map<Object, Object> nonShared) {
/*  98 */       this._shared = shared;
/*  99 */       this._nonShared = nonShared;
/*     */     }
/*     */     
/*     */     public static ContextAttributes getEmpty() {
/* 103 */       return EMPTY;
/*     */     }
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
/*     */     public ContextAttributes withSharedAttribute(Object key, Object value) {
/*     */       Map<Object, Object> m;
/* 117 */       if (this == EMPTY) {
/* 118 */         m = new HashMap<>(8);
/*     */       } else {
/* 120 */         m = _copy(this._shared);
/*     */       } 
/* 122 */       m.put(key, value);
/* 123 */       return new Impl(m);
/*     */     }
/*     */ 
/*     */     
/*     */     public ContextAttributes withSharedAttributes(Map<?, ?> shared) {
/* 128 */       return new Impl(shared);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ContextAttributes withoutSharedAttribute(Object key) {
/* 135 */       if (this._shared.isEmpty()) {
/* 136 */         return this;
/*     */       }
/* 138 */       if (this._shared.containsKey(key)) {
/* 139 */         if (this._shared.size() == 1) {
/* 140 */           return EMPTY;
/*     */         }
/*     */       } else {
/* 143 */         return this;
/*     */       } 
/*     */       
/* 146 */       Map<Object, Object> m = _copy(this._shared);
/* 147 */       m.remove(key);
/* 148 */       return new Impl(m);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getAttribute(Object key) {
/* 160 */       if (this._nonShared != null) {
/* 161 */         Object ob = this._nonShared.get(key);
/* 162 */         if (ob != null) {
/* 163 */           if (ob == NULL_SURROGATE) {
/* 164 */             return null;
/*     */           }
/* 166 */           return ob;
/*     */         } 
/*     */       } 
/* 169 */       return this._shared.get(key);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ContextAttributes withPerCallAttribute(Object key, Object value) {
/* 176 */       if (value == null)
/*     */       {
/* 178 */         if (this._shared.containsKey(key))
/* 179 */         { value = NULL_SURROGATE; }
/* 180 */         else { if (this._nonShared == null || !this._nonShared.containsKey(key))
/*     */           {
/* 182 */             return this;
/*     */           }
/* 184 */           this._nonShared.remove(key);
/* 185 */           return this; }
/*     */       
/*     */       }
/*     */       
/* 189 */       if (this._nonShared == null) {
/* 190 */         return nonSharedInstance(key, value);
/*     */       }
/* 192 */       this._nonShared.put(key, value);
/* 193 */       return this;
/*     */     }
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
/*     */     protected ContextAttributes nonSharedInstance(Object key, Object value) {
/* 208 */       Map<Object, Object> m = new HashMap<>();
/* 209 */       if (value == null) {
/* 210 */         value = NULL_SURROGATE;
/*     */       }
/* 212 */       m.put(key, value);
/* 213 */       return new Impl(this._shared, m);
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<Object, Object> _copy(Map<?, ?> src) {
/* 218 */       return new HashMap<>(src);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\ContextAttributes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */