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
        const url = 'users/organizations/count';
        return instance.get(url);
    }
}