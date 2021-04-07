import React, {Fragment} from "react";
import {useForm} from 'react-hook-form';
import './register.css';

const Register = () => {
    const {register, handleSubmit, errors} = useForm();
    const onSubmit = data => console.log(data);
    console.log(errors);

    return (
        <div className={"auth__form"}>
            <h1 className={"auth__form_header d-flex justify-content-center"}>Register</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="d-flex justify-content-center flex-column auth__form_fields">
                <input type="text" placeholder="First name" name="First name"
                       ref={register({required: true, maxLength: 80})} className={"auth__form_field"}/>
                <input type="text" placeholder="Last name" name="Last name"
                       ref={register({required: true, maxLength: 100})}
                       className={"auth__form_field"}/>
                <input type="text" placeholder="Email" name="Email"
                       ref={register({required: true, pattern: /^\S+@\S+$/i})}
                       className={"auth__form_field"}/>
                <input type="tel" placeholder="Mobile number" name="Mobile number"
                       ref={register({required: true, minLength: 6, maxLength: 12})} className={"auth__form_field"}/>
                <input type="text" placeholder="Password" name="Password"
                       ref={register({required: true, minLength: 6, maxLength: 24})} className={"auth__form_field"}/>

                <input type="submit" className={"auth__form_submit btn btn-success btn-lg"}/>
            </form>
        </div>
    );
}

export default Register;