import React from "react";
import {connect} from "react-redux";
import {getUsersThunk, patchUserDataThunk} from "../../../../../../redux/users-reducer";

class EditUserData extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="edit_tab_container">

            </div>
        );
    }
}

let mapStateToProps = (state) => {
    return({
        userData: state.usersPage.user
    })
}

export default connect(mapStateToProps, {getUsersThunk, patchUserDataThunk})(EditUserData);
