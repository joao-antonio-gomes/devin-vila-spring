package com.senai.vila.config;

import org.springframework.beans.factory.annotation.Value;

public class VillageConfig {
    private static Double budget = 100000.0;

    public static Double getBudget() {
        return budget;
    }
}
