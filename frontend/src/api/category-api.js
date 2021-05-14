import instance from './api'

export const categoryApi = {
    getCategory(id) {
        const url = 'categories/' + (id === undefined ? 1 : id);
        return instance.get(url)
    }
}
