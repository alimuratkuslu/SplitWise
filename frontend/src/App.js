import './App.css';
import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import LoginPage from './Components/Login/LoginPage';
import PrivateRoute from './Components/Utils/PrivateRoute';
import { Switch } from 'react-router-dom';
import Profile from './Components/Profile/Profile';
import Logout from './Components/Logout/Logout';
import Home from './Components/Home/Home';
import ExpenseList from './Components/Expenses/ExpenseList';
import ExpenseEdit from './Components/Expenses/ExpenseEdit.js';
import TodoList from './Components/Todos/TodoList';
import TodoEdit from './Components/Todos/TodoEdit.js';
import HouseList from './Components/House/HouseList.js';
import { Redirect } from 'react-router-dom';

function App() {

  return (
    <Router>
      <Switch>
          <Route path='/auth/login' component={LoginPage} />
          <PrivateRoute exact path='/' component={Home} />
          <PrivateRoute exact path='/expense' component={ExpenseList} />
          <PrivateRoute exact path='/expense/:id' component={ExpenseEdit} />
          <PrivateRoute exact path='/todo' component={TodoList} />
          <PrivateRoute exact path='/todo/:id' component={TodoEdit} />
          <PrivateRoute exact path='/house' component={HouseList} />
          <PrivateRoute exact path='/profile' component={Profile} />
          <Route exact path='/logout' component={Logout} />
          <Redirect to='/auth/login' />
      </Switch>
    </Router>
  );
}

export default App;