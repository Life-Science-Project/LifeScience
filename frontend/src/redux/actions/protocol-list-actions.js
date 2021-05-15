import {articleApi} from "../../api/method-api";

export const RECEIVE_PROTOCOLS = "RECEIVE_PROTOCOLS"
export const CLEAR_PROTOCOLS = "CLEAR_PROTOCOLS"

const receiveProtocols = (data) => {
    return {
        type: RECEIVE_PROTOCOLS,
        protocols: data.protocols,
    }
}

export const clearProtocols = () => {
    return {
        type: CLEAR_PROTOCOLS,
    }
}

export const fetchProtocols = (articleId) => {
    return dispatch => {
        return articleApi.getArticle(articleId)
            .then(response => response.data)
            .then(data => dispatch(receiveProtocols(data)))
    }
}
