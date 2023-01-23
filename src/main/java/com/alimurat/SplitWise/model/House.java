package com.alimurat.SplitWise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private Integer residentNumber;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private List<User> users;
}
