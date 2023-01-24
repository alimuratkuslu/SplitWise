import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../Navbar/Navbar';
import jwt_decode from 'jwt-decode';
import './HouseList.css';
import { PieChart, Pie, Legend, Tooltip, ResponsiveContainer } from 'recharts';

const HouseList = () => {

    const [user, setUser] = useState({});
    const [residentNo, setResidentNo] = useState({});
    const [houseUsers, setHouseUsers] = useState({});
    const [expenses, setExpenses] = useState([]);
    const [loading, setLoading] = useState(true);


    useEffect(() => {
        const data = localStorage.getItem('token');
        console.log(localStorage.getItem('token'));

        if (data) {
          const decoded = jwt_decode(data);
          const userEmail = decoded.sub;
          // console.log('email is :', userEmail);
          
          axios.get(`/house/user/${userEmail}`)
            .then(response => {
              setUser(response.data);
              
              setResidentNo(response.data.residentNumber);
              setHouseUsers(response.data.users);

              axios.get(`/house/summary/${response.data.id}`)
              .then(response => {
                console.log(response.data);
                const keys = Object.keys(response.data);
                const values = Object.values(response.data);

                const data = [
                  { name: keys[0], value: values[0]},
                  { name: keys[1], value: values[1]},
                  { name: keys[2], value: values[2]},
                  { name: keys[3], value: values[3]},
                ];

                setExpenses(data);
                console.log(expenses); 
              })
              .catch(error => {
                console.log(error);
              });
              
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
            <div className='piechart'>
            <PieChart width={400} height={400}>
              <Pie
                dataKey="value"
                isAnimationActive={false}
                data={expenses}
                cx="50%"
                cy="50%"
                outerRadius={80}
                fill="#8884d8"
                label
              />
              <Tooltip />
            </PieChart>
            </div>
            <div className='resNo'>
              <h2>Resident Number: {residentNo}</h2>
            </div>

            <div className='expenses'>
            <li key={expenses.id}>
                <div>
                <div>Bills: {expenses.bill} </div>
                <div>Groceries: {expenses.grocery}</div>
                <div>Rent: {expenses.rent}</div>
                <div>Miscellaneous: {expenses.miscellaneous}</div>
            </div>
            </li>
            </div>
            

            <div className='users'>
              <ul>
              {houseUsers.map((user) => (
                  <li key={user.id}>
                      <div className='test'>
                      <div>Name: {user.name} </div>
                      <div>Surname: {user.surname}</div>
                      <div>Email: {user.email}</div>
                      <br />
                      <br />
                  </div>
                  </li>
              ))}
              </ul>
            </div>
        </div>
    );
}

export default HouseList;