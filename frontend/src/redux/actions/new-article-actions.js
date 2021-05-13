import {articleVersionApi, reviewApi, reviewRequestApi} from "../../api/method-api";
import {createArticle, createRequest, createReview} from "../../utils/article";

export const POST_ARTICLE = "POST_ARTICLE";
export const RECEIVE_POSTED_ARTICLE = "RECEIVE_POSTED_ARTICLE";
export const CLEAR_POST_STATUS = "CLEAR_POST_STATUS";
export const ERROR_WHILE_POSTING = "ERROR_WHILE_POSTING";

export const RequestDestination = Object.freeze({
    article: "ARTICLE",
    protocol: "PROTOCOL"
})

export const ReviewResolution = Object.freeze({
    approve: "APPROVE",
    chgRequested: "CHANGES_REQUESTED"
})

export const PostStatusEnum = Object.freeze({
    ERROR: -1,
    NOT_POSTED: 0,
    POSTING: 1,
    POSTED: 2
})

function postArticle() {
    return {
        type: POST_ARTICLE,
    }
}

function receivePostedArticle(versionId) {
    return {
        type: RECEIVE_POSTED_ARTICLE,
        versionId: versionId,
    }
}

function error(_error) {
    return {
        type: ERROR_WHILE_POSTING,
        message: _error.message
    }
}

export function clearPostStatus() {
    return {
        type: CLEAR_POST_STATUS,
    }
}

/**
 * Create new entity of article, and made put request to backend.
 *
 * @param categoryId - id of inserting category.
 * @param name - article name.
 * @param sections - article sections.
 * @returns {(function(*): Promise<void>)|*}
 */
export const addArticleThunk = (categoryId, name, sections) => async (dispatch) => {
    dispatch(postArticle());

    const article = createArticle(categoryId, name, sections);

    const response = await articleVersionApi.postNewArticle(article);

    if (response.status !== 200) {
        dispatch(error(response.error.message));
        return;
    }

    const versionId = response.data.id;

    //Temporary magic
    const requestResponse = await reviewRequestApi.patchRequest(versionId, createRequest(RequestDestination.article));
    console.log(JSON.stringify(requestResponse))
    const reviewResponse = await reviewApi.postAnswerRequest(versionId, requestResponse.data.id, createReview(ReviewResolution.approve, "All is perfect"));
    console.log(JSON.stringify(reviewResponse))
    //

    dispatch(receivePostedArticle(versionId))
}