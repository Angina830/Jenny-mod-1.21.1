/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StdSubtypeResolver
/*     */   extends SubtypeResolver
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected LinkedHashSet<NamedType> _registeredSubtypes;
/*     */   
/*     */   public void registerSubtypes(NamedType... types) {
/*  34 */     if (this._registeredSubtypes == null) {
/*  35 */       this._registeredSubtypes = new LinkedHashSet<>();
/*     */     }
/*  37 */     for (NamedType type : types) {
/*  38 */       this._registeredSubtypes.add(type);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSubtypes(Class<?>... classes) {
/*  44 */     NamedType[] types = new NamedType[classes.length];
/*  45 */     for (int i = 0, len = classes.length; i < len; i++) {
/*  46 */       types[i] = new NamedType(classes[i]);
/*     */     }
/*  48 */     registerSubtypes(types);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSubtypes(Collection<Class<?>> subtypes) {
/*  53 */     int len = subtypes.size();
/*  54 */     NamedType[] types = new NamedType[len];
/*  55 */     int i = 0;
/*  56 */     for (Class<?> subtype : subtypes) {
/*  57 */       types[i++] = new NamedType(subtype);
/*     */     }
/*  59 */     registerSubtypes(types);
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
/*     */   public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> config, AnnotatedMember property, JavaType baseType) {
/*  72 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/*     */     
/*  74 */     Class<?> rawBase = (baseType == null) ? property.getRawType() : baseType.getRawClass();
/*     */     
/*  76 */     HashMap<NamedType, NamedType> collected = new HashMap<>();
/*     */     
/*  78 */     if (this._registeredSubtypes != null) {
/*  79 */       for (NamedType subtype : this._registeredSubtypes) {
/*     */         
/*  81 */         if (rawBase.isAssignableFrom(subtype.getType())) {
/*  82 */           AnnotatedClass curr = AnnotatedClassResolver.resolveWithoutSuperTypes(config, subtype.getType());
/*     */           
/*  84 */           _collectAndResolve(curr, subtype, config, ai, collected);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (property != null) {
/*  91 */       Collection<NamedType> st = ai.findSubtypes((Annotated)property);
/*  92 */       if (st != null) {
/*  93 */         for (NamedType nt : st) {
/*  94 */           AnnotatedClass annotatedClass = AnnotatedClassResolver.resolveWithoutSuperTypes(config, nt.getType());
/*     */           
/*  96 */           _collectAndResolve(annotatedClass, nt, config, ai, collected);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 101 */     NamedType rootType = new NamedType(rawBase, null);
/* 102 */     AnnotatedClass ac = AnnotatedClassResolver.resolveWithoutSuperTypes(config, rawBase);
/*     */ 
/*     */     
/* 105 */     _collectAndResolve(ac, rootType, config, ai, collected);
/*     */     
/* 107 */     return new ArrayList<>(collected.values());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> config, AnnotatedClass type) {
/* 114 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 115 */     HashMap<NamedType, NamedType> subtypes = new HashMap<>();
/*     */     
/* 117 */     if (this._registeredSubtypes != null) {
/* 118 */       Class<?> rawBase = type.getRawType();
/* 119 */       for (NamedType subtype : this._registeredSubtypes) {
/*     */         
/* 121 */         if (rawBase.isAssignableFrom(subtype.getType())) {
/* 122 */           AnnotatedClass curr = AnnotatedClassResolver.resolveWithoutSuperTypes(config, subtype.getType());
/*     */           
/* 124 */           _collectAndResolve(curr, subtype, config, ai, subtypes);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     NamedType rootType = new NamedType(type.getRawType(), null);
/* 130 */     _collectAndResolve(type, rootType, config, ai, subtypes);
/* 131 */     return new ArrayList<>(subtypes.values());
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
/*     */   public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> config, AnnotatedMember property, JavaType baseType) {
/* 144 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 145 */     Class<?> rawBase = baseType.getRawClass();
/*     */ 
/*     */     
/* 148 */     Set<Class<?>> typesHandled = new HashSet<>();
/* 149 */     Map<String, NamedType> byName = new LinkedHashMap<>();
/*     */ 
/*     */     
/* 152 */     NamedType rootType = new NamedType(rawBase, null);
/* 153 */     AnnotatedClass ac = AnnotatedClassResolver.resolveWithoutSuperTypes(config, rawBase);
/*     */     
/* 155 */     _collectAndResolveByTypeId(ac, rootType, config, typesHandled, byName);
/*     */ 
/*     */     
/* 158 */     if (property != null) {
/* 159 */       Collection<NamedType> st = ai.findSubtypes((Annotated)property);
/* 160 */       if (st != null) {
/* 161 */         for (NamedType nt : st) {
/* 162 */           ac = AnnotatedClassResolver.resolveWithoutSuperTypes(config, nt.getType());
/* 163 */           _collectAndResolveByTypeId(ac, nt, config, typesHandled, byName);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 168 */     if (this._registeredSubtypes != null) {
/* 169 */       for (NamedType subtype : this._registeredSubtypes) {
/*     */         
/* 171 */         if (rawBase.isAssignableFrom(subtype.getType())) {
/* 172 */           AnnotatedClass curr = AnnotatedClassResolver.resolveWithoutSuperTypes(config, subtype.getType());
/*     */           
/* 174 */           _collectAndResolveByTypeId(curr, subtype, config, typesHandled, byName);
/*     */         } 
/*     */       } 
/*     */     }
/* 178 */     return _combineNamedAndUnnamed(rawBase, typesHandled, byName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> config, AnnotatedClass baseType) {
/* 185 */     Class<?> rawBase = baseType.getRawType();
/* 186 */     Set<Class<?>> typesHandled = new HashSet<>();
/* 187 */     Map<String, NamedType> byName = new LinkedHashMap<>();
/*     */     
/* 189 */     NamedType rootType = new NamedType(rawBase, null);
/* 190 */     _collectAndResolveByTypeId(baseType, rootType, config, typesHandled, byName);
/*     */     
/* 192 */     if (this._registeredSubtypes != null) {
/* 193 */       for (NamedType subtype : this._registeredSubtypes) {
/*     */         
/* 195 */         if (rawBase.isAssignableFrom(subtype.getType())) {
/* 196 */           AnnotatedClass curr = AnnotatedClassResolver.resolveWithoutSuperTypes(config, subtype.getType());
/*     */           
/* 198 */           _collectAndResolveByTypeId(curr, subtype, config, typesHandled, byName);
/*     */         } 
/*     */       } 
/*     */     }
/* 202 */     return _combineNamedAndUnnamed(rawBase, typesHandled, byName);
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
/*     */   protected void _collectAndResolve(AnnotatedClass annotatedType, NamedType namedType, MapperConfig<?> config, AnnotationIntrospector ai, HashMap<NamedType, NamedType> collectedSubtypes) {
/* 219 */     if (!namedType.hasName()) {
/* 220 */       String name = ai.findTypeName(annotatedType);
/* 221 */       if (name != null) {
/* 222 */         namedType = new NamedType(namedType.getType(), name);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 227 */     if (collectedSubtypes.containsKey(namedType)) {
/*     */       
/* 229 */       if (namedType.hasName()) {
/* 230 */         NamedType prev = collectedSubtypes.get(namedType);
/* 231 */         if (!prev.hasName()) {
/* 232 */           collectedSubtypes.put(namedType, namedType);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     collectedSubtypes.put(namedType, namedType);
/* 239 */     Collection<NamedType> st = ai.findSubtypes((Annotated)annotatedType);
/* 240 */     if (st != null && !st.isEmpty()) {
/* 241 */       for (NamedType subtype : st) {
/* 242 */         AnnotatedClass subtypeClass = AnnotatedClassResolver.resolveWithoutSuperTypes(config, subtype.getType());
/*     */         
/* 244 */         _collectAndResolve(subtypeClass, subtype, config, ai, collectedSubtypes);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _collectAndResolveByTypeId(AnnotatedClass annotatedType, NamedType namedType, MapperConfig<?> config, Set<Class<?>> typesHandled, Map<String, NamedType> byName) {
/* 257 */     AnnotationIntrospector ai = config.getAnnotationIntrospector();
/* 258 */     if (!namedType.hasName()) {
/* 259 */       String name = ai.findTypeName(annotatedType);
/* 260 */       if (name != null) {
/* 261 */         namedType = new NamedType(namedType.getType(), name);
/*     */       }
/*     */     } 
/* 264 */     if (namedType.hasName()) {
/* 265 */       byName.put(namedType.getName(), namedType);
/*     */     }
/*     */ 
/*     */     
/* 269 */     if (typesHandled.add(namedType.getType())) {
/* 270 */       Collection<NamedType> st = ai.findSubtypes((Annotated)annotatedType);
/* 271 */       if (st != null && !st.isEmpty()) {
/* 272 */         for (NamedType subtype : st) {
/* 273 */           AnnotatedClass subtypeClass = AnnotatedClassResolver.resolveWithoutSuperTypes(config, subtype.getType());
/*     */           
/* 275 */           _collectAndResolveByTypeId(subtypeClass, subtype, config, typesHandled, byName);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collection<NamedType> _combineNamedAndUnnamed(Class<?> rawBase, Set<Class<?>> typesHandled, Map<String, NamedType> byName) {
/* 288 */     ArrayList<NamedType> result = new ArrayList<>(byName.values());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     for (NamedType t : byName.values()) {
/* 294 */       typesHandled.remove(t.getType());
/*     */     }
/* 296 */     for (Class<?> cls : typesHandled) {
/*     */ 
/*     */       
/* 299 */       if (cls == rawBase && Modifier.isAbstract(cls.getModifiers())) {
/*     */         continue;
/*     */       }
/* 302 */       result.add(new NamedType(cls));
/*     */     } 
/* 304 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\StdSubtypeResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */