import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение с двумя числами:");
        String input = scanner.nextLine();
        System.out.println("Результат арифметического выражения:");
        System.out.println(calc(input));

    }

    public static String calc(String input) throws IOException {
        int firstSymbol;
        int secondSymbol;
        String output;
        boolean isRomanNumber;
//Создаем массив возможных арифметических действий для поиска и сравнения со знаком введеным пользователем
        String[] operationSymbol = {"+", "-", "/", "*"};
//Создаем массив возможных арифметических действий для деления строки на подстроки
        String[] regexOperation = {"\\+", "-", "/", "\\*"};
//Удаляем пробелы из строки (при их наличии)
        input = input.replaceAll("\\s", "");
//Идентифицируем арифметическое дествие введенное пользователем
        int operationSymbolIndex = -1;
        for (int i = 0; i < operationSymbol.length; i++) {
            if (input.contains(operationSymbol[i])) {
                operationSymbolIndex = i;
                break;
            }
        }
//Завершение работы приложения при вводе пользователем строки содержащей неподдерживаемое арифметическое действие
        if (operationSymbolIndex == -1) {
            throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
//Делим строку на подстроки по символу - разделителю и помещаем их в массив
        String[] symbols = input.split(regexOperation[operationSymbolIndex]);
//Завершение работы приложения при вводе пользователем строки содержащей < или > 2х чисел
        if (symbols.length != 2) {
            throw new IOException("формат математической операции не поддерживается. Арифметическая операция возможна тоько с двумя числами");
        }
//Проверка на использование системы счисления
        if (RomanNumbers.isRoman(symbols[0]) && RomanNumbers.isRoman(symbols[1])) {
//При условии, что оба числа римские - конвертируем их в арабские
            firstSymbol = RomanNumbers.convertToArabicNumbers(symbols[0]);
            secondSymbol = RomanNumbers.convertToArabicNumbers(symbols[1]);
            isRomanNumber = true;
//При условии, что оба числа арабские - конвертируем их из строк в целые числа
        } else if (!RomanNumbers.isRoman(symbols[0]) && !RomanNumbers.isRoman(symbols[1])) {
            firstSymbol = Integer.parseInt(symbols[0]);
            secondSymbol = Integer.parseInt(symbols[1]);
            isRomanNumber = false;
        } else {
//Завершение работы приложения при вводе пользователем строки содержащей одновременно разные системы счисления
            throw new IOException("формат математической операции не поддерживается. Используются одновременно разные системы счисления");
        }
//Проверка ввода чисел от 1 до 10
        if ((firstSymbol <= 0 || secondSymbol <= 0) || (firstSymbol > 10 || secondSymbol > 10)) {
            throw new IOException("некорректный ввод чисел. Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }
//Выполняем арифметическре действие с числами
        int arabicResult = outputResult(firstSymbol, secondSymbol, operationSymbol, operationSymbolIndex);
        if (isRomanNumber) {
//Завершение работы приложения если римское число получилось <= 0
            if (arabicResult <= 0) {
                throw new IOException("в римской системе нет нуля и отрицательных чисел");
            }
//Конвертируем и сохраняем результат из арабских чисел в римские
            output = RomanNumbers.convertToRomanNumbers(arabicResult);
        } else {
//Конвертируем арабское чисело в строку
            output = String.valueOf(arabicResult);
        }
//Возвращаем результат
        return output;
    }

    // метод для выполнения арифметического действия с числами
    public static int outputResult(int a, int b, String[] operationSymbol, int operationSymbolIndex) {
        int result;
        switch (operationSymbol[operationSymbolIndex]) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            default:
                result = a / b;
        }
        return result;
    }

}

class RomanNumbers {
    static String[] romanArr = {" ", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII",
            "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV",
            "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI",
            "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII",
            "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII",
            "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII",
            "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII",
            "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};

    //Метод для идентификации римских чисел
    public static boolean isRoman(String symbol) {
        for (int i = 0; i < romanArr.length; i++) {
            if (symbol.equals(romanArr[i])) {
                return true;
            }
        }
        return false;
    }

    //Метод для конвертации римских чисел в арабские
    public static int convertToArabicNumbers(String symbol) {
        for (int i = 0; i < romanArr.length; i++) {
            if (symbol.equals(romanArr[i])) {
                return i;
            }
        }
        return -1;
    }

    //Метод для конвертации арабских чисел в римские
    public static String convertToRomanNumbers(int arabicResult) {
        return romanArr[arabicResult];
    }

}