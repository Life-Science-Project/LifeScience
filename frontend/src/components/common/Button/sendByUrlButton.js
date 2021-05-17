import React from "react";
import PropTypes from "prop-types";
import {withRouter} from "react-router";

export class SendByUrlButton extends React.Component {
    constructor(props) {
        super(props);
        this.onSubmit = this.onSubmit.bind(this);
    }


    onSubmit() {
        this.props.history.push(this.props.url);
    }

    render() {
        return (
            <div className="button_container">
                <button className={this.props.message + "_container"} onClick={this.onSubmit}>
                    {this.props.message}
                </button>
            </div>
        )
    }
}

SendByUrlButton.propType = {
    message: PropTypes.string.isRequired,
    url: PropTypes.string.isRequired,
}

export default withRouter(SendByUrlButton);
