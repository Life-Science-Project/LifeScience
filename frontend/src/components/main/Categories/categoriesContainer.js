import React from "react";
import Categories from "./categories";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import {getCategoryThunk} from "../../../redux/category-reducer";

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

    render() {
        return (
            <Categories {...this.props} category={this.props.category} isShowButton={this.props.isAuthorized}/>
        )
    }
}

let mapStateToProps = (state) => {
    return ({
        category: state.categoryPage.category,
        isAuthorized: state.auth.isAuthorized
    })
}

let WithDataContainerComponent = withRouter(CategoriesContainer);

export default connect(mapStateToProps, {getCategoryThunk})(WithDataContainerComponent);