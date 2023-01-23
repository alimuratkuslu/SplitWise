import React, { Component } from 'react';
import { useState, useEffect } from 'react';
import { Link, withRouter } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../Navbar/Navbar';
import jwt_decode from 'jwt-decode';
import './TodoEdit.css';

const TodoEdit = () => {

    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(true);
    const [description, setDescription] = useState('');
    const [checked, setChecked] = useState(false);

    useEffect(() => {
        const data = localStorage.getItem('token');
        
        if (data) {
          const decoded = jwt_decode(data);
          const userEmail = decoded.sub;
          console.log('email is :', userEmail);
          
          axios.get(`/user/details/${userEmail}`)
            .then(response => {
              console.log(response.data);
              setUser(response.data);
              // setTodos(response.data.todos)
              setLoading(false);
            })
            .catch(error => {
              console.log(error);
            });
        }
    }, []);

    const addTodoToUser = async (todoId) => {
        try {
          const response = await fetch(`/todo/${todoId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({  }),
          });
        } catch (err) {
          console.error(err);
        }
      };

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        try {
          const response = await fetch('/todo', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ description: description, checked: checked }),
          });

          const data = await response.json();
          console.log(data);
          setDescription('');
          setChecked(false);

          const saveToUser = await fetch(`/todo/${data.id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: user.email }),
          });
          const newData = await saveToUser.json();
          console.log(newData);

          addTodoToUser(data.id)
        } catch (err) {
          console.error(err);
        }
    };

    return (
      <div>
        <Navbar />
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Add Todo"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
          <br />
          <br />
          Done
          <input
            type="checkbox"
            checked={checked}
            onClick={() => setChecked(!checked)}
        />
          <button type="submit">Add</button>
        </form>
      </div>
      );

    
}

export default withRouter(TodoEdit);