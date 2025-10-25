package com.example.RydeProject_LocationService.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationDto {

    String driverId;

    Double latitude;

    Double longitude;
}
