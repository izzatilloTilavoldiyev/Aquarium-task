package com.company.aquarium.utils;

import com.company.aquarium.repository.FishRepository;
import com.company.aquarium.service.FishService;

public interface Utils {
    FishRepository fishRepository = FishRepository.getInstance();
    FishService fishService = new FishService();
}
