import React from "react";
import './mainPage.css'
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import PropTypes from 'prop-types';
import axios from "axios";
import Section from "./Section/section";
import {get} from "react-hook-form";


class Main extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div>
                <div className="section_name">
                    {this.props.section.name}
                </div>
                <div className="sections_container">
                    {this.props.section.children.map((section) => <Section section={section}/>)}
                </div>
            </div>
        );
    }
}

Main.propTypes = {
    section: PropTypes.exact({
        id: PropTypes.number,
        parentID: PropTypes.number,
        name: PropTypes.string,
        children: PropTypes.arrayOf(PropTypes.exact({
                id: PropTypes.number,
                name: PropTypes.string
            }
        ))
    }).isRequired
};

Main.defaultProps = {
    section: {
        id: 0,
        parentID: 0,
        name: "Main",
        children:
            [{id: 1, name: "Biological System methods"},
                {id: 2, name: "molecular methods"},
                {id: 3, name: "In silico Methods"}]
    }
};

export default Main;