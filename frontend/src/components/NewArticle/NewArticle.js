import "./NewArticle.css"
import React, {useState} from "react";
import Method from "../Method/method";
import MethodPreview from "../Method/MethodPreview/method-preview";

const NewArticle = () => {
    const [preview, setPreview] = useState(false);
    const [sections, setSections] = useState([{
        id: 0,
        name: "General Information",
        content: "",
    }]);

    const newSection = (e) => {
        const section = {
            id: sections.length,
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

    const handleSectionContentUpdate = (e) => {
        let newSections = [...sections]
        newSections[e.target.id].content = e.target.value;
        setSections(newSections);
    };

    const handlePreview = () => {
        setPreview(!preview);
    }

    if (preview) {
        return (
            <MethodPreview sections={sections} goBack={() => setPreview(false)}/>
        );
    } else {
        return (
            <form className="new-article-form">
                <div>
                    {sections.map((e) => {
                        return (
                            <div className="form-group row">
                                <input className="col-sm-2 form-control new-article-form--section-title"
                                       value={e.name}
                                       onChange={handleSectionTitleUpdate}
                                       id={e.id}
                                       placeholder="Section Title"/>
                                <div className="col-sm-10">
                                <textarea type="email" className="form-control new-article-form--textarea"
                                          value={e.content}
                                          onChange={handleSectionContentUpdate}
                                          id={e.id}
                                          placeholder="Text"/>
                                </div>
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
