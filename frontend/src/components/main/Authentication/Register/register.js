import React from "react";
import './register.css';
import {Redirect, withRouter} from "react-router";
import {refresh, signUpUserThunk} from "../../../../redux/auth-reducer";
import {connect} from "react-redux";
import {validateEmail, validateFirstName, validateLastName, validatePassword} from "../../../../utils/validators"
import withUseFormHook from "../../../../hoc/withUserForm";

class Register extends React.Component {
    componentWillUnmount() {
        this.props.refresh();
    }

    render() {
        if (this.props.isAuthorized) {
            return <Redirect to={{pathname: "/"}}/>;
        }

        return (
            <div className={"auth__form"}>
                <h1 className={"auth__form_header d-flex justify-content-center"}>Register</h1>
                <form onSubmit={this.props.handleSubmit(this.props.signUpUserThunk)}
                      className="d-flex justify-content-center flex-column auth__form_fields">
                    <input type="text" placeholder="First name" name="firstName"
                           ref={this.props.register({required: true, minLength: 3, maxLength: 80})}
                           className={"auth__form_field"}/>
                    {validateFirstName(this.props.errors)}
                    <input type="text" placeholder="Last name" name="lastName"
                           ref={this.props.register({required: true, minLength: 3, maxLength: 80})}
                           className={"auth__form_field"}/>
                    {validateLastName(this.props.errors)}
                    <input type="text" placeholder="Email" name="email"
                           ref={this.props.register({required: true, pattern: /^\S+@\S+$/i})}
                           className={"auth__form_field"}/>
                    {validateEmail(this.props.errors)}
                    <input type="password" placeholder="Password" name="password"
                           ref={this.props.register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>
                    {validatePassword(this.props.errors)}
                    <input type="password" placeholder="Confirm Password" name="confirmPassword"
                           ref={this.props.register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>
                    {!this.props.errorMsg.isEmpty && <span className="error">{this.props.errorMsg}</span>}
                    <input type="submit" value="Sign Up" className={"auth__form_submit btn btn-success btn-lg"}/>
                </form>
            </div>
        );
    }
}

let mapStateToProps = (state) => {
    return ({
        isAuthorized: state.auth.isAuthorized,
        errorMsg: state.auth.errorMsg
    })
};

let WithDataContainerComponent = withRouter(withUseFormHook(Register));

export default connect(mapStateToProps, {signUpUserThunk, refresh})(WithDataContainerComponent);
