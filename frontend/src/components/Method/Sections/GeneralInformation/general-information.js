import React from "react";
import './general-information.css'
import ReferenceList from "./ReferenceList/reference-list";

const GeneralInformation = ({paragraphs}) => {
    const REFS = "References"

    const findReferences = () => {
        for (const p of paragraphs) {
            if (p.text === REFS) {
                return p.references
            }
        }
    }
    return (
        <div className="section-content">
            {/*<div className="main-text" dangerouslySetInnerHTML={{__html: content}}/>*/}
            <div className="main-text">
                {
                    paragraphs
                        .filter((p) => (p.text !== REFS))
                        .map((p) => (
                            <div>
                                {p.text}
                            </div>
                        ))
                }
            </div>
            <ReferenceList refs={findReferences()}/>
        </div>
    )
}

export default GeneralInformation