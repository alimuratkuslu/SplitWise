package com.alimurat.SplitWise.service;

import com.alimurat.SplitWise.dto.ExpenseResponse;
import com.alimurat.SplitWise.dto.ExpenseSummary;
import com.alimurat.SplitWise.dto.SaveExpenseRequest;
import com.alimurat.SplitWise.model.Expense;
import com.alimurat.SplitWise.model.ExpenseType;
import com.alimurat.SplitWise.model.House;
import com.alimurat.SplitWise.model.User;
import com.alimurat.SplitWise.repository.ExpenseRepository;
import com.alimurat.SplitWise.repository.HouseRepository;
import com.alimurat.SplitWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private JavaMailSender mailSender;
    private final HouseRepository houseRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository,
                          HouseRepository houseRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }

    public void start(){
        scheduler.scheduleAtFixedRate(this::checkExpenses, 0, 1, TimeUnit.DAYS);
    }

    public void checkExpenses(){

        List<Expense> expenseList = expenseRepository.findAll();

        for (int i = 0; i < expenseList.size(); i++) {
            LocalDate dueDate = expenseList.get(i).getDate();
            LocalDate now = LocalDate.now();
            if(dueDate.isBefore(now.plusWeeks(1)) && dueDate.isAfter(now)){
                sendAlert(expenseList.get(i));
            }
        }
    }

    public void sendAlert(Expense expense){
        User user = expense.getUser();
        String email = user.getEmail();

        String subject = "Upcoming expense payment: " + expense.getExpenseType();

        String message = "A payment for the expense of type " + expense.getExpenseType()
                + " with amount " + expense.getAmount() + " is due on " + expense.getDate().toString()
                + ". Please make sure to make the payment on time.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public ExpenseResponse getExpenseById(Integer id){
        Expense expense = expenseRepository.findById(id).orElseThrow(RuntimeException::new);

        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .expenseType(expense.getExpenseType())
                .user(expense.getUser())
                .build();
    }

    public List<Expense> getExpenses(){
        List<Expense> expenses = new ArrayList<>();
        expenseRepository.findAll().forEach(expenses::add);

        return expenses;
    }

    public ExpenseResponse saveExpense(SaveExpenseRequest request){

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(LocalDate.now())
                .expenseType(request.getExpenseType())
                .build();

        Expense fromDb = expenseRepository.save(expense);

        return ExpenseResponse.builder()
                .id(fromDb.getId())
                .amount(fromDb.getAmount())
                .description(fromDb.getDescription())
                .date(LocalDate.now())
                .expenseType(fromDb.getExpenseType())
                .user(fromDb.getUser())
                .build();
    }

    public ExpenseResponse addFutureExpense(SaveExpenseRequest request){

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .expenseType(request.getExpenseType())
                .build();

        Expense fromDb = expenseRepository.save(expense);


        return ExpenseResponse.builder()
                .id(fromDb.getId())
                .amount(fromDb.getAmount())
                .description(fromDb.getDescription())
                .date(fromDb.getDate())
                .expenseType(fromDb.getExpenseType())
                .user(fromDb.getUser())
                .build();

    }

    public ExpenseResponse addExpenseToUser(Integer expenseId, String email){
        User user = userRepository.findByEmail(email);
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(RuntimeException::new);

        user.getExpenses().add(expense);
        userRepository.save(user);

        expense.setUser(user);
        expenseRepository.save(expense);

        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .expenseType(expense.getExpenseType())
                .user(expense.getUser())
                .build();
    }

    public ExpenseResponse updateExpense(Integer id, SaveExpenseRequest request){

        Expense currentExpense = expenseRepository.findById(id).orElseThrow(RuntimeException::new);

        currentExpense.setAmount(request.getAmount());
        currentExpense.setDescription(request.getDescription());
        currentExpense.setDate(request.getDate());
        currentExpense.setExpenseType(request.getExpenseType());

        expenseRepository.save(currentExpense);

        return ExpenseResponse.builder()
                .id(currentExpense.getId())
                .amount(currentExpense.getAmount())
                .description(currentExpense.getDescription())
                .date(currentExpense.getDate())
                .expenseType(currentExpense.getExpenseType())
                .user(currentExpense.getUser())
                .build();
    }

    public List<Expense> filterExpenseByDate(LocalDate date, String email){
        User user = userRepository.findByEmail(email);

        List<Expense> currentExpenses = user.getExpenses();

        List<Expense> expenseList = new ArrayList<>();

        for (int i = 0; i < currentExpenses.size(); i++) {
            if(currentExpenses.get(i).getDate().equals(date)){
                expenseList.add(currentExpenses.get(i));
            }
        }
        return expenseList;
    }

    public List<Expense> filterExpenseByType(ExpenseType type, String email){
        User user = userRepository.findByEmail(email);

        List<Expense> currentExpenses = user.getExpenses();

        List<Expense> expenseList = new ArrayList<>();

        for (int i = 0; i < currentExpenses.size(); i++) {
            if(currentExpenses.get(i).getExpenseType().equals(type)){
                expenseList.add(currentExpenses.get(i));
            }
        }

        return expenseList;
    }

    public void divideExpenses(Integer houseId){

        House house = houseRepository.findById(houseId).orElseThrow(RuntimeException::new);
        int resNumber = house.getResidentNumber();

        List<User> userList = house.getUsers();

        for (int i = 0; i < userList.size(); i++) {
            List<Expense> expenseList = userList.get(i).getExpenses();

            for (int j = 0; j < expenseList.size(); j++) {
                expenseList.get(j).setAmount(expenseList.get(j).getAmount() / resNumber);
            }
        }
    }

    public ExpenseSummary expenseSummaryOfUser(String email){

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new RuntimeException("User does not exist");
        }

        List<Expense> expenseList = user.getExpenses();
        Float sumBill = 0F;
        Float sumGrocery = 0F;
        Float sumRent = 0F;
        Float sumMiscellaneous = 0F;

        for (int i = 0; i < expenseList.size(); i++) {
            if(expenseList.get(i).getExpenseType() == ExpenseType.BILL){
                sumBill += expenseList.get(i).getAmount();
            }
            else if(expenseList.get(i).getExpenseType() == ExpenseType.GROCERY){
                sumGrocery += expenseList.get(i).getAmount();
            }
            else if(expenseList.get(i).getExpenseType() == ExpenseType.RENT){
                sumRent += expenseList.get(i).getAmount();
            }
            else if(expenseList.get(i).getExpenseType() == ExpenseType.MISCELLANEOUS){
                sumMiscellaneous += expenseList.get(i).getAmount();
            }
        }

        return ExpenseSummary.builder()
                .bill(sumBill)
                .grocery(sumGrocery)
                .rent(sumRent)
                .miscellaneous(sumMiscellaneous)
                .build();
    }

    public ExpenseResponse deleteExpenseFromUser(Integer expenseId, String email){
        User user = userRepository.findByEmail(email);
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(RuntimeException::new);

        user.getExpenses().remove(expense);
        userRepository.save(user);

        expense.setUser(null);
        expenseRepository.save(expense);

        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .expenseType(expense.getExpenseType())
                .user(expense.getUser())
                .build();
    }

    public void deleteExpenseById(Integer id){
        expenseRepository.deleteById(id);
    }
}
