import React from "react";
import {Link} from "react-router-dom";
import {METHOD_URL} from "../../../constants";

const Content = ({text, articleId, sectionId}) => {
    return (
        <div>
            <Link to={`${METHOD_URL}/${articleId}/${sectionId}`}>
                {text}
            </Link>
        </div>
    )
}

export default Content