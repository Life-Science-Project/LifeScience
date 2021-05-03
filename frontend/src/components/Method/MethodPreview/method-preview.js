import React, {useState} from "react";
import '../method.css'
import Section from "../Sections/section";

const MethodPreview = ({name, sections, goBack}) => {

    const [activeSectionIndex, setActiveSectionIndex] = useState(0)

    const handleClick = (e, index) => {
        e.preventDefault()
        setActiveSectionIndex(index)
    }
    return (
        <div>
            <div className="method-name">
                <h2>
                    {name}
                </h2>
            </div>
            <div className="main">
                <ul className="section-list">
                    {
                        sections.map((section, index) => (
                            <li className="list-item">
                                <a href="#"
                                   onClick={e => handleClick(e, index)}
                                   className={"section-link " + (index === activeSectionIndex && "active-section")}>
                                    {section.name}
                                </a>
                            </li>
                        ))
                    }
                    <li>
                        <button className="btn btn-large btn-secondary preview-go-back-button"
                                onClick={goBack}>Go back
                        </button>
                    </li>
                </ul>
                <Section name={sections[activeSectionIndex].name}
                         contents={{text: sections[activeSectionIndex].content}}/>
            </div>

        </div>
    )
}

MethodPreview.defaultProps = {
    name: "Method Name",
    sections: []
}

export default MethodPreview