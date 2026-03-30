package com;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.net.SocketAddress;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;

public class a extends NetworkManager {
  public a(EnumPacketDirection paramEnumPacketDirection) {
    super(paramEnumPacketDirection);
  }
  
  public Channel channel() {
    return new b();
  }
  
  class b implements Channel {
    public ChannelId id() {
      return null;
    }
    
    public EventLoop eventLoop() {
      return null;
    }
    
    public Channel parent() {
      return null;
    }
    
    public ChannelConfig config() {
      return null;
    }
    
    public boolean isOpen() {
      return false;
    }
    
    public boolean isRegistered() {
      return false;
    }
    
    public boolean isActive() {
      return false;
    }
    
    public ChannelMetadata metadata() {
      return null;
    }
    
    public SocketAddress localAddress() {
      return null;
    }
    
    public SocketAddress remoteAddress() {
      return null;
    }
    
    public ChannelFuture closeFuture() {
      return null;
    }
    
    public boolean isWritable() {
      return false;
    }
    
    public long bytesBeforeUnwritable() {
      return 0L;
    }
    
    public long bytesBeforeWritable() {
      return 0L;
    }
    
    public Channel.Unsafe unsafe() {
      return null;
    }
    
    public ChannelPipeline pipeline() {
      return null;
    }
    
    public ByteBufAllocator alloc() {
      return null;
    }
    
    public ChannelPromise newPromise() {
      return null;
    }
    
    public ChannelProgressivePromise newProgressivePromise() {
      return null;
    }
    
    public ChannelFuture newSucceededFuture() {
      return null;
    }
    
    public ChannelFuture newFailedFuture(Throwable param1Throwable) {
      return null;
    }
    
    public ChannelPromise voidPromise() {
      return null;
    }
    
    public ChannelFuture bind(SocketAddress param1SocketAddress) {
      return null;
    }
    
    public ChannelFuture connect(SocketAddress param1SocketAddress) {
      return null;
    }
    
    public ChannelFuture connect(SocketAddress param1SocketAddress1, SocketAddress param1SocketAddress2) {
      return null;
    }
    
    public ChannelFuture disconnect() {
      return null;
    }
    
    public ChannelFuture close() {
      return null;
    }
    
    public ChannelFuture deregister() {
      return null;
    }
    
    public ChannelFuture bind(SocketAddress param1SocketAddress, ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public ChannelFuture connect(SocketAddress param1SocketAddress, ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public ChannelFuture connect(SocketAddress param1SocketAddress1, SocketAddress param1SocketAddress2, ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public ChannelFuture disconnect(ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public ChannelFuture close(ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public ChannelFuture deregister(ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public Channel read() {
      return null;
    }
    
    public ChannelFuture write(Object param1Object) {
      return null;
    }
    
    public ChannelFuture write(Object param1Object, ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public Channel flush() {
      return null;
    }
    
    public ChannelFuture writeAndFlush(Object param1Object, ChannelPromise param1ChannelPromise) {
      return null;
    }
    
    public ChannelFuture writeAndFlush(Object param1Object) {
      return null;
    }
    
    public <T> Attribute<T> attr(AttributeKey<T> param1AttributeKey) {
      return new c();
    }
    
    public <T> boolean hasAttr(AttributeKey<T> param1AttributeKey) {
      return false;
    }
    
    public int a(Channel param1Channel) {
      return 0;
    }
    
    class c implements Attribute<T> {
      public T setIfAbsent(T param2T) {
        return null;
      }
      
      public T getAndSet(T param2T) {
        return null;
      }
      
      public AttributeKey<T> key() {
        return null;
      }
      
      public T getAndRemove() {
        return null;
      }
      
      public void remove() {}
      
      public T get() {
        return null;
      }
      
      public boolean compareAndSet(T param2T1, T param2T2) {
        return false;
      }
      
      public void set(T param2T) {}
    }
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */