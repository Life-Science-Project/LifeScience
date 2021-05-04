import React from "react";
import PropTypes from "prop-types";
import {getLineWithSetLen} from "../../../../../../utils/common";

const UserInformation = (props) => {
    return(
        <div className="user_main_information">
                    <pre>
                        {getLineWithSetLen(25, 'Name:') + props.curUser.firstName + ' ' + props.curUser.lastName}
                        <br/>
                        {getLineWithSetLen(25, 'Degree:') + props.curUser.doctorDegree}
                        <br/>
                        {getLineWithSetLen(25, 'Education:') + props.curUser.academicDegree}
                        <br/>
                        {getLineWithSetLen(25, 'Orcid:') + props.curUser.orcid}
                        <br/>
                        {getLineWithSetLen(25, 'Researcher Id:') + props.curUser.researchId}
                    </pre>
        </div>
    );
}

UserInformation.propType = {
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

export default UserInformation;