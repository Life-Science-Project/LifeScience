import React from "react";
import Categories from "./categories";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import {clearCategory, getCategoryThunk} from "../../../redux/category-reducer";

class CategoriesContainer extends React.Component {
    componentDidMount() {
        this.refreshCategories()
    }

    refreshCategories() {
        let categoryId = this.props.match.params.categoryId;
        this.props.getCategoryThunk(categoryId);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.categoryId !== prevProps.match.params.categoryId ) {
            this.refreshCategories();
        }
    }

    componentWillUnmount() {
        this.props.clearCategory()
    }

    render() {
        return (
            <Categories {...this.props} category={this.props.category} isReceived={this.props.isReceived}/>
        )
    }
}

let mapStateToProps = (state) => {
    return ({
        category: state.categoryPage.category,
        isAuthorized: state.auth.isAuthorized,
        userRoles: state.auth.user?.roles,
        error: state.categoryPage.error,
        isReceived: state.categoryPage.isReceived,
    })
}

let WithDataContainerComponent = withRouter(CategoriesContainer);

export default connect(mapStateToProps, {getCategoryThunk, clearCategory})(WithDataContainerComponent);
