import React from "react";
import {Link, withRouter} from "react-router-dom";

const LocationSubFolder = ({subFolder}) => {
    return (
        <>
            <a href={subFolder.path}>
                {subFolder.name}
            </a>
        </>
    )
}

LocationSubFolder.defaultProps = {
    subFolder: {
        path: "/",
        name: "Home",
    }
}

export default LocationSubFolder