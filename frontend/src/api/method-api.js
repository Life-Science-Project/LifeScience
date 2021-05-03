import {instance} from './api'

export const methodApi = {
    getMethod(articleId) {
        const url = 'articles/' + articleId
        return instance.get(url)
    }
}

export const sectionApi = {
    getSection(versionId, sectionId) {
        const url = `articles/versions/${versionId}/sections/${sectionId}`
        return instance.get(url)
    }
}

