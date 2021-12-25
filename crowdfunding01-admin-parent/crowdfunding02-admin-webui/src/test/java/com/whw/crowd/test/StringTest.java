package com.whw.crowd.test;

import com.whw.crowd.util.CrowdUtil;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Test
    public void testDate(){
        System.out.println(new Date().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println( );
    }
}
