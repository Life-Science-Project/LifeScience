import React from "react";
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import "./category.css"

const Article = ({article}) => {
    return (
        <div className="method_container">
            <Link to={"/method/" + article.version.id}>
                {article.version && article.version.name}
            </Link>
        </div>
    );
};

Article.propsTypes = {
   method: PropTypes.shape({
       id: PropTypes.number.isRequired,
       version: PropTypes.shape({
           name: PropTypes.string.isRequired,
           id: PropTypes.number.isRequired,
           sections: PropTypes.arrayOf(
               PropTypes.shape({
                   id: PropTypes.number.isRequired,
                   name: PropTypes.string.isRequired,
                   order: PropTypes.number.isRequired
               })
           ).isRequired,
           state: PropTypes.string.isRequired
       })
   })
};

export default Article;