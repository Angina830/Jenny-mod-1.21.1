/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerializationUtils
/*     */ {
/*     */   public static <T extends Serializable> T clone(T object) {
/*  78 */     if (object == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     byte[] objectData = serialize((Serializable)object);
/*  82 */     ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
/*     */     
/*  84 */     try (ClassLoaderAwareObjectInputStream in = new ClassLoaderAwareObjectInputStream(bais, object
/*  85 */           .getClass().getClassLoader())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       Serializable serializable = (Serializable)in.readObject();
/*  93 */       return (T)serializable;
/*     */     }
/*  95 */     catch (ClassNotFoundException ex) {
/*  96 */       throw new SerializationException("ClassNotFoundException while reading cloned object data", ex);
/*  97 */     } catch (IOException ex) {
/*  98 */       throw new SerializationException("IOException while reading or closing cloned object data", ex);
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
/*     */   public static <T extends Serializable> T roundtrip(T msg) {
/* 115 */     return (T)deserialize(serialize((Serializable)msg));
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
/*     */   public static void serialize(Serializable obj, OutputStream outputStream) {
/* 136 */     Validate.notNull(outputStream, "The OutputStream must not be null", new Object[0]);
/* 137 */     try (ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
/* 138 */       out.writeObject(obj);
/* 139 */     } catch (IOException ex) {
/* 140 */       throw new SerializationException(ex);
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
/*     */   public static byte[] serialize(Serializable obj) {
/* 153 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
/* 154 */     serialize(obj, baos);
/* 155 */     return baos.toByteArray();
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
/*     */   public static <T> T deserialize(InputStream inputStream) {
/* 189 */     Validate.notNull(inputStream, "The InputStream must not be null", new Object[0]);
/* 190 */     try (ObjectInputStream in = new ObjectInputStream(inputStream)) {
/*     */       
/* 192 */       T obj = (T)in.readObject();
/* 193 */       return obj;
/* 194 */     } catch (ClassNotFoundException|IOException ex) {
/* 195 */       throw new SerializationException(ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T deserialize(byte[] objectData) {
/* 218 */     Validate.notNull(objectData, "The byte[] must not be null", new Object[0]);
/* 219 */     return deserialize(new ByteArrayInputStream(objectData));
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
/*     */   static class ClassLoaderAwareObjectInputStream
/*     */     extends ObjectInputStream
/*     */   {
/* 236 */     private static final Map<String, Class<?>> primitiveTypes = new HashMap<>();
/*     */     private final ClassLoader classLoader;
/*     */     
/*     */     static {
/* 240 */       primitiveTypes.put("byte", byte.class);
/* 241 */       primitiveTypes.put("short", short.class);
/* 242 */       primitiveTypes.put("int", int.class);
/* 243 */       primitiveTypes.put("long", long.class);
/* 244 */       primitiveTypes.put("float", float.class);
/* 245 */       primitiveTypes.put("double", double.class);
/* 246 */       primitiveTypes.put("boolean", boolean.class);
/* 247 */       primitiveTypes.put("char", char.class);
/* 248 */       primitiveTypes.put("void", void.class);
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
/*     */     ClassLoaderAwareObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
/* 261 */       super(in);
/* 262 */       this.classLoader = classLoader;
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
/*     */     protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/* 275 */       String name = desc.getName();
/*     */       try {
/* 277 */         return Class.forName(name, false, this.classLoader);
/* 278 */       } catch (ClassNotFoundException ex) {
/*     */         try {
/* 280 */           return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
/* 281 */         } catch (ClassNotFoundException cnfe) {
/* 282 */           Class<?> cls = primitiveTypes.get(name);
/* 283 */           if (cls != null) {
/* 284 */             return cls;
/*     */           }
/* 286 */           throw cnfe;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\SerializationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */