import React from "react";
import PropTypes from "prop-types";
import "./notFound.css"

const NotFound = ({pageName}) => {
    return(
        <div className="notFound_container">
            <span>
                 Page {pageName} not found!
            </span>
        </div>
    );
}

NotFound.propType = {
    pageName: PropTypes.string
}

export default NotFound;