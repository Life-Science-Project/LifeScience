import React from "react";
import PropTypes from "prop-types";
import "./error.css"

const Error = ({error}) => {
    return (
        <div className="trouble_container">
            <div className="trouble_message">
                {error.message}
            </div>
        </div>
    );
};

Error.propsType = {
    error: PropTypes.shape({
        status: PropTypes.number.isRequired,
        message: PropTypes.string.isRequired
    }).isRequired
};

export default Error;
