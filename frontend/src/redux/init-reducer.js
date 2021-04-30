import {getAuthorizedUserThunk} from "./auth-reducer";

const INIT = 'init';

const initialState = {
    isInitialized: false,
    initData: null
}

const initReducer = (state = initialState, action) => {
    switch (action.type) {
        case INIT:
            return {
                ...state,
                isInitialized: true,
                initData: action.initData
            };
        default:
            return state;
    }
}

export const getInitData = (initData) => {
    return {type: INIT, initData: initData};
}

export const getInitDataThunk = () => async (dispatch) => {
    dispatch(getAuthorizedUserThunk());
    dispatch(getInitData({}));
}

export default initReducer;