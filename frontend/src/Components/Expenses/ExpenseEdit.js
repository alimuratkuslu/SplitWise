import React, { Component } from 'react';
import { useState, useEffect } from 'react';
import { Link, withRouter } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../Navbar/Navbar';
import jwt_decode from 'jwt-decode';
import './ExpenseEdit.css';

const ExpenseEdit = () => {

    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(true);
    const [amount, setAmount] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState('');
    const [type, setType] = useState('');

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

    const addExpenseToUser = async (expenseId) => {
        try {
          const response = await fetch(`/expense/${expenseId}`, {
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
          const response = await fetch('/expense', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ amount: amount, description: description, date: date, expenseType: type }),
          });

          const data = await response.json();
          console.log(data);
          setAmount('');
          setDescription('');
          setDate('');
          setType('');

          const saveToUser = await fetch(`/expense/${data.id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: user.email }),
          });
          const newData = await saveToUser.json();
          console.log(newData);

          addExpenseToUser(data.id)
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
                placeholder="Amount"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
            />
            <br />
            <br />
            <input
                type="text"
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />
            <br />
            <br />
            <input
                type="text"
                placeholder="Date"
                value={date}
                onChange={(e) => setDate(e.target.value)}
            />
            <br />
            <br />
            <input
                type="text"
                placeholder="Type"
                value={type}
                // Add dropdown for Expense Types
                onChange={(e) => setType(e.target.value)}
            />
            <button type="submit">Add</button>
            </form>
        </div>
      );

    
}

export default withRouter(ExpenseEdit);