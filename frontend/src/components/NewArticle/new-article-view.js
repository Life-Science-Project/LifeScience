import "./NewArticle.css"
import React, {useState} from "react";
import MethodPreview from "../Method/MethodPreview/method-preview";
import {Dropdown, DropdownButton} from "react-bootstrap";
import {FaTimes} from "react-icons/all";
import {INFO_SECTION_TITLES} from "../../constants";
import {getSectionsForPreview, getSectionsForShow, getSectionsForSubmit} from "../../utils/sections";


const NewArticleView = ({article, category, onSubmit, sectionTitles, autoSectionTitles}) => {

    const getNewSection = (sectionName) => {
        return {
            name: sectionName ? sectionName : "",
            content: {
                text: "",
            }
        }
    }

    const [preview, setPreview] = useState(false);
    const [sections, setSections] = useState([getNewSection(sectionTitles[0])]);
    const [methodName, setMethodName] = useState("")

    const addNewSection = () => {
        const section = getNewSection()
        setSections(oldSections => [...oldSections, section]);
    }

    const addSectionEnabled = () => {
        const lastSection = sections[sections.length - 1];
        return lastSection.name.length > 0 && lastSection.content.text.length > 0;
    }

    const handleMethodNameChange = (e) => {
        setMethodName(e.target.value)
    }

    const handleSectionContentUpdate = (e, index) => {
        let newSections = [...sections]
        newSections[index].content.text = e.target.value;
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

    function getSectionsForSubmit() {
        const sortedSections = getSortedSections();
        for (const title of autoSectionTitles) {
            sortedSections.push({
                name: title
            })
        }
        return sortedSections
    }

    function getSortedSections() {
        const sortedSections = [];
        for (const title of sectionTitles) {
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
        return sections[0].content.text.length === 0 || methodName.length === 0;
    }

    function removeSection(index) {
        const newSections = [...sections];
        newSections.splice(index, 1);
        setSections(newSections);
    }

    function handleSubmit(e) {
        e.preventDefault();
        onSubmit(getSectionsForPreview(sections), methodName)
    }

    function getHeaderBlock() {
        if (article) {
            return (
                <h4>
                    Article: {article && article.version.name}
                </h4>
            );
        } else {
            return (
                <h4>
                    Category: {category && category.name}
                </h4>
            );
        }
    }

    function getNameField() {
        if (article) {
            return (
                <h4>
                    Protocol name
                </h4>
            );
        } else {
            return (
                <h4>
                    Method name
                </h4>
            );
        }
    }

    function getNamePlaceholder() {
        if (article) {
            return "Input protocol name";
        } else {
            return "Input method name";
        }
    }

    if (preview) return <MethodPreview name={methodName}
                                       sections={getSectionsForPreview(sections)}
                                       goBack={() => setPreview(false)}/>

    return (
        <form className="new-article-form">
            {getHeaderBlock()}
            <div>
                <div className="new-article-form__method-name">
                    <h2 className="col-form-label">
                        {getNameField()}
                    </h2>
                    <textarea className="form-control"
                              onChange={handleMethodNameChange}
                              value={methodName}
                              placeholder={getNamePlaceholder()}
                    />
                </div>
                <div className="form-group">
                    <h2 className="col-form-label">
                        {sectionTitles[0]}
                    </h2>
                    <textarea className="form-control new-article-form__section-content"
                              onChange={
                                  (e) => handleSectionContentUpdate(e, 0)
                              }
                              value={sections[0].content.text}
                              placeholder="Text"
                    />
                </div>
                {sections.map((section, index) => {
                    if (index === 0) return
                    return (
                        <div className="form-group">
                            <div className="new-article-form__section-title-row">
                                <DropdownButton variant="light" id={"choose-section-" + index}
                                                title={sections[index].name ? sections[index].name : "Choose section"}>
                                    {
                                        sectionTitles

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
                                <FaTimes onClick={() => removeSection(index)}
                                         className="new-article-form__remove-icon"/>
                            </div>
                            <textarea className="form-control new-article-form__section-content"
                                      onChange={
                                          (e) => handleSectionContentUpdate(e, index)
                                      }
                                      value={sections[index].content.text}
                                      placeholder="Text"
                            />
                        </div>
                    );
                })}
            </div>
            <div className="d-flex bd-highlight mb-3">
                <button
                    className={"btn btn-large btn-primary new-article-form__button mr-auto p-2 bd-highlight"}
                    onClick={addNewSection} disabled={!addSectionEnabled()}>Add Section
                </button>
                <button type="submit"
                        className="btn btn-large btn-secondary new-article-form__button p-2 bd-highlight"
                        onClick={handlePreview}>Preview
                </button>
                <button type="submit"
                        className="btn btn-large btn-success new-article-form__button p-2 bd-highlight"
                        disabled={submitDisabled()}
                        onClick={handleSubmit}>Submit
                </button>
            </div>
        </form>)


}

export default NewArticleView