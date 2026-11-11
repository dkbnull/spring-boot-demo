package cn.wbnull.springbootdemo.aop;

import cn.wbnull.springbootdemo.constant.DemoConstants;
import cn.wbnull.springbootdemo.boot.GlobalException;
import cn.wbnull.springbootdemo.util.JSONUtils;
import cn.wbnull.springbootdemo.util.LoggerUtils;
import cn.wbnull.springbootdemo.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 验签
 *
 * @author dukunbiao(null)  2018-09-23
 * https://github.com/dkbnull/SpringBootDemo
 */
@Aspect
@Component
public class SignAop {

    @Value("${demo.sign.key}")
    private String signKey;

    @Value("${demo.sign.max-gap-seconds:300}")
    private long maxGapSeconds;

    /**
     * 声明一个切入点，范围为controller包下所有的类
     * 注：作为切入点签名的方法必须返回void类型
     */
    @Pointcut("execution(public * cn.wbnull.springbootdemo.controller.*.*(..))")
    private void signAop() {

    }

    /**
     * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）
     *
     * @param joinPoint
     * @throws Exception
     */
    @Before("signAop()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        Object[] objects = joinPoint.getArgs();
        if (objects == null || objects.length < 3) {
            throw new GlobalException("sign or timestamp or data is null");
        }

        String sign = objects[0] == null ? "" : objects[0].toString();
        String timestamp = objects[1] == null ? "" : objects[1].toString();
        String data = objects[2] == null ? "" : objects[2].toString();

        String strLog = "[" + Thread.currentThread().getId() + "]" + "[请求方法] " + joinPoint.getSignature().getName() + " ||";
        LoggerUtils.getLogger().info(strLog + "[请求参数] sign=" + sign + ",timestamp=" + timestamp + ",data=" + data);

        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp) ||
                StringUtils.isEmpty(data)) {
            throw new GlobalException("sign or timestamp or data is null");
        }

        long timestampValue;
        try {
            timestampValue = Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            throw new GlobalException("timestamp is invalid");
        }

        long now = System.currentTimeMillis() / 1000;
        if (Math.abs(now - timestampValue) > maxGapSeconds) {
            throw new GlobalException("timestamp is expired");
        }

        String md5String = "data=" + data + "&key=" + signKey + "&timestamp=" + timestamp;
        String signNow = DigestUtils.md5Hex(md5String);

        if (!sign.equalsIgnoreCase(signNow)) {
            throw new GlobalException("sign is error");
        }
    }

    /**
     * 后置通知：在某连接点正常完成后执行的通知，通常在一个匹配的方法返回的时候执行
     *
     * @param joinPoint
     * @param params
     * @return
     */
    @AfterReturning(value = "signAop()", returning = "params")
    public JSONObject doAfterReturning(JoinPoint joinPoint, JSONObject params) {
        String data = JSONUtils.getJSONString(params, DemoConstants.DATA);
        long timestamp = System.currentTimeMillis() / 1000;

        String md5String = "data=" + data + "&key=" + signKey + "&timestamp=" + timestamp;
        String sign = DigestUtils.md5Hex(md5String);

        params.put(DemoConstants.TIMESTAMP, timestamp);
        params.put(DemoConstants.SIGN, sign);

        String strLog = "[" + Thread.currentThread().getId() + "]" + "[请求方法] " + joinPoint.getSignature().getName() + " ||";
        LoggerUtils.getLogger().info(strLog + "[返回参数] " + params);

        return params;
    }
}
