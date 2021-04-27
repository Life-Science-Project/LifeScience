import React from "react";
import './general-information.css'
import ReferenceList from "./ReferenceList/reference-list";

const GeneralInformation = ({contents}) => {
    const REFS = "References"

    return (
        <div className="section-content">
            <div className="main-text">
                {contents.text}
            </div>
            <ReferenceList refs={contents.references}/>
        </div>
    )
}

export default GeneralInformation