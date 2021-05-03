import React from "react";
import {Link} from "react-router-dom";
import {METHOD_URL} from "../../../api/api";

const Article = ({name, articleId}) => {
    return (
        <div>
            <Link to={METHOD_URL + articleId}>
                {name}
            </Link>
        </div>
    )
}

export default Article