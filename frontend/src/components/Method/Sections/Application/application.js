import React from "react";

const Application = ({contents}) => {
    if (!contents) {
        contents = Application.defaultProps
    }
    return (
        <div className="section-content">
            <div className="main-text">
                {contents.text}
            </div>
        </div>
    )
}

Application.defaultProps = {
    contents: {
        text: "No applications found for this method"
    }
}

export default Application