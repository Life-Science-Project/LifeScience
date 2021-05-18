import {protocolApi} from "../api/protocol-api";
import {PostStatusEnum} from "./actions/new-article-actions";

const GET_PROTOCOL = 'GET_PROTOCOL'
const POST_PROTOCOL = 'POST_PROTOCOL'

const initialState = {
    postStatus: PostStatusEnum.NOT_POSTED,
    versionId: 0,
}

export default function protocolReducer(state = initialState, action) {
    switch (action.type) {
        case GET_PROTOCOL:
            return {
                ...state,
                postStatus: PostStatusEnum.POSTED,
                versionId: action.versionId,
            }
        case POST_PROTOCOL:
            return {
                ...state,
                postStatus: PostStatusEnum.POSTING
            }
        default:
            return state

    }
}

export const getProtocol = (versionId) => {
    return {type: GET_PROTOCOL, versionId: versionId}
};

export const postProtocol = () => {
    return {type: POST_PROTOCOL};
}

export const addProtocolThunk = (articleId, name, sections) => async (dispatch) => {
    dispatch(postProtocol());
    let response = await protocolApi.postProtocol(articleId, name, sections);
    console.log(response);
    //todo exception handling
    if (response.status !== 200) return
    let versionId = response.data.id;
    response = await protocolApi.requestProtocolReview(versionId);
    console.log(response)
    if (response.status !== 200) return
    let requestId = response.data.id;

    response = await protocolApi.approveProtocol(versionId, requestId);
    console.log(response);
    dispatch(getProtocol(articleId))
}