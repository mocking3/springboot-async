# springboot-async
springboot异步请求示例，线程池由tomcat转为应用管理

启动，测试地址如下：
* [异步@Async方式测试 http://localhost:8080/async/test](http://localhost:8080/async/test)
* [异步@Async方式异常测试 http://localhost:8080/async/test-exception](http://localhost:8080/async/test-exception)
* [异步@Async方式超时测试 http://localhost:8080/async/test-timeout](http://localhost:8080/async/test-timeout)
* [异步@Async方式多个线程池测试 http://localhost:8080/async/test-customize](http://localhost:8080/async/test-customize)
* [异步DeferredResult方式测试 http://localhost:8080/async/test-dr](http://localhost:8080/async/test-dr)
* [异步Callable方式测试 http://localhost:8080/async/test-callable](http://localhost:8080/async/test-callable)
* [同步测试 http://localhost:8080/sync/test](http://localhost:8080/sync/test)

## 管理线程池
查看线程池
```
GET http://localhost:8080/threadpools
```
修改线程池（注意：corePoolSize>maxPoolSize 设置不生效）
```
PUT Content-Type: application/json  http://localhost:8080/threadpools/{name}
{
     "corePoolSize": 300,
     "maxPoolSize": 300
}
```
清空线程池等待队列
```
DELETE http://localhost:8080/threadpools/{name}/queue
```
