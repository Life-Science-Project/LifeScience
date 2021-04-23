import {authApi} from "../api/auth-api";

const SIGN_IN = 'signin';
const SIGN_UP = 'signup';

let initialState = {
    user: null,
    isAuthorized: false
}

const authReducer = (state = initialState, action) => {

    switch (action.type) {
        case SIGN_IN:
            return {
                ...state,
                user: action.user,
                isAuthorized: true
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

export const signInUser = (_user) => {
    return {type: SIGN_IN, user: _user};
}

export const signInUserThunk = (input) => async (dispatch) => {
    console.log("here")
    let response = await authApi.signInUser(input);
    let result;
    if (response.status === 200) {
        result = response.data;
    }
    console.log(result)
    dispatch(signInUser(result))
}

export const signUpUser = (_user) => {
    return {type: SIGN_UP, user: _user};
}

export const signUpUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signUpUser(input);
    let result;
    // TODO
    dispatch(signInUser(result))
}

export default authReducer;