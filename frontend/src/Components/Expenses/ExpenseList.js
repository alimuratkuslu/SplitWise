import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';
import axios from 'axios';
import Navbar from '../Navbar/Navbar';
import jwt_decode from 'jwt-decode';
import Expense from './Expense';
import './ExpenseList.css';

const ExpenseList = () => {

    const [user, setUser] = useState({});
    const [expenses, setExpenses] = useState({});
    const [date, setDate] = useState('');
    const [type, setType] = useState('');
    const [dateFiltered, setDateFiltered] = useState([]);
    const [typeFiltered, setTypeFiltered] = useState([]);
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

    const handleDateChange = event => {
      setDate(event.target.value);
    };

    const handleTypeChange = event => {
      setType(event.target.value);
    };

    const handleSubmitDate = async event => {
      event.preventDefault();
      try {
        const userEmail = { email : user.email};
        const responseDate = await axios.post(`/expense/filter/date/${date}`, userEmail);
        console.log(responseDate);
        setDateFiltered(responseDate.data);
      } catch (error) {
        console.error(error);
      }
    };

    const handleSubmitType = async event => {
      event.preventDefault();
      try {
        const userEmail = { email : user.email};
        const responseType = await axios.post(`/expense/filter/type/${type}`, userEmail);
        setDateFiltered(responseType.data);
      } catch (error) {
        console.error(error);
      }
    };

    if (loading) {
      return <p>Loading...</p>
    }
    
    return (
        <div>
            <Navbar />
            <br />
            <br />
            <div className='filter'>
            <form onSubmit={handleSubmitDate} className='form'>
              <label>
                Date:
                <input type="text" value={date} placeholder="YYYY-MM-DD" onChange={handleDateChange} />
              </label>
              <button type="submit">Filter</button>
            </form>
            <form onSubmit={handleSubmitType} className='type'>
              <label>
                Expense Type:
                <select value={type} onChange={handleTypeChange}>
                  <option value="BILL">BILL</option>
                  <option value="GROCERY">GROCERY</option>
                  <option value="RENT">RENT</option>
                  <option value="MISCELLANEOUS">MISCELLANEOUS</option>
                </select>
              </label>
              <button type="submit">Filter</button>
            </form>
            <table className='table'>
              <thead>
                <tr>
                  <th>Type</th>
                  <th>Amount</th>
                  <th>Description</th>
                </tr>
              </thead>
              <tbody>
                {dateFiltered.map(expense => (
                  <tr key={expense.id}>
                    <td>{expense.expenseType}</td>
                    <td>{expense.amount}</td>
                    <td>{expense.description}</td>
                  </tr>
                ))}
              </tbody>
            </table>

            </div>
            
            <div className='todo'>
              <div className="float-right">
                  <Button color="success" tag={Link} to="/expense/save">Add Todo</Button>
              </div>
              {expenses.map(expense => (
                  <Expense key={expense.id} expense={expense} />
              ))}
            </div>
        </div>
    );
}

export default ExpenseList;

// <input type="text" value={type} placeholder="Expense Type" onChange={handleTypeChange} />
