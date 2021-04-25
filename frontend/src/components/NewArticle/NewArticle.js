import "./NewArticle.css"
import React, {useState} from "react";

const NewArticle = () => {
    const [sections, setSections] = useState([]);

    // Костыль страшного характера, лучше не трогать
    const [k, setK] = useState(0);
    const [count, setCount] = useState(0);
    const newSection = (e) => {
        let currSections = sections;
        sections.push({
            id: count,
            name: "",
            content: ""
        })
        setCount(count + 1);
        setSections(currSections);

        e.preventDefault();
    };

    const newSectionStatus = () => {
        if (sections.length === 0) return true;
        const lastSection = sections[sections.length - 1];
        return lastSection.name.length > 0 && lastSection.content.length > 0;
    }

    const handleSectionTitleUpdate = (e) => {
        let currSections = sections;
        sections[e.target.id].name = e.target.value;
        setSections(currSections);
        setK(k + 1);
    };

    const handleSectionContentUpdate = (e) => {
        let currSections = sections;
        sections[e.target.id].content = e.target.value;
        setSections(currSections);
        setK(k + 1);
    };

    return (
        <form className="new-article-form">
            <div className="form-group row">
                <h1 className="col-sm-2 col-form-label">General Information</h1>
                <div className="col-sm-10">
                    <textarea type="email" className="form-control new-article-form--textarea" id="inputEmail3"
                              placeholder="Text"/>
                </div>
            </div>
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
                <button className={"btn btn-large btn-primary new-article-form--button mr-auto p-2 bd-highlight"} onClick={newSection} disabled={!newSectionStatus()}>Add Section</button>
                <button type="submit" className="btn btn-large btn-secondary new-article-form--button p-2 bd-highlight">Preview</button>
                <button type="submit" className="btn btn-large btn-success new-article-form--button p-2 bd-highlight">Submit</button>
            </div>
        </form>

    );
};

export default NewArticle;
