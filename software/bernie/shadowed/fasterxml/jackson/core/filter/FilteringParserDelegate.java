/*     */ package software.bernie.shadowed.fasterxml.jackson.core.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.JsonParserDelegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilteringParserDelegate
/*     */   extends JsonParserDelegate
/*     */ {
/*     */   protected TokenFilter rootFilter;
/*     */   protected boolean _allowMultipleMatches;
/*     */   protected boolean _includePath;
/*     */   @Deprecated
/*     */   protected boolean _includeImmediateParent;
/*     */   protected JsonToken _currToken;
/*     */   protected JsonToken _lastClearedToken;
/*     */   protected TokenFilterContext _headContext;
/*     */   protected TokenFilterContext _exposedContext;
/*     */   protected TokenFilter _itemFilter;
/*     */   protected int _matchCount;
/*     */   
/*     */   public FilteringParserDelegate(JsonParser p, TokenFilter f, boolean includePath, boolean allowMultipleMatches) {
/* 117 */     super(p);
/* 118 */     this.rootFilter = f;
/*     */     
/* 120 */     this._itemFilter = f;
/* 121 */     this._headContext = TokenFilterContext.createRootContext(f);
/* 122 */     this._includePath = includePath;
/* 123 */     this._allowMultipleMatches = allowMultipleMatches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenFilter getFilter() {
/* 132 */     return this.rootFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMatchCount() {
/* 139 */     return this._matchCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken getCurrentToken() {
/* 148 */     return this._currToken; } public JsonToken currentToken() {
/* 149 */     return this._currToken;
/*     */   }
/*     */   public final int getCurrentTokenId() {
/* 152 */     JsonToken t = this._currToken;
/* 153 */     return (t == null) ? 0 : t.id();
/*     */   }
/*     */   public final int currentTokenId() {
/* 156 */     JsonToken t = this._currToken;
/* 157 */     return (t == null) ? 0 : t.id();
/*     */   }
/*     */   public boolean hasCurrentToken() {
/* 160 */     return (this._currToken != null);
/*     */   } public boolean hasTokenId(int id) {
/* 162 */     JsonToken t = this._currToken;
/* 163 */     if (t == null) {
/* 164 */       return (0 == id);
/*     */     }
/* 166 */     return (t.id() == id);
/*     */   }
/*     */   
/*     */   public final boolean hasToken(JsonToken t) {
/* 170 */     return (this._currToken == t);
/*     */   }
/*     */   
/* 173 */   public boolean isExpectedStartArrayToken() { return (this._currToken == JsonToken.START_ARRAY); } public boolean isExpectedStartObjectToken() {
/* 174 */     return (this._currToken == JsonToken.START_OBJECT);
/*     */   } public JsonLocation getCurrentLocation() {
/* 176 */     return this.delegate.getCurrentLocation();
/*     */   }
/*     */   
/*     */   public JsonStreamContext getParsingContext() {
/* 180 */     return _filterContext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCurrentName() throws IOException {
/* 186 */     JsonStreamContext ctxt = _filterContext();
/* 187 */     if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/* 188 */       JsonStreamContext parent = ctxt.getParent();
/* 189 */       return (parent == null) ? null : parent.getCurrentName();
/*     */     } 
/* 191 */     return ctxt.getCurrentName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCurrentToken() {
/* 202 */     if (this._currToken != null) {
/* 203 */       this._lastClearedToken = this._currToken;
/* 204 */       this._currToken = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public JsonToken getLastClearedToken() {
/* 209 */     return this._lastClearedToken;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void overrideCurrentName(String name) {
/* 217 */     throw new UnsupportedOperationException("Can not currently override name during filtering read");
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
/*     */   public JsonToken nextToken() throws IOException {
/*     */     boolean returnEnd;
/*     */     String name;
/* 237 */     if (!this._allowMultipleMatches && this._currToken != null && this._exposedContext == null)
/*     */     {
/*     */       
/* 240 */       if (this._currToken.isScalarValue() && !this._headContext.isStartHandled() && !this._includePath && this._itemFilter == TokenFilter.INCLUDE_ALL)
/*     */       {
/* 242 */         return this._currToken = null;
/*     */       }
/*     */     }
/*     */     
/* 246 */     TokenFilterContext ctxt = this._exposedContext;
/*     */     
/* 248 */     if (ctxt != null) {
/*     */       while (true) {
/* 250 */         JsonToken jsonToken = ctxt.nextTokenToRead();
/* 251 */         if (jsonToken != null) {
/* 252 */           this._currToken = jsonToken;
/* 253 */           return jsonToken;
/*     */         } 
/*     */         
/* 256 */         if (ctxt == this._headContext) {
/* 257 */           this._exposedContext = null;
/* 258 */           if (ctxt.inArray()) {
/* 259 */             jsonToken = this.delegate.getCurrentToken();
/*     */ 
/*     */             
/* 262 */             this._currToken = jsonToken;
/* 263 */             return jsonToken;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 278 */         ctxt = this._headContext.findChildOf(ctxt);
/* 279 */         this._exposedContext = ctxt;
/* 280 */         if (ctxt == null) {
/* 281 */           throw _constructError("Unexpected problem: chain of filtered context broken");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 287 */     JsonToken t = this.delegate.nextToken();
/* 288 */     if (t == null) {
/*     */       
/* 290 */       this._currToken = t;
/* 291 */       return t;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     switch (t.id())
/*     */     { case 3:
/* 299 */         f = this._itemFilter;
/* 300 */         if (f == TokenFilter.INCLUDE_ALL) {
/* 301 */           this._headContext = this._headContext.createChildArrayContext(f, true);
/* 302 */           return this._currToken = t;
/*     */         } 
/* 304 */         if (f == null) {
/* 305 */           this.delegate.skipChildren();
/*     */         }
/*     */         else {
/*     */           
/* 309 */           f = this._headContext.checkValue(f);
/* 310 */           if (f == null) {
/* 311 */             this.delegate.skipChildren();
/*     */           } else {
/*     */             
/* 314 */             if (f != TokenFilter.INCLUDE_ALL) {
/* 315 */               f = f.filterStartArray();
/*     */             }
/* 317 */             this._itemFilter = f;
/* 318 */             if (f == TokenFilter.INCLUDE_ALL) {
/* 319 */               this._headContext = this._headContext.createChildArrayContext(f, true);
/* 320 */               return this._currToken = t;
/*     */             } 
/* 322 */             this._headContext = this._headContext.createChildArrayContext(f, false);
/*     */ 
/*     */             
/* 325 */             if (this._includePath) {
/* 326 */               t = _nextTokenWithBuffering(this._headContext);
/* 327 */               if (t != null) {
/* 328 */                 this._currToken = t;
/* 329 */                 return t;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 455 */         return _nextToken2();case 1: f = this._itemFilter; if (f == TokenFilter.INCLUDE_ALL) { this._headContext = this._headContext.createChildObjectContext(f, true); return this._currToken = t; }  if (f == null) { this.delegate.skipChildren(); } else { f = this._headContext.checkValue(f); if (f == null) { this.delegate.skipChildren(); } else { if (f != TokenFilter.INCLUDE_ALL) f = f.filterStartObject();  this._itemFilter = f; if (f == TokenFilter.INCLUDE_ALL) { this._headContext = this._headContext.createChildObjectContext(f, true); return this._currToken = t; }  this._headContext = this._headContext.createChildObjectContext(f, false); if (this._includePath) { t = _nextTokenWithBuffering(this._headContext); if (t != null) { this._currToken = t; return t; }  }  }  }  return _nextToken2();case 2: case 4: returnEnd = this._headContext.isStartHandled(); f = this._headContext.getFilter(); if (f != null && f != TokenFilter.INCLUDE_ALL) f.filterFinishArray();  this._headContext = this._headContext.getParent(); this._itemFilter = this._headContext.getFilter(); if (returnEnd) return this._currToken = t;  return _nextToken2();case 5: name = this.delegate.getCurrentName(); f = this._headContext.setFieldName(name); if (f == TokenFilter.INCLUDE_ALL) { this._itemFilter = f; if (!this._includePath) if (this._includeImmediateParent && !this._headContext.isStartHandled()) { t = this._headContext.nextTokenToRead(); this._exposedContext = this._headContext; }   return this._currToken = t; }  if (f == null) { this.delegate.nextToken(); this.delegate.skipChildren(); } else { f = f.includeProperty(name); if (f == null) { this.delegate.nextToken(); this.delegate.skipChildren(); } else { this._itemFilter = f; if (f == TokenFilter.INCLUDE_ALL) if (_verifyAllowedMatches()) { if (this._includePath) return this._currToken = t;  } else { this.delegate.nextToken(); this.delegate.skipChildren(); }   if (this._includePath) { t = _nextTokenWithBuffering(this._headContext); if (t != null) { this._currToken = t; return t; }  }  }  }  return _nextToken2(); }  TokenFilter f = this._itemFilter; if (f == TokenFilter.INCLUDE_ALL) return this._currToken = t;  if (f != null) { f = this._headContext.checkValue(f); if (f == TokenFilter.INCLUDE_ALL || (f != null && f.includeValue(this.delegate))) if (_verifyAllowedMatches()) return this._currToken = t;   }  return _nextToken2();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonToken _nextToken2() throws IOException {
/*     */     while (true) {
/*     */       boolean returnEnd;
/*     */       String name;
/* 468 */       JsonToken t = this.delegate.nextToken();
/* 469 */       if (t == null) {
/* 470 */         this._currToken = t;
/* 471 */         return t;
/*     */       } 
/*     */ 
/*     */       
/* 475 */       switch (t.id()) {
/*     */         case 3:
/* 477 */           f = this._itemFilter;
/* 478 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 479 */             this._headContext = this._headContext.createChildArrayContext(f, true);
/* 480 */             return this._currToken = t;
/*     */           } 
/* 482 */           if (f == null) {
/* 483 */             this.delegate.skipChildren();
/*     */             
/*     */             continue;
/*     */           } 
/* 487 */           f = this._headContext.checkValue(f);
/* 488 */           if (f == null) {
/* 489 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 492 */           if (f != TokenFilter.INCLUDE_ALL) {
/* 493 */             f = f.filterStartArray();
/*     */           }
/* 495 */           this._itemFilter = f;
/* 496 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 497 */             this._headContext = this._headContext.createChildArrayContext(f, true);
/* 498 */             return this._currToken = t;
/*     */           } 
/* 500 */           this._headContext = this._headContext.createChildArrayContext(f, false);
/*     */           
/* 502 */           if (this._includePath) {
/* 503 */             t = _nextTokenWithBuffering(this._headContext);
/* 504 */             if (t != null) {
/* 505 */               this._currToken = t;
/* 506 */               return t;
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         
/*     */         case 1:
/* 512 */           f = this._itemFilter;
/* 513 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 514 */             this._headContext = this._headContext.createChildObjectContext(f, true);
/* 515 */             return this._currToken = t;
/*     */           } 
/* 517 */           if (f == null) {
/* 518 */             this.delegate.skipChildren();
/*     */             
/*     */             continue;
/*     */           } 
/* 522 */           f = this._headContext.checkValue(f);
/* 523 */           if (f == null) {
/* 524 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 527 */           if (f != TokenFilter.INCLUDE_ALL) {
/* 528 */             f = f.filterStartObject();
/*     */           }
/* 530 */           this._itemFilter = f;
/* 531 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 532 */             this._headContext = this._headContext.createChildObjectContext(f, true);
/* 533 */             return this._currToken = t;
/*     */           } 
/* 535 */           this._headContext = this._headContext.createChildObjectContext(f, false);
/* 536 */           if (this._includePath) {
/* 537 */             t = _nextTokenWithBuffering(this._headContext);
/* 538 */             if (t != null) {
/* 539 */               this._currToken = t;
/* 540 */               return t;
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 4:
/* 548 */           returnEnd = this._headContext.isStartHandled();
/* 549 */           f = this._headContext.getFilter();
/* 550 */           if (f != null && f != TokenFilter.INCLUDE_ALL) {
/* 551 */             f.filterFinishArray();
/*     */           }
/* 553 */           this._headContext = this._headContext.getParent();
/* 554 */           this._itemFilter = this._headContext.getFilter();
/* 555 */           if (returnEnd) {
/* 556 */             return this._currToken = t;
/*     */           }
/*     */           continue;
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/* 563 */           name = this.delegate.getCurrentName();
/* 564 */           f = this._headContext.setFieldName(name);
/* 565 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 566 */             this._itemFilter = f;
/* 567 */             return this._currToken = t;
/*     */           } 
/* 569 */           if (f == null) {
/* 570 */             this.delegate.nextToken();
/* 571 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 574 */           f = f.includeProperty(name);
/* 575 */           if (f == null) {
/* 576 */             this.delegate.nextToken();
/* 577 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 580 */           this._itemFilter = f;
/* 581 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 582 */             if (_verifyAllowedMatches() && this._includePath) {
/* 583 */               return this._currToken = t;
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/* 588 */           if (this._includePath) {
/* 589 */             t = _nextTokenWithBuffering(this._headContext);
/* 590 */             if (t != null) {
/* 591 */               this._currToken = t;
/* 592 */               return t;
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 599 */       TokenFilter f = this._itemFilter;
/* 600 */       if (f == TokenFilter.INCLUDE_ALL) {
/* 601 */         return this._currToken = t;
/*     */       }
/* 603 */       if (f != null) {
/* 604 */         f = this._headContext.checkValue(f);
/* 605 */         if (f == TokenFilter.INCLUDE_ALL || (f != null && f.includeValue(this.delegate)))
/*     */         {
/* 607 */           if (_verifyAllowedMatches()) {
/* 608 */             return this._currToken = t;
/*     */           }
/*     */         }
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
/*     */   protected final JsonToken _nextTokenWithBuffering(TokenFilterContext buffRoot) throws IOException {
/*     */     while (true) {
/*     */       boolean gotEnd;
/*     */       String name;
/*     */       boolean returnEnd;
/* 626 */       JsonToken t = this.delegate.nextToken();
/* 627 */       if (t == null) {
/* 628 */         return t;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 636 */       switch (t.id()) {
/*     */         case 3:
/* 638 */           f = this._headContext.checkValue(this._itemFilter);
/* 639 */           if (f == null) {
/* 640 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 643 */           if (f != TokenFilter.INCLUDE_ALL) {
/* 644 */             f = f.filterStartArray();
/*     */           }
/* 646 */           this._itemFilter = f;
/* 647 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 648 */             this._headContext = this._headContext.createChildArrayContext(f, true);
/* 649 */             return _nextBuffered(buffRoot);
/*     */           } 
/* 651 */           this._headContext = this._headContext.createChildArrayContext(f, false);
/*     */           continue;
/*     */         
/*     */         case 1:
/* 655 */           f = this._itemFilter;
/* 656 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 657 */             this._headContext = this._headContext.createChildObjectContext(f, true);
/* 658 */             return t;
/*     */           } 
/* 660 */           if (f == null) {
/* 661 */             this.delegate.skipChildren();
/*     */             
/*     */             continue;
/*     */           } 
/* 665 */           f = this._headContext.checkValue(f);
/* 666 */           if (f == null) {
/* 667 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 670 */           if (f != TokenFilter.INCLUDE_ALL) {
/* 671 */             f = f.filterStartObject();
/*     */           }
/* 673 */           this._itemFilter = f;
/* 674 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 675 */             this._headContext = this._headContext.createChildObjectContext(f, true);
/* 676 */             return _nextBuffered(buffRoot);
/*     */           } 
/* 678 */           this._headContext = this._headContext.createChildObjectContext(f, false);
/*     */           continue;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 4:
/* 686 */           f = this._headContext.getFilter();
/* 687 */           if (f != null && f != TokenFilter.INCLUDE_ALL) {
/* 688 */             f.filterFinishArray();
/*     */           }
/* 690 */           gotEnd = (this._headContext == buffRoot);
/* 691 */           returnEnd = (gotEnd && this._headContext.isStartHandled());
/*     */           
/* 693 */           this._headContext = this._headContext.getParent();
/* 694 */           this._itemFilter = this._headContext.getFilter();
/*     */           
/* 696 */           if (returnEnd) {
/* 697 */             return t;
/*     */           }
/*     */           continue;
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/* 704 */           name = this.delegate.getCurrentName();
/* 705 */           f = this._headContext.setFieldName(name);
/* 706 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 707 */             this._itemFilter = f;
/* 708 */             return _nextBuffered(buffRoot);
/*     */           } 
/* 710 */           if (f == null) {
/* 711 */             this.delegate.nextToken();
/* 712 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 715 */           f = f.includeProperty(name);
/* 716 */           if (f == null) {
/* 717 */             this.delegate.nextToken();
/* 718 */             this.delegate.skipChildren();
/*     */             continue;
/*     */           } 
/* 721 */           this._itemFilter = f;
/* 722 */           if (f == TokenFilter.INCLUDE_ALL) {
/* 723 */             if (_verifyAllowedMatches()) {
/* 724 */               return _nextBuffered(buffRoot);
/*     */             }
/*     */ 
/*     */             
/* 728 */             this._itemFilter = this._headContext.setFieldName(name);
/*     */           } 
/*     */           continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 735 */       TokenFilter f = this._itemFilter;
/* 736 */       if (f == TokenFilter.INCLUDE_ALL) {
/* 737 */         return _nextBuffered(buffRoot);
/*     */       }
/* 739 */       if (f != null) {
/* 740 */         f = this._headContext.checkValue(f);
/* 741 */         if (f == TokenFilter.INCLUDE_ALL || (f != null && f.includeValue(this.delegate)))
/*     */         {
/* 743 */           if (_verifyAllowedMatches()) {
/* 744 */             return _nextBuffered(buffRoot);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonToken _nextBuffered(TokenFilterContext buffRoot) throws IOException {
/* 756 */     this._exposedContext = buffRoot;
/* 757 */     TokenFilterContext ctxt = buffRoot;
/* 758 */     JsonToken t = ctxt.nextTokenToRead();
/* 759 */     if (t != null) {
/* 760 */       return t;
/*     */     }
/*     */     
/*     */     while (true) {
/* 764 */       if (ctxt == this._headContext) {
/* 765 */         throw _constructError("Internal error: failed to locate expected buffered tokens");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 772 */       ctxt = this._exposedContext.findChildOf(ctxt);
/* 773 */       this._exposedContext = ctxt;
/* 774 */       if (ctxt == null) {
/* 775 */         throw _constructError("Unexpected problem: chain of filtered context broken");
/*     */       }
/* 777 */       t = this._exposedContext.nextTokenToRead();
/* 778 */       if (t != null) {
/* 779 */         return t;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private final boolean _verifyAllowedMatches() throws IOException {
/* 785 */     if (this._matchCount == 0 || this._allowMultipleMatches) {
/* 786 */       this._matchCount++;
/* 787 */       return true;
/*     */     } 
/* 789 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken nextValue() throws IOException {
/* 795 */     JsonToken t = nextToken();
/* 796 */     if (t == JsonToken.FIELD_NAME) {
/* 797 */       t = nextToken();
/*     */     }
/* 799 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonParser skipChildren() throws IOException {
/* 810 */     if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY)
/*     */     {
/* 812 */       return (JsonParser)this;
/*     */     }
/* 814 */     int open = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 819 */       JsonToken t = nextToken();
/* 820 */       if (t == null) {
/* 821 */         return (JsonParser)this;
/*     */       }
/* 823 */       if (t.isStructStart()) {
/* 824 */         open++; continue;
/* 825 */       }  if (t.isStructEnd() && 
/* 826 */         --open == 0) {
/* 827 */         return (JsonParser)this;
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
/*     */   public String getText() throws IOException {
/* 839 */     return this.delegate.getText();
/* 840 */   } public boolean hasTextCharacters() { return this.delegate.hasTextCharacters(); }
/* 841 */   public char[] getTextCharacters() throws IOException { return this.delegate.getTextCharacters(); }
/* 842 */   public int getTextLength() throws IOException { return this.delegate.getTextLength(); } public int getTextOffset() throws IOException {
/* 843 */     return this.delegate.getTextOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getBigIntegerValue() throws IOException {
/* 852 */     return this.delegate.getBigIntegerValue();
/*     */   }
/*     */   public boolean getBooleanValue() throws IOException {
/* 855 */     return this.delegate.getBooleanValue();
/*     */   }
/*     */   public byte getByteValue() throws IOException {
/* 858 */     return this.delegate.getByteValue();
/*     */   }
/*     */   public short getShortValue() throws IOException {
/* 861 */     return this.delegate.getShortValue();
/*     */   }
/*     */   public BigDecimal getDecimalValue() throws IOException {
/* 864 */     return this.delegate.getDecimalValue();
/*     */   }
/*     */   public double getDoubleValue() throws IOException {
/* 867 */     return this.delegate.getDoubleValue();
/*     */   }
/*     */   public float getFloatValue() throws IOException {
/* 870 */     return this.delegate.getFloatValue();
/*     */   }
/*     */   public int getIntValue() throws IOException {
/* 873 */     return this.delegate.getIntValue();
/*     */   }
/*     */   public long getLongValue() throws IOException {
/* 876 */     return this.delegate.getLongValue();
/*     */   }
/*     */   public JsonParser.NumberType getNumberType() throws IOException {
/* 879 */     return this.delegate.getNumberType();
/*     */   }
/*     */   public Number getNumberValue() throws IOException {
/* 882 */     return this.delegate.getNumberValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueAsInt() throws IOException {
/* 890 */     return this.delegate.getValueAsInt();
/* 891 */   } public int getValueAsInt(int defaultValue) throws IOException { return this.delegate.getValueAsInt(defaultValue); }
/* 892 */   public long getValueAsLong() throws IOException { return this.delegate.getValueAsLong(); }
/* 893 */   public long getValueAsLong(long defaultValue) throws IOException { return this.delegate.getValueAsLong(defaultValue); }
/* 894 */   public double getValueAsDouble() throws IOException { return this.delegate.getValueAsDouble(); }
/* 895 */   public double getValueAsDouble(double defaultValue) throws IOException { return this.delegate.getValueAsDouble(defaultValue); }
/* 896 */   public boolean getValueAsBoolean() throws IOException { return this.delegate.getValueAsBoolean(); }
/* 897 */   public boolean getValueAsBoolean(boolean defaultValue) throws IOException { return this.delegate.getValueAsBoolean(defaultValue); }
/* 898 */   public String getValueAsString() throws IOException { return this.delegate.getValueAsString(); } public String getValueAsString(String defaultValue) throws IOException {
/* 899 */     return this.delegate.getValueAsString(defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEmbeddedObject() throws IOException {
/* 907 */     return this.delegate.getEmbeddedObject();
/* 908 */   } public byte[] getBinaryValue(Base64Variant b64variant) throws IOException { return this.delegate.getBinaryValue(b64variant); }
/* 909 */   public int readBinaryValue(Base64Variant b64variant, OutputStream out) throws IOException { return this.delegate.readBinaryValue(b64variant, out); } public JsonLocation getTokenLocation() {
/* 910 */     return this.delegate.getTokenLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonStreamContext _filterContext() {
/* 919 */     if (this._exposedContext != null) {
/* 920 */       return this._exposedContext;
/*     */     }
/* 922 */     return this._headContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\filter\FilteringParserDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */