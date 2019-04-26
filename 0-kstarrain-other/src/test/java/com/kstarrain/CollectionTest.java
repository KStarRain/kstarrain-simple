package com.kstarrain;

import com.kstarrain.pojo.Hotel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: Dong Yu
 * @create: 2019-04-26 15:29
 * @description:
 */
public class CollectionTest {

    @Test
    public void test1() {

        List<Hotel> data = new ArrayList<>();
        data.add(new Hotel("汉庭酒店", false, 4));
        data.add(new Hotel("如家酒店", true, 3));
        data.add(new Hotel("锦江之星", true, 5));


        List<Hotel> hotels;
        hotels = data.stream()
                .filter(
                        new Predicate<Hotel>() {
                            @Override
                            public boolean test(Hotel hotel) {
                                if (hotel.getEnable()){
                                    return true;
                                }
                                return false;
                            }
                        }
                )
                .sorted(
                        new Comparator<Hotel>() {
                            @Override
                            public int compare(Hotel o1, Hotel o2) {
                                return o2.getStar() - o1.getStar();
                            }
                        }
//                        (o1,o2) -> o2.getStar() - o1.getStar()
                )
                .collect(Collectors.toList());


        hotels.forEach(System.out :: println);

//                .sorted(Comparator.comparing(SubstituteLabel::getPriority)).collect(Collectors.toList());
    }
}
