/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*      */ 
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonProperty;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter;
/*      */ import software.bernie.shadowed.fasterxml.jackson.annotation.Nulls;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.ConfigOverride;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*      */ 
/*      */ 
/*      */ public class POJOPropertyBuilder
/*      */   extends BeanPropertyDefinition
/*      */   implements Comparable<POJOPropertyBuilder>
/*      */ {
/*   29 */   private static final AnnotationIntrospector.ReferenceProperty NOT_REFEFERENCE_PROP = AnnotationIntrospector.ReferenceProperty.managed("");
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _forSerialization;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final MapperConfig<?> _config;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final AnnotationIntrospector _annotationIntrospector;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final PropertyName _name;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final PropertyName _internalName;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Linked<AnnotatedField> _fields;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Linked<AnnotatedParameter> _ctorParameters;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Linked<AnnotatedMethod> _getters;
/*      */ 
/*      */   
/*      */   protected Linked<AnnotatedMethod> _setters;
/*      */ 
/*      */   
/*      */   protected transient PropertyMetadata _metadata;
/*      */ 
/*      */   
/*      */   protected transient AnnotationIntrospector.ReferenceProperty _referenceInfo;
/*      */ 
/*      */ 
/*      */   
/*      */   public POJOPropertyBuilder(MapperConfig<?> config, AnnotationIntrospector ai, boolean forSerialization, PropertyName internalName) {
/*   75 */     this(config, ai, forSerialization, internalName, internalName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected POJOPropertyBuilder(MapperConfig<?> config, AnnotationIntrospector ai, boolean forSerialization, PropertyName internalName, PropertyName name) {
/*   81 */     this._config = config;
/*   82 */     this._annotationIntrospector = ai;
/*   83 */     this._internalName = internalName;
/*   84 */     this._name = name;
/*   85 */     this._forSerialization = forSerialization;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected POJOPropertyBuilder(POJOPropertyBuilder src, PropertyName newName) {
/*   91 */     this._config = src._config;
/*   92 */     this._annotationIntrospector = src._annotationIntrospector;
/*   93 */     this._internalName = src._internalName;
/*   94 */     this._name = newName;
/*   95 */     this._fields = src._fields;
/*   96 */     this._ctorParameters = src._ctorParameters;
/*   97 */     this._getters = src._getters;
/*   98 */     this._setters = src._setters;
/*   99 */     this._forSerialization = src._forSerialization;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public POJOPropertyBuilder withName(PropertyName newName) {
/*  110 */     return new POJOPropertyBuilder(this, newName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public POJOPropertyBuilder withSimpleName(String newSimpleName) {
/*  116 */     PropertyName newName = this._name.withSimpleName(newSimpleName);
/*  117 */     return (newName == this._name) ? this : new POJOPropertyBuilder(this, newName);
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
/*      */   public int compareTo(POJOPropertyBuilder other) {
/*  132 */     if (this._ctorParameters != null) {
/*  133 */       if (other._ctorParameters == null) {
/*  134 */         return -1;
/*      */       }
/*  136 */     } else if (other._ctorParameters != null) {
/*  137 */       return 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  142 */     return getName().compareTo(other.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  153 */     return (this._name == null) ? null : this._name.getSimpleName();
/*      */   }
/*      */ 
/*      */   
/*      */   public PropertyName getFullName() {
/*  158 */     return this._name;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasName(PropertyName name) {
/*  163 */     return this._name.equals(name);
/*      */   }
/*      */   
/*      */   public String getInternalName() {
/*  167 */     return this._internalName.getSimpleName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyName getWrapperName() {
/*  176 */     AnnotatedMember member = getPrimaryMember();
/*  177 */     return (member == null || this._annotationIntrospector == null) ? null : this._annotationIntrospector.findWrapperName(member);
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
/*      */   public boolean isExplicitlyIncluded() {
/*  191 */     return (_anyExplicits(this._fields) || _anyExplicits(this._getters) || _anyExplicits(this._setters) || _anyExplicitNames(this._ctorParameters));
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
/*      */   public boolean isExplicitlyNamed() {
/*  204 */     return (_anyExplicitNames(this._fields) || _anyExplicitNames(this._getters) || _anyExplicitNames(this._setters) || _anyExplicitNames(this._ctorParameters));
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
/*      */   public PropertyMetadata getMetadata() {
/*  219 */     if (this._metadata == null) {
/*  220 */       Boolean b = _findRequired();
/*  221 */       String desc = _findDescription();
/*  222 */       Integer idx = _findIndex();
/*  223 */       String def = _findDefaultValue();
/*  224 */       if (b == null && idx == null && def == null) {
/*  225 */         this._metadata = (desc == null) ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(desc);
/*      */       } else {
/*      */         
/*  228 */         this._metadata = PropertyMetadata.construct(b, desc, idx, def);
/*      */       } 
/*  230 */       if (!this._forSerialization) {
/*  231 */         this._metadata = _getSetterInfo(this._metadata);
/*      */       }
/*      */     } 
/*  234 */     return this._metadata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyMetadata _getSetterInfo(PropertyMetadata metadata) {
/*  244 */     boolean needMerge = true;
/*  245 */     Nulls valueNulls = null;
/*  246 */     Nulls contentNulls = null;
/*      */ 
/*      */ 
/*      */     
/*  250 */     AnnotatedMember prim = getPrimaryMember();
/*  251 */     AnnotatedMember acc = getAccessor();
/*      */     
/*  253 */     if (prim != null) {
/*      */       
/*  255 */       if (this._annotationIntrospector != null) {
/*  256 */         if (acc != null) {
/*  257 */           Boolean b = this._annotationIntrospector.findMergeInfo(prim);
/*  258 */           if (b != null) {
/*  259 */             needMerge = false;
/*  260 */             if (b.booleanValue()) {
/*  261 */               metadata = metadata.withMergeInfo(PropertyMetadata.MergeInfo.createForPropertyOverride(acc));
/*      */             }
/*      */           } 
/*      */         } 
/*  265 */         JsonSetter.Value setterInfo = this._annotationIntrospector.findSetterInfo(prim);
/*  266 */         if (setterInfo != null) {
/*  267 */           valueNulls = setterInfo.nonDefaultValueNulls();
/*  268 */           contentNulls = setterInfo.nonDefaultContentNulls();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  273 */       if (needMerge || valueNulls == null || contentNulls == null) {
/*  274 */         Class<?> rawType = getRawPrimaryType();
/*  275 */         ConfigOverride co = this._config.getConfigOverride(rawType);
/*  276 */         JsonSetter.Value setterInfo = co.getSetterInfo();
/*  277 */         if (setterInfo != null) {
/*  278 */           if (valueNulls == null) {
/*  279 */             valueNulls = setterInfo.nonDefaultValueNulls();
/*      */           }
/*  281 */           if (contentNulls == null) {
/*  282 */             contentNulls = setterInfo.nonDefaultContentNulls();
/*      */           }
/*      */         } 
/*  285 */         if (needMerge && acc != null) {
/*  286 */           Boolean b = co.getMergeable();
/*  287 */           if (b != null) {
/*  288 */             needMerge = false;
/*  289 */             if (b.booleanValue()) {
/*  290 */               metadata = metadata.withMergeInfo(PropertyMetadata.MergeInfo.createForTypeOverride(acc));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  296 */     if (needMerge || valueNulls == null || contentNulls == null) {
/*  297 */       JsonSetter.Value setterInfo = this._config.getDefaultSetterInfo();
/*  298 */       if (valueNulls == null) {
/*  299 */         valueNulls = setterInfo.nonDefaultValueNulls();
/*      */       }
/*  301 */       if (contentNulls == null) {
/*  302 */         contentNulls = setterInfo.nonDefaultContentNulls();
/*      */       }
/*  304 */       if (needMerge) {
/*  305 */         Boolean b = this._config.getDefaultMergeable();
/*  306 */         if (Boolean.TRUE.equals(b) && acc != null) {
/*  307 */           metadata = metadata.withMergeInfo(PropertyMetadata.MergeInfo.createForDefaults(acc));
/*      */         }
/*      */       } 
/*      */     } 
/*  311 */     if (valueNulls != null || contentNulls != null) {
/*  312 */       metadata = metadata.withNulls(valueNulls, contentNulls);
/*      */     }
/*  314 */     return metadata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JavaType getPrimaryType() {
/*  324 */     if (this._forSerialization) {
/*  325 */       AnnotatedMember annotatedMember = getGetter();
/*  326 */       if (annotatedMember == null) {
/*  327 */         annotatedMember = getField();
/*  328 */         if (annotatedMember == null)
/*      */         {
/*  330 */           return TypeFactory.unknownType();
/*      */         }
/*  332 */         return annotatedMember.getType();
/*      */       } 
/*  334 */       return annotatedMember.getType();
/*      */     } 
/*  336 */     AnnotatedMember m = getConstructorParameter();
/*  337 */     if (m == null) {
/*  338 */       m = getSetter();
/*      */ 
/*      */       
/*  341 */       if (m != null) {
/*  342 */         return ((AnnotatedMethod)m).getParameterType(0);
/*      */       }
/*  344 */       m = getField();
/*      */     } 
/*      */     
/*  347 */     if (m == null) {
/*  348 */       m = getGetter();
/*  349 */       if (m == null) {
/*  350 */         return TypeFactory.unknownType();
/*      */       }
/*      */     } 
/*  353 */     return m.getType();
/*      */   }
/*      */ 
/*      */   
/*      */   public Class<?> getRawPrimaryType() {
/*  358 */     return getPrimaryType().getRawClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasGetter() {
/*  368 */     return (this._getters != null);
/*      */   }
/*      */   public boolean hasSetter() {
/*  371 */     return (this._setters != null);
/*      */   }
/*      */   public boolean hasField() {
/*  374 */     return (this._fields != null);
/*      */   }
/*      */   public boolean hasConstructorParameter() {
/*  377 */     return (this._ctorParameters != null);
/*      */   }
/*      */   
/*      */   public boolean couldDeserialize() {
/*  381 */     return (this._ctorParameters != null || this._setters != null || this._fields != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean couldSerialize() {
/*  386 */     return (this._getters != null || this._fields != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedMethod getGetter() {
/*  393 */     Linked<AnnotatedMethod> curr = this._getters;
/*  394 */     if (curr == null) {
/*  395 */       return null;
/*      */     }
/*  397 */     Linked<AnnotatedMethod> next = curr.next;
/*  398 */     if (next == null) {
/*  399 */       return (AnnotatedMethod)curr.value;
/*      */     }
/*      */     
/*  402 */     for (; next != null; next = next.next) {
/*      */ 
/*      */ 
/*      */       
/*  406 */       Class<?> currClass = ((AnnotatedMethod)curr.value).getDeclaringClass();
/*  407 */       Class<?> nextClass = ((AnnotatedMethod)next.value).getDeclaringClass();
/*  408 */       if (currClass != nextClass) {
/*  409 */         if (currClass.isAssignableFrom(nextClass)) {
/*  410 */           curr = next;
/*      */           continue;
/*      */         } 
/*  413 */         if (nextClass.isAssignableFrom(currClass)) {
/*      */           continue;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  423 */       int priNext = _getterPriority((AnnotatedMethod)next.value);
/*  424 */       int priCurr = _getterPriority((AnnotatedMethod)curr.value);
/*      */       
/*  426 */       if (priNext != priCurr) {
/*  427 */         if (priNext < priCurr) {
/*  428 */           curr = next;
/*      */         }
/*      */       } else {
/*      */         
/*  432 */         throw new IllegalArgumentException("Conflicting getter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod)curr.value).getFullName() + " vs " + ((AnnotatedMethod)next.value).getFullName());
/*      */       } 
/*      */       continue;
/*      */     } 
/*  436 */     this._getters = curr.withoutNext();
/*  437 */     return (AnnotatedMethod)curr.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedMethod getSetter() {
/*  444 */     Linked<AnnotatedMethod> curr = this._setters;
/*  445 */     if (curr == null) {
/*  446 */       return null;
/*      */     }
/*  448 */     Linked<AnnotatedMethod> next = curr.next;
/*  449 */     if (next == null) {
/*  450 */       return (AnnotatedMethod)curr.value;
/*      */     }
/*      */     
/*  453 */     for (; next != null; next = next.next) {
/*      */       
/*  455 */       Class<?> currClass = ((AnnotatedMethod)curr.value).getDeclaringClass();
/*  456 */       Class<?> nextClass = ((AnnotatedMethod)next.value).getDeclaringClass();
/*  457 */       if (currClass != nextClass) {
/*  458 */         if (currClass.isAssignableFrom(nextClass)) {
/*  459 */           curr = next;
/*      */           continue;
/*      */         } 
/*  462 */         if (nextClass.isAssignableFrom(currClass)) {
/*      */           continue;
/*      */         }
/*      */       } 
/*  466 */       AnnotatedMethod nextM = (AnnotatedMethod)next.value;
/*  467 */       AnnotatedMethod currM = (AnnotatedMethod)curr.value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  474 */       int priNext = _setterPriority(nextM);
/*  475 */       int priCurr = _setterPriority(currM);
/*      */       
/*  477 */       if (priNext != priCurr)
/*  478 */       { if (priNext < priCurr) {
/*  479 */           curr = next;
/*      */         
/*      */         }
/*      */          }
/*      */       
/*  484 */       else if (this._annotationIntrospector != null)
/*  485 */       { AnnotatedMethod pref = this._annotationIntrospector.resolveSetterConflict(this._config, currM, nextM);
/*      */ 
/*      */ 
/*      */         
/*  489 */         if (pref != currM)
/*      */         {
/*      */           
/*  492 */           if (pref == nextM)
/*  493 */           { curr = next; }
/*      */           
/*      */           else
/*      */           
/*  497 */           { throw new IllegalArgumentException(String.format("Conflicting setter definitions for property \"%s\": %s vs %s", new Object[] { getName(), ((AnnotatedMethod)curr.value).getFullName(), ((AnnotatedMethod)next.value).getFullName() })); }  }  } else { throw new IllegalArgumentException(String.format("Conflicting setter definitions for property \"%s\": %s vs %s", new Object[] { getName(), ((AnnotatedMethod)curr.value).getFullName(), ((AnnotatedMethod)next.value).getFullName() })); }
/*      */ 
/*      */       
/*      */       continue;
/*      */     } 
/*  502 */     this._setters = curr.withoutNext();
/*  503 */     return (AnnotatedMethod)curr.value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedField getField() {
/*  509 */     if (this._fields == null) {
/*  510 */       return null;
/*      */     }
/*      */     
/*  513 */     AnnotatedField field = (AnnotatedField)this._fields.value;
/*  514 */     Linked<AnnotatedField> next = this._fields.next;
/*  515 */     for (; next != null; next = next.next) {
/*  516 */       AnnotatedField nextField = (AnnotatedField)next.value;
/*  517 */       Class<?> fieldClass = field.getDeclaringClass();
/*  518 */       Class<?> nextClass = nextField.getDeclaringClass();
/*  519 */       if (fieldClass != nextClass) {
/*  520 */         if (fieldClass.isAssignableFrom(nextClass)) {
/*  521 */           field = nextField;
/*      */           continue;
/*      */         } 
/*  524 */         if (nextClass.isAssignableFrom(fieldClass)) {
/*      */           continue;
/*      */         }
/*      */       } 
/*  528 */       throw new IllegalArgumentException("Multiple fields representing property \"" + getName() + "\": " + field.getFullName() + " vs " + nextField.getFullName());
/*      */     } 
/*      */     
/*  531 */     return field;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedParameter getConstructorParameter() {
/*  537 */     if (this._ctorParameters == null) {
/*  538 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  548 */     Linked<AnnotatedParameter> curr = this._ctorParameters;
/*      */     while (true) {
/*  550 */       if (((AnnotatedParameter)curr.value).getOwner() instanceof AnnotatedConstructor) {
/*  551 */         return (AnnotatedParameter)curr.value;
/*      */       }
/*  553 */       curr = curr.next;
/*  554 */       if (curr == null)
/*  555 */         return (AnnotatedParameter)this._ctorParameters.value; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Iterator<AnnotatedParameter> getConstructorParameters() {
/*  560 */     if (this._ctorParameters == null) {
/*  561 */       return ClassUtil.emptyIterator();
/*      */     }
/*  563 */     return new MemberIterator<>(this._ctorParameters);
/*      */   }
/*      */ 
/*      */   
/*      */   public AnnotatedMember getPrimaryMember() {
/*  568 */     if (this._forSerialization) {
/*  569 */       return getAccessor();
/*      */     }
/*  571 */     AnnotatedMember m = getMutator();
/*      */     
/*  573 */     if (m == null) {
/*  574 */       m = getAccessor();
/*      */     }
/*  576 */     return m;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int _getterPriority(AnnotatedMethod m) {
/*  581 */     String name = m.getName();
/*      */     
/*  583 */     if (name.startsWith("get") && name.length() > 3)
/*      */     {
/*  585 */       return 1;
/*      */     }
/*  587 */     if (name.startsWith("is") && name.length() > 2) {
/*  588 */       return 2;
/*      */     }
/*  590 */     return 3;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int _setterPriority(AnnotatedMethod m) {
/*  595 */     String name = m.getName();
/*  596 */     if (name.startsWith("set") && name.length() > 3)
/*      */     {
/*  598 */       return 1;
/*      */     }
/*  600 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?>[] findViews() {
/*  611 */     return fromMemberAnnotations((WithMember)new WithMember<Class<?>[]>()
/*      */         {
/*      */           public Class<?>[] withMember(AnnotatedMember member) {
/*  614 */             return POJOPropertyBuilder.this._annotationIntrospector.findViews(member);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationIntrospector.ReferenceProperty findReferenceType() {
/*  623 */     AnnotationIntrospector.ReferenceProperty result = this._referenceInfo;
/*  624 */     if (result != null) {
/*  625 */       if (result == NOT_REFEFERENCE_PROP) {
/*  626 */         return null;
/*      */       }
/*  628 */       return result;
/*      */     } 
/*  630 */     result = fromMemberAnnotations(new WithMember<AnnotationIntrospector.ReferenceProperty>()
/*      */         {
/*      */           public AnnotationIntrospector.ReferenceProperty withMember(AnnotatedMember member) {
/*  633 */             return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(member);
/*      */           }
/*      */         });
/*  636 */     this._referenceInfo = (result == null) ? NOT_REFEFERENCE_PROP : result;
/*  637 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTypeId() {
/*  642 */     Boolean b = fromMemberAnnotations(new WithMember<Boolean>()
/*      */         {
/*      */           public Boolean withMember(AnnotatedMember member) {
/*  645 */             return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(member);
/*      */           }
/*      */         });
/*  648 */     return (b != null && b.booleanValue());
/*      */   }
/*      */   
/*      */   protected Boolean _findRequired() {
/*  652 */     return fromMemberAnnotations(new WithMember<Boolean>()
/*      */         {
/*      */           public Boolean withMember(AnnotatedMember member) {
/*  655 */             return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(member);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected String _findDescription() {
/*  661 */     return fromMemberAnnotations(new WithMember<String>()
/*      */         {
/*      */           public String withMember(AnnotatedMember member) {
/*  664 */             return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDescription(member);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected Integer _findIndex() {
/*  670 */     return fromMemberAnnotations(new WithMember<Integer>()
/*      */         {
/*      */           public Integer withMember(AnnotatedMember member) {
/*  673 */             return POJOPropertyBuilder.this._annotationIntrospector.findPropertyIndex(member);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected String _findDefaultValue() {
/*  679 */     return fromMemberAnnotations(new WithMember<String>()
/*      */         {
/*      */           public String withMember(AnnotatedMember member) {
/*  682 */             return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDefaultValue(member);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectIdInfo findObjectIdInfo() {
/*  689 */     return fromMemberAnnotations(new WithMember<ObjectIdInfo>()
/*      */         {
/*      */           public ObjectIdInfo withMember(AnnotatedMember member) {
/*  692 */             ObjectIdInfo info = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(member);
/*  693 */             if (info != null) {
/*  694 */               info = POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(member, info);
/*      */             }
/*  696 */             return info;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public JsonInclude.Value findInclusion() {
/*  703 */     AnnotatedMember a = getAccessor();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  708 */     JsonInclude.Value v = (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findPropertyInclusion(a);
/*      */     
/*  710 */     return (v == null) ? JsonInclude.Value.empty() : v;
/*      */   }
/*      */   
/*      */   public JsonProperty.Access findAccess() {
/*  714 */     return fromMemberAnnotationsExcept(new WithMember<JsonProperty.Access>()
/*      */         {
/*      */           public JsonProperty.Access withMember(AnnotatedMember member) {
/*  717 */             return POJOPropertyBuilder.this._annotationIntrospector.findPropertyAccess(member);
/*      */           }
/*      */         },  JsonProperty.Access.AUTO);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addField(AnnotatedField a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
/*  729 */     this._fields = new Linked<>(a, this._fields, name, explName, visible, ignored);
/*      */   }
/*      */   
/*      */   public void addCtor(AnnotatedParameter a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
/*  733 */     this._ctorParameters = new Linked<>(a, this._ctorParameters, name, explName, visible, ignored);
/*      */   }
/*      */   
/*      */   public void addGetter(AnnotatedMethod a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
/*  737 */     this._getters = new Linked<>(a, this._getters, name, explName, visible, ignored);
/*      */   }
/*      */   
/*      */   public void addSetter(AnnotatedMethod a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
/*  741 */     this._setters = new Linked<>(a, this._setters, name, explName, visible, ignored);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAll(POJOPropertyBuilder src) {
/*  750 */     this._fields = merge(this._fields, src._fields);
/*  751 */     this._ctorParameters = merge(this._ctorParameters, src._ctorParameters);
/*  752 */     this._getters = merge(this._getters, src._getters);
/*  753 */     this._setters = merge(this._setters, src._setters);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> Linked<T> merge(Linked<T> chain1, Linked<T> chain2) {
/*  758 */     if (chain1 == null) {
/*  759 */       return chain2;
/*      */     }
/*  761 */     if (chain2 == null) {
/*  762 */       return chain1;
/*      */     }
/*  764 */     return chain1.append(chain2);
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
/*      */   public void removeIgnored() {
/*  779 */     this._fields = _removeIgnored(this._fields);
/*  780 */     this._getters = _removeIgnored(this._getters);
/*  781 */     this._setters = _removeIgnored(this._setters);
/*  782 */     this._ctorParameters = _removeIgnored(this._ctorParameters);
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
/*      */   public JsonProperty.Access removeNonVisible(boolean inferMutators) {
/*  795 */     JsonProperty.Access acc = findAccess();
/*  796 */     if (acc == null) {
/*  797 */       acc = JsonProperty.Access.AUTO;
/*      */     }
/*  799 */     switch (acc) {
/*      */       
/*      */       case READ_ONLY:
/*  802 */         this._setters = null;
/*  803 */         this._ctorParameters = null;
/*  804 */         if (!this._forSerialization) {
/*  805 */           this._fields = null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case READ_WRITE:
/*  828 */         return acc;
/*      */       case WRITE_ONLY:
/*      */         this._getters = null; if (this._forSerialization)
/*      */           this._fields = null; 
/*      */     }  this._getters = _removeNonVisible(this._getters);
/*      */     this._ctorParameters = _removeNonVisible(this._ctorParameters);
/*      */     if (!inferMutators || this._getters == null) {
/*      */       this._fields = _removeNonVisible(this._fields);
/*      */       this._setters = _removeNonVisible(this._setters);
/*  837 */     }  } public void removeConstructors() { this._ctorParameters = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trimByVisibility() {
/*  847 */     this._fields = _trimByVisibility(this._fields);
/*  848 */     this._getters = _trimByVisibility(this._getters);
/*  849 */     this._setters = _trimByVisibility(this._setters);
/*  850 */     this._ctorParameters = _trimByVisibility(this._ctorParameters);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeAnnotations(boolean forSerialization) {
/*  856 */     if (forSerialization) {
/*  857 */       if (this._getters != null) {
/*  858 */         AnnotationMap ann = _mergeAnnotations(0, (Linked<? extends AnnotatedMember>[])new Linked[] { this._getters, this._fields, this._ctorParameters, this._setters });
/*  859 */         this._getters = _applyAnnotations(this._getters, ann);
/*  860 */       } else if (this._fields != null) {
/*  861 */         AnnotationMap ann = _mergeAnnotations(0, (Linked<? extends AnnotatedMember>[])new Linked[] { this._fields, this._ctorParameters, this._setters });
/*  862 */         this._fields = _applyAnnotations(this._fields, ann);
/*      */       }
/*      */     
/*  865 */     } else if (this._ctorParameters != null) {
/*  866 */       AnnotationMap ann = _mergeAnnotations(0, (Linked<? extends AnnotatedMember>[])new Linked[] { this._ctorParameters, this._setters, this._fields, this._getters });
/*  867 */       this._ctorParameters = _applyAnnotations(this._ctorParameters, ann);
/*  868 */     } else if (this._setters != null) {
/*  869 */       AnnotationMap ann = _mergeAnnotations(0, (Linked<? extends AnnotatedMember>[])new Linked[] { this._setters, this._fields, this._getters });
/*  870 */       this._setters = _applyAnnotations(this._setters, ann);
/*  871 */     } else if (this._fields != null) {
/*  872 */       AnnotationMap ann = _mergeAnnotations(0, (Linked<? extends AnnotatedMember>[])new Linked[] { this._fields, this._getters });
/*  873 */       this._fields = _applyAnnotations(this._fields, ann);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationMap _mergeAnnotations(int index, Linked<? extends AnnotatedMember>... nodes) {
/*  881 */     AnnotationMap ann = _getAllAnnotations(nodes[index]);
/*  882 */     while (++index < nodes.length) {
/*  883 */       if (nodes[index] != null) {
/*  884 */         return AnnotationMap.merge(ann, _mergeAnnotations(index, nodes));
/*      */       }
/*      */     } 
/*  887 */     return ann;
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
/*      */   private <T extends AnnotatedMember> AnnotationMap _getAllAnnotations(Linked<T> node) {
/*  900 */     AnnotationMap ann = ((AnnotatedMember)node.value).getAllAnnotations();
/*  901 */     if (node.next != null) {
/*  902 */       ann = AnnotationMap.merge(ann, _getAllAnnotations(node.next));
/*      */     }
/*  904 */     return ann;
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
/*      */   private <T extends AnnotatedMember> Linked<T> _applyAnnotations(Linked<T> node, AnnotationMap ann) {
/*  918 */     AnnotatedMember annotatedMember = (AnnotatedMember)((AnnotatedMember)node.value).withAnnotations(ann);
/*  919 */     if (node.next != null) {
/*  920 */       node = node.withNext(_applyAnnotations(node.next, ann));
/*      */     }
/*  922 */     return node.withValue((T)annotatedMember);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> Linked<T> _removeIgnored(Linked<T> node) {
/*  927 */     if (node == null) {
/*  928 */       return node;
/*      */     }
/*  930 */     return node.withoutIgnored();
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> Linked<T> _removeNonVisible(Linked<T> node) {
/*  935 */     if (node == null) {
/*  936 */       return node;
/*      */     }
/*  938 */     return node.withoutNonVisible();
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> Linked<T> _trimByVisibility(Linked<T> node) {
/*  943 */     if (node == null) {
/*  944 */       return node;
/*      */     }
/*  946 */     return node.trimByVisibility();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> boolean _anyExplicits(Linked<T> n) {
/*  957 */     for (; n != null; n = n.next) {
/*  958 */       if (n.name != null && n.name.hasSimpleName()) {
/*  959 */         return true;
/*      */       }
/*      */     } 
/*  962 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> boolean _anyExplicitNames(Linked<T> n) {
/*  967 */     for (; n != null; n = n.next) {
/*  968 */       if (n.name != null && n.isNameExplicit) {
/*  969 */         return true;
/*      */       }
/*      */     } 
/*  972 */     return false;
/*      */   }
/*      */   
/*      */   public boolean anyVisible() {
/*  976 */     return (_anyVisible(this._fields) || _anyVisible(this._getters) || _anyVisible(this._setters) || _anyVisible(this._ctorParameters));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> boolean _anyVisible(Linked<T> n) {
/*  985 */     for (; n != null; n = n.next) {
/*  986 */       if (n.isVisible) {
/*  987 */         return true;
/*      */       }
/*      */     } 
/*  990 */     return false;
/*      */   }
/*      */   
/*      */   public boolean anyIgnorals() {
/*  994 */     return (_anyIgnorals(this._fields) || _anyIgnorals(this._getters) || _anyIgnorals(this._setters) || _anyIgnorals(this._ctorParameters));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> boolean _anyIgnorals(Linked<T> n) {
/* 1003 */     for (; n != null; n = n.next) {
/* 1004 */       if (n.isMarkedIgnored) {
/* 1005 */         return true;
/*      */       }
/*      */     } 
/* 1008 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<PropertyName> findExplicitNames() {
/* 1019 */     Set<PropertyName> renamed = null;
/* 1020 */     renamed = _findExplicitNames((Linked)this._fields, renamed);
/* 1021 */     renamed = _findExplicitNames((Linked)this._getters, renamed);
/* 1022 */     renamed = _findExplicitNames((Linked)this._setters, renamed);
/* 1023 */     renamed = _findExplicitNames((Linked)this._ctorParameters, renamed);
/* 1024 */     if (renamed == null) {
/* 1025 */       return Collections.emptySet();
/*      */     }
/* 1027 */     return renamed;
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
/*      */   public Collection<POJOPropertyBuilder> explode(Collection<PropertyName> newNames) {
/* 1040 */     HashMap<PropertyName, POJOPropertyBuilder> props = new HashMap<>();
/* 1041 */     _explode(newNames, props, this._fields);
/* 1042 */     _explode(newNames, props, this._getters);
/* 1043 */     _explode(newNames, props, this._setters);
/* 1044 */     _explode(newNames, props, this._ctorParameters);
/* 1045 */     return props.values();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void _explode(Collection<PropertyName> newNames, Map<PropertyName, POJOPropertyBuilder> props, Linked<?> accessors) {
/* 1053 */     Linked<?> firstAcc = accessors;
/* 1054 */     for (Linked<?> node = accessors; node != null; node = node.next) {
/* 1055 */       PropertyName name = node.name;
/* 1056 */       if (!node.isNameExplicit || name == null) {
/*      */         
/* 1058 */         if (node.isVisible)
/*      */         {
/*      */ 
/*      */           
/* 1062 */           throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found multiple explicit names: " + newNames + ", but also implicit accessor: " + node);
/*      */         }
/*      */       } else {
/*      */         
/* 1066 */         POJOPropertyBuilder prop = props.get(name);
/* 1067 */         if (prop == null) {
/* 1068 */           prop = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, this._internalName, name);
/*      */           
/* 1070 */           props.put(name, prop);
/*      */         } 
/*      */         
/* 1073 */         if (firstAcc == this._fields) {
/* 1074 */           Linked<AnnotatedField> n2 = (Linked)node;
/* 1075 */           prop._fields = n2.withNext(prop._fields);
/* 1076 */         } else if (firstAcc == this._getters) {
/* 1077 */           Linked<AnnotatedMethod> n2 = (Linked)node;
/* 1078 */           prop._getters = n2.withNext(prop._getters);
/* 1079 */         } else if (firstAcc == this._setters) {
/* 1080 */           Linked<AnnotatedMethod> n2 = (Linked)node;
/* 1081 */           prop._setters = n2.withNext(prop._setters);
/* 1082 */         } else if (firstAcc == this._ctorParameters) {
/* 1083 */           Linked<AnnotatedParameter> n2 = (Linked)node;
/* 1084 */           prop._ctorParameters = n2.withNext(prop._ctorParameters);
/*      */         } else {
/* 1086 */           throw new IllegalStateException("Internal error: mismatched accessors, property: " + this);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<PropertyName> _findExplicitNames(Linked<? extends AnnotatedMember> node, Set<PropertyName> renamed) {
/* 1094 */     for (; node != null; node = node.next) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1101 */       if (node.isNameExplicit && node.name != null) {
/*      */ 
/*      */         
/* 1104 */         if (renamed == null) {
/* 1105 */           renamed = new HashSet<>();
/*      */         }
/* 1107 */         renamed.add(node.name);
/*      */       } 
/* 1109 */     }  return renamed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1116 */     StringBuilder sb = new StringBuilder();
/* 1117 */     sb.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1123 */     sb.append("]");
/* 1124 */     return sb.toString();
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
/*      */   protected <T> T fromMemberAnnotations(WithMember<T> func) {
/* 1139 */     T result = null;
/* 1140 */     if (this._annotationIntrospector != null) {
/* 1141 */       if (this._forSerialization) {
/* 1142 */         if (this._getters != null) {
/* 1143 */           result = func.withMember((AnnotatedMember)this._getters.value);
/*      */         }
/*      */       } else {
/* 1146 */         if (this._ctorParameters != null) {
/* 1147 */           result = func.withMember((AnnotatedMember)this._ctorParameters.value);
/*      */         }
/* 1149 */         if (result == null && this._setters != null) {
/* 1150 */           result = func.withMember((AnnotatedMember)this._setters.value);
/*      */         }
/*      */       } 
/* 1153 */       if (result == null && this._fields != null) {
/* 1154 */         result = func.withMember((AnnotatedMember)this._fields.value);
/*      */       }
/*      */     } 
/* 1157 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected <T> T fromMemberAnnotationsExcept(WithMember<T> func, T defaultValue) {
/* 1162 */     if (this._annotationIntrospector == null) {
/* 1163 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1168 */     if (this._forSerialization) {
/* 1169 */       if (this._getters != null) {
/* 1170 */         T result = func.withMember((AnnotatedMember)this._getters.value);
/* 1171 */         if (result != null && result != defaultValue) {
/* 1172 */           return result;
/*      */         }
/*      */       } 
/* 1175 */       if (this._fields != null) {
/* 1176 */         T result = func.withMember((AnnotatedMember)this._fields.value);
/* 1177 */         if (result != null && result != defaultValue) {
/* 1178 */           return result;
/*      */         }
/*      */       } 
/* 1181 */       if (this._ctorParameters != null) {
/* 1182 */         T result = func.withMember((AnnotatedMember)this._ctorParameters.value);
/* 1183 */         if (result != null && result != defaultValue) {
/* 1184 */           return result;
/*      */         }
/*      */       } 
/* 1187 */       if (this._setters != null) {
/* 1188 */         T result = func.withMember((AnnotatedMember)this._setters.value);
/* 1189 */         if (result != null && result != defaultValue) {
/* 1190 */           return result;
/*      */         }
/*      */       } 
/* 1193 */       return null;
/*      */     } 
/* 1195 */     if (this._ctorParameters != null) {
/* 1196 */       T result = func.withMember((AnnotatedMember)this._ctorParameters.value);
/* 1197 */       if (result != null && result != defaultValue) {
/* 1198 */         return result;
/*      */       }
/*      */     } 
/* 1201 */     if (this._setters != null) {
/* 1202 */       T result = func.withMember((AnnotatedMember)this._setters.value);
/* 1203 */       if (result != null && result != defaultValue) {
/* 1204 */         return result;
/*      */       }
/*      */     } 
/* 1207 */     if (this._fields != null) {
/* 1208 */       T result = func.withMember((AnnotatedMember)this._fields.value);
/* 1209 */       if (result != null && result != defaultValue) {
/* 1210 */         return result;
/*      */       }
/*      */     } 
/* 1213 */     if (this._getters != null) {
/* 1214 */       T result = func.withMember((AnnotatedMember)this._getters.value);
/* 1215 */       if (result != null && result != defaultValue) {
/* 1216 */         return result;
/*      */       }
/*      */     } 
/* 1219 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static class MemberIterator<T extends AnnotatedMember>
/*      */     implements Iterator<T>
/*      */   {
/*      */     private POJOPropertyBuilder.Linked<T> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MemberIterator(POJOPropertyBuilder.Linked<T> first) {
/* 1241 */       this.next = first;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1246 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public T next() {
/* 1251 */       if (this.next == null) throw new NoSuchElementException(); 
/* 1252 */       AnnotatedMember annotatedMember = (AnnotatedMember)this.next.value;
/* 1253 */       this.next = this.next.next;
/* 1254 */       return (T)annotatedMember;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1259 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final class Linked<T>
/*      */   {
/*      */     public final T value;
/*      */     
/*      */     public final Linked<T> next;
/*      */     
/*      */     public final PropertyName name;
/*      */     
/*      */     public final boolean isNameExplicit;
/*      */     
/*      */     public final boolean isVisible;
/*      */     
/*      */     public final boolean isMarkedIgnored;
/*      */ 
/*      */     
/*      */     public Linked(T v, Linked<T> n, PropertyName name, boolean explName, boolean visible, boolean ignored) {
/* 1281 */       this.value = v;
/* 1282 */       this.next = n;
/*      */       
/* 1284 */       this.name = (name == null || name.isEmpty()) ? null : name;
/*      */       
/* 1286 */       if (explName) {
/* 1287 */         if (this.name == null) {
/* 1288 */           throw new IllegalArgumentException("Cannot pass true for 'explName' if name is null/empty");
/*      */         }
/*      */ 
/*      */         
/* 1292 */         if (!name.hasSimpleName()) {
/* 1293 */           explName = false;
/*      */         }
/*      */       } 
/*      */       
/* 1297 */       this.isNameExplicit = explName;
/* 1298 */       this.isVisible = visible;
/* 1299 */       this.isMarkedIgnored = ignored;
/*      */     }
/*      */     
/*      */     public Linked<T> withoutNext() {
/* 1303 */       if (this.next == null) {
/* 1304 */         return this;
/*      */       }
/* 1306 */       return new Linked(this.value, null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
/*      */     }
/*      */     
/*      */     public Linked<T> withValue(T newValue) {
/* 1310 */       if (newValue == this.value) {
/* 1311 */         return this;
/*      */       }
/* 1313 */       return new Linked(newValue, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
/*      */     }
/*      */     
/*      */     public Linked<T> withNext(Linked<T> newNext) {
/* 1317 */       if (newNext == this.next) {
/* 1318 */         return this;
/*      */       }
/* 1320 */       return new Linked(this.value, newNext, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
/*      */     }
/*      */     
/*      */     public Linked<T> withoutIgnored() {
/* 1324 */       if (this.isMarkedIgnored) {
/* 1325 */         return (this.next == null) ? null : this.next.withoutIgnored();
/*      */       }
/* 1327 */       if (this.next != null) {
/* 1328 */         Linked<T> newNext = this.next.withoutIgnored();
/* 1329 */         if (newNext != this.next) {
/* 1330 */           return withNext(newNext);
/*      */         }
/*      */       } 
/* 1333 */       return this;
/*      */     }
/*      */     
/*      */     public Linked<T> withoutNonVisible() {
/* 1337 */       Linked<T> newNext = (this.next == null) ? null : this.next.withoutNonVisible();
/* 1338 */       return this.isVisible ? withNext(newNext) : newNext;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Linked<T> append(Linked<T> appendable) {
/* 1346 */       if (this.next == null) {
/* 1347 */         return withNext(appendable);
/*      */       }
/* 1349 */       return withNext(this.next.append(appendable));
/*      */     }
/*      */     
/*      */     public Linked<T> trimByVisibility() {
/* 1353 */       if (this.next == null) {
/* 1354 */         return this;
/*      */       }
/* 1356 */       Linked<T> newNext = this.next.trimByVisibility();
/* 1357 */       if (this.name != null) {
/* 1358 */         if (newNext.name == null) {
/* 1359 */           return withNext(null);
/*      */         }
/*      */         
/* 1362 */         return withNext(newNext);
/*      */       } 
/* 1364 */       if (newNext.name != null) {
/* 1365 */         return newNext;
/*      */       }
/*      */       
/* 1368 */       if (this.isVisible == newNext.isVisible) {
/* 1369 */         return withNext(newNext);
/*      */       }
/* 1371 */       return this.isVisible ? withNext(null) : newNext;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1376 */       String msg = String.format("%s[visible=%b,ignore=%b,explicitName=%b]", new Object[] { this.value.toString(), Boolean.valueOf(this.isVisible), Boolean.valueOf(this.isMarkedIgnored), Boolean.valueOf(this.isNameExplicit) });
/*      */       
/* 1378 */       if (this.next != null) {
/* 1379 */         msg = msg + ", " + this.next.toString();
/*      */       }
/* 1381 */       return msg;
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface WithMember<T> {
/*      */     T withMember(AnnotatedMember param1AnnotatedMember);
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\POJOPropertyBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */