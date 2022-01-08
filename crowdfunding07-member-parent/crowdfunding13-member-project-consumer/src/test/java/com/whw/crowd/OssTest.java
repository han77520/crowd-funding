package com.whw.crowd;

import com.whw.crowd.config.OSSProperties;
import com.whw.crowd.util.CrowdUtil;
import com.whw.crowd.util.ResultEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OssTest {

    @Autowired
    private OSSProperties ossProperties;

    @Test
    public void test() throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream("1.jpg");
        // 执行上传
        ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                inputStream,
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                "1.jpg");

        String result = uploadFileToOss.getResult();
        System.out.println(result.toString());

    }

}
