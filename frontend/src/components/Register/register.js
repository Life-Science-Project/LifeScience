import React, {useState} from "react";
import {useForm} from 'react-hook-form';
import './register.css';
import {Redirect, withRouter} from "react-router";
import {signUpUserThunk} from "../../redux/auth-reducer";
import {connect} from "react-redux";

const Register = ({signUpUserThunk, isAuthorized, errorMsg}) => {
    const {register, handleSubmit, errors} = useForm();

    if (isAuthorized) {
        return <Redirect to={{pathname: "/"}}/>;
    } else {
        return (
            <div className={"auth__form"}>
                <h1 className={"auth__form_header d-flex justify-content-center"}>Register</h1>
                <form onSubmit={handleSubmit(signUpUserThunk)}
                      className="d-flex justify-content-center flex-column auth__form_fields">
                    <input type="text" placeholder="First name" name="firstName"
                           ref={register({required: true, maxLength: 80})} className={"auth__form_field"}/>
                    <input type="text" placeholder="Last name" name="lastName"
                           ref={register({required: true, maxLength: 100})}
                           className={"auth__form_field"}/>
                    <input type="text" placeholder="Email" name="email"
                           ref={register({required: true, pattern: /^\S+@\S+$/i})}
                           className={"auth__form_field"}/>
                    <input type="password" placeholder="Password" name="password"
                           ref={register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>
                    <input type="password" placeholder="Confirm Password" name="confirmPassword"
                           ref={register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>

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

let WithDataContainerComponent = withRouter(Register);

export default connect(mapStateToProps, {signUpUserThunk})(WithDataContainerComponent);