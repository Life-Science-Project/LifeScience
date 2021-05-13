import instance from './api'

export const categoryApi = {
    getCategory(id) {
        let url = 'categories/' + (id === undefined ? 'root' : id)
        return instance.get(url)
    }
}
