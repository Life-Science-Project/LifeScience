import React from "react";
import Categories from "./categories";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import {clearCategory, getCategoryThunk} from "../../../redux/actions/category-actions";

class CategoriesContainer extends React.Component {
    componentDidMount() {
        this.refreshCategories()
    }

    refreshCategories() {
        let categoryId = this.props.match.params.categoryId;
        this.props.getCategoryThunk(categoryId);
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        if (this.props.match.params.categoryId !== nextProps.match.params.categoryId ||
            (this.props.match.params?.categoryId && this.props.match.params?.categoryId != this.props.category?.id) || //Не менять на !== (все летит)
            this.props.category?.articles.length !== nextProps.category?.articles.length ||
            this.props.category?.subcategories.length !== nextProps.category?.subcategories.length) {
            return true;
        }
        return false;
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        this.refreshCategories();
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
        isReceived: state.categoryPage.isReceived
    })
}

let WithDataContainerComponent = withRouter(CategoriesContainer);

export default connect(mapStateToProps, {getCategoryThunk, clearCategory})(WithDataContainerComponent);
