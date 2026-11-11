package cn.wbnull.springbootdemo.service;

import cn.wbnull.springbootdemo.util.JSONUtils;
import cn.wbnull.springbootdemo.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 登录接口服务类
 *
 * @author dukunbiao(null)  2018-09-23
 * https://github.com/dkbnull/SpringBootDemo
 */
@Service
public class LoginService {

    public JSONObject login(Object data) throws Exception {
        JSONObject responseParams = new JSONObject();

        if (data == null || StringUtils.isEmpty(data.toString())) {
            responseParams.put("code", "2000");
            responseParams.put("message", "FAIL");

            return responseParams;
        }

        JSONObject requestParams = JSONObject.parseObject(data.toString());
        String username = JSONUtils.getJSONString(requestParams, "username");
        String password = JSONUtils.getJSONString(requestParams, "password");
        if (StringUtils.areNotEmpty(username, password) && username.equalsIgnoreCase(password)) {
            responseParams.put("code", "1000");
            responseParams.put("message", "SUCCESS");
        } else {
            responseParams.put("code", "2000");
            responseParams.put("message", "FAIL");
        }

        return responseParams;
    }
}
