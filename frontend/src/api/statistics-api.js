import instance from './api'

export const statisticsApi = {
    getUserStatistics() {
        const url = 'users/count';
        return instance.get(url);
    },
    getArticleStatistics() {
        const url = 'articles/count';
        return instance.get(url);
    },
    getOrganizationsStatistics() {
        const url = 'users/organisations/count';
        return instance.get(url);
    }
}