package com.whw.crowd.api;

import com.whw.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 12:27
 */
@FeignClient("whw-crowd-redis")
public interface RedisRemoteService {

    @RequestMapping("set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    );

    @RequestMapping("set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("timeout") long timeout,
            @RequestParam("timeUnit") TimeUnit timeUnit
    );

    @RequestMapping("get/redis/string/value/by/key")
    ResultEntity<String> getRedisStringValueByKey(
            @RequestParam("key") String key
    );

    @RequestMapping("remove/redis/string/value/by/key")
    ResultEntity<String> removeRedisStringValueByKey(
            @RequestParam("key") String key
    );
}
