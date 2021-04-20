import React from "react";
import './section.css'
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import PropTypes from 'prop-types';

const Section = ({section}) => {
    return(
        <div className="section_container">
            <Link to={"/main/" + section.id}>
                {section.name}
            </Link>
        </div>
    );
};

Section.propsTypes = {
    section: PropTypes.exact({
        id: PropTypes.number,
        name: PropTypes.string
    }).isRequired
};

export default Section;