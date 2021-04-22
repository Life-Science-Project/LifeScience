import {categoryApi} from "../api/category-api";

const EMPTY = 'empty'
const GET_CATEGORY = 'GET_CATEGORY'

let initialState = {
    category: null,
    status: EMPTY
}

const categoryReducer = (state = initialState, action) => {

    switch (action.type) {
        case GET_CATEGORY:
            return {
                ...state,
                category: action.category
            }
        default:
            return state;
    }
}

export const getCategory = (_id) => {
    return {type: GET_CATEGORY, category: _id}
}

export const getCategoryThunk = (id) => async (dispatch) => {
    let response = await categoryApi.getCategory(id);
    let result;
    if (response.status === 200) {
        result = id !== undefined ? response.data : response.data[0];
    }
    dispatch(getCategory(result))
}

export default categoryReducer;