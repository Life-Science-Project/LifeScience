import React, {useState} from "react";
import {useForm} from 'react-hook-form';
import './register.css';
import axios from "axios";
import {Redirect} from "react-router";

const Register = () => {
    // Used to redirect user to the home page after successful registration.
    const [status, setStatus] = useState(false);

    const {register, handleSubmit, errors} = useForm();

    const onSubmit = data => {
        axios.post('https://life-science-2021.herokuapp.com//api/auth/signup', {
            "username": data.username,
            "firstName": data.firstName,
            "lastName": data.lastName,
            "email": data.email,
            "password": data.password
        }).then(resp => {
            if (resp.data.message === "User registered successfully!") {
                setStatus(true);
            }
        });
    }
    // TODO: handle errors
    console.log(errors);

    if (status) {
        return <Redirect to={{pathname: "/"}}/>;
    } else {
        return (
            <div className={"auth__form"}>
                <h1 className={"auth__form_header d-flex justify-content-center"}>Register</h1>
                <form onSubmit={handleSubmit(onSubmit)}
                      className="d-flex justify-content-center flex-column auth__form_fields">
                    <input type="text" placeholder="First name" name="firstName"
                           ref={register({required: true, maxLength: 80})} className={"auth__form_field"}/>
                    <input type="text" placeholder="Last name" name="lastName"
                           ref={register({required: true, maxLength: 100})}
                           className={"auth__form_field"}/>
                    <input type="text" placeholder="Username" name="username"
                           ref={register({required: true, minLength: 6, maxLength: 12})}
                           className={"auth__form_field"}/>
                    <input type="text" placeholder="Email" name="email"
                           ref={register({required: true, pattern: /^\S+@\S+$/i})}
                           className={"auth__form_field"}/>
                    <input type="tel" placeholder="Mobile number" name="mobile"
                           ref={register({required: true, minLength: 6, maxLength: 12})}
                           className={"auth__form_field"}/>
                    <input type="password" placeholder="Password" name="password"
                           ref={register({required: true, minLength: 6, maxLength: 24})}
                           className={"auth__form_field"}/>

                    <input type="submit" className={"auth__form_submit btn btn-success btn-lg"}/>
                </form>
            </div>
        );
    }
}

export default Register;