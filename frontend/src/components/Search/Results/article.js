import React from "react";
import {Link} from "react-router-dom";
import {METHOD_URL} from "../../../constants";

const Article = ({name, versionId}) => {
    return (
        <div>
            <Link to={METHOD_URL + "/" + versionId}>
                {name}
            </Link>
        </div>
    )
}

export default Article