package com.alimurat.SplitWise.controller;

import com.alimurat.SplitWise.dto.AddUserToHouseDto;
import com.alimurat.SplitWise.dto.ExpenseSummary;
import com.alimurat.SplitWise.dto.HouseResponse;
import com.alimurat.SplitWise.dto.SaveHouseRequest;
import com.alimurat.SplitWise.model.House;
import com.alimurat.SplitWise.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/house")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseResponse> getHouseById(@PathVariable Integer id){
        return ResponseEntity.ok(houseService.getHouseById(id));
    }

    @GetMapping
    public ResponseEntity<List<House>> getHouses(){
        return ResponseEntity.ok(houseService.getHouses());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<HouseResponse> getHouseOfUser(@PathVariable String email){
        return ResponseEntity.ok(houseService.getHouseOfUser(email));
    }

    @PostMapping
    public ResponseEntity<HouseResponse> saveHouse(@RequestBody SaveHouseRequest request){
        return ResponseEntity.ok(houseService.saveHouse(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseResponse> updateHouse(@PathVariable Integer id, @RequestBody SaveHouseRequest request){
        return ResponseEntity.ok(houseService.updateHouse(id, request));
    }

    @PostMapping("/{id}")
    public ResponseEntity<HouseResponse> addUserToHouse(@PathVariable Integer id, @RequestBody AddUserToHouseDto request){
        return ResponseEntity.ok(houseService.addUserToHouse(id, request.getEmail()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HouseResponse> deleteUserFromHouse(@PathVariable Integer id, @RequestBody AddUserToHouseDto request){
        return ResponseEntity.ok(houseService.deleteUserFromHouse(id, request.getEmail()));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<ExpenseSummary> summaryOfHouse(@PathVariable Integer id){
        return ResponseEntity.ok(houseService.expenseSummaryOfHouse(id));
    }
}
