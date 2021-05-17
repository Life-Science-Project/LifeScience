import React from "react";
import {withRouter} from "react-router";
import {
    clearCategory,
    deleteCategoryThunk,
    getCategoryThunk,
    postCategoryThunk,
    putCategoryThunk
} from "../../../../redux/actions/category-actions";
import {connect} from "react-redux";
import "./editCategory.css";
import Preloader from "../../../common/Preloader/preloader";
import {
    Route,
    Switch
} from "react-router-dom";
import NewCategory from "./Parts/newCategory";
import NotFound from "../../../common/NotFound/notFound";
import {getAuthorizedUserThunk} from "../../../../redux/auth-reducer";
import EditCategory from "./Parts/editCategory";
import ShortCategory from "./shortCategory";

class EditCategoryContainer extends React.Component {
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

        return (
            <Switch>
                <Route exact={true} path="/category/add/:parentId" render={() => <NewCategory {...this.props} />}/>
                <Route exact={true} path="/category/edit/:id" render={() => <EditCategory {...this.props} />}/>
                <Route exact={true} path="/category/:id" render={() => <ShortCategory {...this.props} />}/>
                <Route render={() => <NotFound />}/>
            </Switch>
        )
    }
}

let mapStateToProps = (state) => {
    return({
        isInitialized: state.init.isInitialized,
        isAuthorized: state.auth.isAuthorized,
        user: state.auth.user
    });
}

let WithDataContainerComponent = withRouter(EditCategoryContainer);

export default connect(mapStateToProps, {postCategoryThunk, putCategoryThunk, deleteCategoryThunk, getAuthorizedUserThunk, getCategoryThunk, clearCategory})(WithDataContainerComponent);
