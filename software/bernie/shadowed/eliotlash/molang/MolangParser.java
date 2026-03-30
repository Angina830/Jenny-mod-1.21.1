/*     */ package software.bernie.shadowed.eliotlash.molang;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.Constant;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.IValue;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.MathBuilder;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.Variable;
/*     */ import software.bernie.shadowed.eliotlash.molang.expressions.MolangAssignment;
/*     */ import software.bernie.shadowed.eliotlash.molang.expressions.MolangExpression;
/*     */ import software.bernie.shadowed.eliotlash.molang.expressions.MolangMultiStatement;
/*     */ import software.bernie.shadowed.eliotlash.molang.expressions.MolangValue;
/*     */ import software.bernie.shadowed.eliotlash.molang.functions.CosDegrees;
/*     */ import software.bernie.shadowed.eliotlash.molang.functions.SinDegrees;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MolangParser
/*     */   extends MathBuilder
/*     */ {
/*  27 */   public static final MolangExpression ZERO = (MolangExpression)new MolangValue(null, (IValue)new Constant(0.0D));
/*  28 */   public static final MolangExpression ONE = (MolangExpression)new MolangValue(null, (IValue)new Constant(1.0D));
/*     */ 
/*     */   
/*     */   public static final String RETURN = "return ";
/*     */   
/*     */   private MolangMultiStatement currentStatement;
/*     */ 
/*     */   
/*     */   public MolangParser() {
/*  37 */     this.functions.put("cos", CosDegrees.class);
/*  38 */     this.functions.put("sin", SinDegrees.class);
/*     */ 
/*     */     
/*  41 */     remap("abs", "math.abs");
/*  42 */     remap("acos", "math.acos");
/*  43 */     remap("asin", "math.asin");
/*  44 */     remap("atan", "math.atan");
/*  45 */     remap("atan2", "math.atan2");
/*  46 */     remap("ceil", "math.ceil");
/*  47 */     remap("clamp", "math.clamp");
/*  48 */     remap("cos", "math.cos");
/*  49 */     remap("die_roll", "math.die_roll");
/*  50 */     remap("die_roll_integer", "math.die_roll_integer");
/*  51 */     remap("exp", "math.exp");
/*  52 */     remap("floor", "math.floor");
/*  53 */     remap("hermite_blend", "math.hermite_blend");
/*  54 */     remap("lerp", "math.lerp");
/*  55 */     remap("lerprotate", "math.lerprotate");
/*  56 */     remap("ln", "math.ln");
/*  57 */     remap("max", "math.max");
/*  58 */     remap("min", "math.min");
/*  59 */     remap("mod", "math.mod");
/*  60 */     remap("pi", "math.pi");
/*  61 */     remap("pow", "math.pow");
/*  62 */     remap("random", "math.random");
/*  63 */     remap("random_integer", "math.random_integer");
/*  64 */     remap("round", "math.round");
/*  65 */     remap("sin", "math.sin");
/*  66 */     remap("sqrt", "math.sqrt");
/*  67 */     remap("trunc", "math.trunc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remap(String old, String newName) {
/*  74 */     this.functions.put(newName, this.functions.remove(old));
/*     */   }
/*     */   
/*     */   public void setValue(String name, double value) {
/*  78 */     Variable variable = getVariable(name);
/*     */     
/*  80 */     if (variable != null) {
/*  81 */       variable.set(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Variable getVariable(String name) {
/*  90 */     Variable variable = (this.currentStatement == null) ? null : (Variable)this.currentStatement.locals.get(name);
/*     */     
/*  92 */     if (variable == null) {
/*  93 */       variable = super.getVariable(name);
/*     */     }
/*     */     
/*  96 */     if (variable == null) {
/*  97 */       variable = new Variable(name, 0.0D);
/*     */       
/*  99 */       register(variable);
/*     */     } 
/*     */     
/* 102 */     return variable;
/*     */   }
/*     */   
/*     */   public MolangExpression parseJson(JsonElement element) throws MolangException {
/* 106 */     if (element.isJsonPrimitive()) {
/* 107 */       JsonPrimitive primitive = element.getAsJsonPrimitive();
/*     */       
/* 109 */       if (primitive.isString())
/*     */         try {
/* 111 */           return (MolangExpression)new MolangValue(this, (IValue)new Constant(Float.parseFloat(primitive.getAsString())));
/* 112 */         } catch (Exception exception) {
/*     */ 
/*     */           
/* 115 */           return parseExpression(primitive.getAsString());
/*     */         }  
/* 117 */       return (MolangExpression)new MolangValue(this, (IValue)new Constant(primitive.getAsDouble()));
/*     */     } 
/*     */ 
/*     */     
/* 121 */     return ZERO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MolangExpression parseExpression(String expression) throws MolangException {
/* 128 */     List<String> lines = new ArrayList<>();
/*     */     
/* 130 */     for (String split : expression.toLowerCase().trim().split(";")) {
/* 131 */       if (!split.trim().isEmpty()) {
/* 132 */         lines.add(split);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     if (lines.size() == 0) {
/* 137 */       throw new MolangException("Molang expression cannot be blank!");
/*     */     }
/*     */     
/* 140 */     MolangMultiStatement result = new MolangMultiStatement(this);
/*     */     
/* 142 */     this.currentStatement = result;
/*     */     
/*     */     try {
/* 145 */       for (String line : lines) {
/* 146 */         result.expressions.add(parseOneLine(line));
/*     */       }
/* 148 */     } catch (Exception e) {
/* 149 */       this.currentStatement = null;
/*     */       
/* 151 */       throw e;
/*     */     } 
/*     */     
/* 154 */     this.currentStatement = null;
/*     */     
/* 156 */     return (MolangExpression)result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MolangExpression parseOneLine(String expression) throws MolangException {
/* 163 */     expression = expression.trim();
/*     */     
/* 165 */     if (expression.startsWith("return ")) {
/*     */       try {
/* 167 */         return (new MolangValue(this, parse(expression.substring("return ".length())))).addReturn();
/* 168 */       } catch (Exception e) {
/* 169 */         throw new MolangException("Couldn't parse return '" + expression + "' expression!");
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 174 */       List<Object> symbols = breakdownChars(breakdown(expression));
/*     */ 
/*     */       
/* 177 */       if (symbols.size() >= 3 && symbols.get(0) instanceof String && isVariable(symbols.get(0)) && symbols
/* 178 */         .get(1).equals("=")) {
/* 179 */         String name = (String)symbols.get(0);
/* 180 */         symbols = symbols.subList(2, symbols.size());
/*     */         
/* 182 */         Variable variable = null;
/*     */         
/* 184 */         if (!this.variables.containsKey(name) && !this.currentStatement.locals.containsKey(name)) {
/* 185 */           variable = new Variable(name, 0.0D);
/* 186 */           this.currentStatement.locals.put(name, variable);
/*     */         } else {
/* 188 */           variable = getVariable(name);
/*     */         } 
/*     */         
/* 191 */         return (MolangExpression)new MolangAssignment(this, variable, parseSymbolsMolang(symbols));
/*     */       } 
/*     */       
/* 194 */       return (MolangExpression)new MolangValue(this, parseSymbolsMolang(symbols));
/* 195 */     } catch (Exception e) {
/* 196 */       throw new MolangException("Couldn't parse '" + expression + "' expression!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IValue parseSymbolsMolang(List<Object> symbols) throws MolangException {
/*     */     try {
/* 205 */       return parseSymbols(symbols);
/* 206 */     } catch (Exception e) {
/* 207 */       e.printStackTrace();
/*     */       
/* 209 */       throw new MolangException("Couldn't parse an expression!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isOperator(String s) {
/* 219 */     return (super.isOperator(s) || s.equals("="));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\molang\MolangParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */