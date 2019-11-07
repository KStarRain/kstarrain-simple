package com.kstarrain.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.kstarrain.pojo.Student;
import com.kstarrain.pojo.UserCrawlInfo;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2019-03-19 17:08
 * @description:
 */
@Slf4j
public class JSONTest {

    @Test
    public void test1(){

        Student student1 = new Student();
        System.out.println(student1.getAge() > 0);

        log.error("【third party error】 get publicNumber js api ticket fail, result : {}", student1.getAge());


//        System.out.println(student1.getAge() != 0);
//        Student student2 = JSON.parseObject(null, Student.class);

        System.out.println(JSON.toJSONString("1234"));

        Student student = new Student();
        Object o = JSON.toJSON(student);

       /* Student student2 = JSON.parseObject(" ", Student.class);

        String s1 = JSON.toJSONString(null);


        Student student1 = TestDataUtils.createStudent1();
        String s = JSON.toJSONString(student1);
        System.out.println(s);

        Student student = JSON.parseObject(s, Student.class);
        System.out.println(student);*/
    }

    @Test
    public void test2(){

        Student student = TestDataUtils.createStudent1();

        AfterFilter afterFilter = new AfterFilter() {
            @Override
            public void writeAfter(Object paramObject) {
                if (paramObject instanceof Student) {
                    Student student = (Student) paramObject;
                    if (student.getId() != null) {
                        writeKeyValue("id", student.getId());
                    }
                }
            }
        };

        String s = JSON.toJSONString(student, afterFilter);
        System.out.println(s);
    }


    @Test
    public void test3(){

        /*String supplierInfo = "{\"supplier\": \"1345\"}";

        JSONObject jsonObject = JSON.parseObject(supplierInfo);
        System.out.println(jsonObject);

        Student student = JSON.parseObject(supplierInfo, Student.class);
        System.out.println(student);*/

        String data = "[{\"decode\":true,\"name\":\"6L2v6YCa5Yqo5Yqb6ZuG5Zui8J+YrA==\",\"phone\":[\"02869514351\",\"02869514352\",\"02869514353\",\"02869514354\",\"02869514355\",\"02260624587\",\"02260624588\",\"02260624589\",\"02260624590\",\"02260624591\",\"02260624592\",\"02260624593\",\"02260624594\",\"075536336581\",\"075536336582\",\"075536336583\",\"075536336584\",\"075536336585\",\"075536336586\",\"075536336587\",\"075536336588\",\"02131354051\",\"02131354052\",\"02131354053\",\"02131354054\",\"02131354055\",\"02131354056\",\"02131354057\",\"02131354058\",\"02869514351\",\"02869514352\",\"02869514353\",\"02869514354\",\"02869514355\",\"02260624587\",\"02260624588\",\"02260624589\",\"02260624590\",\"02260624591\",\"02260624592\",\"02260624593\",\"02260624594\",\"075536336581\",\"075536336582\",\"075536336583\",\"075536336584\",\"075536336585\",\"075536336586\",\"075536336587\",\"075536336588\",\"02131354051\",\"02131354052\",\"02131354053\",\"02131354054\",\"02131354055\",\"02131354056\",\"02131354057\",\"02131354058\",\"02131354300\",\"02131354301\",\"02131354302\",\"02131354303\",\"02131354304\",\"02131354305\",\"02131354306\",\"02131354307\",\"02131354308\",\"02131354309\",\"02131354310\",\"02131354311\",\"02131354312\",\"02131354313\",\"02131354314\",\"02131354315\",\"02131354316\",\"02131354317\",\"02131354318\",\"02131354319\",\"075536336581\",\"075536336582\",\"075536336583\",\"075536336584\",\"075536336585\",\"075536336586\",\"075536336587\",\"075536336588\",\"02131354051\",\"02131354052\",\"02131354053\",\"02131354054\",\"02131354055\",\"02131354056\",\"02131354057\",\"02131354058\",\"02131354300\",\"02131354301\",\"02131354302\",\"02131354303\",\"02131354304\",\"02131354305\",\"02131354306\",\"02131354307\",\"02131354308\",\"02131354309\",\"02131354310\",\"02131354311\",\"02131354312\",\"02131354313\",\"02131354314\",\"02131354315\",\"02131354316\",\"02131354317\",\"02131354318\",\"02131354319\",\"075561230052\",\"075561230053\",\"075561230054\",\"075561230055\",\"075561230056\",\"075561230057\",\"075561230058\",\"075561230059\",\"075561230060\",\"075561230061\",\"075561230062\",\"075561230063\",\"075561230064\",\"075561230065\",\"075561230066\",\"075561230067\",\"075561230068\",\"075561230069\",\"075561230070\",\"075561230071\",\"075561230072\",\"075561230073\",\"075561230074\",\"075561230075\",\"075561230076\",\"075561230077\",\"075561230078\",\"075561230079\",\"075561230080\",\"075561230081\",\"075561230082\",\"075561230083\",\"075561230084\",\"075561230085\",\"075561230086\",\"075561230087\",\"075561230088\",\"075561230089\",\"075561230090\",\"075561230091\",\"075561230092\",\"075561230093\",\"075561230094\",\"075561230095\",\"075561230096\",\"075561230097\",\"075561230098\",\"075561230099\",\"075561230100\",\"075561230101\"]},{\"decode\":true,\"name\":\"6L2v6YCa5Yqo5Yqb6ZuG5Zui5aSa5Lq66YCa6K+d\",\"phone\":[\"01052729739\",\"02462781276\",\"01052729739\",\"02462781276\",\"01052729739\",\"02462781276\"]},{\"decode\":true,\"name\":\"5Y2O5Li65a6i5pyN\",\"phone\":[\"4008308300\"]},{\"decode\":true,\"name\":\"5b6Q5paw5a6H\",\"phone\":[]},{\"decode\":true,\"name\":\"5Y2g5rSq5YW1\",\"phone\":[]},{\"decode\":true,\"name\":\"5YiY5p2D5Lic\",\"phone\":[]},{\"decode\":true,\"name\":\"6Jab552/\",\"phone\":[]},{\"decode\":true,\"name\":\"6IGU6K+a5rW35Y2aX+adjg==\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O5b+g5p2w\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O6Z2Z\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O6Zev5pWZ57uD\",\"phone\":[]},{\"decode\":true,\"name\":\"5byg5Y6a5rSy\",\"phone\":[]},{\"decode\":true,\"name\":\"5LiK5rW36aG65Liw5b+r6YCS\",\"phone\":[]},{\"decode\":true,\"name\":\"5rGk5piO5rWp\",\"phone\":[]},{\"decode\":true,\"name\":\"5Lit5bGx5Yev5peL\",\"phone\":[]},{\"decode\":true,\"name\":\"6auY5pWw6ICB5biI\",\"phone\":[]},{\"decode\":true,\"name\":\"56em5bCR5b+g\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O5ZCR5Lic\",\"phone\":[]},{\"decode\":true,\"name\":\"5aSc5L+u\",\"phone\":[]},{\"decode\":true,\"name\":\"5q615om/5bKQ\",\"phone\":[]},{\"decode\":true,\"name\":\"5byg5Lmf\",\"phone\":[]},{\"decode\":true,\"name\":\"546L6bmP5Lmd\",\"phone\":[]},{\"decode\":true,\"name\":\"5bq36Zu3\",\"phone\":[]},{\"decode\":true,\"name\":\"5byg5oOg56eL\",\"phone\":[]},{\"decode\":true,\"name\":\"546L5YWG5bOw\",\"phone\":[]},{\"decode\":true,\"name\":\"5pyx5paH5by6\",\"phone\":[]},{\"decode\":true,\"name\":\"6auY5LiH5rGf\",\"phone\":[]},{\"decode\":true,\"name\":\"5ZC055Cq\",\"phone\":[]},{\"decode\":true,\"name\":\"5rip5Yaw5Yaw\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O5aSn6ZKK\",\"phone\":[]},{\"decode\":true,\"name\":\"5p6X6bmP\",\"phone\":[]},{\"decode\":true,\"name\":\"5YiR56uL5qOu\",\"phone\":[]},{\"decode\":true,\"name\":\"6buE6bmP6aOe\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O56eA6Iy5\",\"phone\":[]},{\"decode\":true,\"name\":\"6LS+5pmT6L6J\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O5rSq5rab\",\"phone\":[]},{\"decode\":true,\"name\":\"5YiY546J6Iqs\",\"phone\":[]},{\"decode\":true,\"name\":\"54eV5ZCJ\",\"phone\":[]},{\"decode\":true,\"name\":\"546L5Lyf5L2z\",\"phone\":[]},{\"decode\":true,\"name\":\"56em6Zyy6Zyy77yI6buR6ams\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O6LaF\",\"phone\":[]},{\"decode\":true,\"name\":\"5pyx6JWK77yI6buR6ams77yJ\",\"phone\":[]},{\"decode\":true,\"name\":\"55Sw6ZSB\",\"phone\":[]},{\"decode\":true,\"name\":\"6YOt5riF6L+I\",\"phone\":[]},{\"decode\":true,\"name\":\"6a2P6L+e5Y2O\",\"phone\":[]},{\"decode\":true,\"name\":\"5YiY6Zuq5Liw\",\"phone\":[]},{\"decode\":true,\"name\":\"5byg6Z2Z\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O5oOz\",\"phone\":[]},{\"decode\":true,\"name\":\"5L2Z5om/5rCR\",\"phone\":[]},{\"decode\":true,\"name\":\"6aG65Liw5b+r6YCS\",\"phone\":[]},{\"decode\":true,\"name\":\"6LC35bu65a6H\",\"phone\":[]},{\"decode\":true,\"name\":\"546L6ZSQ\",\"phone\":[]},{\"decode\":true,\"name\":\"5puy5rO96ZGr\",\"phone\":[]},{\"decode\":true,\"name\":\"5L+e54+P\",\"phone\":[]},{\"decode\":true,\"name\":\"55Sw5b+X5Lyf\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O6I2r5a6B\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2O5pyI\",\"phone\":[]},{\"decode\":true,\"name\":\"5ZSQ5paH5p2w\",\"phone\":[]},{\"decode\":true,\"name\":\"6ICB5aeo\",\"phone\":[]},{\"decode\":true,\"name\":\"5bCP5rab\",\"phone\":[]},{\"decode\":true,\"name\":\"5L2V5YWo6IOc\",\"phone\":[]},{\"decode\":true,\"name\":\"5a2Z5b2m5Yia\",\"phone\":[]},{\"decode\":true,\"name\":\"56eB5Lq65pWZ57uD\",\"phone\":[]},{\"decode\":true,\"name\":\"546L56eA5Yek\",\"phone\":[]},{\"decode\":true,\"name\":\"6JKy6L+c5Y2O\",\"phone\":[]},{\"decode\":true,\"name\":\"6YGK5Li9\",\"phone\":[]},{\"decode\":true,\"name\":\"5a6L5qCR6LS1\",\"phone\":[]},{\"decode\":true,\"name\":\"5YiY57u0\",\"phone\":[]},{\"decode\":true,\"name\":\"5L2V6Imz6ICB5biI\",\"phone\":[]},{\"decode\":true,\"name\":\"5byg6Iqz\",\"phone\":[]},{\"decode\":true,\"name\":\"5aea5bm/5bqG\",\"phone\":[]},{\"decode\":true,\"name\":\"5byg5r2H\",\"phone\":[]},{\"decode\":true,\"name\":\"5p2c5b+X6LaF\",\"phone\":[]},{\"decode\":true,\"name\":\"5YiY5a6q5Zu9\",\"phone\":[]},{\"decode\":true,\"name\":\"5LqO5reR5Y2O\",\"phone\":[]},{\"decode\":true,\"name\":\"5aec6bmk\",\"phone\":[]},{\"decode\":true,\"name\":\"5a2Z5b63\",\"phone\":[]},{\"decode\":true,\"name\":\"546L5Lic5YW0\",\"phone\":[]},{\"decode\":true,\"name\":\"6buR6ams5pmP5aic\",\"phone\":[]},{\"decode\":true,\"name\":\"5ZGo5rC45pil\",\"phone\":[]},{\"decode\":true,\"name\":\"6YOR55Sf5qOu\",\"phone\":[]},{\"decode\":true,\"name\":\"6a2P54S2\",\"phone\":[]},{\"decode\":true,\"name\":\"6LW1546J56aP\",\"phone\":[]},{\"decode\":true,\"name\":\"546L6ZSQ\",\"phone\":[]},{\"decode\":true,\"name\":\"55Sw5b+X5Lyf\",\"phone\":[]},{\"decode\":true,\"name\":\"6ams5bu65Y2O77yI55GX55GX77yJ\",\"phone\":[\"186 2195 3669\"]},{\"decode\":true,\"name\":\"5qKB5LiA\",\"phone\":[\"13684845231\"]},{\"decode\":true,\"name\":\"5p6X6bmP\",\"phone\":[\"13089053346\"]},{\"decode\":true,\"name\":\"5YiY5oGS5ruh\",\"phone\":[\"13945618123\",\"5347890\"]},{\"decode\":true,\"name\":\"5YiY5Z2k\",\"phone\":[\"13936736370\"]},{\"decode\":true,\"name\":\"5YiY5p2D5Lic\",\"phone\":[\"13936970591\"]},{\"decode\":true,\"name\":\"5YiY57u0\",\"phone\":[\"15829664239\"]},{\"decode\":true,\"name\":\"5YiY5a6q5Zu9\",\"phone\":[\"13904594818\"]},{\"decode\":true,\"name\":\"5YiY6ZGr\",\"phone\":[\"15164569491\"]},{\"decode\":true,\"name\":\"5YiY6Zuq5Liw\",\"phone\":[\"18249555122\"]},{\"decode\":true,\"name\":\"5YiY5a6H5qWg\",\"phone\":[\"13069678579\"]},{\"decode\":true,\"name\":\"5YiY5a6H6bmP\",\"phone\":[\"18600648453\",\"18547029398\"]},{\"decode\":true,\"name\":\"5YiY546J6Iqs\",\"phone\":[\"13945190548\"]},{\"decode\":true,\"name\":\"5p+z5rO95Y2O\",\"phone\":[\"18246693062\"]},{\"decode\":true,\"name\":\"5Y2i5Yia\",\"phone\":[\"18504157752\"]},{\"decode\":true,\"name\":\"5ZCV5qW3\",\"phone\":[\"18345595685\"]},{\"decode\":true,\"name\":\"5aaI\",\"phone\":[\"13224572077\",\"13234924006\"]},{\"decode\":true,\"name\":\"6ams5by6\",\"phone\":[\"15305530795\"]},{\"decode\":true,\"name\":\"5YCq5reR6Iq5\",\"phone\":[\"15636996292\"]},{\"decode\":true,\"name\":\"5YCq56yR5bq3\",\"phone\":[\"17621230695\"]},{\"decode\":true,\"name\":\"5r2Y6ZSQ\",\"phone\":[\"13936997016\"]},{\"decode\":true,\"name\":\"6JKy6L+c5Y2O\",\"phone\":[\"15845897569\"]},{\"decode\":true,\"name\":\"5py054ix5reR\",\"phone\":[\"15245603224\"]},{\"decode\":true,\"name\":\"56em6Zyy6Zyy77yI6buR6ams\",\"phone\":[\"15055779245\",\"3458437954\"]},{\"decode\":true,\"name\":\"56em5bCR5b+g\",\"phone\":[\"15045980611\"]},{\"decode\":true,\"name\":\"5puy5rO96ZGr\",\"phone\":[\"15546385639\"]},{\"decode\":true,\"name\":\"5LiK5rW36aG65Liw5b+r6YCS\",\"phone\":[\"13120955225\"]},{\"decode\":true,\"name\":\"6aG65Liw5b+r6YCS\",\"phone\":[\"5019210\",\"13704664821\"]},{\"decode\":true,\"name\":\"5a6L5qCR6LS1\",\"phone\":[\"15645932488\"]},{\"decode\":true,\"name\":\"5a2Z6ZW/5Y+L\",\"phone\":[\"15164595676\"]},{\"decode\":true,\"name\":\"5a2Z5b63\",\"phone\":[\"15945900043\"]},{\"decode\":true,\"name\":\"5a2Z5b2m5Yia\",\"phone\":[\"15045972760\"]},{\"decode\":true,\"name\":\"5a2Z5Lit6L6J\",\"phone\":[\"13614513195\"]},{\"decode\":true,\"name\":\"5ZSQ5paH5p2w\",\"phone\":[\"13262257868\"]},{\"decode\":true,\"name\":\"55Sw6ZSB\",\"phone\":[\"15026648721\"]},{\"decode\":true,\"name\":\"55Sw5b+X5Lyf\",\"phone\":[\"13946961602\",\"15645946966\"]},{\"decode\":true,\"name\":\"546L5Lic5YW0\",\"phone\":[\"18245998539\"]},{\"decode\":true,\"name\":\"546L5Y6a5Lit77yI6Z2i5YyF\",\"phone\":[\"13918744279\"]},{\"decode\":true,\"name\":\"546L5YGl5YW0\",\"phone\":[\"15903378752\"]},{\"decode\":true,\"name\":\"546L6bmP5Lmd\",\"phone\":[\"18645900612\"]},{\"decode\":true,\"name\":\"546L6ZSQ\",\"phone\":[\"13214598080\",\"15776561888\"]},{\"decode\":true,\"name\":\"546L5aiB\",\"phone\":[\"15045917332\"]},{\"decode\":true,\"name\":\"546L5Lyf5L2z\",\"phone\":[\"15845887281\"]},{\"decode\":true,\"name\":\"546L5bCP5Li6\",\"phone\":[\"15845838129\"]},{\"decode\":true,\"name\":\"546L56eA5Yek\",\"phone\":[\"13604599292\",\"15645948599\"]},{\"decode\":true,\"name\":\"546L5YWG5bOw\",\"phone\":[\"13331130638\"]},{\"decode\":true,\"name\":\"5b+Y6K6w\",\"phone\":[\"15942345456\"]},{\"decode\":true,\"name\":\"6a2P6L+e5Y2O\",\"phone\":[\"13936872047\"]},{\"decode\":true,\"name\":\"6a2P6bmP\",\"phone\":[\"13154599488\"]},{\"decode\":true,\"name\":\"6a2P54S2\",\"phone\":[\"18616307560\"]},{\"decode\":true,\"name\":\"5rip5Yaw5Yaw\",\"phone\":[\"13936876275\"]},{\"decode\":true,\"name\":\"5ZC055Cq\",\"phone\":[\"18846481285\"]},{\"decode\":true,\"name\":\"6KW/57Gz\",\"phone\":[\"18038997452\"]},{\"decode\":true,\"name\":\"5bCP5rab\",\"phone\":[\"13091454180\"]},{\"decode\":true,\"name\":\"6LCi57qi5qKF\",\"phone\":[\"13144656008\"]},{\"decode\":true,\"name\":\"5YiR56uL5qOu\",\"phone\":[\"15145940886\"]},{\"decode\":true,\"name\":\"5L+h5pa55a6H\",\"phone\":[\"18645958297\"]},{\"decode\":true,\"name\":\"5L+u5qCL5b2s\",\"phone\":[\"15845954366\"]},{\"decode\":true,\"name\":\"5b6Q6L+e5a6P\",\"phone\":[\"15846918972\"]},{\"decode\":true,\"name\":\"5b6Q5paw5a6H\",\"phone\":[\"18746555110\",\"18245560888\"]},{\"decode\":true,\"name\":\"6Jab552/\",\"phone\":[\"13069698791\"]},{\"decode\":true,\"name\":\"6K64546W57qi\",\"phone\":[\"18249554946\"]},{\"decode\":true,\"name\":\"6ZuF5Y2i5YWs5a+T\",\"phone\":[\"13072143601\"]},{\"decode\":true,\"name\":\"54eV6Z2e5YS/\",\"phone\":[\"15143257769\"]},{\"decode\":true,\"name\":\"54eV5ZCJ\",\"phone\":[\"18245898990\",\"18645104043\"]},{\"decode\":true,\"name\":\"5p2o5L2z5pyf\",\"phone\":[\"13796746998\"]},{\"decode\":true,\"name\":\"5p2o5qCR5a2m\",\"phone\":[\"15045972178\"]},{\"decode\":true,\"name\":\"5p2o5pmT5bOw\",\"phone\":[\"18702231177\"]},{\"decode\":true,\"name\":\"5aea5bm/5bqG\",\"phone\":[\"13555554444\"]},{\"decode\":true,\"name\":\"5aSc5L+u\",\"phone\":[\"15062423405\"]},{\"decode\":true,\"name\":\"6YGK5Li9\",\"phone\":[\"13673078097\"]},{\"decode\":true,\"name\":\"5LqO5rSq5pawKOiIhSk=\",\"phone\":[\"13845909177\",\"04596756526\"]},{\"decode\":true,\"name\":\"5LqO5reR5Y2O\",\"phone\":[\"15636999823\",\"18645978158\"]},{\"decode\":true,\"name\":\"5LqO5reR5Y2O\",\"phone\":[\"13633675958\"]},{\"decode\":true,\"name\":\"5LqO5b+g5rSL\",\"phone\":[\"13804666377\"]},{\"decode\":true,\"name\":\"5LqO6ZKf5reH\",\"phone\":[\"15246101143\"]},{\"decode\":true,\"name\":\"5L2Z5om/5rCR\",\"phone\":[\"15205540410\"]},{\"decode\":true,\"name\":\"5L+e54+P\",\"phone\":[\"18845953901\"]},{\"decode\":true,\"name\":\"5pu+5b6X6IOc\",\"phone\":[\"13844646719\",\"15280996468\"]},{\"decode\":true,\"name\":\"5Y2g5rSq5YW1\",\"phone\":[\"13836898219\",\"18846482191\"]},{\"decode\":true,\"name\":\"5byg5pil56OK\",\"phone\":[\"15846899454\",\"6815430\"]},{\"decode\":true,\"name\":\"5byg5biG\",\"phone\":[\"18501426451\"]},{\"decode\":true,\"name\":\"5byg5rW35Lic\",\"phone\":[\"18945298863\"]},{\"decode\":true,\"name\":\"5byg57qi6IGU\",\"phone\":[\"15645915099\"]},{\"decode\":true,\"name\":\"5byg5Y6a5rSy\",\"phone\":[\"18645908671\",\"15840683386\"]},{\"decode\":true,\"name\":\"5byg5oOg56eL\",\"phone\":[\"15645925355\"]},{\"decode\":true,\"name\":\"5byg6Z2Z\",\"phone\":[\"18846798512\",\"04595928688\"]},{\"decode\":true,\"name\":\"5byg5pmv56OK\",\"phone\":[\"15246143005\"]},{\"decode\":true,\"name\":\"5byg5r2H\",\"phone\":[\"15214674476\",\"15694599723\"]},{\"decode\":true,\"name\":\"5byg5r2H\",\"phone\":[\"13936739394\"]},{\"decode\":true,\"name\":\"5byg5oms\",\"phone\":[\"13836728203\"]},{\"decode\":true,\"name\":\"5byg5Lmf\",\"phone\":[\"18645975219\"]},{\"decode\":true,\"name\":\"5byg6Im65a65\",\"phone\":[\"18645998676\"]},{\"decode\":true,\"name\":\"5byg5a6H5Yeh\",\"phone\":[\"13199487553\"]},{\"decode\":true,\"name\":\"5byg5a2Q5YGl\",\"phone\":[\"13845974515\"]},{\"decode\":true,\"name\":\"6LW15Lic\",\"phone\":[\"18245886649\"]},{\"decode\":true,\"name\":\"6LW15Y+45py6\",\"phone\":[\"13555524558\"]},{\"decode\":true,\"name\":\"6LW1546J56aP\",\"phone\":[\"13903691814\"]},{\"decode\":true,\"name\":\"6YOR5LiZ5p6X\",\"phone\":[\"15045893902\"]},{\"decode\":true,\"name\":\"6YOR55Sf5qOu\",\"phone\":[\"18903699017\"]},{\"decode\":true,\"name\":\"6YOR5paw5rqQ\",\"phone\":[\"13936792395\"]},{\"decode\":true,\"name\":\"5Lit5bGx5Yev5peL\",\"phone\":[\"18664470772\"]},{\"decode\":true,\"name\":\"5ZGo5bCP54Wc\",\"phone\":[\"13045498751\"]},{\"decode\":true,\"name\":\"5ZGo5rC45pil\",\"phone\":[\"13836703579\"]},{\"decode\":true,\"name\":\"5pyx6JWK77yI6buR6ams77yJ\",\"phone\":[\"13775860244\",\"3041777094\"]},{\"decode\":true,\"name\":\"5pyx5paH5by6\",\"phone\":[\"13817253816\"]},{\"decode\":true,\"name\":\"6YK557696aqG\",\"phone\":[\"18004597306\"]},{\"decode\":true,\"name\":\"QeaIkQ==\",\"phone\":[\"15800704814\",\"13836843570\"]},{\"decode\":true,\"name\":\"54i4\",\"phone\":[\"13604643931\",\"13945931527\"]},{\"decode\":true,\"name\":\"54i4\",\"phone\":[\"18045709083\"]},{\"decode\":true,\"name\":\"5pu55p2o\",\"phone\":[\"13842758050\",\"13372910411\"]},{\"decode\":true,\"name\":\"6ZmI5Li95rOi\",\"phone\":[\"13796981875\"]},{\"decode\":true,\"name\":\"5Luj5am35am3\",\"phone\":[\"18646691083\"]},{\"decode\":true,\"name\":\"5aSn5bqG5L2z6b6Z\",\"phone\":[\"6322075\"]},{\"decode\":true,\"name\":\"5aSn5bqG6IGU5oOzM2M=\",\"phone\":[\"04596891799\"]}]";
        List<UserCrawlInfo.AddressBook> addressBooks = JSON.parseArray(data, UserCrawlInfo.AddressBook.class);
        UserCrawlInfo.AddressBook addressBook = addressBooks.get(0);
        System.out.println(addressBook.getName());
        System.out.println(addressBook.decodeName());


    }


    @Test
    public void test4() {

        ArrayList<Advertisement> adList = new ArrayList<>();

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Advertisement.class, "url", "action");

        System.out.println(JSON.toJSONString(null, filter));


    }
}
