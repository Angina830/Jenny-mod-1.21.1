/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumResolver
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<Enum<?>> _enumClass;
/*     */   protected final Enum<?>[] _enums;
/*     */   protected final HashMap<String, Enum<?>> _enumsById;
/*     */   protected final Enum<?> _defaultValue;
/*     */   
/*     */   protected EnumResolver(Class<Enum<?>> enumClass, Enum<?>[] enums, HashMap<String, Enum<?>> map, Enum<?> defaultValue) {
/*  26 */     this._enumClass = enumClass;
/*  27 */     this._enums = enums;
/*  28 */     this._enumsById = map;
/*  29 */     this._defaultValue = defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumResolver constructFor(Class<Enum<?>> enumCls, AnnotationIntrospector ai) {
/*  38 */     Enum[] arrayOfEnum = (Enum[])enumCls.getEnumConstants();
/*  39 */     if (arrayOfEnum == null) {
/*  40 */       throw new IllegalArgumentException("No enum constants for class " + enumCls.getName());
/*     */     }
/*  42 */     String[] names = ai.findEnumValues(enumCls, arrayOfEnum, new String[arrayOfEnum.length]);
/*  43 */     HashMap<String, Enum<?>> map = new HashMap<>();
/*  44 */     for (int i = 0, len = arrayOfEnum.length; i < len; i++) {
/*  45 */       String name = names[i];
/*  46 */       if (name == null) {
/*  47 */         name = arrayOfEnum[i].name();
/*     */       }
/*  49 */       map.put(name, arrayOfEnum[i]);
/*     */     } 
/*     */     
/*  52 */     Enum<?> defaultEnum = ai.findDefaultEnumValue(enumCls);
/*     */     
/*  54 */     return new EnumResolver(enumCls, (Enum<?>[])arrayOfEnum, map, defaultEnum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static EnumResolver constructUsingToString(Class<Enum<?>> enumCls) {
/*  62 */     return constructUsingToString(enumCls, null);
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
/*     */   public static EnumResolver constructUsingToString(Class<Enum<?>> enumCls, AnnotationIntrospector ai) {
/*  74 */     Enum[] arrayOfEnum = (Enum[])enumCls.getEnumConstants();
/*  75 */     HashMap<String, Enum<?>> map = new HashMap<>();
/*     */     
/*  77 */     for (int i = arrayOfEnum.length; --i >= 0; ) {
/*  78 */       Enum<?> e = arrayOfEnum[i];
/*  79 */       map.put(e.toString(), e);
/*     */     } 
/*  81 */     Enum<?> defaultEnum = (ai == null) ? null : ai.findDefaultEnumValue(enumCls);
/*  82 */     return new EnumResolver(enumCls, (Enum<?>[])arrayOfEnum, map, defaultEnum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumResolver constructUsingMethod(Class<Enum<?>> enumCls, AnnotatedMember accessor, AnnotationIntrospector ai) {
/*  92 */     Enum[] arrayOfEnum = (Enum[])enumCls.getEnumConstants();
/*  93 */     HashMap<String, Enum<?>> map = new HashMap<>();
/*     */     
/*  95 */     for (int i = arrayOfEnum.length; --i >= 0; ) {
/*  96 */       Enum<?> en = arrayOfEnum[i];
/*     */       try {
/*  98 */         Object o = accessor.getValue(en);
/*  99 */         if (o != null) {
/* 100 */           map.put(o.toString(), en);
/*     */         }
/* 102 */       } catch (Exception e) {
/* 103 */         throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + en + ": " + e.getMessage());
/*     */       } 
/*     */     } 
/* 106 */     Enum<?> defaultEnum = (ai != null) ? ai.findDefaultEnumValue(enumCls) : null;
/* 107 */     return new EnumResolver(enumCls, (Enum<?>[])arrayOfEnum, map, defaultEnum);
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
/*     */   public static EnumResolver constructUnsafe(Class<?> rawEnumCls, AnnotationIntrospector ai) {
/* 120 */     Class<Enum<?>> enumCls = (Class)rawEnumCls;
/* 121 */     return constructFor(enumCls, ai);
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
/*     */   public static EnumResolver constructUnsafeUsingToString(Class<?> rawEnumCls, AnnotationIntrospector ai) {
/* 135 */     Class<Enum<?>> enumCls = (Class)rawEnumCls;
/* 136 */     return constructUsingToString(enumCls, ai);
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
/*     */   public static EnumResolver constructUnsafeUsingMethod(Class<?> rawEnumCls, AnnotatedMember accessor, AnnotationIntrospector ai) {
/* 151 */     Class<Enum<?>> enumCls = (Class)rawEnumCls;
/* 152 */     return constructUsingMethod(enumCls, accessor, ai);
/*     */   }
/*     */   
/*     */   public CompactStringObjectMap constructLookup() {
/* 156 */     return CompactStringObjectMap.construct(this._enumsById);
/*     */   }
/*     */   public Enum<?> findEnum(String key) {
/* 159 */     return this._enumsById.get(key);
/*     */   }
/*     */   public Enum<?> getEnum(int index) {
/* 162 */     if (index < 0 || index >= this._enums.length) {
/* 163 */       return null;
/*     */     }
/* 165 */     return this._enums[index];
/*     */   }
/*     */   
/*     */   public Enum<?> getDefaultValue() {
/* 169 */     return this._defaultValue;
/*     */   }
/*     */   
/*     */   public Enum<?>[] getRawEnums() {
/* 173 */     return this._enums;
/*     */   }
/*     */   
/*     */   public List<Enum<?>> getEnums() {
/* 177 */     ArrayList<Enum<?>> enums = new ArrayList<>(this._enums.length);
/* 178 */     for (Enum<?> e : this._enums) {
/* 179 */       enums.add(e);
/*     */     }
/* 181 */     return enums;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getEnumIds() {
/* 188 */     return this._enumsById.keySet();
/*     */   }
/*     */   public Class<Enum<?>> getEnumClass() {
/* 191 */     return this._enumClass;
/*     */   } public int lastValidIndex() {
/* 193 */     return this._enums.length - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\EnumResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */