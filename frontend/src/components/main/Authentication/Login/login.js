import React from "react";
import '../Register/register.css';
import {Redirect, withRouter} from "react-router";
import '../../../../constants.js';
import {connect} from "react-redux";
import {refresh, signInUserThunk} from "../../../../redux/auth-reducer";
import {validateEmail, validatePassword} from "../../../../utils/validators"
import withUseFormHook from "../../../../hoc/withUserForm";

class Login extends React.Component {
    componentWillUnmount() {
        this.props.refresh();
    }

    render() {
        if (this.props.isAuthorized) {
            return <Redirect to={{pathname: "/"}}/>;
        }

        return(
            <div className={"auth__form"}>
                <h1 className={"auth__form_header d-flex justify-content-center"}>Login</h1>
                <form onSubmit={this.props.handleSubmit(this.props.signInUserThunk)}
                      className="d-flex justify-content-center flex-column auth__form_fields">
                    <input type="text" placeholder="Email" name="email"
                           ref={this.props.register({required: true, pattern: /^\S+\S+$/i})}
                           className={"auth__form_field"}/>
                    {validateEmail(this.props.errors)}
                    <input type="password" placeholder="Password" name="password"
                           ref={this.props.register({required: true, minLength: 5, maxLength: 24})}
                           className={"auth__form_field"}/>
                    {validatePassword(this.props.errors)}
                    {!this.props.errorMsg.isEmpty && <span className="error">{this.props.errorMsg}</span>}
                    <input type="submit" value="Sign In" className={"auth__form_submit btn btn-success btn-lg"}/>
                </form>
            </div>
        )
    }
}

let mapStateToProps = (state) => {
    return ({
        isAuthorized: state.auth.isAuthorized,
        errorMsg: state.auth.errorMsg
    })
};

let WithDataContainerComponent = withRouter(withUseFormHook(Login));

export default connect(mapStateToProps, {signInUserThunk, refresh})(WithDataContainerComponent);
