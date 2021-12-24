package com.whw.crowd.test;

import com.whw.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-20 19:13
 */
public class StringTest {

    @Test
    public void testMd5(){
        String md5 = CrowdUtil.md5("172161");
        System.out.println(md5);
    }
}
