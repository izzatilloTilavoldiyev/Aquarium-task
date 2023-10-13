package com.company.aquarium.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Fish {
    private UUID id;
    private Boolean isAlive;
    private Character gender;
    private LocalDateTime lifeTime;
    private Integer position_row;
    private Integer position_column;
    private UUID father_id;
    private UUID mother_id;

    @Override
    public String toString() {
        return "Fish" + '\n' +
                "id = " + id + '\n' +
                "gender = " + gender + '\n' +
                "lifeTime = " + lifeTime + '\n' +
                "position_row = " + position_row + '\n' +
                "position_column = " + position_column + '\n' +
                "father_id = " + father_id + '\n' +
                "mother_id = " + mother_id + '\n';
    }
}
