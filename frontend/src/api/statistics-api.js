import instance from './api'

export const statisticsApi = {
    getStatistics() {
        const url = 'statistics/api/fix-versions';
        return instance.get(url);
    }
}