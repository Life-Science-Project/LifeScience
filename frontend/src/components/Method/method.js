import React from "react";
import './method.css'
import {
    BrowserRouter as Router,
    Switch,
    Route,
    NavLink,
    Redirect
} from "react-router-dom";
// import {Redirect} from "react-router";

const Method = ({link, name, sections}) => {
    return (
        <Router>
            <div className="method-name">
                <h3>
                    {name}
                </h3>
            </div>
            <div className="main">
                <ul className="section-list">
                    {
                        sections.map((section) => (
                            <li className="section-list-item">
                                <NavLink exact activeClassName="active-section" className="section-link"
                                         to={section.link}>{section.name}</NavLink>
                            </li>
                        ))
                    }
                </ul>
                <Switch>
                    {
                        <Redirect exact from={link} to={link + "/general"}/>
                    }
                </Switch>
                <Switch>
                    {
                        sections.map((section, index) => (
                            <Route exact={index === 0} path={section.link}>
                                {
                                    <div className="section-content"
                                         dangerouslySetInnerHTML={{__html: section.content}}/>
                                }
                            </Route>
                        ))

                    }
                </Switch>
            </div>
        </Router>

    )
}


Method.defaultProps = {
    link: "/bradford-assay",
    name: "Bradford Assay",
    sections: [
        {
            link: "/bradford-assay/general",
            name: "General Information",
            content: "<div>Method for quantify the protein content in sample. This method has multiple applications in experimental sciences. <a href='./general/some'>Chemical basis</a> of the Bradford method (1976) is based on the absorbance shift observed in an acidic solution of dye Coomassie® Brilliant Blue G-250. When added to a solution of protein, the dye binds to the protein resulting in a color change from a reddish brown to blue.</div>"
        },
        {
            link: "/bradford-assay/protocol",
            name: "Protocol",
            content: "<div>Curabitur tempus felis eu diam tincidunt porttitor. Aenean at dui nisi. Phasellus et dictum mi. Nulla et ex vitae dolor vulputate dignissim. Nunc nulla libero, commodo nec odio vel, blandit tincidunt tellus. Nullam porttitor mattis accumsan. Nullam bibendum risus at lorem ultricies lobortis. Fusce et laoreet lorem.\n" +
                "\n" +
                "Duis at malesuada mi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam erat volutpat. Sed euismod dui vitae ligula rutrum, vitae fringilla urna luctus. Fusce sit amet risus vel ipsum tincidunt sagittis eu eget quam. Nullam tincidunt lorem augue, et bibendum nisi pretium at. Proin at eleifend arcu. Pellentesque dolor dui, aliquet eget dui eu, laoreet egestas dui. Sed eros tellus, lobortis ut aliquet eu, consectetur eget est. Curabitur consequat tellus arcu, at aliquet lacus posuere ac. Vestibulum tempor ut orci et laoreet. Curabitur eleifend euismod dapibus. Mauris sit amet massa ipsum. Nulla maximus turpis quis elit congue, non sodales augue euismod. Integer in congue enim.</div>"
        },
        {
            link: "/bradford-assay/trouble",
            name: "Troubleshooting",
            content: "Troubleshooting information"
        }
    ]
}


export default Method