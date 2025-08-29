package com.example.bfhl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class BfhlController {

    // 🔹 Replace with YOUR actual details before deploying
    private static final String USER_ID = "your_fullname_ddmmyyyy"; // all lowercase
    private static final String EMAIL = "your_email@vitstudent.ac.in";
    private static final String ROLL = "your_roll_number";

    @PostMapping("/bfhl")
    public ResponseEntity<Map<String, Object>> process(@RequestBody Map<String, Object> body) {
        Object raw = body.get("data");
        if (!(raw instanceof List<?>)) {
            return ResponseEntity.ok(errorResponse("Invalid payload: 'data' must be an array"));
        }

        List<?> list = (List<?>) raw;
        List<String> input = new ArrayList<>();
        for (Object o : list)
            if (o instanceof String s)
                input.add(s);

        List<String> odd = new ArrayList<>();
        List<String> even = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specials = new ArrayList<>();
        long sum = 0;

        for (String item : input) {
            if (item.matches("\\d+")) { // numbers
                long num = Long.parseLong(item);
                sum += num;
                if ((num % 2) == 0)
                    even.add(item);
                else
                    odd.add(item);
            } else if (item.matches("[a-zA-Z]+")) { // alphabets
                alphabets.add(item.toUpperCase());
            } else { // specials
                specials.add(item);
            }
        }

        // 🔹 concat_string = all letters (joined), reversed, alternating caps
        String joined = String.join("", alphabets);
        StringBuilder concat = new StringBuilder();
        for (int i = joined.length() - 1, j = 0; i >= 0; i--, j++) {
            char c = joined.charAt(i);
            concat.append(j % 2 == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("is_success", true);
        res.put("user_id", USER_ID);
        res.put("email", EMAIL);
        res.put("roll_number", ROLL);
        res.put("odd_numbers", odd);
        res.put("even_numbers", even);
        res.put("alphabets", alphabets);
        res.put("special_characters", specials);
        res.put("sum", String.valueOf(sum)); // must be string
        res.put("concat_string", concat.toString());

        return ResponseEntity.ok(res); // status 200
    }

    private Map<String, Object> errorResponse(String msg) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("is_success", false);
        res.put("error", msg);
        res.put("user_id", USER_ID);
        res.put("email", EMAIL);
        res.put("roll_number", ROLL);
        res.put("odd_numbers", List.of());
        res.put("even_numbers", List.of());
        res.put("alphabets", List.of());
        res.put("special_characters", List.of());
        res.put("sum", "0");
        res.put("concat_string", "");
        return res;
    }
}
