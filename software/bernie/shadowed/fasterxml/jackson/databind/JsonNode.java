/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonPointer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.JsonNodeType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.MissingNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JsonNode
/*     */   extends JsonSerializable.Base
/*     */   implements TreeNode, Iterable<JsonNode>
/*     */ {
/*     */   public int size() {
/*  82 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isValueNode() {
/*  87 */     switch (getNodeType()) { case ARRAY: case OBJECT:
/*     */       case MISSING:
/*  89 */         return false; }
/*     */     
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isContainerNode() {
/*  97 */     JsonNodeType type = getNodeType();
/*  98 */     return (type == JsonNodeType.OBJECT || type == JsonNodeType.ARRAY);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isMissingNode() {
/* 103 */     return (getNodeType() == JsonNodeType.MISSING);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isArray() {
/* 108 */     return (getNodeType() == JsonNodeType.ARRAY);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isObject() {
/* 113 */     return (getNodeType() == JsonNodeType.OBJECT);
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
/*     */   public JsonNode get(String fieldName) {
/* 156 */     return null;
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
/*     */   public Iterator<String> fieldNames() {
/* 183 */     return ClassUtil.emptyIterator();
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
/*     */   public final JsonNode at(JsonPointer ptr) {
/* 200 */     if (ptr.matches()) {
/* 201 */       return this;
/*     */     }
/* 203 */     JsonNode n = _at(ptr);
/* 204 */     if (n == null) {
/* 205 */       return (JsonNode)MissingNode.getInstance();
/*     */     }
/* 207 */     return n.at(ptr.tail());
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
/*     */   public final JsonNode at(String jsonPtrExpr) {
/* 230 */     return at(JsonPointer.compile(jsonPtrExpr));
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
/*     */   public final boolean isPojo() {
/* 261 */     return (getNodeType() == JsonNodeType.POJO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isNumber() {
/* 268 */     return (getNodeType() == JsonNodeType.NUMBER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntegralNumber() {
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFloatingPointNumber() {
/* 282 */     return false;
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
/*     */   public boolean isShort() {
/* 294 */     return false;
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
/*     */   public boolean isInt() {
/* 306 */     return false;
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
/*     */   public boolean isLong() {
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFloat() {
/* 323 */     return false;
/*     */   }
/* 325 */   public boolean isDouble() { return false; }
/* 326 */   public boolean isBigDecimal() { return false; } public boolean isBigInteger() {
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isTextual() {
/* 334 */     return (getNodeType() == JsonNodeType.STRING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isBoolean() {
/* 342 */     return (getNodeType() == JsonNodeType.BOOLEAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isNull() {
/* 350 */     return (getNodeType() == JsonNodeType.NULL);
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
/*     */   public final boolean isBinary() {
/* 362 */     return (getNodeType() == JsonNodeType.BINARY);
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
/*     */   public boolean canConvertToInt() {
/* 379 */     return false;
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
/*     */   public boolean canConvertToLong() {
/* 395 */     return false;
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
/*     */   public String textValue() {
/* 413 */     return null;
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
/*     */   public byte[] binaryValue() throws IOException {
/* 426 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean booleanValue() {
/* 437 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number numberValue() {
/* 447 */     return null;
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
/*     */   public short shortValue() {
/* 459 */     return 0;
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
/*     */   public int intValue() {
/* 471 */     return 0;
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
/*     */   public long longValue() {
/* 483 */     return 0L;
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
/*     */   public float floatValue() {
/* 496 */     return 0.0F;
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
/*     */   public double doubleValue() {
/* 509 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigDecimal decimalValue() {
/* 518 */     return BigDecimal.ZERO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger bigIntegerValue() {
/* 527 */     return BigInteger.ZERO;
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
/*     */   public String asText(String defaultValue) {
/* 552 */     String str = asText();
/* 553 */     return (str == null) ? defaultValue : str;
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
/*     */   public int asInt() {
/* 567 */     return asInt(0);
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
/*     */   public int asInt(int defaultValue) {
/* 581 */     return defaultValue;
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
/*     */   public long asLong() {
/* 595 */     return asLong(0L);
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
/*     */   public long asLong(long defaultValue) {
/* 609 */     return defaultValue;
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
/*     */   public double asDouble() {
/* 623 */     return asDouble(0.0D);
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
/*     */   public double asDouble(double defaultValue) {
/* 637 */     return defaultValue;
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
/*     */   public boolean asBoolean() {
/* 651 */     return asBoolean(false);
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
/*     */   public boolean asBoolean(boolean defaultValue) {
/* 665 */     return defaultValue;
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
/*     */   public boolean has(String fieldName) {
/* 695 */     return (get(fieldName) != null);
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
/*     */   public boolean has(int index) {
/* 721 */     return (get(index) != null);
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
/*     */   public boolean hasNonNull(String fieldName) {
/* 736 */     JsonNode n = get(fieldName);
/* 737 */     return (n != null && !n.isNull());
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
/*     */   public boolean hasNonNull(int index) {
/* 752 */     JsonNode n = get(index);
/* 753 */     return (n != null && !n.isNull());
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
/*     */   public final Iterator<JsonNode> iterator() {
/* 768 */     return elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<JsonNode> elements() {
/* 777 */     return ClassUtil.emptyIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, JsonNode>> fields() {
/* 785 */     return ClassUtil.emptyIterator();
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
/*     */   public final List<JsonNode> findValues(String fieldName) {
/* 816 */     List<JsonNode> result = findValues(fieldName, null);
/* 817 */     if (result == null) {
/* 818 */       return Collections.emptyList();
/*     */     }
/* 820 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<String> findValuesAsText(String fieldName) {
/* 829 */     List<String> result = findValuesAsText(fieldName, null);
/* 830 */     if (result == null) {
/* 831 */       return Collections.emptyList();
/*     */     }
/* 833 */     return result;
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
/*     */   public final List<JsonNode> findParents(String fieldName) {
/* 872 */     List<JsonNode> result = findParents(fieldName, null);
/* 873 */     if (result == null) {
/* 874 */       return Collections.emptyList();
/*     */     }
/* 876 */     return result;
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
/*     */   public JsonNode with(String propertyName) {
/* 898 */     throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + getClass().getName() + "), cannot call with() on it");
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
/*     */   public JsonNode withArray(String propertyName) {
/* 911 */     throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + getClass().getName() + "), cannot call withArray() on it");
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
/*     */   public boolean equals(Comparator<JsonNode> comparator, JsonNode other) {
/* 940 */     return (comparator.compare(this, other) == 0);
/*     */   }
/*     */   
/*     */   public abstract <T extends JsonNode> T deepCopy();
/*     */   
/*     */   public abstract JsonNode get(int paramInt);
/*     */   
/*     */   public abstract JsonNode path(String paramString);
/*     */   
/*     */   public abstract JsonNode path(int paramInt);
/*     */   
/*     */   protected abstract JsonNode _at(JsonPointer paramJsonPointer);
/*     */   
/*     */   public abstract JsonNodeType getNodeType();
/*     */   
/*     */   public abstract String asText();
/*     */   
/*     */   public abstract JsonNode findValue(String paramString);
/*     */   
/*     */   public abstract JsonNode findPath(String paramString);
/*     */   
/*     */   public abstract JsonNode findParent(String paramString);
/*     */   
/*     */   public abstract List<JsonNode> findValues(String paramString, List<JsonNode> paramList);
/*     */   
/*     */   public abstract List<String> findValuesAsText(String paramString, List<String> paramList);
/*     */   
/*     */   public abstract List<JsonNode> findParents(String paramString, List<JsonNode> paramList);
/*     */   
/*     */   public abstract String toString();
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\JsonNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */