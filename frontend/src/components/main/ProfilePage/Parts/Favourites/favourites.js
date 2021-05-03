import React from "react";
import {connect} from "react-redux";
import {deleteFromUserFavouritesThunk, getUserFavouritesThunk} from "../../../../../redux/users-reducer";
import Preloader from "../../../../common/Preloader/preloader";
import ShortMethodPreview from "./shortMethodPreview";

class Favourites extends React.Component {
    constructor(props) {
        super(props);
    }

    refresh() {
        this.props.getUserFavouritesThunk(this.props.user.id);
    }

    componentDidMount() {
        this.refresh();
    }

    render() {
        if (this.props.favourites === null || this.props.favourites === undefined) {
            return <Preloader/>;
        }

        return(
            <div className="user_favorites">
                {this.props.favourites.map(x => <ShortMethodPreview method={x} user={this.props.user} delete={this.props.deleteFromUserFavouritesThunk}/> )}
            </div>
        );
    }
}

let mapStateToProps = (state) => {
    return({
        favourites: state.usersPage.userFavourites,
        user: state.auth.user
    });
}

export default connect(mapStateToProps, {getUserFavouritesThunk, deleteFromUserFavouritesThunk})(Favourites);

