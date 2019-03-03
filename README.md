# ZWRpc 
作者说明：这个项目纯属作为本人兴趣爱好的练手型项目,会不定期更新。现在项目只开发了服务端，而且服务端目前只实现了一些基础功能(粘包拆包，二进制的私有协议解析，心跳，前置过滤器，路由，后置过滤器...)，整个项目的拓展性设计还没有做，并且没怎么测试...后续我会继续开发。  

ZWRpc是一个基于java语言和netty+spring框架的一个RPC通讯框架。  
整个项目包含ServerStub和ClientStub(目前只开发了Server版本)   
  
basic协议说明：  
1、所有byte[]转int/shot均是高位在前  
2、协议分为协议头和报文体两个部分，basic协议的协议头固定为22个字节  
  
详细协议设计  
1、length:  4个字节 int,标识后续报文的长度  
2、magicNumber:  4个字节 hex 固定为0X5A577A77  
3、protoVersion:  2个字节,raw Byte0表示主版本号,Byte1表示辅版本号 如: 0x0001代表版本0.1  
4、msgType: 1个字节  byte 0:同步业务请求消息  1:同步业务响应消息(客户端专用)  2:业务ONE-WAY消息(协议异步请求,不需要应答)  3:心跳请求消息  
5、serializationOption: 1个字节  byte 消息体序列化方式 0x00:protobuf 0x01:json  
6、serviceId: 2个字节 byte 服务ID short 调用具体服务方法的id  
7、serviceVersion: 1个字节 byte 服务版本号 byte   
8、requestId: 4个字节 int 客户端requestId  
9、encryptionKeyIndex: 1个字节  byte 加密秘钥索引,从1开始 如果不加密 固定为0x00.只对数据体进行加密  
10、algorithmIndicator: 1个字节 byte 算法标识,从1开始 0x01: 3DES 如果不加密,固定为0x00.只对数据体进行加密  
11、responseCode:1个字节 byte 应答code 从0开始 0:业务处理成功 1:业务处理失败   发送方此标识默认为0  
// 共 22个字节  
12、data 数据体  
  
目前服务端的启动上下文类为：com.rpc.zw.netty.context.RPCServerContext  
服务器所需的最少jar包也在RPCServer项目的lib下面，如果您想试试项目的话，在把build成jar包的时候，不要把现在RPCServer项目的jar包打进去，把这些jar包放在外层项目里。  
并且服务端不支持一个进程内用此程序监听两个端口！会抛异常。  
后续会继续开发客户端，会增加一些新特性，并且会把结构图补上。  
~继续码代码去了  
 
