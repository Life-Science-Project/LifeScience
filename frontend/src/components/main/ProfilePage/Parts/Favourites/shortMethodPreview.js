import React from "react";
import {NavLink} from "react-router-dom";
import {byField, functionWrapper} from "../../../../../utils/common";
import "./shortMethodPreview.css";

const ShortMethodPreview = (props) => {
    const length = 2;
    const getSections = () => {
        return props.method.sections.slice(0, length).sort(byField('order')).map(x =>
            <li>
                <NavLink to={"/method/" + props.method.id + "/" + x.id} className="link">
                    {x.name}
                </NavLink>
            </li>);
    }
    return (
        <div className="short_method_preview_container">
            <div className="method_name">
                <NavLink to={"/method/" + props.method.id} className="link">
                    {props.method.name}
                </NavLink>
            </div>
            <div className="method_sections_container">
                <ul className="section_list">
                    {getSections()}
                </ul>
            </div>
            <div className="button_delete_container">
                <button onClick={functionWrapper(props.delete, props.user.id, props.method.id)}>
                    Unsave
                </button>
            </div>
        </div>
    );
}


ShortMethodPreview.defaultProps = {
    method: {
        id: 1,
        name: "Bradford assay",
        articleId: 1,
        sections: [
            {id: 1, name: "General information", order: 1},
            {id: 2, name: "Protocol", order: 2},
            {id: 3, name: "Equipment and reagents required", order: 3},
            {id: 4, name: "Application", order: 4},
            {id: 5, name: "Method advantages and disadvantages", order: 5},
            {id: 6, name: "Troubleshooting", order: 6},
            {id: 7, name: "Find collaboration", order: 7},
            {id: 8, name: "Education", order: 8}],
        state: "PUBLISHED"
    }
}

export default ShortMethodPreview;