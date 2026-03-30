/*     */ package org.apache.commons.lang3.exception;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExceptionUtils
/*     */ {
/*     */   private static final int NOT_FOUND = -1;
/*  48 */   private static final String[] CAUSE_METHOD_NAMES = new String[] { "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String WRAPPED_MARKER = " [wrapped] ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static Throwable getCause(Throwable throwable) {
/* 101 */     return getCause(throwable, null);
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
/*     */   @Deprecated
/*     */   public static Throwable getCause(Throwable throwable, String[] methodNames) {
/* 119 */     if (throwable == null) {
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     if (methodNames == null) {
/* 124 */       Throwable cause = throwable.getCause();
/* 125 */       if (cause != null) {
/* 126 */         return cause;
/*     */       }
/*     */       
/* 129 */       methodNames = CAUSE_METHOD_NAMES;
/*     */     } 
/*     */     
/* 132 */     for (String methodName : methodNames) {
/* 133 */       if (methodName != null) {
/* 134 */         Throwable legacyCause = getCauseUsingMethodName(throwable, methodName);
/* 135 */         if (legacyCause != null) {
/* 136 */           return legacyCause;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return null;
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
/*     */   private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName) {
/* 153 */     Method method = null;
/*     */     try {
/* 155 */       method = throwable.getClass().getMethod(methodName, new Class[0]);
/* 156 */     } catch (NoSuchMethodException|SecurityException noSuchMethodException) {}
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
/*     */       try {
/* 162 */         return (Throwable)method.invoke(throwable, new Object[0]);
/* 163 */       } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */     }
/*     */ 
/*     */     
/* 167 */     return null;
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
/*     */   public static String[] getDefaultCauseMethodNames() {
/* 182 */     return (String[])ArrayUtils.clone((Object[])CAUSE_METHOD_NAMES);
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
/*     */   public static String getMessage(Throwable th) {
/* 197 */     if (th == null) {
/* 198 */       return "";
/*     */     }
/* 200 */     String clsName = ClassUtils.getShortClassName(th, null);
/* 201 */     String msg = th.getMessage();
/* 202 */     return clsName + ": " + StringUtils.defaultString(msg);
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
/*     */   public static Throwable getRootCause(Throwable throwable) {
/* 223 */     List<Throwable> list = getThrowableList(throwable);
/* 224 */     return list.isEmpty() ? null : list.get(list.size() - 1);
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
/*     */   public static String getRootCauseMessage(Throwable th) {
/* 239 */     Throwable root = getRootCause(th);
/* 240 */     root = (root == null) ? th : root;
/* 241 */     return getMessage(root);
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
/*     */   public static String[] getRootCauseStackTrace(Throwable throwable) {
/* 259 */     if (throwable == null) {
/* 260 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*     */     }
/* 262 */     Throwable[] throwables = getThrowables(throwable);
/* 263 */     int count = throwables.length;
/* 264 */     List<String> frames = new ArrayList<>();
/* 265 */     List<String> nextTrace = getStackFrameList(throwables[count - 1]);
/* 266 */     for (int i = count; --i >= 0; ) {
/* 267 */       List<String> trace = nextTrace;
/* 268 */       if (i != 0) {
/* 269 */         nextTrace = getStackFrameList(throwables[i - 1]);
/* 270 */         removeCommonFrames(trace, nextTrace);
/*     */       } 
/* 272 */       if (i == count - 1) {
/* 273 */         frames.add(throwables[i].toString());
/*     */       } else {
/* 275 */         frames.add(" [wrapped] " + throwables[i].toString());
/*     */       } 
/* 277 */       frames.addAll(trace);
/*     */     } 
/* 279 */     return frames.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*     */   static List<String> getStackFrameList(Throwable t) {
/* 295 */     String stackTrace = getStackTrace(t);
/* 296 */     String linebreak = System.lineSeparator();
/* 297 */     StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
/* 298 */     List<String> list = new ArrayList<>();
/* 299 */     boolean traceStarted = false;
/* 300 */     while (frames.hasMoreTokens()) {
/* 301 */       String token = frames.nextToken();
/*     */       
/* 303 */       int at = token.indexOf("at");
/* 304 */       if (at != -1 && token.substring(0, at).trim().isEmpty()) {
/* 305 */         traceStarted = true;
/* 306 */         list.add(token); continue;
/* 307 */       }  if (traceStarted) {
/*     */         break;
/*     */       }
/*     */     } 
/* 311 */     return list;
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
/*     */   static String[] getStackFrames(String stackTrace) {
/* 324 */     String linebreak = System.lineSeparator();
/* 325 */     StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
/* 326 */     List<String> list = new ArrayList<>();
/* 327 */     while (frames.hasMoreTokens()) {
/* 328 */       list.add(frames.nextToken());
/*     */     }
/* 330 */     return list.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*     */   public static String[] getStackFrames(Throwable throwable) {
/* 347 */     if (throwable == null) {
/* 348 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*     */     }
/* 350 */     return getStackFrames(getStackTrace(throwable));
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
/*     */   public static String getStackTrace(Throwable throwable) {
/* 367 */     StringWriter sw = new StringWriter();
/* 368 */     PrintWriter pw = new PrintWriter(sw, true);
/* 369 */     throwable.printStackTrace(pw);
/* 370 */     return sw.getBuffer().toString();
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
/*     */   public static int getThrowableCount(Throwable throwable) {
/* 391 */     return getThrowableList(throwable).size();
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
/*     */   public static List<Throwable> getThrowableList(Throwable throwable) {
/* 414 */     List<Throwable> list = new ArrayList<>();
/* 415 */     while (throwable != null && !list.contains(throwable)) {
/* 416 */       list.add(throwable);
/* 417 */       throwable = throwable.getCause();
/*     */     } 
/* 419 */     return list;
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
/*     */   public static Throwable[] getThrowables(Throwable throwable) {
/* 442 */     List<Throwable> list = getThrowableList(throwable);
/* 443 */     return list.<Throwable>toArray(ArrayUtils.EMPTY_THROWABLE_ARRAY);
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
/*     */   public static boolean hasCause(Throwable chain, Class<? extends Throwable> type) {
/* 461 */     if (chain instanceof UndeclaredThrowableException) {
/* 462 */       chain = chain.getCause();
/*     */     }
/* 464 */     return type.isInstance(chain);
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
/*     */   private static int indexOf(Throwable throwable, Class<? extends Throwable> type, int fromIndex, boolean subclass) {
/* 479 */     if (throwable == null || type == null) {
/* 480 */       return -1;
/*     */     }
/* 482 */     if (fromIndex < 0) {
/* 483 */       fromIndex = 0;
/*     */     }
/* 485 */     Throwable[] throwables = getThrowables(throwable);
/* 486 */     if (fromIndex >= throwables.length) {
/* 487 */       return -1;
/*     */     }
/* 489 */     if (subclass) {
/* 490 */       for (int i = fromIndex; i < throwables.length; i++) {
/* 491 */         if (type.isAssignableFrom(throwables[i].getClass())) {
/* 492 */           return i;
/*     */         }
/*     */       } 
/*     */     } else {
/* 496 */       for (int i = fromIndex; i < throwables.length; i++) {
/* 497 */         if (type.equals(throwables[i].getClass())) {
/* 498 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 502 */     return -1;
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
/*     */   public static int indexOfThrowable(Throwable throwable, Class<? extends Throwable> clazz) {
/* 520 */     return indexOf(throwable, clazz, 0, false);
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
/*     */   public static int indexOfThrowable(Throwable throwable, Class<? extends Throwable> clazz, int fromIndex) {
/* 543 */     return indexOf(throwable, clazz, fromIndex, false);
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
/*     */   public static int indexOfType(Throwable throwable, Class<? extends Throwable> type) {
/* 562 */     return indexOf(throwable, type, 0, true);
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
/*     */   public static int indexOfType(Throwable throwable, Class<? extends Throwable> type, int fromIndex) {
/* 586 */     return indexOf(throwable, type, fromIndex, true);
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
/*     */   public static void printRootCauseStackTrace(Throwable throwable) {
/* 609 */     printRootCauseStackTrace(throwable, System.err);
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
/*     */   public static void printRootCauseStackTrace(Throwable throwable, PrintStream printStream) {
/* 633 */     if (throwable == null) {
/*     */       return;
/*     */     }
/* 636 */     Objects.requireNonNull(printStream, "printStream");
/* 637 */     String[] trace = getRootCauseStackTrace(throwable);
/* 638 */     for (String element : trace) {
/* 639 */       printStream.println(element);
/*     */     }
/* 641 */     printStream.flush();
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
/*     */   public static void printRootCauseStackTrace(Throwable throwable, PrintWriter printWriter) {
/* 665 */     if (throwable == null) {
/*     */       return;
/*     */     }
/* 668 */     Objects.requireNonNull(printWriter, "printWriter");
/* 669 */     String[] trace = getRootCauseStackTrace(throwable);
/* 670 */     for (String element : trace) {
/* 671 */       printWriter.println(element);
/*     */     }
/* 673 */     printWriter.flush();
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
/*     */   public static void removeCommonFrames(List<String> causeFrames, List<String> wrapperFrames) {
/* 685 */     if (causeFrames == null || wrapperFrames == null) {
/* 686 */       throw new IllegalArgumentException("The List must not be null");
/*     */     }
/* 688 */     int causeFrameIndex = causeFrames.size() - 1;
/* 689 */     int wrapperFrameIndex = wrapperFrames.size() - 1;
/* 690 */     while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
/*     */ 
/*     */       
/* 693 */       String causeFrame = causeFrames.get(causeFrameIndex);
/* 694 */       String wrapperFrame = wrapperFrames.get(wrapperFrameIndex);
/* 695 */       if (causeFrame.equals(wrapperFrame)) {
/* 696 */         causeFrames.remove(causeFrameIndex);
/*     */       }
/* 698 */       causeFrameIndex--;
/* 699 */       wrapperFrameIndex--;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <R> R rethrow(Throwable throwable) {
/* 760 */     return typeErasure(throwable);
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
/*     */   private static <T extends Throwable> T throwableOf(Throwable throwable, Class<T> type, int fromIndex, boolean subclass) {
/* 776 */     if (throwable == null || type == null) {
/* 777 */       return null;
/*     */     }
/* 779 */     if (fromIndex < 0) {
/* 780 */       fromIndex = 0;
/*     */     }
/* 782 */     Throwable[] throwables = getThrowables(throwable);
/* 783 */     if (fromIndex >= throwables.length) {
/* 784 */       return null;
/*     */     }
/* 786 */     if (subclass) {
/* 787 */       for (int i = fromIndex; i < throwables.length; i++) {
/* 788 */         if (type.isAssignableFrom(throwables[i].getClass())) {
/* 789 */           return type.cast(throwables[i]);
/*     */         }
/*     */       } 
/*     */     } else {
/* 793 */       for (int i = fromIndex; i < throwables.length; i++) {
/* 794 */         if (type.equals(throwables[i].getClass())) {
/* 795 */           return type.cast(throwables[i]);
/*     */         }
/*     */       } 
/*     */     } 
/* 799 */     return null;
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
/*     */   public static <T extends Throwable> T throwableOfThrowable(Throwable throwable, Class<T> clazz) {
/* 819 */     return throwableOf(throwable, clazz, 0, false);
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
/*     */   public static <T extends Throwable> T throwableOfThrowable(Throwable throwable, Class<T> clazz, int fromIndex) {
/* 844 */     return throwableOf(throwable, clazz, fromIndex, false);
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
/*     */   public static <T extends Throwable> T throwableOfType(Throwable throwable, Class<T> type) {
/* 864 */     return throwableOf(throwable, type, 0, true);
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
/*     */   public static <T extends Throwable> T throwableOfType(Throwable throwable, Class<T> type, int fromIndex) {
/* 889 */     return throwableOf(throwable, type, fromIndex, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <R, T extends Throwable> R typeErasure(Throwable throwable) throws T {
/* 900 */     throw (T)throwable;
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
/*     */   public static <R> R wrapAndThrow(Throwable throwable) {
/* 925 */     if (throwable instanceof RuntimeException) {
/* 926 */       throw (RuntimeException)throwable;
/*     */     }
/* 928 */     if (throwable instanceof Error) {
/* 929 */       throw (Error)throwable;
/*     */     }
/* 931 */     throw new UndeclaredThrowableException(throwable);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\exception\ExceptionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */