/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ public class EnumUtils
/*     */ {
/*     */   private static final String NULL_ELEMENTS_NOT_PERMITTED = "null elements not permitted";
/*     */   private static final String CANNOT_STORE_S_S_VALUES_IN_S_BITS = "Cannot store %s %s values in %s bits";
/*     */   private static final String S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE = "%s does not seem to be an Enum type";
/*     */   private static final String ENUM_CLASS_MUST_BE_DEFINED = "EnumClass must be defined.";
/*     */   
/*     */   private static <E extends Enum<E>> Class<E> asEnum(Class<E> enumClass) {
/*  51 */     Validate.notNull(enumClass, "EnumClass must be defined.", new Object[0]);
/*  52 */     Validate.isTrue(enumClass.isEnum(), "%s does not seem to be an Enum type", new Object[] { enumClass });
/*  53 */     return enumClass;
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
/*     */   private static <E extends Enum<E>> Class<E> checkBitVectorable(Class<E> enumClass) {
/*  66 */     Enum[] arrayOfEnum = asEnum(enumClass).getEnumConstants();
/*  67 */     Validate.isTrue((arrayOfEnum.length <= 64), "Cannot store %s %s values in %s bits", new Object[] {
/*  68 */           Integer.valueOf(arrayOfEnum.length), enumClass.getSimpleName(), Integer.valueOf(64)
/*     */         });
/*  70 */     return enumClass;
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
/*     */   
/*     */   @SafeVarargs
/*     */   public static <E extends Enum<E>> long generateBitVector(Class<E> enumClass, E... values) {
/*  92 */     Validate.noNullElements(values);
/*  93 */     return generateBitVector(enumClass, Arrays.asList(values));
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
/*     */ 
/*     */   
/*     */   public static <E extends Enum<E>> long generateBitVector(Class<E> enumClass, Iterable<? extends E> values) {
/* 115 */     checkBitVectorable(enumClass);
/* 116 */     Validate.notNull(values);
/* 117 */     long total = 0L;
/* 118 */     for (Enum enum_ : values) {
/* 119 */       Validate.notNull(enum_, "null elements not permitted", new Object[0]);
/* 120 */       total |= 1L << enum_.ordinal();
/*     */     } 
/* 122 */     return total;
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
/*     */   @SafeVarargs
/*     */   public static <E extends Enum<E>> long[] generateBitVectors(Class<E> enumClass, E... values) {
/* 143 */     asEnum(enumClass);
/* 144 */     Validate.noNullElements(values);
/* 145 */     EnumSet<E> condensed = EnumSet.noneOf(enumClass);
/* 146 */     Collections.addAll(condensed, values);
/* 147 */     long[] result = new long[(((Enum[])enumClass.getEnumConstants()).length - 1) / 64 + 1];
/* 148 */     for (Enum enum_ : condensed) {
/* 149 */       result[enum_.ordinal() / 64] = result[enum_.ordinal() / 64] | 1L << enum_.ordinal() % 64;
/*     */     }
/* 151 */     ArrayUtils.reverse(result);
/* 152 */     return result;
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
/*     */   public static <E extends Enum<E>> long[] generateBitVectors(Class<E> enumClass, Iterable<? extends E> values) {
/* 172 */     asEnum(enumClass);
/* 173 */     Validate.notNull(values);
/* 174 */     EnumSet<E> condensed = EnumSet.noneOf(enumClass);
/* 175 */     for (Enum enum_ : values) {
/* 176 */       Validate.notNull(enum_, "null elements not permitted", new Object[0]);
/* 177 */       condensed.add((E)enum_);
/*     */     } 
/* 179 */     long[] result = new long[(((Enum[])enumClass.getEnumConstants()).length - 1) / 64 + 1];
/* 180 */     for (Enum enum_ : condensed) {
/* 181 */       result[enum_.ordinal() / 64] = result[enum_.ordinal() / 64] | 1L << enum_.ordinal() % 64;
/*     */     }
/* 183 */     ArrayUtils.reverse(result);
/* 184 */     return result;
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
/*     */   public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName) {
/* 199 */     return getEnum(enumClass, enumName, null);
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
/*     */   public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName, E defaultEnum) {
/* 216 */     if (enumName == null) {
/* 217 */       return defaultEnum;
/*     */     }
/*     */     try {
/* 220 */       return Enum.valueOf(enumClass, enumName);
/* 221 */     } catch (IllegalArgumentException ex) {
/* 222 */       return defaultEnum;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Enum<E>> E getEnumIgnoreCase(Class<E> enumClass, String enumName) {
/* 239 */     return getEnumIgnoreCase(enumClass, enumName, null);
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
/*     */   public static <E extends Enum<E>> E getEnumIgnoreCase(Class<E> enumClass, String enumName, E defaultEnum) {
/* 256 */     if (enumName == null || !enumClass.isEnum()) {
/* 257 */       return defaultEnum;
/*     */     }
/* 259 */     for (Enum enum_ : (Enum[])enumClass.getEnumConstants()) {
/* 260 */       if (enum_.name().equalsIgnoreCase(enumName)) {
/* 261 */         return (E)enum_;
/*     */       }
/*     */     } 
/* 264 */     return defaultEnum;
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
/*     */   public static <E extends Enum<E>> List<E> getEnumList(Class<E> enumClass) {
/* 277 */     return new ArrayList<>(Arrays.asList(enumClass.getEnumConstants()));
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
/*     */   public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
/* 290 */     Map<String, E> map = new LinkedHashMap<>();
/* 291 */     for (Enum enum_ : (Enum[])enumClass.getEnumConstants()) {
/* 292 */       map.put(enum_.name(), (E)enum_);
/*     */     }
/* 294 */     return map;
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
/*     */   public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumName) {
/* 309 */     return (getEnum(enumClass, enumName) != null);
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
/*     */   public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(Class<E> enumClass, String enumName) {
/* 326 */     return (getEnumIgnoreCase(enumClass, enumName) != null);
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
/*     */   public static <E extends Enum<E>> EnumSet<E> processBitVector(Class<E> enumClass, long value) {
/* 343 */     checkBitVectorable(enumClass).getEnumConstants();
/* 344 */     return processBitVectors(enumClass, new long[] { value });
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
/*     */   public static <E extends Enum<E>> EnumSet<E> processBitVectors(Class<E> enumClass, long... values) {
/* 361 */     EnumSet<E> results = EnumSet.noneOf(asEnum(enumClass));
/* 362 */     long[] lvalues = ArrayUtils.clone(Validate.<long[]>notNull(values));
/* 363 */     ArrayUtils.reverse(lvalues);
/* 364 */     for (Enum enum_ : (Enum[])enumClass.getEnumConstants()) {
/* 365 */       int block = enum_.ordinal() / 64;
/* 366 */       if (block < lvalues.length && (lvalues[block] & 1L << enum_.ordinal() % 64) != 0L) {
/* 367 */         results.add((E)enum_);
/*     */       }
/*     */     } 
/* 370 */     return results;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\EnumUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */