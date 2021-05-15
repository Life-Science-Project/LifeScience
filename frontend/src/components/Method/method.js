import React, {useState} from "react";
import {withRouter} from "react-router-dom";
import './method.css'
import SectionContainer from "./Sections/section-container?";

const Method = (props) => {
    const {name, sections, versionId, addButton, newProtocolButton, isSectionSelected, backToProtocolsButton} = props;

    const getSectionIndex = () => {
        if (!isSectionSelected) return null
        for (let i = 0; i < sections.length; i++) {
            const section = sections[i]
            if (isSectionSelected(section)) {
                return i
            }
        }
        return null
    }
    const [activeSection, setActiveSection] = useState(getSectionIndex() ?? 0)
    const handleClick = (e, index) => {
        setActiveSection(index)
    }

    return (
        <>
            <div className="method__method-name">
                <h3>
                    {name}
                </h3>
            </div>
            <div className="method__main">
                <ul className="method__section-list">
                    {
                        sections.map((section, index) => (
                            <li className="list-item" key={index}>
                                <div className={"section-link" + ((index === activeSection) ? " active-section" : "")}
                                     onClick={e => handleClick(e, index)}>
                                    {section.name}
                                </div>
                            </li>
                        ))
                    }
                    {
                        backToProtocolsButton &&
                        (<li className="list-item" key={sections.length}>
                            {backToProtocolsButton}
                        </li>)
                    }
                </ul>
                <SectionContainer versionId={versionId} section={sections[activeSection]}/>
                <ul className="method__button-list">
                    {
                        addButton &&
                        (<li className="list-item" key={1}>
                            {addButton}
                        </li>)
                    }
                    {
                        newProtocolButton &&
                        (<li className="list-item" key={2}>
                            {newProtocolButton}
                        </li>)
                    }
                </ul>
            </div>
        </>
    )
}

export default withRouter(Method);
