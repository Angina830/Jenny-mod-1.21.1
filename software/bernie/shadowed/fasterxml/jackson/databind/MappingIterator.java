/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.FormatSchema;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ 
/*     */ public class MappingIterator<T> implements Iterator<T>, Closeable {
/*  16 */   protected static final MappingIterator<?> EMPTY_ITERATOR = new MappingIterator(null, null, null, null, false, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int STATE_CLOSED = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int STATE_NEED_RESYNC = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int STATE_MAY_HAVE_VALUE = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int STATE_HAS_VALUE = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JavaType _type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final DeserializationContext _context;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonDeserializer<T> _deserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonParser _parser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonStreamContext _seqContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final T _updatedValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _closeParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MappingIterator(JavaType type, JsonParser p, DeserializationContext ctxt, JsonDeserializer<?> deser, boolean managedParser, Object valueToUpdate) {
/* 122 */     this._type = type;
/* 123 */     this._parser = p;
/* 124 */     this._context = ctxt;
/* 125 */     this._deserializer = (JsonDeserializer)deser;
/* 126 */     this._closeParser = managedParser;
/* 127 */     if (valueToUpdate == null) {
/* 128 */       this._updatedValue = null;
/*     */     } else {
/* 130 */       this._updatedValue = (T)valueToUpdate;
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
/*     */ 
/*     */     
/* 143 */     if (p == null) {
/* 144 */       this._seqContext = null;
/* 145 */       this._state = 0;
/*     */     } else {
/* 147 */       JsonStreamContext sctxt = p.getParsingContext();
/* 148 */       if (managedParser && p.isExpectedStartArrayToken()) {
/*     */         
/* 150 */         p.clearCurrentToken();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 155 */         JsonToken t = p.getCurrentToken();
/* 156 */         if (t == JsonToken.START_OBJECT || t == JsonToken.START_ARRAY) {
/* 157 */           sctxt = sctxt.getParent();
/*     */         }
/*     */       } 
/* 160 */       this._seqContext = sctxt;
/* 161 */       this._state = 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> MappingIterator<T> emptyIterator() {
/* 167 */     return (MappingIterator)EMPTY_ITERATOR;
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
/*     */   public boolean hasNext() {
/*     */     try {
/* 180 */       return hasNextValue();
/* 181 */     } catch (JsonMappingException e) {
/* 182 */       return ((Boolean)_handleMappingException(e)).booleanValue();
/* 183 */     } catch (IOException e) {
/* 184 */       return ((Boolean)_handleIOException(e)).booleanValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T next() {
/*     */     try {
/* 192 */       return nextValue();
/* 193 */     } catch (JsonMappingException e) {
/* 194 */       throw new RuntimeJsonMappingException(e.getMessage(), e);
/* 195 */     } catch (IOException e) {
/* 196 */       throw new RuntimeException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 202 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 207 */     if (this._state != 0) {
/* 208 */       this._state = 0;
/* 209 */       if (this._parser != null) {
/* 210 */         this._parser.close();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNextValue() throws IOException {
/*     */     JsonToken t;
/* 231 */     switch (this._state) {
/*     */       case 0:
/* 233 */         return false;
/*     */       case 1:
/* 235 */         _resync();
/*     */       
/*     */       case 2:
/* 238 */         t = this._parser.getCurrentToken();
/* 239 */         if (t == null) {
/* 240 */           t = this._parser.nextToken();
/*     */           
/* 242 */           if (t == null || t == JsonToken.END_ARRAY) {
/* 243 */             this._state = 0;
/* 244 */             if (this._closeParser && this._parser != null) {
/* 245 */               this._parser.close();
/*     */             }
/* 247 */             return false;
/*     */           } 
/*     */         } 
/* 250 */         this._state = 3;
/* 251 */         return true;
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public T nextValue() throws IOException {
/* 260 */     switch (this._state) {
/*     */       case 0:
/* 262 */         return _throwNoSuchElement();
/*     */       case 1:
/*     */       case 2:
/* 265 */         if (!hasNextValue()) {
/* 266 */           return _throwNoSuchElement();
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 273 */     int nextState = 1;
/*     */     try {
/*     */       T value;
/* 276 */       if (this._updatedValue == null) {
/* 277 */         value = this._deserializer.deserialize(this._parser, this._context);
/*     */       } else {
/* 279 */         this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
/* 280 */         value = this._updatedValue;
/*     */       } 
/* 282 */       nextState = 2;
/* 283 */       return value;
/*     */     } finally {
/* 285 */       this._state = nextState;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       this._parser.clearCurrentToken();
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
/*     */   public List<T> readAll() throws IOException {
/* 303 */     return readAll(new ArrayList<>());
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
/*     */   public <L extends List<? super T>> L readAll(L resultList) throws IOException {
/* 316 */     while (hasNextValue()) {
/* 317 */       resultList.add(nextValue());
/*     */     }
/* 319 */     return resultList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <C extends java.util.Collection<? super T>> C readAll(C results) throws IOException {
/* 330 */     while (hasNextValue()) {
/* 331 */       results.add(nextValue());
/*     */     }
/* 333 */     return results;
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
/*     */   public JsonParser getParser() {
/* 348 */     return this._parser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FormatSchema getParserSchema() {
/* 359 */     return this._parser.getSchema();
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
/*     */   public JsonLocation getCurrentLocation() {
/* 373 */     return this._parser.getCurrentLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _resync() throws IOException {
/* 384 */     JsonParser p = this._parser;
/*     */     
/* 386 */     if (p.getParsingContext() == this._seqContext) {
/*     */       return;
/*     */     }
/*     */     
/*     */     while (true) {
/* 391 */       JsonToken t = p.nextToken();
/* 392 */       if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
/* 393 */         if (p.getParsingContext() == this._seqContext) {
/* 394 */           p.clearCurrentToken(); return;
/*     */         }  continue;
/*     */       } 
/* 397 */       if (t == JsonToken.START_ARRAY || t == JsonToken.START_OBJECT) {
/* 398 */         p.skipChildren(); continue;
/* 399 */       }  if (t == null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected <R> R _throwNoSuchElement() {
/* 406 */     throw new NoSuchElementException();
/*     */   }
/*     */   
/*     */   protected <R> R _handleMappingException(JsonMappingException e) {
/* 410 */     throw new RuntimeJsonMappingException(e.getMessage(), e);
/*     */   }
/*     */   
/*     */   protected <R> R _handleIOException(IOException e) {
/* 414 */     throw new RuntimeException(e.getMessage(), e);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\MappingIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */