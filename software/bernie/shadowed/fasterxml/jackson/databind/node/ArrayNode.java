/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonPointer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RawValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayNode
/*     */   extends ContainerNode<ArrayNode>
/*     */ {
/*     */   private final List<JsonNode> _children;
/*     */   
/*     */   public ArrayNode(JsonNodeFactory nf) {
/*  30 */     super(nf);
/*  31 */     this._children = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode(JsonNodeFactory nf, int capacity) {
/*  38 */     super(nf);
/*  39 */     this._children = new ArrayList<>(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode(JsonNodeFactory nf, List<JsonNode> children) {
/*  46 */     super(nf);
/*  47 */     this._children = children;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonNode _at(JsonPointer ptr) {
/*  52 */     return get(ptr.getMatchingIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode deepCopy() {
/*  60 */     ArrayNode ret = new ArrayNode(this._nodeFactory);
/*     */     
/*  62 */     for (JsonNode element : this._children) {
/*  63 */       ret._children.add(element.deepCopy());
/*     */     }
/*  65 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider serializers) {
/*  76 */     return this._children.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNodeType getNodeType() {
/*  87 */     return JsonNodeType.ARRAY;
/*     */   }
/*     */   public JsonToken asToken() {
/*  90 */     return JsonToken.START_ARRAY;
/*     */   }
/*     */   
/*     */   public int size() {
/*  94 */     return this._children.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<JsonNode> elements() {
/*  99 */     return this._children.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonNode get(int index) {
/* 104 */     if (index >= 0 && index < this._children.size()) {
/* 105 */       return this._children.get(index);
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public JsonNode get(String fieldName) {
/* 111 */     return null;
/*     */   }
/*     */   public JsonNode path(String fieldName) {
/* 114 */     return MissingNode.getInstance();
/*     */   }
/*     */   
/*     */   public JsonNode path(int index) {
/* 118 */     if (index >= 0 && index < this._children.size()) {
/* 119 */       return this._children.get(index);
/*     */     }
/* 121 */     return MissingNode.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Comparator<JsonNode> comparator, JsonNode o) {
/* 127 */     if (!(o instanceof ArrayNode)) {
/* 128 */       return false;
/*     */     }
/* 130 */     ArrayNode other = (ArrayNode)o;
/* 131 */     int len = this._children.size();
/* 132 */     if (other.size() != len) {
/* 133 */       return false;
/*     */     }
/* 135 */     List<JsonNode> l1 = this._children;
/* 136 */     List<JsonNode> l2 = other._children;
/* 137 */     for (int i = 0; i < len; i++) {
/* 138 */       if (!((JsonNode)l1.get(i)).equals(comparator, l2.get(i))) {
/* 139 */         return false;
/*     */       }
/*     */     } 
/* 142 */     return true;
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
/*     */   public void serialize(JsonGenerator f, SerializerProvider provider) throws IOException {
/* 154 */     List<JsonNode> c = this._children;
/* 155 */     int size = c.size();
/* 156 */     f.writeStartArray(size);
/* 157 */     for (int i = 0; i < size; i++) {
/*     */       
/* 159 */       JsonNode n = c.get(i);
/* 160 */       ((BaseJsonNode)n).serialize(f, provider);
/*     */     } 
/* 162 */     f.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 169 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(this, JsonToken.START_ARRAY));
/*     */     
/* 171 */     for (JsonNode n : this._children) {
/* 172 */       ((BaseJsonNode)n).serialize(g, provider);
/*     */     }
/* 174 */     typeSer.writeTypeSuffix(g, typeIdDef);
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
/*     */   public JsonNode findValue(String fieldName) {
/* 186 */     for (JsonNode node : this._children) {
/* 187 */       JsonNode value = node.findValue(fieldName);
/* 188 */       if (value != null) {
/* 189 */         return value;
/*     */       }
/*     */     } 
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
/* 198 */     for (JsonNode node : this._children) {
/* 199 */       foundSoFar = node.findValues(fieldName, foundSoFar);
/*     */     }
/* 201 */     return foundSoFar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
/* 207 */     for (JsonNode node : this._children) {
/* 208 */       foundSoFar = node.findValuesAsText(fieldName, foundSoFar);
/*     */     }
/* 210 */     return foundSoFar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode findParent(String fieldName) {
/* 216 */     for (JsonNode node : this._children) {
/* 217 */       JsonNode parent = node.findParent(fieldName);
/* 218 */       if (parent != null) {
/* 219 */         return (ObjectNode)parent;
/*     */       }
/*     */     } 
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
/* 228 */     for (JsonNode node : this._children) {
/* 229 */       foundSoFar = node.findParents(fieldName, foundSoFar);
/*     */     }
/* 231 */     return foundSoFar;
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
/*     */   public JsonNode set(int index, JsonNode value) {
/* 253 */     if (value == null) {
/* 254 */       value = nullNode();
/*     */     }
/* 256 */     if (index < 0 || index >= this._children.size()) {
/* 257 */       throw new IndexOutOfBoundsException("Illegal index " + index + ", array size " + size());
/*     */     }
/* 259 */     return this._children.set(index, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(JsonNode value) {
/* 269 */     if (value == null) {
/* 270 */       value = nullNode();
/*     */     }
/* 272 */     _add(value);
/* 273 */     return this;
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
/*     */   public ArrayNode addAll(ArrayNode other) {
/* 286 */     this._children.addAll(other._children);
/* 287 */     return this;
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
/*     */   public ArrayNode addAll(Collection<? extends JsonNode> nodes) {
/* 299 */     this._children.addAll(nodes);
/* 300 */     return this;
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
/*     */   public ArrayNode insert(int index, JsonNode value) {
/* 314 */     if (value == null) {
/* 315 */       value = nullNode();
/*     */     }
/* 317 */     _insert(index, value);
/* 318 */     return this;
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
/*     */   public JsonNode remove(int index) {
/* 330 */     if (index >= 0 && index < this._children.size()) {
/* 331 */       return this._children.remove(index);
/*     */     }
/* 333 */     return null;
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
/*     */   public ArrayNode removeAll() {
/* 345 */     this._children.clear();
/* 346 */     return this;
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
/*     */   public ArrayNode addArray() {
/* 363 */     ArrayNode n = arrayNode();
/* 364 */     _add(n);
/* 365 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode addObject() {
/* 376 */     ObjectNode n = objectNode();
/* 377 */     _add(n);
/* 378 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode addPOJO(Object value) {
/* 389 */     if (value == null) {
/* 390 */       addNull();
/*     */     } else {
/* 392 */       _add(pojoNode(value));
/*     */     } 
/* 394 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode addRawValue(RawValue raw) {
/* 403 */     if (raw == null) {
/* 404 */       addNull();
/*     */     } else {
/* 406 */       _add(rawValueNode(raw));
/*     */     } 
/* 408 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode addNull() {
/* 418 */     _add(nullNode());
/* 419 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(int v) {
/* 428 */     _add(numberNode(v));
/* 429 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(Integer value) {
/* 439 */     if (value == null) {
/* 440 */       return addNull();
/*     */     }
/* 442 */     return _add(numberNode(value.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(long v) {
/* 450 */     return _add(numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(Long value) {
/* 459 */     if (value == null) {
/* 460 */       return addNull();
/*     */     }
/* 462 */     return _add(numberNode(value.longValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(float v) {
/* 471 */     return _add(numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(Float value) {
/* 481 */     if (value == null) {
/* 482 */       return addNull();
/*     */     }
/* 484 */     return _add(numberNode(value.floatValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(double v) {
/* 493 */     return _add(numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(Double value) {
/* 503 */     if (value == null) {
/* 504 */       return addNull();
/*     */     }
/* 506 */     return _add(numberNode(value.doubleValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(BigDecimal v) {
/* 515 */     if (v == null) {
/* 516 */       return addNull();
/*     */     }
/* 518 */     return _add(numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(BigInteger v) {
/* 529 */     if (v == null) {
/* 530 */       return addNull();
/*     */     }
/* 532 */     return _add(numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(String v) {
/* 541 */     if (v == null) {
/* 542 */       return addNull();
/*     */     }
/* 544 */     return _add(textNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(boolean v) {
/* 553 */     return _add(booleanNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(Boolean value) {
/* 563 */     if (value == null) {
/* 564 */       return addNull();
/*     */     }
/* 566 */     return _add(booleanNode(value.booleanValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode add(byte[] v) {
/* 576 */     if (v == null) {
/* 577 */       return addNull();
/*     */     }
/* 579 */     return _add(binaryNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insertArray(int index) {
/* 590 */     ArrayNode n = arrayNode();
/* 591 */     _insert(index, n);
/* 592 */     return n;
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
/*     */   public ObjectNode insertObject(int index) {
/* 604 */     ObjectNode n = objectNode();
/* 605 */     _insert(index, n);
/* 606 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insertPOJO(int index, Object value) {
/* 617 */     if (value == null) {
/* 618 */       return insertNull(index);
/*     */     }
/* 620 */     return _insert(index, pojoNode(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insertNull(int index) {
/* 631 */     _insert(index, nullNode());
/* 632 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, int v) {
/* 642 */     _insert(index, numberNode(v));
/* 643 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, Integer value) {
/* 653 */     if (value == null) {
/* 654 */       insertNull(index);
/*     */     } else {
/* 656 */       _insert(index, numberNode(value.intValue()));
/*     */     } 
/* 658 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, long v) {
/* 668 */     return _insert(index, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, Long value) {
/* 678 */     if (value == null) {
/* 679 */       return insertNull(index);
/*     */     }
/* 681 */     return _insert(index, numberNode(value.longValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, float v) {
/* 691 */     return _insert(index, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, Float value) {
/* 701 */     if (value == null) {
/* 702 */       return insertNull(index);
/*     */     }
/* 704 */     return _insert(index, numberNode(value.floatValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, double v) {
/* 714 */     return _insert(index, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, Double value) {
/* 724 */     if (value == null) {
/* 725 */       return insertNull(index);
/*     */     }
/* 727 */     return _insert(index, numberNode(value.doubleValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, BigDecimal v) {
/* 737 */     if (v == null) {
/* 738 */       return insertNull(index);
/*     */     }
/* 740 */     return _insert(index, numberNode(v));
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
/*     */   public ArrayNode insert(int index, BigInteger v) {
/* 752 */     if (v == null) {
/* 753 */       return insertNull(index);
/*     */     }
/* 755 */     return _insert(index, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, String v) {
/* 765 */     if (v == null) {
/* 766 */       return insertNull(index);
/*     */     }
/* 768 */     return _insert(index, textNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, boolean v) {
/* 778 */     return _insert(index, booleanNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, Boolean value) {
/* 788 */     if (value == null) {
/* 789 */       return insertNull(index);
/*     */     }
/* 791 */     return _insert(index, booleanNode(value.booleanValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode insert(int index, byte[] v) {
/* 802 */     if (v == null) {
/* 803 */       return insertNull(index);
/*     */     }
/* 805 */     return _insert(index, binaryNode(v));
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
/*     */   public boolean equals(Object o) {
/* 817 */     if (o == this) return true; 
/* 818 */     if (o == null) return false; 
/* 819 */     if (o instanceof ArrayNode) {
/* 820 */       return this._children.equals(((ArrayNode)o)._children);
/*     */     }
/* 822 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _childrenEqual(ArrayNode other) {
/* 829 */     return this._children.equals(other._children);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 834 */     return this._children.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 840 */     StringBuilder sb = new StringBuilder(16 + (size() << 4));
/* 841 */     sb.append('[');
/* 842 */     for (int i = 0, len = this._children.size(); i < len; i++) {
/* 843 */       if (i > 0) {
/* 844 */         sb.append(',');
/*     */       }
/* 846 */       sb.append(((JsonNode)this._children.get(i)).toString());
/*     */     } 
/* 848 */     sb.append(']');
/* 849 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArrayNode _add(JsonNode node) {
/* 859 */     this._children.add(node);
/* 860 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ArrayNode _insert(int index, JsonNode node) {
/* 865 */     if (index < 0) {
/* 866 */       this._children.add(0, node);
/* 867 */     } else if (index >= this._children.size()) {
/* 868 */       this._children.add(node);
/*     */     } else {
/* 870 */       this._children.add(index, node);
/*     */     } 
/* 872 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\ArrayNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */