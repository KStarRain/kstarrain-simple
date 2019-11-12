package com.kstarrain.sort;

/**
 * @author: DongYu
 * @create: 2019-03-10 16:41
 * @description:
 */
public class SortDemo {

    //二分法查找
    public static int search(int[] arr, int key){
        int start = 0;
        int end = arr.length - 1;
        while (start <= end)
        {
            int mid = (end + start)/2;
            if (key < arr[mid])
            {
                end = mid - 1;
            }else if (key>arr[mid]){
                start = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    //交换
    public static void swap(int[] arr, int i, int j){
        //普通
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        //位运算
//        arr[i]^=arr[j];
//        arr[j]^=arr[i];
//        arr[i]^=arr[j];
    }

    //插入排序 升序
    public static void insertSort(int[] a) {
        int i, j, insertNote;// 要插入的数据
        for (i = 1; i < a.length; i++) {// 从数组的第二个元素开始循环将数组中的元素插入
            insertNote = a[i];// 设置数组中的第2个元素为第一次循环要插入的数据
            j = i - 1;
            while (j >= 0 && insertNote < a[j]) {
                a[j + 1] = a[j];// 如果要插入的元素小于第j个元素,就将第j个元素向后移动
                j--;
            }
            a[j + 1] = insertNote;// 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
        }
    }

    //冒泡排序 升序
    public static void select(int[] arr){
        for (int i = 0; i < arr.length - 1; i++){
            for (int j = 0; j < arr.length -1 - i;j++){
                if (arr[j] > arr[j+1]){
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
    }

    // 选择排序升序
    public static void selectSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int tmp = arr[i];
            int index=i;
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j]<tmp) {
                    tmp=arr[j];
                    index=j;
                }
            }
            if (index!=i) {
                //交换
                arr[index]=arr[i];
                arr[i]=tmp;
            }
        }
    }

    //去重
    public static void searchRepeatIndex(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i]==arr[j]) {
                    System.out.println("重复元素下标:"+i);
                    break;//去掉这句可以查找重复次数
                }
            }
        }
    }

}
