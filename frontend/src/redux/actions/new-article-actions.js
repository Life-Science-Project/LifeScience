import {articleApi, contentApi} from "../../api/method-api";

export const POST_ARTICLE = "POST_ARTICLE";
export const RECEIVE_POSTED_ARTICLE = "RECEIVE_POSTED_ARTICLE";
export const CLEAR_POST_STATUS = "CLEAR_POST_STATUS";

export const PostStatusEnum = Object.freeze({
    NOT_POSTED: 0,
    POSTING: 1,
    POSTED: 2,
})

function postArticle() {
    return {
        type: POST_ARTICLE,
    }
}

function receivePostedArticle(articleId) {
    return {
        type: RECEIVE_POSTED_ARTICLE,
        articleId: articleId,
    }
}

export function clearPostStatus() {
    return {
        type: CLEAR_POST_STATUS,
    }
}

export const addMethodThunk = (categoryId, name, sections) => async (dispatch) => {
    dispatch(postArticle())
    let response = await articleApi.postVersion(categoryId, name);
    if (response.status === 200) {
        let versionId = response.data.id;
        const articleId = response.data.articleId;
        for (const s of sections) {
            response = await articleApi.postSection(versionId, s.name);
            if (response.status === 200) {
                let sectionId = response.data.id;
                if (s.content) response = await contentApi.postContent(sectionId, s.content);
            }
        }
        if (response.status === 200) {
            await articleApi.approve(versionId);
            dispatch(receivePostedArticle(articleId))
        }
    }
}