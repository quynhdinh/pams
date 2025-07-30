package com.quynhdv.pams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.quynhdv.pams.model.Patient;

public class PAMSApp {
    public static void main(String[] args) {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Daniel", "Agar", "(641) 123-0009", "dagar@m.as", "1 N Street", LocalDate.of(1987, 1, 19)));
        patients.add(new Patient("Ana", "Smith", "(641) 123-0009", "amsith@te.edu", null, LocalDate.of(1948, 12, 5)));
        patients.add(new Patient("Marcus", "Garvey", null, "(123) 292-0018", "4 East Ave", LocalDate.of(2001, 9, 18)));
        patients.add(new Patient("Jeff", "Goldbloom", "(999) 165-1192", "jgold@es.co.za", null, LocalDate.of(1995, 2, 28)));
        patients.add(new Patient("Mary", "Washington", "30 W Burlington", null, null, LocalDate.of(1932, 5, 31)));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var list = patients.stream().sorted((x, y) -> Integer.compare(y.getAge(), x.getAge())).toList();
        list.forEach(
            o -> {
                try {
                    String json = ow.writeValueAsString(o);
                    System.out.println(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        );
    }
}
