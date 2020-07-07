package com.epam.app.stepDefinition;

import com.epam.app.Helpers.BruteForce;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class test {
    
    @Test
    public void test() {
        sorting("brrgg", "rgb", "no");
    }

    public static void sorting(String setText, String order, String removeDuplicates) {
        String input = setText.replaceAll("",",").substring(1);
        ArrayList<String> output = new ArrayList<String>(Arrays.asList(input.split("\\s*,\\s*")));//need this list to remove duplicates
        if (removeDuplicates.equals("yes")) {
            Set<String> set = new HashSet<>(output);
            output = new ArrayList<>(set);
        } else if(!(order.length() == setText.length())) {
            System.out.println("\nTwo strings must contain the same number of characters\n");
        }
        output.clear();//empty the list for main goal
        for (int i = 0; i < order.length(); i++) {
            String check = String.valueOf(order.charAt(i));
            if (setText.contains(check)) {
                output.add(check);
            }
        }
        System.out.println(output.stream().map(Object::toString).collect(Collectors.joining("")));
    }

    @Test
    public void test2() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello");
            }
        };
        Runnable runnable = () -> System.out.println("Hello2");
        System.out.println(runnable);
    }

    Runnable runnable = () -> System.out.println("now");

    @Test
    public void test3() {
        new Thread(() -> System.out.println("1")).start();
    }

    @Test
    public void test4() {
        BruteForce.crack("r12");
    }


}
