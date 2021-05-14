import instance from './api'

export const protocolApi = {
    postProtocol(articleId, name, sections) {
        const url = `/articles/versions/article/${articleId}`;
        return instance.post(url, {
            name: name,
            sections: sections,
        })
    },
    requestProtocolReview(versionId) {
        const url = `/review/request/version/${versionId}`;
        return instance.patch(url, {
            destination: "PROTOCOL"
        })
    },
    approveProtocol(versionId, requestId) {
        const url = `/articles/versions/${versionId}/reviews/request/${requestId}`;
        return instance.post(url, {
            comment: "This protocol was auto-approved",
            resolution: "APPROVE"
        });
    }
}