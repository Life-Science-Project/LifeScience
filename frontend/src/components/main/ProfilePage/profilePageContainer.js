import React from "react";
import ProfilePage from "./profilePage";
import {withRouter} from "react-router-dom";
import {getUsersThunk} from "../../../redux/users-reducer";
import {connect} from "react-redux";

class ProfilePageContainer extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.refreshUser();
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        //TODO more deep checks
        return this.props.user.id !== nextProps.user.id;
    }

    checkAccess() {
        if (!this.props.isAuthorized) {
            this.props.history.push('/login');
        }
    }

    refreshUser() {
        this.props.getUsersThunk(this.props.user.id);
    }

    render() {
        this.checkAccess();
        return(
            <ProfilePage {...this.props} userData={this.props.userData}/>
        );
    }
}

let mapStateToProps = (state) => {
    return({
        isAuthorized: state.auth.isAuthorized,
        user: state.auth.user,
        userData: state.usersPage.user
    });
}

let WithRoutProfileContainer = withRouter(ProfilePageContainer)

export default connect(mapStateToProps, {getUsersThunk})(WithRoutProfileContainer);
