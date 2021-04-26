import React from "react";

export const validateFirstName = (errors) => {
    if (errors.firstName && errors.email.type === "required") {
        return (<span class="error">First name is required</span>);
    } else if (errors.firstName && errors.email.type === "maxLength") {
        return (<span class="error">First name is too long</span>);
    } else if (errors.firstName && errors.email.type === "minLength") {
        return (<span class="error">First name is too short</span>);
    }
};

export const validateLastName = (errors) => {
    if (errors.lastName && errors.lastName.type === "required") {
        return (<span class="error">Last name is required</span>);
    } else if (errors.lastName && errors.lastName.type === "maxLength") {
        return (<span class="error">Last name is too long</span>);
    } else if (errors.lastName && errors.lastName.type === "minLength") {
        return (<span class="error">Last name is too short</span>);
    }
}

export const validateEmail = (errors) => {
    if (errors.email && errors.email.type === "required") {
        return (<span class="error">Email is required</span>);
    } else if (errors.email && errors.email.type === "pattern") {
        return (<span class="error">Invalid email</span>)
    }
};

export const validatePassword = (errors) => {
    if (errors.password && errors.password.type === "required") {
        return (<span class="error">Password is required</span>);
    } else if (errors.password && errors.password.type === "minLength") {
        return (<span class="error">Password is too short</span>)
    } else if (errors.password && errors.password.type === "maxLength") {
        return (<span class="error">Password is too long</span>)
    }
}