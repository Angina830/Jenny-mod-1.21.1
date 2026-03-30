/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.Deserializers;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;
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
/*     */ public class OptionalHandlerFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
/*     */   private static final String SERIALIZERS_FOR_JAVAX_XML = "software.bernie.shadowed.fasterxml.jackson.databind.ext.CoreXMLSerializers";
/*     */   private static final String DESERIALIZERS_FOR_JAVAX_XML = "software.bernie.shadowed.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
/*     */   private static final String SERIALIZER_FOR_DOM_NODE = "software.bernie.shadowed.fasterxml.jackson.databind.ext.DOMSerializer";
/*     */   private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "software.bernie.shadowed.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
/*     */   private static final String DESERIALIZER_FOR_DOM_NODE = "software.bernie.shadowed.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
/*     */   private static final Class<?> CLASS_DOM_NODE;
/*     */   private static final Class<?> CLASS_DOM_DOCUMENT;
/*     */   private static final Java7Support _jdk7Helper;
/*     */   
/*     */   static {
/*  50 */     Class<?> doc = null, node = null;
/*     */     try {
/*  52 */       node = Node.class;
/*  53 */       doc = Document.class;
/*  54 */     } catch (Exception e) {
/*     */       
/*  56 */       Logger.getLogger(OptionalHandlerFactory.class.getName()).log(Level.INFO, "Could not load DOM `Node` and/or `Document` classes: no DOM support");
/*     */     } 
/*     */     
/*  59 */     CLASS_DOM_NODE = node;
/*  60 */     CLASS_DOM_DOCUMENT = doc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     Java7Support x = null;
/*     */     try {
/*  71 */       x = Java7Support.instance();
/*  72 */     } catch (Throwable t) {}
/*  73 */     _jdk7Helper = x;
/*     */   }
/*     */   
/*  76 */   public static final OptionalHandlerFactory instance = new OptionalHandlerFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
/*     */     String factoryName;
/*  89 */     Class<?> rawType = type.getRawClass();
/*     */     
/*  91 */     if (_jdk7Helper != null) {
/*  92 */       JsonSerializer<?> ser = _jdk7Helper.getSerializerForJavaNioFilePath(rawType);
/*  93 */       if (ser != null) {
/*  94 */         return ser;
/*     */       }
/*     */     } 
/*  97 */     if (CLASS_DOM_NODE != null && CLASS_DOM_NODE.isAssignableFrom(rawType)) {
/*  98 */       return (JsonSerializer)instantiate("software.bernie.shadowed.fasterxml.jackson.databind.ext.DOMSerializer");
/*     */     }
/* 100 */     String className = rawType.getName();
/*     */     
/* 102 */     if (className.startsWith("javax.xml.") || hasSuperClassStartingWith(rawType, "javax.xml.")) {
/* 103 */       factoryName = "software.bernie.shadowed.fasterxml.jackson.databind.ext.CoreXMLSerializers";
/*     */     } else {
/* 105 */       return null;
/*     */     } 
/*     */     
/* 108 */     Object ob = instantiate(factoryName);
/* 109 */     if (ob == null) {
/* 110 */       return null;
/*     */     }
/* 112 */     return ((Serializers)ob).findSerializer(config, type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> findDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
/*     */     String factoryName;
/* 119 */     Class<?> rawType = type.getRawClass();
/*     */     
/* 121 */     if (_jdk7Helper != null) {
/* 122 */       JsonDeserializer<?> deser = _jdk7Helper.getDeserializerForJavaNioFilePath(rawType);
/* 123 */       if (deser != null) {
/* 124 */         return deser;
/*     */       }
/*     */     } 
/* 127 */     if (CLASS_DOM_NODE != null && CLASS_DOM_NODE.isAssignableFrom(rawType)) {
/* 128 */       return (JsonDeserializer)instantiate("software.bernie.shadowed.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer");
/*     */     }
/* 130 */     if (CLASS_DOM_DOCUMENT != null && CLASS_DOM_DOCUMENT.isAssignableFrom(rawType)) {
/* 131 */       return (JsonDeserializer)instantiate("software.bernie.shadowed.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer");
/*     */     }
/* 133 */     String className = rawType.getName();
/*     */     
/* 135 */     if (className.startsWith("javax.xml.") || hasSuperClassStartingWith(rawType, "javax.xml.")) {
/*     */       
/* 137 */       factoryName = "software.bernie.shadowed.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
/*     */     } else {
/* 139 */       return null;
/*     */     } 
/* 141 */     Object ob = instantiate(factoryName);
/* 142 */     if (ob == null) {
/* 143 */       return null;
/*     */     }
/* 145 */     return ((Deserializers)ob).findBeanDeserializer(type, config, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object instantiate(String className) {
/*     */     try {
/* 157 */       return ClassUtil.createInstance(Class.forName(className), false);
/* 158 */     } catch (LinkageError e) {
/*     */     
/* 160 */     } catch (Exception e) {}
/* 161 */     return null;
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
/*     */   private boolean hasSuperClassStartingWith(Class<?> rawType, String prefix) {
/* 174 */     for (Class<?> supertype = rawType.getSuperclass(); supertype != null; supertype = supertype.getSuperclass()) {
/* 175 */       if (supertype == Object.class) {
/* 176 */         return false;
/*     */       }
/* 178 */       if (supertype.getName().startsWith(prefix)) {
/* 179 */         return true;
/*     */       }
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\OptionalHandlerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */