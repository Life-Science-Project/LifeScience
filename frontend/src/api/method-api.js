import instance from './api'

export const methodApi = {
    getMethod(id) {
        const url = 'articles/versions/' + id;
        return instance.get(url);
    }
}

export const sectionApi = {
    getSection(versionId, sectionId) {
        const url = `articles/versions/${versionId}/sections/${sectionId}`;
        return instance.get(url);
    },
    putSection(versionId, sectionId, data) {
        const url = `articles/versions/${versionId}/sections/${sectionId}`;
        return instance.put(url, data);
    },
    deleteSection(versionId, sectionId) {
        const url = `articles/versions/${versionId}/sections/${sectionId}`;
        return instance.delete(url);
    },
    getAllSections(versionId) {
        const url = `articles/versions/${versionId}/sections`;
        return instance.get(url);
    },
    postSection(versionId, data) {
        const url = `articles/versions/${versionId}/sections`;
        return instance.post(url, data);
    }
}

export const articleVersionApi = {
    getVersion(versionId) {
        const url = `articles/versions/${versionId}`;
        return instance.get(url);
    },
    putVersion(versionId, data) {
        const url = `articles/versions/${versionId}`;
        return instance.put(url, data);
    },
    putCopyOfVersion(versionId) {
        const url = `articles/versions/${versionId}/copy`;
        return instance.put(url);
    },
    /**
     * Creates new article AND new version inside it with optional sections and content
     *
     * @param data - article
     * @returns {Promise<AxiosResponse<any>>}
     */
    postNewArticle(data) {
        const url = `articles/versions`;
        return instance.post(url, data);
    },
    postNewArticleVersion(articleId, data) {
        const url = `articles/versions/article/${articleId}`;
        return instance.post(url, data);
    },
    archiveVersion(versionId) {
        const url = `articles/versions/${versionId}/archive`;
        return instance.patch(url);
    },
    getCompletedVersion(versionId) {
        const url = `articles/versions/completed/${versionId}`;
        return instance.get(url);
    }
}



export const contentApi = {
    postContent(sectionId, content) {
        const url = `articles/versions/sections/${sectionId}/contents`;
        return instance.post(url, {
            sectionId: sectionId,
            text: content,
            references: [],
            tags: []
        })
    }
}

export const reviewApi = {
    /**
     * Set status to article approve or needed to insert changes.
     *
     * @param versionId - version of article for which is making review.
     * @param requestId - request which is reviewing.
     * @param data - contains comment from approver and resolution. Possible resolution values: [APPROVE, CHANGES_REQUESTED].
     * @returns {Promise<AxiosResponse<any>>}
     */
    postAnswerRequest(versionId, requestId, data) {
        const url = `articles/versions/${versionId}/reviews/request/${requestId}`;
        return instance.post(url, data);
    },
    getReviews(versionId) {
        const url = `articles/versions/${versionId}/reviews`;
        return instance.get(url);
    },
    getReviewsOfRequest(versionId, requestId) {
        const url = `articles/versions/${versionId}/reviews/request/${requestId}`;
        return instance.get(url);
    }
}

export const reviewRequestApi = {
    getReviewRequests(versionId) {
        const url = `review/request/version/${versionId}`;
        return instance.get(url);
    },
    /**
     * Creates a review request for version of protocol or article.
     *
     * @param versionId - version of article
     * @param data - destination, available values: [PROTOCOL, ARTICLE].
     * @returns {Promise<AxiosResponse<any>>}
     */
    patchRequest(versionId, data) {
        const url = `review/request/version/${versionId}`;
        return instance.patch(url, data);
    },
    getActiveRequests(versionId) {
        const url = `review/request/version/active/${versionId}`;
        return instance.get(url);
    },
    deleteRequest(requestId) {
        const url = `review/request/${requestId}`;
        return instance.delete(url);
    }
}
