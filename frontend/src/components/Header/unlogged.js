import React from "react";
import {Link} from "react-router-dom";
import registerIcon from "../../logos/register_icon.svg";
import "./header.css";
import loginIcon from "../../logos/login_icon.svg";

const UnLogged = (props) => {
    return (
        <div className="d-flex justify-content-between">
            <Link to="/login">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">Login</div>
                    <img src={loginIcon} className="header__group_icon" alt="login"/>
                </div>
            </Link>
            <Link to="/register">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">Register</div>
                    <img src={registerIcon} className="header__group_icon" alt="reg"/>
                </div>
            </Link>
        </div>
    );
}

export default UnLogged;