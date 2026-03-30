/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.text.Format;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import org.apache.commons.lang3.ObjectUtils;
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
/*     */ @Deprecated
/*     */ public class ExtendedMessageFormat
/*     */   extends MessageFormat
/*     */ {
/*     */   private static final long serialVersionUID = -2362048321261811743L;
/*     */   private static final int HASH_SEED = 31;
/*     */   private static final String DUMMY_PATTERN = "";
/*     */   private static final char START_FMT = ',';
/*     */   private static final char END_FE = '}';
/*     */   private static final char START_FE = '{';
/*     */   private static final char QUOTE = '\'';
/*     */   private String toPattern;
/*     */   private final Map<String, ? extends FormatFactory> registry;
/*     */   
/*     */   public ExtendedMessageFormat(String pattern) {
/*  93 */     this(pattern, Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedMessageFormat(String pattern, Locale locale) {
/* 104 */     this(pattern, locale, (Map<String, ? extends FormatFactory>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedMessageFormat(String pattern, Map<String, ? extends FormatFactory> registry) {
/* 115 */     this(pattern, Locale.getDefault(), registry);
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
/*     */   public ExtendedMessageFormat(String pattern, Locale locale, Map<String, ? extends FormatFactory> registry) {
/* 127 */     super("");
/* 128 */     setLocale(locale);
/* 129 */     this.registry = registry;
/* 130 */     applyPattern(pattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toPattern() {
/* 138 */     return this.toPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void applyPattern(String pattern) {
/* 148 */     if (this.registry == null) {
/* 149 */       super.applyPattern(pattern);
/* 150 */       this.toPattern = super.toPattern();
/*     */       return;
/*     */     } 
/* 153 */     ArrayList<Format> foundFormats = new ArrayList<>();
/* 154 */     ArrayList<String> foundDescriptions = new ArrayList<>();
/* 155 */     StringBuilder stripCustom = new StringBuilder(pattern.length());
/*     */     
/* 157 */     ParsePosition pos = new ParsePosition(0);
/* 158 */     char[] c = pattern.toCharArray();
/* 159 */     int fmtCount = 0;
/* 160 */     while (pos.getIndex() < pattern.length()) {
/* 161 */       int start, index; Format format; String formatDescription; switch (c[pos.getIndex()]) {
/*     */         case '\'':
/* 163 */           appendQuotedString(pattern, pos, stripCustom);
/*     */           continue;
/*     */         case '{':
/* 166 */           fmtCount++;
/* 167 */           seekNonWs(pattern, pos);
/* 168 */           start = pos.getIndex();
/* 169 */           index = readArgumentIndex(pattern, next(pos));
/* 170 */           stripCustom.append('{').append(index);
/* 171 */           seekNonWs(pattern, pos);
/* 172 */           format = null;
/* 173 */           formatDescription = null;
/* 174 */           if (c[pos.getIndex()] == ',') {
/* 175 */             formatDescription = parseFormatDescription(pattern, 
/* 176 */                 next(pos));
/* 177 */             format = getFormat(formatDescription);
/* 178 */             if (format == null) {
/* 179 */               stripCustom.append(',').append(formatDescription);
/*     */             }
/*     */           } 
/* 182 */           foundFormats.add(format);
/* 183 */           foundDescriptions.add((format == null) ? null : formatDescription);
/* 184 */           Validate.isTrue((foundFormats.size() == fmtCount));
/* 185 */           Validate.isTrue((foundDescriptions.size() == fmtCount));
/* 186 */           if (c[pos.getIndex()] != '}') {
/* 187 */             throw new IllegalArgumentException("Unreadable format element at position " + start);
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 192 */       stripCustom.append(c[pos.getIndex()]);
/* 193 */       next(pos);
/*     */     } 
/*     */     
/* 196 */     super.applyPattern(stripCustom.toString());
/* 197 */     this.toPattern = insertFormats(super.toPattern(), foundDescriptions);
/* 198 */     if (containsElements(foundFormats)) {
/* 199 */       Format[] origFormats = getFormats();
/*     */ 
/*     */       
/* 202 */       int i = 0;
/* 203 */       for (Iterator<Format> it = foundFormats.iterator(); it.hasNext(); i++) {
/* 204 */         Format f = it.next();
/* 205 */         if (f != null) {
/* 206 */           origFormats[i] = f;
/*     */         }
/*     */       } 
/* 209 */       super.setFormats(origFormats);
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
/*     */   public void setFormat(int formatElementIndex, Format newFormat) {
/* 222 */     throw new UnsupportedOperationException();
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
/*     */   public void setFormatByArgumentIndex(int argumentIndex, Format newFormat) {
/* 234 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormats(Format[] newFormats) {
/* 245 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormatsByArgumentIndex(Format[] newFormats) {
/* 256 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 267 */     if (obj == this) {
/* 268 */       return true;
/*     */     }
/* 270 */     if (obj == null) {
/* 271 */       return false;
/*     */     }
/* 273 */     if (!super.equals(obj)) {
/* 274 */       return false;
/*     */     }
/* 276 */     if (ObjectUtils.notEqual(getClass(), obj.getClass())) {
/* 277 */       return false;
/*     */     }
/* 279 */     ExtendedMessageFormat rhs = (ExtendedMessageFormat)obj;
/* 280 */     if (ObjectUtils.notEqual(this.toPattern, rhs.toPattern)) {
/* 281 */       return false;
/*     */     }
/* 283 */     return !ObjectUtils.notEqual(this.registry, rhs.registry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 291 */     int result = super.hashCode();
/* 292 */     result = 31 * result + Objects.hashCode(this.registry);
/* 293 */     result = 31 * result + Objects.hashCode(this.toPattern);
/* 294 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Format getFormat(String desc) {
/* 304 */     if (this.registry != null) {
/* 305 */       String name = desc;
/* 306 */       String args = null;
/* 307 */       int i = desc.indexOf(',');
/* 308 */       if (i > 0) {
/* 309 */         name = desc.substring(0, i).trim();
/* 310 */         args = desc.substring(i + 1).trim();
/*     */       } 
/* 312 */       FormatFactory factory = this.registry.get(name);
/* 313 */       if (factory != null) {
/* 314 */         return factory.getFormat(name, args, getLocale());
/*     */       }
/*     */     } 
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readArgumentIndex(String pattern, ParsePosition pos) {
/* 328 */     int start = pos.getIndex();
/* 329 */     seekNonWs(pattern, pos);
/* 330 */     StringBuilder result = new StringBuilder();
/* 331 */     boolean error = false;
/* 332 */     for (; !error && pos.getIndex() < pattern.length(); next(pos)) {
/* 333 */       char c = pattern.charAt(pos.getIndex());
/* 334 */       if (Character.isWhitespace(c)) {
/* 335 */         seekNonWs(pattern, pos);
/* 336 */         c = pattern.charAt(pos.getIndex());
/* 337 */         if (c != ',' && c != '}') {
/* 338 */           error = true;
/*     */           continue;
/*     */         } 
/*     */       } 
/* 342 */       if ((c == ',' || c == '}') && result.length() > 0) {
/*     */         try {
/* 344 */           return Integer.parseInt(result.toString());
/* 345 */         } catch (NumberFormatException numberFormatException) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 350 */       error = !Character.isDigit(c);
/* 351 */       result.append(c); continue;
/*     */     } 
/* 353 */     if (error) {
/* 354 */       throw new IllegalArgumentException("Invalid format argument index at position " + start + ": " + pattern
/*     */           
/* 356 */           .substring(start, pos.getIndex()));
/*     */     }
/* 358 */     throw new IllegalArgumentException("Unterminated format element at position " + start);
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
/*     */   private String parseFormatDescription(String pattern, ParsePosition pos) {
/* 370 */     int start = pos.getIndex();
/* 371 */     seekNonWs(pattern, pos);
/* 372 */     int text = pos.getIndex();
/* 373 */     int depth = 1;
/* 374 */     for (; pos.getIndex() < pattern.length(); next(pos)) {
/* 375 */       switch (pattern.charAt(pos.getIndex())) {
/*     */         case '{':
/* 377 */           depth++;
/*     */           break;
/*     */         case '}':
/* 380 */           depth--;
/* 381 */           if (depth == 0) {
/* 382 */             return pattern.substring(text, pos.getIndex());
/*     */           }
/*     */           break;
/*     */         case '\'':
/* 386 */           getQuotedString(pattern, pos);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 392 */     throw new IllegalArgumentException("Unterminated format element at position " + start);
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
/*     */   private String insertFormats(String pattern, ArrayList<String> customPatterns) {
/* 404 */     if (!containsElements(customPatterns)) {
/* 405 */       return pattern;
/*     */     }
/* 407 */     StringBuilder sb = new StringBuilder(pattern.length() * 2);
/* 408 */     ParsePosition pos = new ParsePosition(0);
/* 409 */     int fe = -1;
/* 410 */     int depth = 0;
/* 411 */     while (pos.getIndex() < pattern.length()) {
/* 412 */       char c = pattern.charAt(pos.getIndex());
/* 413 */       switch (c) {
/*     */         case '\'':
/* 415 */           appendQuotedString(pattern, pos, sb);
/*     */           continue;
/*     */         case '{':
/* 418 */           depth++;
/* 419 */           sb.append('{').append(readArgumentIndex(pattern, next(pos)));
/*     */           
/* 421 */           if (depth == 1) {
/* 422 */             fe++;
/* 423 */             String customPattern = customPatterns.get(fe);
/* 424 */             if (customPattern != null) {
/* 425 */               sb.append(',').append(customPattern);
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         case '}':
/* 430 */           depth--;
/*     */           break;
/*     */       } 
/* 433 */       sb.append(c);
/* 434 */       next(pos);
/*     */     } 
/*     */     
/* 437 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void seekNonWs(String pattern, ParsePosition pos) {
/* 447 */     int len = 0;
/* 448 */     char[] buffer = pattern.toCharArray();
/*     */     do {
/* 450 */       len = StrMatcher.splitMatcher().isMatch(buffer, pos.getIndex());
/* 451 */       pos.setIndex(pos.getIndex() + len);
/* 452 */     } while (len > 0 && pos.getIndex() < pattern.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ParsePosition next(ParsePosition pos) {
/* 462 */     pos.setIndex(pos.getIndex() + 1);
/* 463 */     return pos;
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
/*     */   private StringBuilder appendQuotedString(String pattern, ParsePosition pos, StringBuilder appendTo) {
/* 477 */     assert pattern.toCharArray()[pos.getIndex()] == '\'' : "Quoted string must start with quote character";
/*     */ 
/*     */ 
/*     */     
/* 481 */     if (appendTo != null) {
/* 482 */       appendTo.append('\'');
/*     */     }
/* 484 */     next(pos);
/*     */     
/* 486 */     int start = pos.getIndex();
/* 487 */     char[] c = pattern.toCharArray();
/* 488 */     int lastHold = start;
/* 489 */     for (int i = pos.getIndex(); i < pattern.length(); i++) {
/* 490 */       if (c[pos.getIndex()] == '\'') {
/* 491 */         next(pos);
/* 492 */         return (appendTo == null) ? null : appendTo.append(c, lastHold, pos
/* 493 */             .getIndex() - lastHold);
/*     */       } 
/* 495 */       next(pos);
/*     */     } 
/* 497 */     throw new IllegalArgumentException("Unterminated quoted string at position " + start);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getQuotedString(String pattern, ParsePosition pos) {
/* 508 */     appendQuotedString(pattern, pos, (StringBuilder)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean containsElements(Collection<?> coll) {
/* 517 */     if (coll == null || coll.isEmpty()) {
/* 518 */       return false;
/*     */     }
/* 520 */     for (Object name : coll) {
/* 521 */       if (name != null) {
/* 522 */         return true;
/*     */       }
/*     */     } 
/* 525 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\ExtendedMessageFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */