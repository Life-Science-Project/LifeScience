import React from "react";
import "./profilePage.css";
import Navigation from "./Parts/Navigation/navigation";
import UserPerformance from "./Parts/User/userPerfomance";
import {Route, Switch} from "react-router-dom";
import Favourites from "./Parts/Favourites/favourites";
import DevelopingPage from "../../common/Developing/developingPage";

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
                    <Switch>
                        <Route exact={true} path="/profile" render={() => <UserPerformance/>}/>
                        <Route exact={true} path="/profile/mail" render={() => <DevelopingPage pageName="mail"/>}/>
                        <Route exact={true} path="/profile/friends" render={() => <DevelopingPage pageName="friends"/>}/>
                        <Route exact={true} path="/profile/favourites" render={() => <Favourites/>}/>
                    </Switch>
                </div>
            </div>
        );
    }
}

export default ProfilePage;