import React, {Fragment, useEffect, useState} from "react";
import {Link, BrowserRouter as Router} from "react-router-dom";
import './header.css'
import registerIcon from '../../logos/register_icon.svg'
import loginIcon from '../../logos/login_icon.svg'

const Header = (props) => {
    // const [authBlock, setAuthBlock] = useState((
    //     <div className="d-flex justify-content-between">
    //         <Link to="/login">
    //             <div className="d-flex align-items-center header__group">
    //                 <div className="header__group_link p-2 bd-highlight">Login</div>
    //                 <img src={loginIcon} className="header__group_icon"/>
    //             </div>
    //         </Link>
    //         <Link to="/register">
    //             <div className="d-flex align-items-center header__group">
    //                 <div className="header__group_link p-2 bd-highlight">Register</div>
    //                 <img src={registerIcon} className="header__group_icon"/>
    //             </div>
    //         </Link>
    //     </div>)
    // );

    console.log(props);
    let authBlock;
    if (Object.entries(props.user).length === 0) {
        authBlock = (<div className="d-flex justify-content-between">
            <Link to="/login">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">Login</div>
                    <img src={loginIcon} className="header__group_icon"/>
                </div>
            </Link>
            <Link to="/register">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">Register</div>
                    <img src={registerIcon} className="header__group_icon"/>
                </div>
            </Link>
        </div>);
    } else {
        authBlock = (<div className="d-flex justify-content-between">
            <Link to="/register">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">{props.user.username}</div>
                    <img src={registerIcon} className="header__group_icon"/>
                </div>
            </Link>
        </div>);
    }


    return (
        <div className="d-flex align-items-center justify-content-between">
            <div className="header__logo_text">Life Science</div>
            {authBlock}
        </div>
    )
        ;
}

export default Header;