import React from "react";
import {connect} from 'react-redux'
import {
    deleteFromUserFavouritesThunk,
    getUserFavouritesThunk,
    patchToUserFavouritesThunk
} from "../../../redux/users-reducer";
import {containsByField} from "../../../utils/common";
import Preloader from "../../common/Preloader/preloader";
import "./addButton.css";

class AddButton extends React.Component {
    constructor(props) {
        super(props);
        this.deleteFromFavorites = this.deleteFromFavorites.bind(this);
        this.addToMyFavourites = this.addToMyFavourites.bind(this);
    }

    componentDidMount() {
        this.props.getUserFavouritesThunk(this.props.userId)
    }

    deleteFromFavorites() {
        this.props.deleteFromUserFavouritesThunk(this.props.userId, this.props.versionId);
    }

    addToMyFavourites() {
        this.props.patchToUserFavouritesThunk(this.props.userId, this.props.versionId);
    }


    render() {
        const REMOVE = "Remove from favourites";
        const ADD = "Add to favourites"

        if (this.props.favourites === null) {
            return <Preloader/>
        }

        const isFavourite = () => containsByField(this.props.favourites, 'id', this.props.versionId);

        return (
            <button onClick={(isFavourite()) ? this.deleteFromFavorites : this.addToMyFavourites} type={"button"}>
                {isFavourite() ? REMOVE : ADD}
            </button>
        );

    }
}

let mapStateToProps = (state) => {
    return ({
        favourites: state.usersPage.userFavourites,
        userId: state.auth.user.id
    })
}

export default connect(mapStateToProps, {
    getUserFavouritesThunk,
    patchToUserFavouritesThunk,
    deleteFromUserFavouritesThunk
})(AddButton);