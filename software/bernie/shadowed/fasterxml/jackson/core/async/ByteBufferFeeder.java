package software.bernie.shadowed.fasterxml.jackson.core.async;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ByteBufferFeeder extends NonBlockingInputFeeder {
  void feedInput(ByteBuffer paramByteBuffer) throws IOException;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\async\ByteBufferFeeder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */