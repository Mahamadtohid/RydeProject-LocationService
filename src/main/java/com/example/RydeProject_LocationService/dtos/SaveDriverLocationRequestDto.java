package com.example.RydeProject_LocationService.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationRequestDto {

    private String driverId;

    private Double latitude;

    private Double longitude;
}
