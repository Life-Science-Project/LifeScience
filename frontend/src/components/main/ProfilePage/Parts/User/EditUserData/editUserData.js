import React from "react";
import {connect} from "react-redux";
import {
    getUsersThunk,
    patchUserDataThunk
} from "../../../../../../redux/users-reducer";
import {getUserWrap} from "../../../../../../utils/common";
import "./editUserData.css";
import {withRouter} from "react-router-dom";
import {ACADEMIC_DEGREE} from "../../../../../../constants";
import {Button, Form} from "react-bootstrap";
import {
    getOrganisationsLine,
    getOrganisationsNames
} from "../../../../../../utils/user";

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
        const value = name === "organisations" ? getOrganisationsNames(event.target.value) : event.target.value;
        this.setState({[name]: value});
    }

    handleSubmit(event) {
        this.props.patchUserDataThunk(this.props.userData.id, this.state);
        this.props.history.push('/profile');
        event.preventDefault();
    }

    getOptions() {
        return ACADEMIC_DEGREE.map(x => <option value={x.value} key={x.value}>{x.name}</option>);
    }

    render() {
        return(
            <div className="edit_user_container">
                <h1>Editing profile</h1>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group controlId="doctorDegree">
                        <Form.Label>Doctor degree</Form.Label>
                        <Form.Check type="radio" label="None" name="doctorDegree" value="NONE" onChange={this.handleChange} checked={this.state.doctorDegree === "NONE"} />
                        <Form.Check type="radio" label="PhD" name="doctorDegree" value="PhD" onChange={this.handleChange} checked={this.state.doctorDegree === "PhD"} />
                    </Form.Group>

                    <Form.Group controlId="academicDegree">
                        <Form.Label>Academic degree</Form.Label>
                        <Form.Control as="select" name="academicDegree" onChange={this.handleChange} defaultValue={this.state.academicDegree}>
                            {this.getOptions()}
                        </Form.Control>
                    </Form.Group>

                    <Form.Group controlId="organisations">
                        <Form.Label>Your organisations:</Form.Label>
                        <Form.Control onChange={this.handleChange} name="organisations" defaultValue={getOrganisationsLine(this.state.organisations)} />
                        <Form.Text className="text-muted">
                            Input your organisations separated by comma!
                        </Form.Text>
                    </Form.Group>

                    <Form.Group controlId="orcid">
                        <Form.Label>Orcid</Form.Label>
                        <Form.Control onChange={this.handleChange} name="orcid" defaultValue={this.state.orcid} />
                    </Form.Group>

                    <Form.Group controlId="researchId">
                        <Form.Label>Research id</Form.Label>
                        <Form.Control onChange={this.handleChange} name="researchId" defaultValue={this.state.researchId} />
                    </Form.Group>

                    <Button variant="primary" type="submit" block>
                        Save data
                    </Button>
                </Form>
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
