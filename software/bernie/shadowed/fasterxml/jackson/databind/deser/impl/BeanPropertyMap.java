/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanPropertyMap
/*     */   implements Iterable<SettableBeanProperty>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   protected final boolean _caseInsensitive;
/*     */   private int _hashMask;
/*     */   private int _size;
/*     */   private int _spillCount;
/*     */   private Object[] _hashArea;
/*     */   private SettableBeanProperty[] _propsInOrder;
/*     */   private final Map<String, List<PropertyName>> _aliasDefs;
/*     */   private final Map<String, String> _aliasMapping;
/*     */   
/*     */   public BeanPropertyMap(boolean caseInsensitive, Collection<SettableBeanProperty> props, Map<String, List<PropertyName>> aliasDefs) {
/*  85 */     this._caseInsensitive = caseInsensitive;
/*  86 */     this._propsInOrder = props.<SettableBeanProperty>toArray(new SettableBeanProperty[props.size()]);
/*  87 */     this._aliasDefs = aliasDefs;
/*  88 */     this._aliasMapping = _buildAliasMapping(aliasDefs);
/*  89 */     init(props);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BeanPropertyMap(boolean caseInsensitive, Collection<SettableBeanProperty> props) {
/*  95 */     this(caseInsensitive, props, Collections.emptyMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeanPropertyMap(BeanPropertyMap base, boolean caseInsensitive) {
/* 103 */     this._caseInsensitive = caseInsensitive;
/* 104 */     this._aliasDefs = base._aliasDefs;
/* 105 */     this._aliasMapping = base._aliasMapping;
/*     */ 
/*     */     
/* 108 */     this._propsInOrder = Arrays.<SettableBeanProperty>copyOf(base._propsInOrder, base._propsInOrder.length);
/* 109 */     init(Arrays.asList(this._propsInOrder));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyMap withCaseInsensitivity(boolean state) {
/* 120 */     if (this._caseInsensitive == state) {
/* 121 */       return this;
/*     */     }
/* 123 */     return new BeanPropertyMap(this, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init(Collection<SettableBeanProperty> props) {
/* 128 */     this._size = props.size();
/*     */ 
/*     */     
/* 131 */     int hashSize = findSize(this._size);
/* 132 */     this._hashMask = hashSize - 1;
/*     */ 
/*     */     
/* 135 */     int alloc = (hashSize + (hashSize >> 1)) * 2;
/* 136 */     Object[] hashed = new Object[alloc];
/* 137 */     int spillCount = 0;
/*     */     
/* 139 */     for (SettableBeanProperty prop : props) {
/*     */       
/* 141 */       if (prop == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 145 */       String key = getPropertyName(prop);
/* 146 */       int slot = _hashCode(key);
/* 147 */       int ix = slot << 1;
/*     */ 
/*     */       
/* 150 */       if (hashed[ix] != null) {
/*     */         
/* 152 */         ix = hashSize + (slot >> 1) << 1;
/* 153 */         if (hashed[ix] != null) {
/*     */           
/* 155 */           ix = (hashSize + (hashSize >> 1) << 1) + spillCount;
/* 156 */           spillCount += 2;
/* 157 */           if (ix >= hashed.length) {
/* 158 */             hashed = Arrays.copyOf(hashed, hashed.length + 4);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 163 */       hashed[ix] = key;
/* 164 */       hashed[ix + 1] = prop;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     this._hashArea = hashed;
/* 172 */     this._spillCount = spillCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int findSize(int size) {
/* 177 */     if (size <= 5) {
/* 178 */       return 8;
/*     */     }
/* 180 */     if (size <= 12) {
/* 181 */       return 16;
/*     */     }
/* 183 */     int needed = size + (size >> 2);
/* 184 */     int result = 32;
/* 185 */     while (result < needed) {
/* 186 */       result += result;
/*     */     }
/* 188 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BeanPropertyMap construct(Collection<SettableBeanProperty> props, boolean caseInsensitive, Map<String, List<PropertyName>> aliasMapping) {
/* 196 */     return new BeanPropertyMap(caseInsensitive, props, aliasMapping);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static BeanPropertyMap construct(Collection<SettableBeanProperty> props, boolean caseInsensitive) {
/* 201 */     return construct(props, caseInsensitive, Collections.emptyMap());
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
/*     */   public BeanPropertyMap withProperty(SettableBeanProperty newProp) {
/* 215 */     String key = getPropertyName(newProp);
/*     */     
/* 217 */     for (int i = 1, end = this._hashArea.length; i < end; i += 2) {
/* 218 */       SettableBeanProperty prop = (SettableBeanProperty)this._hashArea[i];
/* 219 */       if (prop != null && prop.getName().equals(key)) {
/* 220 */         this._hashArea[i] = newProp;
/* 221 */         this._propsInOrder[_findFromOrdered(prop)] = newProp;
/* 222 */         return this;
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     int slot = _hashCode(key);
/* 227 */     int hashSize = this._hashMask + 1;
/* 228 */     int ix = slot << 1;
/*     */ 
/*     */     
/* 231 */     if (this._hashArea[ix] != null) {
/*     */       
/* 233 */       ix = hashSize + (slot >> 1) << 1;
/* 234 */       if (this._hashArea[ix] != null) {
/*     */         
/* 236 */         ix = (hashSize + (hashSize >> 1) << 1) + this._spillCount;
/* 237 */         this._spillCount += 2;
/* 238 */         if (ix >= this._hashArea.length) {
/* 239 */           this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length + 4);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     this._hashArea[ix] = key;
/* 251 */     this._hashArea[ix + 1] = newProp;
/*     */     
/* 253 */     int last = this._propsInOrder.length;
/* 254 */     this._propsInOrder = Arrays.<SettableBeanProperty>copyOf(this._propsInOrder, last + 1);
/* 255 */     this._propsInOrder[last] = newProp;
/*     */ 
/*     */ 
/*     */     
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyMap assignIndexes() {
/* 265 */     int index = 0;
/* 266 */     for (int i = 1, end = this._hashArea.length; i < end; i += 2) {
/* 267 */       SettableBeanProperty prop = (SettableBeanProperty)this._hashArea[i];
/* 268 */       if (prop != null) {
/* 269 */         prop.assignIndex(index++);
/*     */       }
/*     */     } 
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyMap renameAll(NameTransformer transformer) {
/* 281 */     if (transformer == null || transformer == NameTransformer.NOP) {
/* 282 */       return this;
/*     */     }
/*     */     
/* 285 */     int len = this._propsInOrder.length;
/* 286 */     ArrayList<SettableBeanProperty> newProps = new ArrayList<>(len);
/*     */     
/* 288 */     for (int i = 0; i < len; i++) {
/* 289 */       SettableBeanProperty prop = this._propsInOrder[i];
/*     */ 
/*     */       
/* 292 */       if (prop == null) {
/* 293 */         newProps.add(prop);
/*     */       } else {
/*     */         
/* 296 */         newProps.add(_rename(prop, transformer));
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     return new BeanPropertyMap(this._caseInsensitive, newProps, this._aliasDefs);
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
/*     */   public BeanPropertyMap withoutProperties(Collection<String> toExclude) {
/* 318 */     if (toExclude.isEmpty()) {
/* 319 */       return this;
/*     */     }
/* 321 */     int len = this._propsInOrder.length;
/* 322 */     ArrayList<SettableBeanProperty> newProps = new ArrayList<>(len);
/*     */     
/* 324 */     for (int i = 0; i < len; i++) {
/* 325 */       SettableBeanProperty prop = this._propsInOrder[i];
/*     */ 
/*     */ 
/*     */       
/* 329 */       if (prop != null && 
/* 330 */         !toExclude.contains(prop.getName())) {
/* 331 */         newProps.add(prop);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 336 */     return new BeanPropertyMap(this._caseInsensitive, newProps, this._aliasDefs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replace(SettableBeanProperty newProp) {
/* 346 */     String key = getPropertyName(newProp);
/* 347 */     int ix = _findIndexInHash(key);
/* 348 */     if (ix < 0) {
/* 349 */       throw new NoSuchElementException("No entry '" + key + "' found, can't replace");
/*     */     }
/* 351 */     SettableBeanProperty prop = (SettableBeanProperty)this._hashArea[ix];
/* 352 */     this._hashArea[ix] = newProp;
/*     */     
/* 354 */     this._propsInOrder[_findFromOrdered(prop)] = newProp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(SettableBeanProperty propToRm) {
/* 363 */     ArrayList<SettableBeanProperty> props = new ArrayList<>(this._size);
/* 364 */     String key = getPropertyName(propToRm);
/* 365 */     boolean found = false;
/*     */     
/* 367 */     for (int i = 1, end = this._hashArea.length; i < end; i += 2) {
/* 368 */       SettableBeanProperty prop = (SettableBeanProperty)this._hashArea[i];
/* 369 */       if (prop == null) {
/*     */         continue;
/*     */       }
/* 372 */       if (!found) {
/*     */ 
/*     */         
/* 375 */         found = key.equals(this._hashArea[i - 1]);
/* 376 */         if (found) {
/*     */           
/* 378 */           this._propsInOrder[_findFromOrdered(prop)] = null;
/*     */           continue;
/*     */         } 
/*     */       } 
/* 382 */       props.add(prop); continue;
/*     */     } 
/* 384 */     if (!found) {
/* 385 */       throw new NoSuchElementException("No entry '" + propToRm.getName() + "' found, can't remove");
/*     */     }
/* 387 */     init(props);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 396 */     return this._size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCaseInsensitive() {
/* 402 */     return this._caseInsensitive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAliases() {
/* 409 */     return !this._aliasDefs.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<SettableBeanProperty> iterator() {
/* 417 */     return _properties().iterator();
/*     */   }
/*     */   
/*     */   private List<SettableBeanProperty> _properties() {
/* 421 */     ArrayList<SettableBeanProperty> p = new ArrayList<>(this._size);
/* 422 */     for (int i = 1, end = this._hashArea.length; i < end; i += 2) {
/* 423 */       SettableBeanProperty prop = (SettableBeanProperty)this._hashArea[i];
/* 424 */       if (prop != null) {
/* 425 */         p.add(prop);
/*     */       }
/*     */     } 
/* 428 */     return p;
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
/*     */   public SettableBeanProperty[] getPropertiesInInsertionOrder() {
/* 440 */     return this._propsInOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String getPropertyName(SettableBeanProperty prop) {
/* 446 */     return this._caseInsensitive ? prop.getName().toLowerCase() : prop.getName();
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
/*     */   public SettableBeanProperty find(int index) {
/* 462 */     for (int i = 1, end = this._hashArea.length; i < end; i += 2) {
/* 463 */       SettableBeanProperty prop = (SettableBeanProperty)this._hashArea[i];
/* 464 */       if (prop != null && index == prop.getPropertyIndex()) {
/* 465 */         return prop;
/*     */       }
/*     */     } 
/* 468 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty find(String key) {
/* 473 */     if (key == null) {
/* 474 */       throw new IllegalArgumentException("Cannot pass null property name");
/*     */     }
/* 476 */     if (this._caseInsensitive) {
/* 477 */       key = key.toLowerCase();
/*     */     }
/*     */ 
/*     */     
/* 481 */     int slot = key.hashCode() & this._hashMask;
/*     */ 
/*     */ 
/*     */     
/* 485 */     int ix = slot << 1;
/* 486 */     Object match = this._hashArea[ix];
/* 487 */     if (match == key || key.equals(match)) {
/* 488 */       return (SettableBeanProperty)this._hashArea[ix + 1];
/*     */     }
/* 490 */     return _find2(key, slot, match);
/*     */   }
/*     */ 
/*     */   
/*     */   private final SettableBeanProperty _find2(String key, int slot, Object match) {
/* 495 */     if (match == null)
/*     */     {
/* 497 */       return _findWithAlias(this._aliasMapping.get(key));
/*     */     }
/*     */     
/* 500 */     int hashSize = this._hashMask + 1;
/* 501 */     int ix = hashSize + (slot >> 1) << 1;
/* 502 */     match = this._hashArea[ix];
/* 503 */     if (key.equals(match)) {
/* 504 */       return (SettableBeanProperty)this._hashArea[ix + 1];
/*     */     }
/* 506 */     if (match != null) {
/* 507 */       int i = hashSize + (hashSize >> 1) << 1;
/* 508 */       for (int end = i + this._spillCount; i < end; i += 2) {
/* 509 */         match = this._hashArea[i];
/* 510 */         if (match == key || key.equals(match)) {
/* 511 */           return (SettableBeanProperty)this._hashArea[i + 1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 516 */     return _findWithAlias(this._aliasMapping.get(key));
/*     */   }
/*     */ 
/*     */   
/*     */   private SettableBeanProperty _findWithAlias(String keyFromAlias) {
/* 521 */     if (keyFromAlias == null) {
/* 522 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 526 */     int slot = _hashCode(keyFromAlias);
/* 527 */     int ix = slot << 1;
/* 528 */     Object match = this._hashArea[ix];
/* 529 */     if (keyFromAlias.equals(match)) {
/* 530 */       return (SettableBeanProperty)this._hashArea[ix + 1];
/*     */     }
/* 532 */     if (match == null) {
/* 533 */       return null;
/*     */     }
/* 535 */     return _find2ViaAlias(keyFromAlias, slot, match);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SettableBeanProperty _find2ViaAlias(String key, int slot, Object match) {
/* 541 */     int hashSize = this._hashMask + 1;
/* 542 */     int ix = hashSize + (slot >> 1) << 1;
/* 543 */     match = this._hashArea[ix];
/* 544 */     if (key.equals(match)) {
/* 545 */       return (SettableBeanProperty)this._hashArea[ix + 1];
/*     */     }
/* 547 */     if (match != null) {
/* 548 */       int i = hashSize + (hashSize >> 1) << 1;
/* 549 */       for (int end = i + this._spillCount; i < end; i += 2) {
/* 550 */         match = this._hashArea[i];
/* 551 */         if (match == key || key.equals(match)) {
/* 552 */           return (SettableBeanProperty)this._hashArea[i + 1];
/*     */         }
/*     */       } 
/*     */     } 
/* 556 */     return null;
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
/*     */   public boolean findDeserializeAndSet(JsonParser p, DeserializationContext ctxt, Object bean, String key) throws IOException {
/* 577 */     SettableBeanProperty prop = find(key);
/* 578 */     if (prop == null) {
/* 579 */       return false;
/*     */     }
/*     */     try {
/* 582 */       prop.deserializeAndSet(p, ctxt, bean);
/* 583 */     } catch (Exception e) {
/* 584 */       wrapAndThrow(e, bean, key, ctxt);
/*     */     } 
/* 586 */     return true;
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
/*     */   public String toString() {
/* 598 */     StringBuilder sb = new StringBuilder();
/* 599 */     sb.append("Properties=[");
/* 600 */     int count = 0;
/*     */     
/* 602 */     Iterator<SettableBeanProperty> it = iterator();
/* 603 */     while (it.hasNext()) {
/* 604 */       SettableBeanProperty prop = it.next();
/* 605 */       if (count++ > 0) {
/* 606 */         sb.append(", ");
/*     */       }
/* 608 */       sb.append(prop.getName());
/* 609 */       sb.append('(');
/* 610 */       sb.append(prop.getType());
/* 611 */       sb.append(')');
/*     */     } 
/* 613 */     sb.append(']');
/* 614 */     if (!this._aliasDefs.isEmpty()) {
/* 615 */       sb.append("(aliases: ");
/* 616 */       sb.append(this._aliasDefs);
/* 617 */       sb.append(")");
/*     */     } 
/* 619 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty _rename(SettableBeanProperty prop, NameTransformer xf) {
/* 630 */     if (prop == null) {
/* 631 */       return prop;
/*     */     }
/* 633 */     String newName = xf.transform(prop.getName());
/* 634 */     prop = prop.withSimpleName(newName);
/* 635 */     JsonDeserializer<?> deser = prop.getValueDeserializer();
/* 636 */     if (deser != null) {
/*     */       
/* 638 */       JsonDeserializer<Object> newDeser = deser.unwrappingDeserializer(xf);
/*     */       
/* 640 */       if (newDeser != deser) {
/* 641 */         prop = prop.withValueDeserializer(newDeser);
/*     */       }
/*     */     } 
/* 644 */     return prop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wrapAndThrow(Throwable t, Object bean, String fieldName, DeserializationContext ctxt) throws IOException {
/* 651 */     while (t instanceof java.lang.reflect.InvocationTargetException && t.getCause() != null) {
/* 652 */       t = t.getCause();
/*     */     }
/*     */     
/* 655 */     ClassUtil.throwIfError(t);
/*     */     
/* 657 */     boolean wrap = (ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS));
/*     */     
/* 659 */     if (t instanceof IOException) {
/* 660 */       if (!wrap || !(t instanceof software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException)) {
/* 661 */         throw (IOException)t;
/*     */       }
/* 663 */     } else if (!wrap) {
/* 664 */       ClassUtil.throwIfRTE(t);
/*     */     } 
/* 666 */     throw JsonMappingException.wrapWithPath(t, bean, fieldName);
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
/*     */   private final int _findIndexInHash(String key) {
/* 679 */     int slot = _hashCode(key);
/* 680 */     int ix = slot << 1;
/*     */ 
/*     */     
/* 683 */     if (key.equals(this._hashArea[ix])) {
/* 684 */       return ix + 1;
/*     */     }
/*     */     
/* 687 */     int hashSize = this._hashMask + 1;
/* 688 */     ix = hashSize + (slot >> 1) << 1;
/* 689 */     if (key.equals(this._hashArea[ix])) {
/* 690 */       return ix + 1;
/*     */     }
/*     */     
/* 693 */     int i = hashSize + (hashSize >> 1) << 1;
/* 694 */     for (int end = i + this._spillCount; i < end; i += 2) {
/* 695 */       if (key.equals(this._hashArea[i])) {
/* 696 */         return i + 1;
/*     */       }
/*     */     } 
/* 699 */     return -1;
/*     */   }
/*     */   
/*     */   private final int _findFromOrdered(SettableBeanProperty prop) {
/* 703 */     for (int i = 0, end = this._propsInOrder.length; i < end; i++) {
/* 704 */       if (this._propsInOrder[i] == prop) {
/* 705 */         return i;
/*     */       }
/*     */     } 
/* 708 */     throw new IllegalStateException("Illegal state: property '" + prop.getName() + "' missing from _propsInOrder");
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
/*     */   private final int _hashCode(String key) {
/* 722 */     return key.hashCode() & this._hashMask;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, String> _buildAliasMapping(Map<String, List<PropertyName>> defs) {
/* 728 */     if (defs == null || defs.isEmpty()) {
/* 729 */       return Collections.emptyMap();
/*     */     }
/* 731 */     Map<String, String> aliases = new HashMap<>();
/* 732 */     for (Map.Entry<String, List<PropertyName>> entry : defs.entrySet()) {
/* 733 */       String key = entry.getKey();
/* 734 */       if (this._caseInsensitive) {
/* 735 */         key = key.toLowerCase();
/*     */       }
/* 737 */       for (PropertyName pn : entry.getValue()) {
/* 738 */         String mapped = pn.getSimpleName();
/* 739 */         if (this._caseInsensitive) {
/* 740 */           mapped = mapped.toLowerCase();
/*     */         }
/* 742 */         aliases.put(mapped, key);
/*     */       } 
/*     */     } 
/* 745 */     return aliases;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\BeanPropertyMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */