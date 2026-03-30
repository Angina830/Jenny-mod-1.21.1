/*     */ package org.apache.commons.lang3.event;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventListenerSupport<L>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3593265990380473632L;
/*  79 */   private List<L> listeners = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient L proxy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient L[] prototypeArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> EventListenerSupport<T> create(Class<T> listenerInterface) {
/* 109 */     return new EventListenerSupport<>(listenerInterface);
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
/*     */   public EventListenerSupport(Class<L> listenerInterface) {
/* 125 */     this(listenerInterface, Thread.currentThread().getContextClassLoader());
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
/*     */   public EventListenerSupport(Class<L> listenerInterface, ClassLoader classLoader) {
/* 142 */     this();
/* 143 */     Validate.notNull(listenerInterface, "Listener interface cannot be null.", new Object[0]);
/* 144 */     Validate.notNull(classLoader, "ClassLoader cannot be null.", new Object[0]);
/* 145 */     Validate.isTrue(listenerInterface.isInterface(), "Class %s is not an interface", new Object[] { listenerInterface
/* 146 */           .getName() });
/* 147 */     initializeTransientFields(listenerInterface, classLoader);
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
/*     */   public L fire() {
/* 166 */     return this.proxy;
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
/*     */   public void addListener(L listener) {
/* 182 */     addListener(listener, true);
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
/*     */   public void addListener(L listener, boolean allowDuplicate) {
/* 197 */     Validate.notNull(listener, "Listener object cannot be null.", new Object[0]);
/* 198 */     if (allowDuplicate || !this.listeners.contains(listener)) {
/* 199 */       this.listeners.add(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getListenerCount() {
/* 209 */     return this.listeners.size();
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
/*     */   public void removeListener(L listener) {
/* 221 */     Validate.notNull(listener, "Listener object cannot be null.", new Object[0]);
/* 222 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L[] getListeners() {
/* 232 */     return this.listeners.toArray(this.prototypeArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
/* 241 */     ArrayList<L> serializableListeners = new ArrayList<>();
/*     */ 
/*     */     
/* 244 */     ObjectOutputStream testObjectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
/* 245 */     for (L listener : this.listeners) {
/*     */       try {
/* 247 */         testObjectOutputStream.writeObject(listener);
/* 248 */         serializableListeners.add(listener);
/* 249 */       } catch (IOException exception) {
/*     */         
/* 251 */         testObjectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     objectOutputStream.writeObject(serializableListeners.toArray(this.prototypeArray));
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
/*     */   private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
/* 270 */     L[] srcListeners = (L[])objectInputStream.readObject();
/*     */     
/* 272 */     this.listeners = new CopyOnWriteArrayList<>(srcListeners);
/*     */ 
/*     */ 
/*     */     
/* 276 */     Class<L> listenerInterface = (Class)srcListeners.getClass().getComponentType();
/*     */     
/* 278 */     initializeTransientFields(listenerInterface, Thread.currentThread().getContextClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeTransientFields(Class<L> listenerInterface, ClassLoader classLoader) {
/* 289 */     L[] array = (L[])Array.newInstance(listenerInterface, 0);
/* 290 */     this.prototypeArray = array;
/* 291 */     createProxy(listenerInterface, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createProxy(Class<L> listenerInterface, ClassLoader classLoader) {
/* 300 */     this.proxy = listenerInterface.cast(Proxy.newProxyInstance(classLoader, new Class[] { listenerInterface
/* 301 */           }, createInvocationHandler()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InvocationHandler createInvocationHandler() {
/* 310 */     return new ProxyInvocationHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EventListenerSupport() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class ProxyInvocationHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     public Object invoke(Object unusedProxy, Method method, Object[] args) throws Throwable {
/* 332 */       for (L listener : EventListenerSupport.this.listeners) {
/* 333 */         method.invoke(listener, args);
/*     */       }
/* 335 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\event\EventListenerSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */