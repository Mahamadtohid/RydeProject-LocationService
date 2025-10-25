package com.example.RydeProject_LocationService.services;

import com.example.RydeProject_LocationService.dtos.DriverLocationDto;

import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation(String driverId , Double latitude , Double longitude);

    List<DriverLocationDto> getNearbyDriver(Double latitude , Double longitude);
}
