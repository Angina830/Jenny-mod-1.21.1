/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ConcreteBeanPropertyBase
/*     */   implements BeanProperty, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final PropertyMetadata _metadata;
/*     */   protected transient JsonFormat.Value _propertyFormat;
/*     */   protected transient List<PropertyName> _aliases;
/*     */   
/*     */   protected ConcreteBeanPropertyBase(PropertyMetadata md) {
/*  45 */     this._metadata = (md == null) ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : md;
/*     */   }
/*     */   
/*     */   protected ConcreteBeanPropertyBase(ConcreteBeanPropertyBase src) {
/*  49 */     this._metadata = src._metadata;
/*  50 */     this._propertyFormat = src._propertyFormat;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/*  54 */     return this._metadata.isRequired();
/*     */   }
/*     */   public PropertyMetadata getMetadata() {
/*  57 */     return this._metadata;
/*     */   }
/*     */   public boolean isVirtual() {
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public final JsonFormat.Value findFormatOverrides(AnnotationIntrospector intr) {
/*  65 */     JsonFormat.Value f = null;
/*  66 */     if (intr != null) {
/*  67 */       AnnotatedMember member = getMember();
/*  68 */       if (member != null) {
/*  69 */         f = intr.findFormat(member);
/*     */       }
/*     */     } 
/*  72 */     if (f == null) {
/*  73 */       f = EMPTY_FORMAT;
/*     */     }
/*  75 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonFormat.Value findPropertyFormat(MapperConfig<?> config, Class<?> baseType) {
/*  83 */     JsonFormat.Value v = this._propertyFormat;
/*  84 */     if (v == null) {
/*  85 */       JsonFormat.Value v1 = config.getDefaultPropertyFormat(baseType);
/*  86 */       JsonFormat.Value v2 = null;
/*  87 */       AnnotationIntrospector intr = config.getAnnotationIntrospector();
/*  88 */       if (intr != null) {
/*  89 */         AnnotatedMember member = getMember();
/*  90 */         if (member != null) {
/*  91 */           v2 = intr.findFormat(member);
/*     */         }
/*     */       } 
/*  94 */       if (v1 == null) {
/*  95 */         v = (v2 == null) ? EMPTY_FORMAT : v2;
/*     */       } else {
/*  97 */         v = (v2 == null) ? v1 : v1.withOverrides(v2);
/*     */       } 
/*  99 */       this._propertyFormat = v;
/*     */     } 
/* 101 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonInclude.Value findPropertyInclusion(MapperConfig<?> config, Class<?> baseType) {
/* 107 */     AnnotationIntrospector intr = config.getAnnotationIntrospector();
/* 108 */     AnnotatedMember member = getMember();
/* 109 */     if (member == null) {
/* 110 */       JsonInclude.Value def = config.getDefaultPropertyInclusion(baseType);
/* 111 */       return def;
/*     */     } 
/* 113 */     JsonInclude.Value v0 = config.getDefaultInclusion(baseType, member.getRawType());
/* 114 */     if (intr == null) {
/* 115 */       return v0;
/*     */     }
/* 117 */     JsonInclude.Value v = intr.findPropertyInclusion(member);
/* 118 */     if (v0 == null) {
/* 119 */       return v;
/*     */     }
/* 121 */     return v0.withOverrides(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PropertyName> findAliases(MapperConfig<?> config) {
/* 127 */     List<PropertyName> aliases = this._aliases;
/* 128 */     if (aliases == null) {
/* 129 */       AnnotationIntrospector intr = config.getAnnotationIntrospector();
/* 130 */       if (intr != null) {
/* 131 */         aliases = intr.findPropertyAliases(getMember());
/*     */       }
/* 133 */       if (aliases == null) {
/* 134 */         aliases = Collections.emptyList();
/*     */       }
/* 136 */       this._aliases = aliases;
/*     */     } 
/* 138 */     return aliases;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\ConcreteBeanPropertyBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */