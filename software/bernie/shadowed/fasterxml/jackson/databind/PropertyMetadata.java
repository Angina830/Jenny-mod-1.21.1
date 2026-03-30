/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.Nulls;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyMetadata
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1L;
/*  19 */   public static final PropertyMetadata STD_REQUIRED = new PropertyMetadata(Boolean.TRUE, null, null, null, null, null, null);
/*     */ 
/*     */   
/*  22 */   public static final PropertyMetadata STD_OPTIONAL = new PropertyMetadata(Boolean.FALSE, null, null, null, null, null, null);
/*     */ 
/*     */   
/*  25 */   public static final PropertyMetadata STD_REQUIRED_OR_OPTIONAL = new PropertyMetadata(null, null, null, null, null, null, null);
/*     */   
/*     */   protected final Boolean _required;
/*     */   
/*     */   protected final String _description;
/*     */   
/*     */   protected final Integer _index;
/*     */   
/*     */   protected final String _defaultValue;
/*     */   
/*     */   protected final transient MergeInfo _mergeInfo;
/*     */   
/*     */   protected Nulls _valueNulls;
/*     */   
/*     */   protected Nulls _contentNulls;
/*     */   
/*     */   public static final class MergeInfo
/*     */   {
/*     */     public final AnnotatedMember getter;
/*     */     public final boolean fromDefaults;
/*     */     
/*     */     protected MergeInfo(AnnotatedMember getter, boolean fromDefaults) {
/*  47 */       this.getter = getter;
/*  48 */       this.fromDefaults = fromDefaults;
/*     */     }
/*     */     
/*     */     public static MergeInfo createForDefaults(AnnotatedMember getter) {
/*  52 */       return new MergeInfo(getter, true);
/*     */     }
/*     */     
/*     */     public static MergeInfo createForTypeOverride(AnnotatedMember getter) {
/*  56 */       return new MergeInfo(getter, false);
/*     */     }
/*     */     
/*     */     public static MergeInfo createForPropertyOverride(AnnotatedMember getter) {
/*  60 */       return new MergeInfo(getter, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PropertyMetadata(Boolean req, String desc, Integer index, String def, MergeInfo mergeInfo, Nulls valueNulls, Nulls contentNulls) {
/* 123 */     this._required = req;
/* 124 */     this._description = desc;
/* 125 */     this._index = index;
/* 126 */     this._defaultValue = (def == null || def.isEmpty()) ? null : def;
/* 127 */     this._mergeInfo = mergeInfo;
/* 128 */     this._valueNulls = valueNulls;
/* 129 */     this._contentNulls = contentNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PropertyMetadata construct(Boolean req, String desc, Integer index, String defaultValue) {
/* 137 */     if (desc != null || index != null || defaultValue != null) {
/* 138 */       return new PropertyMetadata(req, desc, index, defaultValue, null, null, null);
/*     */     }
/*     */     
/* 141 */     if (req == null) {
/* 142 */       return STD_REQUIRED_OR_OPTIONAL;
/*     */     }
/* 144 */     return req.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PropertyMetadata construct(boolean req, String desc, Integer index, String defaultValue) {
/* 150 */     if (desc != null || index != null || defaultValue != null) {
/* 151 */       return new PropertyMetadata(Boolean.valueOf(req), desc, index, defaultValue, null, null, null);
/*     */     }
/*     */     
/* 154 */     return req ? STD_REQUIRED : STD_OPTIONAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object readResolve() {
/* 163 */     if (this._description == null && this._index == null && this._defaultValue == null && this._mergeInfo == null && this._valueNulls == null && this._contentNulls == null) {
/*     */ 
/*     */       
/* 166 */       if (this._required == null) {
/* 167 */         return STD_REQUIRED_OR_OPTIONAL;
/*     */       }
/* 169 */       return this._required.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
/*     */     } 
/* 171 */     return this;
/*     */   }
/*     */   
/*     */   public PropertyMetadata withDescription(String desc) {
/* 175 */     return new PropertyMetadata(this._required, desc, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyMetadata withMergeInfo(MergeInfo mergeInfo) {
/* 183 */     return new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, mergeInfo, this._valueNulls, this._contentNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyMetadata withNulls(Nulls valueNulls, Nulls contentNulls) {
/* 192 */     return new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, this._mergeInfo, valueNulls, contentNulls);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyMetadata withDefaultValue(String def) {
/* 197 */     if (def == null || def.isEmpty()) {
/* 198 */       if (this._defaultValue == null) {
/* 199 */         return this;
/*     */       }
/* 201 */       def = null;
/* 202 */     } else if (def.equals(this._defaultValue)) {
/* 203 */       return this;
/*     */     } 
/* 205 */     return new PropertyMetadata(this._required, this._description, this._index, def, this._mergeInfo, this._valueNulls, this._contentNulls);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyMetadata withIndex(Integer index) {
/* 210 */     return new PropertyMetadata(this._required, this._description, index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyMetadata withRequired(Boolean b) {
/* 215 */     if (b == null) {
/* 216 */       if (this._required == null) {
/* 217 */         return this;
/*     */       }
/* 219 */     } else if (b.equals(this._required)) {
/* 220 */       return this;
/*     */     } 
/* 222 */     return new PropertyMetadata(b, this._description, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 232 */     return this._description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultValue() {
/* 237 */     return this._defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDefaultValue() {
/* 245 */     return (this._defaultValue != null);
/*     */   } public boolean isRequired() {
/* 247 */     return (this._required != null && this._required.booleanValue());
/*     */   } public Boolean getRequired() {
/* 249 */     return this._required;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getIndex() {
/* 254 */     return this._index;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIndex() {
/* 259 */     return (this._index != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public MergeInfo getMergeInfo() {
/* 264 */     return this._mergeInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public Nulls getValueNulls() {
/* 269 */     return this._valueNulls;
/*     */   }
/*     */ 
/*     */   
/*     */   public Nulls getContentNulls() {
/* 274 */     return this._contentNulls;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\PropertyMetadata.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */