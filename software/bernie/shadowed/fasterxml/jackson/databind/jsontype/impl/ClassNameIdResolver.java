/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DatabindContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassNameIdResolver
/*     */   extends TypeIdResolverBase
/*     */ {
/*     */   public ClassNameIdResolver(JavaType baseType, TypeFactory typeFactory) {
/*  20 */     super(baseType, typeFactory);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.Id getMechanism() {
/*  24 */     return JsonTypeInfo.Id.CLASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSubtype(Class<?> type, String name) {}
/*     */ 
/*     */   
/*     */   public String idFromValue(Object value) {
/*  32 */     return _idFrom(value, value.getClass(), this._typeFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public String idFromValueAndType(Object value, Class<?> type) {
/*  37 */     return _idFrom(value, type, this._typeFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType typeFromId(DatabindContext context, String id) throws IOException {
/*  42 */     return _typeFromId(id, context);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JavaType _typeFromId(String id, DatabindContext ctxt) throws IOException {
/*  47 */     JavaType t = ctxt.resolveSubType(this._baseType, id);
/*  48 */     if (t == null && 
/*  49 */       ctxt instanceof DeserializationContext)
/*     */     {
/*  51 */       return ((DeserializationContext)ctxt).handleUnknownTypeId(this._baseType, id, this, "no such class found");
/*     */     }
/*     */ 
/*     */     
/*  55 */     return t;
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
/*     */   protected final String _idFrom(Object value, Class<?> cls, TypeFactory typeFactory) {
/*  67 */     if (Enum.class.isAssignableFrom(cls) && 
/*  68 */       !cls.isEnum()) {
/*  69 */       cls = cls.getSuperclass();
/*     */     }
/*     */     
/*  72 */     String str = cls.getName();
/*  73 */     if (str.startsWith("java.util")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       if (value instanceof EnumSet) {
/*  81 */         Class<?> enumClass = ClassUtil.findEnumType((EnumSet)value);
/*     */         
/*  83 */         str = typeFactory.constructCollectionType(EnumSet.class, enumClass).toCanonical();
/*  84 */       } else if (value instanceof EnumMap) {
/*  85 */         Class<?> enumClass = ClassUtil.findEnumType((EnumMap)value);
/*  86 */         Class<?> valueClass = Object.class;
/*     */         
/*  88 */         str = typeFactory.constructMapType(EnumMap.class, enumClass, valueClass).toCanonical();
/*     */       } else {
/*  90 */         String end = str.substring(9);
/*  91 */         if ((end.startsWith(".Arrays$") || end.startsWith(".Collections$")) && str.indexOf("List") >= 0)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  99 */           str = "java.util.ArrayList";
/*     */         }
/*     */       } 
/* 102 */     } else if (str.indexOf('$') >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       Class<?> outer = ClassUtil.getOuterClass(cls);
/* 111 */       if (outer != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 116 */         Class<?> staticType = this._baseType.getRawClass();
/* 117 */         if (ClassUtil.getOuterClass(staticType) == null) {
/*     */           
/* 119 */           cls = this._baseType.getRawClass();
/* 120 */           str = cls.getName();
/*     */         } 
/*     */       } 
/*     */     } 
/* 124 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescForKnownTypeIds() {
/* 129 */     return "class name used as type id";
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\ClassNameIdResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */