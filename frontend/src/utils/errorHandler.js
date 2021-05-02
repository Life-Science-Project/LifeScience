export const errorHandler = (error) => {
    switch (error.status) {
        case 400:
            return {status: 400, message: 'Bad request'};
        case 403:
            return {status: 403, message: 'Forbidden'};
        case 404:
            return {status: 404, message: 'Not Found'};
        default:
            return {status: 400,  message: 'Unknown reason'};
    }
}