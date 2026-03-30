package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableDoubleSupplier<E extends Throwable> {
  double getAsDouble() throws E;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\function\FailableDoubleSupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */