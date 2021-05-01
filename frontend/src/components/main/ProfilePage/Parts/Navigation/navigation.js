import React from "react";
import mail from "../../../../../logos/mail_icon.png"
import friends from "../../../../../logos/friends_icon.png"
import saved from "../../../../../logos/saving_icon.png"
import {NavLink} from "react-router-dom";
import "./navigation.css"

const Navigation = () => {
    const url = "/profile";

    return(
        <div className="navigation_container">
            <ul className="navigation_list">
                <li>
                    <NavLink exact activeClassName="active-route" className="rout" to={url}>
                        Me
                    </NavLink>
                </li>
                <li>
                    <NavLink exact activeClassName="active-route" className="rout" to={url + "/mail"}>
                        <img src={mail} alt="mail icon"/> Mail
                    </NavLink>
                </li>
                <li>
                    <NavLink exact activeClassName="active-route" className="rout" to={url + "/friends"}>
                        <img src={friends} alt="friends icon"/> Friends
                    </NavLink>
                </li>
                <li>
                    <NavLink exact activeClassName="active-route" className="rout" to={url + "/favourites"}>
                        <img src={saved} alt="saving icon"/> Protocols
                    </NavLink>
                </li>
            </ul>
        </div>
    );
}

export default Navigation;
