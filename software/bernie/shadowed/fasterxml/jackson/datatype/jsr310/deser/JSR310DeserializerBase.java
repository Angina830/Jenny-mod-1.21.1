/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.util.Arrays;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class JSR310DeserializerBase<T>
/*     */   extends StdScalarDeserializer<T>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   protected JSR310DeserializerBase(Class<T> supportedType) {
/*  44 */     super(supportedType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser parser, DeserializationContext context, TypeDeserializer typeDeserializer) throws IOException {
/*  52 */     return typeDeserializer.deserializeTypedFromAny(parser, context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <BOGUS> BOGUS _reportWrongToken(DeserializationContext context, JsonToken exp, String unit) throws IOException {
/*  58 */     context.reportWrongTokenException((JsonDeserializer)this, exp, "Expected %s for '%s' of %s value", new Object[] { exp
/*     */           
/*  60 */           .name(), unit, handledType().getName() });
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <BOGUS> BOGUS _reportWrongToken(JsonParser parser, DeserializationContext context, JsonToken... expTypes) throws IOException {
/*  69 */     return (BOGUS)context.reportInputMismatch(handledType(), "Unexpected token (%s), expected one of %s for %s value", new Object[] { parser
/*     */           
/*  71 */           .getCurrentToken(), 
/*  72 */           Arrays.<JsonToken>asList(expTypes).toString(), 
/*  73 */           handledType().getName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <BOGUS> BOGUS _rethrowDateTimeException(JsonParser p, DeserializationContext context, DateTimeException e0, String value) throws JsonMappingException {
/*  80 */     if (e0 instanceof java.time.format.DateTimeParseException) {
/*  81 */       JsonMappingException e = context.weirdStringException(value, handledType(), e0.getMessage());
/*  82 */       e.initCause(e0);
/*  83 */       throw e;
/*     */     } 
/*  85 */     if (e0 instanceof DateTimeException) {
/*  86 */       String msg = e0.getMessage();
/*     */ 
/*     */       
/*  89 */       if (msg.contains("invalid format")) {
/*  90 */         JsonMappingException e = context.weirdStringException(value, handledType(), e0.getMessage());
/*  91 */         e.initCause(e0);
/*  92 */         throw e;
/*     */       } 
/*     */     } 
/*  95 */     return (BOGUS)context.reportInputMismatch(handledType(), "Failed to deserialize %s: (%s) %s", new Object[] {
/*     */           
/*  97 */           handledType().getName(), e0.getClass().getName(), e0.getMessage()
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DateTimeException _peelDTE(DateTimeException e) {
/*     */     while (true) {
/* 109 */       Throwable t = e.getCause();
/* 110 */       if (t != null && t instanceof DateTimeException) {
/* 111 */         e = (DateTimeException)t;
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 116 */     return e;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\JSR310DeserializerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */