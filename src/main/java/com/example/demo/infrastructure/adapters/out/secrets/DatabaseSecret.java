package com.example.demo.infrastructure.adapters.out.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatabaseSecret {

    private String url;
    private int port;
    private String username;
    private String password;
    private String dbname;
}
