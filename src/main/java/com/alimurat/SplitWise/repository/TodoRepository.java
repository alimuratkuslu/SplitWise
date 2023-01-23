package com.alimurat.SplitWise.repository;

import com.alimurat.SplitWise.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
