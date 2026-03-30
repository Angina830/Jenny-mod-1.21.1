/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.PrettyPrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinimalPrettyPrinter
/*     */   implements PrettyPrinter, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected String _rootValueSeparator;
/*     */   protected Separators _separators;
/*     */   
/*     */   public MinimalPrettyPrinter() {
/*  44 */     this(DEFAULT_ROOT_VALUE_SEPARATOR.toString());
/*     */   }
/*     */   
/*     */   public MinimalPrettyPrinter(String rootValueSeparator) {
/*  48 */     this._rootValueSeparator = rootValueSeparator;
/*  49 */     this._separators = DEFAULT_SEPARATORS;
/*     */   }
/*     */   
/*     */   public void setRootValueSeparator(String sep) {
/*  53 */     this._rootValueSeparator = sep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinimalPrettyPrinter setSeparators(Separators separators) {
/*  60 */     this._separators = separators;
/*  61 */     return this;
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
/*  73 */     if (this._rootValueSeparator != null) {
/*  74 */       g.writeRaw(this._rootValueSeparator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartObject(JsonGenerator g) throws IOException {
/*  81 */     g.writeRaw('{');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeObjectEntries(JsonGenerator g) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeObjectFieldValueSeparator(JsonGenerator g) throws IOException {
/* 100 */     g.writeRaw(this._separators.getObjectFieldValueSeparator());
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
/*     */   public void writeObjectEntrySeparator(JsonGenerator g) throws IOException {
/* 113 */     g.writeRaw(this._separators.getObjectEntrySeparator());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndObject(JsonGenerator g, int nrOfEntries) throws IOException {
/* 119 */     g.writeRaw('}');
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartArray(JsonGenerator g) throws IOException {
/* 125 */     g.writeRaw('[');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeArrayValues(JsonGenerator g) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeArrayValueSeparator(JsonGenerator g) throws IOException {
/* 144 */     g.writeRaw(this._separators.getArrayValueSeparator());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException {
/* 150 */     g.writeRaw(']');
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\MinimalPrettyPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */