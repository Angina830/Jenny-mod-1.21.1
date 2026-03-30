/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public class LocaleUtils
/*     */ {
/*  41 */   private static final ConcurrentMap<String, List<Locale>> cLanguagesByCountry = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final ConcurrentMap<String, List<Locale>> cCountriesByLanguage = new ConcurrentHashMap<>();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale toLocale(String str) {
/*  91 */     if (str == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     if (str.isEmpty()) {
/*  95 */       return new Locale("", "");
/*     */     }
/*  97 */     if (str.contains("#")) {
/*  98 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 100 */     int len = str.length();
/* 101 */     if (len < 2) {
/* 102 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 104 */     char ch0 = str.charAt(0);
/* 105 */     if (ch0 == '_') {
/* 106 */       if (len < 3) {
/* 107 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 109 */       char ch1 = str.charAt(1);
/* 110 */       char ch2 = str.charAt(2);
/* 111 */       if (!Character.isUpperCase(ch1) || !Character.isUpperCase(ch2)) {
/* 112 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 114 */       if (len == 3) {
/* 115 */         return new Locale("", str.substring(1, 3));
/*     */       }
/* 117 */       if (len < 5) {
/* 118 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 120 */       if (str.charAt(3) != '_') {
/* 121 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 123 */       return new Locale("", str.substring(1, 3), str.substring(4));
/*     */     } 
/*     */     
/* 126 */     return parseLocale(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Locale parseLocale(String str) {
/* 137 */     if (isISO639LanguageCode(str)) {
/* 138 */       return new Locale(str);
/*     */     }
/*     */     
/* 141 */     String[] segments = str.split("_", -1);
/* 142 */     String language = segments[0];
/* 143 */     if (segments.length == 2) {
/* 144 */       String country = segments[1];
/* 145 */       if ((isISO639LanguageCode(language) && isISO3166CountryCode(country)) || 
/* 146 */         isNumericAreaCode(country)) {
/* 147 */         return new Locale(language, country);
/*     */       }
/* 149 */     } else if (segments.length == 3) {
/* 150 */       String country = segments[1];
/* 151 */       String variant = segments[2];
/* 152 */       if (isISO639LanguageCode(language) && (country
/* 153 */         .isEmpty() || isISO3166CountryCode(country) || isNumericAreaCode(country)) && 
/* 154 */         !variant.isEmpty()) {
/* 155 */         return new Locale(language, country, variant);
/*     */       }
/*     */     } 
/* 158 */     throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isISO639LanguageCode(String str) {
/* 168 */     return (StringUtils.isAllLowerCase(str) && (str.length() == 2 || str.length() == 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isISO3166CountryCode(String str) {
/* 178 */     return (StringUtils.isAllUpperCase(str) && str.length() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isNumericAreaCode(String str) {
/* 188 */     return (StringUtils.isNumeric(str) && str.length() == 3);
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
/*     */   public static List<Locale> localeLookupList(Locale locale) {
/* 205 */     return localeLookupList(locale, locale);
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
/*     */   public static List<Locale> localeLookupList(Locale locale, Locale defaultLocale) {
/* 227 */     List<Locale> list = new ArrayList<>(4);
/* 228 */     if (locale != null) {
/* 229 */       list.add(locale);
/* 230 */       if (!locale.getVariant().isEmpty()) {
/* 231 */         list.add(new Locale(locale.getLanguage(), locale.getCountry()));
/*     */       }
/* 233 */       if (!locale.getCountry().isEmpty()) {
/* 234 */         list.add(new Locale(locale.getLanguage(), ""));
/*     */       }
/* 236 */       if (!list.contains(defaultLocale)) {
/* 237 */         list.add(defaultLocale);
/*     */       }
/*     */     } 
/* 240 */     return Collections.unmodifiableList(list);
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
/*     */   public static List<Locale> availableLocaleList() {
/* 254 */     return SyncAvoid.AVAILABLE_LOCALE_LIST;
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
/*     */   public static Set<Locale> availableLocaleSet() {
/* 268 */     return SyncAvoid.AVAILABLE_LOCALE_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailableLocale(Locale locale) {
/* 279 */     return availableLocaleList().contains(locale);
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
/*     */   public static List<Locale> languagesByCountry(String countryCode) {
/* 293 */     if (countryCode == null) {
/* 294 */       return Collections.emptyList();
/*     */     }
/* 296 */     List<Locale> langs = cLanguagesByCountry.get(countryCode);
/* 297 */     if (langs == null) {
/* 298 */       langs = new ArrayList<>();
/* 299 */       List<Locale> locales = availableLocaleList();
/* 300 */       for (Locale locale : locales) {
/* 301 */         if (countryCode.equals(locale.getCountry()) && locale
/* 302 */           .getVariant().isEmpty()) {
/* 303 */           langs.add(locale);
/*     */         }
/*     */       } 
/* 306 */       langs = Collections.unmodifiableList(langs);
/* 307 */       cLanguagesByCountry.putIfAbsent(countryCode, langs);
/* 308 */       langs = cLanguagesByCountry.get(countryCode);
/*     */     } 
/* 310 */     return langs;
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
/*     */   public static List<Locale> countriesByLanguage(String languageCode) {
/* 324 */     if (languageCode == null) {
/* 325 */       return Collections.emptyList();
/*     */     }
/* 327 */     List<Locale> countries = cCountriesByLanguage.get(languageCode);
/* 328 */     if (countries == null) {
/* 329 */       countries = new ArrayList<>();
/* 330 */       List<Locale> locales = availableLocaleList();
/* 331 */       for (Locale locale : locales) {
/* 332 */         if (languageCode.equals(locale.getLanguage()) && 
/* 333 */           !locale.getCountry().isEmpty() && locale
/* 334 */           .getVariant().isEmpty()) {
/* 335 */           countries.add(locale);
/*     */         }
/*     */       } 
/* 338 */       countries = Collections.unmodifiableList(countries);
/* 339 */       cCountriesByLanguage.putIfAbsent(languageCode, countries);
/* 340 */       countries = cCountriesByLanguage.get(languageCode);
/*     */     } 
/* 342 */     return countries;
/*     */   }
/*     */ 
/*     */   
/*     */   static class SyncAvoid
/*     */   {
/*     */     private static final List<Locale> AVAILABLE_LOCALE_LIST;
/*     */     
/*     */     private static final Set<Locale> AVAILABLE_LOCALE_SET;
/*     */ 
/*     */     
/*     */     static {
/* 354 */       List<Locale> list = new ArrayList<>(Arrays.asList(Locale.getAvailableLocales()));
/* 355 */       AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(list);
/* 356 */       AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet<>(list));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\LocaleUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */