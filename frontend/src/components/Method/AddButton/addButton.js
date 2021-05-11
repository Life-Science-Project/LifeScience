import React from "react";
import {connect} from 'react-redux'
import {deleteFromUserFavouritesThunk, getUserFavouritesThunk, patchToUserFavouritesThunk} from "../../../redux/users-reducer";
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
        this.props.deleteFromUserFavouritesThunk(this.props.userId, this.props.articleId);
    }

    addToMyFavourites() {
        this.props.patchToUserFavouritesThunk(this.props.userId, this.props.articleId);
    }


    render() {
        if (this.props.favourites === null) {
            return <Preloader/>
        }

        if (containsByField(this.props.favourites, 'id', this.props.articleId)) {
            return (
                <div className="buttons_container">
                    <button onClick={this.deleteFromFavorites}>
                        Remove from Favourites
                    </button>
                </div>
            );
        }

        return (
            <div className="buttons_container">
                <button onClick={this.addToMyFavourites}>
                    Add to Favourites
                </button>
            </div>
        )
    }
}

let mapStateToProps = (state) => {
    return({
        favourites: state.usersPage.userFavourites,
        userId: state.auth.user.id
    })
}

export default connect(mapStateToProps, {getUserFavouritesThunk, patchToUserFavouritesThunk, deleteFromUserFavouritesThunk})(AddButton);