package com.company.aquarium;

import com.company.aquarium.utils.Utils;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Utils.fishService.fillAquariumWithFishes();
        while (true) {
            Utils.fishService.prepare();
            Thread.sleep(2000);
        }
    }
}