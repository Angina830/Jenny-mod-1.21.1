/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CharRange
/*     */   implements Iterable<Character>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8270183163158333422L;
/*     */   private final char start;
/*     */   private final char end;
/*     */   private final boolean negated;
/*     */   private transient String iToString;
/*     */   
/*     */   private CharRange(char start, char end, boolean negated) {
/*  68 */     if (start > end) {
/*  69 */       char temp = start;
/*  70 */       start = end;
/*  71 */       end = temp;
/*     */     } 
/*     */     
/*  74 */     this.start = start;
/*  75 */     this.end = end;
/*  76 */     this.negated = negated;
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
/*     */   public static CharRange is(char ch) {
/*  88 */     return new CharRange(ch, ch, false);
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
/*     */   public static CharRange isNot(char ch) {
/* 100 */     return new CharRange(ch, ch, true);
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
/*     */   public static CharRange isIn(char start, char end) {
/* 113 */     return new CharRange(start, end, false);
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
/*     */   public static CharRange isNotIn(char start, char end) {
/* 126 */     return new CharRange(start, end, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getStart() {
/* 137 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getEnd() {
/* 146 */     return this.end;
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
/*     */   public boolean isNegated() {
/* 158 */     return this.negated;
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
/*     */   public boolean contains(char ch) {
/* 170 */     return (((ch >= this.start && ch <= this.end)) != this.negated);
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
/*     */   public boolean contains(CharRange range) {
/* 182 */     Validate.notNull(range, "The Range must not be null", new Object[0]);
/* 183 */     if (this.negated) {
/* 184 */       if (range.negated) {
/* 185 */         return (this.start >= range.start && this.end <= range.end);
/*     */       }
/* 187 */       return (range.end < this.start || range.start > this.end);
/*     */     } 
/* 189 */     if (range.negated) {
/* 190 */       return (this.start == '\000' && this.end == Character.MAX_VALUE);
/*     */     }
/* 192 */     return (this.start <= range.start && this.end >= range.end);
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
/*     */   public boolean equals(Object obj) {
/* 206 */     if (obj == this) {
/* 207 */       return true;
/*     */     }
/* 209 */     if (!(obj instanceof CharRange)) {
/* 210 */       return false;
/*     */     }
/* 212 */     CharRange other = (CharRange)obj;
/* 213 */     return (this.start == other.start && this.end == other.end && this.negated == other.negated);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 223 */     return 83 + this.start + 7 * this.end + (this.negated ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 233 */     if (this.iToString == null) {
/* 234 */       StringBuilder buf = new StringBuilder(4);
/* 235 */       if (isNegated()) {
/* 236 */         buf.append('^');
/*     */       }
/* 238 */       buf.append(this.start);
/* 239 */       if (this.start != this.end) {
/* 240 */         buf.append('-');
/* 241 */         buf.append(this.end);
/*     */       } 
/* 243 */       this.iToString = buf.toString();
/*     */     } 
/* 245 */     return this.iToString;
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
/*     */   public Iterator<Character> iterator() {
/* 259 */     return new CharacterIterator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CharacterIterator
/*     */     implements Iterator<Character>
/*     */   {
/*     */     private char current;
/*     */ 
/*     */     
/*     */     private final CharRange range;
/*     */ 
/*     */     
/*     */     private boolean hasNext;
/*     */ 
/*     */ 
/*     */     
/*     */     private CharacterIterator(CharRange r) {
/* 279 */       this.range = r;
/* 280 */       this.hasNext = true;
/*     */       
/* 282 */       if (this.range.negated) {
/* 283 */         if (this.range.start == '\000') {
/* 284 */           if (this.range.end == Character.MAX_VALUE) {
/*     */             
/* 286 */             this.hasNext = false;
/*     */           } else {
/* 288 */             this.current = (char)(this.range.end + 1);
/*     */           } 
/*     */         } else {
/* 291 */           this.current = Character.MIN_VALUE;
/*     */         } 
/*     */       } else {
/* 294 */         this.current = this.range.start;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void prepareNext() {
/* 302 */       if (this.range.negated) {
/* 303 */         if (this.current == Character.MAX_VALUE) {
/* 304 */           this.hasNext = false;
/* 305 */         } else if (this.current + 1 == this.range.start) {
/* 306 */           if (this.range.end == Character.MAX_VALUE) {
/* 307 */             this.hasNext = false;
/*     */           } else {
/* 309 */             this.current = (char)(this.range.end + 1);
/*     */           } 
/*     */         } else {
/* 312 */           this.current = (char)(this.current + 1);
/*     */         } 
/* 314 */       } else if (this.current < this.range.end) {
/* 315 */         this.current = (char)(this.current + 1);
/*     */       } else {
/* 317 */         this.hasNext = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 328 */       return this.hasNext;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Character next() {
/* 338 */       if (!this.hasNext) {
/* 339 */         throw new NoSuchElementException();
/*     */       }
/* 341 */       char cur = this.current;
/* 342 */       prepareNext();
/* 343 */       return Character.valueOf(cur);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 354 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\CharRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */