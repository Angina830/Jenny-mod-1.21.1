/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import javax.xml.datatype.DatatypeConfigurationException;
/*     */ import javax.xml.datatype.DatatypeFactory;
/*     */ import javax.xml.datatype.Duration;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ import javax.xml.namespace.QName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.Deserializers;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
/*     */ 
/*     */ public class CoreXMLDeserializers
/*     */   extends Deserializers.Base {
/*     */   static final DatatypeFactory _dataTypeFactory;
/*     */   protected static final int TYPE_DURATION = 1;
/*     */   
/*     */   static {
/*     */     try {
/*  30 */       _dataTypeFactory = DatatypeFactory.newInstance();
/*  31 */     } catch (DatatypeConfigurationException e) {
/*  32 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static final int TYPE_G_CALENDAR = 2;
/*     */   protected static final int TYPE_QNAME = 3;
/*     */   
/*     */   public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) {
/*  40 */     Class<?> raw = type.getRawClass();
/*  41 */     if (raw == QName.class) {
/*  42 */       return (JsonDeserializer<?>)new Std(raw, 3);
/*     */     }
/*  44 */     if (raw == XMLGregorianCalendar.class) {
/*  45 */       return (JsonDeserializer<?>)new Std(raw, 2);
/*     */     }
/*  47 */     if (raw == Duration.class) {
/*  48 */       return (JsonDeserializer<?>)new Std(raw, 1);
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Std
/*     */     extends FromStringDeserializer<Object>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final int _kind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Std(Class<?> raw, int kind) {
/*  78 */       super(raw);
/*  79 */       this._kind = kind;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/*  88 */       if (this._kind == 2 && 
/*  89 */         p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
/*  90 */         return _gregorianFromDate(ctxt, _parseDate(p, ctxt));
/*     */       }
/*     */       
/*  93 */       return super.deserialize(p, ctxt);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object _deserialize(String value, DeserializationContext ctxt) throws IOException {
/*     */       Date d;
/* 100 */       switch (this._kind) {
/*     */         case 1:
/* 102 */           return CoreXMLDeserializers._dataTypeFactory.newDuration(value);
/*     */         case 3:
/* 104 */           return QName.valueOf(value);
/*     */         
/*     */         case 2:
/*     */           try {
/* 108 */             d = _parseDate(value, ctxt);
/*     */           }
/* 110 */           catch (JsonMappingException e) {
/*     */ 
/*     */             
/* 113 */             return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(value);
/*     */           } 
/* 115 */           return _gregorianFromDate(ctxt, d);
/*     */       } 
/* 117 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected XMLGregorianCalendar _gregorianFromDate(DeserializationContext ctxt, Date d) {
/* 123 */       if (d == null) {
/* 124 */         return null;
/*     */       }
/* 126 */       GregorianCalendar calendar = new GregorianCalendar();
/* 127 */       calendar.setTime(d);
/* 128 */       TimeZone tz = ctxt.getTimeZone();
/* 129 */       if (tz != null) {
/* 130 */         calendar.setTimeZone(tz);
/*     */       }
/* 132 */       return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(calendar);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\CoreXMLDeserializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */