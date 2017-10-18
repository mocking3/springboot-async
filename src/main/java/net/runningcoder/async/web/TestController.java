package net.runningcoder.async.web;

import lombok.extern.slf4j.Slf4j;
import net.runningcoder.async.config.CustomizeThreadPoolConfig;
import net.runningcoder.async.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Created by wangmaocheng on 2017/10/13.
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    @Qualifier("main")
    private Executor executor;

    @Autowired
    private TestService testService;

    @Async
    @ResponseBody
    @GetMapping(value = "async/test")
    public ListenableFuture<String> asyncTest() {
        String result = testService.hello();
        return new AsyncResult<>(result);
    }

    @Async
    @ResponseBody
    @GetMapping(value = "async/test-exception")
    public ListenableFuture<String> asyncTestException() {
        throw new RuntimeException();
    }

    @Async
    @ResponseBody
    @GetMapping(value = "async/test-timeout")
    public ListenableFuture<String> asyncTestTimeout() {
        String result = testService.hello();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(result);
    }

    @Async(CustomizeThreadPoolConfig.THREAD_POOL_CUSTOMIZE)
    @ResponseBody
    @GetMapping(value = "async/test-customize")
    public ListenableFuture<String> asyncTestCustomizeThreadPool() {
        String result = testService.hello();
        return new AsyncResult<>(result);
    }

    @ResponseBody
    @GetMapping(value = "async/test-dr")
    public DeferredResult<String> asyncTestDr() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executor.execute(() -> deferredResult.setResult(testService.hello()));
        return deferredResult;
    }

    @ResponseBody
    @GetMapping(value = "sync/test")
    public String syncTest() {
        String result = testService.hello();
        return result;
    }
}
