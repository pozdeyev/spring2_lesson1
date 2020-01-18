package com.geekbrains.decembermarket.utils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class HistoryVisitedUtils {

//Обрезаем лишние продукты в истории (также убираем и дубли)
    public static LinkedList<String> cutVisitedProductsHistory(String lastProducts, int maxSize){
        LinkedList list = new LinkedList(Arrays.asList(lastProducts.split("q")));
        if (list.size() > maxSize) {
            list.removeFirst();
        }
        LinkedList result = new LinkedList(new LinkedHashSet(list));
        return result;
    }

    public static String listToString(LinkedList list){
        return list.stream().collect(Collectors.joining("q")).toString();
    }

}
