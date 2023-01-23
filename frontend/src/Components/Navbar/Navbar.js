import React from "react";
import { NavLink } from "react-router-dom";
import './Navbar.css';

const Navbar = () => {
  return (
    <nav>
      <div className="nav-left">
        <NavLink to="/" className="nav-item">
          SplitWise
        </NavLink>
      </div>
      <div className="nav-right">
      <NavLink to="/house" className="nav-item">
          My House
        </NavLink>
      <NavLink to="/expense" className="nav-item">
          My Expenses
        </NavLink>
      <NavLink to="/todo" className="nav-item">
          My Todos
        </NavLink>
      <NavLink to="/profile" className="nav-item">
          Profile
        </NavLink>
        <NavLink to="/about" className="nav-item">
          About
        </NavLink>
        <NavLink to="/logout" className="nav-item">
          Logout
        </NavLink>
      </div>
    </nav>
  );
};

export default Navbar;
