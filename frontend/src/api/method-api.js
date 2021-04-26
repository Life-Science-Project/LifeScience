import {instance} from './api'

export const methodApi = {
    getMethod(articleId) {
        let url = 'articles/' + articleId
        return instance.get(url)
    }
}