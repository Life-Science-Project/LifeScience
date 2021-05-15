import React from "react";
import {withRouter} from "react-router";
import {deleteCategoryThunk, postCategoryThunk, putCategoryThunk} from "../../../../redux/actions/category-actions";
import {connect} from "react-redux";
import "./editCategory.css";
import Preloader from "../../../common/Preloader/preloader";
import {Route, Switch} from "react-router-dom";
import NewCategory from "./newCategory";
import DevelopingPage from "../../../common/Developing/developingPage";
import NotFound from "../../../common/NotFound/notFound";
import {getAuthorizedUserThunk} from "../../../../redux/auth-reducer";

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

        if (!this.props.isAuthorized) {
            this.props.history.push('/login');
            return <Preloader/>
        }

        return (
            <Switch>
                <Route exact={true} path="/category/add/:parentId" render={() => <NewCategory {...this.props} />}/>
                <Route exact={true} path="/category/edit/:id" render={() => <DevelopingPage pageName="mail"/>}/>
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

export default connect(mapStateToProps, {postCategoryThunk, putCategoryThunk, deleteCategoryThunk, getAuthorizedUserThunk})(WithDataContainerComponent);
