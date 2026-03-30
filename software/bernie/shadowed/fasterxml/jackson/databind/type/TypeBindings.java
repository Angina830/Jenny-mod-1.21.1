/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ public class TypeBindings implements Serializable {
/*  17 */   private static final String[] NO_STRINGS = new String[0];
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   private static final JavaType[] NO_TYPES = new JavaType[0];
/*     */   
/*  21 */   private static final TypeBindings EMPTY = new TypeBindings(NO_STRINGS, NO_TYPES, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] _names;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JavaType[] _types;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] _unboundVariables;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int _hashCode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TypeBindings(String[] names, JavaType[] types, String[] uvars) {
/*  54 */     this._names = (names == null) ? NO_STRINGS : names;
/*  55 */     this._types = (types == null) ? NO_TYPES : types;
/*  56 */     if (this._names.length != this._types.length) {
/*  57 */       throw new IllegalArgumentException("Mismatching names (" + this._names.length + "), types (" + this._types.length + ")");
/*     */     }
/*  59 */     int h = 1;
/*  60 */     for (int i = 0, len = this._types.length; i < len; i++) {
/*  61 */       h += this._types[i].hashCode();
/*     */     }
/*  63 */     this._unboundVariables = uvars;
/*  64 */     this._hashCode = h;
/*     */   }
/*     */   
/*     */   public static TypeBindings emptyBindings() {
/*  68 */     return EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object readResolve() {
/*  73 */     if (this._names == null || this._names.length == 0) {
/*  74 */       return EMPTY;
/*     */     }
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeBindings create(Class<?> erasedType, List<JavaType> typeList) {
/*  85 */     JavaType[] types = (typeList == null || typeList.isEmpty()) ? NO_TYPES : typeList.<JavaType>toArray(new JavaType[typeList.size()]);
/*     */     
/*  87 */     return create(erasedType, types);
/*     */   }
/*     */   
/*     */   public static TypeBindings create(Class<?> erasedType, JavaType[] types) {
/*     */     String[] names;
/*  92 */     if (types == null)
/*  93 */     { types = NO_TYPES; }
/*  94 */     else { switch (types.length) {
/*     */         case 1:
/*  96 */           return create(erasedType, types[0]);
/*     */         case 2:
/*  98 */           return create(erasedType, types[0], types[1]);
/*     */       }  }
/* 100 */      TypeVariable[] arrayOfTypeVariable = (TypeVariable[])erasedType.getTypeParameters();
/*     */     
/* 102 */     if (arrayOfTypeVariable == null || arrayOfTypeVariable.length == 0) {
/* 103 */       names = NO_STRINGS;
/*     */     } else {
/* 105 */       int len = arrayOfTypeVariable.length;
/* 106 */       names = new String[len];
/* 107 */       for (int i = 0; i < len; i++) {
/* 108 */         names[i] = arrayOfTypeVariable[i].getName();
/*     */       }
/*     */     } 
/*     */     
/* 112 */     if (names.length != types.length) {
/* 113 */       throw new IllegalArgumentException("Cannot create TypeBindings for class " + erasedType.getName() + " with " + types.length + " type parameter" + ((types.length == 1) ? "" : "s") + ": class expects " + names.length);
/*     */     }
/*     */ 
/*     */     
/* 117 */     return new TypeBindings(names, types, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeBindings create(Class<?> erasedType, JavaType typeArg1) {
/* 123 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])TypeParamStash.paramsFor1(erasedType);
/* 124 */     int varLen = (arrayOfTypeVariable == null) ? 0 : arrayOfTypeVariable.length;
/* 125 */     if (varLen != 1) {
/* 126 */       throw new IllegalArgumentException("Cannot create TypeBindings for class " + erasedType.getName() + " with 1 type parameter: class expects " + varLen);
/*     */     }
/*     */     
/* 129 */     return new TypeBindings(new String[] { arrayOfTypeVariable[0].getName() }, new JavaType[] { typeArg1 }, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeBindings create(Class<?> erasedType, JavaType typeArg1, JavaType typeArg2) {
/* 136 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])TypeParamStash.paramsFor2(erasedType);
/* 137 */     int varLen = (arrayOfTypeVariable == null) ? 0 : arrayOfTypeVariable.length;
/* 138 */     if (varLen != 2) {
/* 139 */       throw new IllegalArgumentException("Cannot create TypeBindings for class " + erasedType.getName() + " with 2 type parameters: class expects " + varLen);
/*     */     }
/*     */     
/* 142 */     return new TypeBindings(new String[] { arrayOfTypeVariable[0].getName(), arrayOfTypeVariable[1].getName() }, new JavaType[] { typeArg1, typeArg2 }, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeBindings createIfNeeded(Class<?> erasedType, JavaType typeArg1) {
/* 153 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])erasedType.getTypeParameters();
/* 154 */     int varLen = (arrayOfTypeVariable == null) ? 0 : arrayOfTypeVariable.length;
/* 155 */     if (varLen == 0) {
/* 156 */       return EMPTY;
/*     */     }
/* 158 */     if (varLen != 1) {
/* 159 */       throw new IllegalArgumentException("Cannot create TypeBindings for class " + erasedType.getName() + " with 1 type parameter: class expects " + varLen);
/*     */     }
/*     */     
/* 162 */     return new TypeBindings(new String[] { arrayOfTypeVariable[0].getName() }, new JavaType[] { typeArg1 }, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeBindings createIfNeeded(Class<?> erasedType, JavaType[] types) {
/* 173 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])erasedType.getTypeParameters();
/* 174 */     if (arrayOfTypeVariable == null || arrayOfTypeVariable.length == 0) {
/* 175 */       return EMPTY;
/*     */     }
/* 177 */     if (types == null) {
/* 178 */       types = NO_TYPES;
/*     */     }
/* 180 */     int len = arrayOfTypeVariable.length;
/* 181 */     String[] names = new String[len];
/* 182 */     for (int i = 0; i < len; i++) {
/* 183 */       names[i] = arrayOfTypeVariable[i].getName();
/*     */     }
/*     */     
/* 186 */     if (names.length != types.length) {
/* 187 */       throw new IllegalArgumentException("Cannot create TypeBindings for class " + erasedType.getName() + " with " + types.length + " type parameter" + ((types.length == 1) ? "" : "s") + ": class expects " + names.length);
/*     */     }
/*     */ 
/*     */     
/* 191 */     return new TypeBindings(names, types, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeBindings withUnboundVariable(String name) {
/* 201 */     int len = (this._unboundVariables == null) ? 0 : this._unboundVariables.length;
/* 202 */     String[] names = (len == 0) ? new String[1] : Arrays.<String>copyOf(this._unboundVariables, len + 1);
/*     */     
/* 204 */     names[len] = name;
/* 205 */     return new TypeBindings(this._names, this._types, names);
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
/*     */   
/*     */   public JavaType findBoundType(String name) {
/* 219 */     for (int i = 0, len = this._names.length; i < len; i++) {
/* 220 */       if (name.equals(this._names[i])) {
/* 221 */         JavaType t = this._types[i];
/* 222 */         if (t instanceof ResolvedRecursiveType) {
/* 223 */           ResolvedRecursiveType rrt = (ResolvedRecursiveType)t;
/* 224 */           JavaType t2 = rrt.getSelfReferencedType();
/* 225 */           if (t2 != null) {
/* 226 */             t = t2;
/*     */           }
/*     */         } 
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
/* 239 */         return t;
/*     */       } 
/*     */     } 
/* 242 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 246 */     return (this._types.length == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 253 */     return this._types.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBoundName(int index) {
/* 258 */     if (index < 0 || index >= this._names.length) {
/* 259 */       return null;
/*     */     }
/* 261 */     return this._names[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getBoundType(int index) {
/* 266 */     if (index < 0 || index >= this._types.length) {
/* 267 */       return null;
/*     */     }
/* 269 */     return this._types[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<JavaType> getTypeParameters() {
/* 277 */     if (this._types.length == 0) {
/* 278 */       return Collections.emptyList();
/*     */     }
/* 280 */     return Arrays.asList(this._types);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUnbound(String name) {
/* 287 */     if (this._unboundVariables != null) {
/* 288 */       for (int i = this._unboundVariables.length; --i >= 0;) {
/* 289 */         if (name.equals(this._unboundVariables[i])) {
/* 290 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 294 */     return false;
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
/*     */   public Object asKey(Class<?> rawBase) {
/* 306 */     return new AsKey(rawBase, this._types, this._hashCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 317 */     if (this._types.length == 0) {
/* 318 */       return "<>";
/*     */     }
/* 320 */     StringBuilder sb = new StringBuilder();
/* 321 */     sb.append('<');
/* 322 */     for (int i = 0, len = this._types.length; i < len; i++) {
/* 323 */       if (i > 0) {
/* 324 */         sb.append(',');
/*     */       }
/*     */       
/* 327 */       String sig = this._types[i].getGenericSignature();
/* 328 */       sb.append(sig);
/*     */     } 
/* 330 */     sb.append('>');
/* 331 */     return sb.toString();
/*     */   }
/*     */   public int hashCode() {
/* 334 */     return this._hashCode;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 338 */     if (o == this) return true; 
/* 339 */     if (!ClassUtil.hasClass(o, getClass())) {
/* 340 */       return false;
/*     */     }
/* 342 */     TypeBindings other = (TypeBindings)o;
/* 343 */     int len = this._types.length;
/* 344 */     if (len != other.size()) {
/* 345 */       return false;
/*     */     }
/* 347 */     JavaType[] otherTypes = other._types;
/* 348 */     for (int i = 0; i < len; i++) {
/* 349 */       if (!otherTypes[i].equals(this._types[i])) {
/* 350 */         return false;
/*     */       }
/*     */     } 
/* 353 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType[] typeParameterArray() {
/* 363 */     return this._types;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class TypeParamStash
/*     */   {
/* 384 */     private static final TypeVariable<?>[] VARS_ABSTRACT_LIST = (TypeVariable<?>[])AbstractList.class.getTypeParameters();
/* 385 */     private static final TypeVariable<?>[] VARS_COLLECTION = (TypeVariable<?>[])Collection.class.getTypeParameters();
/* 386 */     private static final TypeVariable<?>[] VARS_ITERABLE = (TypeVariable<?>[])Iterable.class.getTypeParameters();
/* 387 */     private static final TypeVariable<?>[] VARS_LIST = (TypeVariable<?>[])List.class.getTypeParameters();
/* 388 */     private static final TypeVariable<?>[] VARS_ARRAY_LIST = (TypeVariable<?>[])ArrayList.class.getTypeParameters();
/*     */     
/* 390 */     private static final TypeVariable<?>[] VARS_MAP = (TypeVariable<?>[])Map.class.getTypeParameters();
/* 391 */     private static final TypeVariable<?>[] VARS_HASH_MAP = (TypeVariable<?>[])HashMap.class.getTypeParameters();
/* 392 */     private static final TypeVariable<?>[] VARS_LINKED_HASH_MAP = (TypeVariable<?>[])LinkedHashMap.class.getTypeParameters();
/*     */ 
/*     */     
/*     */     public static TypeVariable<?>[] paramsFor1(Class<?> erasedType) {
/* 396 */       if (erasedType == Collection.class) {
/* 397 */         return VARS_COLLECTION;
/*     */       }
/* 399 */       if (erasedType == List.class) {
/* 400 */         return VARS_LIST;
/*     */       }
/* 402 */       if (erasedType == ArrayList.class) {
/* 403 */         return VARS_ARRAY_LIST;
/*     */       }
/* 405 */       if (erasedType == AbstractList.class) {
/* 406 */         return VARS_ABSTRACT_LIST;
/*     */       }
/* 408 */       if (erasedType == Iterable.class) {
/* 409 */         return VARS_ITERABLE;
/*     */       }
/* 411 */       return (TypeVariable<?>[])erasedType.getTypeParameters();
/*     */     }
/*     */ 
/*     */     
/*     */     public static TypeVariable<?>[] paramsFor2(Class<?> erasedType) {
/* 416 */       if (erasedType == Map.class) {
/* 417 */         return VARS_MAP;
/*     */       }
/* 419 */       if (erasedType == HashMap.class) {
/* 420 */         return VARS_HASH_MAP;
/*     */       }
/* 422 */       if (erasedType == LinkedHashMap.class) {
/* 423 */         return VARS_LINKED_HASH_MAP;
/*     */       }
/* 425 */       return (TypeVariable<?>[])erasedType.getTypeParameters();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class AsKey
/*     */   {
/*     */     private final Class<?> _raw;
/*     */     
/*     */     private final JavaType[] _params;
/*     */     
/*     */     private final int _hash;
/*     */ 
/*     */     
/*     */     public AsKey(Class<?> raw, JavaType[] params, int hash) {
/* 440 */       this._raw = raw;
/* 441 */       this._params = params;
/* 442 */       this._hash = hash;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 446 */       return this._hash;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 450 */       if (o == this) return true; 
/* 451 */       if (o == null) return false; 
/* 452 */       if (o.getClass() != getClass()) return false; 
/* 453 */       AsKey other = (AsKey)o;
/*     */       
/* 455 */       if (this._hash == other._hash && this._raw == other._raw) {
/* 456 */         JavaType[] otherParams = other._params;
/* 457 */         int len = this._params.length;
/*     */         
/* 459 */         if (len == otherParams.length) {
/* 460 */           for (int i = 0; i < len; i++) {
/* 461 */             if (!this._params[i].equals(otherParams[i])) {
/* 462 */               return false;
/*     */             }
/*     */           } 
/* 465 */           return true;
/*     */         } 
/*     */       } 
/* 468 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 473 */       return this._raw.getName() + "<>";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\TypeBindings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */