package com.company.aquarium.service;


import com.company.aquarium.entity.Fish;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;

import static com.company.aquarium.utils.Utils.*;

public final class FishService {

    private final Random random = new Random();
    private volatile int numberOfCollision = 0;

    public void prepare(){
        showResultsOnConsole();
        checkFishAlive();
        fishRepository.getAllAliveFishes().forEach((fish -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                startMoving(fish);
            });
        }));
    }

    public void checkFishAlive(){
        Executors.newSingleThreadExecutor().execute(
                () -> {
                    for (Fish f : fishRepository.getAllAliveFishes()) {
                        if(LocalDateTime.now().isAfter(f.getLifeTime())) {
                            f.setIsAlive(false);
                            fishRepository.updateFishInfo(f);
                        }
                    }
                });
    }

    public synchronized void startMoving(Fish fish){
        fish.setPosition_column(random.nextInt(1, 29));
        fish.setPosition_row(random.nextInt(1, 14));
        fishRepository.updateFishInfo(fish);
        checkCollision(fish);
    }


    public void checkCollision(Fish fish){
        fishRepository.getAllExceptCurrentOne(fish.getId()).forEach(
                f -> {
                    if(
                            Objects.equals(fish.getFather_id(), f.getId()) ||
                                    Objects.equals(fish.getMother_id(), f.getId())
                    ){
//                        System.out.println("Family assembled -> \n");
//                        System.out.println("\t Child : " + fish + '\n');
//                        System.out.println("\t" + ((f.getGender().equals('M')) ? "Father : " : "Mother : ") + f + '\n');
                    }
                    else if(
                            Objects.equals(f.getPosition_column(), fish.getPosition_column())
                                    && Objects.equals(f.getPosition_row(), fish.getPosition_row())
                                    && !Objects.equals(f.getGender()    , fish.getGender())
                    ){
                        UUID mother_id;
                        UUID father_id;
                        numberOfCollision++;
                        System.out.println("Parent Fishes -> \n\t" + fish + "\n\t" + f);
                        if(f.getGender().equals('M') || fish.getGender().equals('F')) {
                            mother_id = f.getId();
                            father_id = fish.getId();
                        } else {
                            mother_id = fish.getId();
                            father_id = f.getId();
                        }
                        Fish childFish = Fish
                                .builder()
                                .id(UUID.randomUUID())
                                .isAlive(true)
                                .father_id(father_id)
                                .mother_id(mother_id)
                                .gender((random.nextInt(0, 2) == 0) ? 'M' : 'F')
                                .position_column(random.nextInt(1, 29))
                                .position_row(random.nextInt(1, 14))
                                .lifeTime(LocalDateTime.now().plus(
                                        random.nextInt(5, 15),
                                        ChronoUnit.SECONDS))
                                .build();
                        fishRepository.addFish(childFish);
                        System.out.println("Child Fish -> \n\t" + childFish + '\n');
                    }
                });
    }


    public void fillAquariumWithFishes(){
        int numberOfFishes = random.nextInt(20, 40);

        for (int i = 1; i < numberOfFishes+1; i++) {
            Fish build = Fish
                    .builder()
                    .id(UUID.randomUUID())
                    .isAlive(true)
                    .gender((random.nextInt(0, 2) == 0) ? 'M' : 'F')
                    .position_column(random.nextInt(1, 29))
                    .position_row(random.nextInt(1, 14))
                    .lifeTime(LocalDateTime.now().plus(
                            random.nextInt(5, 15),
                            ChronoUnit.SECONDS))
                    .build();
            fishRepository.addFish(build);
        }
    }


    public void showResultsOnConsole() {
        List<Fish> aliveFishes = fishRepository.getAllAliveFishes();
        for (int i = 0; i < 15; i++) {
            List<Fish> nums = new ArrayList<>();
            for (Fish f : aliveFishes) {
                if(f.getPosition_row() == i) {
                    nums.add(f);
                    List<Fish> refreshedList = new ArrayList<>(aliveFishes);
                    refreshedList.remove(f);
                    aliveFishes = refreshedList;
                }
            }
            for(int j = 0; j < 30; j++) {
                boolean res = true;
                if (i == 0 || i == 14 || j == 0 || j == 29) System.out.print('#');
                else {
                    for (Fish num : nums) {
                        if (num.getPosition_column() == j) {
                            if(Objects.equals(num.getGender(), 'M')) System.out.print("*");
                            else if(Objects.equals(num.getGender(), 'F')) System.out.print('@');
                            List<Fish> refreshNums = new ArrayList<>(nums);
                            refreshNums.remove(num);
                            nums = refreshNums;
                            res = false;
                            break;
                        }
                    }
                    if(res) System.out.print(' ');
                }
            }
            System.out.println();
        }
        System.out.println("Number of Collision : " + numberOfCollision);
        System.out.println("Number of fishes (alive) : " + fishRepository.getAllAliveFishes().size());
        System.out.println("Number of Male : " + fishRepository.getNumberOfMale());
        System.out.println("Number of Female : " + (
                fishRepository.getAllAliveFishes().size() - fishRepository.getNumberOfMale())
        );
        System.out.println("Number of dead fishes : " + fishRepository.getNumberOfDeadFishes() + "\n\n");
    }


}
