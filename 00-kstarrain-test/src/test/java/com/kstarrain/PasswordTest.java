package com.kstarrain;


import com.kstarrain.utils.Base64Utils;
import com.kstarrain.utils.DESUtils;
import org.junit.Test;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class PasswordTest {


    @Test
    public void test1(){

        int a = Integer.parseInt("0915");
        int b =  a % 2;
        String pass = "Uo+N5bnzpZTYys7TBWpH79e5rMXYS9RMQRXFqzpeqjrDChdBYbdmVeH6QVbzOBM9rIxTjIIi6Hw=\\n";

        String decrypt = new String(DESUtils.decrypt(Base64Utils.decode(pass), "H7My43a*NEim4C%k"));
        System.out.println(decrypt);

    }



}
