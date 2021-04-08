import React, {Fragment} from "react";
import {useForm} from 'react-hook-form';
import '../Register/register.css';

const Login = () => {
    const {register, handleSubmit, errors} = useForm();
    const onSubmit = data => console.log(data);
    console.log(errors);

    return (
        <div className={"auth__form"}>
            <h1 className={"auth__form_header d-flex justify-content-center"}>Login</h1>
            <form onSubmit={handleSubmit(onSubmit)}
                  className="d-flex justify-content-center flex-column auth__form_fields">
                <input type="text" placeholder="Email" name="Email"
                       ref={register({required: true, pattern: /^\S+@\S+$/i})}
                       className={"auth__form_field"}/>
                <input type="text" placeholder="Password" name="Password"
                       ref={register({required: true, minLength: 6, maxLength: 24})} className={"auth__form_field"}/>

                <input type="submit" className={"auth__form_submit btn btn-success btn-lg"}/>
            </form>
        </div>
    );
}

export default Login;