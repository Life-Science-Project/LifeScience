import {authApi} from "../api/auth-api";

const SIGN_IN = 'signin';
const SIGN_UP = 'signup';

let initialState = {
    user: null,
    isAuthorized: false,
    errorMsg: ""
}

const authReducer = (state = initialState, action) => {

    switch (action.type) {
        case SIGN_IN:
            return {
                ...state,
                user: action.user,
                isAuthorized: true,
                errorMsg: action.errorMsg
            };
        case SIGN_UP:
            return {
                ...state,
                status: true
            }
        default:
            return state;
    }
}

export const signInUser = ([_user, _errorMsg]) => {
    return {type: SIGN_IN, user: _user, errorMsg: _errorMsg};
}

export const signInUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signInUser(input);
    let result, errorMsg = "";
    if (response.status === 200) {
        result = response.data;
    } else {
        errorMsg = "Invalid login or password";
        result = null;
    }
    dispatch(signInUser([result, errorMsg]))
}

export const signUpUser = (_user) => {
    return {type: SIGN_UP, user: _user};
}

export const signUpUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signUpUser(input);
    let result;
    if (response.status === 200) {
        signInUserThunk({email: input.email, password: input.password});
        return;
    }
    dispatch(signInUser(result))
}

export default authReducer;