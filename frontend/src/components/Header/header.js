import React from "react";
import {Link} from "react-router-dom";
import './header.css'
import registerIcon from '../../logos/register_icon.svg'
import loginIcon from '../../logos/login_icon.svg'
import {connect} from "react-redux";

const Header = ({user}) => {
    // const [user, setUser] = useState(null);
    //
    // store.subscribe(() => {
    //     setUser(store.getState().user);
    // })

    console.log(user)
    let authBlock;
    console.log(user);
    // Check if user is logged in.
    // Show Login/Register Buttons if he is not and profile page link otherwise.
    if (!user) {
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
            <Link to="/userPage">
                <div className="d-flex align-items-center header__group">
                    <div className="header__group_link p-2 bd-highlight">{`${user.userView.firstName} ${user.userView.lastName}`}</div>
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

const mapStateToProps = state => {
    return {
        user: state.auth.user
    };
};

export default connect(mapStateToProps)(Header);