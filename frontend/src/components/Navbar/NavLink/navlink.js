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
            <li className={classes}>
                <Link to={this.props.path} className="nav-link navbar__link">
                    {name}
                </Link>
            </li>
        );
    };
};

const NavLink = withRouter(__NavLink);

NavLink.propTypes = {
    path: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired
}

export default NavLink;