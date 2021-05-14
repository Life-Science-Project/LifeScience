import instance from './api'

export const methodApi = {
    getMethod(id) {
        const url = 'articles/versions/' + id;
        return instance.get(url);
    },

    addMethod(categoryId) {
        const url = 'articles/';
        return instance.post(url, {categoryId});
    }
}

export const sectionApi = {
    getSection(versionId, sectionId) {
        const url = `articles/versions/${versionId}/sections/${sectionId}`;
        return instance.get(url);
    }
}

export const articleApi = {
    postVersion(categoryId, name, sections) {
        const url = 'articles/versions';
        return instance.post(url, {
            articleDTO: {
                categoryId: categoryId
            },
            name: name,
            sections: sections,
        })
    },
    postSection(versionId, name) {
        const url = `/articles/versions/${versionId}/sections`
        return instance.post(url, {
            name: name,
            articleVersionId: versionId,
            description: "",
            parameters: [],
            order: 0,
            visible: true
        })
    },
    approve(versionId) {
        const url = `/articles/versions/${versionId}/reviews/approve`;
        return instance.patch(url);
    },
    getArticle(articleId) {
        const url = `/articles/${articleId}`;
        return instance.get(url);
    }
}

export const contentApi = {
    postContent(sectionId, content) {
        const url = `/articles/versions/sections/${sectionId}/contents`;
        return instance.post(url, {
            sectionId: sectionId,
            text: content,
            references: [],
            tags: []
        })
    }
}

