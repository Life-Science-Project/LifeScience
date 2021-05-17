import React from "react";
import {ROLES} from "../../../../../constants";
import {Redirect, withRouter} from "react-router";
import CategoryForm from "./categoryForm";
import Preloader from "../../../../common/Preloader/preloader";
import {clearCategory, getCategoryThunk} from "../../../../../redux/actions/category-actions";
import {connect} from "react-redux";
import "../editCategory.css";

class EditCategory extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {error: null}
    }

    componentDidMount() {
        this.refreshCategories();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.id !== prevProps.match.params.id ) {
            this.refreshCategories();
        }
    }

    componentWillUnmount() {
        this.props.clearCategory()
    }

    refreshCategories() {
        const id = this.props.match.params.id;
        this.props.getCategoryThunk(id);
    }


    handleSubmit(event) {
        event.preventDefault();
        const data = {
            name: event.target.elements.name.value,
            parentId: this.props.category.parentId,
            order: event.target.elements.order.value
        }
        this.props.clearCategory();
        this.props.history.push(`/categories/${this.props.match.params.id}`);
    }

    render() {
        if (!(this.props.user.roles.includes(ROLES.admin) || this.props.user.roles.includes(ROLES.moderator))) {
            return <Redirect to={{pathname: `/categories/${this.props.match.params.parentId}`}}/>
        }

        if (!this.props.isReceived) {
            return <Preloader/>
        }

        const data = {
            name: this.props.category.name,
            parentId: this.props.category.parentId,
            order: this.props.category.order
        }

        return (
            <CategoryForm onSubmit={this.handleSubmit}
                          message={"Edit Category"}
                          data={data} btnMessage={"Edit"}
                          canChgParentId={true} error={this.state?.error}/>
        )
    }
}

let mapStateToProps = (state) => {
    return({
        isReceived: state.categoryPage.isReceived,
        category: state.categoryPage.category
    });
}

export default connect(mapStateToProps, {getCategoryThunk, clearCategory})(withRouter(EditCategory));
