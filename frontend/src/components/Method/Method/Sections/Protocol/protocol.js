import React from "react";

const Protocol = ({content,}) => {
    return (
        <div className="section-content">
            <div className="main-text" dangerouslySetInnerHTML={{__html: content}}/>
        </div>
    )
}

export default Protocol