/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class NodeCursor
/*     */   extends JsonStreamContext
/*     */ {
/*     */   protected final NodeCursor _parent;
/*     */   protected String _currentName;
/*     */   protected Object _currentValue;
/*     */   
/*     */   public NodeCursor(int contextType, NodeCursor p) {
/*  34 */     this._type = contextType;
/*  35 */     this._index = -1;
/*  36 */     this._parent = p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final NodeCursor getParent() {
/*  47 */     return this._parent;
/*     */   }
/*     */   
/*     */   public final String getCurrentName() {
/*  51 */     return this._currentName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void overrideCurrentName(String name) {
/*  58 */     this._currentName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getCurrentValue() {
/*  63 */     return this._currentValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentValue(Object v) {
/*  68 */     this._currentValue = v;
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
/*     */   public final NodeCursor iterateChildren() {
/*  89 */     JsonNode n = currentNode();
/*  90 */     if (n == null) throw new IllegalStateException("No current node"); 
/*  91 */     if (n.isArray()) {
/*  92 */       return new ArrayCursor(n, this);
/*     */     }
/*  94 */     if (n.isObject()) {
/*  95 */       return new ObjectCursor(n, this);
/*     */     }
/*  97 */     throw new IllegalStateException("Current node of type " + n.getClass().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract JsonToken nextToken();
/*     */ 
/*     */   
/*     */   public abstract JsonToken nextValue();
/*     */   
/*     */   public abstract JsonToken endToken();
/*     */   
/*     */   public abstract JsonNode currentNode();
/*     */   
/*     */   public abstract boolean currentHasChildren();
/*     */   
/*     */   protected static final class RootCursor
/*     */     extends NodeCursor
/*     */   {
/*     */     protected JsonNode _node;
/*     */     protected boolean _done = false;
/*     */     
/*     */     public RootCursor(JsonNode n, NodeCursor p) {
/* 119 */       super(0, p);
/* 120 */       this._node = n;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void overrideCurrentName(String name) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonToken nextToken() {
/* 130 */       if (!this._done) {
/* 131 */         this._done = true;
/* 132 */         return this._node.asToken();
/*     */       } 
/* 134 */       this._node = null;
/* 135 */       return null;
/*     */     }
/*     */     
/*     */     public JsonToken nextValue() {
/* 139 */       return nextToken();
/*     */     } public JsonToken endToken() {
/* 141 */       return null;
/*     */     } public JsonNode currentNode() {
/* 143 */       return this._node;
/*     */     } public boolean currentHasChildren() {
/* 145 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final class ArrayCursor
/*     */     extends NodeCursor
/*     */   {
/*     */     protected Iterator<JsonNode> _contents;
/*     */     
/*     */     protected JsonNode _currentNode;
/*     */ 
/*     */     
/*     */     public ArrayCursor(JsonNode n, NodeCursor p) {
/* 159 */       super(1, p);
/* 160 */       this._contents = n.elements();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonToken nextToken() {
/* 166 */       if (!this._contents.hasNext()) {
/* 167 */         this._currentNode = null;
/* 168 */         return null;
/*     */       } 
/* 170 */       this._currentNode = this._contents.next();
/* 171 */       return this._currentNode.asToken();
/*     */     }
/*     */     
/*     */     public JsonToken nextValue() {
/* 175 */       return nextToken();
/*     */     } public JsonToken endToken() {
/* 177 */       return JsonToken.END_ARRAY;
/*     */     }
/*     */     public JsonNode currentNode() {
/* 180 */       return this._currentNode;
/*     */     }
/*     */     
/*     */     public boolean currentHasChildren() {
/* 184 */       return (((ContainerNode)currentNode()).size() > 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class ObjectCursor
/*     */     extends NodeCursor
/*     */   {
/*     */     protected Iterator<Map.Entry<String, JsonNode>> _contents;
/*     */     
/*     */     protected Map.Entry<String, JsonNode> _current;
/*     */     
/*     */     protected boolean _needEntry;
/*     */ 
/*     */     
/*     */     public ObjectCursor(JsonNode n, NodeCursor p) {
/* 201 */       super(2, p);
/* 202 */       this._contents = ((ObjectNode)n).fields();
/* 203 */       this._needEntry = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonToken nextToken() {
/* 210 */       if (this._needEntry) {
/* 211 */         if (!this._contents.hasNext()) {
/* 212 */           this._currentName = null;
/* 213 */           this._current = null;
/* 214 */           return null;
/*     */         } 
/* 216 */         this._needEntry = false;
/* 217 */         this._current = this._contents.next();
/* 218 */         this._currentName = (this._current == null) ? null : this._current.getKey();
/* 219 */         return JsonToken.FIELD_NAME;
/*     */       } 
/* 221 */       this._needEntry = true;
/* 222 */       return ((JsonNode)this._current.getValue()).asToken();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonToken nextValue() {
/* 228 */       JsonToken t = nextToken();
/* 229 */       if (t == JsonToken.FIELD_NAME) {
/* 230 */         t = nextToken();
/*     */       }
/* 232 */       return t;
/*     */     }
/*     */     
/*     */     public JsonToken endToken() {
/* 236 */       return JsonToken.END_OBJECT;
/*     */     }
/*     */     
/*     */     public JsonNode currentNode() {
/* 240 */       return (this._current == null) ? null : this._current.getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean currentHasChildren() {
/* 245 */       return (((ContainerNode)currentNode()).size() > 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\NodeCursor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */