import {
    CLEAR_POST_STATUS,
    ERROR_WHILE_POSTING,
    POST_ARTICLE,
    PostStatusEnum,
    RECEIVE_POSTED_ARTICLE
} from "./actions/new-article-actions";

const initialState = {
    postStatus: PostStatusEnum.NOT_POSTED,
    versionId: 0,
}

export default function newArticleReducer(state = initialState, action) {
    switch (action.type) {
        case POST_ARTICLE: return {
            ...state,
            postStatus: PostStatusEnum.POSTING,
        }
        case RECEIVE_POSTED_ARTICLE : return {
            ...state,
            postStatus: PostStatusEnum.POSTED,
            versionId: action.versionId,
        }
        case CLEAR_POST_STATUS : return {
            ...state,
            postStatus: PostStatusEnum.NOT_POSTED,
            versionId: 0,
        }
        case ERROR_WHILE_POSTING : return {
            ...state,
            postStatus: PostStatusEnum.ERROR,
            errorMessage: action.message
        }
        default: return state
    }
}
