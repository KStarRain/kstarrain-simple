package com.kstarrain.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author: Dong Yu
 * @create: 2019-08-21 12:02
 * @description:
 */
public class StringTest {

    @Test
    public void test1() {


        String oldString = "[Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:685ce425e7bd4d6c953f512eee23b5da> stored,indexed,tokenized<gamename:王者荣耀> stored<gamekind:手机游戏> stored,indexed,tokenized<keywordSearch:wangzherongyao wzry 王者荣耀 > stored<dictname:王者荣耀> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:289> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:944285> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:685ce425e7bd4d6c953f512eee23b5da>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:76bae5aefec047e89bcb6aa4e417f138> stored,indexed,tokenized<gamename:王者荣耀> stored<gamekind:phone> stored,indexed,tokenized<keywordSearch:wangzherongyao wzry 王者荣耀 > stored<dictname:王者荣耀> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:943962> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:76bae5aefec047e89bcb6aa4e417f138>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:de0ac9f584f040e5a79317fa1e5deec1> stored,indexed,tokenized<gamename:5173防骗心得> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wuqianyibaiqishisanfangpianxinde wqybqssfpxd 五千一百七十三防骗心得 > stored<dictname:5173防骗心得> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:180961> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:de0ac9f584f040e5a79317fa1e5deec1>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:e89100f5f4e64606a1157e5b71a8ee08> stored,indexed,tokenized<gamename:萬王之王2(台服)> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wanwangzhiwangertaifu wwzwetf 萬王之王二台服 萬王之王二 wanwangzhiwanger wwzwe 台服 taifu tf > stored<dictname:萬王之王2(台服)> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:180970> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:e89100f5f4e64606a1157e5b71a8ee08>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:fa326ec0cded45e5bd9787e28bcfcafd> stored,indexed,tokenized<gamename:5173网游公社论坛> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wuqianyibaiqishisanwangyougongsheluntan wqybqsswygslt 五千一百七十三网游公社论坛 > stored<dictname:5173网游公社论坛> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:181497> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:fa326ec0cded45e5bd9787e28bcfcafd>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:e8edbb68887d40beb160e46b856882ab> stored,indexed,tokenized<gamename:王者> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wangzhe wz 王者 > stored<dictname:王者> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:181222> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:e8edbb68887d40beb160e46b856882ab>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:a65ac4441b5343a9a61373172ab4b13d> stored,indexed,tokenized<gamename:无双勇气> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wushuangyongqi wsyq 无双勇气 > stored<dictname:无双勇气> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:132143> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:a65ac4441b5343a9a61373172ab4b13d>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:d3339402fa76484bab73aa6c8ad077c2> stored,indexed,tokenized<gamename:舞嘻哈(台服)> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wuxihataifu wxhtf 舞嘻哈台服 舞嘻哈 wuxiha wxh 台服 taifu tf > stored<dictname:舞嘻哈(台服)> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:135414> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:d3339402fa76484bab73aa6c8ad077c2>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:de4253f453264dae8824a20deb7c4535> stored,indexed,tokenized<gamename:王者世界(台服)> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wangzheshijietaifu wzsjtf 王者世界台服 王者世界 wangzheshijie wzsj 台服 taifu tf > stored<dictname:王者世界(台服)> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:135081> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:de4253f453264dae8824a20deb7c4535>>, Document<stored,indexed,tokenized,omitNorms,indexOptions=DOCS<gameid:f0fa346ffe4346e493fd0f2cfdc809c9> stored,indexed,tokenized<gamename:无尽的任务2> stored<gamekind:网络游戏> stored,indexed,tokenized<keywordSearch:wujinderenwuer wjdrwe 无尽的任务二 > stored<dictname:无尽的任务2> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dicttype:GAME> stored<sortValue:0> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<updateindex:135097> stored,indexed,tokenized,omitNorms,indexOptions=DOCS<dictid:f0fa346ffe4346e493fd0f2cfdc809c9>>]";

        String newStr = replaceStr(oldString);

        System.out.println(newStr);
    }


    private String replaceStr(String oldString) {

        String prefix = "<sortValue:";
        String suffix = ">";

        String[] split = oldString.split(prefix);

        if (ArrayUtils.isEmpty(split) || split.length < 2){
            return oldString;
        }

        StringBuffer newString = new StringBuffer();

        for (int i = 0; i < split.length; i++) {

            if (i == 0 ){
                newString.append(split[i]);
            } else {
                String oldStr = split[i];
                String oldSortValueStr = oldStr.substring(0, oldStr.indexOf(suffix));
                BigDecimal newSortValueDecimal = new BigDecimal(oldSortValueStr).multiply(new BigDecimal(0.8)).setScale(2,BigDecimal.ROUND_DOWN);
                String newStr = oldStr.replaceFirst(oldSortValueStr, newSortValueDecimal.toString());
                newString.append(newStr);
            }
        }

        return newString.toString();
    }


    @Test
    public void test2() {

        String a = null;

        String b = "aaa";

        System.out.println(StringUtils.equals(a, b));
//        System.out.println(a.equals(b));
    }

}
