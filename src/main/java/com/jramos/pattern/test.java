package com.jramos.pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class test {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>(List.of("Jhoel", "Genesis", "Jhoel"));
        removeDuplicates(list);
    }

    public static void removeDuplicates(List<String> list) {

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {

            if (list.contains(iterator.next().intern())) {


            }

            System.out.println(iterator);

        }
    }
}
