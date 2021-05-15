import instance from './api'

export const categoryApi = {
    getCategory(id) {
        const url = 'categories/' + (id === undefined ? 1 : id);
        return instance.get(url)
    },
    putCategory(id, data) {
        const url = `categories/${id}`;
        return instance.put(url, data);
    },
    deleteCategory(id) {
        const url = `categories/${id}`;
        return instance.delete(url);
    },
    postCategory(data) {
        const url = `categories`;
        return instance.post(url, data);
    }
}
