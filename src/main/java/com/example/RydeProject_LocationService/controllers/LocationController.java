package com.example.RydeProject_LocationService.controllers;

import com.example.RydeProject_LocationService.dtos.NearbyDriversRequestDto;
import com.example.RydeProject_LocationService.dtos.SaveDriverLocationRequestDto;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api//location")
public class LocationController {

    private StringRedisTemplate stringRedisTemplate;


    private static final String DRIVER_GEO_OPS_KEY= "drivers";

    private static final Double SEARCH_RADIUS= 5.0;

    public LocationController (StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(SaveDriverLocationRequestDto saveDriverLocationRequestDto){

        try{

            GeoOperations<String , String> geoOps = stringRedisTemplate.opsForGeo();

            geoOps.add(
                    DRIVER_GEO_OPS_KEY ,
                    new RedisGeoCommands.GeoLocation<>(saveDriverLocationRequestDto.getDriverId(),
                            new Point(saveDriverLocationRequestDto.getLatitude(), saveDriverLocationRequestDto.getLongitude())));

            return new ResponseEntity<>(true , HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e);
        }

        return new ResponseEntity<>(false , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/nearbt/drivers")
    public ResponseEntity<List<String>> getNearbyDrivers(NearbyDriversRequestDto nearbyDriversRequestDto){

       try{
           GeoOperations<String , String> geoOps = stringRedisTemplate.opsForGeo();
           Distance radius = new Distance(SEARCH_RADIUS , Metrics.KILOMETERS);

           Circle within = new Circle(
                   new Point(nearbyDriversRequestDto.getLatitude() , nearbyDriversRequestDto.getLongitude()),
                   radius);

           GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY , within);

           List<String> drivers = new ArrayList<>();
           for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results){

               drivers.add(result.getContent().getName());

           }

           return new ResponseEntity<>(drivers , HttpStatus.OK);
       }catch (Exception e){
           System.out.println(e.getMessage());
           System.out.println(e);

           return new ResponseEntity<>(new ArrayList<>() , HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }
}
