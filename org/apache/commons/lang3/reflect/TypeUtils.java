/*      */ package org.apache.commons.lang3.reflect;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.GenericDeclaration;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.lang.reflect.WildcardType;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.commons.lang3.ClassUtils;
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.commons.lang3.builder.Builder;
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
/*      */ public class TypeUtils
/*      */ {
/*      */   public static class WildcardTypeBuilder
/*      */     implements Builder<WildcardType>
/*      */   {
/*      */     private Type[] upperBounds;
/*      */     private Type[] lowerBounds;
/*      */     
/*      */     private WildcardTypeBuilder() {}
/*      */     
/*      */     public WildcardTypeBuilder withUpperBounds(Type... bounds) {
/*   69 */       this.upperBounds = bounds;
/*   70 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WildcardTypeBuilder withLowerBounds(Type... bounds) {
/*   79 */       this.lowerBounds = bounds;
/*   80 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WildcardType build() {
/*   88 */       return new TypeUtils.WildcardTypeImpl(this.upperBounds, this.lowerBounds);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class GenericArrayTypeImpl
/*      */     implements GenericArrayType
/*      */   {
/*      */     private final Type componentType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private GenericArrayTypeImpl(Type componentType) {
/*  104 */       this.componentType = componentType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getGenericComponentType() {
/*  112 */       return this.componentType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  120 */       return TypeUtils.toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  128 */       return (obj == this || (obj instanceof GenericArrayType && TypeUtils.equals(this, (GenericArrayType)obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  136 */       int result = 1072;
/*  137 */       result |= this.componentType.hashCode();
/*  138 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ParameterizedTypeImpl
/*      */     implements ParameterizedType
/*      */   {
/*      */     private final Class<?> raw;
/*      */ 
/*      */     
/*      */     private final Type useOwner;
/*      */ 
/*      */     
/*      */     private final Type[] typeArguments;
/*      */ 
/*      */ 
/*      */     
/*      */     private ParameterizedTypeImpl(Class<?> rawClass, Type useOwner, Type[] typeArguments) {
/*  158 */       this.raw = rawClass;
/*  159 */       this.useOwner = useOwner;
/*  160 */       this.typeArguments = Arrays.<Type, Type>copyOf(typeArguments, typeArguments.length, Type[].class);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getRawType() {
/*  168 */       return this.raw;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getOwnerType() {
/*  176 */       return this.useOwner;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type[] getActualTypeArguments() {
/*  184 */       return (Type[])this.typeArguments.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  192 */       return TypeUtils.toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  200 */       return (obj == this || (obj instanceof ParameterizedType && TypeUtils.equals(this, (ParameterizedType)obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  208 */       int result = 1136;
/*  209 */       result |= this.raw.hashCode();
/*  210 */       result <<= 4;
/*  211 */       result |= Objects.hashCode(this.useOwner);
/*  212 */       result <<= 8;
/*  213 */       result |= Arrays.hashCode((Object[])this.typeArguments);
/*  214 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class WildcardTypeImpl
/*      */     implements WildcardType
/*      */   {
/*      */     private final Type[] upperBounds;
/*      */ 
/*      */     
/*      */     private final Type[] lowerBounds;
/*      */ 
/*      */ 
/*      */     
/*      */     private WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
/*  232 */       this.upperBounds = (Type[])ObjectUtils.defaultIfNull(upperBounds, ArrayUtils.EMPTY_TYPE_ARRAY);
/*  233 */       this.lowerBounds = (Type[])ObjectUtils.defaultIfNull(lowerBounds, ArrayUtils.EMPTY_TYPE_ARRAY);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type[] getUpperBounds() {
/*  241 */       return (Type[])this.upperBounds.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type[] getLowerBounds() {
/*  249 */       return (Type[])this.lowerBounds.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  257 */       return TypeUtils.toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  265 */       return (obj == this || (obj instanceof WildcardType && TypeUtils.equals(this, (WildcardType)obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  273 */       int result = 18688;
/*  274 */       result |= Arrays.hashCode((Object[])this.upperBounds);
/*  275 */       result <<= 8;
/*  276 */       result |= Arrays.hashCode((Object[])this.lowerBounds);
/*  277 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  285 */   public static final WildcardType WILDCARD_ALL = wildcardType().withUpperBounds(new Type[] { Object.class }).build();
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
/*      */   public static boolean isAssignable(Type type, Type toType) {
/*  309 */     return isAssignable(type, toType, (Map<TypeVariable<?>, Type>)null);
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
/*      */   private static boolean isAssignable(Type type, Type toType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  323 */     if (toType == null || toType instanceof Class) {
/*  324 */       return isAssignable(type, (Class)toType);
/*      */     }
/*      */     
/*  327 */     if (toType instanceof ParameterizedType) {
/*  328 */       return isAssignable(type, (ParameterizedType)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  331 */     if (toType instanceof GenericArrayType) {
/*  332 */       return isAssignable(type, (GenericArrayType)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  335 */     if (toType instanceof WildcardType) {
/*  336 */       return isAssignable(type, (WildcardType)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  339 */     if (toType instanceof TypeVariable) {
/*  340 */       return isAssignable(type, (TypeVariable)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  343 */     throw new IllegalStateException("found an unhandled type: " + toType);
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
/*      */   private static boolean isAssignable(Type type, Class<?> toClass) {
/*  355 */     if (type == null)
/*      */     {
/*  357 */       return (toClass == null || !toClass.isPrimitive());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  362 */     if (toClass == null) {
/*  363 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  367 */     if (toClass.equals(type)) {
/*  368 */       return true;
/*      */     }
/*      */     
/*  371 */     if (type instanceof Class)
/*      */     {
/*  373 */       return ClassUtils.isAssignable((Class)type, toClass);
/*      */     }
/*      */     
/*  376 */     if (type instanceof ParameterizedType)
/*      */     {
/*  378 */       return isAssignable(getRawType((ParameterizedType)type), toClass);
/*      */     }
/*      */ 
/*      */     
/*  382 */     if (type instanceof TypeVariable) {
/*      */ 
/*      */       
/*  385 */       for (Type bound : ((TypeVariable)type).getBounds()) {
/*  386 */         if (isAssignable(bound, toClass)) {
/*  387 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  391 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  396 */     if (type instanceof GenericArrayType) {
/*  397 */       return (toClass.equals(Object.class) || (toClass
/*  398 */         .isArray() && 
/*  399 */         isAssignable(((GenericArrayType)type).getGenericComponentType(), toClass
/*  400 */           .getComponentType())));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  405 */     if (type instanceof WildcardType) {
/*  406 */       return false;
/*      */     }
/*      */     
/*  409 */     throw new IllegalStateException("found an unhandled type: " + type);
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
/*      */   private static boolean isAssignable(Type type, ParameterizedType toParameterizedType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  423 */     if (type == null) {
/*  424 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  429 */     if (toParameterizedType == null) {
/*  430 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  434 */     if (toParameterizedType.equals(type)) {
/*  435 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  439 */     Class<?> toClass = getRawType(toParameterizedType);
/*      */ 
/*      */     
/*  442 */     Map<TypeVariable<?>, Type> fromTypeVarAssigns = getTypeArguments(type, toClass, (Map<TypeVariable<?>, Type>)null);
/*      */ 
/*      */     
/*  445 */     if (fromTypeVarAssigns == null) {
/*  446 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  452 */     if (fromTypeVarAssigns.isEmpty()) {
/*  453 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  457 */     Map<TypeVariable<?>, Type> toTypeVarAssigns = getTypeArguments(toParameterizedType, toClass, typeVarAssigns);
/*      */ 
/*      */ 
/*      */     
/*  461 */     for (TypeVariable<?> var : toTypeVarAssigns.keySet()) {
/*  462 */       Type toTypeArg = unrollVariableAssignments(var, toTypeVarAssigns);
/*  463 */       Type fromTypeArg = unrollVariableAssignments(var, fromTypeVarAssigns);
/*      */       
/*  465 */       if (toTypeArg == null && fromTypeArg instanceof Class) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  472 */       if (fromTypeArg != null && 
/*  473 */         !toTypeArg.equals(fromTypeArg) && (!(toTypeArg instanceof WildcardType) || 
/*  474 */         !isAssignable(fromTypeArg, toTypeArg, typeVarAssigns)))
/*      */       {
/*  476 */         return false;
/*      */       }
/*      */     } 
/*  479 */     return true;
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
/*      */   private static Type unrollVariableAssignments(TypeVariable<?> typeVariable, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*      */     Type result;
/*      */     while (true) {
/*  494 */       result = typeVarAssigns.get(typeVariable);
/*  495 */       if (result instanceof TypeVariable && !result.equals(typeVariable)) {
/*  496 */         typeVariable = (TypeVariable)result;
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  501 */     return result;
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
/*      */   private static boolean isAssignable(Type type, GenericArrayType toGenericArrayType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  516 */     if (type == null) {
/*  517 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  522 */     if (toGenericArrayType == null) {
/*  523 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  527 */     if (toGenericArrayType.equals(type)) {
/*  528 */       return true;
/*      */     }
/*      */     
/*  531 */     Type toComponentType = toGenericArrayType.getGenericComponentType();
/*      */     
/*  533 */     if (type instanceof Class) {
/*  534 */       Class<?> cls = (Class)type;
/*      */ 
/*      */       
/*  537 */       return (cls.isArray() && 
/*  538 */         isAssignable(cls.getComponentType(), toComponentType, typeVarAssigns));
/*      */     } 
/*      */     
/*  541 */     if (type instanceof GenericArrayType)
/*      */     {
/*  543 */       return isAssignable(((GenericArrayType)type).getGenericComponentType(), toComponentType, typeVarAssigns);
/*      */     }
/*      */ 
/*      */     
/*  547 */     if (type instanceof WildcardType) {
/*      */       
/*  549 */       for (Type bound : getImplicitUpperBounds((WildcardType)type)) {
/*  550 */         if (isAssignable(bound, toGenericArrayType)) {
/*  551 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  555 */       return false;
/*      */     } 
/*      */     
/*  558 */     if (type instanceof TypeVariable) {
/*      */ 
/*      */       
/*  561 */       for (Type bound : getImplicitBounds((TypeVariable)type)) {
/*  562 */         if (isAssignable(bound, toGenericArrayType)) {
/*  563 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  567 */       return false;
/*      */     } 
/*      */     
/*  570 */     if (type instanceof ParameterizedType)
/*      */     {
/*      */ 
/*      */       
/*  574 */       return false;
/*      */     }
/*      */     
/*  577 */     throw new IllegalStateException("found an unhandled type: " + type);
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
/*      */   private static boolean isAssignable(Type type, WildcardType toWildcardType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  592 */     if (type == null) {
/*  593 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  598 */     if (toWildcardType == null) {
/*  599 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  603 */     if (toWildcardType.equals(type)) {
/*  604 */       return true;
/*      */     }
/*      */     
/*  607 */     Type[] toUpperBounds = getImplicitUpperBounds(toWildcardType);
/*  608 */     Type[] toLowerBounds = getImplicitLowerBounds(toWildcardType);
/*      */     
/*  610 */     if (type instanceof WildcardType) {
/*  611 */       WildcardType wildcardType = (WildcardType)type;
/*  612 */       Type[] upperBounds = getImplicitUpperBounds(wildcardType);
/*  613 */       Type[] lowerBounds = getImplicitLowerBounds(wildcardType);
/*      */       
/*  615 */       for (Type toBound : toUpperBounds) {
/*      */ 
/*      */         
/*  618 */         toBound = substituteTypeVariables(toBound, typeVarAssigns);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  623 */         for (Type bound : upperBounds) {
/*  624 */           if (!isAssignable(bound, toBound, typeVarAssigns)) {
/*  625 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  630 */       for (Type toBound : toLowerBounds) {
/*      */ 
/*      */         
/*  633 */         toBound = substituteTypeVariables(toBound, typeVarAssigns);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  638 */         for (Type bound : lowerBounds) {
/*  639 */           if (!isAssignable(toBound, bound, typeVarAssigns)) {
/*  640 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*  644 */       return true;
/*      */     } 
/*      */     
/*  647 */     for (Type toBound : toUpperBounds) {
/*      */ 
/*      */       
/*  650 */       if (!isAssignable(type, substituteTypeVariables(toBound, typeVarAssigns), typeVarAssigns))
/*      */       {
/*  652 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  656 */     for (Type toBound : toLowerBounds) {
/*      */ 
/*      */       
/*  659 */       if (!isAssignable(substituteTypeVariables(toBound, typeVarAssigns), type, typeVarAssigns))
/*      */       {
/*  661 */         return false;
/*      */       }
/*      */     } 
/*  664 */     return true;
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
/*      */   private static boolean isAssignable(Type type, TypeVariable<?> toTypeVariable, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  679 */     if (type == null) {
/*  680 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  685 */     if (toTypeVariable == null) {
/*  686 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  690 */     if (toTypeVariable.equals(type)) {
/*  691 */       return true;
/*      */     }
/*      */     
/*  694 */     if (type instanceof TypeVariable) {
/*      */ 
/*      */ 
/*      */       
/*  698 */       Type[] bounds = getImplicitBounds((TypeVariable)type);
/*      */       
/*  700 */       for (Type bound : bounds) {
/*  701 */         if (isAssignable(bound, toTypeVariable, typeVarAssigns)) {
/*  702 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  707 */     if (type instanceof Class || type instanceof ParameterizedType || type instanceof GenericArrayType || type instanceof WildcardType)
/*      */     {
/*  709 */       return false;
/*      */     }
/*      */     
/*  712 */     throw new IllegalStateException("found an unhandled type: " + type);
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
/*      */   private static Type substituteTypeVariables(Type type, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  724 */     if (type instanceof TypeVariable && typeVarAssigns != null) {
/*  725 */       Type replacementType = typeVarAssigns.get(type);
/*      */       
/*  727 */       if (replacementType == null) {
/*  728 */         throw new IllegalArgumentException("missing assignment type for type variable " + type);
/*      */       }
/*      */       
/*  731 */       return replacementType;
/*      */     } 
/*  733 */     return type;
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
/*      */   public static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType type) {
/*  750 */     return getTypeArguments(type, getRawType(type), (Map<TypeVariable<?>, Type>)null);
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
/*      */   public static Map<TypeVariable<?>, Type> getTypeArguments(Type type, Class<?> toClass) {
/*  786 */     return getTypeArguments(type, toClass, (Map<TypeVariable<?>, Type>)null);
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
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(Type type, Class<?> toClass, Map<TypeVariable<?>, Type> subtypeVarAssigns) {
/*  799 */     if (type instanceof Class) {
/*  800 */       return getTypeArguments((Class)type, toClass, subtypeVarAssigns);
/*      */     }
/*      */     
/*  803 */     if (type instanceof ParameterizedType) {
/*  804 */       return getTypeArguments((ParameterizedType)type, toClass, subtypeVarAssigns);
/*      */     }
/*      */     
/*  807 */     if (type instanceof GenericArrayType) {
/*  808 */       return getTypeArguments(((GenericArrayType)type).getGenericComponentType(), 
/*  809 */           toClass.isArray() ? toClass.getComponentType() : toClass, subtypeVarAssigns);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  814 */     if (type instanceof WildcardType) {
/*  815 */       for (Type bound : getImplicitUpperBounds((WildcardType)type)) {
/*      */         
/*  817 */         if (isAssignable(bound, toClass)) {
/*  818 */           return getTypeArguments(bound, toClass, subtypeVarAssigns);
/*      */         }
/*      */       } 
/*      */       
/*  822 */       return null;
/*      */     } 
/*      */     
/*  825 */     if (type instanceof TypeVariable) {
/*  826 */       for (Type bound : getImplicitBounds((TypeVariable)type)) {
/*      */         
/*  828 */         if (isAssignable(bound, toClass)) {
/*  829 */           return getTypeArguments(bound, toClass, subtypeVarAssigns);
/*      */         }
/*      */       } 
/*      */       
/*  833 */       return null;
/*      */     } 
/*  835 */     throw new IllegalStateException("found an unhandled type: " + type);
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
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType parameterizedType, Class<?> toClass, Map<TypeVariable<?>, Type> subtypeVarAssigns) {
/*      */     Map<TypeVariable<?>, Type> typeVarAssigns;
/*  849 */     Class<?> cls = getRawType(parameterizedType);
/*      */ 
/*      */     
/*  852 */     if (!isAssignable(cls, toClass)) {
/*  853 */       return null;
/*      */     }
/*      */     
/*  856 */     Type ownerType = parameterizedType.getOwnerType();
/*      */ 
/*      */     
/*  859 */     if (ownerType instanceof ParameterizedType) {
/*      */       
/*  861 */       ParameterizedType parameterizedOwnerType = (ParameterizedType)ownerType;
/*  862 */       typeVarAssigns = getTypeArguments(parameterizedOwnerType, 
/*  863 */           getRawType(parameterizedOwnerType), subtypeVarAssigns);
/*      */     } else {
/*      */       
/*  866 */       typeVarAssigns = (subtypeVarAssigns == null) ? new HashMap<>() : new HashMap<>(subtypeVarAssigns);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  871 */     Type[] typeArgs = parameterizedType.getActualTypeArguments();
/*      */     
/*  873 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])cls.getTypeParameters();
/*      */ 
/*      */     
/*  876 */     for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/*  877 */       Type typeArg = typeArgs[i];
/*  878 */       typeVarAssigns.put(arrayOfTypeVariable[i], typeVarAssigns
/*      */           
/*  880 */           .getOrDefault(typeArg, typeArg));
/*      */     } 
/*      */ 
/*      */     
/*  884 */     if (toClass.equals(cls))
/*      */     {
/*  886 */       return typeVarAssigns;
/*      */     }
/*      */ 
/*      */     
/*  890 */     return getTypeArguments(getClosestParentType(cls, toClass), toClass, typeVarAssigns);
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
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(Class<?> cls, Class<?> toClass, Map<TypeVariable<?>, Type> subtypeVarAssigns) {
/*  904 */     if (!isAssignable(cls, toClass)) {
/*  905 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  909 */     if (cls.isPrimitive()) {
/*      */       
/*  911 */       if (toClass.isPrimitive())
/*      */       {
/*      */         
/*  914 */         return new HashMap<>();
/*      */       }
/*      */ 
/*      */       
/*  918 */       cls = ClassUtils.primitiveToWrapper(cls);
/*      */     } 
/*      */ 
/*      */     
/*  922 */     HashMap<TypeVariable<?>, Type> typeVarAssigns = (subtypeVarAssigns == null) ? new HashMap<>() : new HashMap<>(subtypeVarAssigns);
/*      */ 
/*      */ 
/*      */     
/*  926 */     if (toClass.equals(cls)) {
/*  927 */       return typeVarAssigns;
/*      */     }
/*      */ 
/*      */     
/*  931 */     return getTypeArguments(getClosestParentType(cls, toClass), toClass, typeVarAssigns);
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
/*      */   public static Map<TypeVariable<?>, Type> determineTypeArguments(Class<?> cls, ParameterizedType superType) {
/*  963 */     Validate.notNull(cls, "cls is null", new Object[0]);
/*  964 */     Validate.notNull(superType, "superType is null", new Object[0]);
/*      */     
/*  966 */     Class<?> superClass = getRawType(superType);
/*      */ 
/*      */     
/*  969 */     if (!isAssignable(cls, superClass)) {
/*  970 */       return null;
/*      */     }
/*      */     
/*  973 */     if (cls.equals(superClass)) {
/*  974 */       return getTypeArguments(superType, superClass, (Map<TypeVariable<?>, Type>)null);
/*      */     }
/*      */ 
/*      */     
/*  978 */     Type midType = getClosestParentType(cls, superClass);
/*      */ 
/*      */     
/*  981 */     if (midType instanceof Class) {
/*  982 */       return determineTypeArguments((Class)midType, superType);
/*      */     }
/*      */     
/*  985 */     ParameterizedType midParameterizedType = (ParameterizedType)midType;
/*  986 */     Class<?> midClass = getRawType(midParameterizedType);
/*      */ 
/*      */     
/*  989 */     Map<TypeVariable<?>, Type> typeVarAssigns = determineTypeArguments(midClass, superType);
/*      */     
/*  991 */     mapTypeVariablesToArguments(cls, midParameterizedType, typeVarAssigns);
/*      */     
/*  993 */     return typeVarAssigns;
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
/*      */   private static <T> void mapTypeVariablesToArguments(Class<T> cls, ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/* 1007 */     Type ownerType = parameterizedType.getOwnerType();
/*      */     
/* 1009 */     if (ownerType instanceof ParameterizedType)
/*      */     {
/* 1011 */       mapTypeVariablesToArguments(cls, (ParameterizedType)ownerType, typeVarAssigns);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1018 */     Type[] typeArgs = parameterizedType.getActualTypeArguments();
/*      */ 
/*      */ 
/*      */     
/* 1022 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])getRawType(parameterizedType).getTypeParameters();
/*      */ 
/*      */     
/* 1025 */     List<TypeVariable<Class<T>>> typeVarList = Arrays.asList(cls
/* 1026 */         .getTypeParameters());
/*      */     
/* 1028 */     for (int i = 0; i < typeArgs.length; i++) {
/* 1029 */       TypeVariable<?> typeVar = arrayOfTypeVariable[i];
/* 1030 */       Type typeArg = typeArgs[i];
/*      */ 
/*      */       
/* 1033 */       if (typeVarList.contains(typeArg) && typeVarAssigns
/*      */ 
/*      */         
/* 1036 */         .containsKey(typeVar))
/*      */       {
/* 1038 */         typeVarAssigns.put((TypeVariable)typeArg, typeVarAssigns.get(typeVar));
/*      */       }
/*      */     } 
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
/*      */   private static Type getClosestParentType(Class<?> cls, Class<?> superClass) {
/* 1053 */     if (superClass.isInterface()) {
/*      */       
/* 1055 */       Type[] interfaceTypes = cls.getGenericInterfaces();
/*      */       
/* 1057 */       Type genericInterface = null;
/*      */ 
/*      */       
/* 1060 */       for (Type midType : interfaceTypes) {
/* 1061 */         Class<?> midClass = null;
/*      */         
/* 1063 */         if (midType instanceof ParameterizedType) {
/* 1064 */           midClass = getRawType((ParameterizedType)midType);
/* 1065 */         } else if (midType instanceof Class) {
/* 1066 */           midClass = (Class)midType;
/*      */         } else {
/* 1068 */           throw new IllegalStateException("Unexpected generic interface type found: " + midType);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1074 */         if (isAssignable(midClass, superClass) && 
/* 1075 */           isAssignable(genericInterface, midClass)) {
/* 1076 */           genericInterface = midType;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1081 */       if (genericInterface != null) {
/* 1082 */         return genericInterface;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1088 */     return cls.getGenericSuperclass();
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
/*      */   public static boolean isInstance(Object value, Type type) {
/* 1100 */     if (type == null) {
/* 1101 */       return false;
/*      */     }
/*      */     
/* 1104 */     return (value == null) ? ((!(type instanceof Class) || !((Class)type).isPrimitive())) : 
/* 1105 */       isAssignable(value.getClass(), type, (Map<TypeVariable<?>, Type>)null);
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
/*      */   public static Type[] normalizeUpperBounds(Type[] bounds) {
/* 1130 */     Validate.notNull(bounds, "null value specified for bounds array", new Object[0]);
/*      */     
/* 1132 */     if (bounds.length < 2) {
/* 1133 */       return bounds;
/*      */     }
/*      */     
/* 1136 */     Set<Type> types = new HashSet<>(bounds.length);
/*      */     
/* 1138 */     for (Type type1 : bounds) {
/* 1139 */       boolean subtypeFound = false;
/*      */       
/* 1141 */       for (Type type2 : bounds) {
/* 1142 */         if (type1 != type2 && isAssignable(type2, type1, (Map<TypeVariable<?>, Type>)null)) {
/* 1143 */           subtypeFound = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1148 */       if (!subtypeFound) {
/* 1149 */         types.add(type1);
/*      */       }
/*      */     } 
/*      */     
/* 1153 */     return types.<Type>toArray(ArrayUtils.EMPTY_TYPE_ARRAY);
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
/*      */   public static Type[] getImplicitBounds(TypeVariable<?> typeVariable) {
/* 1166 */     Validate.notNull(typeVariable, "typeVariable is null", new Object[0]);
/* 1167 */     Type[] bounds = typeVariable.getBounds();
/*      */     
/* 1169 */     (new Type[1])[0] = Object.class; return (bounds.length == 0) ? new Type[1] : normalizeUpperBounds(bounds);
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
/*      */   public static Type[] getImplicitUpperBounds(WildcardType wildcardType) {
/* 1183 */     Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
/* 1184 */     Type[] bounds = wildcardType.getUpperBounds();
/*      */     
/* 1186 */     (new Type[1])[0] = Object.class; return (bounds.length == 0) ? new Type[1] : normalizeUpperBounds(bounds);
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
/*      */   public static Type[] getImplicitLowerBounds(WildcardType wildcardType) {
/* 1199 */     Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
/* 1200 */     Type[] bounds = wildcardType.getLowerBounds();
/*      */     
/* 1202 */     (new Type[1])[0] = null; return (bounds.length == 0) ? new Type[1] : bounds;
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
/*      */   public static boolean typesSatisfyVariables(Map<TypeVariable<?>, Type> typeVarAssigns) {
/* 1219 */     Validate.notNull(typeVarAssigns, "typeVarAssigns is null", new Object[0]);
/*      */ 
/*      */     
/* 1222 */     for (Map.Entry<TypeVariable<?>, Type> entry : typeVarAssigns.entrySet()) {
/* 1223 */       TypeVariable<?> typeVar = entry.getKey();
/* 1224 */       Type type = entry.getValue();
/*      */       
/* 1226 */       for (Type bound : getImplicitBounds(typeVar)) {
/* 1227 */         if (!isAssignable(type, substituteTypeVariables(bound, typeVarAssigns), typeVarAssigns))
/*      */         {
/* 1229 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 1233 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> getRawType(ParameterizedType parameterizedType) {
/* 1244 */     Type rawType = parameterizedType.getRawType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1251 */     if (!(rawType instanceof Class)) {
/* 1252 */       throw new IllegalStateException("Wait... What!? Type of rawType: " + rawType);
/*      */     }
/*      */     
/* 1255 */     return (Class)rawType;
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
/*      */   public static Class<?> getRawType(Type type, Type assigningType) {
/* 1271 */     if (type instanceof Class)
/*      */     {
/* 1273 */       return (Class)type;
/*      */     }
/*      */     
/* 1276 */     if (type instanceof ParameterizedType)
/*      */     {
/* 1278 */       return getRawType((ParameterizedType)type);
/*      */     }
/*      */     
/* 1281 */     if (type instanceof TypeVariable) {
/* 1282 */       if (assigningType == null) {
/* 1283 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1287 */       Object genericDeclaration = ((TypeVariable)type).getGenericDeclaration();
/*      */ 
/*      */ 
/*      */       
/* 1291 */       if (!(genericDeclaration instanceof Class)) {
/* 1292 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1297 */       Map<TypeVariable<?>, Type> typeVarAssigns = getTypeArguments(assigningType, (Class)genericDeclaration);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1302 */       if (typeVarAssigns == null) {
/* 1303 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1307 */       Type typeArgument = typeVarAssigns.get(type);
/*      */       
/* 1309 */       if (typeArgument == null) {
/* 1310 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1314 */       return getRawType(typeArgument, assigningType);
/*      */     } 
/*      */     
/* 1317 */     if (type instanceof GenericArrayType) {
/*      */       
/* 1319 */       Class<?> rawComponentType = getRawType(((GenericArrayType)type)
/* 1320 */           .getGenericComponentType(), assigningType);
/*      */ 
/*      */       
/* 1323 */       return Array.newInstance(rawComponentType, 0).getClass();
/*      */     } 
/*      */ 
/*      */     
/* 1327 */     if (type instanceof WildcardType) {
/* 1328 */       return null;
/*      */     }
/*      */     
/* 1331 */     throw new IllegalArgumentException("unknown type: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isArrayType(Type type) {
/* 1340 */     return (type instanceof GenericArrayType || (type instanceof Class && ((Class)type).isArray()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type getArrayComponentType(Type type) {
/* 1349 */     if (type instanceof Class) {
/* 1350 */       Class<?> cls = (Class)type;
/* 1351 */       return cls.isArray() ? cls.getComponentType() : null;
/*      */     } 
/* 1353 */     if (type instanceof GenericArrayType) {
/* 1354 */       return ((GenericArrayType)type).getGenericComponentType();
/*      */     }
/* 1356 */     return null;
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
/*      */   public static Type unrollVariables(Map<TypeVariable<?>, Type> typeArguments, Type type) {
/* 1368 */     if (typeArguments == null) {
/* 1369 */       typeArguments = Collections.emptyMap();
/*      */     }
/* 1371 */     if (containsTypeVariables(type)) {
/* 1372 */       if (type instanceof TypeVariable) {
/* 1373 */         return unrollVariables(typeArguments, typeArguments.get(type));
/*      */       }
/* 1375 */       if (type instanceof ParameterizedType) {
/* 1376 */         Map<TypeVariable<?>, Type> parameterizedTypeArguments; ParameterizedType p = (ParameterizedType)type;
/*      */         
/* 1378 */         if (p.getOwnerType() == null) {
/* 1379 */           parameterizedTypeArguments = typeArguments;
/*      */         } else {
/* 1381 */           parameterizedTypeArguments = new HashMap<>(typeArguments);
/* 1382 */           parameterizedTypeArguments.putAll(getTypeArguments(p));
/*      */         } 
/* 1384 */         Type[] args = p.getActualTypeArguments();
/* 1385 */         for (int i = 0; i < args.length; i++) {
/* 1386 */           Type unrolled = unrollVariables(parameterizedTypeArguments, args[i]);
/* 1387 */           if (unrolled != null) {
/* 1388 */             args[i] = unrolled;
/*      */           }
/*      */         } 
/* 1391 */         return parameterizeWithOwner(p.getOwnerType(), (Class)p.getRawType(), args);
/*      */       } 
/* 1393 */       if (type instanceof WildcardType) {
/* 1394 */         WildcardType wild = (WildcardType)type;
/* 1395 */         return wildcardType().withUpperBounds(unrollBounds(typeArguments, wild.getUpperBounds()))
/* 1396 */           .withLowerBounds(unrollBounds(typeArguments, wild.getLowerBounds())).build();
/*      */       } 
/*      */     } 
/* 1399 */     return type;
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
/*      */   private static Type[] unrollBounds(Map<TypeVariable<?>, Type> typeArguments, Type[] bounds) {
/* 1411 */     Type[] result = bounds;
/* 1412 */     int i = 0;
/* 1413 */     for (; i < result.length; i++) {
/* 1414 */       Type unrolled = unrollVariables(typeArguments, result[i]);
/* 1415 */       if (unrolled == null) {
/* 1416 */         result = (Type[])ArrayUtils.remove((Object[])result, i--);
/*      */       } else {
/* 1418 */         result[i] = unrolled;
/*      */       } 
/*      */     } 
/* 1421 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean containsTypeVariables(Type type) {
/* 1432 */     if (type instanceof TypeVariable) {
/* 1433 */       return true;
/*      */     }
/* 1435 */     if (type instanceof Class) {
/* 1436 */       return ((((Class)type).getTypeParameters()).length > 0);
/*      */     }
/* 1438 */     if (type instanceof ParameterizedType) {
/* 1439 */       for (Type arg : ((ParameterizedType)type).getActualTypeArguments()) {
/* 1440 */         if (containsTypeVariables(arg)) {
/* 1441 */           return true;
/*      */         }
/*      */       } 
/* 1444 */       return false;
/*      */     } 
/* 1446 */     if (type instanceof WildcardType) {
/* 1447 */       WildcardType wild = (WildcardType)type;
/* 1448 */       return (containsTypeVariables(getImplicitLowerBounds(wild)[0]) || 
/* 1449 */         containsTypeVariables(getImplicitUpperBounds(wild)[0]));
/*      */     } 
/* 1451 */     return false;
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
/*      */   public static final ParameterizedType parameterize(Class<?> rawClass, Type... typeArguments) {
/* 1463 */     return parameterizeWithOwner((Type)null, rawClass, typeArguments);
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
/*      */   public static final ParameterizedType parameterize(Class<?> rawClass, Map<TypeVariable<?>, Type> typeArgMappings) {
/* 1476 */     Validate.notNull(rawClass, "raw class is null", new Object[0]);
/* 1477 */     Validate.notNull(typeArgMappings, "typeArgMappings is null", new Object[0]);
/* 1478 */     return parameterizeWithOwner((Type)null, rawClass, 
/* 1479 */         extractTypeArgumentsFrom(typeArgMappings, (TypeVariable<?>[])rawClass.getTypeParameters()));
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
/*      */   public static final ParameterizedType parameterizeWithOwner(Type owner, Class<?> rawClass, Type... typeArguments) {
/*      */     Type useOwner;
/* 1494 */     Validate.notNull(rawClass, "raw class is null", new Object[0]);
/*      */     
/* 1496 */     if (rawClass.getEnclosingClass() == null) {
/* 1497 */       Validate.isTrue((owner == null), "no owner allowed for top-level %s", new Object[] { rawClass });
/* 1498 */       useOwner = null;
/* 1499 */     } else if (owner == null) {
/* 1500 */       useOwner = rawClass.getEnclosingClass();
/*      */     } else {
/* 1502 */       Validate.isTrue(isAssignable(owner, rawClass.getEnclosingClass()), "%s is invalid owner type for parameterized %s", new Object[] { owner, rawClass });
/*      */       
/* 1504 */       useOwner = owner;
/*      */     } 
/* 1506 */     Validate.noNullElements((Object[])typeArguments, "null type argument at index %s", new Object[0]);
/* 1507 */     Validate.isTrue(((rawClass.getTypeParameters()).length == typeArguments.length), "invalid number of type parameters specified: expected %d, got %d", new Object[] {
/* 1508 */           Integer.valueOf((rawClass.getTypeParameters()).length), 
/* 1509 */           Integer.valueOf(typeArguments.length)
/*      */         });
/* 1511 */     return new ParameterizedTypeImpl(rawClass, useOwner, typeArguments);
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
/*      */   public static final ParameterizedType parameterizeWithOwner(Type owner, Class<?> rawClass, Map<TypeVariable<?>, Type> typeArgMappings) {
/* 1525 */     Validate.notNull(rawClass, "raw class is null", new Object[0]);
/* 1526 */     Validate.notNull(typeArgMappings, "typeArgMappings is null", new Object[0]);
/* 1527 */     return parameterizeWithOwner(owner, rawClass, 
/* 1528 */         extractTypeArgumentsFrom(typeArgMappings, (TypeVariable<?>[])rawClass.getTypeParameters()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type[] extractTypeArgumentsFrom(Map<TypeVariable<?>, Type> mappings, TypeVariable<?>[] variables) {
/* 1538 */     Type[] result = new Type[variables.length];
/* 1539 */     int index = 0;
/* 1540 */     for (TypeVariable<?> var : variables) {
/* 1541 */       Validate.isTrue(mappings.containsKey(var), "missing argument mapping for %s", new Object[] { toString(var) });
/* 1542 */       result[index++] = mappings.get(var);
/*      */     } 
/* 1544 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WildcardTypeBuilder wildcardType() {
/* 1553 */     return new WildcardTypeBuilder();
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
/*      */   public static GenericArrayType genericArrayType(Type componentType) {
/* 1565 */     return new GenericArrayTypeImpl((Type)Validate.notNull(componentType, "componentType is null", new Object[0]));
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
/*      */   public static boolean equals(Type type1, Type type2) {
/* 1577 */     if (Objects.equals(type1, type2)) {
/* 1578 */       return true;
/*      */     }
/* 1580 */     if (type1 instanceof ParameterizedType) {
/* 1581 */       return equals((ParameterizedType)type1, type2);
/*      */     }
/* 1583 */     if (type1 instanceof GenericArrayType) {
/* 1584 */       return equals((GenericArrayType)type1, type2);
/*      */     }
/* 1586 */     if (type1 instanceof WildcardType) {
/* 1587 */       return equals((WildcardType)type1, type2);
/*      */     }
/* 1589 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(ParameterizedType parameterizedType, Type type) {
/* 1600 */     if (type instanceof ParameterizedType) {
/* 1601 */       ParameterizedType other = (ParameterizedType)type;
/* 1602 */       if (equals(parameterizedType.getRawType(), other.getRawType()) && 
/* 1603 */         equals(parameterizedType.getOwnerType(), other.getOwnerType())) {
/* 1604 */         return equals(parameterizedType.getActualTypeArguments(), other.getActualTypeArguments());
/*      */       }
/*      */     } 
/* 1607 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(GenericArrayType genericArrayType, Type type) {
/* 1618 */     return (type instanceof GenericArrayType && 
/* 1619 */       equals(genericArrayType.getGenericComponentType(), ((GenericArrayType)type).getGenericComponentType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(WildcardType wildcardType, Type type) {
/* 1630 */     if (type instanceof WildcardType) {
/* 1631 */       WildcardType other = (WildcardType)type;
/* 1632 */       return (equals(getImplicitLowerBounds(wildcardType), getImplicitLowerBounds(other)) && 
/* 1633 */         equals(getImplicitUpperBounds(wildcardType), getImplicitUpperBounds(other)));
/*      */     } 
/* 1635 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(Type[] type1, Type[] type2) {
/* 1646 */     if (type1.length == type2.length) {
/* 1647 */       for (int i = 0; i < type1.length; i++) {
/* 1648 */         if (!equals(type1[i], type2[i])) {
/* 1649 */           return false;
/*      */         }
/*      */       } 
/* 1652 */       return true;
/*      */     } 
/* 1654 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Type type) {
/* 1665 */     Validate.notNull(type);
/* 1666 */     if (type instanceof Class) {
/* 1667 */       return classToString((Class)type);
/*      */     }
/* 1669 */     if (type instanceof ParameterizedType) {
/* 1670 */       return parameterizedTypeToString((ParameterizedType)type);
/*      */     }
/* 1672 */     if (type instanceof WildcardType) {
/* 1673 */       return wildcardTypeToString((WildcardType)type);
/*      */     }
/* 1675 */     if (type instanceof TypeVariable) {
/* 1676 */       return typeVariableToString((TypeVariable)type);
/*      */     }
/* 1678 */     if (type instanceof GenericArrayType) {
/* 1679 */       return genericArrayTypeToString((GenericArrayType)type);
/*      */     }
/* 1681 */     throw new IllegalArgumentException(ObjectUtils.identityToString(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toLongString(TypeVariable<?> var) {
/* 1692 */     Validate.notNull(var, "var is null", new Object[0]);
/* 1693 */     StringBuilder buf = new StringBuilder();
/* 1694 */     GenericDeclaration d = (GenericDeclaration)var.getGenericDeclaration();
/* 1695 */     if (d instanceof Class) {
/* 1696 */       Class<?> c = (Class)d;
/*      */       while (true) {
/* 1698 */         if (c.getEnclosingClass() == null) {
/* 1699 */           buf.insert(0, c.getName());
/*      */           break;
/*      */         } 
/* 1702 */         buf.insert(0, c.getSimpleName()).insert(0, '.');
/* 1703 */         c = c.getEnclosingClass();
/*      */       } 
/* 1705 */     } else if (d instanceof Type) {
/* 1706 */       buf.append(toString((Type)d));
/*      */     } else {
/* 1708 */       buf.append(d);
/*      */     } 
/* 1710 */     return buf.append(':').append(typeVariableToString(var)).toString();
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
/*      */   public static <T> Typed<T> wrap(Type type) {
/* 1722 */     return () -> type;
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
/*      */   public static <T> Typed<T> wrap(Class<T> type) {
/* 1734 */     return wrap(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String classToString(Class<?> cls) {
/* 1744 */     if (cls.isArray()) {
/* 1745 */       return toString(cls.getComponentType()) + "[]";
/*      */     }
/*      */     
/* 1748 */     StringBuilder buf = new StringBuilder();
/*      */     
/* 1750 */     if (cls.getEnclosingClass() != null) {
/* 1751 */       buf.append(classToString(cls.getEnclosingClass())).append('.').append(cls.getSimpleName());
/*      */     } else {
/* 1753 */       buf.append(cls.getName());
/*      */     } 
/* 1755 */     if ((cls.getTypeParameters()).length > 0) {
/* 1756 */       buf.append('<');
/* 1757 */       appendAllTo(buf, ", ", (Object[])cls.getTypeParameters());
/* 1758 */       buf.append('>');
/*      */     } 
/* 1760 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String typeVariableToString(TypeVariable<?> typeVariable) {
/* 1770 */     StringBuilder buf = new StringBuilder(typeVariable.getName());
/* 1771 */     Type[] bounds = typeVariable.getBounds();
/* 1772 */     if (bounds.length > 0 && (bounds.length != 1 || !Object.class.equals(bounds[0]))) {
/* 1773 */       buf.append(" extends ");
/* 1774 */       appendAllTo(buf, " & ", typeVariable.getBounds());
/*      */     } 
/* 1776 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parameterizedTypeToString(ParameterizedType parameterizedType) {
/* 1786 */     StringBuilder builder = new StringBuilder();
/*      */     
/* 1788 */     Type useOwner = parameterizedType.getOwnerType();
/* 1789 */     Class<?> raw = (Class)parameterizedType.getRawType();
/*      */     
/* 1791 */     if (useOwner == null) {
/* 1792 */       builder.append(raw.getName());
/*      */     } else {
/* 1794 */       if (useOwner instanceof Class) {
/* 1795 */         builder.append(((Class)useOwner).getName());
/*      */       } else {
/* 1797 */         builder.append(useOwner.toString());
/*      */       } 
/* 1799 */       builder.append('.').append(raw.getSimpleName());
/*      */     } 
/*      */     
/* 1802 */     int[] recursiveTypeIndexes = findRecursiveTypes(parameterizedType);
/*      */     
/* 1804 */     if (recursiveTypeIndexes.length > 0) {
/* 1805 */       appendRecursiveTypes(builder, recursiveTypeIndexes, parameterizedType.getActualTypeArguments());
/*      */     } else {
/* 1807 */       appendAllTo(builder.append('<'), ", ", parameterizedType.getActualTypeArguments()).append('>');
/*      */     } 
/*      */     
/* 1810 */     return builder.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void appendRecursiveTypes(StringBuilder builder, int[] recursiveTypeIndexes, Type[] argumentTypes) {
/* 1815 */     for (int i = 0; i < recursiveTypeIndexes.length; i++) {
/* 1816 */       appendAllTo(builder.append('<'), ", ", new String[] { argumentTypes[i].toString() }).append('>');
/*      */     } 
/*      */     
/* 1819 */     Type[] argumentsFiltered = (Type[])ArrayUtils.removeAll((Object[])argumentTypes, recursiveTypeIndexes);
/*      */     
/* 1821 */     if (argumentsFiltered.length > 0) {
/* 1822 */       appendAllTo(builder.append('<'), ", ", argumentsFiltered).append('>');
/*      */     }
/*      */   }
/*      */   
/*      */   private static int[] findRecursiveTypes(ParameterizedType parameterizedType) {
/* 1827 */     Type[] filteredArgumentTypes = Arrays.<Type>copyOf(parameterizedType.getActualTypeArguments(), (parameterizedType
/* 1828 */         .getActualTypeArguments()).length);
/* 1829 */     int[] indexesToRemove = new int[0];
/* 1830 */     for (int i = 0; i < filteredArgumentTypes.length; i++) {
/* 1831 */       if (filteredArgumentTypes[i] instanceof TypeVariable && 
/* 1832 */         containsVariableTypeSameParametrizedTypeBound((TypeVariable)filteredArgumentTypes[i], parameterizedType))
/*      */       {
/* 1834 */         indexesToRemove = ArrayUtils.add(indexesToRemove, i);
/*      */       }
/*      */     } 
/*      */     
/* 1838 */     return indexesToRemove;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean containsVariableTypeSameParametrizedTypeBound(TypeVariable<?> typeVariable, ParameterizedType parameterizedType) {
/* 1843 */     return ArrayUtils.contains((Object[])typeVariable.getBounds(), parameterizedType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String wildcardTypeToString(WildcardType wildcardType) {
/* 1853 */     StringBuilder buf = (new StringBuilder()).append('?');
/* 1854 */     Type[] lowerBounds = wildcardType.getLowerBounds();
/* 1855 */     Type[] upperBounds = wildcardType.getUpperBounds();
/* 1856 */     if (lowerBounds.length > 1 || (lowerBounds.length == 1 && lowerBounds[0] != null)) {
/* 1857 */       appendAllTo(buf.append(" super "), " & ", lowerBounds);
/* 1858 */     } else if (upperBounds.length > 1 || (upperBounds.length == 1 && !Object.class.equals(upperBounds[0]))) {
/* 1859 */       appendAllTo(buf.append(" extends "), " & ", upperBounds);
/*      */     } 
/* 1861 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String genericArrayTypeToString(GenericArrayType genericArrayType) {
/* 1871 */     return String.format("%s[]", new Object[] { toString(genericArrayType.getGenericComponentType()) });
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
/*      */   private static <T> StringBuilder appendAllTo(StringBuilder builder, String sep, T... types) {
/* 1884 */     Validate.notEmpty(Validate.noNullElements((Object[])types));
/* 1885 */     if (types.length > 0) {
/* 1886 */       builder.append(toString(types[0]));
/* 1887 */       for (int i = 1; i < types.length; i++) {
/* 1888 */         builder.append(sep).append(toString(types[i]));
/*      */       }
/*      */     } 
/* 1891 */     return builder;
/*      */   }
/*      */   
/*      */   private static <T> String toString(T object) {
/* 1895 */     return (object instanceof Type) ? toString((Type)object) : object.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\reflect\TypeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */