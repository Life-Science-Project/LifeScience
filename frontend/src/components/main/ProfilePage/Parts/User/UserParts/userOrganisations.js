import React from "react";
import PropTypes from "prop-types";

const UserOrganisations = ({organisations}) => {
    const getOrganisations = () => {
        if (organisations.length  === 0) {
            return <p>None</p>
        }
        return organisations.map(comp => <li key={comp.name}>{comp.name}</li>);
    }
    return(
        <div className="organisations_container">
            <div className="header">
                organisations
            </div>
            <ul>
                {getOrganisations()}
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