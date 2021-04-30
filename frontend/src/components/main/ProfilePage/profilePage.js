import React from "react";
import "./profilePage.css";
import Navigation from "./Parts/Navigation/navigation";
import UserPerformance from "./Parts/User/userPerfomance";
import {Switch} from "react-router-dom";

class ProfilePage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="profile_container">
                <div className="nav_container">
                    <Navigation/>
                </div>
                <div className="main_container">

                    <UserPerformance/>
                </div>
            </div>
        );
    }
}

export default ProfilePage;