import React from "react";
import ProfilePage from "./profilePage";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {getAuthorizedUserThunk} from "../../../redux/auth-reducer";
import Preloader from "../../common/Preloader/preloader";

class ProfilePageContainer extends React.Component {
    componentDidMount() {
        this.refreshUser();
    }

    refreshUser() {
        this.props.getAuthorizedUserThunk();
    }

    render() {
        if (!this.props.isInitialized) {
            return <Preloader/>
        }
        if (!this.props.isAuthorized) {
            this.props.history.push('/login');
            return <Preloader/>
        }
        return(
            <ProfilePage {...this.props} userData={this.props.user}/>
        );
    }
}

let mapStateToProps = (state) => {
    return({
        isInitialized: state.init.isInitialized,
        isAuthorized: state.auth.isAuthorized,
        user: state.auth.user
    });
}

let WithRoutProfileContainer = withRouter(ProfilePageContainer)

export default connect(mapStateToProps, {getAuthorizedUserThunk})(WithRoutProfileContainer);
