import React from "react";
import PropTypes from "prop-types";
import {getLineWithSetLen} from "../../../utils/common";
import "./userPage.css"
import ava from "../../../logos/photo.jpeg"

const UserPage = (props) => {
    return(
        <div className="user_container">
            <div className="user_header">
                <div className="user_photo">
                    <img src={ava} alt="profile avatar"/>
                </div>
                <div className="user_main_information">
                    <pre>
                        {getLineWithSetLen(25, 'Name:') + props.user.firstName + ' ' + props.user.lastName}
                        <br/>
                        {getLineWithSetLen(25, 'Degree:') + props.user.doctorDegree}
                        <br/>
                        {getLineWithSetLen(25, 'Education:') + props.user.academicDegree}
                        <br/>
                        {getLineWithSetLen(25, 'Orcid:') + props.user.orcid}
                        <br/>
                        {getLineWithSetLen(25, 'Researcher Id:') + props.user.researchId}
                    </pre>
                </div>
                <div className="organisations_container">
                    <div className="header">
                        organisations
                    </div>
                    <ul>
                        {props.user.organisations.map(comp => <li>{comp.name}</li>)}
                    </ul>
                </div>
                {/*TODO*/}
            </div>
        </div>
    )
};

UserPage.propType = {
    user: PropTypes.exact({
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
};

UserPage.defaultProps = {
    user: {
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

export default UserPage;
