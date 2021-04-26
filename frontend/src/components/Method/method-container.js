import React from "react";
import {BrowserRouter as Router, withRouter} from "react-router-dom";
import { connect } from 'react-redux'
import {fetchSections} from "../../redux/method-reducer";
import ReduxMethod from "./method";


class MethodContainer extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.getSections()
    }

    render() {
        const {name, sections} = this.props
        return (
            <ReduxMethod name={name} sections={sections}/>
        )
    }

    getSections() {
        const id = this.props.match.params.articleId;
        this.props.dispatch(fetchSections(id))
    }

}


const MethodContainerWithRouter = withRouter(MethodContainer)

function mapStateToProps(state) {
    return state.method;
}

export default connect(mapStateToProps)(MethodContainerWithRouter)