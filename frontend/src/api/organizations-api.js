import instance from './api'

export const organizationsApi = {
    getOrganizations() {
        let url = 'users/organisations'
        return instance.get(url)
    }
}