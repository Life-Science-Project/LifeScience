import React from "react";
import NoContent from "../no-content";

const Troubleshooting = ({contents}) => {
    if (!contents) return <NoContent/>
    return (
        <div className="section-content">
            {contents.text}
        </div>
    )
}

export default Troubleshooting