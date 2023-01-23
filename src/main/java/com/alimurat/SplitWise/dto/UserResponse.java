package com.alimurat.SplitWise.dto;

import com.alimurat.SplitWise.model.Expense;
import com.alimurat.SplitWise.model.House;
import com.alimurat.SplitWise.model.Role;
import com.alimurat.SplitWise.model.Todo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {

    private Integer id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private Boolean isActive;

    private Role role;
    private House house;
    private List<Expense> expenses;
    private List<Todo> todos;
}
