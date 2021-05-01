import {authApi} from "../api/auth-api";
import {initApi} from "../api/init-api";
import {getUserView} from "../utils/user-translator";
import {getInitData} from "./init-reducer";

const LOGOUT = 'logout';
const SIGN_IN = 'signin';
const SIGN_UP = 'signup';
const ERROR = 'error';

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
                isAuthorized: true
            };
        case SIGN_UP:
            return {
                ...state,
                status: true
            }
        case ERROR:
            return {
                ...state,
                errorMsg: action.errorMsg
            }
        case LOGOUT:
            return {
                ...state,
                user: null,
                isAuthorized: false
            }
        default:
            return state;
    }
}

export const logoutUser = () => {
    localStorage.removeItem('jwtToken');
    return {type: LOGOUT}
}

export const error = (_errorMsg) => {
    return {type: ERROR, errorMsg: _errorMsg};
}

export const signInUser = ([_user, _errorMsg]) => {
    return {type: SIGN_IN, user: _user};

}

export const getAuthorizedUserThunk = () => async (dispatch) => {
    let response = await initApi.getAuthorizedUser();
    let errorMsg = "";
    if (response.status === 200) {
        dispatch(signInUser([getUserView(response.data), errorMsg]));
    } else {
        localStorage.removeItem('jwtToken');
    }
    alert(JSON.stringify(response))
    dispatch(getInitData({}));
}

export const signInUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signInUser(input);
    let result, errorMsg = "";
    if (response.status === 200) {
        result = response.data.userView;
        localStorage.setItem('jwtToken', response.data.jwtResponse.accessToken);
        dispatch(signInUser([result, errorMsg]));
    } else {
        errorMsg = "Invalid login or password";
        result = null;
        dispatch(error(errorMsg));
    }
}

export const signUpUser = (_user) => {
    return {type: SIGN_UP, user: _user};
}

export const signUpUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signUpUser({
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        password: input.password
    });
    let result, errorMsg = "";
    if (response.status === 200) {
        // Auth User on successful registration
        const signInData = {email : input.email, password : input.password};
        dispatch(signInUserThunk(signInData));
    } else {
        // TODO proper error handling
        dispatch(error("User already exists"))
    }
}

export default authReducer;