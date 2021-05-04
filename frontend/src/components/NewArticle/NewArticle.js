import "./NewArticle.css"
import React, {useEffect, useState} from "react";
import MethodPreview from "../Method/MethodPreview/method-preview";
import {Dropdown, DropdownButton} from "react-bootstrap";
import {FaTimes} from "react-icons/all";
import {withRouter} from "react-router-dom";
import {getCategoryThunk} from "../../redux/category-reducer";
import {connect, useDispatch, useSelector} from "react-redux";
import {addMethodThunk} from "../../redux/method-reducer";

const NewArticle = (props) => {

    const categoryId = props.match.params.categoryId;

    const SECTION_TITLES = ["General Information", "Protocol", "Equipment and reagents required", "Application",
        "Method advantages and disadvantages", "Troubleshooting"];

    const AUTO_SECTION_TITLES = ["Find collaboration", "Education"];

    const [preview, setPreview] = useState(false);
    const [sections, setSections] = useState([{
        name: SECTION_TITLES[0],
        content: "",
    }]);

    const [methodName, setMethodName] = useState("")

    const dispatch = useDispatch()

    useEffect(() => {
        refreshCategories()
    }, [])

    const refreshCategories = () => {
        const categoryId = props.match.params.categoryId;
        getCategoryThunk(categoryId)(dispatch);
    }

    const newSection = () => {
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

    const handleSubmit = () => {
        addMethodThunk(categoryId, methodName, sections);
    }


    function getSectionsForPreview() {
        const sortedSections = getSortedSections();
        for (const title of AUTO_SECTION_TITLES) {
            sortedSections.push({
                name: title
            })
        }
        return sortedSections
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
        return sections[0].content.length === 0 || methodName.length === 0;
    }

    function removeSection(index) {
        const newSections = [...sections];
        newSections.splice(index, 1);
        setSections(newSections);
    }

    const category = useSelector(state => state.categoryPage.category)

    return (
        preview
            ?
            (
                <MethodPreview name={methodName}
                               sections={getSectionsForPreview()}
                               goBack={() => setPreview(false)}/>
            )
            :
            (
                <form className="new-article-form">
                    <h4>
                        Category: {category && category.name}
                    </h4>
                    <div>
                        <div className="new-article-form__method-name">
                            <h2 className="col-form-label">
                                Method name
                            </h2>
                            <textarea className="form-control"
                                      onChange={handleMethodNameChange}
                                      value={methodName}
                                      placeholder="Input method name"
                            />
                        </div>
                        <div className="form-group">
                            <h2 className="col-form-label">
                                {SECTION_TITLES[0]}
                            </h2>
                            <textarea className="form-control new-article-form__section-content"
                                      onChange={
                                          (e) => handleSectionContentUpdate(e, 0)
                                      }
                                      value={sections[0].content}
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
                                        <FaTimes onClick={() => removeSection(index)}
                                                 className="new-article-form__remove-icon"/>
                                    </div>
                                    <textarea className="form-control new-article-form__section-content"
                                              onChange={
                                                  (e) => handleSectionContentUpdate(e, index)
                                              }
                                              value={sections[index].content}
                                              placeholder="Text"
                                    />
                                </div>
                            );
                        })}
                    </div>
                    <div className="d-flex bd-highlight mb-3">
                        <button
                            className={"btn btn-large btn-primary new-article-form__button mr-auto p-2 bd-highlight"}
                            onClick={newSection} disabled={!newSectionStatus()}>Add Section
                        </button>
                        <button type="submit"
                                className="btn btn-large btn-secondary new-article-form__button p-2 bd-highlight"
                                onClick={handlePreview}>Preview
                        </button>
                        <button type="submit"
                                className="btn btn-large btn-success new-article-form__button p-2 bd-highlight"
                                disabled={handleSubmit}>Submit
                        </button>
                    </div>
                </form>

            )
    )
}

const mapStateToProps = (state) => {
    return ({
        category: state.categoryPage.category
    })
}

export default connect(mapStateToProps, {getCategoryThunk, addMethodThunk})(withRouter(NewArticle));
