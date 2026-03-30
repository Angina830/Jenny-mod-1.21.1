/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
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
/*     */ public final class TextBuffer
/*     */ {
/*  29 */   static final char[] NO_CHARS = new char[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int MIN_SEGMENT_LEN = 1000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int MAX_SEGMENT_LEN = 262144;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BufferRecycler _allocator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] _inputBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _inputStart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _inputLen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<char[]> _segments;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _hasSegments;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _segmentSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] _currentSegment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _currentSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String _resultString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] _resultArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBuffer(BufferRecycler allocator) {
/* 122 */     this._allocator = allocator;
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
/*     */   public void releaseBuffers() {
/* 136 */     if (this._allocator == null) {
/* 137 */       resetWithEmpty();
/*     */     }
/* 139 */     else if (this._currentSegment != null) {
/*     */       
/* 141 */       resetWithEmpty();
/*     */       
/* 143 */       char[] buf = this._currentSegment;
/* 144 */       this._currentSegment = null;
/* 145 */       this._allocator.releaseCharBuffer(2, buf);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetWithEmpty() {
/* 156 */     this._inputStart = -1;
/* 157 */     this._currentSize = 0;
/* 158 */     this._inputLen = 0;
/*     */     
/* 160 */     this._inputBuffer = null;
/* 161 */     this._resultString = null;
/* 162 */     this._resultArray = null;
/*     */ 
/*     */     
/* 165 */     if (this._hasSegments) {
/* 166 */       clearSegments();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetWith(char ch) {
/* 175 */     this._inputStart = -1;
/* 176 */     this._inputLen = 0;
/*     */     
/* 178 */     this._resultString = null;
/* 179 */     this._resultArray = null;
/*     */     
/* 181 */     if (this._hasSegments) {
/* 182 */       clearSegments();
/* 183 */     } else if (this._currentSegment == null) {
/* 184 */       this._currentSegment = buf(1);
/*     */     } 
/* 186 */     this._currentSegment[0] = ch;
/* 187 */     this._currentSize = this._segmentSize = 1;
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
/*     */   public void resetWithShared(char[] buf, int start, int len) {
/* 199 */     this._resultString = null;
/* 200 */     this._resultArray = null;
/*     */ 
/*     */     
/* 203 */     this._inputBuffer = buf;
/* 204 */     this._inputStart = start;
/* 205 */     this._inputLen = len;
/*     */ 
/*     */     
/* 208 */     if (this._hasSegments) {
/* 209 */       clearSegments();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetWithCopy(char[] buf, int start, int len) {
/* 215 */     this._inputBuffer = null;
/* 216 */     this._inputStart = -1;
/* 217 */     this._inputLen = 0;
/*     */     
/* 219 */     this._resultString = null;
/* 220 */     this._resultArray = null;
/*     */ 
/*     */     
/* 223 */     if (this._hasSegments) {
/* 224 */       clearSegments();
/* 225 */     } else if (this._currentSegment == null) {
/* 226 */       this._currentSegment = buf(len);
/*     */     } 
/* 228 */     this._currentSize = this._segmentSize = 0;
/* 229 */     append(buf, start, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetWithCopy(String text, int start, int len) {
/* 237 */     this._inputBuffer = null;
/* 238 */     this._inputStart = -1;
/* 239 */     this._inputLen = 0;
/*     */     
/* 241 */     this._resultString = null;
/* 242 */     this._resultArray = null;
/*     */     
/* 244 */     if (this._hasSegments) {
/* 245 */       clearSegments();
/* 246 */     } else if (this._currentSegment == null) {
/* 247 */       this._currentSegment = buf(len);
/*     */     } 
/* 249 */     this._currentSize = this._segmentSize = 0;
/* 250 */     append(text, start, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetWithString(String value) {
/* 255 */     this._inputBuffer = null;
/* 256 */     this._inputStart = -1;
/* 257 */     this._inputLen = 0;
/*     */     
/* 259 */     this._resultString = value;
/* 260 */     this._resultArray = null;
/*     */     
/* 262 */     if (this._hasSegments) {
/* 263 */       clearSegments();
/*     */     }
/* 265 */     this._currentSize = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getBufferWithoutReset() {
/* 273 */     return this._currentSegment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] buf(int needed) {
/* 282 */     if (this._allocator != null) {
/* 283 */       return this._allocator.allocCharBuffer(2, needed);
/*     */     }
/* 285 */     return new char[Math.max(needed, 1000)];
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearSegments() {
/* 290 */     this._hasSegments = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     this._segments.clear();
/* 299 */     this._currentSize = this._segmentSize = 0;
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
/*     */   public int size() {
/* 312 */     if (this._inputStart >= 0) {
/* 313 */       return this._inputLen;
/*     */     }
/* 315 */     if (this._resultArray != null) {
/* 316 */       return this._resultArray.length;
/*     */     }
/* 318 */     if (this._resultString != null) {
/* 319 */       return this._resultString.length();
/*     */     }
/*     */     
/* 322 */     return this._segmentSize + this._currentSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextOffset() {
/* 330 */     return (this._inputStart >= 0) ? this._inputStart : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTextAsCharacters() {
/* 340 */     if (this._inputStart >= 0 || this._resultArray != null) return true;
/*     */     
/* 342 */     if (this._resultString != null) return false; 
/* 343 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getTextBuffer() {
/* 354 */     if (this._inputStart >= 0) return this._inputBuffer; 
/* 355 */     if (this._resultArray != null) return this._resultArray; 
/* 356 */     if (this._resultString != null) {
/* 357 */       return this._resultArray = this._resultString.toCharArray();
/*     */     }
/*     */     
/* 360 */     if (!this._hasSegments) {
/* 361 */       return (this._currentSegment == null) ? NO_CHARS : this._currentSegment;
/*     */     }
/*     */     
/* 364 */     return contentsAsArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String contentsAsString() {
/* 375 */     if (this._resultString == null)
/*     */     {
/* 377 */       if (this._resultArray != null) {
/* 378 */         this._resultString = new String(this._resultArray);
/*     */       
/*     */       }
/* 381 */       else if (this._inputStart >= 0) {
/* 382 */         if (this._inputLen < 1) {
/* 383 */           return this._resultString = "";
/*     */         }
/* 385 */         this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
/*     */       } else {
/*     */         
/* 388 */         int segLen = this._segmentSize;
/* 389 */         int currLen = this._currentSize;
/*     */         
/* 391 */         if (segLen == 0) {
/* 392 */           this._resultString = (currLen == 0) ? "" : new String(this._currentSegment, 0, currLen);
/*     */         } else {
/* 394 */           StringBuilder sb = new StringBuilder(segLen + currLen);
/*     */           
/* 396 */           if (this._segments != null) {
/* 397 */             for (int i = 0, len = this._segments.size(); i < len; i++) {
/* 398 */               char[] curr = this._segments.get(i);
/* 399 */               sb.append(curr, 0, curr.length);
/*     */             } 
/*     */           }
/*     */           
/* 403 */           sb.append(this._currentSegment, 0, this._currentSize);
/* 404 */           this._resultString = sb.toString();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 409 */     return this._resultString;
/*     */   }
/*     */   
/*     */   public char[] contentsAsArray() {
/* 413 */     char[] result = this._resultArray;
/* 414 */     if (result == null) {
/* 415 */       this._resultArray = result = resultArray();
/*     */     }
/* 417 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigDecimal contentsAsDecimal() throws NumberFormatException {
/* 427 */     if (this._resultArray != null) {
/* 428 */       return NumberInput.parseBigDecimal(this._resultArray);
/*     */     }
/*     */     
/* 431 */     if (this._inputStart >= 0 && this._inputBuffer != null) {
/* 432 */       return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
/*     */     }
/*     */     
/* 435 */     if (this._segmentSize == 0 && this._currentSegment != null) {
/* 436 */       return NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize);
/*     */     }
/*     */     
/* 439 */     return NumberInput.parseBigDecimal(contentsAsArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double contentsAsDouble() throws NumberFormatException {
/* 447 */     return NumberInput.parseDouble(contentsAsString());
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
/*     */   public int contentsAsInt(boolean neg) {
/* 459 */     if (this._inputStart >= 0 && this._inputBuffer != null) {
/* 460 */       if (neg) {
/* 461 */         return -NumberInput.parseInt(this._inputBuffer, this._inputStart + 1, this._inputLen - 1);
/*     */       }
/* 463 */       return NumberInput.parseInt(this._inputBuffer, this._inputStart, this._inputLen);
/*     */     } 
/* 465 */     if (neg) {
/* 466 */       return -NumberInput.parseInt(this._currentSegment, 1, this._currentSize - 1);
/*     */     }
/* 468 */     return NumberInput.parseInt(this._currentSegment, 0, this._currentSize);
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
/*     */   public long contentsAsLong(boolean neg) {
/* 480 */     if (this._inputStart >= 0 && this._inputBuffer != null) {
/* 481 */       if (neg) {
/* 482 */         return -NumberInput.parseLong(this._inputBuffer, this._inputStart + 1, this._inputLen - 1);
/*     */       }
/* 484 */       return NumberInput.parseLong(this._inputBuffer, this._inputStart, this._inputLen);
/*     */     } 
/* 486 */     if (neg) {
/* 487 */       return -NumberInput.parseLong(this._currentSegment, 1, this._currentSize - 1);
/*     */     }
/* 489 */     return NumberInput.parseLong(this._currentSegment, 0, this._currentSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int contentsToWriter(Writer w) throws IOException {
/* 497 */     if (this._resultArray != null) {
/* 498 */       w.write(this._resultArray);
/* 499 */       return this._resultArray.length;
/*     */     } 
/* 501 */     if (this._resultString != null) {
/* 502 */       w.write(this._resultString);
/* 503 */       return this._resultString.length();
/*     */     } 
/*     */     
/* 506 */     if (this._inputStart >= 0) {
/* 507 */       int i = this._inputLen;
/* 508 */       if (i > 0) {
/* 509 */         w.write(this._inputBuffer, this._inputStart, i);
/*     */       }
/* 511 */       return i;
/*     */     } 
/*     */     
/* 514 */     int total = 0;
/* 515 */     if (this._segments != null) {
/* 516 */       for (int i = 0, end = this._segments.size(); i < end; i++) {
/* 517 */         char[] curr = this._segments.get(i);
/* 518 */         int currLen = curr.length;
/* 519 */         w.write(curr, 0, currLen);
/* 520 */         total += currLen;
/*     */       } 
/*     */     }
/* 523 */     int len = this._currentSize;
/* 524 */     if (len > 0) {
/* 525 */       w.write(this._currentSegment, 0, len);
/* 526 */       total += len;
/*     */     } 
/* 528 */     return total;
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
/*     */   public void ensureNotShared() {
/* 542 */     if (this._inputStart >= 0) {
/* 543 */       unshare(16);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(char c) {
/* 549 */     if (this._inputStart >= 0) {
/* 550 */       unshare(16);
/*     */     }
/* 552 */     this._resultString = null;
/* 553 */     this._resultArray = null;
/*     */     
/* 555 */     char[] curr = this._currentSegment;
/* 556 */     if (this._currentSize >= curr.length) {
/* 557 */       expand(1);
/* 558 */       curr = this._currentSegment;
/*     */     } 
/* 560 */     curr[this._currentSize++] = c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(char[] c, int start, int len) {
/* 566 */     if (this._inputStart >= 0) {
/* 567 */       unshare(len);
/*     */     }
/* 569 */     this._resultString = null;
/* 570 */     this._resultArray = null;
/*     */ 
/*     */     
/* 573 */     char[] curr = this._currentSegment;
/* 574 */     int max = curr.length - this._currentSize;
/*     */     
/* 576 */     if (max >= len) {
/* 577 */       System.arraycopy(c, start, curr, this._currentSize, len);
/* 578 */       this._currentSize += len;
/*     */       
/*     */       return;
/*     */     } 
/* 582 */     if (max > 0) {
/* 583 */       System.arraycopy(c, start, curr, this._currentSize, max);
/* 584 */       start += max;
/* 585 */       len -= max;
/*     */     } 
/*     */ 
/*     */     
/*     */     do {
/* 590 */       expand(len);
/* 591 */       int amount = Math.min(this._currentSegment.length, len);
/* 592 */       System.arraycopy(c, start, this._currentSegment, 0, amount);
/* 593 */       this._currentSize += amount;
/* 594 */       start += amount;
/* 595 */       len -= amount;
/* 596 */     } while (len > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(String str, int offset, int len) {
/* 602 */     if (this._inputStart >= 0) {
/* 603 */       unshare(len);
/*     */     }
/* 605 */     this._resultString = null;
/* 606 */     this._resultArray = null;
/*     */ 
/*     */     
/* 609 */     char[] curr = this._currentSegment;
/* 610 */     int max = curr.length - this._currentSize;
/* 611 */     if (max >= len) {
/* 612 */       str.getChars(offset, offset + len, curr, this._currentSize);
/* 613 */       this._currentSize += len;
/*     */       
/*     */       return;
/*     */     } 
/* 617 */     if (max > 0) {
/* 618 */       str.getChars(offset, offset + max, curr, this._currentSize);
/* 619 */       len -= max;
/* 620 */       offset += max;
/*     */     } 
/*     */ 
/*     */     
/*     */     do {
/* 625 */       expand(len);
/* 626 */       int amount = Math.min(this._currentSegment.length, len);
/* 627 */       str.getChars(offset, offset + amount, this._currentSegment, 0);
/* 628 */       this._currentSize += amount;
/* 629 */       offset += amount;
/* 630 */       len -= amount;
/* 631 */     } while (len > 0);
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
/*     */   public char[] getCurrentSegment() {
/* 646 */     if (this._inputStart >= 0) {
/* 647 */       unshare(1);
/*     */     } else {
/* 649 */       char[] curr = this._currentSegment;
/* 650 */       if (curr == null) {
/* 651 */         this._currentSegment = buf(0);
/* 652 */       } else if (this._currentSize >= curr.length) {
/*     */         
/* 654 */         expand(1);
/*     */       } 
/*     */     } 
/* 657 */     return this._currentSegment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] emptyAndGetCurrentSegment() {
/* 663 */     this._inputStart = -1;
/* 664 */     this._currentSize = 0;
/* 665 */     this._inputLen = 0;
/*     */     
/* 667 */     this._inputBuffer = null;
/* 668 */     this._resultString = null;
/* 669 */     this._resultArray = null;
/*     */ 
/*     */     
/* 672 */     if (this._hasSegments) {
/* 673 */       clearSegments();
/*     */     }
/* 675 */     char[] curr = this._currentSegment;
/* 676 */     if (curr == null) {
/* 677 */       this._currentSegment = curr = buf(0);
/*     */     }
/* 679 */     return curr;
/*     */   }
/*     */   
/* 682 */   public int getCurrentSegmentSize() { return this._currentSize; } public void setCurrentLength(int len) {
/* 683 */     this._currentSize = len;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String setCurrentAndReturn(int len) {
/* 689 */     this._currentSize = len;
/*     */     
/* 691 */     if (this._segmentSize > 0) {
/* 692 */       return contentsAsString();
/*     */     }
/*     */     
/* 695 */     int currLen = this._currentSize;
/* 696 */     String str = (currLen == 0) ? "" : new String(this._currentSegment, 0, currLen);
/* 697 */     this._resultString = str;
/* 698 */     return str;
/*     */   }
/*     */   
/*     */   public char[] finishCurrentSegment() {
/* 702 */     if (this._segments == null) {
/* 703 */       this._segments = (ArrayList)new ArrayList<char>();
/*     */     }
/* 705 */     this._hasSegments = true;
/* 706 */     this._segments.add(this._currentSegment);
/* 707 */     int oldLen = this._currentSegment.length;
/* 708 */     this._segmentSize += oldLen;
/* 709 */     this._currentSize = 0;
/*     */ 
/*     */     
/* 712 */     int newLen = oldLen + (oldLen >> 1);
/* 713 */     if (newLen < 1000) {
/* 714 */       newLen = 1000;
/* 715 */     } else if (newLen > 262144) {
/* 716 */       newLen = 262144;
/*     */     } 
/* 718 */     char[] curr = carr(newLen);
/* 719 */     this._currentSegment = curr;
/* 720 */     return curr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] expandCurrentSegment() {
/* 730 */     char[] curr = this._currentSegment;
/*     */     
/* 732 */     int len = curr.length;
/* 733 */     int newLen = len + (len >> 1);
/*     */     
/* 735 */     if (newLen > 262144) {
/* 736 */       newLen = len + (len >> 2);
/*     */     }
/* 738 */     return this._currentSegment = Arrays.copyOf(curr, newLen);
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
/*     */   public char[] expandCurrentSegment(int minSize) {
/* 751 */     char[] curr = this._currentSegment;
/* 752 */     if (curr.length >= minSize) return curr; 
/* 753 */     this._currentSegment = curr = Arrays.copyOf(curr, minSize);
/* 754 */     return curr;
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
/*     */   public String toString() {
/* 768 */     return contentsAsString();
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
/*     */   private void unshare(int needExtra) {
/* 782 */     int sharedLen = this._inputLen;
/* 783 */     this._inputLen = 0;
/* 784 */     char[] inputBuf = this._inputBuffer;
/* 785 */     this._inputBuffer = null;
/* 786 */     int start = this._inputStart;
/* 787 */     this._inputStart = -1;
/*     */ 
/*     */     
/* 790 */     int needed = sharedLen + needExtra;
/* 791 */     if (this._currentSegment == null || needed > this._currentSegment.length) {
/* 792 */       this._currentSegment = buf(needed);
/*     */     }
/* 794 */     if (sharedLen > 0) {
/* 795 */       System.arraycopy(inputBuf, start, this._currentSegment, 0, sharedLen);
/*     */     }
/* 797 */     this._segmentSize = 0;
/* 798 */     this._currentSize = sharedLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void expand(int minNewSegmentSize) {
/* 808 */     if (this._segments == null) {
/* 809 */       this._segments = (ArrayList)new ArrayList<char>();
/*     */     }
/* 811 */     char[] curr = this._currentSegment;
/* 812 */     this._hasSegments = true;
/* 813 */     this._segments.add(curr);
/* 814 */     this._segmentSize += curr.length;
/* 815 */     this._currentSize = 0;
/* 816 */     int oldLen = curr.length;
/*     */ 
/*     */     
/* 819 */     int newLen = oldLen + (oldLen >> 1);
/* 820 */     if (newLen < 1000) {
/* 821 */       newLen = 1000;
/* 822 */     } else if (newLen > 262144) {
/* 823 */       newLen = 262144;
/*     */     } 
/* 825 */     this._currentSegment = carr(newLen);
/*     */   }
/*     */ 
/*     */   
/*     */   private char[] resultArray() {
/* 830 */     if (this._resultString != null) {
/* 831 */       return this._resultString.toCharArray();
/*     */     }
/*     */     
/* 834 */     if (this._inputStart >= 0) {
/* 835 */       int len = this._inputLen;
/* 836 */       if (len < 1) {
/* 837 */         return NO_CHARS;
/*     */       }
/* 839 */       int start = this._inputStart;
/* 840 */       if (start == 0) {
/* 841 */         return Arrays.copyOf(this._inputBuffer, len);
/*     */       }
/* 843 */       return Arrays.copyOfRange(this._inputBuffer, start, start + len);
/*     */     } 
/*     */     
/* 846 */     int size = size();
/* 847 */     if (size < 1) {
/* 848 */       return NO_CHARS;
/*     */     }
/* 850 */     int offset = 0;
/* 851 */     char[] result = carr(size);
/* 852 */     if (this._segments != null) {
/* 853 */       for (int i = 0, len = this._segments.size(); i < len; i++) {
/* 854 */         char[] curr = this._segments.get(i);
/* 855 */         int currLen = curr.length;
/* 856 */         System.arraycopy(curr, 0, result, offset, currLen);
/* 857 */         offset += currLen;
/*     */       } 
/*     */     }
/* 860 */     System.arraycopy(this._currentSegment, 0, result, offset, this._currentSize);
/* 861 */     return result;
/*     */   }
/*     */   private char[] carr(int len) {
/* 864 */     return new char[len];
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\TextBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */