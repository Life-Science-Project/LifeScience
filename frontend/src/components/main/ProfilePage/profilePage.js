import React from "react";
import "./profilePage.css";
import Navigation from "./Parts/Navigation/navigation";
import UserPerformance from "./Parts/User/userPerfomance";
import {Route, Switch} from "react-router-dom";
import Favourites from "./Parts/Favourites/favourites";
import DevelopingPage from "../../common/Developing/developingPage";
import PropTypes from "prop-types";
import EditUserData from "./Parts/User/EditUserData/editUserData";
import NotFound from "../../common/NotFound/notFound";

class ProfilePage extends React.Component {
    render() {
        return(
            <div className="profile_container">
                <div className="nav_container">
                    <Navigation/>
                </div>
                <div className="main_container">
                    <Switch>
                        <Route exact={true} path="/profile" render={() => <UserPerformance curUser={this.props.userData}/>}/>
                        <Route exact={true} path="/profile/mail" render={() => <DevelopingPage pageName="mail"/>}/>
                        <Route exact={true} path="/profile/friends" render={() => <DevelopingPage pageName="friends"/>}/>
                        <Route exact={true} path="/profile/favourites" render={() => <Favourites/>}/>
                        <Route exact={true} path="/profile/edit" render={() => <EditUserData/>}/>
                        <Route render={() => <NotFound />}/>
                    </Switch>
                </div>
            </div>
        );
    }
}

ProfilePage.propsType = {
    userData: PropTypes.exact({
        userId: PropTypes.number.isRequired
    })
}

export default ProfilePage;
