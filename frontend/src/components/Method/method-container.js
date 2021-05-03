import React from "react";
import {withRouter} from "react-router-dom";
import {connect} from 'react-redux';
import {fetchSections} from "../../redux/method-reducer";
import Method from "./method";
import Preloader from "../common/Preloader/preloader";
import AddButton from "./AddButton/addButton";


class MethodContainer extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.getSections()
    }

    getSections() {
        const id = this.props.match.params.articleId;
        this.props.fetchSections(id)
    }

    render() {
        if (!this.props.isReceived) {
            return (
                <Preloader/>
            );
        }

        if (this.props.isAuthorized) {
            return (
                <div>
                    <AddButton articleId={this.props.match.params.articleId}/>
                    <Method name={this.props.name} sections={this.props.sections} versionId={this.props.versionId}/>
                </div>
            );
        }

        return (
            <div>
                <Method name={this.props.name} sections={this.props.sections} versionId={this.props.versionId}/>
            </div>
        );
    }

}

let mapStateToProps = (state) => {
    return ({
        name: state.method.name,
        sections: state.method.sections,
        versionId: state.method.versionId,
        isReceived: state.method.isReceived,
        isAuthorized: state.auth.isAuthorized
    });
}

export default withRouter(connect(mapStateToProps, {fetchSections})(MethodContainer))