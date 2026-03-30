/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeParser
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final TypeFactory _factory;
/*     */   
/*     */   public TypeParser(TypeFactory f) {
/*  20 */     this._factory = f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeParser withFactory(TypeFactory f) {
/*  27 */     return (f == this._factory) ? this : new TypeParser(f);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaType parse(String canonical) throws IllegalArgumentException {
/*  32 */     canonical = canonical.trim();
/*  33 */     MyTokenizer tokens = new MyTokenizer(canonical);
/*  34 */     JavaType type = parseType(tokens);
/*     */     
/*  36 */     if (tokens.hasMoreTokens()) {
/*  37 */       throw _problem(tokens, "Unexpected tokens after complete type");
/*     */     }
/*  39 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType parseType(MyTokenizer tokens) throws IllegalArgumentException {
/*  45 */     if (!tokens.hasMoreTokens()) {
/*  46 */       throw _problem(tokens, "Unexpected end-of-string");
/*     */     }
/*  48 */     Class<?> base = findClass(tokens.nextToken(), tokens);
/*     */ 
/*     */     
/*  51 */     if (tokens.hasMoreTokens()) {
/*  52 */       String token = tokens.nextToken();
/*  53 */       if ("<".equals(token)) {
/*  54 */         List<JavaType> parameterTypes = parseTypes(tokens);
/*  55 */         TypeBindings b = TypeBindings.create(base, parameterTypes);
/*  56 */         return this._factory._fromClass(null, base, b);
/*     */       } 
/*     */       
/*  59 */       tokens.pushBack(token);
/*     */     } 
/*  61 */     return this._factory._fromClass(null, base, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<JavaType> parseTypes(MyTokenizer tokens) throws IllegalArgumentException {
/*  67 */     ArrayList<JavaType> types = new ArrayList<>();
/*  68 */     while (tokens.hasMoreTokens()) {
/*  69 */       types.add(parseType(tokens));
/*  70 */       if (!tokens.hasMoreTokens())
/*  71 */         break;  String token = tokens.nextToken();
/*  72 */       if (">".equals(token)) return types; 
/*  73 */       if (!",".equals(token)) {
/*  74 */         throw _problem(tokens, "Unexpected token '" + token + "', expected ',' or '>')");
/*     */       }
/*     */     } 
/*  77 */     throw _problem(tokens, "Unexpected end-of-string");
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> findClass(String className, MyTokenizer tokens) {
/*     */     try {
/*  83 */       return this._factory.findClass(className);
/*  84 */     } catch (Exception e) {
/*  85 */       ClassUtil.throwIfRTE(e);
/*  86 */       throw _problem(tokens, "Cannot locate class '" + className + "', problem: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected IllegalArgumentException _problem(MyTokenizer tokens, String msg) {
/*  92 */     return new IllegalArgumentException("Failed to parse type '" + tokens.getAllInput() + "' (remaining: '" + tokens.getRemainingInput() + "'): " + msg);
/*     */   }
/*     */ 
/*     */   
/*     */   static final class MyTokenizer
/*     */     extends StringTokenizer
/*     */   {
/*     */     protected final String _input;
/*     */     
/*     */     protected int _index;
/*     */     
/*     */     protected String _pushbackToken;
/*     */     
/*     */     public MyTokenizer(String str) {
/* 106 */       super(str, "<,>", true);
/* 107 */       this._input = str;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasMoreTokens() {
/* 112 */       return (this._pushbackToken != null || super.hasMoreTokens());
/*     */     }
/*     */ 
/*     */     
/*     */     public String nextToken() {
/*     */       String token;
/* 118 */       if (this._pushbackToken != null) {
/* 119 */         token = this._pushbackToken;
/* 120 */         this._pushbackToken = null;
/*     */       } else {
/* 122 */         token = super.nextToken();
/*     */       } 
/* 124 */       this._index += token.length();
/* 125 */       return token;
/*     */     }
/*     */     
/*     */     public void pushBack(String token) {
/* 129 */       this._pushbackToken = token;
/* 130 */       this._index -= token.length();
/*     */     }
/*     */     
/* 133 */     public String getAllInput() { return this._input; }
/* 134 */     public String getUsedInput() { return this._input.substring(0, this._index); } public String getRemainingInput() {
/* 135 */       return this._input.substring(this._index);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\type\TypeParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */