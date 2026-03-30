package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableDoubleBinaryOperator<E extends Throwable> {
  double applyAsDouble(double paramDouble1, double paramDouble2) throws E;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableDoubleBinaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */