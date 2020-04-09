package com.kstarrain;

import com.kstarrain.framework.htmltopdf.HtmlToPdf;
import com.kstarrain.framework.htmltopdf.HtmlToPdfObject;
import com.kstarrain.framework.htmltopdf.PdfColorMode;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2020-04-08 14:24
 * @description:
 */
public class HtmlToPdfTest {

    String filePath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + "律师函.pdf";

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


        HtmlToPdfObject htmlToPdfObject = HtmlToPdfObject.forHtml(body);
        htmlToPdfObject.defaultEncoding("utf-8");
        HtmlToPdf.create()
                //.pageSize(PdfPageSize.A4).marginLeft("30"). marginRight("30").marginTop("25mm").marginBottom("25mm")
                .disableSmartShrinking(true).colorMode(PdfColorMode.COLOR).object(htmlToPdfObject).convert(filePath);


    }
}
