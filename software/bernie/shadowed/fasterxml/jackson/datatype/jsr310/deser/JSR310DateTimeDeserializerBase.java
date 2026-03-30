/*    */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.util.Locale;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ 
/*    */ public abstract class JSR310DateTimeDeserializerBase<T>
/*    */   extends JSR310DeserializerBase<T>
/*    */   implements ContextualDeserializer
/*    */ {
/*    */   protected final DateTimeFormatter _formatter;
/*    */   
/*    */   protected JSR310DateTimeDeserializerBase(Class<T> supportedType, DateTimeFormatter f) {
/* 22 */     super(supportedType);
/* 23 */     this._formatter = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 32 */     JsonFormat.Value format = findFormatOverrides(ctxt, property, handledType());
/* 33 */     if (format != null && 
/* 34 */       format.hasPattern()) {
/* 35 */       DateTimeFormatter df; String pattern = format.getPattern();
/* 36 */       Locale locale = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
/*    */       
/* 38 */       if (locale == null) {
/* 39 */         df = DateTimeFormatter.ofPattern(pattern);
/*    */       } else {
/* 41 */         df = DateTimeFormatter.ofPattern(pattern, locale);
/*    */       } 
/*    */ 
/*    */       
/* 45 */       if (format.hasTimeZone()) {
/* 46 */         df = df.withZone(format.getTimeZone().toZoneId());
/*    */       }
/* 48 */       return withDateFormat(df);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return (JsonDeserializer<?>)this;
/*    */   }
/*    */   
/*    */   protected abstract JsonDeserializer<T> withDateFormat(DateTimeFormatter paramDateTimeFormatter);
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\JSR310DateTimeDeserializerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */