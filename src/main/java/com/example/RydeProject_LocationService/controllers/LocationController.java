package com.example.RydeProject_LocationService.controllers;

import com.example.RydeProject_LocationService.dtos.DriverLocationDto;
import com.example.RydeProject_LocationService.dtos.NearbyDriversRequestDto;
import com.example.RydeProject_LocationService.dtos.SaveDriverLocationRequestDto;
import com.example.RydeProject_LocationService.services.RedisLocationServiceImpl;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.RydeProject_LocationService.services.LocationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api//location")
public class LocationController {

    private LocationService locationService;




    public LocationController (LocationService locationService){
        this.locationService =  locationService;
    }


    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(SaveDriverLocationRequestDto saveDriverLocationRequestDto){

        try{

            Boolean response = locationService.saveDriverLocation(saveDriverLocationRequestDto.getDriverId(),
                    saveDriverLocationRequestDto.getLatitude(), saveDriverLocationRequestDto.getLongitude());


            return new ResponseEntity<>(response , HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e);
        }

        return new ResponseEntity<>(false , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/nearbt/drivers")
    public ResponseEntity<List<DriverLocationDto>> getNearbyDrivers(NearbyDriversRequestDto nearbyDriversRequestDto){

       try{
           List<DriverLocationDto> drivers = locationService.getNearbyDriver(nearbyDriversRequestDto.getLatitude() ,
                   nearbyDriversRequestDto.getLongitude());

           return new ResponseEntity<>(drivers , HttpStatus.OK);
       }catch (Exception e){
           System.out.println(e.getMessage());
           System.out.println(e);

           return new ResponseEntity<>(new ArrayList<>() , HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }
}
