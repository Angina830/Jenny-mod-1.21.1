/*      */ package org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Deprecated
/*      */ public class StrSubstitutor
/*      */ {
/*      */   public static final char DEFAULT_ESCAPE = '$';
/*  138 */   public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
/*      */ 
/*      */ 
/*      */   
/*  142 */   public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char escapeChar;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StrMatcher prefixMatcher;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StrMatcher suffixMatcher;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StrMatcher valueDelimiterMatcher;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StrLookup<?> variableResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableSubstitutionInVariables;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean preserveEscapes = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> String replace(Object source, Map<String, V> valueMap) {
/*  189 */     return (new StrSubstitutor(valueMap)).replace(source);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> String replace(Object source, Map<String, V> valueMap, String prefix, String suffix) {
/*  206 */     return (new StrSubstitutor(valueMap, prefix, suffix)).replace(source);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replace(Object source, Properties valueProperties) {
/*  218 */     if (valueProperties == null) {
/*  219 */       return source.toString();
/*      */     }
/*  221 */     Map<String, String> valueMap = new HashMap<>();
/*  222 */     Enumeration<?> propNames = valueProperties.propertyNames();
/*  223 */     while (propNames.hasMoreElements()) {
/*  224 */       String propName = (String)propNames.nextElement();
/*  225 */       String propValue = valueProperties.getProperty(propName);
/*  226 */       valueMap.put(propName, propValue);
/*      */     } 
/*  228 */     return replace(source, valueMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String replaceSystemProperties(Object source) {
/*  239 */     return (new StrSubstitutor(StrLookup.systemPropertiesLookup())).replace(source);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor() {
/*  248 */     this((StrLookup<?>)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <V> StrSubstitutor(Map<String, V> valueMap) {
/*  259 */     this(StrLookup.mapLookup(valueMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <V> StrSubstitutor(Map<String, V> valueMap, String prefix, String suffix) {
/*  272 */     this(StrLookup.mapLookup(valueMap), prefix, suffix, '$');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <V> StrSubstitutor(Map<String, V> valueMap, String prefix, String suffix, char escape) {
/*  287 */     this(StrLookup.mapLookup(valueMap), prefix, suffix, escape);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <V> StrSubstitutor(Map<String, V> valueMap, String prefix, String suffix, char escape, String valueDelimiter) {
/*  304 */     this(StrLookup.mapLookup(valueMap), prefix, suffix, escape, valueDelimiter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(StrLookup<?> variableResolver) {
/*  313 */     this(variableResolver, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(StrLookup<?> variableResolver, String prefix, String suffix, char escape) {
/*  327 */     setVariableResolver(variableResolver);
/*  328 */     setVariablePrefix(prefix);
/*  329 */     setVariableSuffix(suffix);
/*  330 */     setEscapeChar(escape);
/*  331 */     setValueDelimiterMatcher(DEFAULT_VALUE_DELIMITER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(StrLookup<?> variableResolver, String prefix, String suffix, char escape, String valueDelimiter) {
/*  347 */     setVariableResolver(variableResolver);
/*  348 */     setVariablePrefix(prefix);
/*  349 */     setVariableSuffix(suffix);
/*  350 */     setEscapeChar(escape);
/*  351 */     setValueDelimiter(valueDelimiter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(StrLookup<?> variableResolver, StrMatcher prefixMatcher, StrMatcher suffixMatcher, char escape) {
/*  366 */     this(variableResolver, prefixMatcher, suffixMatcher, escape, DEFAULT_VALUE_DELIMITER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(StrLookup<?> variableResolver, StrMatcher prefixMatcher, StrMatcher suffixMatcher, char escape, StrMatcher valueDelimiterMatcher) {
/*  383 */     setVariableResolver(variableResolver);
/*  384 */     setVariablePrefixMatcher(prefixMatcher);
/*  385 */     setVariableSuffixMatcher(suffixMatcher);
/*  386 */     setEscapeChar(escape);
/*  387 */     setValueDelimiterMatcher(valueDelimiterMatcher);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(String source) {
/*  399 */     if (source == null) {
/*  400 */       return null;
/*      */     }
/*  402 */     StrBuilder buf = new StrBuilder(source);
/*  403 */     if (!substitute(buf, 0, source.length())) {
/*  404 */       return source;
/*      */     }
/*  406 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(String source, int offset, int length) {
/*  422 */     if (source == null) {
/*  423 */       return null;
/*      */     }
/*  425 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  426 */     if (!substitute(buf, 0, length)) {
/*  427 */       return source.substring(offset, offset + length);
/*      */     }
/*  429 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(char[] source) {
/*  442 */     if (source == null) {
/*  443 */       return null;
/*      */     }
/*  445 */     StrBuilder buf = (new StrBuilder(source.length)).append(source);
/*  446 */     substitute(buf, 0, source.length);
/*  447 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(char[] source, int offset, int length) {
/*  464 */     if (source == null) {
/*  465 */       return null;
/*      */     }
/*  467 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  468 */     substitute(buf, 0, length);
/*  469 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(StringBuffer source) {
/*  482 */     if (source == null) {
/*  483 */       return null;
/*      */     }
/*  485 */     StrBuilder buf = (new StrBuilder(source.length())).append(source);
/*  486 */     substitute(buf, 0, buf.length());
/*  487 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(StringBuffer source, int offset, int length) {
/*  504 */     if (source == null) {
/*  505 */       return null;
/*      */     }
/*  507 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  508 */     substitute(buf, 0, length);
/*  509 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(CharSequence source) {
/*  522 */     if (source == null) {
/*  523 */       return null;
/*      */     }
/*  525 */     return replace(source, 0, source.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(CharSequence source, int offset, int length) {
/*  543 */     if (source == null) {
/*  544 */       return null;
/*      */     }
/*  546 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  547 */     substitute(buf, 0, length);
/*  548 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(StrBuilder source) {
/*  561 */     if (source == null) {
/*  562 */       return null;
/*      */     }
/*  564 */     StrBuilder buf = (new StrBuilder(source.length())).append(source);
/*  565 */     substitute(buf, 0, buf.length());
/*  566 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(StrBuilder source, int offset, int length) {
/*  583 */     if (source == null) {
/*  584 */       return null;
/*      */     }
/*  586 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  587 */     substitute(buf, 0, length);
/*  588 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(Object source) {
/*  601 */     if (source == null) {
/*  602 */       return null;
/*      */     }
/*  604 */     StrBuilder buf = (new StrBuilder()).append(source);
/*  605 */     substitute(buf, 0, buf.length());
/*  606 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceIn(StringBuffer source) {
/*  619 */     if (source == null) {
/*  620 */       return false;
/*      */     }
/*  622 */     return replaceIn(source, 0, source.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceIn(StringBuffer source, int offset, int length) {
/*  639 */     if (source == null) {
/*  640 */       return false;
/*      */     }
/*  642 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  643 */     if (!substitute(buf, 0, length)) {
/*  644 */       return false;
/*      */     }
/*  646 */     source.replace(offset, offset + length, buf.toString());
/*  647 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceIn(StringBuilder source) {
/*  661 */     if (source == null) {
/*  662 */       return false;
/*      */     }
/*  664 */     return replaceIn(source, 0, source.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceIn(StringBuilder source, int offset, int length) {
/*  682 */     if (source == null) {
/*  683 */       return false;
/*      */     }
/*  685 */     StrBuilder buf = (new StrBuilder(length)).append(source, offset, length);
/*  686 */     if (!substitute(buf, 0, length)) {
/*  687 */       return false;
/*      */     }
/*  689 */     source.replace(offset, offset + length, buf.toString());
/*  690 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceIn(StrBuilder source) {
/*  702 */     if (source == null) {
/*  703 */       return false;
/*      */     }
/*  705 */     return substitute(source, 0, source.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceIn(StrBuilder source, int offset, int length) {
/*  721 */     if (source == null) {
/*  722 */       return false;
/*      */     }
/*  724 */     return substitute(source, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean substitute(StrBuilder buf, int offset, int length) {
/*  743 */     return (substitute(buf, offset, length, null) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int substitute(StrBuilder buf, int offset, int length, List<String> priorVariables) {
/*  759 */     StrMatcher pfxMatcher = getVariablePrefixMatcher();
/*  760 */     StrMatcher suffMatcher = getVariableSuffixMatcher();
/*  761 */     char escape = getEscapeChar();
/*  762 */     StrMatcher valueDelimMatcher = getValueDelimiterMatcher();
/*  763 */     boolean substitutionInVariablesEnabled = isEnableSubstitutionInVariables();
/*      */     
/*  765 */     boolean top = (priorVariables == null);
/*  766 */     boolean altered = false;
/*  767 */     int lengthChange = 0;
/*  768 */     char[] chars = buf.buffer;
/*  769 */     int bufEnd = offset + length;
/*  770 */     int pos = offset;
/*  771 */     while (pos < bufEnd) {
/*  772 */       int startMatchLen = pfxMatcher.isMatch(chars, pos, offset, bufEnd);
/*      */       
/*  774 */       if (startMatchLen == 0) {
/*  775 */         pos++;
/*      */         continue;
/*      */       } 
/*  778 */       if (pos > offset && chars[pos - 1] == escape) {
/*      */         
/*  780 */         if (this.preserveEscapes) {
/*  781 */           pos++;
/*      */           continue;
/*      */         } 
/*  784 */         buf.deleteCharAt(pos - 1);
/*  785 */         chars = buf.buffer;
/*  786 */         lengthChange--;
/*  787 */         altered = true;
/*  788 */         bufEnd--;
/*      */         continue;
/*      */       } 
/*  791 */       int startPos = pos;
/*  792 */       pos += startMatchLen;
/*  793 */       int endMatchLen = 0;
/*  794 */       int nestedVarCount = 0;
/*  795 */       while (pos < bufEnd) {
/*  796 */         if (substitutionInVariablesEnabled && (
/*  797 */           endMatchLen = pfxMatcher.isMatch(chars, pos, offset, bufEnd)) != 0) {
/*      */ 
/*      */           
/*  800 */           nestedVarCount++;
/*  801 */           pos += endMatchLen;
/*      */           
/*      */           continue;
/*      */         } 
/*  805 */         endMatchLen = suffMatcher.isMatch(chars, pos, offset, bufEnd);
/*      */         
/*  807 */         if (endMatchLen == 0) {
/*  808 */           pos++;
/*      */           continue;
/*      */         } 
/*  811 */         if (nestedVarCount == 0) {
/*  812 */           String varNameExpr = new String(chars, startPos + startMatchLen, pos - startPos - startMatchLen);
/*      */ 
/*      */           
/*  815 */           if (substitutionInVariablesEnabled) {
/*  816 */             StrBuilder bufName = new StrBuilder(varNameExpr);
/*  817 */             substitute(bufName, 0, bufName.length());
/*  818 */             varNameExpr = bufName.toString();
/*      */           } 
/*  820 */           pos += endMatchLen;
/*  821 */           int endPos = pos;
/*      */           
/*  823 */           String varName = varNameExpr;
/*  824 */           String varDefaultValue = null;
/*      */           
/*  826 */           if (valueDelimMatcher != null) {
/*  827 */             char[] varNameExprChars = varNameExpr.toCharArray();
/*  828 */             int valueDelimiterMatchLen = 0;
/*  829 */             for (int i = 0; i < varNameExprChars.length; i++) {
/*      */               
/*  831 */               if (!substitutionInVariablesEnabled && pfxMatcher
/*  832 */                 .isMatch(varNameExprChars, i, i, varNameExprChars.length) != 0) {
/*      */                 break;
/*      */               }
/*  835 */               if ((valueDelimiterMatchLen = valueDelimMatcher.isMatch(varNameExprChars, i)) != 0) {
/*  836 */                 varName = varNameExpr.substring(0, i);
/*  837 */                 varDefaultValue = varNameExpr.substring(i + valueDelimiterMatchLen);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  844 */           if (priorVariables == null) {
/*  845 */             priorVariables = new ArrayList<>();
/*  846 */             priorVariables.add(new String(chars, offset, length));
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  851 */           checkCyclicSubstitution(varName, priorVariables);
/*  852 */           priorVariables.add(varName);
/*      */ 
/*      */           
/*  855 */           String varValue = resolveVariable(varName, buf, startPos, endPos);
/*      */           
/*  857 */           if (varValue == null) {
/*  858 */             varValue = varDefaultValue;
/*      */           }
/*  860 */           if (varValue != null) {
/*      */             
/*  862 */             int varLen = varValue.length();
/*  863 */             buf.replace(startPos, endPos, varValue);
/*  864 */             altered = true;
/*  865 */             int change = substitute(buf, startPos, varLen, priorVariables);
/*      */             
/*  867 */             change = change + varLen - endPos - startPos;
/*      */             
/*  869 */             pos += change;
/*  870 */             bufEnd += change;
/*  871 */             lengthChange += change;
/*  872 */             chars = buf.buffer;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  877 */           priorVariables
/*  878 */             .remove(priorVariables.size() - 1);
/*      */           break;
/*      */         } 
/*  881 */         nestedVarCount--;
/*  882 */         pos += endMatchLen;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  888 */     if (top) {
/*  889 */       return altered ? 1 : 0;
/*      */     }
/*  891 */     return lengthChange;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCyclicSubstitution(String varName, List<String> priorVariables) {
/*  901 */     if (!priorVariables.contains(varName)) {
/*      */       return;
/*      */     }
/*  904 */     StrBuilder buf = new StrBuilder(256);
/*  905 */     buf.append("Infinite loop in property interpolation of ");
/*  906 */     buf.append(priorVariables.remove(0));
/*  907 */     buf.append(": ");
/*  908 */     buf.appendWithSeparators(priorVariables, "->");
/*  909 */     throw new IllegalStateException(buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String resolveVariable(String variableName, StrBuilder buf, int startPos, int endPos) {
/*  930 */     StrLookup<?> resolver = getVariableResolver();
/*  931 */     if (resolver == null) {
/*  932 */       return null;
/*      */     }
/*  934 */     return resolver.lookup(variableName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getEscapeChar() {
/*  945 */     return this.escapeChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEscapeChar(char escapeCharacter) {
/*  956 */     this.escapeChar = escapeCharacter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrMatcher getVariablePrefixMatcher() {
/*  971 */     return this.prefixMatcher;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setVariablePrefixMatcher(StrMatcher prefixMatcher) {
/*  986 */     if (prefixMatcher == null) {
/*  987 */       throw new IllegalArgumentException("Variable prefix matcher must not be null.");
/*      */     }
/*  989 */     this.prefixMatcher = prefixMatcher;
/*  990 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setVariablePrefix(char prefix) {
/* 1004 */     return setVariablePrefixMatcher(StrMatcher.charMatcher(prefix));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setVariablePrefix(String prefix) {
/* 1018 */     if (prefix == null) {
/* 1019 */       throw new IllegalArgumentException("Variable prefix must not be null.");
/*      */     }
/* 1021 */     return setVariablePrefixMatcher(StrMatcher.stringMatcher(prefix));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrMatcher getVariableSuffixMatcher() {
/* 1036 */     return this.suffixMatcher;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setVariableSuffixMatcher(StrMatcher suffixMatcher) {
/* 1051 */     if (suffixMatcher == null) {
/* 1052 */       throw new IllegalArgumentException("Variable suffix matcher must not be null.");
/*      */     }
/* 1054 */     this.suffixMatcher = suffixMatcher;
/* 1055 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setVariableSuffix(char suffix) {
/* 1069 */     return setVariableSuffixMatcher(StrMatcher.charMatcher(suffix));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setVariableSuffix(String suffix) {
/* 1083 */     if (suffix == null) {
/* 1084 */       throw new IllegalArgumentException("Variable suffix must not be null.");
/*      */     }
/* 1086 */     return setVariableSuffixMatcher(StrMatcher.stringMatcher(suffix));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrMatcher getValueDelimiterMatcher() {
/* 1104 */     return this.valueDelimiterMatcher;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setValueDelimiterMatcher(StrMatcher valueDelimiterMatcher) {
/* 1122 */     this.valueDelimiterMatcher = valueDelimiterMatcher;
/* 1123 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setValueDelimiter(char valueDelimiter) {
/* 1138 */     return setValueDelimiterMatcher(StrMatcher.charMatcher(valueDelimiter));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor setValueDelimiter(String valueDelimiter) {
/* 1156 */     if (StringUtils.isEmpty(valueDelimiter)) {
/* 1157 */       setValueDelimiterMatcher(null);
/* 1158 */       return this;
/*      */     } 
/* 1160 */     return setValueDelimiterMatcher(StrMatcher.stringMatcher(valueDelimiter));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrLookup<?> getVariableResolver() {
/* 1171 */     return this.variableResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVariableResolver(StrLookup<?> variableResolver) {
/* 1180 */     this.variableResolver = variableResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableSubstitutionInVariables() {
/* 1192 */     return this.enableSubstitutionInVariables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableSubstitutionInVariables(boolean enableSubstitutionInVariables) {
/* 1206 */     this.enableSubstitutionInVariables = enableSubstitutionInVariables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPreserveEscapes() {
/* 1217 */     return this.preserveEscapes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreserveEscapes(boolean preserveEscapes) {
/* 1233 */     this.preserveEscapes = preserveEscapes;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\text\StrSubstitutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */