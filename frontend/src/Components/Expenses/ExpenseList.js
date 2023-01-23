import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';
import axios from 'axios';
import Navbar from '../Navbar/Navbar';
import jwt_decode from 'jwt-decode';
import Expense from './Expense';

const ExpenseList = () => {

    const [user, setUser] = useState({});
    const [expenses, setExpenses] = useState({});
    const [loading, setLoading] = useState(true);

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
              setExpenses(response.data.expenses)
              setLoading(false);
            })
            .catch(error => {
              console.log(error);
            });
        }
    }, []);

    if (loading) {
      return <p>Loading...</p>
    }
    
    return (
        <div>
            <Navbar />
            <br />
            <br />
            <div className="float-right">
                <Button color="success" tag={Link} to="/expense/save">Add Todo</Button>
            </div>
            {expenses.map(expense => (
                <Expense key={expense.id} expense={expense} />
            ))}
        </div>
    );
}

export default ExpenseList;
