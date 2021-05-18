import React from "react";
import {
    Redirect,
    withRouter
} from "react-router";
import {ROLES} from "../../../../../constants";
import "../editCategory.css";
import CategoryForm from "./categoryForm";
import SendByUrlButton from "../../../../common/Button/sendByUrlButton";

class NewCategory extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = {
            name: event.target.elements.name.value,
            parentId: this.props.match.params.parentId,
            order: event.target.elements.order.value
        }
        this.props.postCategoryThunk(data)
        this.props.clearCategory();
        this.props.history.push(`/categories/${this.props.match.params.parentId}`);
    }

    render() {
        if (!(this.props.user.roles.includes(ROLES.admin) || this.props.user.roles.includes(ROLES.moderator))) {
            return <Redirect to={{pathname: `/categories/${this.props.match.params.parentId}`}}/>
        }
        const data = {
            name: "Category name",
            parentId: this.props.match.params.parentId,
            order: 10
        }

        return (
            <div>
                <div className="buttons_container">
                    <SendByUrlButton message="Previous" url={"/category/" + this.props.match.params.parentId} {...this.props} />
                </div>
                <CategoryForm onSubmit={this.handleSubmit}
                              message={"Add Category"}
                              data={data} btnMessage={"Save"}
                              canChgParentId={false} />
            </div>
        )
    }
}

export default withRouter(NewCategory);
