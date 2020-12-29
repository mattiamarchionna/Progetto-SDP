package com.subscribe.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: SubscribeService.proto")
public final class SubscribeServiceGrpc {

  private SubscribeServiceGrpc() {}

  public static final String SERVICE_NAME = "com.subscribe.grpc.SubscribeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest,
      com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> METHOD_SUBSCRIBE_TO =
      io.grpc.MethodDescriptor.<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest, com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.subscribe.grpc.SubscribeService", "subscribeTo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse.getDefaultInstance()))
          .setSchemaDescriptor(new SubscribeServiceMethodDescriptorSupplier("subscribeTo"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest,
      com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> METHOD_UNSUBSCRIBE_FROM =
      io.grpc.MethodDescriptor.<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest, com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.subscribe.grpc.SubscribeService", "unsubscribeFrom"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse.getDefaultInstance()))
          .setSchemaDescriptor(new SubscribeServiceMethodDescriptorSupplier("unsubscribeFrom"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend,
      com.subscribe.grpc.SubscribeServiceOuterClass.ACK> METHOD_INSERT_MEASURE_TOKEN =
      io.grpc.MethodDescriptor.<com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend, com.subscribe.grpc.SubscribeServiceOuterClass.ACK>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.subscribe.grpc.SubscribeService", "insertMeasureToken"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.ACK.getDefaultInstance()))
          .setSchemaDescriptor(new SubscribeServiceMethodDescriptorSupplier("insertMeasureToken"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.subscribe.grpc.SubscribeServiceOuterClass.NodoId,
      com.subscribe.grpc.SubscribeServiceOuterClass.ACK> METHOD_SEND_CONFIRM_GLOBAL =
      io.grpc.MethodDescriptor.<com.subscribe.grpc.SubscribeServiceOuterClass.NodoId, com.subscribe.grpc.SubscribeServiceOuterClass.ACK>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.subscribe.grpc.SubscribeService", "sendConfirmGlobal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.NodoId.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.ACK.getDefaultInstance()))
          .setSchemaDescriptor(new SubscribeServiceMethodDescriptorSupplier("sendConfirmGlobal"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.subscribe.grpc.SubscribeServiceOuterClass.NodoId,
      com.subscribe.grpc.SubscribeServiceOuterClass.ACK> METHOD_HAS_TOKEN_RPC =
      io.grpc.MethodDescriptor.<com.subscribe.grpc.SubscribeServiceOuterClass.NodoId, com.subscribe.grpc.SubscribeServiceOuterClass.ACK>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.subscribe.grpc.SubscribeService", "hasTokenRPC"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.NodoId.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.subscribe.grpc.SubscribeServiceOuterClass.ACK.getDefaultInstance()))
          .setSchemaDescriptor(new SubscribeServiceMethodDescriptorSupplier("hasTokenRPC"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SubscribeServiceStub newStub(io.grpc.Channel channel) {
    return new SubscribeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SubscribeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SubscribeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SubscribeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SubscribeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SubscribeServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribeTo(com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBSCRIBE_TO, responseObserver);
    }

    /**
     */
    public void unsubscribeFrom(com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UNSUBSCRIBE_FROM, responseObserver);
    }

    /**
     */
    public void insertMeasureToken(com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_MEASURE_TOKEN, responseObserver);
    }

    /**
     */
    public void sendConfirmGlobal(com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEND_CONFIRM_GLOBAL, responseObserver);
    }

    /**
     */
    public void hasTokenRPC(com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_HAS_TOKEN_RPC, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SUBSCRIBE_TO,
            asyncUnaryCall(
              new MethodHandlers<
                com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest,
                com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse>(
                  this, METHODID_SUBSCRIBE_TO)))
          .addMethod(
            METHOD_UNSUBSCRIBE_FROM,
            asyncUnaryCall(
              new MethodHandlers<
                com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest,
                com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse>(
                  this, METHODID_UNSUBSCRIBE_FROM)))
          .addMethod(
            METHOD_INSERT_MEASURE_TOKEN,
            asyncUnaryCall(
              new MethodHandlers<
                com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend,
                com.subscribe.grpc.SubscribeServiceOuterClass.ACK>(
                  this, METHODID_INSERT_MEASURE_TOKEN)))
          .addMethod(
            METHOD_SEND_CONFIRM_GLOBAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.subscribe.grpc.SubscribeServiceOuterClass.NodoId,
                com.subscribe.grpc.SubscribeServiceOuterClass.ACK>(
                  this, METHODID_SEND_CONFIRM_GLOBAL)))
          .addMethod(
            METHOD_HAS_TOKEN_RPC,
            asyncUnaryCall(
              new MethodHandlers<
                com.subscribe.grpc.SubscribeServiceOuterClass.NodoId,
                com.subscribe.grpc.SubscribeServiceOuterClass.ACK>(
                  this, METHODID_HAS_TOKEN_RPC)))
          .build();
    }
  }

  /**
   */
  public static final class SubscribeServiceStub extends io.grpc.stub.AbstractStub<SubscribeServiceStub> {
    private SubscribeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SubscribeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscribeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SubscribeServiceStub(channel, callOptions);
    }

    /**
     */
    public void subscribeTo(com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIBE_TO, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unsubscribeFrom(com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UNSUBSCRIBE_FROM, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void insertMeasureToken(com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_MEASURE_TOKEN, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendConfirmGlobal(com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEND_CONFIRM_GLOBAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void hasTokenRPC(com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request,
        io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_HAS_TOKEN_RPC, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SubscribeServiceBlockingStub extends io.grpc.stub.AbstractStub<SubscribeServiceBlockingStub> {
    private SubscribeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SubscribeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscribeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SubscribeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse subscribeTo(com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBSCRIBE_TO, getCallOptions(), request);
    }

    /**
     */
    public com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse unsubscribeFrom(com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UNSUBSCRIBE_FROM, getCallOptions(), request);
    }

    /**
     */
    public com.subscribe.grpc.SubscribeServiceOuterClass.ACK insertMeasureToken(com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_MEASURE_TOKEN, getCallOptions(), request);
    }

    /**
     */
    public com.subscribe.grpc.SubscribeServiceOuterClass.ACK sendConfirmGlobal(com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEND_CONFIRM_GLOBAL, getCallOptions(), request);
    }

    /**
     */
    public com.subscribe.grpc.SubscribeServiceOuterClass.ACK hasTokenRPC(com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_HAS_TOKEN_RPC, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SubscribeServiceFutureStub extends io.grpc.stub.AbstractStub<SubscribeServiceFutureStub> {
    private SubscribeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SubscribeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscribeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SubscribeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> subscribeTo(
        com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIBE_TO, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse> unsubscribeFrom(
        com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UNSUBSCRIBE_FROM, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> insertMeasureToken(
        com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_MEASURE_TOKEN, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> sendConfirmGlobal(
        com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEND_CONFIRM_GLOBAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.subscribe.grpc.SubscribeServiceOuterClass.ACK> hasTokenRPC(
        com.subscribe.grpc.SubscribeServiceOuterClass.NodoId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_HAS_TOKEN_RPC, getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE_TO = 0;
  private static final int METHODID_UNSUBSCRIBE_FROM = 1;
  private static final int METHODID_INSERT_MEASURE_TOKEN = 2;
  private static final int METHODID_SEND_CONFIRM_GLOBAL = 3;
  private static final int METHODID_HAS_TOKEN_RPC = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SubscribeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SubscribeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE_TO:
          serviceImpl.subscribeTo((com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse>) responseObserver);
          break;
        case METHODID_UNSUBSCRIBE_FROM:
          serviceImpl.unsubscribeFrom((com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse>) responseObserver);
          break;
        case METHODID_INSERT_MEASURE_TOKEN:
          serviceImpl.insertMeasureToken((com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend) request,
              (io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK>) responseObserver);
          break;
        case METHODID_SEND_CONFIRM_GLOBAL:
          serviceImpl.sendConfirmGlobal((com.subscribe.grpc.SubscribeServiceOuterClass.NodoId) request,
              (io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK>) responseObserver);
          break;
        case METHODID_HAS_TOKEN_RPC:
          serviceImpl.hasTokenRPC((com.subscribe.grpc.SubscribeServiceOuterClass.NodoId) request,
              (io.grpc.stub.StreamObserver<com.subscribe.grpc.SubscribeServiceOuterClass.ACK>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SubscribeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SubscribeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.subscribe.grpc.SubscribeServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SubscribeService");
    }
  }

  private static final class SubscribeServiceFileDescriptorSupplier
      extends SubscribeServiceBaseDescriptorSupplier {
    SubscribeServiceFileDescriptorSupplier() {}
  }

  private static final class SubscribeServiceMethodDescriptorSupplier
      extends SubscribeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SubscribeServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SubscribeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SubscribeServiceFileDescriptorSupplier())
              .addMethod(METHOD_SUBSCRIBE_TO)
              .addMethod(METHOD_UNSUBSCRIBE_FROM)
              .addMethod(METHOD_INSERT_MEASURE_TOKEN)
              .addMethod(METHOD_SEND_CONFIRM_GLOBAL)
              .addMethod(METHOD_HAS_TOKEN_RPC)
              .build();
        }
      }
    }
    return result;
  }
}
