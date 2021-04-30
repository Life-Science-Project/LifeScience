import React from "react";
import LocationSubFolder from "./locationSubFolder";
import './location.css';

const Location = ({subFolders}) => {

    const ARROW = <p>></p>

    return (
        <div className="location">
            {
                subFolders
                    .map((folder) => (
                        <LocationSubFolder subFolder={folder}/>
                    ))
                    .reduce((prev, cur) => [prev, ARROW, cur])
            }
        </div>
    )
}

// Location.defaultProps = {
//     subFolders: [{
//         path: "",
//         name: "Home",
//     }]
// }

export default Location;

