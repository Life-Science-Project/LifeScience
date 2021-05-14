import React from "react";
import {Link} from "react-router-dom";
import {METHOD_URL} from "../../../constants";
import {useDispatch} from "react-redux";
import {passSectionId} from "../../../redux/method-reducer";

const Content = ({text, versionId, sectionId}) => {
    const dispatch = useDispatch()

    const handleClick = () => {
        dispatch(passSectionId(sectionId))
    }

    return (
        <div>
            <Link to={`${METHOD_URL}/${versionId}`}
                  onClick={handleClick}>
                {text}
            </Link>
        </div>
    )
}

export default Content