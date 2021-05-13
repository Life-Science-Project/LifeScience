import React, {useState} from "react";
import {withRouter} from "react-router-dom";
import './method.css'
import SectionContainer from "./Sections/section-container?";

const Method = (props) => {
    const {name, sections, versionId, addButton} = props;
    const buttonStyle = {
        "margin-left": "-1.5rem"
    }
    const [activeSection, setActiveSection] = useState(0)

    const handleClick = (e, index) => {
        setActiveSection(index)
    }

    return (
        <>
            <div className="method__method-name">
                <h2>
                    {name}
                </h2>
            </div>
            <div className="method__main">
                <ul className="method__section-list">
                    {
                        sections.map((section, index) => (
                            <li className="list-item">
                                <div className={"section-link" + ((index === activeSection) ? " active-section" : "")}
                                     onClick={e => handleClick(e, index)}>
                                    {section.name}
                                </div>
                            </li>
                        ))
                    }
                    {
                        addButton &&
                        (<li className="list-item" style={buttonStyle}>
                            {addButton}
                        </li>)
                    }
                </ul>
                <SectionContainer versionId={versionId} section={sections[activeSection]}/>
            </div>
        </>
    )
}

export default withRouter(Method);
