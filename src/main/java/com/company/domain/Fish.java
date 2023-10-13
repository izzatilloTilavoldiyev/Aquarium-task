package com.company.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@EqualsAndHashCode(callSuper = true)
@Data
public class Fish extends Thread{

    public static final List<Fish> fishList = Collections.synchronizedList(new ArrayList<>());
    private static final Lock lock = new ReentrantLock();
    private static final int ONE_YEAR_LIFE = 1000;

    private Gender gender;
    private int maxAge;
    private int birthAge;
    private FishState fishState;
    private int happyDay;
}