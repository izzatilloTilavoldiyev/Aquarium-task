package com.company.aquarium.repository;

import com.company.aquarium.entity.Fish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class FishRepository {
    private List<Fish> fishes = new ArrayList<>();

    private static final FishRepository instance = new FishRepository();

    public static FishRepository getInstance() {
        return instance;
    }

    private FishRepository(){}

    public void addFish(Fish fish){
        fishes.add(fish);
    }

    public void updateFishInfo(Fish fish){
        for (int i = 0; i < fishes.size(); i++) {
            if(Objects.equals(fishes.get(i).getId(), fish.getId())) {
                fishes.set(i, fish);
                break;
            }
        }
    }

    public List<Fish> getAllExceptCurrentOne(UUID id){
        return getAllAliveFishes().stream()
                .filter(fish -> !Objects.equals(fish.getId(),id))
                .toList();
    }

    public int getNumberOfDeadFishes(){
        return (int) fishes.stream()
                .filter(fish -> !fish.getIsAlive())
                .count();
    }

    public int getNumberOfMale(){
        return (int) getAllAliveFishes().stream()
                .filter(fish -> Objects.equals(fish.getGender(), 'M'))
                .count();
    }

    public List<Fish> getAllAliveFishes(){
        return fishes.stream()
                .filter(Fish::getIsAlive)
                .toList();
    }
}
