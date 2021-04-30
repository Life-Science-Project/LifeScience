export const getUserView = (user) => {
    return {
        id: user.userDetailsId,
        firstName: user.firstName,
        lastName: user.lastName
    }
}