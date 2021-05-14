export const errorHandler = (error) => {
    return {status: error.status, message: getMessage(error)};
}

export const getMessage = (error) => {
    return error.trouble.message ? error.trouble.message : getStandardMessage(error)
}

export const getStandardMessage = (error) => {
    switch (error.status) {
        case 400:
            return 'Bad request';
        case 403:
            return 'Forbidden';
        case 404:
            return 'Not Found';
        default:
            return 'Unknown reason';
    }
}
