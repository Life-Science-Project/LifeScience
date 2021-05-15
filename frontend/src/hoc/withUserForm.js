import React from "react";
import {useForm} from "react-hook-form";

const withUseFormHook = (Component) => {
    return props => {
        const form = useForm();
        return <Component {...props} {...form} />
    }
}

export default withUseFormHook;