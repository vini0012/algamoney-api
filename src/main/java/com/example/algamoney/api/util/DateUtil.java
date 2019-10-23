package com.example.algamoney.api.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static String getFormatada(Date data) {

        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    public static String getLocalDateFormatada(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDate.format(formatter);
    }
}
