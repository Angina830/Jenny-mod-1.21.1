/*      */ package software.bernie.shadowed.fasterxml.jackson.core.sym;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.util.InternCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ByteQuadsCanonicalizer
/*      */ {
/*      */   private static final int DEFAULT_T_SIZE = 64;
/*      */   private static final int MAX_T_SIZE = 65536;
/*      */   private static final int MIN_HASH_SIZE = 16;
/*      */   static final int MAX_ENTRIES_FOR_REUSE = 6000;
/*      */   private final ByteQuadsCanonicalizer _parent;
/*      */   private final AtomicReference<TableInfo> _tableInfo;
/*      */   private final int _seed;
/*      */   private boolean _intern;
/*      */   private final boolean _failOnDoS;
/*      */   private int[] _hashArea;
/*      */   private int _hashSize;
/*      */   private int _secondaryStart;
/*      */   private int _tertiaryStart;
/*      */   private int _tertiaryShift;
/*      */   private int _count;
/*      */   private String[] _names;
/*      */   private int _spilloverEnd;
/*      */   private int _longNameOffset;
/*      */   private transient boolean _needRehash;
/*      */   private boolean _hashShared;
/*      */   private static final int MULT = 33;
/*      */   private static final int MULT2 = 65599;
/*      */   private static final int MULT3 = 31;
/*      */   
/*      */   private ByteQuadsCanonicalizer(int sz, boolean intern, int seed, boolean failOnDoS) {
/*  223 */     this._parent = null;
/*  224 */     this._seed = seed;
/*  225 */     this._intern = intern;
/*  226 */     this._failOnDoS = failOnDoS;
/*      */     
/*  228 */     if (sz < 16) {
/*  229 */       sz = 16;
/*      */ 
/*      */     
/*      */     }
/*  233 */     else if ((sz & sz - 1) != 0) {
/*  234 */       int curr = 16;
/*  235 */       while (curr < sz) {
/*  236 */         curr += curr;
/*      */       }
/*  238 */       sz = curr;
/*      */     } 
/*      */     
/*  241 */     this._tableInfo = new AtomicReference<TableInfo>(TableInfo.createInitial(sz));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer parent, boolean intern, int seed, boolean failOnDoS, TableInfo state) {
/*  250 */     this._parent = parent;
/*  251 */     this._seed = seed;
/*  252 */     this._intern = intern;
/*  253 */     this._failOnDoS = failOnDoS;
/*  254 */     this._tableInfo = null;
/*      */ 
/*      */     
/*  257 */     this._count = state.count;
/*  258 */     this._hashSize = state.size;
/*  259 */     this._secondaryStart = this._hashSize << 2;
/*  260 */     this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
/*  261 */     this._tertiaryShift = state.tertiaryShift;
/*      */     
/*  263 */     this._hashArea = state.mainHash;
/*  264 */     this._names = state.names;
/*      */     
/*  266 */     this._spilloverEnd = state.spilloverEnd;
/*  267 */     this._longNameOffset = state.longNameOffset;
/*      */ 
/*      */     
/*  270 */     this._needRehash = false;
/*  271 */     this._hashShared = true;
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
/*      */   public static ByteQuadsCanonicalizer createRoot() {
/*  287 */     long now = System.currentTimeMillis();
/*      */     
/*  289 */     int seed = (int)now + (int)(now >>> 32L) | 0x1;
/*  290 */     return createRoot(seed);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ByteQuadsCanonicalizer createRoot(int seed) {
/*  296 */     return new ByteQuadsCanonicalizer(64, true, seed, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteQuadsCanonicalizer makeChild(int flags) {
/*  304 */     return new ByteQuadsCanonicalizer(this, JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(flags), this._seed, JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(flags), this._tableInfo.get());
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
/*      */   public void release() {
/*  320 */     if (this._parent != null && maybeDirty()) {
/*  321 */       this._parent.mergeChild(new TableInfo(this));
/*      */ 
/*      */       
/*  324 */       this._hashShared = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void mergeChild(TableInfo childState) {
/*  330 */     int childCount = childState.count;
/*  331 */     TableInfo currState = this._tableInfo.get();
/*      */ 
/*      */ 
/*      */     
/*  335 */     if (childCount == currState.count) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  343 */     if (childCount > 6000)
/*      */     {
/*  345 */       childState = TableInfo.createInitial(64);
/*      */     }
/*  347 */     this._tableInfo.compareAndSet(currState, childState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  358 */     if (this._tableInfo != null) {
/*  359 */       return ((TableInfo)this._tableInfo.get()).count;
/*      */     }
/*      */     
/*  362 */     return this._count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int bucketCount() {
/*  368 */     return this._hashSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean maybeDirty() {
/*  375 */     return !this._hashShared;
/*      */   } public int hashSeed() {
/*  377 */     return this._seed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int primaryCount() {
/*  386 */     int count = 0;
/*  387 */     for (int offset = 3, end = this._secondaryStart; offset < end; offset += 4) {
/*  388 */       if (this._hashArea[offset] != 0) {
/*  389 */         count++;
/*      */       }
/*      */     } 
/*  392 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int secondaryCount() {
/*  400 */     int count = 0;
/*  401 */     int offset = this._secondaryStart + 3;
/*  402 */     for (int end = this._tertiaryStart; offset < end; offset += 4) {
/*  403 */       if (this._hashArea[offset] != 0) {
/*  404 */         count++;
/*      */       }
/*      */     } 
/*  407 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int tertiaryCount() {
/*  415 */     int count = 0;
/*  416 */     int offset = this._tertiaryStart + 3;
/*  417 */     for (int end = offset + this._hashSize; offset < end; offset += 4) {
/*  418 */       if (this._hashArea[offset] != 0) {
/*  419 */         count++;
/*      */       }
/*      */     } 
/*  422 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int spilloverCount() {
/*  431 */     return this._spilloverEnd - _spilloverStart() >> 2;
/*      */   }
/*      */ 
/*      */   
/*      */   public int totalCount() {
/*  436 */     int count = 0;
/*  437 */     for (int offset = 3, end = this._hashSize << 3; offset < end; offset += 4) {
/*  438 */       if (this._hashArea[offset] != 0) {
/*  439 */         count++;
/*      */       }
/*      */     } 
/*  442 */     return count;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  447 */     int pri = primaryCount();
/*  448 */     int sec = secondaryCount();
/*  449 */     int tert = tertiaryCount();
/*  450 */     int spill = spilloverCount();
/*  451 */     int total = totalCount();
/*  452 */     return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", new Object[] { getClass().getName(), Integer.valueOf(this._count), Integer.valueOf(this._hashSize), Integer.valueOf(pri), Integer.valueOf(sec), Integer.valueOf(tert), Integer.valueOf(spill), Integer.valueOf(pri + sec + tert + spill), Integer.valueOf(total) });
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
/*      */   public String findName(int q1) {
/*  465 */     int offset = _calcOffset(calcHash(q1));
/*      */     
/*  467 */     int[] hashArea = this._hashArea;
/*      */     
/*  469 */     int len = hashArea[offset + 3];
/*      */     
/*  471 */     if (len == 1) {
/*  472 */       if (hashArea[offset] == q1) {
/*  473 */         return this._names[offset >> 2];
/*      */       }
/*  475 */     } else if (len == 0) {
/*  476 */       return null;
/*      */     } 
/*      */     
/*  479 */     int offset2 = this._secondaryStart + (offset >> 3 << 2);
/*      */     
/*  481 */     len = hashArea[offset2 + 3];
/*      */     
/*  483 */     if (len == 1) {
/*  484 */       if (hashArea[offset2] == q1) {
/*  485 */         return this._names[offset2 >> 2];
/*      */       }
/*  487 */     } else if (len == 0) {
/*  488 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  492 */     return _findSecondary(offset, q1);
/*      */   }
/*      */ 
/*      */   
/*      */   public String findName(int q1, int q2) {
/*  497 */     int offset = _calcOffset(calcHash(q1, q2));
/*      */     
/*  499 */     int[] hashArea = this._hashArea;
/*      */     
/*  501 */     int len = hashArea[offset + 3];
/*      */     
/*  503 */     if (len == 2) {
/*  504 */       if (q1 == hashArea[offset] && q2 == hashArea[offset + 1]) {
/*  505 */         return this._names[offset >> 2];
/*      */       }
/*  507 */     } else if (len == 0) {
/*  508 */       return null;
/*      */     } 
/*      */     
/*  511 */     int offset2 = this._secondaryStart + (offset >> 3 << 2);
/*      */     
/*  513 */     len = hashArea[offset2 + 3];
/*      */     
/*  515 */     if (len == 2) {
/*  516 */       if (q1 == hashArea[offset2] && q2 == hashArea[offset2 + 1]) {
/*  517 */         return this._names[offset2 >> 2];
/*      */       }
/*  519 */     } else if (len == 0) {
/*  520 */       return null;
/*      */     } 
/*  522 */     return _findSecondary(offset, q1, q2);
/*      */   }
/*      */ 
/*      */   
/*      */   public String findName(int q1, int q2, int q3) {
/*  527 */     int offset = _calcOffset(calcHash(q1, q2, q3));
/*  528 */     int[] hashArea = this._hashArea;
/*  529 */     int len = hashArea[offset + 3];
/*      */     
/*  531 */     if (len == 3) {
/*  532 */       if (q1 == hashArea[offset] && hashArea[offset + 1] == q2 && hashArea[offset + 2] == q3) {
/*  533 */         return this._names[offset >> 2];
/*      */       }
/*  535 */     } else if (len == 0) {
/*  536 */       return null;
/*      */     } 
/*      */     
/*  539 */     int offset2 = this._secondaryStart + (offset >> 3 << 2);
/*      */     
/*  541 */     len = hashArea[offset2 + 3];
/*      */     
/*  543 */     if (len == 3) {
/*  544 */       if (q1 == hashArea[offset2] && hashArea[offset2 + 1] == q2 && hashArea[offset2 + 2] == q3) {
/*  545 */         return this._names[offset2 >> 2];
/*      */       }
/*  547 */     } else if (len == 0) {
/*  548 */       return null;
/*      */     } 
/*  550 */     return _findSecondary(offset, q1, q2, q3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String findName(int[] q, int qlen) {
/*  559 */     if (qlen < 4) {
/*  560 */       switch (qlen) {
/*      */         case 3:
/*  562 */           return findName(q[0], q[1], q[2]);
/*      */         case 2:
/*  564 */           return findName(q[0], q[1]);
/*      */         case 1:
/*  566 */           return findName(q[0]);
/*      */       } 
/*  568 */       return "";
/*      */     } 
/*      */     
/*  571 */     int hash = calcHash(q, qlen);
/*  572 */     int offset = _calcOffset(hash);
/*      */     
/*  574 */     int[] hashArea = this._hashArea;
/*      */     
/*  576 */     int len = hashArea[offset + 3];
/*      */     
/*  578 */     if (hash == hashArea[offset] && len == qlen)
/*      */     {
/*  580 */       if (_verifyLongName(q, qlen, hashArea[offset + 1])) {
/*  581 */         return this._names[offset >> 2];
/*      */       }
/*      */     }
/*  584 */     if (len == 0) {
/*  585 */       return null;
/*      */     }
/*      */     
/*  588 */     int offset2 = this._secondaryStart + (offset >> 3 << 2);
/*      */     
/*  590 */     int len2 = hashArea[offset2 + 3];
/*  591 */     if (hash == hashArea[offset2] && len2 == qlen && 
/*  592 */       _verifyLongName(q, qlen, hashArea[offset2 + 1])) {
/*  593 */       return this._names[offset2 >> 2];
/*      */     }
/*      */     
/*  596 */     return _findSecondary(offset, hash, q, qlen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int _calcOffset(int hash) {
/*  604 */     int ix = hash & this._hashSize - 1;
/*      */     
/*  606 */     return ix << 2;
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
/*      */   private String _findSecondary(int origOffset, int q1) {
/*  621 */     int offset = this._tertiaryStart + (origOffset >> this._tertiaryShift + 2 << this._tertiaryShift);
/*  622 */     int[] hashArea = this._hashArea;
/*  623 */     int bucketSize = 1 << this._tertiaryShift;
/*  624 */     for (int end = offset + bucketSize; offset < end; offset += 4) {
/*  625 */       int len = hashArea[offset + 3];
/*  626 */       if (q1 == hashArea[offset] && 1 == len) {
/*  627 */         return this._names[offset >> 2];
/*      */       }
/*  629 */       if (len == 0) {
/*  630 */         return null;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  636 */     for (offset = _spilloverStart(); offset < this._spilloverEnd; offset += 4) {
/*  637 */       if (q1 == hashArea[offset] && 1 == hashArea[offset + 3]) {
/*  638 */         return this._names[offset >> 2];
/*      */       }
/*      */     } 
/*  641 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private String _findSecondary(int origOffset, int q1, int q2) {
/*  646 */     int offset = this._tertiaryStart + (origOffset >> this._tertiaryShift + 2 << this._tertiaryShift);
/*  647 */     int[] hashArea = this._hashArea;
/*      */     
/*  649 */     int bucketSize = 1 << this._tertiaryShift;
/*  650 */     for (int end = offset + bucketSize; offset < end; offset += 4) {
/*  651 */       int len = hashArea[offset + 3];
/*  652 */       if (q1 == hashArea[offset] && q2 == hashArea[offset + 1] && 2 == len) {
/*  653 */         return this._names[offset >> 2];
/*      */       }
/*  655 */       if (len == 0) {
/*  656 */         return null;
/*      */       }
/*      */     } 
/*  659 */     for (offset = _spilloverStart(); offset < this._spilloverEnd; offset += 4) {
/*  660 */       if (q1 == hashArea[offset] && q2 == hashArea[offset + 1] && 2 == hashArea[offset + 3]) {
/*  661 */         return this._names[offset >> 2];
/*      */       }
/*      */     } 
/*  664 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private String _findSecondary(int origOffset, int q1, int q2, int q3) {
/*  669 */     int offset = this._tertiaryStart + (origOffset >> this._tertiaryShift + 2 << this._tertiaryShift);
/*  670 */     int[] hashArea = this._hashArea;
/*      */     
/*  672 */     int bucketSize = 1 << this._tertiaryShift;
/*  673 */     for (int end = offset + bucketSize; offset < end; offset += 4) {
/*  674 */       int len = hashArea[offset + 3];
/*  675 */       if (q1 == hashArea[offset] && q2 == hashArea[offset + 1] && q3 == hashArea[offset + 2] && 3 == len) {
/*  676 */         return this._names[offset >> 2];
/*      */       }
/*  678 */       if (len == 0) {
/*  679 */         return null;
/*      */       }
/*      */     } 
/*  682 */     for (offset = _spilloverStart(); offset < this._spilloverEnd; offset += 4) {
/*  683 */       if (q1 == hashArea[offset] && q2 == hashArea[offset + 1] && q3 == hashArea[offset + 2] && 3 == hashArea[offset + 3])
/*      */       {
/*  685 */         return this._names[offset >> 2];
/*      */       }
/*      */     } 
/*  688 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private String _findSecondary(int origOffset, int hash, int[] q, int qlen) {
/*  693 */     int offset = this._tertiaryStart + (origOffset >> this._tertiaryShift + 2 << this._tertiaryShift);
/*  694 */     int[] hashArea = this._hashArea;
/*      */     
/*  696 */     int bucketSize = 1 << this._tertiaryShift;
/*  697 */     for (int end = offset + bucketSize; offset < end; offset += 4) {
/*  698 */       int len = hashArea[offset + 3];
/*  699 */       if (hash == hashArea[offset] && qlen == len && 
/*  700 */         _verifyLongName(q, qlen, hashArea[offset + 1])) {
/*  701 */         return this._names[offset >> 2];
/*      */       }
/*      */       
/*  704 */       if (len == 0) {
/*  705 */         return null;
/*      */       }
/*      */     } 
/*  708 */     for (offset = _spilloverStart(); offset < this._spilloverEnd; offset += 4) {
/*  709 */       if (hash == hashArea[offset] && qlen == hashArea[offset + 3] && 
/*  710 */         _verifyLongName(q, qlen, hashArea[offset + 1])) {
/*  711 */         return this._names[offset >> 2];
/*      */       }
/*      */     } 
/*      */     
/*  715 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean _verifyLongName(int[] q, int qlen, int spillOffset) {
/*  720 */     int[] hashArea = this._hashArea;
/*      */     
/*  722 */     int ix = 0;
/*      */     
/*  724 */     switch (qlen)
/*      */     { default:
/*  726 */         return _verifyLongName2(q, qlen, spillOffset);
/*      */       case 8:
/*  728 */         if (q[ix++] != hashArea[spillOffset++]) return false; 
/*      */       case 7:
/*  730 */         if (q[ix++] != hashArea[spillOffset++]) return false; 
/*      */       case 6:
/*  732 */         if (q[ix++] != hashArea[spillOffset++]) return false; 
/*      */       case 5:
/*  734 */         if (q[ix++] != hashArea[spillOffset++]) return false;  break;
/*      */       case 4:
/*  736 */         break; }  if (q[ix++] != hashArea[spillOffset++]) return false; 
/*  737 */     if (q[ix++] != hashArea[spillOffset++]) return false; 
/*  738 */     if (q[ix++] != hashArea[spillOffset++]) return false; 
/*  739 */     if (q[ix++] != hashArea[spillOffset++]) return false;
/*      */     
/*  741 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean _verifyLongName2(int[] q, int qlen, int spillOffset) {
/*  746 */     int ix = 0;
/*      */     while (true) {
/*  748 */       if (q[ix++] != this._hashArea[spillOffset++]) {
/*  749 */         return false;
/*      */       }
/*  751 */       if (ix >= qlen) {
/*  752 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String addName(String name, int q1) {
/*  762 */     _verifySharing();
/*  763 */     if (this._intern) {
/*  764 */       name = InternCache.instance.intern(name);
/*      */     }
/*  766 */     int offset = _findOffsetForAdd(calcHash(q1));
/*  767 */     this._hashArea[offset] = q1;
/*  768 */     this._hashArea[offset + 3] = 1;
/*  769 */     this._names[offset >> 2] = name;
/*  770 */     this._count++;
/*  771 */     _verifyNeedForRehash();
/*  772 */     return name;
/*      */   }
/*      */   
/*      */   public String addName(String name, int q1, int q2) {
/*  776 */     _verifySharing();
/*  777 */     if (this._intern) {
/*  778 */       name = InternCache.instance.intern(name);
/*      */     }
/*  780 */     int hash = (q2 == 0) ? calcHash(q1) : calcHash(q1, q2);
/*  781 */     int offset = _findOffsetForAdd(hash);
/*  782 */     this._hashArea[offset] = q1;
/*  783 */     this._hashArea[offset + 1] = q2;
/*  784 */     this._hashArea[offset + 3] = 2;
/*  785 */     this._names[offset >> 2] = name;
/*  786 */     this._count++;
/*  787 */     _verifyNeedForRehash();
/*  788 */     return name;
/*      */   }
/*      */   
/*      */   public String addName(String name, int q1, int q2, int q3) {
/*  792 */     _verifySharing();
/*  793 */     if (this._intern) {
/*  794 */       name = InternCache.instance.intern(name);
/*      */     }
/*  796 */     int offset = _findOffsetForAdd(calcHash(q1, q2, q3));
/*  797 */     this._hashArea[offset] = q1;
/*  798 */     this._hashArea[offset + 1] = q2;
/*  799 */     this._hashArea[offset + 2] = q3;
/*  800 */     this._hashArea[offset + 3] = 3;
/*  801 */     this._names[offset >> 2] = name;
/*  802 */     this._count++;
/*  803 */     _verifyNeedForRehash();
/*  804 */     return name;
/*      */   }
/*      */ 
/*      */   
/*      */   public String addName(String name, int[] q, int qlen) {
/*  809 */     _verifySharing();
/*  810 */     if (this._intern) {
/*  811 */       name = InternCache.instance.intern(name);
/*      */     }
/*      */ 
/*      */     
/*  815 */     switch (qlen)
/*      */     
/*      */     { case 1:
/*  818 */         offset = _findOffsetForAdd(calcHash(q[0]));
/*  819 */         this._hashArea[offset] = q[0];
/*  820 */         this._hashArea[offset + 3] = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  850 */         this._names[offset >> 2] = name;
/*      */ 
/*      */         
/*  853 */         this._count++;
/*  854 */         _verifyNeedForRehash();
/*  855 */         return name;case 2: offset = _findOffsetForAdd(calcHash(q[0], q[1])); this._hashArea[offset] = q[0]; this._hashArea[offset + 1] = q[1]; this._hashArea[offset + 3] = 2; this._names[offset >> 2] = name; this._count++; _verifyNeedForRehash(); return name;case 3: offset = _findOffsetForAdd(calcHash(q[0], q[1], q[2])); this._hashArea[offset] = q[0]; this._hashArea[offset + 1] = q[1]; this._hashArea[offset + 2] = q[2]; this._hashArea[offset + 3] = 3; this._names[offset >> 2] = name; this._count++; _verifyNeedForRehash(); return name; }  int hash = calcHash(q, qlen); int offset = _findOffsetForAdd(hash); this._hashArea[offset] = hash; int longStart = _appendLongName(q, qlen); this._hashArea[offset + 1] = longStart; this._hashArea[offset + 3] = qlen; this._names[offset >> 2] = name; this._count++; _verifyNeedForRehash(); return name;
/*      */   }
/*      */ 
/*      */   
/*      */   private void _verifyNeedForRehash() {
/*  860 */     if (this._count > this._hashSize >> 1) {
/*  861 */       int spillCount = this._spilloverEnd - _spilloverStart() >> 2;
/*  862 */       if (spillCount > 1 + this._count >> 7 || this._count > this._hashSize * 0.8D)
/*      */       {
/*  864 */         this._needRehash = true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void _verifySharing() {
/*  871 */     if (this._hashShared) {
/*  872 */       this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length);
/*  873 */       this._names = Arrays.<String>copyOf(this._names, this._names.length);
/*  874 */       this._hashShared = false;
/*      */ 
/*      */       
/*  877 */       _verifyNeedForRehash();
/*      */     } 
/*  879 */     if (this._needRehash) {
/*  880 */       rehash();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _findOffsetForAdd(int hash) {
/*  890 */     int offset = _calcOffset(hash);
/*  891 */     int[] hashArea = this._hashArea;
/*  892 */     if (hashArea[offset + 3] == 0)
/*      */     {
/*  894 */       return offset;
/*      */     }
/*      */     
/*  897 */     int offset2 = this._secondaryStart + (offset >> 3 << 2);
/*  898 */     if (hashArea[offset2 + 3] == 0)
/*      */     {
/*  900 */       return offset2;
/*      */     }
/*      */ 
/*      */     
/*  904 */     offset2 = this._tertiaryStart + (offset >> this._tertiaryShift + 2 << this._tertiaryShift);
/*  905 */     int bucketSize = 1 << this._tertiaryShift; int end;
/*  906 */     for (end = offset2 + bucketSize; offset2 < end; offset2 += 4) {
/*  907 */       if (hashArea[offset2 + 3] == 0)
/*      */       {
/*  909 */         return offset2;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  914 */     offset = this._spilloverEnd;
/*  915 */     this._spilloverEnd += 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  925 */     end = this._hashSize << 3;
/*  926 */     if (this._spilloverEnd >= end) {
/*  927 */       if (this._failOnDoS) {
/*  928 */         _reportTooManyCollisions();
/*      */       }
/*      */ 
/*      */       
/*  932 */       this._needRehash = true;
/*      */     } 
/*  934 */     return offset;
/*      */   }
/*      */ 
/*      */   
/*      */   private int _appendLongName(int[] quads, int qlen) {
/*  939 */     int start = this._longNameOffset;
/*      */ 
/*      */     
/*  942 */     if (start + qlen > this._hashArea.length) {
/*      */       
/*  944 */       int toAdd = start + qlen - this._hashArea.length;
/*      */       
/*  946 */       int minAdd = Math.min(4096, this._hashSize);
/*      */       
/*  948 */       int newSize = this._hashArea.length + Math.max(toAdd, minAdd);
/*  949 */       this._hashArea = Arrays.copyOf(this._hashArea, newSize);
/*      */     } 
/*  951 */     System.arraycopy(quads, 0, this._hashArea, start, qlen);
/*  952 */     this._longNameOffset += qlen;
/*  953 */     return start;
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
/*      */   public int calcHash(int q1) {
/*  978 */     int hash = q1 ^ this._seed;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  984 */     hash += hash >>> 16;
/*  985 */     hash ^= hash << 3;
/*  986 */     hash += hash >>> 12;
/*  987 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int calcHash(int q1, int q2) {
/*  994 */     int hash = q1;
/*      */     
/*  996 */     hash += hash >>> 15;
/*  997 */     hash ^= hash >>> 9;
/*  998 */     hash += q2 * 33;
/*  999 */     hash ^= this._seed;
/* 1000 */     hash += hash >>> 16;
/* 1001 */     hash ^= hash >>> 4;
/* 1002 */     hash += hash << 3;
/*      */     
/* 1004 */     return hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public int calcHash(int q1, int q2, int q3) {
/* 1009 */     int hash = q1 ^ this._seed;
/* 1010 */     hash += hash >>> 9;
/* 1011 */     hash *= 31;
/* 1012 */     hash += q2;
/* 1013 */     hash *= 33;
/* 1014 */     hash += hash >>> 15;
/* 1015 */     hash ^= q3;
/*      */     
/* 1017 */     hash += hash >>> 4;
/*      */     
/* 1019 */     hash += hash >>> 15;
/* 1020 */     hash ^= hash << 9;
/*      */     
/* 1022 */     return hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public int calcHash(int[] q, int qlen) {
/* 1027 */     if (qlen < 4) {
/* 1028 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1035 */     int hash = q[0] ^ this._seed;
/* 1036 */     hash += hash >>> 9;
/* 1037 */     hash += q[1];
/* 1038 */     hash += hash >>> 15;
/* 1039 */     hash *= 33;
/* 1040 */     hash ^= q[2];
/* 1041 */     hash += hash >>> 4;
/*      */     
/* 1043 */     for (int i = 3; i < qlen; i++) {
/* 1044 */       int next = q[i];
/* 1045 */       next ^= next >> 21;
/* 1046 */       hash += next;
/*      */     } 
/* 1048 */     hash *= 65599;
/*      */ 
/*      */     
/* 1051 */     hash += hash >>> 19;
/* 1052 */     hash ^= hash << 5;
/* 1053 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rehash() {
/* 1064 */     this._needRehash = false;
/*      */     
/* 1066 */     this._hashShared = false;
/*      */ 
/*      */ 
/*      */     
/* 1070 */     int[] oldHashArea = this._hashArea;
/* 1071 */     String[] oldNames = this._names;
/* 1072 */     int oldSize = this._hashSize;
/* 1073 */     int oldCount = this._count;
/* 1074 */     int newSize = oldSize + oldSize;
/* 1075 */     int oldEnd = this._spilloverEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1080 */     if (newSize > 65536) {
/* 1081 */       nukeSymbols(true);
/*      */       
/*      */       return;
/*      */     } 
/* 1085 */     this._hashArea = new int[oldHashArea.length + (oldSize << 3)];
/* 1086 */     this._hashSize = newSize;
/* 1087 */     this._secondaryStart = newSize << 2;
/* 1088 */     this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
/* 1089 */     this._tertiaryShift = _calcTertiaryShift(newSize);
/*      */ 
/*      */     
/* 1092 */     this._names = new String[oldNames.length << 1];
/* 1093 */     nukeSymbols(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1100 */     int copyCount = 0;
/* 1101 */     int[] q = new int[16];
/* 1102 */     for (int offset = 0, end = oldEnd; offset < end; offset += 4) {
/* 1103 */       int len = oldHashArea[offset + 3];
/* 1104 */       if (len != 0) {
/*      */         int qoff;
/*      */         
/* 1107 */         copyCount++;
/* 1108 */         String name = oldNames[offset >> 2];
/* 1109 */         switch (len) {
/*      */           case 1:
/* 1111 */             q[0] = oldHashArea[offset];
/* 1112 */             addName(name, q, 1);
/*      */             break;
/*      */           case 2:
/* 1115 */             q[0] = oldHashArea[offset];
/* 1116 */             q[1] = oldHashArea[offset + 1];
/* 1117 */             addName(name, q, 2);
/*      */             break;
/*      */           case 3:
/* 1120 */             q[0] = oldHashArea[offset];
/* 1121 */             q[1] = oldHashArea[offset + 1];
/* 1122 */             q[2] = oldHashArea[offset + 2];
/* 1123 */             addName(name, q, 3);
/*      */             break;
/*      */           default:
/* 1126 */             if (len > q.length) {
/* 1127 */               q = new int[len];
/*      */             }
/*      */             
/* 1130 */             qoff = oldHashArea[offset + 1];
/* 1131 */             System.arraycopy(oldHashArea, qoff, q, 0, len);
/* 1132 */             addName(name, q, len);
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/* 1139 */     if (copyCount != oldCount) {
/* 1140 */       throw new IllegalStateException("Failed rehash(): old count=" + oldCount + ", copyCount=" + copyCount);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void nukeSymbols(boolean fill) {
/* 1149 */     this._count = 0;
/*      */     
/* 1151 */     this._spilloverEnd = _spilloverStart();
/*      */     
/* 1153 */     this._longNameOffset = this._hashSize << 3;
/* 1154 */     if (fill) {
/* 1155 */       Arrays.fill(this._hashArea, 0);
/* 1156 */       Arrays.fill((Object[])this._names, (Object)null);
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
/*      */   private final int _spilloverStart() {
/* 1172 */     int offset = this._hashSize;
/* 1173 */     return (offset << 3) - offset;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void _reportTooManyCollisions() {
/* 1179 */     if (this._hashSize <= 1024) {
/*      */       return;
/*      */     }
/* 1182 */     throw new IllegalStateException("Spill-over slots in symbol table with " + this._count + " entries, hash area of " + this._hashSize + " slots is now full (all " + (this._hashSize >> 3) + " slots -- suspect a DoS attack based on hash collisions." + " You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int _calcTertiaryShift(int primarySlots) {
/* 1191 */     int tertSlots = primarySlots >> 2;
/*      */ 
/*      */     
/* 1194 */     if (tertSlots < 64) {
/* 1195 */       return 4;
/*      */     }
/* 1197 */     if (tertSlots <= 256) {
/* 1198 */       return 5;
/*      */     }
/* 1200 */     if (tertSlots <= 1024) {
/* 1201 */       return 6;
/*      */     }
/*      */     
/* 1204 */     return 7;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class TableInfo
/*      */   {
/*      */     public final int size;
/*      */ 
/*      */     
/*      */     public final int count;
/*      */ 
/*      */     
/*      */     public final int tertiaryShift;
/*      */ 
/*      */     
/*      */     public final int[] mainHash;
/*      */ 
/*      */     
/*      */     public final String[] names;
/*      */ 
/*      */     
/*      */     public final int spilloverEnd;
/*      */ 
/*      */     
/*      */     public final int longNameOffset;
/*      */ 
/*      */     
/*      */     public TableInfo(int size, int count, int tertiaryShift, int[] mainHash, String[] names, int spilloverEnd, int longNameOffset) {
/* 1233 */       this.size = size;
/* 1234 */       this.count = count;
/* 1235 */       this.tertiaryShift = tertiaryShift;
/* 1236 */       this.mainHash = mainHash;
/* 1237 */       this.names = names;
/* 1238 */       this.spilloverEnd = spilloverEnd;
/* 1239 */       this.longNameOffset = longNameOffset;
/*      */     }
/*      */ 
/*      */     
/*      */     public TableInfo(ByteQuadsCanonicalizer src) {
/* 1244 */       this.size = src._hashSize;
/* 1245 */       this.count = src._count;
/* 1246 */       this.tertiaryShift = src._tertiaryShift;
/* 1247 */       this.mainHash = src._hashArea;
/* 1248 */       this.names = src._names;
/* 1249 */       this.spilloverEnd = src._spilloverEnd;
/* 1250 */       this.longNameOffset = src._longNameOffset;
/*      */     }
/*      */     
/*      */     public static TableInfo createInitial(int sz) {
/* 1254 */       int hashAreaSize = sz << 3;
/* 1255 */       int tertShift = ByteQuadsCanonicalizer._calcTertiaryShift(sz);
/*      */       
/* 1257 */       return new TableInfo(sz, 0, tertShift, new int[hashAreaSize], new String[sz << 1], hashAreaSize - sz, hashAreaSize);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\sym\ByteQuadsCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */