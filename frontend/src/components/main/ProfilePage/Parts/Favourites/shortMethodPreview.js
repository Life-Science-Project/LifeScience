import React from "react";
import PropTypes from "prop-types";
import {NavLink} from "react-router-dom";
import {byField} from "../../../../../utils/common";

const ShortMethodPreview = (props) => {
    const getSections = () => {
        return props.method.version.sections.sort(byField('order')).map(x =>
            <li>
                <NavLink to={"/method/" + props.method.id + "/" + x.id} className="link">
                    {x.name}
                </NavLink>
            </li>);
    }
    return(
        <div className="short_method_preview_container">
            <div className="method_name">
                <NavLink to={"/method/" + props.method.id} className="link">
                    {props.method.version.name}
                </NavLink>
            </div>
            <div className="method_sections_container">
                <ul className="section_list">
                    {getSections()}
                </ul>
            </div>
        </div>
    );
}

ShortMethodPreview.propTypes = {
    method: PropTypes.exact({
        id: PropTypes.number.isRequired,
        version: PropTypes.exact({
            name: PropTypes.string.isRequired,
            articleId: PropTypes.number.isRequired,
            sections: PropTypes.arrayOf(
                PropTypes.exact({
                    id: PropTypes.number.isRequired,
                    name: PropTypes.string.isRequired,
                    order: PropTypes.number.isRequired
                })
            ).isRequired,
            state: PropTypes.string.isRequired
        })
    })
}

ShortMethodPreview.defaultProps = {
    method: {
        id: 1,
        version: {
            name: "Bradford assay",
            articleId: 1,
            sections: [
                {id: 1, name: "General information", order: 1},
                {id: 2, name: "Protocol", order: 2},
                {id: 3, name: "Equipment and reagents required", order: 3},
                {id: 4, name: "Application", order: 4},
                {id: 5, name: "Method advantages and disadvantages", order: 5},
                {id: 6, name: "Troubleshooting", order: 6},
                {id: 7,name: "Find collaboration", order: 7},
                {id:8, name:"Education", order: 8}],
            state: "PUBLISHED"}
    }
}

export default ShortMethodPreview;