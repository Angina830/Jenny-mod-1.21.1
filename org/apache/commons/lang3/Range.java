/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Range<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Comparator<T> comparator;
/*     */   private transient int hashCode;
/*     */   private final T maximum;
/*     */   private final T minimum;
/*     */   private transient String toString;
/*     */   
/*     */   private enum ComparableComparator
/*     */     implements Comparator
/*     */   {
/*  38 */     INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Object obj1, Object obj2) {
/*  48 */       return ((Comparable<Object>)obj1).compareTo(obj2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Comparable<T>> Range<T> between(T fromInclusive, T toInclusive) {
/*  74 */     return between(fromInclusive, toInclusive, null);
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
/*     */   public static <T> Range<T> between(T fromInclusive, T toInclusive, Comparator<T> comparator) {
/*  95 */     return new Range<>(fromInclusive, toInclusive, comparator);
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
/*     */   public static <T extends Comparable<T>> Range<T> is(T element) {
/* 112 */     return between(element, element, null);
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
/*     */   public static <T> Range<T> is(T element, Comparator<T> comparator) {
/* 130 */     return between(element, element, comparator);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Range(T element1, T element2, Comparator<T> comp) {
/* 170 */     if (element1 == null || element2 == null) {
/* 171 */       throw new IllegalArgumentException("Elements in a range must not be null: element1=" + element1 + ", element2=" + element2);
/*     */     }
/*     */     
/* 174 */     if (comp == null) {
/* 175 */       this.comparator = ComparableComparator.INSTANCE;
/*     */     } else {
/* 177 */       this.comparator = comp;
/*     */     } 
/* 179 */     if (this.comparator.compare(element1, element2) < 1) {
/* 180 */       this.minimum = element1;
/* 181 */       this.maximum = element2;
/*     */     } else {
/* 183 */       this.minimum = element2;
/* 184 */       this.maximum = element1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(T element) {
/* 195 */     if (element == null) {
/* 196 */       return false;
/*     */     }
/* 198 */     return (this.comparator.compare(element, this.minimum) > -1 && this.comparator.compare(element, this.maximum) < 1);
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
/*     */   public boolean containsRange(Range<T> otherRange) {
/* 211 */     if (otherRange == null) {
/* 212 */       return false;
/*     */     }
/* 214 */     return (contains(otherRange.minimum) && 
/* 215 */       contains(otherRange.maximum));
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
/*     */   public int elementCompareTo(T element) {
/* 230 */     Validate.notNull(element, "Element is null", new Object[0]);
/* 231 */     if (isAfter(element))
/* 232 */       return -1; 
/* 233 */     if (isBefore(element)) {
/* 234 */       return 1;
/*     */     }
/* 236 */     return 0;
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
/*     */   public boolean equals(Object obj) {
/* 254 */     if (obj == this)
/* 255 */       return true; 
/* 256 */     if (obj == null || obj.getClass() != getClass()) {
/* 257 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 261 */     Range<T> range = (Range<T>)obj;
/* 262 */     return (this.minimum.equals(range.minimum) && this.maximum
/* 263 */       .equals(range.maximum));
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
/*     */   public Comparator<T> getComparator() {
/* 276 */     return this.comparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMaximum() {
/* 285 */     return this.maximum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMinimum() {
/* 294 */     return this.minimum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 304 */     int result = this.hashCode;
/* 305 */     if (this.hashCode == 0) {
/* 306 */       result = 17;
/* 307 */       result = 37 * result + getClass().hashCode();
/* 308 */       result = 37 * result + this.minimum.hashCode();
/* 309 */       result = 37 * result + this.maximum.hashCode();
/* 310 */       this.hashCode = result;
/*     */     } 
/* 312 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Range<T> intersectionWith(Range<T> other) {
/* 323 */     if (!isOverlappedBy(other)) {
/* 324 */       throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", new Object[] { other }));
/*     */     }
/*     */     
/* 327 */     if (equals(other)) {
/* 328 */       return this;
/*     */     }
/* 330 */     T min = (getComparator().compare(this.minimum, other.minimum) < 0) ? other.minimum : this.minimum;
/* 331 */     T max = (getComparator().compare(this.maximum, other.maximum) < 0) ? this.maximum : other.maximum;
/* 332 */     return between(min, max, getComparator());
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
/*     */   public boolean isAfter(T element) {
/* 345 */     if (element == null) {
/* 346 */       return false;
/*     */     }
/* 348 */     return (this.comparator.compare(element, this.minimum) < 0);
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
/*     */   public boolean isAfterRange(Range<T> otherRange) {
/* 361 */     if (otherRange == null) {
/* 362 */       return false;
/*     */     }
/* 364 */     return isAfter(otherRange.maximum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBefore(T element) {
/* 374 */     if (element == null) {
/* 375 */       return false;
/*     */     }
/* 377 */     return (this.comparator.compare(element, this.maximum) > 0);
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
/*     */   public boolean isBeforeRange(Range<T> otherRange) {
/* 390 */     if (otherRange == null) {
/* 391 */       return false;
/*     */     }
/* 393 */     return isBefore(otherRange.minimum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndedBy(T element) {
/* 403 */     if (element == null) {
/* 404 */       return false;
/*     */     }
/* 406 */     return (this.comparator.compare(element, this.maximum) == 0);
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
/*     */   public boolean isNaturalOrdering() {
/* 421 */     return (this.comparator == ComparableComparator.INSTANCE);
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
/*     */   public boolean isOverlappedBy(Range<T> otherRange) {
/* 437 */     if (otherRange == null) {
/* 438 */       return false;
/*     */     }
/* 440 */     return (otherRange.contains(this.minimum) || otherRange
/* 441 */       .contains(this.maximum) || 
/* 442 */       contains(otherRange.minimum));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartedBy(T element) {
/* 452 */     if (element == null) {
/* 453 */       return false;
/*     */     }
/* 455 */     return (this.comparator.compare(element, this.minimum) == 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T fit(T element) {
/* 481 */     Validate.notNull(element, "element", new Object[0]);
/* 482 */     if (isAfter(element))
/* 483 */       return this.minimum; 
/* 484 */     if (isBefore(element)) {
/* 485 */       return this.maximum;
/*     */     }
/* 487 */     return element;
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
/*     */   public String toString() {
/* 500 */     if (this.toString == null) {
/* 501 */       this.toString = "[" + this.minimum + ".." + this.maximum + "]";
/*     */     }
/* 503 */     return this.toString;
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
/*     */   public String toString(String format) {
/* 519 */     return String.format(format, new Object[] { this.minimum, this.maximum, this.comparator });
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\Range.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */