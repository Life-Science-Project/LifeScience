import React from "react";
import UserPage from "./userPage";
import {withRouter} from "react-router";
import {getUserThunk} from "../../../redux/users-reducer";
import {connect} from "react-redux";

class UserPageContainer extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.refreshState();
    }

    refreshState() {
        let userId = this.props.match.params.userId === undefined ? this.getLoginUser() : this.props.match.params.userId;
        this.props.getUserThunk(userId);
    }

    getLoginUser() {
        if (this.props.user === undefined) {
            this.props.history.push("/login");
        }
        return this.props.user.userView.id;
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.userId !== prevProps.match.params.userId || this.props.curUser.userDetailsId !== prevProps.curUser.userDetailsId) {
            this.refreshState();
        }
    }

    render() {
        return (
            <UserPage {...this.props} user={this.props.curUser}/>
        );
    }
}

let mapStateToProps = (state) => {
    return ({
        curUser: state.usersPage.user
    });
}

let withDataContainerComponent = withRouter(UserPageContainer);

export default connect(mapStateToProps, {getUserThunk})(withDataContainerComponent);