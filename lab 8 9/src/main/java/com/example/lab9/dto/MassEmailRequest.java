package com.example.lab9.dto;

import lombok.Data;
import java.util.List;

@Data
public class MassEmailRequest {
    private List<String> emails;
    private String subject;
    private String body;
}
