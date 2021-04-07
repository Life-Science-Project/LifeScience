import React from "react";
import {Link, withRouter} from "react-router-dom";
import PropTypes from 'prop-types';
import classNames from "classnames";

class __NavLink extends React.Component {
    render() {
        const { location, path, name } = this.props;
        const isActive = location.pathname === path;
        const classes = classNames({
            'nav-item': true,
            'active': isActive
        });

        return (
            <Link to={this.props.path}>
                <li className={classes}>
                    <a className="nav-link navbar__link" href="#">{name}</a>
                </li>
            </Link>
        );
    };
};

const NavLink = withRouter(__NavLink);

NavLink.propTypes = {
    path: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired
}

export default NavLink;