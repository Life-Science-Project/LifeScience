import React from "react";
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import "./category.css"

const Article = ({article}) => {
    return (
        <div className="method_container">
            <Link to={"/method/" + article.id}>
                {article.version.name}
            </Link>
        </div>
    );
};

Article.propsTypes = {
   method: PropTypes.exact({
       id: PropTypes.number.isRequired,
       version: PropTypes.exact({
           name: PropTypes.string.isRequired,
           articleId: PropTypes.number.isRequired,
           sectionsIds: PropTypes.arrayOf(
               PropTypes.exact({
                   id: PropTypes.number.isRequired,
                   name: PropTypes.string.isRequired
               })
           ).isRequired
       })
   })
};

export default Article;