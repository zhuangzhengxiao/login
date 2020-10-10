# 登录注册及Token校验
导入即可用的后端登录注册接口功能

Springboot + mybatis + jwt + mysql + redis

流程：创建farm数据库 ，再导入resources下的两个sql文件，修改application.properties的数据库信息及redis信息

这是一个较为完善的基础项目架构，需要添加什么功能，直接往里面添加业务代码就可以了，通过拦截器拦截了所有的请求，开放了登录、注册、token校验的接口。没有使用JPA 或 Mybatis-Plus，如有需要可以自己添加

注意：登录注册的请求参数是通过json字符串转成base64进行传输的
