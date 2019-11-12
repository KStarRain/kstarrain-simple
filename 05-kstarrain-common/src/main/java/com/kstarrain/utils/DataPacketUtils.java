package com.kstarrain.utils;



import java.util.ArrayList;
import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-11-08 09:06
 * @description: 数据分组包装工具类
 */
public class DataPacketUtils {


    /**
     * 批量数据分组处理
     * 在单组处理逻辑中对该组数据进行修改会使原数据也发生修改
     * @param data              原数据
     * @param count             单组数据条数
     * @param packetCallback    单组处理数据业务函数
     */
    public static <T> void handle(List<T> data, int count, DataPacketCallback<Void, List<T>> packetCallback)  {

        for (int i = 0, len = data.size() / count + 1; i < len; i++) {
            //单组数据
            List<T> singleUnit;
            if (data.size() == 0) {
                // 整除 多一次
                continue;
            }
            else if (data.size() >= count) {
                singleUnit = data.subList(0, count);
                data = data.subList(count, data.size());
            }
            else {
                // 非整除，最后一次
                singleUnit = data;
            }
            packetCallback.execute(singleUnit);
        }
    }



    /**
     * 批量数据分组处理
     * 在单组处理逻辑中对该组数据进行修改不会使原数据发生修改
     * @param data              原数据
     * @param count             单组数据条数
     * @param callback          单组处理数据业务函数
     */
    public static <T> void handleSerialize(List<T> data, int count, DataPacketCallback<Void, List<T>> callback)  {

        for (int i = 0, len = data.size() / count + 1; i < len; i++) {
            //单组数据
            List<T> singleUnit;
            if (data.size() == 0) {
                // 整除 多一次
                continue;
            }
            else if (data.size() >= count) {
                singleUnit = new ArrayList<>(data.subList(0, count));
                data = data.subList(count, data.size());
            }
            else {
                // 非整除，最后一次
                singleUnit = new ArrayList<>(data);
            }
            callback.execute(singleUnit);
        }
    }

}
