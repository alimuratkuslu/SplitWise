package com.alimurat.SplitWise.controller;

import com.alimurat.SplitWise.dto.AddTodoToUserDto;
import com.alimurat.SplitWise.dto.SaveTodoRequest;
import com.alimurat.SplitWise.dto.TodoResponse;
import com.alimurat.SplitWise.model.Todo;
import com.alimurat.SplitWise.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Integer id){
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(){
        return ResponseEntity.ok(todoService.getTodos());
    }

    @PostMapping
    public ResponseEntity<TodoResponse> saveTodo(@RequestBody SaveTodoRequest request){
        return ResponseEntity.ok(todoService.saveTodo(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Integer id, @RequestBody SaveTodoRequest request){
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    @PostMapping("/{id}")
    public ResponseEntity<TodoResponse> addTodoToUser(@PathVariable Integer id, @RequestBody AddTodoToUserDto request){
        return ResponseEntity.ok(todoService.addTodoToUser(id, request.getEmail()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<TodoResponse> deleteTodoFromUser(@PathVariable Integer id, @RequestBody AddTodoToUserDto request){
        return ResponseEntity.ok(todoService.deleteTodoFromUser(id, request.getEmail()));
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Integer id){
        todoService.deleteTodoById(id);
    }

    @PatchMapping("/{id}")
    public void checkTodo(@PathVariable Integer id){
        todoService.checkTodo(id);
    }

    @PatchMapping("/uncheck/{id}")
    public void uncheckTodo(@PathVariable Integer id){
        todoService.uncheckTodo(id);
    }

}
