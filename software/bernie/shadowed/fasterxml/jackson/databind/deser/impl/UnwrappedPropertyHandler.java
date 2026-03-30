/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnwrappedPropertyHandler
/*    */ {
/*    */   protected final List<SettableBeanProperty> _properties;
/*    */   
/*    */   public UnwrappedPropertyHandler() {
/* 23 */     this._properties = new ArrayList<>();
/*    */   }
/*    */   protected UnwrappedPropertyHandler(List<SettableBeanProperty> props) {
/* 26 */     this._properties = props;
/*    */   }
/*    */   
/*    */   public void addProperty(SettableBeanProperty property) {
/* 30 */     this._properties.add(property);
/*    */   }
/*    */ 
/*    */   
/*    */   public UnwrappedPropertyHandler renameAll(NameTransformer transformer) {
/* 35 */     ArrayList<SettableBeanProperty> newProps = new ArrayList<>(this._properties.size());
/* 36 */     for (SettableBeanProperty prop : this._properties) {
/* 37 */       String newName = transformer.transform(prop.getName());
/* 38 */       prop = prop.withSimpleName(newName);
/* 39 */       JsonDeserializer<?> deser = prop.getValueDeserializer();
/* 40 */       if (deser != null) {
/*    */         
/* 42 */         JsonDeserializer<Object> newDeser = deser.unwrappingDeserializer(transformer);
/*    */         
/* 44 */         if (newDeser != deser) {
/* 45 */           prop = prop.withValueDeserializer(newDeser);
/*    */         }
/*    */       } 
/* 48 */       newProps.add(prop);
/*    */     } 
/* 50 */     return new UnwrappedPropertyHandler(newProps);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object processUnwrapped(JsonParser originalParser, DeserializationContext ctxt, Object bean, TokenBuffer buffered) throws IOException {
/* 58 */     for (int i = 0, len = this._properties.size(); i < len; i++) {
/* 59 */       SettableBeanProperty prop = this._properties.get(i);
/* 60 */       JsonParser p = buffered.asParser();
/* 61 */       p.nextToken();
/* 62 */       prop.deserializeAndSet(p, ctxt, bean);
/*    */     } 
/* 64 */     return bean;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\UnwrappedPropertyHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */