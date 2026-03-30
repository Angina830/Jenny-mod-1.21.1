/*      */ package org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import org.apache.commons.lang3.ArrayUtils;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Deprecated
/*      */ public class StrBuilder
/*      */   implements CharSequence, Appendable, Serializable, Builder<String>
/*      */ {
/*      */   static final int CAPACITY = 32;
/*      */   private static final long serialVersionUID = 7628716375283629643L;
/*      */   protected char[] buffer;
/*      */   protected int size;
/*      */   private String newLine;
/*      */   private String nullText;
/*      */   
/*      */   public StrBuilder() {
/*  109 */     this(32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder(int initialCapacity) {
/*  119 */     if (initialCapacity <= 0) {
/*  120 */       initialCapacity = 32;
/*      */     }
/*  122 */     this.buffer = new char[initialCapacity];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder(String str) {
/*  133 */     if (str == null) {
/*  134 */       this.buffer = new char[32];
/*      */     } else {
/*  136 */       this.buffer = new char[str.length() + 32];
/*  137 */       append(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNewLineText() {
/*  148 */     return this.newLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setNewLineText(String newLine) {
/*  158 */     this.newLine = newLine;
/*  159 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNullText() {
/*  169 */     return this.nullText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setNullText(String nullText) {
/*  179 */     if (nullText != null && nullText.isEmpty()) {
/*  180 */       nullText = null;
/*      */     }
/*  182 */     this.nullText = nullText;
/*  183 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  194 */     return this.size;
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
/*      */   public StrBuilder setLength(int length) {
/*  206 */     if (length < 0) {
/*  207 */       throw new StringIndexOutOfBoundsException(length);
/*      */     }
/*  209 */     if (length < this.size) {
/*  210 */       this.size = length;
/*  211 */     } else if (length > this.size) {
/*  212 */       ensureCapacity(length);
/*  213 */       int oldEnd = this.size;
/*  214 */       int newEnd = length;
/*  215 */       this.size = length;
/*  216 */       for (int i = oldEnd; i < newEnd; i++) {
/*  217 */         this.buffer[i] = Character.MIN_VALUE;
/*      */       }
/*      */     } 
/*  220 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  230 */     return this.buffer.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder ensureCapacity(int capacity) {
/*  240 */     if (capacity > this.buffer.length) {
/*  241 */       char[] old = this.buffer;
/*  242 */       this.buffer = new char[capacity * 2];
/*  243 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*      */     } 
/*  245 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder minimizeCapacity() {
/*  254 */     if (this.buffer.length > length()) {
/*  255 */       char[] old = this.buffer;
/*  256 */       this.buffer = new char[length()];
/*  257 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*      */     } 
/*  259 */     return this;
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
/*      */   public int size() {
/*  272 */     return this.size;
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
/*      */   public boolean isEmpty() {
/*  284 */     return (this.size == 0);
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
/*      */   public StrBuilder clear() {
/*  299 */     this.size = 0;
/*  300 */     return this;
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
/*      */   public char charAt(int index) {
/*  315 */     if (index < 0 || index >= length()) {
/*  316 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  318 */     return this.buffer[index];
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
/*      */   public StrBuilder setCharAt(int index, char ch) {
/*  332 */     if (index < 0 || index >= length()) {
/*  333 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  335 */     this.buffer[index] = ch;
/*  336 */     return this;
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
/*      */   public StrBuilder deleteCharAt(int index) {
/*  349 */     if (index < 0 || index >= this.size) {
/*  350 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  352 */     deleteImpl(index, index + 1, 1);
/*  353 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray() {
/*  363 */     if (this.size == 0) {
/*  364 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  366 */     char[] chars = new char[this.size];
/*  367 */     System.arraycopy(this.buffer, 0, chars, 0, this.size);
/*  368 */     return chars;
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
/*      */   public char[] toCharArray(int startIndex, int endIndex) {
/*  382 */     endIndex = validateRange(startIndex, endIndex);
/*  383 */     int len = endIndex - startIndex;
/*  384 */     if (len == 0) {
/*  385 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  387 */     char[] chars = new char[len];
/*  388 */     System.arraycopy(this.buffer, startIndex, chars, 0, len);
/*  389 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getChars(char[] destination) {
/*  399 */     int len = length();
/*  400 */     if (destination == null || destination.length < len) {
/*  401 */       destination = new char[len];
/*      */     }
/*  403 */     System.arraycopy(this.buffer, 0, destination, 0, len);
/*  404 */     return destination;
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
/*      */   public void getChars(int startIndex, int endIndex, char[] destination, int destinationIndex) {
/*  418 */     if (startIndex < 0) {
/*  419 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/*  421 */     if (endIndex < 0 || endIndex > length()) {
/*  422 */       throw new StringIndexOutOfBoundsException(endIndex);
/*      */     }
/*  424 */     if (startIndex > endIndex) {
/*  425 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/*  427 */     System.arraycopy(this.buffer, startIndex, destination, destinationIndex, endIndex - startIndex);
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
/*      */   public int readFrom(Readable readable) throws IOException {
/*  443 */     int oldSize = this.size;
/*  444 */     if (readable instanceof Reader) {
/*  445 */       Reader r = (Reader)readable;
/*  446 */       ensureCapacity(this.size + 1);
/*      */       int read;
/*  448 */       while ((read = r.read(this.buffer, this.size, this.buffer.length - this.size)) != -1) {
/*  449 */         this.size += read;
/*  450 */         ensureCapacity(this.size + 1);
/*      */       } 
/*  452 */     } else if (readable instanceof CharBuffer) {
/*  453 */       CharBuffer cb = (CharBuffer)readable;
/*  454 */       int remaining = cb.remaining();
/*  455 */       ensureCapacity(this.size + remaining);
/*  456 */       cb.get(this.buffer, this.size, remaining);
/*  457 */       this.size += remaining;
/*      */     } else {
/*      */       while (true) {
/*  460 */         ensureCapacity(this.size + 1);
/*  461 */         CharBuffer buf = CharBuffer.wrap(this.buffer, this.size, this.buffer.length - this.size);
/*  462 */         int read = readable.read(buf);
/*  463 */         if (read == -1) {
/*      */           break;
/*      */         }
/*  466 */         this.size += read;
/*      */       } 
/*      */     } 
/*  469 */     return this.size - oldSize;
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
/*      */   public StrBuilder appendNewLine() {
/*  483 */     if (this.newLine == null) {
/*  484 */       append(System.lineSeparator());
/*  485 */       return this;
/*      */     } 
/*  487 */     return append(this.newLine);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendNull() {
/*  496 */     if (this.nullText == null) {
/*  497 */       return this;
/*      */     }
/*  499 */     return append(this.nullText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(Object obj) {
/*  510 */     if (obj == null) {
/*  511 */       return appendNull();
/*      */     }
/*  513 */     if (obj instanceof CharSequence) {
/*  514 */       return append((CharSequence)obj);
/*      */     }
/*  516 */     return append(obj.toString());
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
/*      */   public StrBuilder append(CharSequence seq) {
/*  529 */     if (seq == null) {
/*  530 */       return appendNull();
/*      */     }
/*  532 */     if (seq instanceof StrBuilder) {
/*  533 */       return append((StrBuilder)seq);
/*      */     }
/*  535 */     if (seq instanceof StringBuilder) {
/*  536 */       return append((StringBuilder)seq);
/*      */     }
/*  538 */     if (seq instanceof StringBuffer) {
/*  539 */       return append((StringBuffer)seq);
/*      */     }
/*  541 */     if (seq instanceof CharBuffer) {
/*  542 */       return append((CharBuffer)seq);
/*      */     }
/*  544 */     return append(seq.toString());
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
/*      */   public StrBuilder append(CharSequence seq, int startIndex, int length) {
/*  559 */     if (seq == null) {
/*  560 */       return appendNull();
/*      */     }
/*  562 */     return append(seq.toString(), startIndex, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String str) {
/*  573 */     if (str == null) {
/*  574 */       return appendNull();
/*      */     }
/*  576 */     int strLen = str.length();
/*  577 */     if (strLen > 0) {
/*  578 */       int len = length();
/*  579 */       ensureCapacity(len + strLen);
/*  580 */       str.getChars(0, strLen, this.buffer, len);
/*  581 */       this.size += strLen;
/*      */     } 
/*  583 */     return this;
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
/*      */   public StrBuilder append(String str, int startIndex, int length) {
/*  597 */     if (str == null) {
/*  598 */       return appendNull();
/*      */     }
/*  600 */     if (startIndex < 0 || startIndex > str.length()) {
/*  601 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  603 */     if (length < 0 || startIndex + length > str.length()) {
/*  604 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  606 */     if (length > 0) {
/*  607 */       int len = length();
/*  608 */       ensureCapacity(len + length);
/*  609 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  610 */       this.size += length;
/*      */     } 
/*  612 */     return this;
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
/*      */   public StrBuilder append(String format, Object... objs) {
/*  625 */     return append(String.format(format, objs));
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
/*      */   public StrBuilder append(CharBuffer buf) {
/*  637 */     if (buf == null) {
/*  638 */       return appendNull();
/*      */     }
/*  640 */     if (buf.hasArray()) {
/*  641 */       int length = buf.remaining();
/*  642 */       int len = length();
/*  643 */       ensureCapacity(len + length);
/*  644 */       System.arraycopy(buf.array(), buf.arrayOffset() + buf.position(), this.buffer, len, length);
/*  645 */       this.size += length;
/*      */     } else {
/*  647 */       append(buf.toString());
/*      */     } 
/*  649 */     return this;
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
/*      */   public StrBuilder append(CharBuffer buf, int startIndex, int length) {
/*  663 */     if (buf == null) {
/*  664 */       return appendNull();
/*      */     }
/*  666 */     if (buf.hasArray()) {
/*  667 */       int totalLength = buf.remaining();
/*  668 */       if (startIndex < 0 || startIndex > totalLength) {
/*  669 */         throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */       }
/*  671 */       if (length < 0 || startIndex + length > totalLength) {
/*  672 */         throw new StringIndexOutOfBoundsException("length must be valid");
/*      */       }
/*  674 */       int len = length();
/*  675 */       ensureCapacity(len + length);
/*  676 */       System.arraycopy(buf.array(), buf.arrayOffset() + buf.position() + startIndex, this.buffer, len, length);
/*  677 */       this.size += length;
/*      */     } else {
/*  679 */       append(buf.toString(), startIndex, length);
/*      */     } 
/*  681 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuffer str) {
/*  692 */     if (str == null) {
/*  693 */       return appendNull();
/*      */     }
/*  695 */     int strLen = str.length();
/*  696 */     if (strLen > 0) {
/*  697 */       int len = length();
/*  698 */       ensureCapacity(len + strLen);
/*  699 */       str.getChars(0, strLen, this.buffer, len);
/*  700 */       this.size += strLen;
/*      */     } 
/*  702 */     return this;
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
/*      */   public StrBuilder append(StringBuffer str, int startIndex, int length) {
/*  715 */     if (str == null) {
/*  716 */       return appendNull();
/*      */     }
/*  718 */     if (startIndex < 0 || startIndex > str.length()) {
/*  719 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  721 */     if (length < 0 || startIndex + length > str.length()) {
/*  722 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  724 */     if (length > 0) {
/*  725 */       int len = length();
/*  726 */       ensureCapacity(len + length);
/*  727 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  728 */       this.size += length;
/*      */     } 
/*  730 */     return this;
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
/*      */   public StrBuilder append(StringBuilder str) {
/*  742 */     if (str == null) {
/*  743 */       return appendNull();
/*      */     }
/*  745 */     int strLen = str.length();
/*  746 */     if (strLen > 0) {
/*  747 */       int len = length();
/*  748 */       ensureCapacity(len + strLen);
/*  749 */       str.getChars(0, strLen, this.buffer, len);
/*  750 */       this.size += strLen;
/*      */     } 
/*  752 */     return this;
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
/*      */   public StrBuilder append(StringBuilder str, int startIndex, int length) {
/*  766 */     if (str == null) {
/*  767 */       return appendNull();
/*      */     }
/*  769 */     if (startIndex < 0 || startIndex > str.length()) {
/*  770 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  772 */     if (length < 0 || startIndex + length > str.length()) {
/*  773 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  775 */     if (length > 0) {
/*  776 */       int len = length();
/*  777 */       ensureCapacity(len + length);
/*  778 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  779 */       this.size += length;
/*      */     } 
/*  781 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StrBuilder str) {
/*  792 */     if (str == null) {
/*  793 */       return appendNull();
/*      */     }
/*  795 */     int strLen = str.length();
/*  796 */     if (strLen > 0) {
/*  797 */       int len = length();
/*  798 */       ensureCapacity(len + strLen);
/*  799 */       System.arraycopy(str.buffer, 0, this.buffer, len, strLen);
/*  800 */       this.size += strLen;
/*      */     } 
/*  802 */     return this;
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
/*      */   public StrBuilder append(StrBuilder str, int startIndex, int length) {
/*  815 */     if (str == null) {
/*  816 */       return appendNull();
/*      */     }
/*  818 */     if (startIndex < 0 || startIndex > str.length()) {
/*  819 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  821 */     if (length < 0 || startIndex + length > str.length()) {
/*  822 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  824 */     if (length > 0) {
/*  825 */       int len = length();
/*  826 */       ensureCapacity(len + length);
/*  827 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  828 */       this.size += length;
/*      */     } 
/*  830 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char[] chars) {
/*  841 */     if (chars == null) {
/*  842 */       return appendNull();
/*      */     }
/*  844 */     int strLen = chars.length;
/*  845 */     if (strLen > 0) {
/*  846 */       int len = length();
/*  847 */       ensureCapacity(len + strLen);
/*  848 */       System.arraycopy(chars, 0, this.buffer, len, strLen);
/*  849 */       this.size += strLen;
/*      */     } 
/*  851 */     return this;
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
/*      */   public StrBuilder append(char[] chars, int startIndex, int length) {
/*  864 */     if (chars == null) {
/*  865 */       return appendNull();
/*      */     }
/*  867 */     if (startIndex < 0 || startIndex > chars.length) {
/*  868 */       throw new StringIndexOutOfBoundsException("Invalid startIndex: " + length);
/*      */     }
/*  870 */     if (length < 0 || startIndex + length > chars.length) {
/*  871 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*      */     }
/*  873 */     if (length > 0) {
/*  874 */       int len = length();
/*  875 */       ensureCapacity(len + length);
/*  876 */       System.arraycopy(chars, startIndex, this.buffer, len, length);
/*  877 */       this.size += length;
/*      */     } 
/*  879 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(boolean value) {
/*  889 */     if (value) {
/*  890 */       ensureCapacity(this.size + 4);
/*  891 */       this.buffer[this.size++] = 't';
/*  892 */       this.buffer[this.size++] = 'r';
/*  893 */       this.buffer[this.size++] = 'u';
/*  894 */       this.buffer[this.size++] = 'e';
/*      */     } else {
/*  896 */       ensureCapacity(this.size + 5);
/*  897 */       this.buffer[this.size++] = 'f';
/*  898 */       this.buffer[this.size++] = 'a';
/*  899 */       this.buffer[this.size++] = 'l';
/*  900 */       this.buffer[this.size++] = 's';
/*  901 */       this.buffer[this.size++] = 'e';
/*      */     } 
/*  903 */     return this;
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
/*      */   public StrBuilder append(char ch) {
/*  915 */     int len = length();
/*  916 */     ensureCapacity(len + 1);
/*  917 */     this.buffer[this.size++] = ch;
/*  918 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(int value) {
/*  928 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(long value) {
/*  938 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(float value) {
/*  948 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(double value) {
/*  958 */     return append(String.valueOf(value));
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
/*      */   public StrBuilder appendln(Object obj) {
/*  971 */     return append(obj).appendNewLine();
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
/*      */   public StrBuilder appendln(String str) {
/*  983 */     return append(str).appendNewLine();
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
/*      */   public StrBuilder appendln(String str, int startIndex, int length) {
/*  997 */     return append(str, startIndex, length).appendNewLine();
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
/*      */   public StrBuilder appendln(String format, Object... objs) {
/* 1010 */     return append(format, objs).appendNewLine();
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
/*      */   public StrBuilder appendln(StringBuffer str) {
/* 1022 */     return append(str).appendNewLine();
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
/*      */   public StrBuilder appendln(StringBuilder str) {
/* 1034 */     return append(str).appendNewLine();
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
/*      */   public StrBuilder appendln(StringBuilder str, int startIndex, int length) {
/* 1048 */     return append(str, startIndex, length).appendNewLine();
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
/*      */   public StrBuilder appendln(StringBuffer str, int startIndex, int length) {
/* 1062 */     return append(str, startIndex, length).appendNewLine();
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
/*      */   public StrBuilder appendln(StrBuilder str) {
/* 1074 */     return append(str).appendNewLine();
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
/*      */   public StrBuilder appendln(StrBuilder str, int startIndex, int length) {
/* 1088 */     return append(str, startIndex, length).appendNewLine();
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
/*      */   public StrBuilder appendln(char[] chars) {
/* 1100 */     return append(chars).appendNewLine();
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
/*      */   public StrBuilder appendln(char[] chars, int startIndex, int length) {
/* 1114 */     return append(chars, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(boolean value) {
/* 1125 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char ch) {
/* 1136 */     return append(ch).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(int value) {
/* 1147 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(long value) {
/* 1158 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(float value) {
/* 1169 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(double value) {
/* 1180 */     return append(value).appendNewLine();
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
/*      */   public <T> StrBuilder appendAll(T... array) {
/* 1201 */     if (ArrayUtils.isNotEmpty((Object[])array)) {
/* 1202 */       for (T element : array) {
/* 1203 */         append(element);
/*      */       }
/*      */     }
/* 1206 */     return this;
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
/*      */   public StrBuilder appendAll(Iterable<?> iterable) {
/* 1219 */     if (iterable != null) {
/* 1220 */       for (Object o : iterable) {
/* 1221 */         append(o);
/*      */       }
/*      */     }
/* 1224 */     return this;
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
/*      */   public StrBuilder appendAll(Iterator<?> it) {
/* 1237 */     if (it != null) {
/* 1238 */       while (it.hasNext()) {
/* 1239 */         append(it.next());
/*      */       }
/*      */     }
/* 1242 */     return this;
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
/*      */   public StrBuilder appendWithSeparators(Object[] array, String separator) {
/* 1257 */     if (array != null && array.length > 0) {
/* 1258 */       String sep = Objects.toString(separator, "");
/* 1259 */       append(array[0]);
/* 1260 */       for (int i = 1; i < array.length; i++) {
/* 1261 */         append(sep);
/* 1262 */         append(array[i]);
/*      */       } 
/*      */     } 
/* 1265 */     return this;
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
/*      */   public StrBuilder appendWithSeparators(Iterable<?> iterable, String separator) {
/* 1279 */     if (iterable != null) {
/* 1280 */       String sep = Objects.toString(separator, "");
/* 1281 */       Iterator<?> it = iterable.iterator();
/* 1282 */       while (it.hasNext()) {
/* 1283 */         append(it.next());
/* 1284 */         if (it.hasNext()) {
/* 1285 */           append(sep);
/*      */         }
/*      */       } 
/*      */     } 
/* 1289 */     return this;
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
/*      */   public StrBuilder appendWithSeparators(Iterator<?> it, String separator) {
/* 1303 */     if (it != null) {
/* 1304 */       String sep = Objects.toString(separator, "");
/* 1305 */       while (it.hasNext()) {
/* 1306 */         append(it.next());
/* 1307 */         if (it.hasNext()) {
/* 1308 */           append(sep);
/*      */         }
/*      */       } 
/*      */     } 
/* 1312 */     return this;
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
/*      */   public StrBuilder appendSeparator(String separator) {
/* 1337 */     return appendSeparator(separator, (String)null);
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
/*      */   public StrBuilder appendSeparator(String standard, String defaultIfEmpty) {
/* 1368 */     String str = isEmpty() ? defaultIfEmpty : standard;
/* 1369 */     if (str != null) {
/* 1370 */       append(str);
/*      */     }
/* 1372 */     return this;
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
/*      */   public StrBuilder appendSeparator(char separator) {
/* 1395 */     if (size() > 0) {
/* 1396 */       append(separator);
/*      */     }
/* 1398 */     return this;
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
/*      */   public StrBuilder appendSeparator(char standard, char defaultIfEmpty) {
/* 1413 */     if (size() > 0) {
/* 1414 */       append(standard);
/*      */     } else {
/* 1416 */       append(defaultIfEmpty);
/*      */     } 
/* 1418 */     return this;
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
/*      */   public StrBuilder appendSeparator(String separator, int loopIndex) {
/* 1443 */     if (separator != null && loopIndex > 0) {
/* 1444 */       append(separator);
/*      */     }
/* 1446 */     return this;
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
/*      */   public StrBuilder appendSeparator(char separator, int loopIndex) {
/* 1471 */     if (loopIndex > 0) {
/* 1472 */       append(separator);
/*      */     }
/* 1474 */     return this;
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
/*      */   public StrBuilder appendPadding(int length, char padChar) {
/* 1486 */     if (length >= 0) {
/* 1487 */       ensureCapacity(this.size + length);
/* 1488 */       for (int i = 0; i < length; i++) {
/* 1489 */         this.buffer[this.size++] = padChar;
/*      */       }
/*      */     } 
/* 1492 */     return this;
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
/*      */   public StrBuilder appendFixedWidthPadLeft(Object obj, int width, char padChar) {
/* 1508 */     if (width > 0) {
/* 1509 */       ensureCapacity(this.size + width);
/* 1510 */       String str = (obj == null) ? getNullText() : obj.toString();
/* 1511 */       if (str == null) {
/* 1512 */         str = "";
/*      */       }
/* 1514 */       int strLen = str.length();
/* 1515 */       if (strLen >= width) {
/* 1516 */         str.getChars(strLen - width, strLen, this.buffer, this.size);
/*      */       } else {
/* 1518 */         int padLen = width - strLen;
/* 1519 */         for (int i = 0; i < padLen; i++) {
/* 1520 */           this.buffer[this.size + i] = padChar;
/*      */         }
/* 1522 */         str.getChars(0, strLen, this.buffer, this.size + padLen);
/*      */       } 
/* 1524 */       this.size += width;
/*      */     } 
/* 1526 */     return this;
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
/*      */   public StrBuilder appendFixedWidthPadLeft(int value, int width, char padChar) {
/* 1540 */     return appendFixedWidthPadLeft(String.valueOf(value), width, padChar);
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
/*      */   public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
/* 1555 */     if (width > 0) {
/* 1556 */       ensureCapacity(this.size + width);
/* 1557 */       String str = (obj == null) ? getNullText() : obj.toString();
/* 1558 */       if (str == null) {
/* 1559 */         str = "";
/*      */       }
/* 1561 */       int strLen = str.length();
/* 1562 */       if (strLen >= width) {
/* 1563 */         str.getChars(0, width, this.buffer, this.size);
/*      */       } else {
/* 1565 */         int padLen = width - strLen;
/* 1566 */         str.getChars(0, strLen, this.buffer, this.size);
/* 1567 */         for (int i = 0; i < padLen; i++) {
/* 1568 */           this.buffer[this.size + strLen + i] = padChar;
/*      */         }
/*      */       } 
/* 1571 */       this.size += width;
/*      */     } 
/* 1573 */     return this;
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
/*      */   public StrBuilder appendFixedWidthPadRight(int value, int width, char padChar) {
/* 1587 */     return appendFixedWidthPadRight(String.valueOf(value), width, padChar);
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
/*      */   public StrBuilder insert(int index, Object obj) {
/* 1601 */     if (obj == null) {
/* 1602 */       return insert(index, this.nullText);
/*      */     }
/* 1604 */     return insert(index, obj.toString());
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
/*      */   public StrBuilder insert(int index, String str) {
/* 1617 */     validateIndex(index);
/* 1618 */     if (str == null) {
/* 1619 */       str = this.nullText;
/*      */     }
/* 1621 */     if (str != null) {
/* 1622 */       int strLen = str.length();
/* 1623 */       if (strLen > 0) {
/* 1624 */         int newSize = this.size + strLen;
/* 1625 */         ensureCapacity(newSize);
/* 1626 */         System.arraycopy(this.buffer, index, this.buffer, index + strLen, this.size - index);
/* 1627 */         this.size = newSize;
/* 1628 */         str.getChars(0, strLen, this.buffer, index);
/*      */       } 
/*      */     } 
/* 1631 */     return this;
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
/*      */   public StrBuilder insert(int index, char[] chars) {
/* 1644 */     validateIndex(index);
/* 1645 */     if (chars == null) {
/* 1646 */       return insert(index, this.nullText);
/*      */     }
/* 1648 */     int len = chars.length;
/* 1649 */     if (len > 0) {
/* 1650 */       ensureCapacity(this.size + len);
/* 1651 */       System.arraycopy(this.buffer, index, this.buffer, index + len, this.size - index);
/* 1652 */       System.arraycopy(chars, 0, this.buffer, index, len);
/* 1653 */       this.size += len;
/*      */     } 
/* 1655 */     return this;
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
/*      */   public StrBuilder insert(int index, char[] chars, int offset, int length) {
/* 1670 */     validateIndex(index);
/* 1671 */     if (chars == null) {
/* 1672 */       return insert(index, this.nullText);
/*      */     }
/* 1674 */     if (offset < 0 || offset > chars.length) {
/* 1675 */       throw new StringIndexOutOfBoundsException("Invalid offset: " + offset);
/*      */     }
/* 1677 */     if (length < 0 || offset + length > chars.length) {
/* 1678 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*      */     }
/* 1680 */     if (length > 0) {
/* 1681 */       ensureCapacity(this.size + length);
/* 1682 */       System.arraycopy(this.buffer, index, this.buffer, index + length, this.size - index);
/* 1683 */       System.arraycopy(chars, offset, this.buffer, index, length);
/* 1684 */       this.size += length;
/*      */     } 
/* 1686 */     return this;
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
/*      */   public StrBuilder insert(int index, boolean value) {
/* 1698 */     validateIndex(index);
/* 1699 */     if (value) {
/* 1700 */       ensureCapacity(this.size + 4);
/* 1701 */       System.arraycopy(this.buffer, index, this.buffer, index + 4, this.size - index);
/* 1702 */       this.buffer[index++] = 't';
/* 1703 */       this.buffer[index++] = 'r';
/* 1704 */       this.buffer[index++] = 'u';
/* 1705 */       this.buffer[index] = 'e';
/* 1706 */       this.size += 4;
/*      */     } else {
/* 1708 */       ensureCapacity(this.size + 5);
/* 1709 */       System.arraycopy(this.buffer, index, this.buffer, index + 5, this.size - index);
/* 1710 */       this.buffer[index++] = 'f';
/* 1711 */       this.buffer[index++] = 'a';
/* 1712 */       this.buffer[index++] = 'l';
/* 1713 */       this.buffer[index++] = 's';
/* 1714 */       this.buffer[index] = 'e';
/* 1715 */       this.size += 5;
/*      */     } 
/* 1717 */     return this;
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
/*      */   public StrBuilder insert(int index, char value) {
/* 1729 */     validateIndex(index);
/* 1730 */     ensureCapacity(this.size + 1);
/* 1731 */     System.arraycopy(this.buffer, index, this.buffer, index + 1, this.size - index);
/* 1732 */     this.buffer[index] = value;
/* 1733 */     this.size++;
/* 1734 */     return this;
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
/*      */   public StrBuilder insert(int index, int value) {
/* 1746 */     return insert(index, String.valueOf(value));
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
/*      */   public StrBuilder insert(int index, long value) {
/* 1758 */     return insert(index, String.valueOf(value));
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
/*      */   public StrBuilder insert(int index, float value) {
/* 1770 */     return insert(index, String.valueOf(value));
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
/*      */   public StrBuilder insert(int index, double value) {
/* 1782 */     return insert(index, String.valueOf(value));
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
/*      */   private void deleteImpl(int startIndex, int endIndex, int len) {
/* 1795 */     System.arraycopy(this.buffer, endIndex, this.buffer, startIndex, this.size - endIndex);
/* 1796 */     this.size -= len;
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
/*      */   public StrBuilder delete(int startIndex, int endIndex) {
/* 1809 */     endIndex = validateRange(startIndex, endIndex);
/* 1810 */     int len = endIndex - startIndex;
/* 1811 */     if (len > 0) {
/* 1812 */       deleteImpl(startIndex, endIndex, len);
/*      */     }
/* 1814 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(char ch) {
/* 1825 */     for (int i = 0; i < this.size; i++) {
/* 1826 */       if (this.buffer[i] == ch) {
/* 1827 */         int start = i; do {  }
/* 1828 */         while (++i < this.size && 
/* 1829 */           this.buffer[i] == ch);
/*      */ 
/*      */ 
/*      */         
/* 1833 */         int len = i - start;
/* 1834 */         deleteImpl(start, i, len);
/* 1835 */         i -= len;
/*      */       } 
/*      */     } 
/* 1838 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(char ch) {
/* 1848 */     for (int i = 0; i < this.size; i++) {
/* 1849 */       if (this.buffer[i] == ch) {
/* 1850 */         deleteImpl(i, i + 1, 1);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1854 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(String str) {
/* 1865 */     int len = (str == null) ? 0 : str.length();
/* 1866 */     if (len > 0) {
/* 1867 */       int index = indexOf(str, 0);
/* 1868 */       while (index >= 0) {
/* 1869 */         deleteImpl(index, index + len, len);
/* 1870 */         index = indexOf(str, index);
/*      */       } 
/*      */     } 
/* 1873 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(String str) {
/* 1883 */     int len = (str == null) ? 0 : str.length();
/* 1884 */     if (len > 0) {
/* 1885 */       int index = indexOf(str, 0);
/* 1886 */       if (index >= 0) {
/* 1887 */         deleteImpl(index, index + len, len);
/*      */       }
/*      */     } 
/* 1890 */     return this;
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
/*      */   public StrBuilder deleteAll(StrMatcher matcher) {
/* 1905 */     return replace(matcher, null, 0, this.size, -1);
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
/*      */   public StrBuilder deleteFirst(StrMatcher matcher) {
/* 1919 */     return replace(matcher, null, 0, this.size, 1);
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
/*      */   private void replaceImpl(int startIndex, int endIndex, int removeLen, String insertStr, int insertLen) {
/* 1934 */     int newSize = this.size - removeLen + insertLen;
/* 1935 */     if (insertLen != removeLen) {
/* 1936 */       ensureCapacity(newSize);
/* 1937 */       System.arraycopy(this.buffer, endIndex, this.buffer, startIndex + insertLen, this.size - endIndex);
/* 1938 */       this.size = newSize;
/*      */     } 
/* 1940 */     if (insertLen > 0) {
/* 1941 */       insertStr.getChars(0, insertLen, this.buffer, startIndex);
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
/*      */ 
/*      */   
/*      */   public StrBuilder replace(int startIndex, int endIndex, String replaceStr) {
/* 1957 */     endIndex = validateRange(startIndex, endIndex);
/* 1958 */     int insertLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1959 */     replaceImpl(startIndex, endIndex, endIndex - startIndex, replaceStr, insertLen);
/* 1960 */     return this;
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
/*      */   public StrBuilder replaceAll(char search, char replace) {
/* 1973 */     if (search != replace) {
/* 1974 */       for (int i = 0; i < this.size; i++) {
/* 1975 */         if (this.buffer[i] == search) {
/* 1976 */           this.buffer[i] = replace;
/*      */         }
/*      */       } 
/*      */     }
/* 1980 */     return this;
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
/*      */   public StrBuilder replaceFirst(char search, char replace) {
/* 1992 */     if (search != replace) {
/* 1993 */       for (int i = 0; i < this.size; i++) {
/* 1994 */         if (this.buffer[i] == search) {
/* 1995 */           this.buffer[i] = replace;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 2000 */     return this;
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
/*      */   public StrBuilder replaceAll(String searchStr, String replaceStr) {
/* 2012 */     int searchLen = (searchStr == null) ? 0 : searchStr.length();
/* 2013 */     if (searchLen > 0) {
/* 2014 */       int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 2015 */       int index = indexOf(searchStr, 0);
/* 2016 */       while (index >= 0) {
/* 2017 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/* 2018 */         index = indexOf(searchStr, index + replaceLen);
/*      */       } 
/*      */     } 
/* 2021 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(String searchStr, String replaceStr) {
/* 2032 */     int searchLen = (searchStr == null) ? 0 : searchStr.length();
/* 2033 */     if (searchLen > 0) {
/* 2034 */       int index = indexOf(searchStr, 0);
/* 2035 */       if (index >= 0) {
/* 2036 */         int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 2037 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/*      */       } 
/*      */     } 
/* 2040 */     return this;
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
/*      */   public StrBuilder replaceAll(StrMatcher matcher, String replaceStr) {
/* 2056 */     return replace(matcher, replaceStr, 0, this.size, -1);
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
/*      */   public StrBuilder replaceFirst(StrMatcher matcher, String replaceStr) {
/* 2071 */     return replace(matcher, replaceStr, 0, this.size, 1);
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
/*      */   public StrBuilder replace(StrMatcher matcher, String replaceStr, int startIndex, int endIndex, int replaceCount) {
/* 2094 */     endIndex = validateRange(startIndex, endIndex);
/* 2095 */     return replaceImpl(matcher, replaceStr, startIndex, endIndex, replaceCount);
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
/*      */   private StrBuilder replaceImpl(StrMatcher matcher, String replaceStr, int from, int to, int replaceCount) {
/* 2116 */     if (matcher == null || this.size == 0) {
/* 2117 */       return this;
/*      */     }
/* 2119 */     int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 2120 */     for (int i = from; i < to && replaceCount != 0; i++) {
/* 2121 */       char[] buf = this.buffer;
/* 2122 */       int removeLen = matcher.isMatch(buf, i, from, to);
/* 2123 */       if (removeLen > 0) {
/* 2124 */         replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen);
/* 2125 */         to = to - removeLen + replaceLen;
/* 2126 */         i = i + replaceLen - 1;
/* 2127 */         if (replaceCount > 0) {
/* 2128 */           replaceCount--;
/*      */         }
/*      */       } 
/*      */     } 
/* 2132 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder reverse() {
/* 2142 */     if (this.size == 0) {
/* 2143 */       return this;
/*      */     }
/*      */     
/* 2146 */     int half = this.size / 2;
/* 2147 */     char[] buf = this.buffer;
/* 2148 */     for (int leftIdx = 0, rightIdx = this.size - 1; leftIdx < half; leftIdx++, rightIdx--) {
/* 2149 */       char swap = buf[leftIdx];
/* 2150 */       buf[leftIdx] = buf[rightIdx];
/* 2151 */       buf[rightIdx] = swap;
/*      */     } 
/* 2153 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder trim() {
/* 2164 */     if (this.size == 0) {
/* 2165 */       return this;
/*      */     }
/* 2167 */     int len = this.size;
/* 2168 */     char[] buf = this.buffer;
/* 2169 */     int pos = 0;
/* 2170 */     while (pos < len && buf[pos] <= ' ') {
/* 2171 */       pos++;
/*      */     }
/* 2173 */     while (pos < len && buf[len - 1] <= ' ') {
/* 2174 */       len--;
/*      */     }
/* 2176 */     if (len < this.size) {
/* 2177 */       delete(len, this.size);
/*      */     }
/* 2179 */     if (pos > 0) {
/* 2180 */       delete(0, pos);
/*      */     }
/* 2182 */     return this;
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
/*      */   public boolean startsWith(String str) {
/* 2195 */     if (str == null) {
/* 2196 */       return false;
/*      */     }
/* 2198 */     int len = str.length();
/* 2199 */     if (len == 0) {
/* 2200 */       return true;
/*      */     }
/* 2202 */     if (len > this.size) {
/* 2203 */       return false;
/*      */     }
/* 2205 */     for (int i = 0; i < len; i++) {
/* 2206 */       if (this.buffer[i] != str.charAt(i)) {
/* 2207 */         return false;
/*      */       }
/*      */     } 
/* 2210 */     return true;
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
/*      */   public boolean endsWith(String str) {
/* 2222 */     if (str == null) {
/* 2223 */       return false;
/*      */     }
/* 2225 */     int len = str.length();
/* 2226 */     if (len == 0) {
/* 2227 */       return true;
/*      */     }
/* 2229 */     if (len > this.size) {
/* 2230 */       return false;
/*      */     }
/* 2232 */     int pos = this.size - len;
/* 2233 */     for (int i = 0; i < len; i++, pos++) {
/* 2234 */       if (this.buffer[pos] != str.charAt(i)) {
/* 2235 */         return false;
/*      */       }
/*      */     } 
/* 2238 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSequence subSequence(int startIndex, int endIndex) {
/* 2248 */     if (startIndex < 0) {
/* 2249 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/* 2251 */     if (endIndex > this.size) {
/* 2252 */       throw new StringIndexOutOfBoundsException(endIndex);
/*      */     }
/* 2254 */     if (startIndex > endIndex) {
/* 2255 */       throw new StringIndexOutOfBoundsException(endIndex - startIndex);
/*      */     }
/* 2257 */     return substring(startIndex, endIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int start) {
/* 2268 */     return substring(start, this.size);
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
/*      */   public String substring(int startIndex, int endIndex) {
/* 2285 */     endIndex = validateRange(startIndex, endIndex);
/* 2286 */     return new String(this.buffer, startIndex, endIndex - startIndex);
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
/*      */   public String leftString(int length) {
/* 2302 */     if (length <= 0)
/* 2303 */       return ""; 
/* 2304 */     if (length >= this.size) {
/* 2305 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2307 */     return new String(this.buffer, 0, length);
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
/*      */   public String rightString(int length) {
/* 2324 */     if (length <= 0)
/* 2325 */       return ""; 
/* 2326 */     if (length >= this.size) {
/* 2327 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2329 */     return new String(this.buffer, this.size - length, length);
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
/*      */   public String midString(int index, int length) {
/* 2350 */     if (index < 0) {
/* 2351 */       index = 0;
/*      */     }
/* 2353 */     if (length <= 0 || index >= this.size) {
/* 2354 */       return "";
/*      */     }
/* 2356 */     if (this.size <= index + length) {
/* 2357 */       return new String(this.buffer, index, this.size - index);
/*      */     }
/* 2359 */     return new String(this.buffer, index, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(char ch) {
/* 2370 */     char[] thisBuf = this.buffer;
/* 2371 */     for (int i = 0; i < this.size; i++) {
/* 2372 */       if (thisBuf[i] == ch) {
/* 2373 */         return true;
/*      */       }
/*      */     } 
/* 2376 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(String str) {
/* 2386 */     return (indexOf(str, 0) >= 0);
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
/*      */   public boolean contains(StrMatcher matcher) {
/* 2401 */     return (indexOf(matcher, 0) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch) {
/* 2412 */     return indexOf(ch, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch, int startIndex) {
/* 2423 */     startIndex = Math.max(startIndex, 0);
/* 2424 */     if (startIndex >= this.size) {
/* 2425 */       return -1;
/*      */     }
/* 2427 */     char[] thisBuf = this.buffer;
/* 2428 */     for (int i = startIndex; i < this.size; i++) {
/* 2429 */       if (thisBuf[i] == ch) {
/* 2430 */         return i;
/*      */       }
/*      */     } 
/* 2433 */     return -1;
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
/*      */   public int indexOf(String str) {
/* 2445 */     return indexOf(str, 0);
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
/*      */   public int indexOf(String str, int startIndex) {
/* 2459 */     startIndex = Math.max(startIndex, 0);
/* 2460 */     if (str == null || startIndex >= this.size) {
/* 2461 */       return -1;
/*      */     }
/* 2463 */     int strLen = str.length();
/* 2464 */     if (strLen == 1) {
/* 2465 */       return indexOf(str.charAt(0), startIndex);
/*      */     }
/* 2467 */     if (strLen == 0) {
/* 2468 */       return startIndex;
/*      */     }
/* 2470 */     if (strLen > this.size) {
/* 2471 */       return -1;
/*      */     }
/* 2473 */     char[] thisBuf = this.buffer;
/* 2474 */     int len = this.size - strLen + 1;
/*      */     
/* 2476 */     for (int i = startIndex; i < len; i++) {
/* 2477 */       int j = 0; while (true) { if (j < strLen) {
/* 2478 */           if (str.charAt(j) != thisBuf[i + j])
/*      */             break;  j++;
/*      */           continue;
/*      */         } 
/* 2482 */         return i; }
/*      */     
/* 2484 */     }  return -1;
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
/*      */   public int indexOf(StrMatcher matcher) {
/* 2498 */     return indexOf(matcher, 0);
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
/*      */   public int indexOf(StrMatcher matcher, int startIndex) {
/* 2514 */     startIndex = Math.max(startIndex, 0);
/* 2515 */     if (matcher == null || startIndex >= this.size) {
/* 2516 */       return -1;
/*      */     }
/* 2518 */     int len = this.size;
/* 2519 */     char[] buf = this.buffer;
/* 2520 */     for (int i = startIndex; i < len; i++) {
/* 2521 */       if (matcher.isMatch(buf, i, startIndex, len) > 0) {
/* 2522 */         return i;
/*      */       }
/*      */     } 
/* 2525 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(char ch) {
/* 2536 */     return lastIndexOf(ch, this.size - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(char ch, int startIndex) {
/* 2547 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2548 */     if (startIndex < 0) {
/* 2549 */       return -1;
/*      */     }
/* 2551 */     for (int i = startIndex; i >= 0; i--) {
/* 2552 */       if (this.buffer[i] == ch) {
/* 2553 */         return i;
/*      */       }
/*      */     } 
/* 2556 */     return -1;
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
/*      */   public int lastIndexOf(String str) {
/* 2568 */     return lastIndexOf(str, this.size - 1);
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
/*      */   public int lastIndexOf(String str, int startIndex) {
/* 2582 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2583 */     if (str == null || startIndex < 0) {
/* 2584 */       return -1;
/*      */     }
/* 2586 */     int strLen = str.length();
/* 2587 */     if (strLen > 0 && strLen <= this.size) {
/* 2588 */       if (strLen == 1) {
/* 2589 */         return lastIndexOf(str.charAt(0), startIndex);
/*      */       }
/*      */ 
/*      */       
/* 2593 */       for (int i = startIndex - strLen + 1; i >= 0; i--) {
/* 2594 */         int j = 0; while (true) { if (j < strLen) {
/* 2595 */             if (str.charAt(j) != this.buffer[i + j])
/*      */               break;  j++;
/*      */             continue;
/*      */           } 
/* 2599 */           return i; }
/*      */       
/*      */       } 
/* 2602 */     } else if (strLen == 0) {
/* 2603 */       return startIndex;
/*      */     } 
/* 2605 */     return -1;
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
/*      */   public int lastIndexOf(StrMatcher matcher) {
/* 2619 */     return lastIndexOf(matcher, this.size);
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
/*      */   public int lastIndexOf(StrMatcher matcher, int startIndex) {
/* 2635 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2636 */     if (matcher == null || startIndex < 0) {
/* 2637 */       return -1;
/*      */     }
/* 2639 */     char[] buf = this.buffer;
/* 2640 */     int endIndex = startIndex + 1;
/* 2641 */     for (int i = startIndex; i >= 0; i--) {
/* 2642 */       if (matcher.isMatch(buf, i, 0, endIndex) > 0) {
/* 2643 */         return i;
/*      */       }
/*      */     } 
/* 2646 */     return -1;
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
/*      */   public StrTokenizer asTokenizer() {
/* 2683 */     return new StrBuilderTokenizer();
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
/*      */   public Reader asReader() {
/* 2707 */     return new StrBuilderReader();
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
/*      */   public Writer asWriter() {
/* 2732 */     return new StrBuilderWriter();
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
/*      */   public void appendTo(Appendable appendable) throws IOException {
/* 2748 */     if (appendable instanceof Writer) {
/* 2749 */       ((Writer)appendable).write(this.buffer, 0, this.size);
/* 2750 */     } else if (appendable instanceof StringBuilder) {
/* 2751 */       ((StringBuilder)appendable).append(this.buffer, 0, this.size);
/* 2752 */     } else if (appendable instanceof StringBuffer) {
/* 2753 */       ((StringBuffer)appendable).append(this.buffer, 0, this.size);
/* 2754 */     } else if (appendable instanceof CharBuffer) {
/* 2755 */       ((CharBuffer)appendable).put(this.buffer, 0, this.size);
/*      */     } else {
/* 2757 */       appendable.append(this);
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
/*      */   public boolean equalsIgnoreCase(StrBuilder other) {
/* 2769 */     if (this == other) {
/* 2770 */       return true;
/*      */     }
/* 2772 */     if (this.size != other.size) {
/* 2773 */       return false;
/*      */     }
/* 2775 */     char[] thisBuf = this.buffer;
/* 2776 */     char[] otherBuf = other.buffer;
/* 2777 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2778 */       char c1 = thisBuf[i];
/* 2779 */       char c2 = otherBuf[i];
/* 2780 */       if (c1 != c2 && Character.toUpperCase(c1) != Character.toUpperCase(c2)) {
/* 2781 */         return false;
/*      */       }
/*      */     } 
/* 2784 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(StrBuilder other) {
/* 2795 */     if (this == other) {
/* 2796 */       return true;
/*      */     }
/* 2798 */     if (other == null) {
/* 2799 */       return false;
/*      */     }
/* 2801 */     if (this.size != other.size) {
/* 2802 */       return false;
/*      */     }
/* 2804 */     char[] thisBuf = this.buffer;
/* 2805 */     char[] otherBuf = other.buffer;
/* 2806 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2807 */       if (thisBuf[i] != otherBuf[i]) {
/* 2808 */         return false;
/*      */       }
/*      */     } 
/* 2811 */     return true;
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
/*      */   public boolean equals(Object obj) {
/* 2823 */     return (obj instanceof StrBuilder && equals((StrBuilder)obj));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2833 */     char[] buf = this.buffer;
/* 2834 */     int hash = 0;
/* 2835 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2836 */       hash = 31 * hash + buf[i];
/*      */     }
/* 2838 */     return hash;
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
/*      */   public String toString() {
/* 2853 */     return new String(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer toStringBuffer() {
/* 2863 */     return (new StringBuffer(this.size)).append(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuilder toStringBuilder() {
/* 2874 */     return (new StringBuilder(this.size)).append(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String build() {
/* 2885 */     return toString();
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
/*      */   protected int validateRange(int startIndex, int endIndex) {
/* 2899 */     if (startIndex < 0) {
/* 2900 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/* 2902 */     if (endIndex > this.size) {
/* 2903 */       endIndex = this.size;
/*      */     }
/* 2905 */     if (startIndex > endIndex) {
/* 2906 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/* 2908 */     return endIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void validateIndex(int index) {
/* 2918 */     if (index < 0 || index > this.size) {
/* 2919 */       throw new StringIndexOutOfBoundsException(index);
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
/*      */ 
/*      */ 
/*      */   
/*      */   class StrBuilderTokenizer
/*      */     extends StrTokenizer
/*      */   {
/*      */     protected List<String> tokenize(char[] chars, int offset, int count) {
/* 2939 */       if (chars == null) {
/* 2940 */         return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
/*      */       }
/* 2942 */       return super.tokenize(chars, offset, count);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getContent() {
/* 2948 */       String str = super.getContent();
/* 2949 */       if (str == null) {
/* 2950 */         return StrBuilder.this.toString();
/*      */       }
/* 2952 */       return str;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class StrBuilderReader
/*      */     extends Reader
/*      */   {
/*      */     private int pos;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int mark;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int read() {
/* 2982 */       if (!ready()) {
/* 2983 */         return -1;
/*      */       }
/* 2985 */       return StrBuilder.this.charAt(this.pos++);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(char[] b, int off, int len) {
/* 2991 */       if (off < 0 || len < 0 || off > b.length || off + len > b.length || off + len < 0)
/*      */       {
/* 2993 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 2995 */       if (len == 0) {
/* 2996 */         return 0;
/*      */       }
/* 2998 */       if (this.pos >= StrBuilder.this.size()) {
/* 2999 */         return -1;
/*      */       }
/* 3001 */       if (this.pos + len > StrBuilder.this.size()) {
/* 3002 */         len = StrBuilder.this.size() - this.pos;
/*      */       }
/* 3004 */       StrBuilder.this.getChars(this.pos, this.pos + len, b, off);
/* 3005 */       this.pos += len;
/* 3006 */       return len;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 3012 */       if (this.pos + n > StrBuilder.this.size()) {
/* 3013 */         n = (StrBuilder.this.size() - this.pos);
/*      */       }
/* 3015 */       if (n < 0L) {
/* 3016 */         return 0L;
/*      */       }
/* 3018 */       this.pos = (int)(this.pos + n);
/* 3019 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean ready() {
/* 3025 */       return (this.pos < StrBuilder.this.size());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean markSupported() {
/* 3031 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mark(int readAheadLimit) {
/* 3037 */       this.mark = this.pos;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void reset() {
/* 3043 */       this.pos = this.mark;
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
/*      */   class StrBuilderWriter
/*      */     extends Writer
/*      */   {
/*      */     public void close() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int c) {
/* 3075 */       StrBuilder.this.append((char)c);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(char[] cbuf) {
/* 3081 */       StrBuilder.this.append(cbuf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(char[] cbuf, int off, int len) {
/* 3087 */       StrBuilder.this.append(cbuf, off, len);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(String str) {
/* 3093 */       StrBuilder.this.append(str);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(String str, int off, int len) {
/* 3099 */       StrBuilder.this.append(str, off, len);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\StrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */