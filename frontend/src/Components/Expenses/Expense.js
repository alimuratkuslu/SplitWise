import React, { useState, useEffect } from 'react';
import { Button, ButtonGroup } from 'reactstrap';
import { Link } from 'react-router-dom';
import './Expense.css';

const Expense = ({expense}) => {
    const [showMore, setShowMore] = useState(false);

    const handleDelete = (id) => {
        fetch(`/expense/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    return (
        <div className='more-info'>
            <div className='desc-info'>
                {expense.description}
                <button className='desc-button' onClick={() => setShowMore(!showMore)}>
                    {showMore ? 'Show Less' : 'Show More'}
                </button>
            </div>
            {showMore && (
                <div className='test'>
                    <div>Amount: {expense.amount} ₺</div>
                    <div>Description: {expense.description}</div>
                    <div>Date: {expense.date}</div>
                    <div>Category: {expense.expenseType}</div>
                    <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/expense/" + expense.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => handleDelete(expense.id)}>Delete</Button>
                    </ButtonGroup>
                    </td>
                </div>
            )}
        </div>
    );
  };

  export default Expense;

