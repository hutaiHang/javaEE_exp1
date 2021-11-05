# 实验一、单点登录系统

单点登录全称Single Sign On（以下简称SSO），是指在多系统应用群中登录一个系统，便可在其他所有系统中得到授权而无需再次登录。

## 任务清单

* [X] 掌握sso工作流程
* [X] 基础框架搭建
* [X] 采用mongodb存储用户身份信息（用户名、密码等）
* [X] 对于session不共享的问题，采用mongodb数据库模拟session
* [X] 对于cookie跨域不共享的问题，登录子系统时统一跳转到认证中心
* [X] 单个子系统的测试
* [x] 多个子系统的测试
* [x] 单点退出
* [x] 前端界面的美化
* [x] 代码的规范与完善
* [x] 撰写实验报告

## 技术难点

### url转发/重定向

单点登录主要存在三个问题：

1. 不同域名下cookie不共享
2. 不同服务器端session不共享
3. token有效期问题

对于问题1：

> token统一存储到sso认证中心服务端，当用户登录子系统时，统一去认证中心获取token，此时应采用url重定向机制，否则访问sso认证中心时不会带上cookie中的token。
>
> 在获得token之后，去sso认证中心检查token是否有效，如果token已经过期，则跳转到登录界面进行登录；
>
> 若token有效，则子系统服务器与客户端之间建立局部会话，此时应使用url转发，否则无法与子系统建立局部会话。

对于问题2：

> 采用mongodb数据库模拟session，不同服务端通过读取数据库来获取session（其实session存储采用redis比较好，session不需要持久化，与redis运行在内存中，速度很快的特点相符）

对于问题3：

> 局部会话与全局会话的关系为：
>
> * 全局会话有效，局部会话不一定有效
> * 局部会话有效，全局会话一定有效；
> * 全局会话无效，局部会话一定无效。
>
> 因此可以将局部会话与全局会话的有效时间设为一致。当建立全局会话时，token写入session中，之后建立局部会话时首先检查全局会话是否有效（即检查session中是> 否包含token），若全局会话有效，则建立局部会话，将$isLogin$字段设为真。
>
> 假设有效期为30min，此时局部会话刚刚建立，有效期为30min，全局会话刚刚进行了访问，有效期也为30min。
> 30min后，两个会话均失效。此时再次访问子系统，尽快cookie中带有token，但此时的token是无效的，因此需要重新登录。

### 主要流程

假设目前有两个子系统A、B,以及sso认证中心C。

当我们初次访问A时：

1. 拦截器对访问进行拦截，检查局部会话中 $isLogin$字段是否为真，由于是初次访问，肯定不为真；
2. 重定向到认证中心C，认证中心检查cookie中有无token（字段），若有则说明之前建立了全局连接，并将token返回。初次访问时没有token字段，因此认证中心重定向到登录界面，用户进行登录，生成token，写入到cookie和session中,生成全局会话。再带着token重定向到系统A;
3. 系统A拿到token后去认证中心检查token是否有效（url转发），若token有效则建立局部会话，将$isLogin$字段设为真；
4. 最后再转发到系统A，此时该字段为真，不进行拦截，显示系统A界面。

当我们再次访问系统A时：

1. 拦截器对访问进行拦截，检查局部会话中 $isLogin$字段是否为真，之前以及建立了局部会话此时为真；
2. 不进行拦截，显示系统A界面。

此时初次访问系统B：

1. 拦截器对访问进行拦截，检查局部会话中 $isLogin$字段是否为真，由于是初次访问，肯定不为真；
2. 重定向到认证中心C，认证中心检查cookie中有无token（字段），由于之前建立了全局连接，因此可以在cookie中找到token。将token返回；
3. 系统B拿到token后去认证中心检查token是否有效（url转发），若token有效则建立局部会话，将$isLogin$字段设为真；
4. 最后再转发到系统B，此时该字段为真，不进行拦截，显示系统B界面。

若一开始就访问认证中心进行登录，则建立了全局会话并生成token，之后访问系统A或系统B的流程与上述类似。


## 参考资料

什么是单点登录（SSO） https://zhuanlan.zhihu.com/p/66037342  

单点登录流程 https://cloud.tencent.com/developer/article/1460801

Spring Boot 请求转发和重定向 https://www.zhangbj.com/p/556.html

SpringBoot 之Thymeleaf模板 https://www.cnblogs.com/jmcui/p/9765785.html

SpringBoot拦截器 https://cloud.tencent.com/developer/article/1606373

Springboot分层详解 https://blog.csdn.net/qq_38129062/article/details/88972936

SpringBoot常用注解 https://segmentfault.com/a/1190000022521844

Spring Boot 使用 MongoDB 实现共享 Session https://www.zhangbj.com/p/582.html

SpringBoot redis配置 https://blog.csdn.net/Abysscarry/article/details/80557347
