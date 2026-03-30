/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.lang.reflect.WildcardType;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumMap;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.type.TypeReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.LRUMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TypeFactory
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   39 */   private static final JavaType[] NO_TYPES = new JavaType[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   46 */   protected static final TypeFactory instance = new TypeFactory();
/*      */   
/*   48 */   protected static final TypeBindings EMPTY_BINDINGS = TypeBindings.emptyBindings();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   60 */   private static final Class<?> CLS_STRING = String.class;
/*   61 */   private static final Class<?> CLS_OBJECT = Object.class;
/*      */   
/*   63 */   private static final Class<?> CLS_COMPARABLE = Comparable.class;
/*   64 */   private static final Class<?> CLS_CLASS = Class.class;
/*   65 */   private static final Class<?> CLS_ENUM = Enum.class;
/*      */   
/*   67 */   private static final Class<?> CLS_BOOL = boolean.class;
/*   68 */   private static final Class<?> CLS_INT = int.class;
/*   69 */   private static final Class<?> CLS_LONG = long.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   protected static final SimpleType CORE_TYPE_BOOL = new SimpleType(CLS_BOOL);
/*   79 */   protected static final SimpleType CORE_TYPE_INT = new SimpleType(CLS_INT);
/*   80 */   protected static final SimpleType CORE_TYPE_LONG = new SimpleType(CLS_LONG);
/*      */ 
/*      */   
/*   83 */   protected static final SimpleType CORE_TYPE_STRING = new SimpleType(CLS_STRING);
/*      */ 
/*      */   
/*   86 */   protected static final SimpleType CORE_TYPE_OBJECT = new SimpleType(CLS_OBJECT);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   protected static final SimpleType CORE_TYPE_COMPARABLE = new SimpleType(CLS_COMPARABLE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   protected static final SimpleType CORE_TYPE_ENUM = new SimpleType(CLS_ENUM);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   protected static final SimpleType CORE_TYPE_CLASS = new SimpleType(CLS_CLASS);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final LRUMap<Object, JavaType> _typeCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TypeModifier[] _modifiers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TypeParser _parser;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ClassLoader _classLoader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TypeFactory() {
/*  145 */     this(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TypeFactory(LRUMap<Object, JavaType> typeCache) {
/*  152 */     if (typeCache == null) {
/*  153 */       typeCache = new LRUMap(16, 200);
/*      */     }
/*  155 */     this._typeCache = typeCache;
/*  156 */     this._parser = new TypeParser(this);
/*  157 */     this._modifiers = null;
/*  158 */     this._classLoader = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected TypeFactory(LRUMap<Object, JavaType> typeCache, TypeParser p, TypeModifier[] mods, ClassLoader classLoader) {
/*  164 */     if (typeCache == null) {
/*  165 */       typeCache = new LRUMap(16, 200);
/*      */     }
/*  167 */     this._typeCache = typeCache;
/*      */     
/*  169 */     this._parser = p.withFactory(this);
/*  170 */     this._modifiers = mods;
/*  171 */     this._classLoader = classLoader;
/*      */   }
/*      */   
/*      */   public TypeFactory withModifier(TypeModifier mod) {
/*      */     TypeModifier[] mods;
/*  176 */     LRUMap<Object, JavaType> typeCache = this._typeCache;
/*      */     
/*  178 */     if (mod == null) {
/*  179 */       mods = null;
/*      */ 
/*      */       
/*  182 */       typeCache = null;
/*  183 */     } else if (this._modifiers == null) {
/*  184 */       mods = new TypeModifier[] { mod };
/*      */     } else {
/*  186 */       mods = (TypeModifier[])ArrayBuilders.insertInListNoDup((Object[])this._modifiers, mod);
/*      */     } 
/*  188 */     return new TypeFactory(typeCache, this._parser, mods, this._classLoader);
/*      */   }
/*      */   
/*      */   public TypeFactory withClassLoader(ClassLoader classLoader) {
/*  192 */     return new TypeFactory(this._typeCache, this._parser, this._modifiers, classLoader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeFactory withCache(LRUMap<Object, JavaType> cache) {
/*  203 */     return new TypeFactory(cache, this._parser, this._modifiers, this._classLoader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TypeFactory defaultInstance() {
/*  211 */     return instance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearCache() {
/*  224 */     this._typeCache.clear();
/*      */   }
/*      */   
/*      */   public ClassLoader getClassLoader() {
/*  228 */     return this._classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JavaType unknownType() {
/*  243 */     return defaultInstance()._unknownType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> rawClass(Type t) {
/*  253 */     if (t instanceof Class) {
/*  254 */       return (Class)t;
/*      */     }
/*      */     
/*  257 */     return defaultInstance().constructType(t).getRawClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> findClass(String className) throws ClassNotFoundException {
/*  274 */     if (className.indexOf('.') < 0) {
/*  275 */       Class<?> prim = _findPrimitive(className);
/*  276 */       if (prim != null) {
/*  277 */         return prim;
/*      */       }
/*      */     } 
/*      */     
/*  281 */     Throwable prob = null;
/*  282 */     ClassLoader loader = getClassLoader();
/*  283 */     if (loader == null) {
/*  284 */       loader = Thread.currentThread().getContextClassLoader();
/*      */     }
/*  286 */     if (loader != null) {
/*      */       try {
/*  288 */         return classForName(className, true, loader);
/*  289 */       } catch (Exception e) {
/*  290 */         prob = ClassUtil.getRootCause(e);
/*      */       } 
/*      */     }
/*      */     try {
/*  294 */       return classForName(className);
/*  295 */     } catch (Exception e) {
/*  296 */       if (prob == null) {
/*  297 */         prob = ClassUtil.getRootCause(e);
/*      */       }
/*      */       
/*  300 */       ClassUtil.throwIfRTE(prob);
/*  301 */       throw new ClassNotFoundException(prob.getMessage(), prob);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Class<?> classForName(String name, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
/*  306 */     return Class.forName(name, true, loader);
/*      */   }
/*      */   
/*      */   protected Class<?> classForName(String name) throws ClassNotFoundException {
/*  310 */     return Class.forName(name);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Class<?> _findPrimitive(String className) {
/*  315 */     if ("int".equals(className)) return int.class; 
/*  316 */     if ("long".equals(className)) return long.class; 
/*  317 */     if ("float".equals(className)) return float.class; 
/*  318 */     if ("double".equals(className)) return double.class; 
/*  319 */     if ("boolean".equals(className)) return boolean.class; 
/*  320 */     if ("byte".equals(className)) return byte.class; 
/*  321 */     if ("char".equals(className)) return char.class; 
/*  322 */     if ("short".equals(className)) return short.class; 
/*  323 */     if ("void".equals(className)) return void.class; 
/*  324 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructSpecializedType(JavaType baseType, Class<?> subclass) {
/*  343 */     Class<?> rawBase = baseType.getRawClass();
/*  344 */     if (rawBase == subclass) {
/*  345 */       return baseType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  352 */     if (rawBase == Object.class)
/*  353 */     { newType = _fromClass(null, subclass, TypeBindings.emptyBindings()); }
/*      */     else
/*      */     
/*  356 */     { if (!rawBase.isAssignableFrom(subclass)) {
/*  357 */         throw new IllegalArgumentException(String.format("Class %s not subtype of %s", new Object[] { subclass.getName(), baseType }));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  363 */       if (baseType.getBindings().isEmpty())
/*  364 */       { newType = _fromClass(null, subclass, TypeBindings.emptyBindings()); }
/*      */       
/*      */       else
/*      */       
/*  368 */       { if (baseType.isContainerType())
/*  369 */           if (baseType.isMapLikeType())
/*  370 */           { if (subclass == HashMap.class || subclass == LinkedHashMap.class || subclass == EnumMap.class || subclass == TreeMap.class)
/*      */             
/*      */             { 
/*      */               
/*  374 */               newType = _fromClass(null, subclass, TypeBindings.create(subclass, baseType.getKeyType(), baseType.getContentType()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  426 */               newType = newType.withHandlersFrom(baseType);
/*  427 */               return newType; }  } else if (baseType.isCollectionLikeType()) { if (subclass == ArrayList.class || subclass == LinkedList.class || subclass == HashSet.class || subclass == TreeSet.class) { newType = _fromClass(null, subclass, TypeBindings.create(subclass, baseType.getContentType())); } else { if (rawBase == EnumSet.class) return baseType;  int typeParamCount = (subclass.getTypeParameters()).length; }  newType = newType.withHandlersFrom(baseType); return newType; }   int i = (subclass.getTypeParameters()).length; }  }  JavaType newType = newType.withHandlersFrom(baseType); return newType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TypeBindings _bindingsForSubtype(JavaType baseType, int typeParamCount, Class<?> subclass) {
/*  479 */     int baseCount = baseType.containedTypeCount();
/*  480 */     if (baseCount == typeParamCount) {
/*  481 */       if (typeParamCount == 1) {
/*  482 */         return TypeBindings.create(subclass, baseType.containedType(0));
/*      */       }
/*  484 */       if (typeParamCount == 2) {
/*  485 */         return TypeBindings.create(subclass, baseType.containedType(0), baseType.containedType(1));
/*      */       }
/*      */       
/*  488 */       List<JavaType> types = new ArrayList<>(baseCount);
/*  489 */       for (int i = 0; i < baseCount; i++) {
/*  490 */         types.add(baseType.containedType(i));
/*      */       }
/*  492 */       return TypeBindings.create(subclass, types);
/*      */     } 
/*      */     
/*  495 */     return TypeBindings.emptyBindings();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructGeneralizedType(JavaType baseType, Class<?> superClass) {
/*  511 */     Class<?> rawBase = baseType.getRawClass();
/*  512 */     if (rawBase == superClass) {
/*  513 */       return baseType;
/*      */     }
/*  515 */     JavaType superType = baseType.findSuperType(superClass);
/*  516 */     if (superType == null) {
/*      */       
/*  518 */       if (!superClass.isAssignableFrom(rawBase)) {
/*  519 */         throw new IllegalArgumentException(String.format("Class %s not a super-type of %s", new Object[] { superClass.getName(), baseType }));
/*      */       }
/*      */ 
/*      */       
/*  523 */       throw new IllegalArgumentException(String.format("Internal error: class %s not included as super-type for %s", new Object[] { superClass.getName(), baseType }));
/*      */     } 
/*      */ 
/*      */     
/*  527 */     return superType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructFromCanonical(String canonical) throws IllegalArgumentException {
/*  542 */     return this._parser.parse(canonical);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType[] findTypeParameters(JavaType type, Class<?> expType) {
/*  556 */     JavaType match = type.findSuperType(expType);
/*  557 */     if (match == null) {
/*  558 */       return NO_TYPES;
/*      */     }
/*  560 */     return match.getBindings().typeParameterArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType[] findTypeParameters(Class<?> clz, Class<?> expType, TypeBindings bindings) {
/*  568 */     return findTypeParameters(constructType(clz, bindings), expType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType[] findTypeParameters(Class<?> clz, Class<?> expType) {
/*  576 */     return findTypeParameters(constructType(clz), expType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType moreSpecificType(JavaType type1, JavaType type2) {
/*  591 */     if (type1 == null) {
/*  592 */       return type2;
/*      */     }
/*  594 */     if (type2 == null) {
/*  595 */       return type1;
/*      */     }
/*  597 */     Class<?> raw1 = type1.getRawClass();
/*  598 */     Class<?> raw2 = type2.getRawClass();
/*  599 */     if (raw1 == raw2) {
/*  600 */       return type1;
/*      */     }
/*      */     
/*  603 */     if (raw1.isAssignableFrom(raw2)) {
/*  604 */       return type2;
/*      */     }
/*  606 */     return type1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructType(Type type) {
/*  616 */     return _fromAny(null, type, EMPTY_BINDINGS);
/*      */   }
/*      */   
/*      */   public JavaType constructType(Type type, TypeBindings bindings) {
/*  620 */     return _fromAny(null, type, bindings);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructType(TypeReference<?> typeRef) {
/*  626 */     return _fromAny(null, typeRef.getType(), EMPTY_BINDINGS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType constructType(Type type, Class<?> contextClass) {
/*  652 */     JavaType contextType = (contextClass == null) ? null : constructType(contextClass);
/*  653 */     return constructType(type, contextType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType constructType(Type type, JavaType contextType) {
/*      */     TypeBindings bindings;
/*  662 */     if (contextType == null) {
/*  663 */       bindings = TypeBindings.emptyBindings();
/*      */     } else {
/*  665 */       bindings = contextType.getBindings();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  670 */       if (type.getClass() != Class.class)
/*      */       {
/*      */         
/*  673 */         while (bindings.isEmpty()) {
/*  674 */           contextType = contextType.getSuperClass();
/*  675 */           if (contextType == null) {
/*      */             break;
/*      */           }
/*  678 */           bindings = contextType.getBindings();
/*      */         } 
/*      */       }
/*      */     } 
/*  682 */     return _fromAny(null, type, bindings);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayType constructArrayType(Class<?> elementType) {
/*  698 */     return ArrayType.construct(_fromAny(null, elementType, null), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayType constructArrayType(JavaType elementType) {
/*  708 */     return ArrayType.construct(elementType, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CollectionType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
/*  719 */     return constructCollectionType(collectionClass, _fromClass(null, elementClass, EMPTY_BINDINGS));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CollectionType constructCollectionType(Class<? extends Collection> collectionClass, JavaType elementType) {
/*  732 */     TypeBindings bindings = TypeBindings.createIfNeeded(collectionClass, elementType);
/*  733 */     CollectionType result = (CollectionType)_fromClass(null, collectionClass, bindings);
/*      */ 
/*      */     
/*  736 */     if (bindings.isEmpty() && elementType != null) {
/*  737 */       JavaType t = result.findSuperType(Collection.class);
/*  738 */       JavaType realET = t.getContentType();
/*  739 */       if (!realET.equals(elementType)) {
/*  740 */         throw new IllegalArgumentException(String.format("Non-generic Collection class %s did not resolve to something with element type %s but %s ", new Object[] { ClassUtil.nameOf(collectionClass), elementType, realET }));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  745 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CollectionLikeType constructCollectionLikeType(Class<?> collectionClass, Class<?> elementClass) {
/*  755 */     return constructCollectionLikeType(collectionClass, _fromClass(null, elementClass, EMPTY_BINDINGS));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CollectionLikeType constructCollectionLikeType(Class<?> collectionClass, JavaType elementType) {
/*  766 */     JavaType type = _fromClass(null, collectionClass, TypeBindings.createIfNeeded(collectionClass, elementType));
/*      */     
/*  768 */     if (type instanceof CollectionLikeType) {
/*  769 */       return (CollectionLikeType)type;
/*      */     }
/*  771 */     return CollectionLikeType.upgradeFrom(type, elementType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapType constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
/*      */     JavaType kt;
/*      */     JavaType vt;
/*  783 */     if (mapClass == Properties.class) {
/*  784 */       kt = vt = CORE_TYPE_STRING;
/*      */     } else {
/*  786 */       kt = _fromClass(null, keyClass, EMPTY_BINDINGS);
/*  787 */       vt = _fromClass(null, valueClass, EMPTY_BINDINGS);
/*      */     } 
/*  789 */     return constructMapType(mapClass, kt, vt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapType constructMapType(Class<? extends Map> mapClass, JavaType keyType, JavaType valueType) {
/*  799 */     TypeBindings bindings = TypeBindings.createIfNeeded(mapClass, new JavaType[] { keyType, valueType });
/*  800 */     MapType result = (MapType)_fromClass(null, mapClass, bindings);
/*      */ 
/*      */     
/*  803 */     if (bindings.isEmpty()) {
/*  804 */       JavaType t = result.findSuperType(Map.class);
/*  805 */       JavaType realKT = t.getKeyType();
/*  806 */       if (!realKT.equals(keyType)) {
/*  807 */         throw new IllegalArgumentException(String.format("Non-generic Map class %s did not resolve to something with key type %s but %s ", new Object[] { ClassUtil.nameOf(mapClass), keyType, realKT }));
/*      */       }
/*      */ 
/*      */       
/*  811 */       JavaType realVT = t.getContentType();
/*  812 */       if (!realVT.equals(valueType)) {
/*  813 */         throw new IllegalArgumentException(String.format("Non-generic Map class %s did not resolve to something with value type %s but %s ", new Object[] { ClassUtil.nameOf(mapClass), valueType, realVT }));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  818 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapLikeType constructMapLikeType(Class<?> mapClass, Class<?> keyClass, Class<?> valueClass) {
/*  828 */     return constructMapLikeType(mapClass, _fromClass(null, keyClass, EMPTY_BINDINGS), _fromClass(null, valueClass, EMPTY_BINDINGS));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapLikeType constructMapLikeType(Class<?> mapClass, JavaType keyType, JavaType valueType) {
/*  842 */     JavaType type = _fromClass(null, mapClass, TypeBindings.createIfNeeded(mapClass, new JavaType[] { keyType, valueType }));
/*      */     
/*  844 */     if (type instanceof MapLikeType) {
/*  845 */       return (MapLikeType)type;
/*      */     }
/*  847 */     return MapLikeType.upgradeFrom(type, keyType, valueType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructSimpleType(Class<?> rawType, JavaType[] parameterTypes) {
/*  856 */     return _fromClass(null, rawType, TypeBindings.create(rawType, parameterTypes));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType constructSimpleType(Class<?> rawType, Class<?> parameterTarget, JavaType[] parameterTypes) {
/*  870 */     return constructSimpleType(rawType, parameterTypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructReferenceType(Class<?> rawType, JavaType referredType) {
/*  878 */     return ReferenceType.construct(rawType, null, null, null, referredType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType uncheckedSimpleType(Class<?> cls) {
/*  897 */     return _constructSimple(cls, EMPTY_BINDINGS, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
/*  928 */     int len = parameterClasses.length;
/*  929 */     JavaType[] pt = new JavaType[len];
/*  930 */     for (int i = 0; i < len; i++) {
/*  931 */       pt[i] = _fromClass(null, parameterClasses[i], null);
/*      */     }
/*  933 */     return constructParametricType(parametrized, pt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType constructParametricType(Class<?> rawType, JavaType... parameterTypes) {
/*  965 */     return _fromClass(null, rawType, TypeBindings.create(rawType, parameterTypes));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType constructParametrizedType(Class<?> parametrized, Class<?> parametersFor, JavaType... parameterTypes) {
/*  977 */     return constructParametricType(parametrized, parameterTypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JavaType constructParametrizedType(Class<?> parametrized, Class<?> parametersFor, Class<?>... parameterClasses) {
/*  989 */     return constructParametricType(parametrized, parameterClasses);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CollectionType constructRawCollectionType(Class<? extends Collection> collectionClass) {
/* 1011 */     return constructCollectionType(collectionClass, unknownType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CollectionLikeType constructRawCollectionLikeType(Class<?> collectionClass) {
/* 1026 */     return constructCollectionLikeType(collectionClass, unknownType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapType constructRawMapType(Class<? extends Map> mapClass) {
/* 1041 */     return constructMapType(mapClass, unknownType(), unknownType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapLikeType constructRawMapLikeType(Class<?> mapClass) {
/* 1056 */     return constructMapLikeType(mapClass, unknownType(), unknownType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JavaType _mapType(Class<?> rawClass, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/*      */     JavaType kt;
/*      */     JavaType vt;
/* 1071 */     if (rawClass == Properties.class)
/* 1072 */     { kt = vt = CORE_TYPE_STRING; }
/*      */     else
/* 1074 */     { List<JavaType> typeParams = bindings.getTypeParameters();
/*      */       
/* 1076 */       switch (typeParams.size())
/*      */       { case 0:
/* 1078 */           kt = vt = _unknownType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1088 */           return MapType.construct(rawClass, bindings, superClass, superInterfaces, kt, vt);case 2: kt = typeParams.get(0); vt = typeParams.get(1); return MapType.construct(rawClass, bindings, superClass, superInterfaces, kt, vt); }  throw new IllegalArgumentException("Strange Map type " + rawClass.getName() + ": cannot determine type parameters"); }  return MapType.construct(rawClass, bindings, superClass, superInterfaces, kt, vt);
/*      */   }
/*      */ 
/*      */   
/*      */   private JavaType _collectionType(Class<?> rawClass, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/*      */     JavaType ct;
/* 1094 */     List<JavaType> typeParams = bindings.getTypeParameters();
/*      */ 
/*      */     
/* 1097 */     if (typeParams.isEmpty()) {
/* 1098 */       ct = _unknownType();
/* 1099 */     } else if (typeParams.size() == 1) {
/* 1100 */       ct = typeParams.get(0);
/*      */     } else {
/* 1102 */       throw new IllegalArgumentException("Strange Collection type " + rawClass.getName() + ": cannot determine type parameters");
/*      */     } 
/* 1104 */     return CollectionType.construct(rawClass, bindings, superClass, superInterfaces, ct);
/*      */   }
/*      */ 
/*      */   
/*      */   private JavaType _referenceType(Class<?> rawClass, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/*      */     JavaType ct;
/* 1110 */     List<JavaType> typeParams = bindings.getTypeParameters();
/*      */ 
/*      */     
/* 1113 */     if (typeParams.isEmpty()) {
/* 1114 */       ct = _unknownType();
/* 1115 */     } else if (typeParams.size() == 1) {
/* 1116 */       ct = typeParams.get(0);
/*      */     } else {
/* 1118 */       throw new IllegalArgumentException("Strange Reference type " + rawClass.getName() + ": cannot determine type parameters");
/*      */     } 
/* 1120 */     return ReferenceType.construct(rawClass, bindings, superClass, superInterfaces, ct);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _constructSimple(Class<?> raw, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 1134 */     if (bindings.isEmpty()) {
/* 1135 */       JavaType result = _findWellKnownSimple(raw);
/* 1136 */       if (result != null) {
/* 1137 */         return result;
/*      */       }
/*      */     } 
/* 1140 */     return _newSimpleType(raw, bindings, superClass, superInterfaces);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _newSimpleType(Class<?> raw, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 1153 */     return new SimpleType(raw, bindings, superClass, superInterfaces);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _unknownType() {
/* 1162 */     return CORE_TYPE_OBJECT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _findWellKnownSimple(Class<?> clz) {
/* 1173 */     if (clz.isPrimitive()) {
/* 1174 */       if (clz == CLS_BOOL) return CORE_TYPE_BOOL; 
/* 1175 */       if (clz == CLS_INT) return CORE_TYPE_INT; 
/* 1176 */       if (clz == CLS_LONG) return CORE_TYPE_LONG; 
/*      */     } else {
/* 1178 */       if (clz == CLS_STRING) return CORE_TYPE_STRING; 
/* 1179 */       if (clz == CLS_OBJECT) return CORE_TYPE_OBJECT; 
/*      */     } 
/* 1181 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromAny(ClassStack context, Type type, TypeBindings bindings) {
/*      */     JavaType resultType;
/* 1200 */     if (type instanceof Class) {
/*      */       
/* 1202 */       resultType = _fromClass(context, (Class)type, EMPTY_BINDINGS);
/*      */     
/*      */     }
/* 1205 */     else if (type instanceof ParameterizedType) {
/* 1206 */       resultType = _fromParamType(context, (ParameterizedType)type, bindings);
/*      */     } else {
/* 1208 */       if (type instanceof JavaType)
/*      */       {
/* 1210 */         return (JavaType)type;
/*      */       }
/* 1212 */       if (type instanceof GenericArrayType) {
/* 1213 */         resultType = _fromArrayType(context, (GenericArrayType)type, bindings);
/*      */       }
/* 1215 */       else if (type instanceof TypeVariable) {
/* 1216 */         resultType = _fromVariable(context, (TypeVariable)type, bindings);
/*      */       }
/* 1218 */       else if (type instanceof WildcardType) {
/* 1219 */         resultType = _fromWildcard(context, (WildcardType)type, bindings);
/*      */       } else {
/*      */         
/* 1222 */         throw new IllegalArgumentException("Unrecognized Type: " + ((type == null) ? "[null]" : type.toString()));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1227 */     if (this._modifiers != null) {
/* 1228 */       TypeBindings b = resultType.getBindings();
/* 1229 */       if (b == null) {
/* 1230 */         b = EMPTY_BINDINGS;
/*      */       }
/* 1232 */       for (TypeModifier mod : this._modifiers) {
/* 1233 */         JavaType t = mod.modifyType(resultType, type, b, this);
/* 1234 */         if (t == null) {
/* 1235 */           throw new IllegalStateException(String.format("TypeModifier %s (of type %s) return null for type %s", new Object[] { mod, mod.getClass().getName(), resultType }));
/*      */         }
/*      */ 
/*      */         
/* 1239 */         resultType = t;
/*      */       } 
/*      */     } 
/* 1242 */     return resultType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromClass(ClassStack context, Class<?> rawType, TypeBindings bindings) {
/*      */     Object key;
/* 1252 */     JavaType result = _findWellKnownSimple(rawType);
/* 1253 */     if (result != null) {
/* 1254 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1258 */     if (bindings == null || bindings.isEmpty()) {
/* 1259 */       key = rawType;
/*      */     } else {
/* 1261 */       key = bindings.asKey(rawType);
/*      */     } 
/* 1263 */     result = (JavaType)this._typeCache.get(key);
/* 1264 */     if (result != null) {
/* 1265 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1269 */     if (context == null) {
/* 1270 */       context = new ClassStack(rawType);
/*      */     } else {
/* 1272 */       ClassStack prev = context.find(rawType);
/* 1273 */       if (prev != null) {
/*      */         
/* 1275 */         ResolvedRecursiveType selfRef = new ResolvedRecursiveType(rawType, EMPTY_BINDINGS);
/* 1276 */         prev.addSelfReference(selfRef);
/* 1277 */         return selfRef;
/*      */       } 
/*      */       
/* 1280 */       context = context.child(rawType);
/*      */     } 
/*      */ 
/*      */     
/* 1284 */     if (rawType.isArray()) {
/* 1285 */       result = ArrayType.construct(_fromAny(context, rawType.getComponentType(), bindings), bindings);
/*      */     } else {
/*      */       JavaType superClass;
/*      */ 
/*      */       
/*      */       JavaType[] superInterfaces;
/*      */ 
/*      */       
/* 1293 */       if (rawType.isInterface()) {
/* 1294 */         superClass = null;
/* 1295 */         superInterfaces = _resolveSuperInterfaces(context, rawType, bindings);
/*      */       } else {
/*      */         
/* 1298 */         superClass = _resolveSuperClass(context, rawType, bindings);
/* 1299 */         superInterfaces = _resolveSuperInterfaces(context, rawType, bindings);
/*      */       } 
/*      */ 
/*      */       
/* 1303 */       if (rawType == Properties.class) {
/* 1304 */         result = MapType.construct(rawType, bindings, superClass, superInterfaces, CORE_TYPE_STRING, CORE_TYPE_STRING);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1309 */       else if (superClass != null) {
/* 1310 */         result = superClass.refine(rawType, bindings, superClass, superInterfaces);
/*      */       } 
/*      */       
/* 1313 */       if (result == null) {
/* 1314 */         result = _fromWellKnownClass(context, rawType, bindings, superClass, superInterfaces);
/* 1315 */         if (result == null) {
/* 1316 */           result = _fromWellKnownInterface(context, rawType, bindings, superClass, superInterfaces);
/* 1317 */           if (result == null)
/*      */           {
/* 1319 */             result = _newSimpleType(rawType, bindings, superClass, superInterfaces);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1324 */     context.resolveSelfReferences(result);
/*      */ 
/*      */     
/* 1327 */     if (!result.hasHandlers()) {
/* 1328 */       this._typeCache.putIfAbsent(key, result);
/*      */     }
/* 1330 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected JavaType _resolveSuperClass(ClassStack context, Class<?> rawType, TypeBindings parentBindings) {
/* 1335 */     Type parent = ClassUtil.getGenericSuperclass(rawType);
/* 1336 */     if (parent == null) {
/* 1337 */       return null;
/*      */     }
/* 1339 */     return _fromAny(context, parent, parentBindings);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JavaType[] _resolveSuperInterfaces(ClassStack context, Class<?> rawType, TypeBindings parentBindings) {
/* 1344 */     Type[] types = ClassUtil.getGenericInterfaces(rawType);
/* 1345 */     if (types == null || types.length == 0) {
/* 1346 */       return NO_TYPES;
/*      */     }
/* 1348 */     int len = types.length;
/* 1349 */     JavaType[] resolved = new JavaType[len];
/* 1350 */     for (int i = 0; i < len; i++) {
/* 1351 */       Type type = types[i];
/* 1352 */       resolved[i] = _fromAny(context, type, parentBindings);
/*      */     } 
/* 1354 */     return resolved;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromWellKnownClass(ClassStack context, Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 1365 */     if (bindings == null) {
/* 1366 */       bindings = TypeBindings.emptyBindings();
/*      */     }
/*      */ 
/*      */     
/* 1370 */     if (rawType == Map.class) {
/* 1371 */       return _mapType(rawType, bindings, superClass, superInterfaces);
/*      */     }
/* 1373 */     if (rawType == Collection.class) {
/* 1374 */       return _collectionType(rawType, bindings, superClass, superInterfaces);
/*      */     }
/*      */     
/* 1377 */     if (rawType == AtomicReference.class) {
/* 1378 */       return _referenceType(rawType, bindings, superClass, superInterfaces);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1384 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromWellKnownInterface(ClassStack context, Class<?> rawType, TypeBindings bindings, JavaType superClass, JavaType[] superInterfaces) {
/* 1392 */     int intCount = superInterfaces.length;
/*      */     
/* 1394 */     for (int i = 0; i < intCount; i++) {
/* 1395 */       JavaType result = superInterfaces[i].refine(rawType, bindings, superClass, superInterfaces);
/* 1396 */       if (result != null) {
/* 1397 */         return result;
/*      */       }
/*      */     } 
/* 1400 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromParamType(ClassStack context, ParameterizedType ptype, TypeBindings parentBindings) {
/*      */     TypeBindings newBindings;
/* 1411 */     Class<?> rawType = (Class)ptype.getRawType();
/*      */ 
/*      */ 
/*      */     
/* 1415 */     if (rawType == CLS_ENUM) {
/* 1416 */       return CORE_TYPE_ENUM;
/*      */     }
/* 1418 */     if (rawType == CLS_COMPARABLE) {
/* 1419 */       return CORE_TYPE_COMPARABLE;
/*      */     }
/* 1421 */     if (rawType == CLS_CLASS) {
/* 1422 */       return CORE_TYPE_CLASS;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1428 */     Type[] args = ptype.getActualTypeArguments();
/* 1429 */     int paramCount = (args == null) ? 0 : args.length;
/*      */ 
/*      */ 
/*      */     
/* 1433 */     if (paramCount == 0) {
/* 1434 */       newBindings = EMPTY_BINDINGS;
/*      */     } else {
/* 1436 */       JavaType[] pt = new JavaType[paramCount];
/* 1437 */       for (int i = 0; i < paramCount; i++) {
/* 1438 */         pt[i] = _fromAny(context, args[i], parentBindings);
/*      */       }
/* 1440 */       newBindings = TypeBindings.create(rawType, pt);
/*      */     } 
/* 1442 */     return _fromClass(context, rawType, newBindings);
/*      */   }
/*      */ 
/*      */   
/*      */   protected JavaType _fromArrayType(ClassStack context, GenericArrayType type, TypeBindings bindings) {
/* 1447 */     JavaType elementType = _fromAny(context, type.getGenericComponentType(), bindings);
/* 1448 */     return ArrayType.construct(elementType, bindings);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromVariable(ClassStack context, TypeVariable<?> var, TypeBindings bindings) {
/* 1454 */     String name = var.getName();
/* 1455 */     JavaType type = bindings.findBoundType(name);
/* 1456 */     if (type != null) {
/* 1457 */       return type;
/*      */     }
/*      */ 
/*      */     
/* 1461 */     if (bindings.hasUnbound(name)) {
/* 1462 */       return CORE_TYPE_OBJECT;
/*      */     }
/* 1464 */     bindings = bindings.withUnboundVariable(name);
/*      */     
/* 1466 */     Type[] bounds = var.getBounds();
/* 1467 */     return _fromAny(context, bounds[0], bindings);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JavaType _fromWildcard(ClassStack context, WildcardType type, TypeBindings bindings) {
/* 1477 */     return _fromAny(context, type.getUpperBounds()[0], bindings);
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\TypeFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */