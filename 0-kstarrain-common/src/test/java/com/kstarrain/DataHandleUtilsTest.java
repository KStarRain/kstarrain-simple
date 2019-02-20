package com.kstarrain;

import com.kstarrain.utils.PageUtils;
import com.kstarrain.utils.callback.PacketCallback;
import com.kstarrain.utils.DataHandleUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-02-20 16:05
 * @description:
 */
public class DataHandleUtilsTest {

    @Test
    public void packet(){

        List<String> data = new ArrayList<>();
        data.add("貂蝉1");
        data.add("貂蝉2");

//        DataHandleUtils.packet(data, 1, new PacketCallback<Void, List<String>>() {
//            @Override
//            public Void execute(List<String> strings) {
//                strings.set(0,"吕布");
//                return null;
//            }
//        });

        DataHandleUtils.packetSerialize(data, 1, new PacketCallback<Void, List<String>>() {
            @Override
            public Void execute(List<String> strings) {
                strings.set(0,"吕布");
                return null;
            }
        });

        System.out.println(data);
    }


}
