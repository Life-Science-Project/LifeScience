import React from "react";
import {Link} from "react-router-dom";
import './header.css'
import registerIcon from '../../logos/register_icon.svg'
import loginIcon from '../../logos/login_icon.svg'
import {connect} from "react-redux";
import {withRouter} from "react-router";

const Header = ({user}) => {
    let authBlock;
    console.log(user);
    // Check if user is logged in.
    // Show Login/Register Buttons if he is not and profile page link otherwise.
    if (user === null) {
        authBlock = (<div className="d-flex justify-content-between">
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
        </div>);
    } else {
        authBlock = (<div className="d-flex justify-content-between">
            {/*TODO: profile page link*/}
            <Link to="/profilePage">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">{user.username}</div>
                    {/*TODO: proper userpage icon*/}
                    <img src={registerIcon} className="header__group_icon" alt="reg"/>
                </div>
                {/*TODO: logout button*/}
            </Link>
        </div>);
    }


    return (
        <div className="d-flex align-items-center justify-content-between">
            <div className="header__logo_text">Life Science</div>
            {authBlock}
        </div>
    );
}

let mapStateToProps = (state) => {
    return ({
        user: state.auth.user
    })
};

// TODO don't use connect
let WithDataContainerComponent = withRouter(Header);

export default connect(mapStateToProps, {Header})(WithDataContainerComponent);