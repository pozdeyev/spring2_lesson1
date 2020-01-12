package com.geekbrains.decembermarket.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Проверка номера телефона
Метод checkTelNumber должен проверять, является ли аргумент telNumber валидным номером телефона.

Критерии валидности:
1) если номер начинается с '+', то он содержит 12 цифр
2) если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
3) может содержать 0-2 знаков '-', которые не могут идти подряд
4) может содержать 1 пару скобок '(' и ')' , причем если она есть, то она расположена левее знаков '-'
5) скобки внутри содержат четко 3 цифры
6) номер не содержит букв
7) номер заканчивается на цифру

Примеры:
+380501234567 - true
+38(050)1234567 - true
+38050123-45-67 - true
050123-4567 - true
+38)050(1234567 - false
+38(050)1-23-45-6-7 - false
050ххх4567 - false
050123456 - false
(0)501234567 - false


*/

public class PhoneEmailValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public PhoneEmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public static boolean checkTelNumber(String telNumber) {
        if (telNumber != null) {
            String reg = "(\\+?\\d+\\(?\\d{3}\\)?\\d{2}\\-?\\d{2}\\-?\\d{2,3})";
            //"(\\+*)\\d{11}"
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(telNumber);
            return m.matches();
        } else {
            return false;
        }
    }
    public boolean checkEmail(final String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
