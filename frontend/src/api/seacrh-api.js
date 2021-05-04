import instance from './api'

export const searchApi = {
    search(query) {
        const data = {
            text: query,
        }
        const url = "search"
        return instance.post(url, data)
    }
}
