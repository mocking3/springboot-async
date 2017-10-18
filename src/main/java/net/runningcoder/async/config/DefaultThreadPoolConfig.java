package net.runningcoder.async.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import java.util.concurrent.Executor;

/**
 * Created by wangmaocheng on 2017/10/16.
 */
@Configuration
public class DefaultThreadPoolConfig extends AsyncConfigurerSupport {

    @Autowired
    @Qualifier("main")
    private Executor executor;

    @Override
    public Executor getAsyncExecutor() {
        return executor;
    }

}
