package com.alimurat.SplitWise.service;

import com.alimurat.SplitWise.dto.SaveTodoRequest;
import com.alimurat.SplitWise.dto.TodoResponse;
import com.alimurat.SplitWise.model.Todo;
import com.alimurat.SplitWise.model.User;
import com.alimurat.SplitWise.repository.TodoRepository;
import com.alimurat.SplitWise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public TodoResponse getTodoById(Integer id){
        Todo todo = todoRepository.findById(id).orElseThrow(RuntimeException::new);

        return TodoResponse.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .checked(todo.getChecked())
                .user(todo.getUser())
                .build();
    }

    public List<Todo> getTodos(){
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);

        return todos;
    }

    public TodoResponse saveTodo(SaveTodoRequest request){

        Todo todo = Todo.builder()
                .description(request.getDescription())
                .checked(false)
                .build();

        Todo fromDb = todoRepository.save(todo);

        return TodoResponse.builder()
                .id(fromDb.getId())
                .description(fromDb.getDescription())
                .checked(fromDb.getChecked())
                .user(fromDb.getUser())
                .build();
    }

    public TodoResponse updateTodo(Integer id, SaveTodoRequest request){

        Todo currentTodo = todoRepository.findById(id).orElseThrow(RuntimeException::new);

        currentTodo.setDescription(request.getDescription());
        currentTodo.setChecked(request.getChecked());

        todoRepository.save(currentTodo);

        return TodoResponse.builder()
                .id(currentTodo.getId())
                .description(currentTodo.getDescription())
                .checked(currentTodo.getChecked())
                .user(currentTodo.getUser())
                .build();
    }

    public TodoResponse addTodoToUser(Integer id, String email){

        Todo todo = todoRepository.findById(id).orElseThrow(RuntimeException::new);
        User user = userRepository.findByEmail(email);

        if(!todo.getChecked()){

            user.getTodos().add(todo);
            userRepository.save(user);

            todo.setUser(user);
            todoRepository.save(todo);
        }

        return TodoResponse.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .checked(todo.getChecked())
                .user(todo.getUser())
                .build();
    }

    public TodoResponse deleteTodoFromUser(Integer id, String email){

        Todo todo = todoRepository.findById(id).orElseThrow(RuntimeException::new);
        User user = userRepository.findByEmail(email);

        user.getTodos().remove(todo);
        userRepository.save(user);

        todo.setUser(null);
        todoRepository.save(todo);

        return TodoResponse.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .checked(todo.getChecked())
                .user(todo.getUser())
                .build();
    }

    public void checkTodo(Integer id){
        changeTodoStatus(id, true);
    }

    public void uncheckTodo(Integer id){
        changeTodoStatus(id, false);
    }

    private void changeTodoStatus(Integer id, Boolean checked){
        Todo currentTodo = todoRepository.findById(id).orElseThrow(RuntimeException::new);

        currentTodo.setChecked(checked);
        todoRepository.save(currentTodo);
    }

    public void deleteTodoById(Integer id){

        todoRepository.deleteById(id);
    }
}
