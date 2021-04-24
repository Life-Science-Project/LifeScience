import React from "react";
import PropTypes from "prop-types";

const Trouble = ({trouble}) => {
    return (
        <div className="trouble_container">
            <div className="trouble_message">
                {trouble.message}
            </div>
            Time: {trouble.timestamp}
        </div>
    );
};

Trouble.propsType = {
    trouble: PropTypes.exact({
        message: PropTypes.string.isRequired,
        timestamp: PropTypes.string.isRequired
    }).isRequired
};

export default Trouble;
