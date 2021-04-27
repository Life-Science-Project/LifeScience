import React from "react";
import {fetchContents} from "../../../redux/section-reducer";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import Section from "./section";


class SectionContainer extends React.Component {

    constructor(props) {
        super(props);
        this.versionId = props.versionId;
        this.sectionId = props.sectionId;
    }

    componentDidMount() {
        this.getContents()
    }

    getContents() {
        this.props.dispatch(fetchContents(this.versionId, this.sectionId))
    }

    render() {
        const {contents, name} = this.props;
        return (
            <Section contents={contents} name={name}/>
        )
    }
}


function mapStateToProps(state) {
    return {...state.section}
}

export default connect(mapStateToProps)(SectionContainer)