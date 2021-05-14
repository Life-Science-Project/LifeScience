import {articleApi} from "../api/method-api";

const ERROR = 'ERROR';
const EMPTY = 'empty';
const GET_ARTICLE = 'GET_ARTICLE';

let initialState = {
    article: null,
    status: EMPTY
};

const articleReducer = (state = initialState, action) => {

    switch (action.type) {
        case GET_ARTICLE:
            return {
                ...state,
                article: action.article
            };
        case ERROR:
            return {
                ...state,
                trouble: action.trouble
            }
        default:
            return state;
    }
}

export const getArticle = (_article) => {
    return {type: GET_ARTICLE, article: _article}
}

export const getError = (trouble, reason) => {
    return {type: reason, trouble: trouble}
}


export const getArticleThunk = (id) => async (dispatch) => {
    let response = await articleApi.getArticle(id);
    if (response.status !== 200) {
        dispatch(getError(response.data, ERROR))
    }
    const result = response.data;
    dispatch(getArticle(result));
}

export default articleReducer;
