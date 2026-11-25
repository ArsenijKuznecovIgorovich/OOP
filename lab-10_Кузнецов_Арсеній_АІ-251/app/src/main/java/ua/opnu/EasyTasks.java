package ua.opnu;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EasyTasks {

    public static void main(String[] args) {
        // Для виконання лабораторної роботи необхідно написати вміст методів згідно умовам завдання,
        // після чого протестувати метод за допомогою тестів, які знаходяться в папці
        // src\test\TaskTest.java
    }

    // Завдання 1: кожне число * 2
    public List<Integer> doubling(List<Integer> nums) {
        return nums.stream()
                .map(n -> n * 2)
                .toList();
    }

    // Завдання 2: кожне число * саме на себе
    public List<Integer> square(List<Integer> nums) {
        return nums.stream()
                .map(n -> n * n)
                .toList();
    }

    // Завдання 3: додати 'y' на початку та в кінці кожного рядка
    public List<String> moreY(List<String> strings) {
        return strings.stream()
                .map(s -> "y" + s + "y")
                .toList();
    }

    // Завдання 4: прибрати від'ємні числа
    public List<Integer> noNeg(List<Integer> nums) {
        return nums.stream()
                .filter(n -> n >= 0)
                .toList();
    }

    // Завдання 5: прибрати числа, які закінчуються на 9
    public List<Integer> no9(List<Integer> nums) {
        return nums.stream()
                .filter(n -> n % 10 != 9)
                .toList();
    }

    // Завдання 6: прибрати рядки, що містять літеру 'z'
    public List<String> noZ(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.contains("z"))
                .toList();
    }

    // Завдання 7: унікальні рядки, відсортовані за спаданням довжини
    public List<String> refinedStrings(List<String> strings) {
        return strings.stream()
                .distinct()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .toList();
    }

    // Завдання 8: розбити рядки "Ім'я Прізвище" на окремі слова
    public List<String> flatten(List<String> strings) {
        return strings.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .toList();
    }

}
