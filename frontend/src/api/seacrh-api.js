import {instance} from './api'

export const searchApi = {
    search(query, tags, exclusionTypes) {
        const data = {
            query: query,
            tags: tags ? tags : [],
            exclusionTypes: exclusionTypes ? exclusionTypes : [],
        }
        const url = "/search"
        return instance.post(url, data)
    }
}
