import React from "react";
import {Button, Form} from "react-bootstrap";

const CategoryForm = ({onSubmit, data, message, btnMessage, canChgParentId, error}) => {
    return (
        <div className="addCategory_container">
            <div className="form_container">
                <div className="header_container">
                    {message}
                </div>
                <Form className="category_form" onSubmit={onSubmit}>
                    <Form.Group controlId="name">
                        <Form.Label>Category Name</Form.Label>
                        <Form.Control size="lg" type="text" defaultValue={data.name} />
                    </Form.Group>
                    <Form.Group controlId="parentId">
                        <Form.Label>Parent category id</Form.Label>
                        <Form.Control readOnly={!canChgParentId} size="lg" defaultValue={data.parentId} />
                        {error && error.message}
                    </Form.Group>
                    <Form.Group controlId="order">
                        <Form.Label>Order</Form.Label>
                        <Form.Control size="lg" type="range" min={0} max={20} defaultValue={data.order} />
                    </Form.Group>
                    <Button variant="primary" type="submit" block>
                        {btnMessage}
                    </Button>
                </Form>
            </div>
        </div>
    )
}

export default CategoryForm;
