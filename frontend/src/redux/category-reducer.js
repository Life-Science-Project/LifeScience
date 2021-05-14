import {categoryApi} from "../api/category-api";

const EMPTY = 'empty';
const GET_CATEGORY = 'GET_CATEGORY';
const ERROR = 'ERROR';

let initialState = {
    category: null,
    status: EMPTY
};

const categoryReducer = (state = initialState, action) => {

    switch (action.type) {
        case GET_CATEGORY:
            return {
                ...state,
                category: action.category,
                error: null
            };
        case ERROR:
            return {
                ...state,
                error: action.error
            }
        default:
            return state;
    }
}

export const getCategory = (_id) => {
    return {type: GET_CATEGORY, category: _id}
}

export const getError = (reason, error) => {
    return {type: reason, error: error}
}

export const getCategoryThunk = (id) => async (dispatch) => {
    let response = await categoryApi.getCategory(id);
    if (response.status !== 200) {
        dispatch(getError(ERROR, response))
        return;
    }
    dispatch(getCategory(response.data))
}

export default categoryReducer;