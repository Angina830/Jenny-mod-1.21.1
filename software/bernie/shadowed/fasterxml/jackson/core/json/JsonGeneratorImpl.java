/*     */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.base.GeneratorBase;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharacterEscapes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.IOContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JsonGeneratorImpl
/*     */   extends GeneratorBase
/*     */ {
/*  31 */   protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final IOContext _ioContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   protected int[] _outputEscapes = sOutputEscapes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _maximumNonEscapedChar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharacterEscapes _characterEscapes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   protected SerializableString _rootValueSeparator = (SerializableString)DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _cfgUnqNames;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGeneratorImpl(IOContext ctxt, int features, ObjectCodec codec) {
/* 103 */     super(features, codec);
/* 104 */     this._ioContext = ctxt;
/* 105 */     if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(features))
/*     */     {
/* 107 */       this._maximumNonEscapedChar = 127;
/*     */     }
/* 109 */     this._cfgUnqNames = !JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Version version() {
/* 120 */     return VersionUtil.versionFor(getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGenerator enable(JsonGenerator.Feature f) {
/* 131 */     super.enable(f);
/* 132 */     if (f == JsonGenerator.Feature.QUOTE_FIELD_NAMES) {
/* 133 */       this._cfgUnqNames = false;
/*     */     }
/* 135 */     return (JsonGenerator)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator disable(JsonGenerator.Feature f) {
/* 140 */     super.disable(f);
/* 141 */     if (f == JsonGenerator.Feature.QUOTE_FIELD_NAMES) {
/* 142 */       this._cfgUnqNames = true;
/*     */     }
/* 144 */     return (JsonGenerator)this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _checkStdFeatureChanges(int newFeatureFlags, int changedFeatures) {
/* 149 */     super._checkStdFeatureChanges(newFeatureFlags, changedFeatures);
/* 150 */     this._cfgUnqNames = !JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(newFeatureFlags);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator setHighestNonEscapedChar(int charCode) {
/* 155 */     this._maximumNonEscapedChar = (charCode < 0) ? 0 : charCode;
/* 156 */     return (JsonGenerator)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighestEscapedChar() {
/* 161 */     return this._maximumNonEscapedChar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonGenerator setCharacterEscapes(CharacterEscapes esc) {
/* 167 */     this._characterEscapes = esc;
/* 168 */     if (esc == null) {
/* 169 */       this._outputEscapes = sOutputEscapes;
/*     */     } else {
/* 171 */       this._outputEscapes = esc.getEscapeCodesForAscii();
/*     */     } 
/* 173 */     return (JsonGenerator)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharacterEscapes getCharacterEscapes() {
/* 182 */     return this._characterEscapes;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonGenerator setRootValueSeparator(SerializableString sep) {
/* 187 */     this._rootValueSeparator = sep;
/* 188 */     return (JsonGenerator)this;
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
/*     */   public final void writeStringField(String fieldName, String value) throws IOException {
/* 202 */     writeFieldName(fieldName);
/* 203 */     writeString(value);
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
/*     */   protected void _verifyPrettyValueWrite(String typeMsg, int status) throws IOException {
/* 215 */     switch (status) {
/*     */       case 1:
/* 217 */         this._cfgPrettyPrinter.writeArrayValueSeparator((JsonGenerator)this);
/*     */         return;
/*     */       case 2:
/* 220 */         this._cfgPrettyPrinter.writeObjectFieldValueSeparator((JsonGenerator)this);
/*     */         return;
/*     */       case 3:
/* 223 */         this._cfgPrettyPrinter.writeRootValueSeparator((JsonGenerator)this);
/*     */         return;
/*     */       
/*     */       case 0:
/* 227 */         if (this._writeContext.inArray()) {
/* 228 */           this._cfgPrettyPrinter.beforeArrayValues((JsonGenerator)this);
/* 229 */         } else if (this._writeContext.inObject()) {
/* 230 */           this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
/*     */         } 
/*     */         return;
/*     */       case 5:
/* 234 */         _reportCantWriteValueExpectName(typeMsg);
/*     */         return;
/*     */     } 
/* 237 */     _throwInternal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _reportCantWriteValueExpectName(String typeMsg) throws IOException {
/* 244 */     _reportError(String.format("Can not %s, expecting field name (context: %s)", new Object[] { typeMsg, this._writeContext.typeDesc() }));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\JsonGeneratorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */