import React from "react";
import {connect} from "react-redux";
import {getUsersThunk, patchUserDataThunk} from "../../../../../../redux/users-reducer";
import {getUserWrap} from "../../../../../../utils/common";
import "./editUserData.css"
import {withRouter} from "react-router-dom";
import {ACADEMIC_DEGREE} from "../../../../../../constants";

class EditUserData extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ...getUserWrap(this.props.userData)
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.getOptions = this.getOptions.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        const value = event.target.value;
        this.setState({[name]: value});
    }

    handleSubmit(event) {
        this.props.patchUserDataThunk(this.props.userData.id, this.state);
        this.props.history.push('/profile');
        event.preventDefault();
    }

    getOptions() {
        return ACADEMIC_DEGREE.map(x => (x.value === this.state.academicDegree) ? <option selected value={x.value}>{x.name}</option> : <option value={x.value}>{x.name}</option>);
    }

    render() {
        return(
            <div className="edit_user_container">
                <h2>Editing profile</h2>
                <form onSubmit={this.handleSubmit}>
                    <div className="form_part_container">
                        <div className="input_name">Doctor degree:</div>
                        <div className="input_sections">
                            <input name="doctorDegree" type="radio" className="pin" value="PhD" onChange={this.handleChange}/> PhD
                            <input name="doctorDegree" type="radio" className="pin" value="NONE" onChange={this.handleChange} /> None
                        </div>
                    </div>
                    <div className="form_part_container">
                        <div className="input_name">Academic degree:</div>
                        <div className="input_sections">
                            <select name="academicDegree" className="field" onChange={this.handleChange}>
                                {this.getOptions()}
                            </select>
                        </div>
                    </div>
                    <div className="form_part_container">
                        <div className="input_name">Orcid:</div>
                        <div className="input_sections">
                            <input name="orcid" type="text" value={this.state.orcid} className="field" onChange={this.handleChange}/>
                        </div>
                    </div>
                    <div className="form_part_container">
                        <div className="input_name">Research id:</div>
                        <div className="input_sections">
                            <input name="researchId" type="text" value={this.state.researchId} className="field" onChange={this.handleChange}/>
                        </div>
                    </div>
                    <div className="form_part_container">
                        <input type="submit" className="button" value="Save data"/>
                    </div>
                </form>
            </div>
        );
    }
}

let mapStateToProps = (state) => {
    return ({
        userData: state.auth.user
    })
}

export default connect(mapStateToProps, {getUsersThunk, patchUserDataThunk})(withRouter(EditUserData));
