import React from "react";
import UserAvatar from "./UserParts/userAvatar";
import UserInformation from "./UserParts/userInformation";
import UserOrganisations from "./UserParts/userOrganisations";
import PropTypes from "prop-types";
import "./userPerfomance.css";
import {Link} from "react-router-dom";

const UserPerformance = ({curUser}) => {
    return(
        <div className="user">
            <div className="user_container">
                <UserAvatar/>
                <UserInformation user={curUser}/>
                <UserOrganisations organisations={curUser.organisations}/>
            </div>
            <div className="edit_link">
                <Link to="/profile/edit">
                    Edit data
                </Link>
            </div>
        </div>
    );
}

UserPerformance.propType = {
    curUser: PropTypes.exact({
        userDetailsId: PropTypes.number.isRequired,
        firstName: PropTypes.string.isRequired,
        lastName: PropTypes.string.isRequired,
        doctorDegree: PropTypes.string.isRequired,
        academicDegree: PropTypes.string.isRequired,
        organisations: PropTypes.arrayOf(
            PropTypes.exact({
                id: PropTypes.number.isRequired,
                name: PropTypes.string.isRequired
            })
        ),
        orcid: PropTypes.string.isRequired,
        researchId: PropTypes.string.isRequired
    })
}

UserPerformance.defaultProps = {
    curUser: {
        userDetailsId: 0,
        firstName: 'Irina',
        lastName: 'Iankelevich',
        doctorDegree: 'PhD',
        academicDegree: 'ASSOCIATE',
        organisations: [
            {id: 0, name: 'JetBrains-Research'},
            {id: 1, name: 'Best Researchers'},
            {id: 2, name: 'JPMorgan'},
            {id: 3, name: 'X5 group'}
        ],
        orcid: 'some orcid',
        researchId: 'researcher893428jvfs823'
    }
}

export default UserPerformance;