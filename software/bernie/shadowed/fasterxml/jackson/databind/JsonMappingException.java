/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonIgnore;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonMappingException
/*     */   extends JsonProcessingException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   static final int MAX_REFS_TO_LIST = 1000;
/*     */   protected LinkedList<Reference> _path;
/*     */   protected transient Closeable _processor;
/*     */   
/*     */   public static class Reference
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2L;
/*     */     protected transient Object _from;
/*     */     protected String _fieldName;
/*  66 */     protected int _index = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String _desc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Reference() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference(Object from) {
/*  83 */       this._from = from;
/*     */     }
/*     */     public Reference(Object from, String fieldName) {
/*  86 */       this._from = from;
/*  87 */       if (fieldName == null) {
/*  88 */         throw new NullPointerException("Cannot pass null fieldName");
/*     */       }
/*  90 */       this._fieldName = fieldName;
/*     */     }
/*     */     
/*     */     public Reference(Object from, int index) {
/*  94 */       this._from = from;
/*  95 */       this._index = index;
/*     */     }
/*     */     
/*     */     void setFieldName(String n) {
/*  99 */       this._fieldName = n;
/* 100 */     } void setIndex(int ix) { this._index = ix; } void setDescription(String d) {
/* 101 */       this._desc = d;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @JsonIgnore
/*     */     public Object getFrom() {
/* 114 */       return this._from;
/*     */     }
/* 116 */     public String getFieldName() { return this._fieldName; } public int getIndex() {
/* 117 */       return this._index;
/*     */     } public String getDescription() {
/* 119 */       if (this._desc == null) {
/* 120 */         StringBuilder sb = new StringBuilder();
/*     */         
/* 122 */         if (this._from == null) {
/* 123 */           sb.append("UNKNOWN");
/*     */         } else {
/* 125 */           Class<?> cls = (this._from instanceof Class) ? (Class)this._from : this._from.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 130 */           int arrays = 0;
/* 131 */           while (cls.isArray()) {
/* 132 */             cls = cls.getComponentType();
/* 133 */             arrays++;
/*     */           } 
/* 135 */           sb.append(cls.getName());
/* 136 */           while (--arrays >= 0) {
/* 137 */             sb.append("[]");
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 147 */         sb.append('[');
/* 148 */         if (this._fieldName != null) {
/* 149 */           sb.append('"');
/* 150 */           sb.append(this._fieldName);
/* 151 */           sb.append('"');
/* 152 */         } else if (this._index >= 0) {
/* 153 */           sb.append(this._index);
/*     */         } else {
/* 155 */           sb.append('?');
/*     */         } 
/* 157 */         sb.append(']');
/* 158 */         this._desc = sb.toString();
/*     */       } 
/* 160 */       return this._desc;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 165 */       return getDescription();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object writeReplace() {
/* 176 */       getDescription();
/* 177 */       return this;
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
/*     */   @Deprecated
/*     */   public JsonMappingException(String msg) {
/* 213 */     super(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonMappingException(String msg, Throwable rootCause) {
/* 219 */     super(msg, rootCause);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonMappingException(String msg, JsonLocation loc) {
/* 225 */     super(msg, loc);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JsonMappingException(String msg, JsonLocation loc, Throwable rootCause) {
/* 231 */     super(msg, loc, rootCause);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonMappingException(Closeable processor, String msg) {
/* 237 */     super(msg);
/* 238 */     this._processor = processor;
/* 239 */     if (processor instanceof JsonParser)
/*     */     {
/*     */ 
/*     */       
/* 243 */       this._location = ((JsonParser)processor).getTokenLocation();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonMappingException(Closeable processor, String msg, Throwable problem) {
/* 251 */     super(msg, problem);
/* 252 */     this._processor = processor;
/* 253 */     if (processor instanceof JsonParser) {
/* 254 */       this._location = ((JsonParser)processor).getTokenLocation();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonMappingException(Closeable processor, String msg, JsonLocation loc) {
/* 262 */     super(msg, loc);
/* 263 */     this._processor = processor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(JsonParser p, String msg) {
/* 270 */     return new JsonMappingException((Closeable)p, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(JsonParser p, String msg, Throwable problem) {
/* 277 */     return new JsonMappingException((Closeable)p, msg, problem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(JsonGenerator g, String msg) {
/* 284 */     return new JsonMappingException((Closeable)g, msg, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(JsonGenerator g, String msg, Throwable problem) {
/* 291 */     return new JsonMappingException((Closeable)g, msg, problem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(DeserializationContext ctxt, String msg) {
/* 298 */     return new JsonMappingException((Closeable)ctxt.getParser(), msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(DeserializationContext ctxt, String msg, Throwable t) {
/* 305 */     return new JsonMappingException((Closeable)ctxt.getParser(), msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(SerializerProvider ctxt, String msg) {
/* 312 */     return new JsonMappingException((Closeable)ctxt.getGenerator(), msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException from(SerializerProvider ctxt, String msg, Throwable problem) {
/* 322 */     return new JsonMappingException((Closeable)ctxt.getGenerator(), msg, problem);
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
/*     */   public static JsonMappingException fromUnexpectedIOE(IOException src) {
/* 336 */     return new JsonMappingException(null, String.format("Unexpected IOException (of type %s): %s", new Object[] { src.getClass().getName(), src.getMessage() }));
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
/*     */   public static JsonMappingException wrapWithPath(Throwable src, Object refFrom, String refFieldName) {
/* 351 */     return wrapWithPath(src, new Reference(refFrom, refFieldName));
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
/*     */   public static JsonMappingException wrapWithPath(Throwable src, Object refFrom, int index) {
/* 363 */     return wrapWithPath(src, new Reference(refFrom, index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonMappingException wrapWithPath(Throwable src, Reference ref) {
/*     */     JsonMappingException jme;
/* 375 */     if (src instanceof JsonMappingException) {
/* 376 */       jme = (JsonMappingException)src;
/*     */     } else {
/* 378 */       String msg = src.getMessage();
/*     */       
/* 380 */       if (msg == null || msg.length() == 0) {
/* 381 */         msg = "(was " + src.getClass().getName() + ")";
/*     */       }
/*     */       
/* 384 */       Closeable proc = null;
/* 385 */       if (src instanceof JsonProcessingException) {
/* 386 */         Object proc0 = ((JsonProcessingException)src).getProcessor();
/* 387 */         if (proc0 instanceof Closeable) {
/* 388 */           proc = (Closeable)proc0;
/*     */         }
/*     */       } 
/* 391 */       jme = new JsonMappingException(proc, msg, src);
/*     */     } 
/* 393 */     jme.prependPath(ref);
/* 394 */     return jme;
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
/*     */   public List<Reference> getPath() {
/* 409 */     if (this._path == null) {
/* 410 */       return Collections.emptyList();
/*     */     }
/* 412 */     return Collections.unmodifiableList(this._path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPathReference() {
/* 421 */     return getPathReference(new StringBuilder()).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public StringBuilder getPathReference(StringBuilder sb) {
/* 426 */     _appendPathDesc(sb);
/* 427 */     return sb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependPath(Object referrer, String fieldName) {
/* 436 */     Reference ref = new Reference(referrer, fieldName);
/* 437 */     prependPath(ref);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependPath(Object referrer, int index) {
/* 445 */     Reference ref = new Reference(referrer, index);
/* 446 */     prependPath(ref);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prependPath(Reference r) {
/* 451 */     if (this._path == null) {
/* 452 */       this._path = new LinkedList<>();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 458 */     if (this._path.size() < 1000) {
/* 459 */       this._path.addFirst(r);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonIgnore
/*     */   public Object getProcessor() {
/* 471 */     return this._processor;
/*     */   }
/*     */   
/*     */   public String getLocalizedMessage() {
/* 475 */     return _buildMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 484 */     return _buildMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String _buildMessage() {
/* 492 */     String msg = super.getMessage();
/* 493 */     if (this._path == null) {
/* 494 */       return msg;
/*     */     }
/* 496 */     StringBuilder sb = (msg == null) ? new StringBuilder() : new StringBuilder(msg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 502 */     sb.append(" (through reference chain: ");
/* 503 */     sb = getPathReference(sb);
/* 504 */     sb.append(')');
/* 505 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 511 */     return getClass().getName() + ": " + getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _appendPathDesc(StringBuilder sb) {
/* 522 */     if (this._path == null) {
/*     */       return;
/*     */     }
/* 525 */     Iterator<Reference> it = this._path.iterator();
/* 526 */     while (it.hasNext()) {
/* 527 */       sb.append(((Reference)it.next()).toString());
/* 528 */       if (it.hasNext())
/* 529 */         sb.append("->"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\JsonMappingException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */