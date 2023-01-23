package com.alimurat.SplitWise.service;

import com.alimurat.SplitWise.dto.*;
import com.alimurat.SplitWise.model.Expense;
import com.alimurat.SplitWise.model.ExpenseType;
import com.alimurat.SplitWise.model.House;
import com.alimurat.SplitWise.model.User;
import com.alimurat.SplitWise.repository.HouseRepository;
import com.alimurat.SplitWise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    public HouseService(HouseRepository houseRepository, UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    public HouseResponse getHouseById(Integer id){
        House house = houseRepository.findById(id).orElseThrow(RuntimeException::new);

        return HouseResponse.builder()
                .id(house.getId())
                .name(house.getName())
                .residentNumber(house.getResidentNumber())
                .users(house.getUsers())
                .build();
    }

    public HouseResponse getHouseOfUser(String email){

        List<House> houses = getHouses();

        for (int i = 0; i < houses.size(); i++) {
            House house = houses.get(i);
            List<User> userList = house.getUsers();

            for (int j = 0; j < userList.size(); j++) {
                User user = userList.get(j);
                if(user.getEmail().equals(email)){
                     return HouseResponse.builder()
                            .id(house.getId())
                            .name(house.getName())
                            .residentNumber(house.getResidentNumber())
                            .users(house.getUsers())
                            .build();
                }
            }
        }

        return null;
    }

    public List<House> getHouses(){
        List<House> houses = new ArrayList<>();
        houseRepository.findAll().forEach(houses::add);

        return houses;
    }

    public HouseResponse saveHouse(SaveHouseRequest request){

        House house = House.builder()
                .name(request.getName())
                .residentNumber(request.getResidentNumber())
                .build();

        House fromDb = houseRepository.save(house);
        
        return HouseResponse.builder()
                .id(fromDb.getId())
                .name(fromDb.getName())
                .residentNumber(fromDb.getResidentNumber())
                .users(fromDb.getUsers())
                .build();
    }

    public HouseResponse addUserToHouse(Integer id, String email){

        House house = houseRepository.findById(id).orElseThrow(RuntimeException::new);
        User user = userRepository.findByEmail(email);

        if(house.getUsers().size() <= house.getResidentNumber()){

            user.setHouse(house);
            userRepository.save(user);

            house.getUsers().add(user);
            houseRepository.save(house);
        }

        return HouseResponse.builder()
                .id(house.getId())
                .name(house.getName())
                .residentNumber(house.getResidentNumber())
                .users(house.getUsers())
                .build();
    }

    public HouseResponse updateHouse(Integer id, SaveHouseRequest request){

        House currentHouse = houseRepository.findById(id).orElseThrow(RuntimeException::new);

        if(currentHouse.getUsers().size() == 0){
            throw new RuntimeException("The house does not have any users");
        }

        currentHouse.setName(request.getName());
        currentHouse.setResidentNumber(request.getResidentNumber());

        houseRepository.save(currentHouse);

        return HouseResponse.builder()
                .id(currentHouse.getId())
                .name(currentHouse.getName())
                .residentNumber(currentHouse.getResidentNumber())
                .users(currentHouse.getUsers())
                .build();
    }

    public ExpenseSummary expenseSummaryOfHouse(Integer id){
        House house = houseRepository.findById(id).orElseThrow(RuntimeException::new);

        List<User> userList = house.getUsers();
        Float sumBill = 0F;
        Float sumGrocery = 0F;
        Float sumRent = 0F;
        Float sumMiscellaneous = 0F;

        for (int i = 0; i < userList.size(); i++) {
            List<Expense> expenseList = userList.get(i).getExpenses();

            for (int j = 0; j < expenseList.size(); j++) {
                if(expenseList.get(j).getExpenseType() == ExpenseType.BILL){
                    sumBill += expenseList.get(j).getAmount();
                }
                else if(expenseList.get(j).getExpenseType() == ExpenseType.GROCERY){
                    sumGrocery += expenseList.get(j).getAmount();
                }
                else if(expenseList.get(j).getExpenseType() == ExpenseType.RENT){
                    sumRent += expenseList.get(j).getAmount();
                }
                else if(expenseList.get(j).getExpenseType() == ExpenseType.MISCELLANEOUS){
                    sumMiscellaneous += expenseList.get(j).getAmount();
                }
            }
        }

        return ExpenseSummary.builder()
                .bill(sumBill)
                .grocery(sumGrocery)
                .rent(sumRent)
                .miscellaneous(sumMiscellaneous)
                .build();
    }

    public HouseResponse deleteUserFromHouse(Integer id, String email){
        House house = houseRepository.findById(id).orElseThrow(RuntimeException::new);
        User user = userRepository.findByEmail(email);

        user.setHouse(null);
        userRepository.save(user);

        house.getUsers().remove(user);
        houseRepository.save(house);

        return HouseResponse.builder()
                .id(house.getId())
                .name(house.getName())
                .residentNumber(house.getResidentNumber())
                .users(house.getUsers())
                .build();
    }
}
