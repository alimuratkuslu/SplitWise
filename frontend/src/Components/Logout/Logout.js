import React from 'react';
import { useHistory } from 'react-router-dom';
import './Logout.css';

const Logout = () => {
    const history = useHistory();

    const handleClick = () => {
        localStorage.removeItem('token');
        history.push('/auth/login');
        history.go('/auth/login');
    }

    return (
        <div className="logout-container">
            <h1>Are you sure you want to logout?</h1>
            <button onClick={handleClick}>Logout</button>
        </div>
    );
}

export default Logout;