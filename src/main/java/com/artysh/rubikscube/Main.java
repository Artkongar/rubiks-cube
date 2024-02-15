package com.artysh.rubikscube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        //TODO: defects - fix UI and rotate with cube size 4 and more
        //TODO: story - auth, scrambler, dynamic result check
        SpringApplication.run(Main.class, args);
    }


}
