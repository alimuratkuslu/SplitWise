import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../Navbar/Navbar';
import jwt_decode from 'jwt-decode';
import './Profile.css';

const Profile = (props) => {
    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const data = localStorage.getItem('token');

        console.log(jwt_decode(data).sub);
        
        if (data) {
          const decoded = jwt_decode(data);
          const userEmail = decoded.sub;
          console.log('email is :', userEmail);
          
          axios.get(`/user/details/${userEmail}`)
            .then(response => {
              console.log(response.data);
              setUser(response.data);
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
            <div className='profile-container'>
              <div className='profile-header'>
                <h1>Welcome, {user.name} {user.surname}!</h1>
              </div>

              <div className='profile-info'>
                <h2>About Me</h2>
                <br />

                <p>Name: {user.name}</p>
                <p>Surname: {user.surname}</p>
                <p>Email: {user.email}</p>
              </div>
            </div>
        </div>
    );
};

export default Profile;