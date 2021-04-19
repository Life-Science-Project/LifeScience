import React from "react";
import './general-information.css'
import ReferenceList from "./ReferenceList/reference-list";

const GeneralInformation = ({content, refs}) => {
    return (
        <div className="section-content">
            <div className="main-text" dangerouslySetInnerHTML={{__html: content}}/>
            <ReferenceList refs={refs}/>
        </div>
    )
}

export default GeneralInformation