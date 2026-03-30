/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.Format;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.commons.lang3.Validate;
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
/*     */ abstract class FormatCache<F extends Format>
/*     */ {
/*     */   static final int NONE = -1;
/*  43 */   private final ConcurrentMap<MultipartKey, F> cInstanceCache = new ConcurrentHashMap<>(7);
/*     */ 
/*     */   
/*  46 */   private static final ConcurrentMap<MultipartKey, String> cDateTimeInstanceCache = new ConcurrentHashMap<>(7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public F getInstance() {
/*  56 */     return getDateTimeInstance(3, 3, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public F getInstance(String pattern, TimeZone timeZone, Locale locale) {
/*  72 */     Validate.notNull(pattern, "pattern must not be null", new Object[0]);
/*  73 */     if (timeZone == null) {
/*  74 */       timeZone = TimeZone.getDefault();
/*     */     }
/*  76 */     if (locale == null) {
/*  77 */       locale = Locale.getDefault();
/*     */     }
/*  79 */     MultipartKey key = new MultipartKey(new Object[] { pattern, timeZone, locale });
/*  80 */     Format format = (Format)this.cInstanceCache.get(key);
/*  81 */     if (format == null) {
/*  82 */       format = (Format)createInstance(pattern, timeZone, locale);
/*  83 */       Format format1 = (Format)this.cInstanceCache.putIfAbsent(key, (F)format);
/*  84 */       if (format1 != null)
/*     */       {
/*     */         
/*  87 */         format = format1;
/*     */       }
/*     */     } 
/*  90 */     return (F)format;
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
/*     */   protected abstract F createInstance(String paramString, TimeZone paramTimeZone, Locale paramLocale);
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
/*     */   private F getDateTimeInstance(Integer dateStyle, Integer timeStyle, TimeZone timeZone, Locale locale) {
/* 121 */     if (locale == null) {
/* 122 */       locale = Locale.getDefault();
/*     */     }
/* 124 */     String pattern = getPatternForStyle(dateStyle, timeStyle, locale);
/* 125 */     return getInstance(pattern, timeZone, locale);
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
/*     */   F getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale) {
/* 143 */     return getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), timeZone, locale);
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
/*     */   F getDateInstance(int dateStyle, TimeZone timeZone, Locale locale) {
/* 160 */     return getDateTimeInstance(Integer.valueOf(dateStyle), (Integer)null, timeZone, locale);
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
/*     */   F getTimeInstance(int timeStyle, TimeZone timeZone, Locale locale) {
/* 177 */     return getDateTimeInstance((Integer)null, Integer.valueOf(timeStyle), timeZone, locale);
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
/*     */   static String getPatternForStyle(Integer dateStyle, Integer timeStyle, Locale locale) {
/* 191 */     MultipartKey key = new MultipartKey(new Object[] { dateStyle, timeStyle, locale });
/*     */     
/* 193 */     String pattern = cDateTimeInstanceCache.get(key);
/* 194 */     if (pattern == null) {
/*     */       try {
/*     */         DateFormat formatter;
/* 197 */         if (dateStyle == null) {
/* 198 */           formatter = DateFormat.getTimeInstance(timeStyle.intValue(), locale);
/* 199 */         } else if (timeStyle == null) {
/* 200 */           formatter = DateFormat.getDateInstance(dateStyle.intValue(), locale);
/*     */         } else {
/* 202 */           formatter = DateFormat.getDateTimeInstance(dateStyle.intValue(), timeStyle.intValue(), locale);
/*     */         } 
/* 204 */         pattern = ((SimpleDateFormat)formatter).toPattern();
/* 205 */         String previous = cDateTimeInstanceCache.putIfAbsent(key, pattern);
/* 206 */         if (previous != null)
/*     */         {
/*     */ 
/*     */           
/* 210 */           pattern = previous;
/*     */         }
/* 212 */       } catch (ClassCastException ex) {
/* 213 */         throw new IllegalArgumentException("No date time pattern for locale: " + locale);
/*     */       } 
/*     */     }
/* 216 */     return pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MultipartKey
/*     */   {
/*     */     private final Object[] keys;
/*     */ 
/*     */     
/*     */     private int hashCode;
/*     */ 
/*     */ 
/*     */     
/*     */     MultipartKey(Object... keys) {
/* 232 */       this.keys = keys;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 243 */       return Arrays.equals(this.keys, ((MultipartKey)obj).keys);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 251 */       if (this.hashCode == 0) {
/* 252 */         int rc = 0;
/* 253 */         for (Object key : this.keys) {
/* 254 */           if (key != null) {
/* 255 */             rc = rc * 7 + key.hashCode();
/*     */           }
/*     */         } 
/* 258 */         this.hashCode = rc;
/*     */       } 
/* 260 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\time\FormatCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */