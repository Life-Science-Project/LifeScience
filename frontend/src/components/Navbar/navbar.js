import React from "react";
import './navbar.css';
import NavLink from "./NavLink/navlink";
import SearchForm from "./search-form";

const Navbar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <ul className="navbar-nav">
                <NavLink path="/" name="Home"/>
                <NavLink path="/categories" name="Categories"/>
            </ul>
            <SearchForm/>
        </nav>
    );
}

export default Navbar;
