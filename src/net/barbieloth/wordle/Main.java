package net.barbieloth.wordle;

import java.util.*;

public class Main {
    private static int wordLenght = 5;
    private static int maxAttempts = 6;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true){
            showMainMenu();
            String choise = sc.nextLine();

            switch (choise){
                case "1": startGame(sc); break;
                case "2": openSetting(sc); break;
                case "3": System.exit(0); break;
                default: System.out.println("Невірний вибір!");
            }
        }


    }

    private static void showMainMenu(){
        System.out.println("\n--- WORLDE --- ");
        System.out.println("1. Старт");
        System.out.println("2. Налаштування");
        System.out.println("3. Вихід");
        System.out.println("Оберіть опцію: ");
    }

    private static void openSetting(Scanner sc){
        while(true){
            System.out.println("\n--- Налаштування --- ");
            System.out.println("1. Довжина слова (зараз: " + wordLenght + ")");
            System.out.println("2. Макс.спроб (зараз: " + maxAttempts + ")");
            System.out.println("3. Вихід в головне меню");
            String choise = sc.nextLine();
            if (choise.equals("1")){
                System.out.println("Введіть нову довжину: ");
                wordLenght = Integer.parseInt(sc.nextLine());
            } else if (choise.equals("2")){
                System.out.println("Введіть нову кількість спроб: ");
                maxAttempts = Integer.parseInt(sc.nextLine());
            } else if (choise.equals("3")) break;
        }
    }

    public static void startGame(Scanner sc) {
        List<String> history =  new ArrayList<>();

        while (true) {


            System.out.println("====== WORDLE ======");

            for (String row : history) {
                System.out.println(row);
            }
            for (int i = history.size(); i < maxAttempts; i++) {
                System.out.println("\u001B[48;5;240m   \u001B[0m ".repeat(5));
            }
            System.out.println("====================\n");

            String words = "";
            words = sc.nextLine();
        }
    }




}
