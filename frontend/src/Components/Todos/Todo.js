import React, { useState, useEffect } from 'react';
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
                    <br />
                    <br />
                </div>
            )}
        </div>
    );
  };

  export default Todo;

