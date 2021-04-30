import React from "react";
import PropTypes from "prop-types";

const UserOrganisations = ({organisations}) => {
    return(
        <div className="organisations_container">
            <div className="header">
                organisations
            </div>
            <ul>
                {organisations.map(comp => <li>{comp.name}</li>)}
            </ul>
        </div>
    );
}

UserOrganisations.propType = {
    organisations: PropTypes.arrayOf(
        PropTypes.exact({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired
        })
    )
}

export default UserOrganisations;