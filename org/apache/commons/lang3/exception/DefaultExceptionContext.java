/*     */ package org.apache.commons.lang3.exception;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExceptionContext
/*     */   implements ExceptionContext, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20110706L;
/*  46 */   private final List<Pair<String, Object>> contextValues = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultExceptionContext addContextValue(String label, Object value) {
/*  53 */     this.contextValues.add(new ImmutablePair(label, value));
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultExceptionContext setContextValue(String label, Object value) {
/*  62 */     this.contextValues.removeIf(p -> StringUtils.equals(label, (CharSequence)p.getKey()));
/*  63 */     addContextValue(label, value);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> getContextValues(String label) {
/*  72 */     List<Object> values = new ArrayList();
/*  73 */     for (Pair<String, Object> pair : this.contextValues) {
/*  74 */       if (StringUtils.equals(label, (CharSequence)pair.getKey())) {
/*  75 */         values.add(pair.getValue());
/*     */       }
/*     */     } 
/*  78 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFirstContextValue(String label) {
/*  86 */     for (Pair<String, Object> pair : this.contextValues) {
/*  87 */       if (StringUtils.equals(label, (CharSequence)pair.getKey())) {
/*  88 */         return pair.getValue();
/*     */       }
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getContextLabels() {
/*  99 */     Set<String> labels = new HashSet<>();
/* 100 */     for (Pair<String, Object> pair : this.contextValues) {
/* 101 */       labels.add(pair.getKey());
/*     */     }
/* 103 */     return labels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Pair<String, Object>> getContextEntries() {
/* 111 */     return this.contextValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedExceptionMessage(String baseMessage) {
/* 122 */     StringBuilder buffer = new StringBuilder(256);
/* 123 */     if (baseMessage != null) {
/* 124 */       buffer.append(baseMessage);
/*     */     }
/*     */     
/* 127 */     if (!this.contextValues.isEmpty()) {
/* 128 */       if (buffer.length() > 0) {
/* 129 */         buffer.append('\n');
/*     */       }
/* 131 */       buffer.append("Exception Context:\n");
/*     */       
/* 133 */       int i = 0;
/* 134 */       for (Pair<String, Object> pair : this.contextValues) {
/* 135 */         buffer.append("\t[");
/* 136 */         buffer.append(++i);
/* 137 */         buffer.append(':');
/* 138 */         buffer.append((String)pair.getKey());
/* 139 */         buffer.append("=");
/* 140 */         Object value = pair.getValue();
/* 141 */         if (value == null) {
/* 142 */           buffer.append("null");
/*     */         } else {
/*     */           String valueStr;
/*     */           try {
/* 146 */             valueStr = value.toString();
/* 147 */           } catch (Exception e) {
/* 148 */             valueStr = "Exception thrown on toString(): " + ExceptionUtils.getStackTrace(e);
/*     */           } 
/* 150 */           buffer.append(valueStr);
/*     */         } 
/* 152 */         buffer.append("]\n");
/*     */       } 
/* 154 */       buffer.append("---------------------------------");
/*     */     } 
/* 156 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\exception\DefaultExceptionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */