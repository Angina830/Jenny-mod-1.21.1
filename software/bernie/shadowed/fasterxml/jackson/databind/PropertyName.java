/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.InternCache;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
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
/*     */ public class PropertyName
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String _USE_DEFAULT = "";
/*     */   private static final String _NO_NAME = "";
/*  29 */   public static final PropertyName USE_DEFAULT = new PropertyName("", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final PropertyName NO_NAME = new PropertyName(new String(""), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String _simpleName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String _namespace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializableString _encodedSimple;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyName(String simpleName) {
/*  61 */     this(simpleName, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyName(String simpleName, String namespace) {
/*  66 */     this._simpleName = ClassUtil.nonNullString(simpleName);
/*  67 */     this._namespace = namespace;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object readResolve() {
/*  72 */     if (this._namespace == null && (
/*  73 */       this._simpleName == null || "".equals(this._simpleName))) {
/*  74 */       return USE_DEFAULT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PropertyName construct(String simpleName) {
/*  92 */     if (simpleName == null || simpleName.length() == 0) {
/*  93 */       return USE_DEFAULT;
/*     */     }
/*  95 */     return new PropertyName(InternCache.instance.intern(simpleName), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertyName construct(String simpleName, String ns) {
/* 100 */     if (simpleName == null) {
/* 101 */       simpleName = "";
/*     */     }
/* 103 */     if (ns == null && simpleName.length() == 0) {
/* 104 */       return USE_DEFAULT;
/*     */     }
/* 106 */     return new PropertyName(InternCache.instance.intern(simpleName), ns);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyName internSimpleName() {
/* 111 */     if (this._simpleName.length() == 0) {
/* 112 */       return this;
/*     */     }
/* 114 */     String interned = InternCache.instance.intern(this._simpleName);
/* 115 */     if (interned == this._simpleName) {
/* 116 */       return this;
/*     */     }
/* 118 */     return new PropertyName(interned, this._namespace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyName withSimpleName(String simpleName) {
/* 127 */     if (simpleName == null) {
/* 128 */       simpleName = "";
/*     */     }
/* 130 */     if (simpleName.equals(this._simpleName)) {
/* 131 */       return this;
/*     */     }
/* 133 */     return new PropertyName(simpleName, this._namespace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyName withNamespace(String ns) {
/* 141 */     if (ns == null) {
/* 142 */       if (this._namespace == null) {
/* 143 */         return this;
/*     */       }
/* 145 */     } else if (ns.equals(this._namespace)) {
/* 146 */       return this;
/*     */     } 
/* 148 */     return new PropertyName(this._simpleName, ns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSimpleName() {
/* 158 */     return this._simpleName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializableString simpleAsEncoded(MapperConfig<?> config) {
/* 168 */     SerializableString sstr = this._encodedSimple;
/* 169 */     if (sstr == null) {
/* 170 */       if (config == null) {
/* 171 */         SerializedString serializedString = new SerializedString(this._simpleName);
/*     */       } else {
/* 173 */         sstr = config.compileString(this._simpleName);
/*     */       } 
/* 175 */       this._encodedSimple = sstr;
/*     */     } 
/* 177 */     return sstr;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/* 181 */     return this._namespace;
/*     */   }
/*     */   
/*     */   public boolean hasSimpleName() {
/* 185 */     return (this._simpleName.length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSimpleName(String str) {
/* 193 */     return this._simpleName.equals(str);
/*     */   }
/*     */   
/*     */   public boolean hasNamespace() {
/* 197 */     return (this._namespace != null);
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
/*     */   public boolean isEmpty() {
/* 209 */     return (this._namespace == null && this._simpleName.isEmpty());
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
/*     */   public boolean equals(Object o) {
/* 221 */     if (o == this) return true; 
/* 222 */     if (o == null) return false;
/*     */ 
/*     */ 
/*     */     
/* 226 */     if (o.getClass() != getClass()) return false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     PropertyName other = (PropertyName)o;
/* 232 */     if (this._simpleName == null) {
/* 233 */       if (other._simpleName != null) return false; 
/* 234 */     } else if (!this._simpleName.equals(other._simpleName)) {
/* 235 */       return false;
/*     */     } 
/* 237 */     if (this._namespace == null) {
/* 238 */       return (null == other._namespace);
/*     */     }
/* 240 */     return this._namespace.equals(other._namespace);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 245 */     if (this._namespace == null) {
/* 246 */       return this._simpleName.hashCode();
/*     */     }
/* 248 */     return this._namespace.hashCode() ^ this._simpleName.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 253 */     if (this._namespace == null) {
/* 254 */       return this._simpleName;
/*     */     }
/* 256 */     return "{" + this._namespace + "}" + this._simpleName;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\PropertyName.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */