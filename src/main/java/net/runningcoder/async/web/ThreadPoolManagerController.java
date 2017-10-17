package net.runningcoder.async.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmaocheng on 2017/10/17.
 */
@RestController
@RequestMapping(value = "threadpools")
public class ThreadPoolManagerController {

    @GetMapping(value = "")
    @ResponseBody
    public List<JSONObject> list(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(session.getServletContext());
        Map<String, ThreadPoolTaskExecutor> threadPoolTaskExecutorMap = ctx.getBeansOfType(ThreadPoolTaskExecutor.class);
        List<JSONObject> list = Lists.newArrayList();

        threadPoolTaskExecutorMap.forEach((s, threadPoolTaskExecutor) -> {
            JSONObject json = new JSONObject();
            json.put("name", s);
            json.put("corePoolSize", threadPoolTaskExecutor.getCorePoolSize());
            json.put("maxPoolSize", threadPoolTaskExecutor.getMaxPoolSize());
            json.put("activeCount", threadPoolTaskExecutor.getActiveCount());
            json.put("keepAliveSeconds", threadPoolTaskExecutor.getKeepAliveSeconds());
            json.put("largestPoolSize", threadPoolTaskExecutor.getThreadPoolExecutor().getLargestPoolSize());
            json.put("queueSize", threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size());
            list.add(json);
        });

        return list;
    }

    @PutMapping(value = "{name}")
    @ResponseBody
    public JSONObject update(@PathVariable String name, @RequestBody JSONObject data, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(session.getServletContext());
        ThreadPoolTaskExecutor threadPoolTaskExecutor = ctx.getBean(name, ThreadPoolTaskExecutor.class);
        if (data.getIntValue("maxPoolSize") > 0)
            threadPoolTaskExecutor.setMaxPoolSize(data.getIntValue("maxPoolSize"));
        if (data.getIntValue("corePoolSize") > 0)
            threadPoolTaskExecutor.setCorePoolSize(data.getIntValue("corePoolSize"));
        if (data.getIntValue("keepAliveSeconds") > 0)
            threadPoolTaskExecutor.setKeepAliveSeconds(data.getIntValue("keepAliveSeconds"));

        JSONObject result = new JSONObject();
        result.put("success", true);
        return result;
    }

    @DeleteMapping(value = "{name}/queue")
    @ResponseBody
    public JSONObject clean(@PathVariable String name, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(session.getServletContext());
        ThreadPoolTaskExecutor threadPoolTaskExecutor = ctx.getBean(name, ThreadPoolTaskExecutor.class);
        threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().clear();

        JSONObject result = new JSONObject();
        result.put("success", true);
        return result;
    }
}
