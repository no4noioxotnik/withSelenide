package com.epam.app.Helpers;



public class BruteForce {
    private static String password;
    private static StringBuilder string = new StringBuilder("");
    private static int min = 32, max = 127;
    private static long start;

    private static void loop(int index) {
        for(int i = min; i < max; i++) {
            string.setCharAt(index, (char) i);
            if(index < string.length() - 1)
                loop(index + 1);
            System.out.println(string);
            if(string.toString().equals(password)) {
                System.err.println("password found: " + string);
                System.err.println("It took: " + (System.currentTimeMillis() - start) + " milliseconds");
                System.exit(0);
            }
        }
    }

    public static void crack(String password) {
        BruteForce.password = password;
        start = System.currentTimeMillis();
        while(true) {
            string.append((char) min);

            for(int i = 0; i < string.length() - 1; i++) {
                for(int j = min; j < max; j++) {
                    string.setCharAt(i, (char) j);
                    loop(i + 1);
                }
            }
        }
    }
}
