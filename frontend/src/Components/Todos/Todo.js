import React, { useState, useEffect } from 'react';
import { Button, ButtonGroup } from 'reactstrap';
import jwt_decode from 'jwt-decode';
import { Link } from 'react-router-dom';
import './Todo.css';

const Todo = ({todo}) => {
    const [showMore, setShowMore] = useState(false);
    const [completed, setCompleted] = useState(todo.checked);

    const handleCheckboxClick = async () => {
        setCompleted(!completed);
        try {
          const response = await fetch(`/todo/${todo.id}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ checked: !completed }),
          });

          const data = await response.json();
          console.log(data);

        } catch (err) {
          console.error(err);
        }
      };

      const handleDelete = (id) => {
        const data = localStorage.getItem('token');
        const decoded = jwt_decode(data);
        const userEmail = decoded.sub;
        console.log(userEmail);


        fetch(`/todo/user/${id}`, {
            method: 'DELETE',
            body: JSON.stringify({email : userEmail}),
            headers: { 'Content-Type': 'application/json' },
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
                {todo.description}
                <button className='desc-button' onClick={() => setShowMore(!showMore)}>
                    {showMore ? 'Show Less' : 'Show More'}
                </button>
            </div>
            {showMore && (
                <div className='test'>
                    <div>Description: {todo.description}</div>
                    <input
                        type="checkbox"
                        checked={completed}
                        onClick={handleCheckboxClick}
                    />
                    <span>Done</span>
                    <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/todo/" + todo.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => handleDelete(todo.id)}>Delete</Button>
                    </ButtonGroup>
                    </td>
                    <br />
                    <br />
                </div>
            )}
        </div>
    );
  };

  export default Todo;

