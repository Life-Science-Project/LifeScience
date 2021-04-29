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

    const [methodName, setMethodName] = useState("")

    const newSection = (e) => {
        const section = {
            name: "",
            content: ""
        }
        setSections(oldSections => [...oldSections, section]);
    };

    const newSectionStatus = () => {
        const lastSection = sections[sections.length - 1];
        return lastSection.name.length > 0 && lastSection.content.length > 0;
    }

    const handleMethodNameChange = (e) => {
        setMethodName(e.target.value)
    }

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

    function getSortedSections() {
        const sortedSections = [];
        for (const title of SECTION_TITLES) {
            for (const section of sections) {
                if (section.name === title) {
                    sortedSections.push(section)
                }
            }
        }
        return sortedSections;
    }

    function isSectionSelected(title) {
        for (const section of sections) {
            if (section.name === title) {
                return true
            }
        }
        return false
    }

    function submitDisabled() {

    }

    if (preview) {
        return (
            <MethodPreview name={methodName} sections={getSortedSections()} goBack={() => setPreview(false)}/>
        );
    } else {
        return (
            <form className="new-article-form">
                <div>
                    <textarea className="form-control new-article-form__method-name"
                              onChange={handleMethodNameChange}
                              placeholder="Method name"
                    />
                    {sections.map((section, index) => {
                        return (
                            <div className="form-group">
                                <DropdownButton className="new-article-form__section-title"
                                                variant="light" id={"choose-section-" + index}
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
                                <textarea className="form-control new-article-form__section-content"
                                          value={section.content}
                                          onChange={
                                              (e) => handleSectionContentUpdate(e, index)
                                          }
                                          placeholder="Text"
                                />
                            </div>
                        );
                    })}
                </div>
                <div className="d-flex bd-highlight mb-3">
                    <button className={"btn btn-large btn-primary new-article-form__button mr-auto p-2 bd-highlight"}
                            onClick={newSection} disabled={!newSectionStatus()}>Add Section
                    </button>
                    <button type="submit"
                            className="btn btn-large btn-secondary new-article-form__button p-2 bd-highlight"
                            onClick={handlePreview}>Preview
                    </button>
                    <button type="submit"
                            className="btn btn-large btn-success new-article-form__button p-2 bd-highlight"
                            disabled={submitDisabled()}>Submit
                    </button>
                </div>
            </form>

        );
    }
};

export default NewArticle;
