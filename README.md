# **项目名称：校园二手交易平台**<hr><br>

## 成员组成：<br>
>**<font size =4 color="red">林鑫</font> 杨洋 侯雪静 曹永颖 李煌 李恒宇**
<br>

# I.项目描述<hr>
## 一、项目简介：<br>
>**web端校园二手交易平台，买家和卖家均可在平台上注册，注册后卖家可以发布出售的商品信息，买家也可以发布自己想入手的商品信息，买家卖家可通过平台进行联系，商谈并确定进行线下交易**
## 二、Web应用功能需求 
### 1、用户需求：

> 1.&ensp;&nbsp;作为用户，可以在平台上面发布想出售的旧物，让别人能看见我的。<br>
> 2.&ensp;&nbsp;作为用户，可以在平台上面发布或检索想购买的旧物的旧物用户的帖子，寻找需要的商品。<br>
> 3.&ensp;&nbsp;作为用户，可以在平台上注册并绑定电话或其他联系方式等必要信息，可以与别的用户进行聊天。<br>
> 4.&ensp;&nbsp;作为用户，可以浏览其他用户想出售的旧物或想购买的旧物，同时可以与该用户进行私密聊天，进而了解旧物详情。<br>
>5.&ensp;&nbsp;作为用户，可以在平台上用户可以在平台上对所需要购买的旧物检索，从而达到货比三家。



## 三、初步的Web应用界面
### A、用户端功能
>1.&ensp;&nbsp;能够注册、登陆、登出、找回密码<br>
>2.&ensp;&nbsp;登录成功后，能够看到自己的个人信息<br>
>3.&ensp;&nbsp;能够在系统中浏览出售商品的信息<br>
>4.&ensp;&nbsp;能够在系统中出售物品<br>
>5.&ensp;&nbsp;能够
    

    
## 四、Web应用页面总体风格及美工效果
>首页：登录程序<br>
>用户：个人中心、商城界面<br>
>管理员：个人中心、管理中心、商城界面<br>
    
## 五、主页面及次页面大概数量
>1. 用户、管理员等个人中心页面
>2. 收货帖子页面
>3. 出货帖子页面
>4. 用户间聊天页面
>5. 商城页面
>6. 管理员管理中心页面
      
## 六、Web应用质量要求 
   >1.能够基本实现搜索相关帖子<br>
   >2.Firefox，Opera，Chrome 等主流浏览器均可运行<br>
   >3.对于用户信息的安全性有一定保护
    
    
## 七、Web应用的软、硬件环境 
  >浏览器版本为IE6.0及以上，Win7以上操作系统
  
## 八、项目风险分析及应对策略
### 经济问题
>&nbsp;&nbsp;**解决方案**：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.减少开发时间，提高开发效率，缩短开发周期
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.
 
<br>
<hr>


# II.项目实现

## 一、项目分工：
![](https://img-blog.csdnimg.cn/20200409222158769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzQ1MjQyNA==,size_16,color_FFFFFF,t_70)

## 二、技术架构：
![MVC模型概述](<https://images2018.cnblogs.com/blog/1027054/201805/1027054-20180521151956650-1028459743.png>)

<br>
><font color="red">**模型-视图-控制器模式**</font>，也称<font color="red">**MVC模式（Model View Controller）**</font>。用一种``业务逻辑、数据、界面``显示分离的方法组织代码，将业务逻辑聚集到一个部件里面。
<br>

<font color="blue">``模型（Model）``：</font> 负责存储系统的中心数据。<br>
<font color="red">``视图（View）``：</font>将信息显示给用户（可以定义多个视图）。  
<font color="green">``控制器（Controller）``：</font>处理用户输入的信息。负责从视图读取数据，控制用户输入，并向模型发送数据，是应用程序中处理用户交互的部分。

该项目中：  

- 前端页面如（商品展示页面，用户查询页面，用户下单页面，买卖交易页面等可归为``视图层``，将信息通过前端技术的加工排版后，友好的展示给用户，提供用户和系统间的交互功能。  
- 数据持久化（数据库的设计，商品信息表，用户注册表，货物出售表等）可归为``模型层``，将这些重要信息永久的，安全的保存在数据库中，该层与视图层保持数据一致性。  
- 事务的捕获，分发，处理（捕获并处理前端传来的请求），可归为``控制器层``，进行响应的事务处理，是视图层与模型层之间的桥梁。  
  
    
    
  

![SpringMVC架构](<https://images2018.cnblogs.com/blog/1027054/201805/1027054-20180522173053209-1511254402.png>)

一个业务可大致分为：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**事务的请求，捕获，分发，处理，返回，响应**<br>
可与如下步骤对应：<br>  
<font color="red">**Http请求——>寻找处理器——>调用处理器——>调用业务处理和返回结果——>处理视图映射并返回模型——>Http响应** </font> 

(1)<font color="blue">``Http请求：``</font>客户端请求提交到**DispatcherServlet（分发器）**。  
(2)<font color="blue">``寻找处理器：``</font>由**DispatcherServlet**控制器查询一个或多个**HandlerMapping**（处理映射器），找到处理请求的**Controller**。  
(3)<font color="blue">``调用处理器：``</font>**DispatcherServlet**将请求提交到**Controller**。  
(4)(5)<font color="blue">``调用业务处理和返回结果：``</font>  **Controller**调用业务逻辑处理后，返回**ModelAndView**。  
(6)(7)<font color="blue">``处理视图映射并返回模型：``</font> **DispatcherServlet**查询一个或多个**ViewResoler**视图解析器，找到**ModelAndView**指定的视图。  
(8)<font color="blue">``Http响应：``</font>视图负责将结果显示到客户端。

## 三、内容建模：

![](https://ae01.alicdn.com/kf/H2d4555f5d6194e55845de4af372c32b9Z.jpg)

## 四、功能需求建模
![](https://ae01.alicdn.com/kf/Hb3fd651ddc064880b1c91d8cef73143a1.jpg)

## 五、超文本建模
![](https://ae01.alicdn.com/kf/H1d866132275c4ed38b45e0ebda1d641d3.jpg)

2020-4-9
