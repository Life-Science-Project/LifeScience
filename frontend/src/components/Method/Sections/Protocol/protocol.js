import React from "react";
import NoContent from "../no-content";

const Protocol = ({contents}) => {
    if (!contents) return <NoContent/>
    return (
        <div className="section-content">
            {contents.text}
        </div>
    )
}

export default Protocol