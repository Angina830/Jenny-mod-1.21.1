/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonPointer
/*     */ {
/*     */   public static final char SEPARATOR = '/';
/*  34 */   protected static final JsonPointer EMPTY = new JsonPointer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonPointer _nextSegment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected volatile JsonPointer _head;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String _asString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String _matchingPropertyName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _matchingElementIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonPointer() {
/*  78 */     this._nextSegment = null;
/*  79 */     this._matchingPropertyName = "";
/*  80 */     this._matchingElementIndex = -1;
/*  81 */     this._asString = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonPointer(String fullString, String segment, JsonPointer next) {
/*  88 */     this._asString = fullString;
/*  89 */     this._nextSegment = next;
/*     */     
/*  91 */     this._matchingPropertyName = segment;
/*     */     
/*  93 */     this._matchingElementIndex = _parseIndex(segment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonPointer(String fullString, String segment, int matchIndex, JsonPointer next) {
/* 100 */     this._asString = fullString;
/* 101 */     this._nextSegment = next;
/* 102 */     this._matchingPropertyName = segment;
/* 103 */     this._matchingElementIndex = matchIndex;
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
/*     */   public static JsonPointer compile(String input) throws IllegalArgumentException {
/* 124 */     if (input == null || input.length() == 0) {
/* 125 */       return EMPTY;
/*     */     }
/*     */     
/* 128 */     if (input.charAt(0) != '/') {
/* 129 */       throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + input + "\"");
/*     */     }
/* 131 */     return _parseTail(input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonPointer valueOf(String input) {
/* 138 */     return compile(input);
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
/*     */   public static JsonPointer forPath(JsonStreamContext context, boolean includeRoot) {
/* 155 */     if (context == null) {
/* 156 */       return EMPTY;
/*     */     }
/* 158 */     if (!context.hasPathSegment())
/*     */     {
/* 160 */       if (!includeRoot || !context.inRoot() || !context.hasCurrentIndex()) {
/* 161 */         context = context.getParent();
/*     */       }
/*     */     }
/* 164 */     JsonPointer tail = null;
/*     */     
/* 166 */     for (; context != null; context = context.getParent()) {
/* 167 */       if (context.inObject()) {
/* 168 */         String seg = context.getCurrentName();
/* 169 */         if (seg == null) {
/* 170 */           seg = "";
/*     */         }
/* 172 */         tail = new JsonPointer(_fullPath(tail, seg), seg, tail);
/* 173 */       } else if (context.inArray() || includeRoot) {
/* 174 */         int ix = context.getCurrentIndex();
/* 175 */         String ixStr = String.valueOf(ix);
/* 176 */         tail = new JsonPointer(_fullPath(tail, ixStr), ixStr, ix, tail);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (tail == null) {
/* 183 */       return EMPTY;
/*     */     }
/* 185 */     return tail;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String _fullPath(JsonPointer tail, String segment) {
/* 190 */     if (tail == null) {
/* 191 */       StringBuilder stringBuilder = new StringBuilder(segment.length() + 1);
/* 192 */       stringBuilder.append('/');
/* 193 */       _appendEscaped(stringBuilder, segment);
/* 194 */       return stringBuilder.toString();
/*     */     } 
/* 196 */     String tailDesc = tail._asString;
/* 197 */     StringBuilder sb = new StringBuilder(segment.length() + 1 + tailDesc.length());
/* 198 */     sb.append('/');
/* 199 */     _appendEscaped(sb, segment);
/* 200 */     sb.append(tailDesc);
/* 201 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void _appendEscaped(StringBuilder sb, String segment) {
/* 206 */     for (int i = 0, end = segment.length(); i < end; i++) {
/* 207 */       char c = segment.charAt(i);
/* 208 */       if (c == '/') {
/* 209 */         sb.append("~1");
/*     */       
/*     */       }
/* 212 */       else if (c == '~') {
/* 213 */         sb.append("~0");
/*     */       } else {
/*     */         
/* 216 */         sb.append(c);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches() {
/* 248 */     return (this._nextSegment == null);
/* 249 */   } public String getMatchingProperty() { return this._matchingPropertyName; }
/* 250 */   public int getMatchingIndex() { return this._matchingElementIndex; }
/* 251 */   public boolean mayMatchProperty() { return (this._matchingPropertyName != null); } public boolean mayMatchElement() {
/* 252 */     return (this._matchingElementIndex >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPointer last() {
/* 261 */     JsonPointer current = this;
/* 262 */     if (current == EMPTY) {
/* 263 */       return null;
/*     */     }
/*     */     JsonPointer next;
/* 266 */     while ((next = current._nextSegment) != EMPTY) {
/* 267 */       current = next;
/*     */     }
/* 269 */     return current;
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
/*     */   public JsonPointer append(JsonPointer tail) {
/* 289 */     if (this == EMPTY) {
/* 290 */       return tail;
/*     */     }
/* 292 */     if (tail == EMPTY) {
/* 293 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 298 */     String currentJsonPointer = this._asString;
/* 299 */     if (currentJsonPointer.endsWith("/"))
/*     */     {
/* 301 */       currentJsonPointer = currentJsonPointer.substring(0, currentJsonPointer.length() - 1);
/*     */     }
/* 303 */     return compile(currentJsonPointer + tail._asString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesProperty(String name) {
/* 313 */     return (this._nextSegment != null && this._matchingPropertyName.equals(name));
/*     */   }
/*     */   
/*     */   public JsonPointer matchProperty(String name) {
/* 317 */     if (this._nextSegment != null && this._matchingPropertyName.equals(name)) {
/* 318 */       return this._nextSegment;
/*     */     }
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesElement(int index) {
/* 330 */     return (index == this._matchingElementIndex && index >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPointer matchElement(int index) {
/* 337 */     if (index != this._matchingElementIndex || index < 0) {
/* 338 */       return null;
/*     */     }
/* 340 */     return this._nextSegment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPointer tail() {
/* 349 */     return this._nextSegment;
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
/*     */   public JsonPointer head() {
/* 363 */     JsonPointer h = this._head;
/* 364 */     if (h == null) {
/* 365 */       if (this != EMPTY) {
/* 366 */         h = _constructHead();
/*     */       }
/* 368 */       this._head = h;
/*     */     } 
/* 370 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 379 */     return this._asString; } public int hashCode() {
/* 380 */     return this._asString.hashCode();
/*     */   }
/*     */   public boolean equals(Object o) {
/* 383 */     if (o == this) return true; 
/* 384 */     if (o == null) return false; 
/* 385 */     if (!(o instanceof JsonPointer)) return false; 
/* 386 */     return this._asString.equals(((JsonPointer)o)._asString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int _parseIndex(String str) {
/* 396 */     int len = str.length();
/*     */ 
/*     */     
/* 399 */     if (len == 0 || len > 10) {
/* 400 */       return -1;
/*     */     }
/*     */     
/* 403 */     char c = str.charAt(0);
/* 404 */     if (c <= '0') {
/* 405 */       return (len == 1 && c == '0') ? 0 : -1;
/*     */     }
/* 407 */     if (c > '9') {
/* 408 */       return -1;
/*     */     }
/* 410 */     for (int i = 1; i < len; i++) {
/* 411 */       c = str.charAt(i);
/* 412 */       if (c > '9' || c < '0') {
/* 413 */         return -1;
/*     */       }
/*     */     } 
/* 416 */     if (len == 10) {
/* 417 */       long l = NumberInput.parseLong(str);
/* 418 */       if (l > 2147483647L) {
/* 419 */         return -1;
/*     */       }
/*     */     } 
/* 422 */     return NumberInput.parseInt(str);
/*     */   }
/*     */   
/*     */   protected static JsonPointer _parseTail(String input) {
/* 426 */     int end = input.length();
/*     */ 
/*     */     
/* 429 */     for (int i = 1; i < end; ) {
/* 430 */       char c = input.charAt(i);
/* 431 */       if (c == '/') {
/* 432 */         return new JsonPointer(input, input.substring(1, i), _parseTail(input.substring(i)));
/*     */       }
/*     */       
/* 435 */       i++;
/*     */       
/* 437 */       if (c == '~' && i < end) {
/* 438 */         return _parseQuotedTail(input, i);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 443 */     return new JsonPointer(input, input.substring(1), EMPTY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static JsonPointer _parseQuotedTail(String input, int i) {
/* 454 */     int end = input.length();
/* 455 */     StringBuilder sb = new StringBuilder(Math.max(16, end));
/* 456 */     if (i > 2) {
/* 457 */       sb.append(input, 1, i - 1);
/*     */     }
/* 459 */     _appendEscape(sb, input.charAt(i++));
/* 460 */     while (i < end) {
/* 461 */       char c = input.charAt(i);
/* 462 */       if (c == '/') {
/* 463 */         return new JsonPointer(input, sb.toString(), _parseTail(input.substring(i)));
/*     */       }
/*     */       
/* 466 */       i++;
/* 467 */       if (c == '~' && i < end) {
/* 468 */         _appendEscape(sb, input.charAt(i++));
/*     */         continue;
/*     */       } 
/* 471 */       sb.append(c);
/*     */     } 
/*     */     
/* 474 */     return new JsonPointer(input, sb.toString(), EMPTY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonPointer _constructHead() {
/* 480 */     JsonPointer last = last();
/* 481 */     if (last == this) {
/* 482 */       return EMPTY;
/*     */     }
/*     */     
/* 485 */     int suffixLength = last._asString.length();
/* 486 */     JsonPointer next = this._nextSegment;
/* 487 */     return new JsonPointer(this._asString.substring(0, this._asString.length() - suffixLength), this._matchingPropertyName, this._matchingElementIndex, next._constructHead(suffixLength, last));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonPointer _constructHead(int suffixLength, JsonPointer last) {
/* 493 */     if (this == last) {
/* 494 */       return EMPTY;
/*     */     }
/* 496 */     JsonPointer next = this._nextSegment;
/* 497 */     String str = this._asString;
/* 498 */     return new JsonPointer(str.substring(0, str.length() - suffixLength), this._matchingPropertyName, this._matchingElementIndex, next._constructHead(suffixLength, last));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void _appendEscape(StringBuilder sb, char c) {
/* 503 */     if (c == '0') {
/* 504 */       c = '~';
/* 505 */     } else if (c == '1') {
/* 506 */       c = '/';
/*     */     } else {
/* 508 */       sb.append('~');
/*     */     } 
/* 510 */     sb.append(c);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\JsonPointer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */