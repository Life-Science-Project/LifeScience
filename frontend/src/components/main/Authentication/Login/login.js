import React from "react";
import {useForm} from 'react-hook-form';
import '../Register/register.css';
import {Redirect, withRouter} from "react-router";
import '../../../../constants.js';
import {connect} from "react-redux";
import {signInUserThunk} from "../../../../redux/auth-reducer";
import {validateEmail, validatePassword} from "../../../../utils/validators"

const Login = ({signInUserThunk, isAuthorized, errorMsg}) => {
    const {register, handleSubmit, errors} = useForm();

    if (isAuthorized) {
        return <Redirect to={{pathname: "/"}}/>;
    } else {
        return (
            <div className={"auth__form"}>
                <h1 className={"auth__form_header d-flex justify-content-center"}>Login</h1>
                <form onSubmit={handleSubmit(signInUserThunk)}
                      className="d-flex justify-content-center flex-column auth__form_fields">
                    <input type="text" placeholder="Email" name="email"
                           ref={register({required: true, pattern: /^\S+@\S+$/i})}
                           className={"auth__form_field"}/>
                    {validateEmail(errors)}
                    <input type="password" placeholder="Password" name="password"
                           ref={register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>
                    {validatePassword(errors)}
                    {!errorMsg.isEmpty && <span>{errorMsg}</span>}
                    <input type="submit" value="Sign In" className={"auth__form_submit btn btn-success btn-lg"}/>
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

let WithDataContainerComponent = withRouter(Login);

export default connect(mapStateToProps, {signInUserThunk})(WithDataContainerComponent);