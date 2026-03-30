/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonPointer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RawValue;
/*     */ 
/*     */ public class ObjectNode extends ContainerNode<ObjectNode> {
/*     */   public ObjectNode(JsonNodeFactory nc) {
/*  26 */     super(nc);
/*  27 */     this._children = new LinkedHashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Map<String, JsonNode> _children;
/*     */   
/*     */   public ObjectNode(JsonNodeFactory nc, Map<String, JsonNode> kids) {
/*  34 */     super(nc);
/*  35 */     this._children = kids;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonNode _at(JsonPointer ptr) {
/*  40 */     return get(ptr.getMatchingProperty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode deepCopy() {
/*  51 */     ObjectNode ret = new ObjectNode(this._nodeFactory);
/*     */     
/*  53 */     for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
/*  54 */       ret._children.put(entry.getKey(), ((JsonNode)entry.getValue()).deepCopy());
/*     */     }
/*  56 */     return ret;
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
/*  67 */     return this._children.isEmpty();
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
/*  78 */     return JsonNodeType.OBJECT;
/*     */   }
/*     */   public JsonToken asToken() {
/*  81 */     return JsonToken.START_OBJECT;
/*     */   }
/*     */   
/*     */   public int size() {
/*  85 */     return this._children.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<JsonNode> elements() {
/*  90 */     return this._children.values().iterator();
/*     */   }
/*     */   
/*     */   public JsonNode get(int index) {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public JsonNode get(String fieldName) {
/*  98 */     return this._children.get(fieldName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> fieldNames() {
/* 103 */     return this._children.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonNode path(int index) {
/* 108 */     return MissingNode.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode path(String fieldName) {
/* 114 */     JsonNode n = this._children.get(fieldName);
/* 115 */     if (n != null) {
/* 116 */       return n;
/*     */     }
/* 118 */     return MissingNode.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, JsonNode>> fields() {
/* 127 */     return this._children.entrySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectNode with(String propertyName) {
/* 132 */     JsonNode n = this._children.get(propertyName);
/* 133 */     if (n != null) {
/* 134 */       if (n instanceof ObjectNode) {
/* 135 */         return (ObjectNode)n;
/*     */       }
/* 137 */       throw new UnsupportedOperationException("Property '" + propertyName + "' has value that is not of type ObjectNode (but " + n.getClass().getName() + ")");
/*     */     } 
/*     */ 
/*     */     
/* 141 */     ObjectNode result = objectNode();
/* 142 */     this._children.put(propertyName, result);
/* 143 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode withArray(String propertyName) {
/* 149 */     JsonNode n = this._children.get(propertyName);
/* 150 */     if (n != null) {
/* 151 */       if (n instanceof ArrayNode) {
/* 152 */         return (ArrayNode)n;
/*     */       }
/* 154 */       throw new UnsupportedOperationException("Property '" + propertyName + "' has value that is not of type ArrayNode (but " + n.getClass().getName() + ")");
/*     */     } 
/*     */ 
/*     */     
/* 158 */     ArrayNode result = arrayNode();
/* 159 */     this._children.put(propertyName, result);
/* 160 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Comparator<JsonNode> comparator, JsonNode o) {
/* 166 */     if (!(o instanceof ObjectNode)) {
/* 167 */       return false;
/*     */     }
/* 169 */     ObjectNode other = (ObjectNode)o;
/* 170 */     Map<String, JsonNode> m1 = this._children;
/* 171 */     Map<String, JsonNode> m2 = other._children;
/*     */     
/* 173 */     int len = m1.size();
/* 174 */     if (m2.size() != len) {
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     for (Map.Entry<String, JsonNode> entry : m1.entrySet()) {
/* 179 */       JsonNode v2 = m2.get(entry.getKey());
/* 180 */       if (v2 == null || !((JsonNode)entry.getValue()).equals(comparator, v2)) {
/* 181 */         return false;
/*     */       }
/*     */     } 
/* 184 */     return true;
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
/* 196 */     for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
/* 197 */       if (fieldName.equals(entry.getKey())) {
/* 198 */         return entry.getValue();
/*     */       }
/* 200 */       JsonNode value = ((JsonNode)entry.getValue()).findValue(fieldName);
/* 201 */       if (value != null) {
/* 202 */         return value;
/*     */       }
/*     */     } 
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
/* 211 */     for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
/* 212 */       if (fieldName.equals(entry.getKey())) {
/* 213 */         if (foundSoFar == null) {
/* 214 */           foundSoFar = new ArrayList<>();
/*     */         }
/* 216 */         foundSoFar.add(entry.getValue()); continue;
/*     */       } 
/* 218 */       foundSoFar = ((JsonNode)entry.getValue()).findValues(fieldName, foundSoFar);
/*     */     } 
/*     */     
/* 221 */     return foundSoFar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
/* 227 */     for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
/* 228 */       if (fieldName.equals(entry.getKey())) {
/* 229 */         if (foundSoFar == null) {
/* 230 */           foundSoFar = new ArrayList<>();
/*     */         }
/* 232 */         foundSoFar.add(((JsonNode)entry.getValue()).asText()); continue;
/*     */       } 
/* 234 */       foundSoFar = ((JsonNode)entry.getValue()).findValuesAsText(fieldName, foundSoFar);
/*     */     } 
/*     */ 
/*     */     
/* 238 */     return foundSoFar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode findParent(String fieldName) {
/* 244 */     for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
/* 245 */       if (fieldName.equals(entry.getKey())) {
/* 246 */         return this;
/*     */       }
/* 248 */       JsonNode value = ((JsonNode)entry.getValue()).findParent(fieldName);
/* 249 */       if (value != null) {
/* 250 */         return (ObjectNode)value;
/*     */       }
/*     */     } 
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
/* 259 */     for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
/* 260 */       if (fieldName.equals(entry.getKey())) {
/* 261 */         if (foundSoFar == null) {
/* 262 */           foundSoFar = new ArrayList<>();
/*     */         }
/* 264 */         foundSoFar.add(this); continue;
/*     */       } 
/* 266 */       foundSoFar = ((JsonNode)entry.getValue()).findParents(fieldName, foundSoFar);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return foundSoFar;
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
/*     */   public void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
/* 288 */     boolean trimEmptyArray = (provider != null && !provider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS));
/*     */     
/* 290 */     g.writeStartObject(this);
/* 291 */     for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       BaseJsonNode value = (BaseJsonNode)en.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 302 */       if (trimEmptyArray && value.isArray() && value.isEmpty(provider)) {
/*     */         continue;
/*     */       }
/* 305 */       g.writeFieldName(en.getKey());
/* 306 */       value.serialize(g, provider);
/*     */     } 
/* 308 */     g.writeEndObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 317 */     boolean trimEmptyArray = (provider != null && !provider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS));
/*     */ 
/*     */     
/* 320 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(this, JsonToken.START_OBJECT));
/*     */     
/* 322 */     for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
/* 323 */       BaseJsonNode value = (BaseJsonNode)en.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 328 */       if (trimEmptyArray && value.isArray() && value.isEmpty(provider)) {
/*     */         continue;
/*     */       }
/*     */       
/* 332 */       g.writeFieldName(en.getKey());
/* 333 */       value.serialize(g, provider);
/*     */     } 
/* 335 */     typeSer.writeTypeSuffix(g, typeIdDef);
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
/*     */   public JsonNode set(String fieldName, JsonNode value) {
/* 362 */     if (value == null) {
/* 363 */       value = nullNode();
/*     */     }
/* 365 */     this._children.put(fieldName, value);
/* 366 */     return this;
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
/*     */   public JsonNode setAll(Map<String, ? extends JsonNode> properties) {
/* 381 */     for (Map.Entry<String, ? extends JsonNode> en : properties.entrySet()) {
/* 382 */       JsonNode n = en.getValue();
/* 383 */       if (n == null) {
/* 384 */         n = nullNode();
/*     */       }
/* 386 */       this._children.put(en.getKey(), n);
/*     */     } 
/* 388 */     return this;
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
/*     */   public JsonNode setAll(ObjectNode other) {
/* 403 */     this._children.putAll(other._children);
/* 404 */     return this;
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
/*     */   public JsonNode replace(String fieldName, JsonNode value) {
/* 421 */     if (value == null) {
/* 422 */       value = nullNode();
/*     */     }
/* 424 */     return this._children.put(fieldName, value);
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
/*     */   public JsonNode without(String fieldName) {
/* 437 */     this._children.remove(fieldName);
/* 438 */     return this;
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
/*     */   public ObjectNode without(Collection<String> fieldNames) {
/* 453 */     this._children.keySet().removeAll(fieldNames);
/* 454 */     return this;
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
/*     */   @Deprecated
/*     */   public JsonNode put(String fieldName, JsonNode value) {
/* 478 */     if (value == null) {
/* 479 */       value = nullNode();
/*     */     }
/* 481 */     return this._children.put(fieldName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode remove(String fieldName) {
/* 492 */     return this._children.remove(fieldName);
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
/*     */   public ObjectNode remove(Collection<String> fieldNames) {
/* 505 */     this._children.keySet().removeAll(fieldNames);
/* 506 */     return this;
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
/*     */   public ObjectNode removeAll() {
/* 518 */     this._children.clear();
/* 519 */     return this;
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
/*     */   @Deprecated
/*     */   public JsonNode putAll(Map<String, ? extends JsonNode> properties) {
/* 534 */     return setAll(properties);
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
/*     */   @Deprecated
/*     */   public JsonNode putAll(ObjectNode other) {
/* 549 */     return setAll(other);
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
/*     */   public ObjectNode retain(Collection<String> fieldNames) {
/* 562 */     this._children.keySet().retainAll(fieldNames);
/* 563 */     return this;
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
/*     */   public ObjectNode retain(String... fieldNames) {
/* 575 */     return retain(Arrays.asList(fieldNames));
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
/*     */   public ArrayNode putArray(String fieldName) {
/* 597 */     ArrayNode n = arrayNode();
/* 598 */     _put(fieldName, n);
/* 599 */     return n;
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
/*     */   public ObjectNode putObject(String fieldName) {
/* 615 */     ObjectNode n = objectNode();
/* 616 */     _put(fieldName, n);
/* 617 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode putPOJO(String fieldName, Object pojo) {
/* 624 */     return _put(fieldName, pojoNode(pojo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode putRawValue(String fieldName, RawValue raw) {
/* 631 */     return _put(fieldName, rawValueNode(raw));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode putNull(String fieldName) {
/* 639 */     this._children.put(fieldName, nullNode());
/* 640 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, short v) {
/* 649 */     return _put(fieldName, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, Short v) {
/* 659 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v.shortValue()));
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
/*     */   public ObjectNode put(String fieldName, int v) {
/* 673 */     return _put(fieldName, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, Integer v) {
/* 683 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v.intValue()));
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
/*     */   public ObjectNode put(String fieldName, long v) {
/* 697 */     return _put(fieldName, numberNode(v));
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
/*     */   public ObjectNode put(String fieldName, Long v) {
/* 713 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v.longValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, float v) {
/* 723 */     return _put(fieldName, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, Float v) {
/* 733 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v.floatValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, double v) {
/* 743 */     return _put(fieldName, numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, Double v) {
/* 753 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v.doubleValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, BigDecimal v) {
/* 763 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v));
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
/*     */   public ObjectNode put(String fieldName, BigInteger v) {
/* 775 */     return _put(fieldName, (v == null) ? nullNode() : numberNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, String v) {
/* 785 */     return _put(fieldName, (v == null) ? nullNode() : textNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, boolean v) {
/* 795 */     return _put(fieldName, booleanNode(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, Boolean v) {
/* 805 */     return _put(fieldName, (v == null) ? nullNode() : booleanNode(v.booleanValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode put(String fieldName, byte[] v) {
/* 815 */     return _put(fieldName, (v == null) ? nullNode() : binaryNode(v));
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
/*     */   public boolean equals(Object o) {
/* 828 */     if (o == this) return true; 
/* 829 */     if (o == null) return false; 
/* 830 */     if (o instanceof ObjectNode) {
/* 831 */       return _childrenEqual((ObjectNode)o);
/*     */     }
/* 833 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _childrenEqual(ObjectNode other) {
/* 841 */     return this._children.equals(other._children);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 847 */     return this._children.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 853 */     StringBuilder sb = new StringBuilder(32 + (size() << 4));
/* 854 */     sb.append("{");
/* 855 */     int count = 0;
/* 856 */     for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
/* 857 */       if (count > 0) {
/* 858 */         sb.append(",");
/*     */       }
/* 860 */       count++;
/* 861 */       TextNode.appendQuoted(sb, en.getKey());
/* 862 */       sb.append(':');
/* 863 */       sb.append(((JsonNode)en.getValue()).toString());
/*     */     } 
/* 865 */     sb.append("}");
/* 866 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectNode _put(String fieldName, JsonNode value) {
/* 877 */     this._children.put(fieldName, value);
/* 878 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\ObjectNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */