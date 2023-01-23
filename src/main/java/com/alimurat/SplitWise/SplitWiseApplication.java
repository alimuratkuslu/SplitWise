package com.alimurat.SplitWise;

import com.alimurat.SplitWise.model.*;
import com.alimurat.SplitWise.repository.ExpenseRepository;
import com.alimurat.SplitWise.repository.HouseRepository;
import com.alimurat.SplitWise.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SplitWiseApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final HouseRepository houseRepository;
	private final ExpenseRepository expenseRepository;

	private final PasswordEncoder passwordEncoder;

	public SplitWiseApplication(UserRepository userRepository, HouseRepository houseRepository, ExpenseRepository expenseRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.houseRepository = houseRepository;
		this.expenseRepository = expenseRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(SplitWiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user1 = User.builder()
				.id(1)
				.name("Ali Murat")
				.surname("Kuşlu")
				.email("ali@gmail.com")
				.password(passwordEncoder.encode("alipassword"))
				.role(Role.ADMIN)
				.isActive(true)
				.build();

		User user2 = User.builder()
				.id(2)
				.name("Şükran")
				.surname("Atan")
				.email("sukran@gmail.com")
				.password(passwordEncoder.encode("sukranpassword"))
				.role(Role.USER)
				.isActive(true)
				.build();

		User user3 = User.builder()
				.id(3)
				.name("Doğan")
				.surname("Kuşlu")
				.email("dogan@gmail.com")
				.password(passwordEncoder.encode("doganpassword"))
				.role(Role.USER)
				.isActive(true)
				.build();

		User user4 = User.builder()
				.id(4)
				.name("Ege")
				.surname("Çelik")
				.email("ege@gmail.com")
				.password(passwordEncoder.encode("egepassword"))
				.role(Role.USER)
				.isActive(true)
				.build();

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);

		House house1 = House.builder()
				.id(1)
				.name("Kısmetse Olur")
				.residentNumber(4)
				.build();

		houseRepository.save(house1);


		Expense expense1 = Expense.builder()
				.id(1)
				.amount(47.50F)
				.description("Market Alışverişi")
				.date(LocalDate.now())
				.expenseType(ExpenseType.GROCERY)
				.build();

		Expense expense2 = Expense.builder()
				.id(2)
				.amount(350F)
				.description("Elektrik Faturası")
				.date(LocalDate.now())
				.expenseType(ExpenseType.BILL)
				.build();

		Expense expense3 = Expense.builder()
				.id(3)
				.amount(1500F)
				.description("Aralık Ayı Kirası")
				.date(LocalDate.now())
				.expenseType(ExpenseType.RENT)
				.build();

		Expense expense4 = Expense.builder()
				.id(4)
				.amount(27.50F)
				.description("Ütü masası")
				.date(LocalDate.now())
				.expenseType(ExpenseType.MISCELLANEOUS)
				.build();

		expenseRepository.save(expense1);
		expenseRepository.save(expense2);
		expenseRepository.save(expense3);
		expenseRepository.save(expense4);

		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user3);
		System.out.println(user4);
		System.out.println(house1);
		System.out.println(expense1);
		System.out.println(expense2);
		System.out.println(expense3);
		System.out.println(expense4);

	}
}
