import React from "react";
import {Redirect, withRouter} from "react-router";
import {ROLES} from "../../../../../constants";
import {Button, Form} from "react-bootstrap";
import "../editCategory.css";

class NewCategory extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = {
            name: event.target.elements.name.value,
            parentId: event.target.elements.parentId.value,
            order: event.target.elements.order.value
        }
        this.props.postCategoryThunk(data)
        this.props.history.push(`/categories/${this.props.match.params.parentId}`);
    }

    render() {
        if (!(this.props.user.roles.includes(ROLES.admin) || this.props.user.roles.includes(ROLES.moderator))) {
            return <Redirect to={{pathname: `/categories/${this.props.match.params.parentId}`}}/>
        }

        return (
            <div className="addCategory_container">
                <div className="form_container">
                    <div className="header_container">
                        Add new category
                    </div>
                    <Form className="category_form" onSubmit={this.handleSubmit}>
                        <Form.Group controlId="name">
                            <Form.Label>Category Name</Form.Label>
                            <Form.Control size="lg" type="text" placeholder="CategoryName" />
                        </Form.Group>
                        <Form.Group controlId="parentId">
                            <Form.Label>Parent category id</Form.Label>
                            <Form.Control readOnly size="lg" defaultValue={this.props.match.params.parentId} />
                        </Form.Group>
                        <Form.Group controlId="order">
                            <Form.Label>Showing priority</Form.Label>
                            <Form.Control size="lg" type="range" min={0} max={20}/>
                        </Form.Group>
                        <Button variant="primary" type="submit" block>
                            Add Category
                        </Button>
                    </Form>
                </div>
            </div>
        )
    }
}

export default withRouter(NewCategory);
