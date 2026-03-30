/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ArrayNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.BooleanNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.NullNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.TextNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RawValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BaseNodeDeserializer<T extends JsonNode>
/*     */   extends StdDeserializer<T>
/*     */ {
/*     */   protected final Boolean _supportsUpdates;
/*     */   
/*     */   public BaseNodeDeserializer(Class<T> vc, Boolean supportsUpdates) {
/* 174 */     super(vc);
/* 175 */     this._supportsUpdates = supportsUpdates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 184 */     return typeDeserializer.deserializeTypedFromAny(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachable() {
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 196 */     return this._supportsUpdates;
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
/*     */   protected void _handleDuplicateField(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory, String fieldName, ObjectNode objectNode, JsonNode oldValue, JsonNode newValue) throws JsonProcessingException {
/* 225 */     if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)) {
/* 226 */       ctxt.reportInputMismatch(JsonNode.class, "Duplicate field '%s' for ObjectNode: not allowed when FAIL_ON_READING_DUP_TREE_KEY enabled", new Object[] { fieldName });
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
/*     */   protected final ObjectNode deserializeObject(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/* 245 */     ObjectNode node = nodeFactory.objectNode();
/* 246 */     String key = p.nextFieldName();
/* 247 */     for (; key != null; key = p.nextFieldName()) {
/*     */       ObjectNode objectNode; ArrayNode arrayNode; JsonNode jsonNode3; TextNode textNode; JsonNode jsonNode2; BooleanNode booleanNode; NullNode nullNode; JsonNode jsonNode1;
/* 249 */       JsonToken t = p.nextToken();
/* 250 */       if (t == null) {
/* 251 */         t = JsonToken.NOT_AVAILABLE;
/*     */       }
/* 253 */       switch (t.id()) {
/*     */         case 1:
/* 255 */           objectNode = deserializeObject(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 3:
/* 258 */           arrayNode = deserializeArray(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 12:
/* 261 */           jsonNode3 = _fromEmbedded(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 6:
/* 264 */           textNode = nodeFactory.textNode(p.getText());
/*     */           break;
/*     */         case 7:
/* 267 */           jsonNode2 = _fromInt(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 9:
/* 270 */           booleanNode = nodeFactory.booleanNode(true);
/*     */           break;
/*     */         case 10:
/* 273 */           booleanNode = nodeFactory.booleanNode(false);
/*     */           break;
/*     */         case 11:
/* 276 */           nullNode = nodeFactory.nullNode();
/*     */           break;
/*     */         default:
/* 279 */           jsonNode1 = deserializeAny(p, ctxt, nodeFactory); break;
/*     */       } 
/* 281 */       JsonNode old = node.replace(key, jsonNode1);
/* 282 */       if (old != null) {
/* 283 */         _handleDuplicateField(p, ctxt, nodeFactory, key, node, old, jsonNode1);
/*     */       }
/*     */     } 
/*     */     
/* 287 */     return node;
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
/*     */   protected final ObjectNode deserializeObjectAtName(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/* 299 */     ObjectNode node = nodeFactory.objectNode();
/* 300 */     String key = p.getCurrentName();
/* 301 */     for (; key != null; key = p.nextFieldName()) {
/*     */       ObjectNode objectNode; ArrayNode arrayNode; JsonNode jsonNode3; TextNode textNode; JsonNode jsonNode2; BooleanNode booleanNode; NullNode nullNode; JsonNode jsonNode1;
/* 303 */       JsonToken t = p.nextToken();
/* 304 */       if (t == null) {
/* 305 */         t = JsonToken.NOT_AVAILABLE;
/*     */       }
/* 307 */       switch (t.id()) {
/*     */         case 1:
/* 309 */           objectNode = deserializeObject(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 3:
/* 312 */           arrayNode = deserializeArray(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 12:
/* 315 */           jsonNode3 = _fromEmbedded(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 6:
/* 318 */           textNode = nodeFactory.textNode(p.getText());
/*     */           break;
/*     */         case 7:
/* 321 */           jsonNode2 = _fromInt(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 9:
/* 324 */           booleanNode = nodeFactory.booleanNode(true);
/*     */           break;
/*     */         case 10:
/* 327 */           booleanNode = nodeFactory.booleanNode(false);
/*     */           break;
/*     */         case 11:
/* 330 */           nullNode = nodeFactory.nullNode();
/*     */           break;
/*     */         default:
/* 333 */           jsonNode1 = deserializeAny(p, ctxt, nodeFactory); break;
/*     */       } 
/* 335 */       JsonNode old = node.replace(key, jsonNode1);
/* 336 */       if (old != null) {
/* 337 */         _handleDuplicateField(p, ctxt, nodeFactory, key, node, old, jsonNode1);
/*     */       }
/*     */     } 
/*     */     
/* 341 */     return node;
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
/*     */   protected final JsonNode updateObject(JsonParser p, DeserializationContext ctxt, ObjectNode node) throws IOException {
/* 354 */     if (p.isExpectedStartObjectToken()) {
/* 355 */       str = p.nextFieldName();
/*     */     } else {
/* 357 */       if (!p.hasToken(JsonToken.FIELD_NAME)) {
/* 358 */         return (JsonNode)deserialize(p, ctxt);
/*     */       }
/* 360 */       str = p.getCurrentName();
/*     */     }  String str;
/* 362 */     for (; str != null; str = p.nextFieldName()) {
/*     */       ObjectNode objectNode; ArrayNode arrayNode; JsonNode jsonNode3; TextNode textNode; JsonNode jsonNode2; BooleanNode booleanNode; NullNode nullNode; JsonNode jsonNode1;
/* 364 */       JsonToken t = p.nextToken();
/*     */ 
/*     */       
/* 367 */       JsonNode old = node.get(str);
/* 368 */       if (old != null) {
/* 369 */         if (old instanceof ObjectNode) {
/* 370 */           JsonNode newValue = updateObject(p, ctxt, (ObjectNode)old);
/* 371 */           if (newValue != old) {
/* 372 */             node.set(str, newValue);
/*     */           }
/*     */           continue;
/*     */         } 
/* 376 */         if (old instanceof ArrayNode) {
/* 377 */           JsonNode newValue = updateArray(p, ctxt, (ArrayNode)old);
/* 378 */           if (newValue != old) {
/* 379 */             node.set(str, newValue);
/*     */           }
/*     */           continue;
/*     */         } 
/*     */       } 
/* 384 */       if (t == null) {
/* 385 */         t = JsonToken.NOT_AVAILABLE;
/*     */       }
/*     */       
/* 388 */       JsonNodeFactory nodeFactory = ctxt.getNodeFactory();
/* 389 */       switch (t.id()) {
/*     */         case 1:
/* 391 */           objectNode = deserializeObject(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 3:
/* 394 */           arrayNode = deserializeArray(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 12:
/* 397 */           jsonNode3 = _fromEmbedded(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 6:
/* 400 */           textNode = nodeFactory.textNode(p.getText());
/*     */           break;
/*     */         case 7:
/* 403 */           jsonNode2 = _fromInt(p, ctxt, nodeFactory);
/*     */           break;
/*     */         case 9:
/* 406 */           booleanNode = nodeFactory.booleanNode(true);
/*     */           break;
/*     */         case 10:
/* 409 */           booleanNode = nodeFactory.booleanNode(false);
/*     */           break;
/*     */         case 11:
/* 412 */           nullNode = nodeFactory.nullNode();
/*     */           break;
/*     */         default:
/* 415 */           jsonNode1 = deserializeAny(p, ctxt, nodeFactory); break;
/*     */       } 
/* 417 */       if (old != null) {
/* 418 */         _handleDuplicateField(p, ctxt, nodeFactory, str, node, old, jsonNode1);
/*     */       }
/*     */       
/* 421 */       node.set(str, jsonNode1); continue;
/*     */     } 
/* 423 */     return (JsonNode)node;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ArrayNode deserializeArray(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/* 429 */     ArrayNode node = nodeFactory.arrayNode();
/*     */     while (true) {
/* 431 */       JsonToken t = p.nextToken();
/* 432 */       switch (t.id()) {
/*     */         case 1:
/* 434 */           node.add((JsonNode)deserializeObject(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 3:
/* 437 */           node.add((JsonNode)deserializeArray(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 4:
/* 440 */           return node;
/*     */         case 12:
/* 442 */           node.add(_fromEmbedded(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 6:
/* 445 */           node.add((JsonNode)nodeFactory.textNode(p.getText()));
/*     */           continue;
/*     */         case 7:
/* 448 */           node.add(_fromInt(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 9:
/* 451 */           node.add((JsonNode)nodeFactory.booleanNode(true));
/*     */           continue;
/*     */         case 10:
/* 454 */           node.add((JsonNode)nodeFactory.booleanNode(false));
/*     */           continue;
/*     */         case 11:
/* 457 */           node.add((JsonNode)nodeFactory.nullNode());
/*     */           continue;
/*     */       } 
/* 460 */       node.add(deserializeAny(p, ctxt, nodeFactory));
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
/*     */   protected final JsonNode updateArray(JsonParser p, DeserializationContext ctxt, ArrayNode node) throws IOException {
/* 475 */     JsonNodeFactory nodeFactory = ctxt.getNodeFactory();
/*     */     while (true) {
/* 477 */       JsonToken t = p.nextToken();
/* 478 */       switch (t.id()) {
/*     */         case 1:
/* 480 */           node.add((JsonNode)deserializeObject(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 3:
/* 483 */           node.add((JsonNode)deserializeArray(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 4:
/* 486 */           return (JsonNode)node;
/*     */         case 12:
/* 488 */           node.add(_fromEmbedded(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 6:
/* 491 */           node.add((JsonNode)nodeFactory.textNode(p.getText()));
/*     */           continue;
/*     */         case 7:
/* 494 */           node.add(_fromInt(p, ctxt, nodeFactory));
/*     */           continue;
/*     */         case 9:
/* 497 */           node.add((JsonNode)nodeFactory.booleanNode(true));
/*     */           continue;
/*     */         case 10:
/* 500 */           node.add((JsonNode)nodeFactory.booleanNode(false));
/*     */           continue;
/*     */         case 11:
/* 503 */           node.add((JsonNode)nodeFactory.nullNode());
/*     */           continue;
/*     */       } 
/* 506 */       node.add(deserializeAny(p, ctxt, nodeFactory));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonNode deserializeAny(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/* 515 */     switch (p.getCurrentTokenId()) {
/*     */       case 2:
/* 517 */         return (JsonNode)nodeFactory.objectNode();
/*     */       case 5:
/* 519 */         return (JsonNode)deserializeObjectAtName(p, ctxt, nodeFactory);
/*     */       case 12:
/* 521 */         return _fromEmbedded(p, ctxt, nodeFactory);
/*     */       case 6:
/* 523 */         return (JsonNode)nodeFactory.textNode(p.getText());
/*     */       case 7:
/* 525 */         return _fromInt(p, ctxt, nodeFactory);
/*     */       case 8:
/* 527 */         return _fromFloat(p, ctxt, nodeFactory);
/*     */       case 9:
/* 529 */         return (JsonNode)nodeFactory.booleanNode(true);
/*     */       case 10:
/* 531 */         return (JsonNode)nodeFactory.booleanNode(false);
/*     */       case 11:
/* 533 */         return (JsonNode)nodeFactory.nullNode();
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 550 */     return (JsonNode)ctxt.handleUnexpectedToken(handledType(), p);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonNode _fromInt(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/*     */     JsonParser.NumberType nt;
/* 557 */     int feats = ctxt.getDeserializationFeatures();
/* 558 */     if ((feats & F_MASK_INT_COERCIONS) != 0) {
/* 559 */       if (DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.enabledIn(feats)) {
/* 560 */         nt = JsonParser.NumberType.BIG_INTEGER;
/* 561 */       } else if (DeserializationFeature.USE_LONG_FOR_INTS.enabledIn(feats)) {
/* 562 */         nt = JsonParser.NumberType.LONG;
/*     */       } else {
/* 564 */         nt = p.getNumberType();
/*     */       } 
/*     */     } else {
/* 567 */       nt = p.getNumberType();
/*     */     } 
/* 569 */     if (nt == JsonParser.NumberType.INT) {
/* 570 */       return (JsonNode)nodeFactory.numberNode(p.getIntValue());
/*     */     }
/* 572 */     if (nt == JsonParser.NumberType.LONG) {
/* 573 */       return (JsonNode)nodeFactory.numberNode(p.getLongValue());
/*     */     }
/* 575 */     return (JsonNode)nodeFactory.numberNode(p.getBigIntegerValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonNode _fromFloat(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/* 581 */     JsonParser.NumberType nt = p.getNumberType();
/* 582 */     if (nt == JsonParser.NumberType.BIG_DECIMAL) {
/* 583 */       return (JsonNode)nodeFactory.numberNode(p.getDecimalValue());
/*     */     }
/* 585 */     if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/*     */ 
/*     */       
/* 588 */       if (p.isNaN()) {
/* 589 */         return (JsonNode)nodeFactory.numberNode(p.getDoubleValue());
/*     */       }
/* 591 */       return (JsonNode)nodeFactory.numberNode(p.getDecimalValue());
/*     */     } 
/* 593 */     if (nt == JsonParser.NumberType.FLOAT) {
/* 594 */       return (JsonNode)nodeFactory.numberNode(p.getFloatValue());
/*     */     }
/* 596 */     return (JsonNode)nodeFactory.numberNode(p.getDoubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonNode _fromEmbedded(JsonParser p, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
/* 602 */     Object ob = p.getEmbeddedObject();
/* 603 */     if (ob == null) {
/* 604 */       return (JsonNode)nodeFactory.nullNode();
/*     */     }
/* 606 */     Class<?> type = ob.getClass();
/* 607 */     if (type == byte[].class) {
/* 608 */       return (JsonNode)nodeFactory.binaryNode((byte[])ob);
/*     */     }
/*     */     
/* 611 */     if (ob instanceof RawValue) {
/* 612 */       return (JsonNode)nodeFactory.rawValueNode((RawValue)ob);
/*     */     }
/* 614 */     if (ob instanceof JsonNode)
/*     */     {
/* 616 */       return (JsonNode)ob;
/*     */     }
/*     */     
/* 619 */     return (JsonNode)nodeFactory.pojoNode(ob);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\BaseNodeDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */