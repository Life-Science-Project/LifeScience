import React, {useState} from "react";
import {useForm} from 'react-hook-form';
import '../Register/register.css';
import {Redirect, withRouter} from "react-router";
import '../../constants.js';
import {connect} from "react-redux";
import {signInUserThunk} from "../../redux/auth-reducer";

const Login = ({signInUserThunk, isAuthorized, errorMsg}) => {
    const {register, handleSubmit, errors} = useForm();
    console.log(errorMsg)
    console.log(isAuthorized)
    const validateEmail = () => {
        if (errors.email && errors.email.type === "required") {
            return (<span class="error">Email is required</span>);
        } else if (errors.email && errors.email.type === "pattern") {
            return (<span class="error">Invalid email</span>)
        }
    };
    const validatePassword = () => {
        if (errors.password && errors.password.type === "required") {
            return (<span class="error">Password is required</span>);
        } else if (errors.password && errors.password.type === "minLength") {
            return (<span class="error">Password is too short</span>)
        } else if (errors.password && errors.password.type === "maxLength") {
            return (<span class="error">Password is too long</span>)
        }
    }

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
                    {validateEmail()}
                    <input type="password" placeholder="Password" name="password"
                           ref={register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>
                    {validatePassword()}
                    {!errorMsg.isEmpty && <span>{errorMsg}</span>}
                    <input type="submit" className={"auth__form_submit btn btn-success btn-lg"}/>
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