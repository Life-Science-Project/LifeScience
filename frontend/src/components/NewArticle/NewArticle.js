import "./NewArticle.css"
import React, {useState} from "react";
import Method from "../Method/method";
import MethodPreview from "../Method/MethodPreview/method-preview";
import {Dropdown, DropdownButton} from "react-bootstrap";

const NewArticle = () => {

    const SECTION_TITLES = ["General Information", "Protocol", "Equipment and reagents required", "Application",
        "Method advantages and disadvantages", "Troubleshooting"];

    const [preview, setPreview] = useState(false);
    const [sections, setSections] = useState([{
        name: SECTION_TITLES[0],
        content: "",
    }]);

    const newSection = (e) => {
        const section = {
            name: "",
            content: ""
        }
        setSections(oldSections => [...oldSections, section]);
        e.preventDefault();
    };

    const newSectionStatus = () => {
        const lastSection = sections[sections.length - 1];
        return lastSection.name.length > 0 && lastSection.content.length > 0;
    }

    const handleSectionTitleUpdate = (e) => {
        let newSections = [...sections]
        newSections[e.target.id].name = e.target.value;
        setSections(newSections);
    };

    const handleSectionContentUpdate = (e, index) => {
        let newSections = [...sections]
        newSections[index].content = e.target.value;
        setSections(newSections);
    };

    const handleSectionTitleSelect = (title, index) => {
        let newSections = [...sections];
        newSections[index].name = title;
        setSections(newSections);
    }

    const handlePreview = () => {
        setPreview(!preview);
    }

    function isSectionSelected(title) {
        for (const section of sections) {
            if (section.name === title) {
                return true
            }
        }
        return false
    }

    if (preview) {
        return (
            <MethodPreview sections={sections} goBack={() => setPreview(false)}/>
        );
    } else {
        return (
            <form className="new-article-form">
                <div>
                    {sections.map((section, index) => {
                        return (
                            <div className="form-group">
                                <DropdownButton variant="light" id={"choose-section-" + index}
                                                title={sections[index].name ? sections[index].name : "Choose section"}>
                                    {
                                        SECTION_TITLES
                                            .filter((title) => !isSectionSelected(title))
                                            .map(type => (
                                                <Dropdown.Item
                                                    eventKey={type}
                                                    onSelect={(title) => handleSectionTitleSelect(title, index)}>
                                                    {type}
                                                </Dropdown.Item>
                                            ))
                                    }
                                </DropdownButton>
                                <textarea className="form-control new-article-form--textarea"
                                          value={section.content}
                                          onChange={
                                              (e) => handleSectionContentUpdate(e, index)
                                          }
                                          placeholder="Text"/>
                            </div>
                        );
                    })}
                </div>
                <div className="d-flex bd-highlight mb-3">
                    <button className={"btn btn-large btn-primary new-article-form--button mr-auto p-2 bd-highlight"}
                            onClick={newSection} disabled={!newSectionStatus()}>Add Section
                    </button>
                    <button type="submit"
                            className="btn btn-large btn-secondary new-article-form--button p-2 bd-highlight"
                            onClick={handlePreview}>Preview
                    </button>
                    <button type="submit"
                            className="btn btn-large btn-success new-article-form--button p-2 bd-highlight">Submit
                    </button>
                </div>
            </form>

        );
    }
};

export default NewArticle;
