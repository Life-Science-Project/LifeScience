import React from "react";
import {Link} from "react-router-dom";
import registerIcon from "../../logos/register_icon.svg";
import "./header.css";
import PropTypes from "prop-types";

const Logged = (props) => {
    return(
        <div className="d-flex justify-content-between">
            <Link to="/profile">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">{`${props.user.firstName} ${props.user.lastName}`}</div>
                    {/*TODO: proper userpage icon*/}
                    <img src={registerIcon} className="header__group_icon" alt="reg"/>
                </div>
            </Link>
            <div className="d-flex align-items-center header__group">
                <button className="d-flex align-items-center header__group" onClick={props.logoutUser}>
                    Log out
                </button>
            </div>
        </div>
    );
}

Logged.propTypes = {
    user: PropTypes.shape({
        id: PropTypes.number.isRequired,
        firstName: PropTypes.string.isRequired,
        lastName: PropTypes.string.isRequired
    }).isRequired
}

export default Logged;
