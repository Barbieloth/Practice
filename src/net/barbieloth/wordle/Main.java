package net.barbieloth.wordle;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);

        int attempts = 6;
        List<String> words = Arrays.asList(
                "арбуз", "банан", "ветер", "город", "дверь",
                "зебра", "книга", "лодка", "метро", "носок",
                "пирог", "радио", "спорт", "туман", "экран"
        );
        List<String> history = new ArrayList<>();

        String main_word = words.get(rand.nextInt(words.size()));

        displayGrid(history, attempts);

    }
    private static void displayGrid(List<String> history, int max) {
        System.out.println("====== WORDLE ======");

        for (String row : history) {
            System.out.println(row);
        }
        for(int i = history.size(); i < max; i++) {
            System.out.println("\u001B[48;5;240m   \u001B[0m ".repeat(5));
        }
        System.out.println("====================\n");
    }


}
