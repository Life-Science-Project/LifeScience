import React from "react";
import './homePage.css'
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";
import {getStatisticsThunk} from "../../../redux/init-reducer";
import * as PropTypes from "prop-types";
import Preloader from "../../common/Preloader/preloader";

class Home extends React.Component {
    componentDidMount() {
        this.props.getStatisticsThunk();
    }

    render() {
        if (!this.props.isStatisticsInitialized) {
            return <Preloader/>
        }
        let {statistics} = this.props;
        return (
            <div className="home_container">
                <h1>Welcome to the Life Science project!</h1>
                <br/>
                <br/>
                <h5>Total users: {statistics.userCount}</h5>
                <h5>Total articles: {statistics.postCount}</h5>
            </div>
        );
    }
}

Home.propTypes = {statistics: PropTypes.any}

const mapStateToProps = (state) => {
    return ({
        statistics: state.init.statistics,
        isStatisticsInitialized: state.init.isStatisticsInitialized
    })
}

export default connect(mapStateToProps, {getStatisticsThunk})(withRouter(Home));