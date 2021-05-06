import React from "react";
import {BrowserRouter as Router, NavLink, Redirect, Route, Switch, withRouter} from "react-router-dom";
import './method.css'
import SectionContainer from "./Sections/section-container?";

const Method = (props) => {
    const link = props.match.url
    const {name, sections, versionId, addButton} = props;
    const buttonStyle = {
        "margin-left": "-1.5rem"
    }
    return (
        <Router>
            <div className="method__method-name">
                <h2>
                    {name}
                </h2>
            </div>
            <div className="method__main">
                <ul className="method__section-list">
                    {
                        sections.map((section) => (
                            <li className="list-item">
                                <NavLink exact activeClassName="active-section" className="section-link"
                                         to={link + "/" + section.id}>{section.name}</NavLink>
                            </li>
                        ))
                    }
                    {
                        addButton &&
                        (<li className="list-item" style={buttonStyle}>
                            {addButton}
                        </li>)
                    }
                </ul>
                <Switch>
                    {
                        sections[0] && (
                            <Redirect exact from={link} to={link + "/" + sections[0].id}/>
                        )
                    }
                </Switch>
                <Switch>
                    <Route path={link + "/:sectionId"}>
                        <SectionContainer versionId={versionId}/>
                    </Route>
                </Switch>
            </div>
        </Router>
    )
}

export default withRouter(Method);
