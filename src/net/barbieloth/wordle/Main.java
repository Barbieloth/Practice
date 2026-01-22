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
                case "1": openSetting(sc); break;
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





}
