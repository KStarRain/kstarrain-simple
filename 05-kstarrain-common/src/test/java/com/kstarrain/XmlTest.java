package com.kstarrain;

import com.kstarrain.model.Order;
import com.kstarrain.model.Student;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: DongYu
 * @create: 2019-11-20 13:46
 * @description:
 */
public class XmlTest {


    @Test
    public void beanToXmlStr() throws Exception {

        Student student1 = new Student();
        student1.setId(UUID.randomUUID().toString().replace("-", ""));
        student1.setName("貂蝉Mm");
        student1.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-09-07 23:24:51"));
        student1.setMoney(new BigDecimal("1314.98"));
        student1.setCreateDate(new Date());
        student1.setUpdateDate(new Date());
        student1.setAliveFlag(true);

        Order order1 = new Order();
        order1.setGoodsName("上衣");

        Order order2 = new Order();
        order2.setGoodsName("裙子");

//        student1.setOrder(order1);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        student1.setOrderList(orderList);

        XStream xStream = new XStream();
        xStream.processAnnotations(Student.class);
        String xmlStr = xStream.toXML(student1);
        System.out.println(xmlStr);
    }


    @Test
    public void xmlStrToBean() throws Exception {

        String xmlStr = "<xml>\n" +
                        "  <id>91bc6e9036f54fc49b41d05bd665b80e</id>\n" +
                        "  <name>貂蝉Mm</name>\n" +
                        "  <birthday>1991-09-07 14:24:51.0 UTC</birthday>\n" +
                        "  <money>1314.98</money>\n" +
                        "  <createDate>2019-11-20 06:13:40.479 UTC</createDate>\n" +
                        "  <updateDate>2019-11-20 06:13:40.479 UTC</updateDate>\n" +
                        "  <aliveFlag>true</aliveFlag>\n" +
                        "  <orderList>\n" +
                        "    <order>\n" +
                        "      <goodsName>上衣</goodsName>\n" +
                        "    </order>\n" +
                        "    <order>\n" +
                        "      <goodsName>裙子</goodsName>\n" +
                        "    </order>\n" +
                        "  </orderList>\n" +
                        "</xml>";

        XStream xStream = new XStream();
        xStream.processAnnotations(Student.class);
        Student student = (Student)xStream.fromXML(xmlStr);
        System.out.println(student);
    }
}