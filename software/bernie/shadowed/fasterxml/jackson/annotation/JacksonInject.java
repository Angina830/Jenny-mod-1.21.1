/*     */ package software.bernie.shadowed.fasterxml.jackson.annotation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
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
/*     */ @Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ @JacksonAnnotation
/*     */ public @interface JacksonInject
/*     */ {
/*     */   String value() default "";
/*     */   
/*     */   OptBoolean useInput() default OptBoolean.DEFAULT;
/*     */   
/*     */   public static class Value
/*     */     implements JacksonAnnotationValue<JacksonInject>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  62 */     protected static final Value EMPTY = new Value(null, null);
/*     */ 
/*     */     
/*     */     protected final Object _id;
/*     */ 
/*     */     
/*     */     protected final Boolean _useInput;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Value(Object id, Boolean useInput) {
/*  73 */       this._id = id;
/*  74 */       this._useInput = useInput;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<JacksonInject> valueFor() {
/*  79 */       return JacksonInject.class;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Value empty() {
/*  89 */       return EMPTY;
/*     */     }
/*     */     
/*     */     public static Value construct(Object id, Boolean useInput) {
/*  93 */       if ("".equals(id)) {
/*  94 */         id = null;
/*     */       }
/*  96 */       if (_empty(id, useInput)) {
/*  97 */         return EMPTY;
/*     */       }
/*  99 */       return new Value(id, useInput);
/*     */     }
/*     */     
/*     */     public static Value from(JacksonInject src) {
/* 103 */       if (src == null) {
/* 104 */         return EMPTY;
/*     */       }
/* 106 */       return construct(src.value(), src.useInput().asBoolean());
/*     */     }
/*     */     
/*     */     public static Value forId(Object id) {
/* 110 */       return construct(id, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value withId(Object id) {
/* 120 */       if (id == null) {
/* 121 */         if (this._id == null) {
/* 122 */           return this;
/*     */         }
/* 124 */       } else if (id.equals(this._id)) {
/* 125 */         return this;
/*     */       } 
/* 127 */       return new Value(id, this._useInput);
/*     */     }
/*     */     
/*     */     public Value withUseInput(Boolean useInput) {
/* 131 */       if (useInput == null) {
/* 132 */         if (this._useInput == null) {
/* 133 */           return this;
/*     */         }
/* 135 */       } else if (useInput.equals(this._useInput)) {
/* 136 */         return this;
/*     */       } 
/* 138 */       return new Value(this._id, useInput);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getId() {
/* 147 */       return this._id; } public Boolean getUseInput() {
/* 148 */       return this._useInput;
/*     */     }
/*     */     public boolean hasId() {
/* 151 */       return (this._id != null);
/*     */     }
/*     */     
/*     */     public boolean willUseInput(boolean defaultSetting) {
/* 155 */       return (this._useInput == null) ? defaultSetting : this._useInput.booleanValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 166 */       return String.format("JacksonInject.Value(id=%s,useInput=%s)", new Object[] { this._id, this._useInput });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 172 */       int h = 1;
/* 173 */       if (this._id != null) {
/* 174 */         h += this._id.hashCode();
/*     */       }
/* 176 */       if (this._useInput != null) {
/* 177 */         h += this._useInput.hashCode();
/*     */       }
/* 179 */       return h;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 184 */       if (o == this) return true; 
/* 185 */       if (o == null) return false; 
/* 186 */       if (o.getClass() == getClass()) {
/* 187 */         Value other = (Value)o;
/* 188 */         if (OptBoolean.equals(this._useInput, other._useInput)) {
/* 189 */           if (this._id == null) {
/* 190 */             return (other._id == null);
/*     */           }
/* 192 */           return this._id.equals(other._id);
/*     */         } 
/*     */       } 
/* 195 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean _empty(Object id, Boolean useInput) {
/* 205 */       return (id == null && useInput == null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\annotation\JacksonInject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */