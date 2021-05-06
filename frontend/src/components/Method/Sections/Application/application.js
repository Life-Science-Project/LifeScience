import React from "react";
import NoContent from "../no-content";

const Application = ({contents}) => {
    if (!contents) return <NoContent/>
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