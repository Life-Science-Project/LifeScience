import React from "react";
import './navbar.css';
import NavLink from "./NavLink/navlink";
import SearchForm from "./search-form";

const Navbar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav">
                    {/*<NavLink path="/">*/}
                    {/*    <a className="nav-link" href="#">Home <span className="sr-only">(current)</span></a>*/}
                    {/*</NavLink>*/}
                    <NavLink path="/" name="Home"/>
                    <NavLink path="/categories" name="Categories"/>
                </ul>
                <SearchForm/>
            </div>
        </nav>
    );
}

export default Navbar;