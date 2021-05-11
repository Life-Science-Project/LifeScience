import React from "react";
import PropTypes from "prop-types";
import {getLineWithSetLen} from "../../../../../../utils/common";
import Preloader from "../../../../../common/Preloader/preloader";

const UserInformation = ({user}) => {
    if (!user) return <Preloader/>

    function replaceNulls() {
        const replace = (str) => ((str === null) ? "NONE" : str)
        const keys = Object.keys(user);
        for (const key of keys) {
            user[key] = replace(user[key])
        }
    }

    replaceNulls()


    return(
        <div className="user_main_information">
                    <pre>
                        {getLineWithSetLen(25, 'Name:') + user.firstName + ' ' + user.lastName}
                        <br/>
                        {getLineWithSetLen(25, 'Degree:') + user.doctorDegree}
                        <br/>
                        {getLineWithSetLen(25, 'Education:') + user.academicDegree}
                        <br/>
                        {getLineWithSetLen(25, 'Orcid:') + user.orcid}
                        <br/>
                        {getLineWithSetLen(25, 'Researcher Id:') + user.researchId}
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