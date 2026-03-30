/*     */ package software.bernie.shadowed.eliotlash.mclib.math;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.Function;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.ACos;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.ASin;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.ATan;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.ATan2;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Abs;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Cos;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Exp;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Ln;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Pow;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Sin;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Sqrt;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Clamp;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Max;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Ceil;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Floor;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Round;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.rounding.Trunc;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.DieRoll;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.DieRollInteger;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.HermiteBlend;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Lerp;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.LerpRotate;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Random;
/*     */ import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.RandomInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathBuilder
/*     */ {
/*  54 */   public Map<String, Variable> variables = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Map<String, Class<? extends Function>> functions = new HashMap<>();
/*     */ 
/*     */   
/*     */   public MathBuilder() {
/*  63 */     register(new Variable("PI", Math.PI));
/*  64 */     register(new Variable("E", Math.E));
/*     */ 
/*     */     
/*  67 */     this.functions.put("floor", Floor.class);
/*  68 */     this.functions.put("round", Round.class);
/*  69 */     this.functions.put("ceil", Ceil.class);
/*  70 */     this.functions.put("trunc", Trunc.class);
/*     */ 
/*     */     
/*  73 */     this.functions.put("clamp", Clamp.class);
/*  74 */     this.functions.put("max", Max.class);
/*  75 */     this.functions.put("min", Min.class);
/*     */ 
/*     */     
/*  78 */     this.functions.put("abs", Abs.class);
/*  79 */     this.functions.put("acos", ACos.class);
/*  80 */     this.functions.put("asin", ASin.class);
/*  81 */     this.functions.put("atan", ATan.class);
/*  82 */     this.functions.put("atan2", ATan2.class);
/*  83 */     this.functions.put("cos", Cos.class);
/*  84 */     this.functions.put("sin", Sin.class);
/*  85 */     this.functions.put("exp", Exp.class);
/*  86 */     this.functions.put("ln", Ln.class);
/*  87 */     this.functions.put("sqrt", Sqrt.class);
/*  88 */     this.functions.put("mod", Mod.class);
/*  89 */     this.functions.put("pow", Pow.class);
/*     */ 
/*     */     
/*  92 */     this.functions.put("lerp", Lerp.class);
/*  93 */     this.functions.put("lerprotate", LerpRotate.class);
/*  94 */     this.functions.put("hermite_blend", HermiteBlend.class);
/*  95 */     this.functions.put("die_roll", DieRoll.class);
/*  96 */     this.functions.put("die_roll_integer", DieRollInteger.class);
/*  97 */     this.functions.put("random", Random.class);
/*  98 */     this.functions.put("random_integer", RandomInteger.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(Variable variable) {
/* 105 */     this.variables.put(variable.getName(), variable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IValue parse(String expression) throws Exception {
/* 113 */     return parseSymbols(breakdownChars(breakdown(expression)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] breakdown(String expression) throws Exception {
/* 121 */     if (!expression.matches("^[\\w\\d\\s_+-/*%^&|<>=!?:.,()]+$")) {
/* 122 */       throw new Exception("Given expression '" + expression + "' contains illegal characters!");
/*     */     }
/*     */ 
/*     */     
/* 126 */     expression = expression.replaceAll("\\s+", "");
/*     */     
/* 128 */     String[] chars = expression.split("(?!^)");
/*     */     
/* 130 */     int left = 0;
/* 131 */     int right = 0;
/*     */     
/* 133 */     for (String s : chars) {
/* 134 */       if (s.equals("(")) {
/* 135 */         left++;
/* 136 */       } else if (s.equals(")")) {
/* 137 */         right++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 142 */     if (left != right) {
/* 143 */       throw new Exception("Given expression '" + expression + "' has more uneven amount of parenthesis, there are " + left + " open and " + right + " closed!");
/*     */     }
/*     */ 
/*     */     
/* 147 */     return chars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> breakdownChars(String[] chars) {
/* 154 */     List<Object> symbols = new ArrayList();
/* 155 */     String buffer = "";
/* 156 */     int len = chars.length;
/*     */     
/* 158 */     for (int i = 0; i < len; i++) {
/* 159 */       String s = chars[i];
/* 160 */       boolean longOperator = (i > 0 && isOperator(chars[i - 1] + s));
/*     */       
/* 162 */       if (isOperator(s) || longOperator || s.equals(",")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 167 */         if (s.equals("-")) {
/* 168 */           int size = symbols.size();
/*     */           
/* 170 */           boolean isFirst = (size == 0 && buffer.isEmpty());
/*     */ 
/*     */           
/* 173 */           boolean isOperatorBehind = (size > 0 && (isOperator(symbols.get(size - 1)) || symbols.get(size - 1).equals(",")) && buffer.isEmpty());
/*     */           
/* 175 */           if (isFirst || isOperatorBehind) {
/* 176 */             buffer = buffer + s;
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         
/* 182 */         if (longOperator) {
/* 183 */           s = chars[i - 1] + s;
/* 184 */           buffer = buffer.substring(0, buffer.length() - 1);
/*     */         } 
/*     */ 
/*     */         
/* 188 */         if (!buffer.isEmpty()) {
/* 189 */           symbols.add(buffer);
/* 190 */           buffer = "";
/*     */         } 
/*     */         
/* 193 */         symbols.add(s); continue;
/* 194 */       }  if (s.equals("(")) {
/*     */         
/* 196 */         if (!buffer.isEmpty()) {
/* 197 */           symbols.add(buffer);
/* 198 */           buffer = "";
/*     */         } 
/*     */         
/* 201 */         int counter = 1;
/*     */         
/* 203 */         for (int j = i + 1; j < len; j++) {
/* 204 */           String c = chars[j];
/*     */           
/* 206 */           if (c.equals("(")) {
/* 207 */             counter++;
/* 208 */           } else if (c.equals(")")) {
/* 209 */             counter--;
/*     */           } 
/*     */           
/* 212 */           if (counter == 0) {
/* 213 */             symbols.add(breakdownChars(buffer.split("(?!^)")));
/*     */             
/* 215 */             i = j;
/* 216 */             buffer = "";
/*     */             
/*     */             break;
/*     */           } 
/* 220 */           buffer = buffer + c;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 225 */         buffer = buffer + s;
/*     */       } 
/*     */       continue;
/*     */     } 
/* 229 */     if (!buffer.isEmpty()) {
/* 230 */       symbols.add(buffer);
/*     */     }
/*     */     
/* 233 */     return symbols;
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
/*     */   public IValue parseSymbols(List<Object> symbols) throws Exception {
/* 248 */     IValue ternary = tryTernary(symbols);
/*     */     
/* 250 */     if (ternary != null) {
/* 251 */       return ternary;
/*     */     }
/*     */     
/* 254 */     int size = symbols.size();
/*     */ 
/*     */     
/* 257 */     if (size == 1) {
/* 258 */       return valueFromObject(symbols.get(0));
/*     */     }
/*     */ 
/*     */     
/* 262 */     if (size == 2) {
/* 263 */       Object first = symbols.get(0);
/* 264 */       Object second = symbols.get(1);
/*     */       
/* 266 */       if ((isVariable(first) || first.equals("-")) && second instanceof List) {
/* 267 */         return createFunction((String)first, (List<Object>)second);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 272 */     int lastOp = seekLastOperator(symbols);
/* 273 */     int op = lastOp;
/*     */     
/* 275 */     while (op != -1) {
/* 276 */       int leftOp = seekLastOperator(symbols, op - 1);
/*     */       
/* 278 */       if (leftOp != -1) {
/* 279 */         Operation left = operationForOperator((String)symbols.get(leftOp));
/* 280 */         Operation right = operationForOperator((String)symbols.get(op));
/*     */         
/* 282 */         if (right.value > left.value) {
/* 283 */           IValue leftValue = parseSymbols(symbols.subList(0, leftOp));
/* 284 */           IValue rightValue = parseSymbols(symbols.subList(leftOp + 1, size));
/*     */           
/* 286 */           return new Operator(left, leftValue, rightValue);
/* 287 */         }  if (left.value > right.value) {
/* 288 */           Operation initial = operationForOperator((String)symbols.get(lastOp));
/*     */           
/* 290 */           if (initial.value < left.value) {
/* 291 */             IValue iValue1 = parseSymbols(symbols.subList(0, lastOp));
/* 292 */             IValue iValue2 = parseSymbols(symbols.subList(lastOp + 1, size));
/*     */             
/* 294 */             return new Operator(initial, iValue1, iValue2);
/*     */           } 
/*     */           
/* 297 */           IValue leftValue = parseSymbols(symbols.subList(0, op));
/* 298 */           IValue rightValue = parseSymbols(symbols.subList(op + 1, size));
/*     */           
/* 300 */           return new Operator(right, leftValue, rightValue);
/*     */         } 
/*     */       } 
/*     */       
/* 304 */       op = leftOp;
/*     */     } 
/*     */     
/* 307 */     Operation operation = operationForOperator((String)symbols.get(lastOp));
/*     */     
/* 309 */     return new Operator(operation, parseSymbols(symbols.subList(0, lastOp)), 
/* 310 */         parseSymbols(symbols.subList(lastOp + 1, size)));
/*     */   }
/*     */   
/*     */   protected int seekLastOperator(List<Object> symbols) {
/* 314 */     return seekLastOperator(symbols, symbols.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int seekLastOperator(List<Object> symbols, int offset) {
/* 321 */     for (int i = offset; i >= 0; i--) {
/* 322 */       Object o = symbols.get(i);
/*     */       
/* 324 */       if (isOperator(o)) {
/* 325 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 329 */     return -1;
/*     */   }
/*     */   
/*     */   protected int seekFirstOperator(List<Object> symbols) {
/* 333 */     return seekFirstOperator(symbols, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int seekFirstOperator(List<Object> symbols, int offset) {
/* 340 */     for (int i = offset, size = symbols.size(); i < size; i++) {
/* 341 */       Object o = symbols.get(i);
/*     */       
/* 343 */       if (isOperator(o)) {
/* 344 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 348 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IValue tryTernary(List<Object> symbols) throws Exception {
/* 359 */     int question = -1;
/* 360 */     int questions = 0;
/* 361 */     int colon = -1;
/* 362 */     int colons = 0;
/* 363 */     int size = symbols.size();
/*     */     
/* 365 */     for (int i = 0; i < size; i++) {
/* 366 */       Object object = symbols.get(i);
/*     */       
/* 368 */       if (object instanceof String) {
/* 369 */         if (object.equals("?")) {
/* 370 */           if (question == -1) {
/* 371 */             question = i;
/*     */           }
/*     */           
/* 374 */           questions++;
/* 375 */         } else if (object.equals(":")) {
/* 376 */           if (colons + 1 == questions && colon == -1) {
/* 377 */             colon = i;
/*     */           }
/*     */           
/* 380 */           colons++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 385 */     if (questions == colons && question > 0 && question + 1 < colon && colon < size - 1) {
/* 386 */       return new Ternary(parseSymbols(symbols.subList(0, question)), 
/* 387 */           parseSymbols(symbols.subList(question + 1, colon)), 
/* 388 */           parseSymbols(symbols.subList(colon + 1, size)));
/*     */     }
/*     */     
/* 391 */     return null;
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
/*     */   protected IValue createFunction(String first, List<Object> args) throws Exception {
/* 406 */     if (first.equals("!")) {
/* 407 */       return new Negate(parseSymbols(args));
/*     */     }
/*     */     
/* 410 */     if (first.startsWith("!") && first.length() > 1) {
/* 411 */       return new Negate(createFunction(first.substring(1), args));
/*     */     }
/*     */ 
/*     */     
/* 415 */     if (first.equals("-")) {
/* 416 */       return new Negative(parseSymbols(args));
/*     */     }
/*     */     
/* 419 */     if (first.startsWith("-") && first.length() > 1) {
/* 420 */       return new Negative(createFunction(first.substring(1), args));
/*     */     }
/*     */     
/* 423 */     if (!this.functions.containsKey(first)) {
/* 424 */       throw new Exception("Function '" + first + "' couldn't be found!");
/*     */     }
/*     */     
/* 427 */     List<IValue> values = new ArrayList<>();
/* 428 */     List<Object> buffer = new ArrayList();
/*     */     
/* 430 */     for (Object o : args) {
/* 431 */       if (o.equals(",")) {
/* 432 */         values.add(parseSymbols(buffer));
/* 433 */         buffer.clear(); continue;
/*     */       } 
/* 435 */       buffer.add(o);
/*     */     } 
/*     */ 
/*     */     
/* 439 */     if (!buffer.isEmpty()) {
/* 440 */       values.add(parseSymbols(buffer));
/*     */     }
/*     */     
/* 443 */     Class<? extends Function> function = this.functions.get(first);
/* 444 */     Constructor<? extends Function> ctor = function.getConstructor(new Class[] { IValue[].class, String.class });
/* 445 */     Function func = ctor.newInstance(new Object[] { values.toArray(new IValue[values.size()]), first });
/*     */     
/* 447 */     return (IValue)func;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IValue valueFromObject(Object object) throws Exception {
/* 458 */     if (object instanceof String) {
/* 459 */       String symbol = (String)object;
/*     */ 
/*     */       
/* 462 */       if (symbol.startsWith("!")) {
/* 463 */         return new Negate(valueFromObject(symbol.substring(1)));
/*     */       }
/*     */       
/* 466 */       if (isDecimal(symbol))
/* 467 */         return new Constant(Double.parseDouble(symbol)); 
/* 468 */       if (isVariable(symbol))
/*     */       {
/* 470 */         if (symbol.startsWith("-")) {
/* 471 */           symbol = symbol.substring(1);
/* 472 */           Variable value = getVariable(symbol);
/*     */           
/* 474 */           if (value != null) {
/* 475 */             return new Negative(value);
/*     */           }
/*     */         } else {
/* 478 */           IValue value = getVariable(symbol);
/*     */ 
/*     */           
/* 481 */           if (value != null) {
/* 482 */             return value;
/*     */           }
/*     */         } 
/*     */       }
/* 486 */     } else if (object instanceof List) {
/* 487 */       return new Group(parseSymbols((List<Object>)object));
/*     */     } 
/*     */     
/* 490 */     throw new Exception("Given object couldn't be converted to value! " + object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Variable getVariable(String name) {
/* 497 */     return this.variables.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Operation operationForOperator(String op) throws Exception {
/* 504 */     for (Operation operation : Operation.values()) {
/* 505 */       if (operation.sign.equals(op)) {
/* 506 */         return operation;
/*     */       }
/*     */     } 
/*     */     
/* 510 */     throw new Exception("There is no such operator '" + op + "'!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVariable(Object o) {
/* 517 */     return (o instanceof String && !isDecimal((String)o) && !isOperator((String)o));
/*     */   }
/*     */   
/*     */   protected boolean isOperator(Object o) {
/* 521 */     return (o instanceof String && isOperator((String)o));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isOperator(String s) {
/* 528 */     return (Operation.OPERATORS.contains(s) || s.equals("?") || s.equals(":"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDecimal(String s) {
/* 535 */     return s.matches("^-?\\d+(\\.\\d+)?$");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\MathBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */