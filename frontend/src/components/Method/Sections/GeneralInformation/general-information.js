import React from "react";
import './general-information.css'
import ReferenceList from "./ReferenceList/reference-list";
import NotFound from "../../../common/NotFound/notFound";

const GeneralInformation = ({contents}) => {
    if (!contents) return <NotFound/>
    return (
        <div className="section-content">
            <div className="main-text">
                {contents.text}
            </div>
            {
                (contents.references && contents.references.length > 0) &&
                <ReferenceList refs={contents.references}/>
            }

        </div>
    )
}

export default GeneralInformation