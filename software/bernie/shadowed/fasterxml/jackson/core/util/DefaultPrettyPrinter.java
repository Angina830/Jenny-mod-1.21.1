/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.SerializedString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPrettyPrinter
/*     */   implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   protected Indenter _arrayIndenter = FixedSpaceIndenter.instance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   protected Indenter _objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SerializableString _rootSeparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _spacesInObjectEntries = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient int _nesting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Separators _separators;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _objectFieldValueSeparatorWithSpaces;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter() {
/* 101 */     this((SerializableString)DEFAULT_ROOT_VALUE_SEPARATOR);
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
/*     */   public DefaultPrettyPrinter(String rootSeparator) {
/* 116 */     this((rootSeparator == null) ? null : (SerializableString)new SerializedString(rootSeparator));
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
/*     */   public DefaultPrettyPrinter(SerializableString rootSeparator) {
/* 128 */     this._rootSeparator = rootSeparator;
/* 129 */     withSeparators(DEFAULT_SEPARATORS);
/*     */   }
/*     */   
/*     */   public DefaultPrettyPrinter(DefaultPrettyPrinter base) {
/* 133 */     this(base, base._rootSeparator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter(DefaultPrettyPrinter base, SerializableString rootSeparator) {
/* 139 */     this._arrayIndenter = base._arrayIndenter;
/* 140 */     this._objectIndenter = base._objectIndenter;
/* 141 */     this._spacesInObjectEntries = base._spacesInObjectEntries;
/* 142 */     this._nesting = base._nesting;
/*     */     
/* 144 */     this._separators = base._separators;
/* 145 */     this._objectFieldValueSeparatorWithSpaces = base._objectFieldValueSeparatorWithSpaces;
/*     */     
/* 147 */     this._rootSeparator = rootSeparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter withRootSeparator(SerializableString rootSeparator) {
/* 152 */     if (this._rootSeparator == rootSeparator || (rootSeparator != null && rootSeparator.equals(this._rootSeparator)))
/*     */     {
/* 154 */       return this;
/*     */     }
/* 156 */     return new DefaultPrettyPrinter(this, rootSeparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter withRootSeparator(String rootSeparator) {
/* 163 */     return withRootSeparator((rootSeparator == null) ? null : (SerializableString)new SerializedString(rootSeparator));
/*     */   }
/*     */   
/*     */   public void indentArraysWith(Indenter i) {
/* 167 */     this._arrayIndenter = (i == null) ? NopIndenter.instance : i;
/*     */   }
/*     */   
/*     */   public void indentObjectsWith(Indenter i) {
/* 171 */     this._objectIndenter = (i == null) ? NopIndenter.instance : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter withArrayIndenter(Indenter i) {
/* 178 */     if (i == null) {
/* 179 */       i = NopIndenter.instance;
/*     */     }
/* 181 */     if (this._arrayIndenter == i) {
/* 182 */       return this;
/*     */     }
/* 184 */     DefaultPrettyPrinter pp = new DefaultPrettyPrinter(this);
/* 185 */     pp._arrayIndenter = i;
/* 186 */     return pp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter withObjectIndenter(Indenter i) {
/* 193 */     if (i == null) {
/* 194 */       i = NopIndenter.instance;
/*     */     }
/* 196 */     if (this._objectIndenter == i) {
/* 197 */       return this;
/*     */     }
/* 199 */     DefaultPrettyPrinter pp = new DefaultPrettyPrinter(this);
/* 200 */     pp._objectIndenter = i;
/* 201 */     return pp;
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
/*     */   public DefaultPrettyPrinter withSpacesInObjectEntries() {
/* 213 */     return _withSpaces(true);
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
/*     */   public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
/* 225 */     return _withSpaces(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultPrettyPrinter _withSpaces(boolean state) {
/* 230 */     if (this._spacesInObjectEntries == state) {
/* 231 */       return this;
/*     */     }
/* 233 */     DefaultPrettyPrinter pp = new DefaultPrettyPrinter(this);
/* 234 */     pp._spacesInObjectEntries = state;
/* 235 */     return pp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter withSeparators(Separators separators) {
/* 242 */     this._separators = separators;
/* 243 */     this._objectFieldValueSeparatorWithSpaces = " " + separators.getObjectFieldValueSeparator() + " ";
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPrettyPrinter createInstance() {
/* 255 */     return new DefaultPrettyPrinter(this);
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
/*     */   public void writeRootValueSeparator(JsonGenerator g) throws IOException {
/* 267 */     if (this._rootSeparator != null) {
/* 268 */       g.writeRaw(this._rootSeparator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartObject(JsonGenerator g) throws IOException {
/* 275 */     g.writeRaw('{');
/* 276 */     if (!this._objectIndenter.isInline()) {
/* 277 */       this._nesting++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeObjectEntries(JsonGenerator g) throws IOException {
/* 284 */     this._objectIndenter.writeIndentation(g, this._nesting);
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
/*     */   public void writeObjectFieldValueSeparator(JsonGenerator g) throws IOException {
/* 299 */     if (this._spacesInObjectEntries) {
/* 300 */       g.writeRaw(this._objectFieldValueSeparatorWithSpaces);
/*     */     } else {
/* 302 */       g.writeRaw(this._separators.getObjectFieldValueSeparator());
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
/*     */   public void writeObjectEntrySeparator(JsonGenerator g) throws IOException {
/* 318 */     g.writeRaw(this._separators.getObjectEntrySeparator());
/* 319 */     this._objectIndenter.writeIndentation(g, this._nesting);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndObject(JsonGenerator g, int nrOfEntries) throws IOException {
/* 325 */     if (!this._objectIndenter.isInline()) {
/* 326 */       this._nesting--;
/*     */     }
/* 328 */     if (nrOfEntries > 0) {
/* 329 */       this._objectIndenter.writeIndentation(g, this._nesting);
/*     */     } else {
/* 331 */       g.writeRaw(' ');
/*     */     } 
/* 333 */     g.writeRaw('}');
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartArray(JsonGenerator g) throws IOException {
/* 339 */     if (!this._arrayIndenter.isInline()) {
/* 340 */       this._nesting++;
/*     */     }
/* 342 */     g.writeRaw('[');
/*     */   }
/*     */ 
/*     */   
/*     */   public void beforeArrayValues(JsonGenerator g) throws IOException {
/* 347 */     this._arrayIndenter.writeIndentation(g, this._nesting);
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
/*     */   public void writeArrayValueSeparator(JsonGenerator g) throws IOException {
/* 362 */     g.writeRaw(this._separators.getArrayValueSeparator());
/* 363 */     this._arrayIndenter.writeIndentation(g, this._nesting);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException {
/* 369 */     if (!this._arrayIndenter.isInline()) {
/* 370 */       this._nesting--;
/*     */     }
/* 372 */     if (nrOfValues > 0) {
/* 373 */       this._arrayIndenter.writeIndentation(g, this._nesting);
/*     */     } else {
/* 375 */       g.writeRaw(' ');
/*     */     } 
/* 377 */     g.writeRaw(']');
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Indenter
/*     */   {
/*     */     void writeIndentation(JsonGenerator param1JsonGenerator, int param1Int) throws IOException;
/*     */ 
/*     */     
/*     */     boolean isInline();
/*     */   }
/*     */   
/*     */   public static class NopIndenter
/*     */     implements Indenter, Serializable
/*     */   {
/* 392 */     public static final NopIndenter instance = new NopIndenter();
/*     */ 
/*     */     
/*     */     public void writeIndentation(JsonGenerator g, int level) throws IOException {}
/*     */     
/*     */     public boolean isInline() {
/* 398 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FixedSpaceIndenter
/*     */     extends NopIndenter
/*     */   {
/* 408 */     public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeIndentation(JsonGenerator g, int level) throws IOException {
/* 413 */       g.writeRaw(' ');
/*     */     }
/*     */     
/*     */     public boolean isInline() {
/* 417 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\DefaultPrettyPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */