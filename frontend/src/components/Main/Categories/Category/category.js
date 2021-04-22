import React from "react"
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import './category.css'

const Category = ({category}) => {
    return(
        <div className="category_container">
            <Link to={"/categories/" + category.id}>
                {category.name}
            </Link>
        </div>
    );
};

Category.propsTypes = {
    section: PropTypes.exact({
        id: PropTypes.number,
        name: PropTypes.string
    }).isRequired
};

export default Category;