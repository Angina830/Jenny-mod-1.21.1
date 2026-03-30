/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.ResolvedType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ 
/*     */ public abstract class TypeBase extends JavaType implements JsonSerializable {
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   private static final TypeBindings NO_BINDINGS = TypeBindings.emptyBindings();
/*  20 */   private static final JavaType[] NO_TYPES = new JavaType[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _superClass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType[] _superInterfaces;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final TypeBindings _bindings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient String _canonicalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeBase(Class<?> raw, TypeBindings bindings, JavaType superClass, JavaType[] superInts, int hash, Object valueHandler, Object typeHandler, boolean asStatic) {
/*  47 */     super(raw, hash, valueHandler, typeHandler, asStatic);
/*  48 */     this._bindings = (bindings == null) ? NO_BINDINGS : bindings;
/*  49 */     this._superClass = superClass;
/*  50 */     this._superInterfaces = superInts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeBase(TypeBase base) {
/*  59 */     super(base);
/*  60 */     this._superClass = base._superClass;
/*  61 */     this._superInterfaces = base._superInterfaces;
/*  62 */     this._bindings = base._bindings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCanonical() {
/*  68 */     String str = this._canonicalName;
/*  69 */     if (str == null) {
/*  70 */       str = buildCanonicalName();
/*     */     }
/*  72 */     return str;
/*     */   }
/*     */   
/*     */   protected String buildCanonicalName() {
/*  76 */     return this._class.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeBindings getBindings() {
/*  87 */     return this._bindings;
/*     */   }
/*     */ 
/*     */   
/*     */   public int containedTypeCount() {
/*  92 */     return this._bindings.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType containedType(int index) {
/*  97 */     return this._bindings.getBoundType(index);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String containedTypeName(int index) {
/* 103 */     return this._bindings.getBoundName(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType getSuperClass() {
/* 108 */     return this._superClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<JavaType> getInterfaces() {
/* 113 */     if (this._superInterfaces == null) {
/* 114 */       return Collections.emptyList();
/*     */     }
/* 116 */     switch (this._superInterfaces.length) {
/*     */       case 0:
/* 118 */         return Collections.emptyList();
/*     */       case 1:
/* 120 */         return Collections.singletonList(this._superInterfaces[0]);
/*     */     } 
/* 122 */     return Arrays.asList(this._superInterfaces);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final JavaType findSuperType(Class<?> rawTarget) {
/* 128 */     if (rawTarget == this._class) {
/* 129 */       return this;
/*     */     }
/*     */     
/* 132 */     if (rawTarget.isInterface() && this._superInterfaces != null) {
/* 133 */       for (int i = 0, count = this._superInterfaces.length; i < count; i++) {
/* 134 */         JavaType type = this._superInterfaces[i].findSuperType(rawTarget);
/* 135 */         if (type != null) {
/* 136 */           return type;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 141 */     if (this._superClass != null) {
/* 142 */       JavaType type = this._superClass.findSuperType(rawTarget);
/* 143 */       if (type != null) {
/* 144 */         return type;
/*     */       }
/*     */     } 
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType[] findTypeParameters(Class<?> expType) {
/* 153 */     JavaType match = findSuperType(expType);
/* 154 */     if (match == null) {
/* 155 */       return NO_TYPES;
/*     */     }
/* 157 */     return match.getBindings().typeParameterArray();
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
/*     */   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 171 */     WritableTypeId typeIdDef = new WritableTypeId(this, JsonToken.VALUE_STRING);
/* 172 */     typeSer.writeTypePrefix(g, typeIdDef);
/* 173 */     serialize(g, provider);
/* 174 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
/* 181 */     gen.writeString(toCanonical());
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
/*     */   protected static StringBuilder _classSignature(Class<?> cls, StringBuilder sb, boolean trailingSemicolon) {
/* 197 */     if (cls.isPrimitive()) {
/* 198 */       if (cls == boolean.class) {
/* 199 */         sb.append('Z');
/* 200 */       } else if (cls == byte.class) {
/* 201 */         sb.append('B');
/*     */       }
/* 203 */       else if (cls == short.class) {
/* 204 */         sb.append('S');
/*     */       }
/* 206 */       else if (cls == char.class) {
/* 207 */         sb.append('C');
/*     */       }
/* 209 */       else if (cls == int.class) {
/* 210 */         sb.append('I');
/*     */       }
/* 212 */       else if (cls == long.class) {
/* 213 */         sb.append('J');
/*     */       }
/* 215 */       else if (cls == float.class) {
/* 216 */         sb.append('F');
/*     */       }
/* 218 */       else if (cls == double.class) {
/* 219 */         sb.append('D');
/*     */       }
/* 221 */       else if (cls == void.class) {
/* 222 */         sb.append('V');
/*     */       } else {
/* 224 */         throw new IllegalStateException("Unrecognized primitive type: " + cls.getName());
/*     */       } 
/*     */     } else {
/* 227 */       sb.append('L');
/* 228 */       String name = cls.getName();
/* 229 */       for (int i = 0, len = name.length(); i < len; i++) {
/* 230 */         char c = name.charAt(i);
/* 231 */         if (c == '.') c = '/'; 
/* 232 */         sb.append(c);
/*     */       } 
/* 234 */       if (trailingSemicolon) {
/* 235 */         sb.append(';');
/*     */       }
/*     */     } 
/* 238 */     return sb;
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
/*     */   protected static JavaType _bogusSuperClass(Class<?> cls) {
/* 251 */     Class<?> parent = cls.getSuperclass();
/* 252 */     if (parent == null) {
/* 253 */       return null;
/*     */     }
/* 255 */     return TypeFactory.unknownType();
/*     */   }
/*     */   
/*     */   public abstract StringBuilder getGenericSignature(StringBuilder paramStringBuilder);
/*     */   
/*     */   public abstract StringBuilder getErasedSignature(StringBuilder paramStringBuilder);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\TypeBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */