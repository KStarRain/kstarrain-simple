package com.kstarrain;

import lombok.extern.slf4j.Slf4j;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2020-04-08 11:35
 * @description:
 */
@Slf4j
public class JtwigTest {

    @Test
    public void render() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/not_sign.twig");

        Map<String, Object> params = new HashMap<>();
        params.put("appellation", "貂蝉");
        params.put("respondentCertIdSimple", "**************0914");
        params.put("billDate", "2020-04-08");
        params.put("payAmt", "52100");
        params.put("businessContact", "吕布");
        params.put("businessMobile",  "13555555555");
        params.put("docDate", "2020-04-08");

        JtwigModel values = JtwigModel.newModel(params);
        String body = template.render(values);

        System.out.println(body);
    }
}
