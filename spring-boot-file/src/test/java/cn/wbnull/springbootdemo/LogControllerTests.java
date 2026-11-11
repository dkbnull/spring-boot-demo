package cn.wbnull.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * 日志接口测试类
 *
 * @author dukunbiao(null)  2019-03-27
 * https://github.com/dkbnull/SpringBootDemo
 */
@Slf4j
public class LogControllerTests {

    @Test
    public void logUpload() throws Exception {
        String url = "http://127.0.0.1:8084/log/upload";
        String pathname = new File("../logs" + File.separator + "log_20190310.log").getCanonicalPath();
        logUpload(url, pathname);
    }

    private static void logUpload(String url, String pathname) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(pathname));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String result = restTemplate.postForObject(url, requestEntity, String.class);

        log.info("{}", result);
    }
}
