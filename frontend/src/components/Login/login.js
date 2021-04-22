import React, {useState} from "react";
import {useForm} from 'react-hook-form';
import '../Register/register.css';
import axios from "axios";
import {Redirect} from "react-router";
import '../../constants.js';
import {rootUrl} from "../../constants";

const Login = ({loggedUserStateUpdater}) => {
    const [status, setStatus] = useState(false);
    const {register, handleSubmit, errors} = useForm();
    const onSubmit = data => {
        axios.post(rootUrl + '/api/auth/signin', data).then(resp => {
            if (resp.status === 200) {
                loggedUserStateUpdater(resp.data);
                // Save auth-data to localStorage to retrieve on app restart
                localStorage.setItem('auth-data', JSON.stringify(resp.data));
                setStatus(true);
            }
        });
    }
    console.log(errors);

    if (status) {
        return <Redirect to={{pathname: "/"}}/>;
    } else {
        return (
            <div className={"auth__form"}>
                <h1 className={"auth__form_header d-flex justify-content-center"}>Login</h1>
                <form onSubmit={handleSubmit(onSubmit)}
                      className="d-flex justify-content-center flex-column auth__form_fields">
                    <input type="text" placeholder="Email" name="email"
                           ref={register({required: true, pattern: /^\S+@\S+$/i})}
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

export default Login;